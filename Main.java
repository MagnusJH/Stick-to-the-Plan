package Main;

import CalendarGUI.Calendar;
import Connector.Connector;
import FoodGUI.Food;
import WeightGUI.Weight;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connector c = new Connector();
        Connection con = c.getConnection();
        Calendar cal = new Calendar(2);
        Food food = new Food(con,1);
        Weight weight = new Weight(con,1);
    }
}
