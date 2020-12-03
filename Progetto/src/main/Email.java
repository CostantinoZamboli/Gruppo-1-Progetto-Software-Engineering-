package main;

import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.crypto.Data;

public class Email {

    private int id_maintainer;
    private String name_maintainer;
    private String day;
    private int activity_id;
    private String time_slot;
    private int week;
    private String site;
    private String area;
    private String typology;
    private String time_required;
    private String intervention_description;
    private String workspace_notes;
    private String timestamp;
    private String email;

    public Email(int id_maintainer, String name_maintainer, int activity_id, String day, String time_slot, int week, String timestamp) {
        this.id_maintainer = id_maintainer;
        this.name_maintainer = name_maintainer;
        this.day = day;
        this.time_slot  = time_slot;
        this.week = week;
        this.activity_id = activity_id;
        this.timestamp = timestamp;
    }

    private String createContent(){

        PostgresDbConnection dbConnection = new PostgresDbConnection();
        DatabaseView dbToJson = new DatabaseView();

        String activityJson =  dbToJson.getActivityByIdtoJson(this.activity_id, dbConnection);
        JSONArray jsonArray = new JSONArray(activityJson);
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            this.site = (jsonObject.getString("site"));
            this.area = (jsonObject.getString("area"));
            this.typology = (jsonObject.getString("typology"));
            this.time_required = (jsonObject.getString("time"));
            this.intervention_description = (jsonObject.getString("intervention_description"));
            this.workspace_notes = (jsonObject.getString("workspace_notes"));

        }

        String content = "Dear " + name_maintainer + ", You have been assigned the following activity: " + "\n" +
                site + " - " + area + "  " + typology + "\n" + "Time required: " + time_required + "\nIntervention description: " +
                intervention_description + "\nWorkspace notes: " + workspace_notes + "\nweek: " + week + "/ day: " + day + " / time slot: " + time_slot + "\n\n\n" + timestamp;

        return content;
    }

    public void getEmailById(){

        int id_maintainer = this.id_maintainer;

        try {
            PostgresDbConnection dbConnection = new PostgresDbConnection();
            Connection con = dbConnection.connect();
            PreparedStatement stmt = con.prepareStatement("select email from Maintainer where id_maintainer = ?");
            stmt.setInt(1, id_maintainer);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                this.email = rs.getString(1);
            }
        }
        catch (Exception ex){
            System.out.println(ex.getCause());
        }
    }


    public  String sendEmail() {

        getEmailById();
        String content = createContent();
        final String username = "";         // inserire email
        final String password = "";         // inserire password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ciostantino1999@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(this.email));
            message.setSubject("PLANNED ACTIVITY ASSIGNMENT");
            message.setText(content);

            Transport.send(message);

        }

        catch (MessagingException e)
        {
            // throw new RuntimeException(e);
            return " Fail! Username or Password are incorrect ... exiting !";
        }

        return "Done";
    }
}