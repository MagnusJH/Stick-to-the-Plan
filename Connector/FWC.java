import java.sql.Connection;
import java.sql.SQLException;

public abstract class FWC<T> {
    protected Connection con;
    protected int parentID;

    public FWC(Connection con, int parentID) {
        this.con = con;
        this.parentID = parentID;
    }

    protected abstract void addEntry(T entry) throws Exception;

    public void addRow(T entry) {
        try {
            addEntry(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
