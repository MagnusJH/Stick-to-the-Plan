import java.time.ZonedDateTime;

public class CalendarActivity {
    private ZonedDateTime date;
    private String Name;
    private Integer serviceNo;

    public CalendarActivity(ZonedDateTime date, String Name, Integer serviceNo) {
        this.date = date;
        this.Name = Name;
        this.serviceNo = serviceNo;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getName() {
        return Name;
    }

    public void setClientName(String Name) {
        this.Name = Name;
    }

    public Integer getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(Integer serviceNo) {
        this.serviceNo = serviceNo;
    }

    @Override
    public String toString() {
        return "CalenderActivity{" +
                "date=" + date +
                ", Name='" + Name + '\'' +
                ", serviceNo=" + serviceNo +
                '}';
    }
}
