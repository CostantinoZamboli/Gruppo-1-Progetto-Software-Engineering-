package test;

import main.EwoActivity;
import main.PostgresDbConnection;

public class TestEwoActivity {

    public static void main(String[] args){

        PostgresDbConnection dbConnection = new PostgresDbConnection();
        EwoActivity ewoActivity = new EwoActivity();

        /*
        System.out.println(dbToJson.getDatatoJson(dbConnection));

        String activity = dbToJson.getActivityByIdtoJson(2, dbConnection);
        System.out.println(activity);

        System.out.println(dbToJson.getSkillByIdtoJson(9, dbConnection));
        dbToJson.updateWorkspaceNotes(4, "zimeo", dbConnection);
        System.out.println(dbToJson.getMaintainerNameWeek(49, 1,dbConnection));
        System.out.println(dbToJson.timeSlotAvailability(dbConnection, 1, 3, 50, "Wednesday"));
        System.out.println(dbToJson.getTicketsToJson(dbConnection));
        System.out.println(dbToJson.getTicketByIdToJson(dbConnection, 11));
        System.out.println(dbToJson.insertEWO(dbConnection, 10, "prova", 20, 50, "Tuesday", 2, 3));

        System.out.println(dbToJson.getSkillToJson(dbConnection));
                //System.out.println(dbToJson.timeSlotAvailabilityEwo(dbConnection, 1, 50, "Wednesday"));

        /*String activity = dbToJson.getEWOByIdToJson(15, dbConnection);
        System.out.println(activity);

        System.out.println(dbToJson.getTicketsToJson(dbConnection));
        System.out.println(dbToJson.getTicketByIdToJson(dbConnection, 15));*/

//        try {
//            System.out.println(dbToJson.getTickets(dbConnection));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            System.out.println(dbToJson.updateState(17, dbConnection));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            System.out.println(dbToJson.insertEwoActivity(dbConnection, 22, "prova", 20, 50, "Tuesday", 2, 2));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//        try {
//            System.out.println(dbToJson.getTicketByIdToJson(dbConnection, 22));
//        } catch (Exception e) {
//            System.out.println("zimep");
//
        // order 1 json


        try {
            System.out.println(ewoActivity.getTickets(dbConnection));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(ewoActivity.getTicketByIdToJson(dbConnection, 30));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(ewoActivity.getEwoByIdToJson(30, dbConnection));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(ewoActivity.getTimeSlotAvailabilityEwo(dbConnection, 30, 51, "Friday"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(ewoActivity.getTimeSlotAvailabilityEwo(dbConnection, 200, 51, "Friday"));
        } catch (Exception e) {
            e.printStackTrace();
        }






    }

}

