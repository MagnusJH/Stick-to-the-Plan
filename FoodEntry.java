public class FoodEntry {
    private int rowID;
    private String foodName;
    private int cals;
    private int nutrNum;
    private String nutrName;

    public FoodEntry(int rowID, String foodName, int cals, int nutrNum, String nutrName) {
        this.rowID = rowID;
        this.foodName = foodName;
        this.cals = cals;
        this.nutrNum = nutrNum;
        this.nutrName = nutrName;
    }

    public int getRowID() {
        return rowID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String f) {
        foodName = f;
    }

    public int getCals() {
        return cals;
    }

    public void setCals(int c) {
        cals = c;
    }

    public int getNutrNum() {
        return nutrNum;
    }

    public void setNutrNum(int n) {
        nutrNum = n;
    }

    public String getNutrName() {
        return nutrName;
    }

    public void setNutrName(String n) {
        nutrName = n;
    }
}
