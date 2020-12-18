package main;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
class EwoActivityTest {

    private int idActivity;
    private int week;
    private String day;
    private PostgresDbConnection dbConnection;
    private EwoActivity ewoActivity;

    @BeforeEach
    void setUp() {
        dbConnection = new PostgresDbConnection();
        ewoActivity = new EwoActivity();
        idActivity = 30;
        week = 51;
        day = "Friday";
    }

    @Test
    @Order(4)
    void insertSkillsEwo() {

        List<Integer> skillList = new ArrayList<>();
        skillList.add(1);
        skillList.add(2);
        skillList.add(3);

        try {
            String actual = ewoActivity.insertSkillsEwo(dbConnection, idActivity, skillList);
            assertEquals("Insert completed!", actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        int id_activity2 = 220;   // id_activity non presente nel db
        List skillList2 = new ArrayList<>();
        skillList2.add(1);
        skillList2.add(2);
        skillList2.add(3);
        skillList2.add(4);

        // il test ha successo se il metodo lancia l'eccezione.
        assertThrows(Exception.class, () -> ewoActivity.insertSkillsEwo(dbConnection, id_activity2, skillList2));

        List skillList3 = new ArrayList<>();
        skillList3.add(1);
        skillList3.add(2);
        skillList3.add(3);
        skillList3.add(4);
        skillList3.add(15);     // skill non presente nel db
        // il test ha successo se il metodo lancia l'eccezione.
        assertThrows(Exception.class, () -> ewoActivity.insertSkillsEwo(dbConnection, idActivity, skillList3));


    }

    @Test
    @Order(3)
    void getSkillToJson() {

        String expected = "[{\"id_skill\":\"1\", \"competenza\":\"PAV certification\"},{\"id_skill\":\"2\", \"competenza\":\"Electrical maintenance\"},{\"id_skill\":\"3\", \"competenza\":\"Knowledge of cable types\"},{\"id_skill\":\"4\", \"competenza\":\"Robot knowledge\"},{\"id_skill\":\"5\", \"competenza\":\"Knowledge of robot workstation\"}]";

        String actual = null;
        try {
            actual = ewoActivity.getSkillToJson(dbConnection);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(1)
    void getTickets() {

        String expected = "[{\"week\":\"51\", \"day\":\"Friday\"},{\"ewo_id\":\"27\",\"site\":\"Morra\",\"area\":\"Painting\", \"typology\":\"Mechanical\",\"stato\":\"Not Assigned\"},{\"ewo_id\":\"28\",\"site\":\"Morra\",\"area\":\"Molding\", \"typology\":\"Electronic\",\"stato\":\"Not Assigned\"},{\"ewo_id\":\"29\",\"site\":\"Morra\",\"area\":\"Molding\", \"typology\":\"Hydraulic\",\"stato\":\"Not Assigned\"},{\"ewo_id\":\"30\",\"site\":\"Fisciano\",\"area\":\"Painting\", \"typology\":\"Electrical\",\"stato\":\"Not Assigned\"},{\"ewo_id\":\"31\",\"site\":\"Nusco\",\"area\":\"Carpentry\", \"typology\":\"Electronic\",\"stato\":\"Not Assigned\"},{\"ewo_id\":\"32\",\"site\":\"Morra\",\"area\":\"Painting\", \"typology\":\"Hydraulic\",\"stato\":\"Not Assigned\"}]";

        String actual = null;
        try {
            actual = ewoActivity.getTickets(dbConnection);
            assertEquals(expected, actual);
        }
        catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    @Order(5)
    void insertEwoActivity() {

        String intervention_description = "prova";
        int time = 60;

        try {
            boolean actual = ewoActivity.insertEwoActivity(dbConnection, idActivity, intervention_description, time);
            assertTrue(actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        int idActivity2 = 200;
        try{
            boolean actual = ewoActivity.insertEwoActivity(dbConnection, idActivity2, intervention_description, time);
            assertFalse(actual);
        } catch (Exception e){
            fail(e.getMessage());
        }

    }

    @Test
    @Order(6)
    void getEwoByIdToJson() {

        String expected = "[{\"id\":\"30\",\"site\":\"Fisciano\",\"area\":\"Painting\", \"typology\":\"Electrical\",\"time\":\"60\",\"week\":\"51\",\"day\":\"1\",\"intervention_description\":\"prova\", \"workspace_notes\":\"The plant is closed from 00 to 3\", \"id_workspace_notes\":\"2\"}]";

        String actual = null;
        try {
            actual = ewoActivity.getEwoByIdToJson(idActivity, dbConnection);
            assertEquals(expected, actual);
        }
        catch (Exception e){
            fail(e.getMessage());
        }

        // se l'id_activity non è corretto il result set è vuoto. L'eccezione deriva dal removeLastChar ed è StringIndexOutOfBoundException
        int id_activity2 = 200;
        assertThrows(StringIndexOutOfBoundsException.class, () -> ewoActivity.getEwoByIdToJson(id_activity2, dbConnection));

    }

    @Test
    @Order(2)
    void getTicketByIdToJson() {

        String expected = "[{\"week\":\"51\", \"day\":\"Friday\"},{\"ewo_id\":\"30\",\"site\":\"Fisciano\",\"area\":\"Painting\",\"id_site\":\"2\", \"typology\":\"Electrical\", \"id_typology\":\"1\", \"workspace_notes\":\"The plant is closed from 00 to 3\"}]";

        try {
            String actual = ewoActivity.getTicketByIdToJson(dbConnection, idActivity);
            assertEquals(expected, actual);
        } catch (Exception ex){
            fail(ex.getMessage());
        }

        // se l'id_activity non è corretto il result set è vuoto. L'eccezione deriva dal removeLastChar ed è StringIndexOutOfBoundException
        int idActivity2 = 200;
        assertThrows(StringIndexOutOfBoundsException.class, () -> ewoActivity.getTicketByIdToJson(dbConnection, idActivity2));
    }

    @Test
    @Order(7)
    void getTimeSlotAvailabilityEwo() {

        String expected = "[{\"id_maintainer\":\"1\",\"nome\":\"Pippo\",\"skill_match\":\"3/3\",\"avail_8_9\":\"20\",\"avail_9_10\":\"50\",\"avail_10_11\":\"60\",\"avail_11_12\":\"60\",\"avail_12_13\":\"35\",\"avail_13_14\":\"25\",\"avail_14_15\":\"15\"},{\"id_maintainer\":\"2\",\"nome\":\"Pluto\",\"skill_match\":\"2/3\",\"avail_8_9\":\"30\",\"avail_9_10\":\"50\",\"avail_10_11\":\"15\",\"avail_11_12\":\"20\",\"avail_12_13\":\"25\",\"avail_13_14\":\"30\",\"avail_14_15\":\"25\"}]";
        try {
            String actual = ewoActivity.getTimeSlotAvailabilityEwo(dbConnection, idActivity, week, day);
            assertEquals(expected, actual);
        } catch (Exception ex){
            fail(ex.getMessage());
        }

        // se l'id_activity non è corretto skill match sarà 0/0 (intersect con insieme vuoto).
        int idActivity2 = 200;
        expected = "[{\"id_maintainer\":\"1\",\"nome\":\"Pippo\",\"skill_match\":\"0/0\",\"avail_8_9\":\"20\",\"avail_9_10\":\"50\",\"avail_10_11\":\"60\",\"avail_11_12\":\"60\",\"avail_12_13\":\"35\",\"avail_13_14\":\"25\",\"avail_14_15\":\"15\"},{\"id_maintainer\":\"2\",\"nome\":\"Pluto\",\"skill_match\":\"0/0\",\"avail_8_9\":\"30\",\"avail_9_10\":\"50\",\"avail_10_11\":\"15\",\"avail_11_12\":\"20\",\"avail_12_13\":\"25\",\"avail_13_14\":\"30\",\"avail_14_15\":\"25\"}]";
        try {
            String actual = ewoActivity.getTimeSlotAvailabilityEwo(dbConnection, idActivity2, week, day);
            assertEquals(expected, actual);
        } catch (Exception ex){
            fail(ex.getMessage());
        }

        // se la week non è presente le db, il result set è vuoto. RemoveLastChar sul result set vuoto provoca l'eccezione
        int week2=56;
        assertThrows(StringIndexOutOfBoundsException.class, () -> ewoActivity.getTimeSlotAvailabilityEwo(dbConnection, idActivity, week2, day));

        // se il day è inesistente, il result set è vuoto. RemoveLastChar sul result set vuoto provoca l'eccezione
        String day2 = "ggggggg";
        assertThrows(StringIndexOutOfBoundsException.class, () -> ewoActivity.getTimeSlotAvailabilityEwo(dbConnection, idActivity, week, day2));
    }

    @Test
    @Order(8)
    @Disabled
    void updateState() {

        try{
            boolean actual = ewoActivity.updateState(idActivity, dbConnection);
            assertTrue(actual);
        } catch (Exception ex){
            fail(ex.getMessage());
        }

        int idActivity2 = 200;

        try{
            boolean actual = ewoActivity.updateState(idActivity2, dbConnection);
            assertFalse(actual);
        } catch (Exception ex){
            fail(ex.getMessage());
        }

    }


}