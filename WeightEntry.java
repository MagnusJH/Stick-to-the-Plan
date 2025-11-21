public class WeightEntry {
    private int rowID;
    private int sets;
    private int reps;
    private int weight;
    private String wName;

    public WeightEntry(int rowID, int sets, int reps, int weight, String wName) {
        this.rowID = rowID;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.wName = wName;
    }

    public int getRowID() {
        return rowID;
    }

    public String getSets() {
        return sets;
    }

    public int getReps {
        return reps;
    }

    public int getWeight() {
        return weight;
    }

    public String getWName() {
        return wName;
    }
}
