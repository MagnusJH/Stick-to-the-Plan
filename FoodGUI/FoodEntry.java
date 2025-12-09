package FoodGUI;

import javafx.beans.property.*;

public class FoodEntry {
    private int rowID;
    private final StringProperty foodName;
    private final IntegerProperty cals;
    private final IntegerProperty nutrNum;

    public FoodEntry(int rowID, String foodName, int cals, int nutrNum) {
        this.rowID = rowID;
        this.foodName = new SimpleStringProperty(foodName);
        this.cals = new SimpleIntegerProperty(cals);
        this.nutrNum = new SimpleIntegerProperty(nutrNum);
    }

    public int getRowID() {
        return rowID;
    }

    public String getFoodName() {
        return foodName.get();
    }

    public void setFoodName(String foodName) {
        this.foodName.set(foodName);
    }

    public StringProperty foodNameProperty() {
        return foodName;
    }

    public int getCals() {
        return cals.get();
    }

    public void setCals(int cals) {
        this.cals.set(cals);
    }

    public IntegerProperty calsProperty() {
        return cals;
    }

    public int getNutrNum() {
        return nutrNum.get();
    }

    public void setNutrNum(int nutrNum) {
        this.nutrNum.set(nutrNum);
    }

    public IntegerProperty nutrNumProperty() {
        return nutrNum;
    }

    @Override
    public String toString() {
        return "FoodEntry{" +
                "rowID=" + rowID +
                ", foodName=" + foodName.get() +
                ", cals=" + cals.get() +
                ", nutrNum=" + nutrNum.get() +
                '}';
    }
}

