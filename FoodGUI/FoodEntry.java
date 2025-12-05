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

    public void setFoodName(String f) {
        foodName.set(f);
    }

    public StringProperty fNameProperty() {
        return foodName;
    }

    public int getCals() {
        return cals.get();
    }

    public void setCals(int c) {
        cals.set(c);
    }

    public IntegerProperty calsProperty() {
        return cals;
    }

    public int getNutrNum() { return nutrNum.get();
    }

    public void setNutrNum(int n) {
        nutrNum.set(n);
    }

    public IntegerProperty  nutrNumProperty() {
        return nutrNum;
    }

}

