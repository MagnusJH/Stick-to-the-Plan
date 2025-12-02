import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarController implements Initializable {

    private final List<CalendarActivity> allActivities = new ArrayList<>();

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    private int selectedDayForEvent;

    // UI ELEMENTS
    @FXML private Text year;
    @FXML private Text month;
    @FXML private FlowPane calendar;

    // NEW POPUP UI ELEMENTS
    @FXML private AnchorPane addEventPane;
    @FXML private Text selectedDateLabel;
    @FXML private TextField eventNameField;
    @FXML private TextField eventTimeField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar(){

        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }

        int dateOffset = ZonedDateTime.of(
                dateFocus.getYear(),
                dateFocus.getMonthValue(),
                1,
                0,0,0,0,
                dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {

                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);

                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setWidth(rectangleWidth);
                rectangle.setHeight(rectangleHeight);

                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);

                if (calculatedDate > dateOffset) {

                    int currentDate = calculatedDate - dateOffset;

                    if (currentDate <= monthMaxDate) {

                        Text dateText = new Text(String.valueOf(currentDate));
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        dateText.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(dateText);

                        int finalCurrentDate = currentDate;

                        stackPane.setOnMouseClicked(mouseEvent -> {
                            openPopupForDay(finalCurrentDate);
                        });

                        List<CalendarActivity> activities = calendarActivityMap.get(currentDate);
                        if (activities != null) {
                            createCalendarActivity(activities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }

                    if (today.getYear() == dateFocus.getYear()
                            && today.getMonth() == dateFocus.getMonth()
                            && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }

                calendar.getChildren().add(stackPane);
            }
        }
    }

    // -------------------------
    // NEW POPUP HANDLING
    // -------------------------

    private void openPopupForDay(int day) {
        selectedDayForEvent = day;

        selectedDateLabel.setText("Date: " + month.getText() + " " + day);
        eventNameField.clear();
        eventTimeField.clear();

        addEventPane.setVisible(true);
    }

    @FXML
    private void cancelAddEvent(ActionEvent event) {
        addEventPane.setVisible(false);
    }

    @FXML
    private void confirmAddEvent(ActionEvent e) {
        try {
            String[] parts = eventTimeField.getText().split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);

            ZonedDateTime eventDate = ZonedDateTime.of(
                    dateFocus.getYear(),
                    dateFocus.getMonthValue(),
                    selectedDayForEvent,
                    hour, minute, 0, 0,
                    dateFocus.getZone()
            );

            allActivities.add(new CalendarActivity(eventDate, eventNameField.getText(), 0));

            addEventPane.setVisible(false);

            calendar.getChildren().clear();
            drawCalendar();

        } catch (Exception ex) {
            System.out.println("Invalid input");
        }
    }

    // -------------------------
    // EXISTING CODE
    // -------------------------

    private void createCalendarActivity(List<CalendarActivity> calendarActivities,
                                        double rectangleHeight, double rectangleWidth,
                                        StackPane stackPane) {

        VBox activityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {

            if (k >= 2) {
                Text more = new Text("...");
                activityBox.getChildren().add(more);
                more.setOnMouseClicked(e -> System.out.println(calendarActivities));
                break;
            }

            CalendarActivity activity = calendarActivities.get(k);

            Text text = new Text(activity.getClientName() + ", " + activity.getDate().toLocalTime());
            activityBox.getChildren().add(text);

            text.setOnMouseClicked(e -> System.out.println(text.getText()));
        }

        activityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        activityBox.setMaxWidth(rectangleWidth * 0.8);
        activityBox.setMaxHeight(rectangleHeight * 0.65);
        activityBox.setStyle("-fx-background-color:GRAY");

        stackPane.getChildren().add(activityBox);
    }

    private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> activities) {
        Map<Integer, List<CalendarActivity>> map = new HashMap<>();

        for (CalendarActivity activity : activities) {
            int day = activity.getDate().getDayOfMonth();
            map.computeIfAbsent(day, d -> new ArrayList<>()).add(activity);
        }
        return map;
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {

        List<CalendarActivity> filtered = new ArrayList<>();

        for (CalendarActivity activity : allActivities) {
            if (activity.getDate().getYear() == dateFocus.getYear() &&
                    activity.getDate().getMonth() == dateFocus.getMonth()) {
                filtered.add(activity);
            }
        }

        return createCalendarMap(filtered);
    }

}
