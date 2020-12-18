package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EwoActivity {

    private Connection con;

    /**
     * Assegna ad una attività una o più skill necessarie per portare a termine l'attività.
     * @param db
     * @param id_activity
     * @param skill_list lista contenente gli id delle skill da inserire
     * @return stringa in formato JSON
     * @throws Exception
     */
    public String insertSkillsEwo(PostgresDbConnection db, int id_activity, List skill_list) throws Exception{


        this.con = db.connect();

        Iterator it = skill_list.iterator();
        while (it.hasNext()) {
            try {
                int id_skill = (int) it.next();
                PreparedStatement stmt = con.prepareStatement("insert into Maintenance_Skill values (?, ?)");
                stmt.setInt(1, id_activity);
                stmt.setInt(2, id_skill);

                stmt.executeUpdate();
            } catch (Exception ex) {
                throw ex;
            }
        }
        try{
            con.close();
        }catch (Exception ex){
            throw ex;
        }
        return "Insert completed!";

    }

    /**
     * Ritorna tutte le skill presenti all'interno del database.
     * @param db
     * @return stringa in formato JSON
     * @throws Exception
     */
    public String getSkillToJson(PostgresDbConnection db) throws Exception {

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
     * Ritorna tutte le informazioni relative ai ticket presenti all'interno del database.
     * @param db
     * @return stringa in formato JSON
     * @throws Exception
     */
    public String getTickets(PostgresDbConnection db) throws Exception {

        this.con = db.connect();
        String format_week_day = "{\"week\":\"{WEEK}\", \"day\":\"{DAY}\"}";
        String format = "{\"ewo_id\":\"{EWO-ID}\",\"site\":\"{SITE}\",\"area\":\"{AREA}\", \"typology\":\"{TYPOLOGY}\",\"stato\":\"{STATO}\"}";
        String result = "";

        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Ewo_Activity where giorno=? and week=?");
            stmt.setString(1, day);
            stmt.setInt(2, week);
            ResultSet rs = stmt.executeQuery();

            format_week_day = format_week_day.replace("{WEEK}", Util.utf8Encode(String.valueOf(week)));
            format_week_day = format_week_day.replace("{DAY}", Util.utf8Encode(day)) + ",";;

            String row;
            while (rs.next()) {
                row = format.replace("{EWO-ID}", Util.utf8Encode(String.valueOf(rs.getInt(1))));
                row = row.replace("{SITE}", Util.utf8Encode(rs.getString(2)));
                row = row.replace("{AREA}", Util.utf8Encode(rs.getString(3)));
                row = row.replace("{TYPOLOGY}", Util.utf8Encode(rs.getString(4)));
                row = row.replace("{STATO}", Util.utf8Encode(rs.getString(12)));

                result = result + row + ",";
            }
            con.close();
            result = Util.removeLastChar(result);
        } catch (Exception e) {
            throw e;
        }
        return "[" + format_week_day + result + "]";
    }

    /**
     * Assegna la descrizione dell'intervento e il tempo richiesto ad una attività.
     * @param dbConnection
     * @param id_activity
     * @param intervention_description
     * @param time
     * @return true se l'update ha successo, false altrimenti
     * @throws Exception
     */
    public boolean insertEwoActivity(PostgresDbConnection dbConnection, int id_activity, String intervention_description, int time) throws Exception {

        this.con = dbConnection.connect();
        try{

            PreparedStatement stmt = con.prepareStatement("update Maintenance_Procedures set intervention_description=? ,intervention_time=? where id_procedure = ?");
            stmt.setString(1, intervention_description);
            stmt.setInt(2, time);
            stmt.setInt(3, id_activity);

            int updatedRows = stmt.executeUpdate();
            if(updatedRows == 0){
                return false;
            }
            con.close();
        } catch (Exception ex){
            throw ex;
        }
        return true;
    }

    /**
     * Ritorna le informazioni relative ad una singola attività non pianificata, utilizzando il relativo id.
     * @param id_activity
     * @param db
     * @return
     * @throws Exception
     */
    public String getEwoByIdToJson(int id_activity, PostgresDbConnection db) throws Exception{

        this.con = db.connect();
        String format = "{\"id\":\"{ID}\",\"site\":\"{SITE}\",\"area\":\"{AREA}\", \"typology\":\"{TYPOLOGY}\",\"time\":\"{TIME}\",\"week\":\"{WEEK}\",\"day\":\"{DAY}\",\"intervention_description\":\"{INTERVENTION_DESCRIPTION}\", \"workspace_notes\":\"{WORKSPACE_NOTES}\", \"id_workspace_notes\":\"{ID_WORKSPACE_NOTES}\"}";
        String result = "";
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Ewo_Activity where id_activity = ?");
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
                row = row.replace("{WORKSPACE_NOTES}", Util.utf8Encode(rs.getString(8)));
                row = row.replace("{ID_WORKSPACE_NOTES}", Util.utf8Encode(String.valueOf(rs.getInt(9))));
                row = row.replace("{DAY}", Util.utf8Encode(rs.getString(10)));
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
     * Ritorna le informazioni relative ad un singolo ticket.
     * @param db
     * @param id_activity
     * @return
     * @throws Exception
     */
    public String getTicketByIdToJson(PostgresDbConnection db, int id_activity) throws Exception {

        this.con = db.connect();
        String format_week_day = "{\"week\":\"{WEEK}\", \"day\":\"{DAY}\"}";
        String format = "{\"ewo_id\":\"{EWO-ID}\",\"site\":\"{SITE}\",\"area\":\"{AREA}\",\"id_site\":\"{ID_SITE}\", \"typology\":\"{TYPOLOGY}\", \"id_typology\":\"{ID_TYPOLOGY}\", \"workspace_notes\":\"{WORKSPACE_NOTES}\"}";
        String result = "";

        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Ewo_activity where id_activity = ? and week=? and giorno=?");
            stmt.setInt(1, id_activity);
            stmt.setInt(2, week);
            stmt.setString(3, day);
            ResultSet rs = stmt.executeQuery();

            format_week_day = format_week_day.replace("{WEEK}", Util.utf8Encode(String.valueOf(week)));
            format_week_day = format_week_day.replace("{DAY}", Util.utf8Encode(day)) + ",";;

            String row;
            while (rs.next()) {
                row = format.replace("{EWO-ID}", Util.utf8Encode(String.valueOf(rs.getInt(1))));
                row = row.replace("{SITE}", Util.utf8Encode(rs.getString(2)));
                row = row.replace("{AREA}", Util.utf8Encode(rs.getString(3)));
                row = row.replace("{TYPOLOGY}", Util.utf8Encode(rs.getString(4)));
                row = row.replace("{ID_SITE}", Util.utf8Encode(String.valueOf(rs.getInt(9))));
                row = row.replace("{ID_TYPOLOGY}", Util.utf8Encode(String.valueOf(rs.getInt(10))));
                row = row.replace("{WORKSPACE_NOTES}", Util.utf8Encode(rs.getString(8)));

                result = result + row + ",";
            }
            con.close();
            result = Util.removeLastChar(result);
        } catch (Exception ex){
            throw ex;
        }
        return "[" + format_week_day + result + "]";

    }

    /**
     * Ritorna la disponibilità temporale relativa a tutti i maintainer.
     * @param dbConnection
     * @param id_activity
     * @param week
     * @param giorno
     * @return
     * @throws Exception
     */
    public String getTimeSlotAvailabilityEwo(PostgresDbConnection dbConnection, int id_activity, int week, String giorno) throws Exception {

        this.con = dbConnection.connect();
        String format = "{\"id_maintainer\":\"{ID_MAINTAINER}\",\"nome\":\"{NOME}\",\"skill_match\":\"{SKILL_MATCH}\",\"avail_8_9\":\"{AVAIL_8_9}\",\"avail_9_10\":\"{AVAIL_9_10}\",\"avail_10_11\":\"{AVAIL_10_11}\",\"avail_11_12\":\"{AVAIL_11_12}\",\"avail_12_13\":\"{AVAIL_12_13}\",\"avail_13_14\":\"{AVAIL_13_14}\",\"avail_14_15\":\"{AVAIL_14_15}\"}";
        String result = "";
        int totalSkill = 0;
        String nome = "";
        ArrayList<Integer> listSkillMatch = new ArrayList<>();
        try {
            /*select distinct id_maintainer  from Maintainer_Availability where id_week = 50 and giorno = 'Wednesday';
             */

            PreparedStatement statement = con.prepareStatement("select distinct id_maintainer from Maintainer_Availability where id_week = ? and giorno = ?");
            statement.setInt(1, week);
            statement.setString(2, giorno);
            ResultSet resultSet = statement.executeQuery();
            int i = 0;

            while (resultSet.next()) {

                int id_maintainer = resultSet.getInt(1);
                String row;
                PreparedStatement stmt = con.prepareStatement("select count(*) from (select Maintenance_Skill.competencies from Maintenance_Skill where id_procedure = ? intersect select Maintainer_Skill.competencies from Maintainer_Skill where id_maintainer = ? )as skill_match");
                stmt.setInt(1, id_activity);
                stmt.setInt(2, id_maintainer);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    listSkillMatch.add(rs.getInt(1));
                }

                PreparedStatement stmt2 = con.prepareStatement("select count(competencies) from Maintenance_Skill where id_procedure = ?");
                stmt2.setInt(1, id_activity);
                ResultSet rs2 = stmt2.executeQuery();

                while (rs2.next()) {
                    totalSkill = rs2.getInt(1);
                }

                PreparedStatement stmt3 = con.prepareStatement("select disponibilita  from Maintainer_Availability where id_maintainer = ? and id_week = ? and giorno = ? order by id_availability;");
                stmt3.setInt(1, id_maintainer);
                stmt3.setInt(2, week);
                stmt3.setString(3, giorno);

                ResultSet rs3 = stmt3.executeQuery();

                ArrayList<Integer> availabilityList = new ArrayList<>();
                while (rs3.next()) {
                    availabilityList.add(rs3.getInt(1));
                }

                PreparedStatement stmt4 = con.prepareStatement("select nome from Maintainer where id_maintainer = ?");
                stmt4.setInt(1, id_maintainer);
                ResultSet rs4 = stmt4.executeQuery();

                while (rs4.next()) {
                    nome = rs4.getString(1);
                }

                row = format.replace("{SKILL_MATCH}", Util.utf8Encode((listSkillMatch.get(i) + "/" + totalSkill)));
                row = row.replace("{ID_MAINTAINER}", Util.utf8Encode(String.valueOf(id_maintainer)));
                row = row.replace("{NOME}", Util.utf8Encode(nome));
                row = row.replace("{AVAIL_8_9}", Util.utf8Encode(String.valueOf(availabilityList.get(0))));
                row = row.replace("{AVAIL_9_10}", Util.utf8Encode(String.valueOf(availabilityList.get(1))));
                row = row.replace("{AVAIL_10_11}", Util.utf8Encode(String.valueOf(availabilityList.get(2))));
                row = row.replace("{AVAIL_11_12}", Util.utf8Encode(String.valueOf(availabilityList.get(3))));
                row = row.replace("{AVAIL_12_13}", Util.utf8Encode(String.valueOf(availabilityList.get(4))));
                row = row.replace("{AVAIL_13_14}", Util.utf8Encode(String.valueOf(availabilityList.get(5))));
                row = row.replace("{AVAIL_14_15}", Util.utf8Encode(String.valueOf(availabilityList.get(6))));

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
     * Effettua l'update dello stato relativo ad un ticket, utilizzando il relativo id.
     * @param id_activity
     * @param dbConnection
     * @return true se l'update ha successo, false altrimenti
     * @throws Exception
     */
    public boolean updateState(int id_activity, PostgresDbConnection dbConnection) throws Exception{

        con = dbConnection.connect();
        try {
            PreparedStatement stmt = con.prepareStatement("update Maintenance_Procedures set stato=? where id_procedure=?");
            stmt.setString(1, "Assigned");
            stmt.setInt(2, id_activity);
            int updatedRows = stmt.executeUpdate();
            if(updatedRows == 0){
                return false;
            }
            con.close();
        } catch (Exception ex){
            throw ex;
        }
        return true;
    }


}
