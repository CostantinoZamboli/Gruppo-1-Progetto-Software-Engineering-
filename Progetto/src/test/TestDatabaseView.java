package test;

import main.DatabaseView;
import main.PostgresDbConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.crypto.Data;

public class TestDatabaseView {

    public static void main(String[] args){

        PostgresDbConnection dbConnection = new PostgresDbConnection();
        DatabaseView dbToJson = new DatabaseView();
        System.out.println(dbToJson.getDatatoJson(dbConnection));

        String activity = dbToJson.getActivityByIdtoJson(1, dbConnection);
        System.out.println(activity);

        System.out.println(dbToJson.getSkillByIdtoJson(9, dbConnection));
        dbToJson.updateWorkspaceNotes(4, "zimeo", dbConnection);
        System.out.println(dbToJson.getMaintainerNameWeek(49, 1,dbConnection));
        System.out.println(dbToJson.timeSlotAvailability(dbConnection, 1, 1, 49, "Monday"));
    }

}

