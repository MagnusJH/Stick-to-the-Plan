package WeightGUI;

import javafx.beans.property.*;

public class WeightEntry {
    private int rowID;
    private final StringProperty wName;
    private final IntegerProperty sets;
    private final IntegerProperty reps;
    private final IntegerProperty weight;

    public WeightEntry(int rowID, String wName, int sets, int reps, int weight) {
        this.rowID = rowID;
        this.wName = new SimpleStringProperty(wName);
        this.sets = new SimpleIntegerProperty(sets);
        this.reps = new SimpleIntegerProperty(reps);
        this.weight = new SimpleIntegerProperty(weight);
    }

    public int getRowID() {
        return rowID;
    }

    public String getWName() {
        return wName.get();
    }
    public void setWName(String value) {
        wName.set(value);
    }
    public StringProperty wNameProperty() {
        return wName;
    }

    public int getSets() {
        return sets.get();
    }
    public void setSets(int value) {
        sets.set(value);
    }
    public IntegerProperty setsProperty() {
        return sets;
    }

    public int getReps() {
        return reps.get();
    }
    public void setReps(int value) {
        reps.set(value);
    }
    public IntegerProperty repsProperty() {
        return reps;
    }

    public int getWeight() {
        return weight.get();
    }
    public void setWeight(int value) {
        weight.set(value);
    }
    public IntegerProperty weightProperty() {
        return weight;
    }
}

