module GUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.jdi;
    requires javafx.graphics;
    requires mysql.connector.j;

    opens FoodGUI to javafx.fxml;
    exports FoodGUI;
    exports Connector;
    opens Connector to javafx.fxml;

    exports CardioGUI;
    opens CardioGUI to javafx.fxml;

    exports WeightGUI;
    opens WeightGUI to javafx.fxml;

    exports Login;
    opens Login to javafx.fxml;

    exports CalendarGUI;
    opens CalendarGUI to javafx.fxml;

}
