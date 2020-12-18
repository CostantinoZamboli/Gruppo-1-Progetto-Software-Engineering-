package main;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FacadeEmail {

    private Email email;
    private PostgresDbConnection dbConnection;
    private EwoActivity ewoActivity;
    private PlannedActivity plannedActivity;
    private String site;
    private String area;
    private String typology;
    private String timeRequired;
    private String interventionDescription;
    private String workspaceNotes;
    private int idMaintainer;
    private String nomeMaintainer;
    private  int idActivity;
    private String day;
    private String timeSlot1;
    private String timeSlot2;
    private int week;
    private String timeStamp;
    private String jsonInfo;

    public FacadeEmail(String username, String password, int idActivity, String jsonInfo){
        this.jsonInfo = jsonInfo;
        this.idActivity = idActivity;
        this.email = new Email(username, password);
        this.dbConnection = new PostgresDbConnection();
        this.ewoActivity = new EwoActivity();
        this.plannedActivity = new PlannedActivity();
    }

    /**
     * Utilizza i metodi della classe EwoActivity istanziata nel costruttore per prelevare
     * le informazioni necessarie per costruire il contenuto dell'email.
     * Utilizza il metodo sendEmail di Email per inviare la notifica.
     * @return stringa contenente l'esito dell'invio dell'email
     */
    public String sendEmailEwo(){

        String activityJson = null;
        try {
            activityJson = ewoActivity.getEwoByIdToJson(idActivity, dbConnection);
        } catch (Exception e) {
            return "Failed to send Email";
        }
        String content = createContentEwo(activityJson, jsonInfo);
        try {
            setRecipientAddress();     // get email da maitainer e setEmail
        } catch (Exception e) {
            return "Failed to send email. Error getting recipient email!";
        }
        email.setTitle("EWO ACTIVITY ASSIGNMENT");
        String result = email.sendEmail(content);
        try {
                ewoActivity.updateState(idActivity, dbConnection);
        } catch (Exception e) {
            return "Update EWO state failed";
        }
        return result;
    }

    /**
     * Utilizza i metodi della classe PlannedActivity istanziata nel costruttore per prelevare
     * le informazioni necessarie per costruire il contenuto dell'email.
     * Utilizza il metodo sendEmail di Email per inviare la notifica.
     * @return stringa contenente l'esito dell'invio dell'email
     */
    public String sendEmailPlanned(){

        String activityJson = null;
        try {
            activityJson = plannedActivity.getActivityByIdToJson(idActivity, dbConnection);
        } catch (Exception e) {
            return "Failed to send email. Error while retrieving information";
        }
        String content = createContentPlanned(activityJson, jsonInfo);
        try {
            setRecipientAddress();
        } catch (Exception e) {
            return "Failed to send email. Wrong recipient address!";
        }

        email.setTitle("PLANNED ACTIVITY ASSIGNMENT");
        String returnSendEmail = email.sendEmail(content);

        try {
            plannedActivity.deletePlannedActivityById(dbConnection, idActivity);
        } catch (Exception e) {
            return "Failed to delete Planned activity";
        }

        return returnSendEmail;

    }

    private String createContentEwo(String activityJson, String jsonInfo){
        JSONArray jsonArray = new JSONArray(activityJson);
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            this.site = (jsonObject.getString("site"));
            this.area = (jsonObject.getString("area"));
            this.typology = (jsonObject.getString("typology"));
            this.timeRequired = (jsonObject.getString("time"));
            this.interventionDescription = (jsonObject.getString("intervention_description"));
            this.workspaceNotes = (jsonObject.getString("workspace_notes"));
        }

        JSONArray jsonArray1 = new JSONArray(jsonInfo);
        for(int i=0; i<jsonArray1.length(); i++){
            JSONObject jsonObject = jsonArray1.getJSONObject(i);
            this.idMaintainer = jsonObject.getInt("id_maintainer");
            this.week = jsonObject.getInt("week");
            this.day = jsonObject.getString("day");
            this.timeSlot1 = jsonObject.getString("time1");
            this.timeSlot2 = jsonObject.getString("time2");
            this.nomeMaintainer = jsonObject.getString("name");
            this.timeStamp = jsonObject.getString("timestamp");
        }

        return "Dear " + nomeMaintainer + ", You have been assigned the following activity: " + "\n" +
                site + " - " + area + "  " + typology + "\n" + "Time required: " + timeRequired + "\nIntervention description: " +
                interventionDescription + "\nWorkspace notes: " + workspaceNotes + "\nweek: " + week + "/ day: " + day + " / time slot: " + timeSlot1 + " / " + timeSlot2 + "\n\n\n" + timeStamp;
    }

    private String createContentPlanned(String activityJson, String jsonInfo){
        JSONArray jsonArray = new JSONArray(activityJson);
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            this.site = (jsonObject.getString("site"));
            this.area = (jsonObject.getString("area"));
            this.typology = (jsonObject.getString("typology"));
            this.timeRequired = (jsonObject.getString("time"));
            this.interventionDescription = (jsonObject.getString("intervention_description"));
            this.workspaceNotes = (jsonObject.getString("workspace_notes"));
        }

        JSONArray jsonArray1 = new JSONArray(jsonInfo);
        for(int i=0; i<jsonArray1.length(); i++){
            JSONObject jsonObject = jsonArray1.getJSONObject(i);
            this.idMaintainer = jsonObject.getInt("id_maintainer");
            this.week = jsonObject.getInt("week");
            this.day = jsonObject.getString("day");
            this.timeSlot1 = jsonObject.getString("time");
            this.nomeMaintainer = jsonObject.getString("name");
            this.timeStamp = jsonObject.getString("date");
        }
         return "Dear " + nomeMaintainer + ", You have been assigned the following activity: " + "\n" +
                site + " - " + area + "  " + typology + "\n" + "Time required: " + timeRequired + "\nIntervention description: " +
                interventionDescription + "\nWorkspace notes: " + workspaceNotes + "\nweek: " + week + "/ day: " + day + " / time slot: " + timeSlot1 + "\n\n\n" + timeStamp;

    }

    private void setRecipientAddress() throws Exception{

        try {
            PostgresDbConnection dbConnection = new PostgresDbConnection();
            Connection con = dbConnection.connect();
            PreparedStatement stmt = con.prepareStatement("select email from Maintainer where id_maintainer = ?");
            stmt.setInt(1, this.idMaintainer);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                email.setRecipient(rs.getString(1));
            }
        }
        catch (Exception ex){
            throw ex;
        }
    }




}
