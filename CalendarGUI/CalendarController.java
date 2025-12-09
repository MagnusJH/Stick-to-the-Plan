package CalendarGUI;

import Connector.Connector;
import FoodGUI.FoodEntry;
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
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarController implements Initializable {

    private final List<CalendarActivity> allActivities = new ArrayList<>();
    MainFolder.SceneLoader loader = new MainFolder.SceneLoader();

    Connector c = new Connector();
    Connection con = c.getConnection();

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    private String username;

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

    // constructor
    public CalendarController(String username) {
        this.username = username;
    }

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

// Add food entries into the calendar map
        /*
        for (int day = 1; day <= dateFocus.getMonth().maxLength(); day++) {

            int fwcID = getCalendarEntry(dateFocus.getYear(), dateFocus.getMonthValue(), day);

            if (fwcID != -1) {
                List<CalendarActivity> foodActs = getFoodActivitiesForDate(fwcID);

                if (!foodActs.isEmpty()) {
                    calendarActivityMap
                            .computeIfAbsent(day, d -> new ArrayList<>())
                            .addAll(foodActs);
                }
            }
        }*/



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

                            // check for calendar entry
                            int fwcID = getCalendarEntry(dateFocus.getYear(), dateFocus.getMonthValue(), currentDate);
                            // add calendar entry if does not exist then get the id for that entry
                            if (fwcID == -1) {
                                addCalendarEntry(dateFocus.getYear(), dateFocus.getMonthValue(), currentDate);
                                fwcID = getCalendarEntry(dateFocus.getYear(), dateFocus.getMonthValue(),  currentDate);
                            }

                            // hide calendar
                            MainFolder.SceneLoader.getCalendarStage().close();

                            // load weight page
                            try {
                                loader.weightPage(fwcID);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            // load food page
                            try {
                                loader.foodPage(fwcID);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            // load cardio page
                            try {
                                loader.cardioPage(fwcID);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            loader.getWeightStage().show();
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

    private int getCalendarEntry(int year, int month, int day) {
        String sql = "SELECT FWCTable FROM calendar WHERE dateID = ? AND userName = ?";
        try {
            PreparedStatement prepState = con.prepareStatement(sql);
            prepState.setString(1, year + "-" + month + "-" + day);
            prepState.setString(2, username);

            ResultSet result = prepState.executeQuery();

            if (result.next()) {
                return result.getInt("FWCTable");
            } else {
                return -1;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCalendarEntry(int year, int month, int day) {
        String insert = "INSERT INTO calendar (userName, dateID) VALUES (?, ?)";

        try (PreparedStatement prepState = con.prepareStatement(insert)) {
            prepState.setString(1, username);
            prepState.setString(2, year + "-" + month + "-" + day);

            prepState.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
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

            Text text = new Text(activity.getName() + ", " + activity.getDate().toLocalTime());
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
