package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlannedActivity {

    private Connection con;


    /**
     * Ritorna tutte le informazioni relative alle attività pianificate contenute all'interno del database.
     * Viene lanciata un'eccezione quando il ResultSet è vuoto.
     * @param db oggetto di tipo Connection per la connessione al database
     * @return stringa in formato JSON
     * @throws Exception
     */
    public String getPlannedActivities(PostgresDbConnection db) throws Exception {

        this.con = db.connect();
        String format_week = "{\"week\":\"{WEEK}\"}";
        String format = "{\"id\":\"{ID}\",\"site\":\"{SITE}\",\"area\":\"{AREA}\", \"typology\":\"{TYPOLOGY}\",\"time\":\"{TIME}\"}";
        String result = "";

        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM planned_activity where week = ?");
            stmt.setInt(1, week);
            ResultSet rs = stmt.executeQuery();

            format_week = format_week.replace("{WEEK}", Util.utf8Encode(String.valueOf(week))) + ",";

            String row;
            while (rs.next()) {
                row = format.replace("{ID}", Util.utf8Encode(String.valueOf(rs.getInt(1))));
                row = row.replace("{SITE}", Util.utf8Encode(rs.getString(2)));
                row = row.replace("{AREA}", Util.utf8Encode(rs.getString(3)));
                row = row.replace("{TYPOLOGY}", Util.utf8Encode(rs.getString(4)));
                row = row.replace("{TIME}", Util.utf8Encode(String.valueOf(rs.getInt(5))));

                result = result + row + ",";
            }
            con.close();
            result = Util.removeLastChar(result);
        } catch (Exception e) {
            throw  e;
        }
        return "[" + format_week + result + "]";
    }

    /**
     * Ritorna tutte le informazioni relative alle skill contenute all'interno del database.
     * Viene lanciata un'eccezione quando il ResultSet è vuoto.
     * @param db oggetto di tipo Connection per la connessione al database
     * @return string in formato JSON
     * @throws Exception
     */
    public String getSkill(PostgresDbConnection db) throws Exception{

        String format = "{\"id_skill\":\"{ID_SKILL}\", \"competenza\":\"{COMPETENZA}\"}";
        String result = "";
        this.con = db.connect();
        try {
            PreparedStatement stmt = con.prepareStatement("select * from Skill");
            ResultSet rs = stmt.executeQuery();
            String row;

            while (rs.next()){
                row = format.replace("{ID_SKILL}",Util.utf8Encode(String.valueOf(rs.getInt(2))));
                row = row.replace("{COMPETENZA}",Util.utf8Encode(rs.getString(1)));

                result = result + row + ",";
            }
            con.close();
            result = Util.removeLastChar(result);
        }catch(Exception ex){
            throw ex;
        }
        return "[" + result + "]";
    }

    /**
     * Ritorna tutte le informazioni relative ad una singola attività.
     * Viene lanciata un'eccezione quando il ResultSet è vuoto.
     * @param db oggetto di tipo Connection per la connessione al database
     * @param id_activity id dell'attività pianificata di cui prelevare le informazioni
     * @return
     * @throws Exception
     */
    public String getActivityByIdToJson(int id_activity, PostgresDbConnection db) throws Exception {

        this.con = db.connect();
        String format = "{\"id\":\"{ID}\",\"site\":\"{SITE}\",\"area\":\"{AREA}\", \"typology\":\"{TYPOLOGY}\",\"time\":\"{TIME}\",\"week\":\"{WEEK}\",\"intervention_description\":\"{INTERVENTION_DESCRIPTION}\",\"smp\":\"{SMP}\", \"workspace_notes\":\"{WORKSPACE_NOTES}\", \"id_workspace_notes\":\"{ID_WORKSPACE_NOTES}\"}";
        String result = "";
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Planned_Activity where id_activity = ?");
            stmt.setInt(1, id_activity);
            ResultSet rs = stmt.executeQuery();

            String row;
            while (rs.next()) {
                row = format.replace("{ID}", Util.utf8Encode(String.valueOf(rs.getInt(1))));
                row = row.replace("{SITE}", Util.utf8Encode(rs.getString(2)));
                row = row.replace("{AREA}", Util.utf8Encode(rs.getString(3)));
                row = row.replace("{TYPOLOGY}", Util.utf8Encode(rs.getString(4)));
                row = row.replace("{TIME}", Util.utf8Encode(String.valueOf(rs.getInt(5))));
                row = row.replace("{WEEK}", Util.utf8Encode(String.valueOf(rs.getInt(6))));
                row = row.replace("{INTERVENTION_DESCRIPTION}", Util.utf8Encode(rs.getString(7)));
                row = row.replace("{SMP}", Util.utf8Encode(rs.getString(8)));
                row = row.replace("{WORKSPACE_NOTES}", Util.utf8Encode(rs.getString(9)));
                row = row.replace("{ID_WORKSPACE_NOTES}", Util.utf8Encode(String.valueOf(rs.getInt(10))));
                result = result + row + ",";
            }
            con.close();
            result = Util.removeLastChar(result);
        } catch (Exception e) {
            throw e;
        }
        return "[" + result + "]";
    }

    /**
     * Ritorna le skill relative ad una singola attività.
     * Viene lanciata un'eccezione se il ResultSet è vuoto
     * @param id_activity id dell'attività pianificata di cui prelevare le informazioni
     * @param db oggetto di tipo Connection per la connessione al database
     * @return stringa in formato JSON
     * @throws Exception
     */
    public String getSkillByIdToJson(int id_activity, PostgresDbConnection db) throws Exception{
        this.con = db.connect();
        String format = "{\"skills\":\"{SKILLS}\"}";
        String result = "";
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Activity_Selection_Skill where id_activity = ?");
            stmt.setInt(1, id_activity);
            ResultSet rs = stmt.executeQuery();

            String row;

            while (rs.next()) {
                row = format.replace("{SKILLS}", Util.utf8Encode((rs.getString(2))));
                result = result + row + ",";
            }
            con.close();
            result = Util.removeLastChar(result);
        } catch (Exception e) {
            throw e;
        }
        return "[" + result + "]";
    }

    /**
     * Effettua l'update del workspace notes utilizzando il relativo id.
     * Viene lanciata un'eccezione quando si verifica un errore effettuando la query.
     * @param id_workspace_notes id del workspace notes
     * @param description descrizione del workspace notes
     * @param dbConnection oggetto di tipo Connection per la connessione al database
     * @return true se l'update è avvenuto con successo, false altrimenti.
     * @throws Exception
     */
    public boolean updateWorkspaceNotes(int id_workspace_notes, String description, PostgresDbConnection dbConnection) throws Exception{
        this.con = dbConnection.connect();
        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE Workspace_Notes set description=? where id_workspace_notes = ?");
            stmt.setString(1, description);
            stmt.setInt(2, id_workspace_notes);
            int updatedRows = stmt.executeUpdate();
            if(updatedRows == 0){
                return false;
            }
            con.close();
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    /**
     * Ritorna la lista dei maintainer e la relativa disponibilità per una settimana.
     * Utilizza l'id dell'attività selezionata per calcolare il match tra le skill possedute da un maintainer e le skill necessarie per
     * portare a termine l'attività.
     * Viene lanciata un'eccezione quando il ResultSet è vuoto.
     * @param week
     * @param id_activity id dell'attività pianificata
     * @param dbConnection oggetto di tipo Connection per la connessione al database
     * @return stringa in formato JSON
     * @throws Exception
     */
    public String getMaintainerAvailability(int week, int id_activity, PostgresDbConnection dbConnection) throws Exception{

        this.con = dbConnection.connect();
        String format = "{\"id_maintainer\":\"{ID_MAINTAINER}\",\"nome\":\"{NOME}\",\"week\":\"{WEEK}\",\"skill_match\":\"{SKILL_MATCH}\",\"avail_lun\":\"{AVAIL_LUN}\",\"avail_mar\":\"{AVAIL_MAR}\",\"avail_mer\":\"{AVAIL_MER}\",\"avail_gio\":\"{AVAIL_GIO}\",\"avail_ven\":\"{AVAIL_VEN}\",\"avail_sab\":\"{AVAIL_SAB}\",\"avail_dom\":\"{AVAIL_DOM}\"}";
        String result = "";
        int totalSkill = 0;
        ArrayList<Integer> listSkillMatch = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT id_maintainer FROM Prova where week = ?");
            stmt.setInt(1, week);
            ResultSet rs = stmt.executeQuery();

            String row;
            while (rs.next()) {

                int id_maintainer = rs.getInt(1);
                PreparedStatement stmt2 = con.prepareStatement("select count(*) from (select Maintenance_Skill.competencies from Maintenance_Skill where id_procedure = ? intersect select Maintainer_Skill.competencies from Maintainer_Skill where id_maintainer = ? )as skill_match");
                stmt2.setInt(1, id_activity);
                stmt2.setInt(2, id_maintainer);
                ResultSet rs2 = stmt2.executeQuery();

                while (rs2.next()){
                    listSkillMatch.add(rs2.getInt(1));
                }
            }

            PreparedStatement stmt3 = con.prepareStatement("select count(competencies) from Maintenance_Skill where id_procedure = ?");
            stmt3.setInt(1, id_activity);
            ResultSet rs3 = stmt3.executeQuery();

            while(rs3.next()){
                totalSkill = rs3.getInt(1);
            }

            PreparedStatement stmt4 = con.prepareStatement("select * from Prova where week = ?");
            stmt4.setInt(1, week);
            ResultSet rs4 = stmt4.executeQuery();

            while(rs4.next()){
                int i = 0;
                row = format.replace("{ID_MAINTAINER}", Util.utf8Encode(String.valueOf(rs4.getInt(1))));
                row = row.replace("{NOME}", Util.utf8Encode(rs4.getString(2)));
                row = row.replace("{WEEK}", Util.utf8Encode(String.valueOf(rs4.getInt(3))));
                row = row.replace("{SKILL_MATCH}", Util.utf8Encode((listSkillMatch.get(i) + "/" + totalSkill)));
                row = row.replace("{AVAIL_LUN}", Util.utf8Encode(rs4.getString(4)));
                row = row.replace("{AVAIL_MAR}", Util.utf8Encode(rs4.getString(5)));
                row = row.replace("{AVAIL_MER}", Util.utf8Encode(rs4.getString(6)));
                row = row.replace("{AVAIL_GIO}", Util.utf8Encode(rs4.getString(7)));
                row = row.replace("{AVAIL_VEN}", Util.utf8Encode(rs4.getString(8)));
                row = row.replace("{AVAIL_SAB}", Util.utf8Encode(rs4.getString(9)));
                row = row.replace("{AVAIL_DOM}", Util.utf8Encode(rs4.getString(10)));

                result = result + row + ",";
                i++;
            }

            con.close();
            result = Util.removeLastChar(result);
        } catch (Exception e) {
            throw e;
        }
        return "[" + result + "]";
    }


    /**
     * Ritorna la disponibilità temporale relativa al giorno e al maintainer selezionato.
     * Viene lanciata un'eccezione quando il ResultSet è vuoto.
     * @param dbConnection
     * @param id_activity
     * @param id_maintainer id del maintainer selezionato
     * @param week
     * @param giorno giorno selezionato
     * @return stringa in formato JSON
     * @throws Exception
     */
    public String getTimeSlotAvailability (PostgresDbConnection dbConnection, int id_activity, int id_maintainer, int week, String giorno) throws Exception{

        this.con = dbConnection.connect();
        String format = "{\"nome\":\"{NOME}\",\"skill_match\":\"{SKILL_MATCH}\",\"avail_8_9\":\"{AVAIL_8_9}\",\"avail_9_10\":\"{AVAIL_9_10}\",\"avail_10_11\":\"{AVAIL_10_11}\",\"avail_11_12\":\"{AVAIL_11_12}\",\"avail_12_13\":\"{AVAIL_12_13}\",\"avail_13_14\":\"{AVAIL_13_14}\",\"avail_14_15\":\"{AVAIL_14_15}\"}";
        String result = "";
        int totalSkill = 0;
        String nome = "";
        ArrayList<Integer> listSkillMatch = new ArrayList<>();
        try {

            String row;
            PreparedStatement stmt = con.prepareStatement("select count(*) from (select Maintenance_Skill.competencies from Maintenance_Skill where id_procedure = ? intersect select Maintainer_Skill.competencies from Maintainer_Skill where id_maintainer = ? )as skill_match");
            stmt.setInt(1, id_activity);
            stmt.setInt(2, id_maintainer);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                listSkillMatch.add(rs.getInt(1));
            }

            PreparedStatement stmt2 = con.prepareStatement("select count(competencies) from Maintenance_Skill where id_procedure = ?");
            stmt2.setInt(1, id_activity);
            ResultSet rs2 = stmt2.executeQuery();

            while(rs2.next()){
                totalSkill = rs2.getInt(1);
            }

            PreparedStatement stmt3 = con.prepareStatement("select disponibilita  from Maintainer_Availability where id_maintainer = ? and id_week = ? and giorno = ? order by id_availability;");
            stmt3.setInt(1, id_maintainer);
            stmt3.setInt(2, week);
            stmt3.setString(3, giorno);
            ResultSet rs3 = stmt3.executeQuery();

            ArrayList<Integer> availabilityList = new ArrayList<>();
            while(rs3.next()){
                availabilityList.add(rs3.getInt(1));
            }

            PreparedStatement stmt4 = con.prepareStatement("select nome from Maintainer where id_maintainer = ?");
            stmt4.setInt(1, id_maintainer);
            ResultSet rs4 = stmt4.executeQuery();

            while(rs4.next()){
                nome = rs4.getString(1);
            }

            row = format.replace("{SKILL_MATCH}", Util.utf8Encode((listSkillMatch.get(0) + "/" + totalSkill)));
            row = row.replace("{NOME}", Util.utf8Encode(nome));
            row = row.replace("{AVAIL_8_9}", Util.utf8Encode(String.valueOf(availabilityList.get(0))));
            row = row.replace("{AVAIL_9_10}", Util.utf8Encode(String.valueOf(availabilityList.get(1))));
            row = row.replace("{AVAIL_10_11}", Util.utf8Encode(String.valueOf(availabilityList.get(2))));
            row = row.replace("{AVAIL_11_12}", Util.utf8Encode(String.valueOf(availabilityList.get(3))));
            row = row.replace("{AVAIL_12_13}", Util.utf8Encode(String.valueOf(availabilityList.get(4))));
            row = row.replace("{AVAIL_13_14}", Util.utf8Encode(String.valueOf(availabilityList.get(5))));
            row = row.replace("{AVAIL_14_15}", Util.utf8Encode(String.valueOf(availabilityList.get(6))));

            result = result + row + ",";

            con.close();
            result = Util.removeLastChar(result);
        } catch (Exception e) {
            throw e;
        }
        return "[" + result + "]";

    }

    /**
     * Cancella un'attività pianificata utilizzando il relativo id.
     * Viene lanciata un'eccezione quando si verifica un errore effettuando la query.
     * @param dbConnection
     * @param idActivity
     * @return
     * @throws Exception
     */
    public boolean deletePlannedActivityById(PostgresDbConnection dbConnection, int idActivity) throws Exception{

        this.con = dbConnection.connect();

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("delete from Maintenance_Procedures where id_procedure = ?");
            stmt.setInt(1, idActivity);
            int deletedRows = stmt.executeUpdate();
            if(deletedRows == 0){
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
        return true;
    }
}
