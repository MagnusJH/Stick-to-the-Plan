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

    public int getCals() {
        return cals;
    }

    public int getNutrNum() {
        return nutrNum;
    }

    public String getNutrName() {
        return nutrName;
    }
}
