package test;

import main.PostgresDbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDbConnection {

    public static void main(String[] args) {
        PostgresDbConnection db = new PostgresDbConnection();
        Connection con = db.connect();

        String result = "";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Planned_activity");
            String row;
            while (rs.next()) {
                row = rs.getInt(1) +";" + rs.getString(2) + ";" +
                        rs.getString(3) + ";" + rs.getString(4) +
                        rs.getInt(5) + ";" + rs.getInt(6) + "\n";
                result = result + row;

            }

            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(result);

    }

}
