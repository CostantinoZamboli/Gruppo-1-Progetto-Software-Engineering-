package test;

import main.PlannedActivity;
import main.PostgresDbConnection;

public class TestPlannedActivities {

    public static void main(String[] args) {

        PostgresDbConnection dbConnection = new PostgresDbConnection();
        PlannedActivity dbToJson = new PlannedActivity();

        try {
            System.out.println(dbToJson.getPlannedActivities(dbConnection));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(dbToJson.getActivityByIdToJson(6, dbConnection));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(dbToJson.getSkillByIdToJson(6, dbConnection));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(dbToJson.updateWorkspaceNotes(14, "prova update wn", dbConnection));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(dbToJson.getMaintainerAvailability(51, 66, dbConnection));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(dbToJson.getTimeSlotAvailability(dbConnection, 6, 1, 51, "Wednesday"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(dbToJson.deletePlannedActivityById(dbConnection, 6));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
