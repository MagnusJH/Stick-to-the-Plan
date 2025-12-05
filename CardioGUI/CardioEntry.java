package CardioGUI;

import javafx.beans.property.*;

public class CardioEntry {

    private final IntegerProperty rowID = new SimpleIntegerProperty();
    private final IntegerProperty cardioID = new SimpleIntegerProperty();
    private final StringProperty cName = new SimpleStringProperty();
    private final DoubleProperty duration = new SimpleDoubleProperty();
    private final StringProperty durType = new SimpleStringProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final StringProperty distType = new SimpleStringProperty();
    private final IntegerProperty caloriesBurned = new SimpleIntegerProperty();

    public CardioEntry(int cardioID, int rowID, String cName,
                       double duration, String durType,
                       double distance, String distType, int caloriesBurned) {

        this.cardioID.set(cardioID);
        this.rowID.set(rowID);
        this.cName.set(cName);
        this.duration.set(duration);
        this.durType.set(durType);
        this.distance.set(distance);
        this.distType.set(distType);
        this.caloriesBurned.set(caloriesBurned);
    }

    // Getters for TableView
    public StringProperty cNameProperty() { return cName; }
    public DoubleProperty durationProperty() { return duration; }
    public StringProperty durTypeProperty() { return durType; }
    public DoubleProperty distanceProperty() { return distance; }
    public StringProperty distTypeProperty() { return distType; }
    public IntegerProperty caloriesBurnedProperty() { return caloriesBurned; }

    public int getRowID() { return rowID.get(); }
    public int getCardioID() { return cardioID.get(); }
    public String getCName() { return cName.get(); }
    public double getDuration() { return duration.get(); }
    public String getDurType() { return durType.get(); }
    public double getDistance() { return distance.get(); }
    public String getDistType() { return distType.get(); }
    public int getCalBurned() { return caloriesBurned.get(); }


    // Setters
    public void setCName(String v) { cName.set(v); }
    public void setDuration(double v) { duration.set(v); }
    public void setDurType(String v) { durType.set(v); }
    public void setDistance(double v) { distance.set(v); }
    public void setDistType(String v) { distType.set(v); }
    public void setCaloriesBurned(int v) { caloriesBurned.set(v); }
}
