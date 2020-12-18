package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityPlannedTest {
    private PlannedActivity plannedActivity;
    private PostgresDbConnection dbConnection;
    private int idActivity;
    private int week;
    private int idMaintainer;
    private String giorno;
    @BeforeEach
    void setUp() {
        dbConnection = new PostgresDbConnection();
        plannedActivity = new PlannedActivity();
        idActivity = 6;
        week = 51;
        idMaintainer = 1;
        giorno = "Wednesday";
    }

    @Order(1)
    @Test
    void getPlannedActivities() {
        String expected = "[{\"week\":\"51\"},{\"id\":\"2\",\"site\":\"Fisciano\",\"area\":\"Molding\", \"typology\":\"Mechanical\",\"time\":\"20\"},{\"id\":\"3\",\"site\":\"Morra\",\"area\":\"Painting\", \"typology\":\"Electronic\",\"time\":\"30\"},{\"id\":\"4\",\"site\":\"Morra\",\"area\":\"Painting\", \"typology\":\"Electronic\",\"time\":\"30\"},{\"id\":\"6\",\"site\":\"Nusco\",\"area\":\"Carpentry\", \"typology\":\"Electronic\",\"time\":\"30\"}]";
        try {
            String actual = plannedActivity.getPlannedActivities(dbConnection);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getSkill() {

        String expected = "[{\"id_skill\":\"1\", \"competenza\":\"PAV certification\"},{\"id_skill\":\"2\", \"competenza\":\"Electrical maintenance\"},{\"id_skill\":\"3\", \"competenza\":\"Knowledge of cable types\"},{\"id_skill\":\"4\", \"competenza\":\"Robot knowledge\"},{\"id_skill\":\"5\", \"competenza\":\"Knowledge of robot workstation\"}]";
        try {
            String actual = plannedActivity.getSkill(dbConnection);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    void getActivityByIdToJson() {

        String expected = "[{\"id\":\"6\",\"site\":\"Nusco\",\"area\":\"Carpentry\", \"typology\":\"Electronic\",\"time\":\"30\",\"week\":\"51\",\"intervention_description\":\"check workstation 10\",\"smp\":\"Ingegneriainformatica_Magistrale_1_Comune_06227.pdf\", \"workspace_notes\":\"The plant is closed from 00 to 4\", \"id_workspace_notes\":\"4\"}]";
        try {
            String actual = plannedActivity.getActivityByIdToJson(idActivity, dbConnection);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        int idActivity2 = 66;
        assertThrows(StringIndexOutOfBoundsException.class, () -> plannedActivity.getActivityByIdToJson(idActivity2, dbConnection));
    }

    @Test
    void getSkillByIdToJson() {

        String expected = "[{\"skills\":\"PAV certification\"},{\"skills\":\"Knowledge of robot workstation\"}]";
        try {
            String actual = plannedActivity.getSkillByIdToJson(idActivity, dbConnection);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        int idActivity2 = 66;
        assertThrows(StringIndexOutOfBoundsException.class, () -> plannedActivity.getSkillByIdToJson(idActivity2, dbConnection));
    }

    @Test
    void updateWorkspaceNotes() {

        int idWorkspaceNotes = 1;
        String description = "prova wn";
        try {
            boolean actual = plannedActivity.updateWorkspaceNotes(idWorkspaceNotes, description, dbConnection);
            assertTrue(actual);
        } catch (Exception e){
            fail(e.getMessage());
        }

        idWorkspaceNotes = 14;
        try {
            boolean actual = plannedActivity.updateWorkspaceNotes(idWorkspaceNotes, description, dbConnection);
            assertFalse(actual);
        } catch (Exception e){
            fail(e.getMessage());
        }

    }

    @Test
    void getMaintainerAvailability() {

        String expected = "[{\"id_maintainer\":\"1\",\"nome\":\"Pippo\",\"week\":\"51\",\"skill_match\":\"2/2\",\"avail_lun\":\"58\",\"avail_mar\":\"58\",\"avail_mer\":\"74\",\"avail_gio\":\"61\",\"avail_ven\":\"63\",\"avail_sab\":\"55\",\"avail_dom\":\"63\"},{\"id_maintainer\":\"2\",\"nome\":\"Pluto\",\"week\":\"51\",\"skill_match\":\"2/2\",\"avail_lun\":\"51\",\"avail_mar\":\"61\",\"avail_mer\":\"49\",\"avail_gio\":\"54\",\"avail_ven\":\"46\",\"avail_sab\":\"52\",\"avail_dom\":\"60\"}]";
        try {
            String actual = plannedActivity.getMaintainerAvailability(week, idActivity, dbConnection);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }


        int week2 = 56;
        assertThrows(StringIndexOutOfBoundsException.class, () -> plannedActivity.getMaintainerAvailability(week2, idActivity, dbConnection));


        expected = "[{\"id_maintainer\":\"1\",\"nome\":\"Pippo\",\"week\":\"51\",\"skill_match\":\"0/0\",\"avail_lun\":\"58\",\"avail_mar\":\"58\",\"avail_mer\":\"74\",\"avail_gio\":\"61\",\"avail_ven\":\"63\",\"avail_sab\":\"55\",\"avail_dom\":\"63\"},{\"id_maintainer\":\"2\",\"nome\":\"Pluto\",\"week\":\"51\",\"skill_match\":\"0/0\",\"avail_lun\":\"51\",\"avail_mar\":\"61\",\"avail_mer\":\"49\",\"avail_gio\":\"54\",\"avail_ven\":\"46\",\"avail_sab\":\"52\",\"avail_dom\":\"60\"}]";
        int idActivity2 = 66;
        try {
            String actual = plannedActivity.getMaintainerAvailability(week, idActivity2, dbConnection);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getTimeSlotAvailability() {

        String expected = "[{\"nome\":\"Pippo\",\"skill_match\":\"2/2\",\"avail_8_9\":\"20\",\"avail_9_10\":\"30\",\"avail_10_11\":\"50\",\"avail_11_12\":\"60\",\"avail_12_13\":\"35\",\"avail_13_14\":\"55\",\"avail_14_15\":\"60\"}]";
        try {
            String actual = plannedActivity.getTimeSlotAvailability(dbConnection, idActivity, idMaintainer, week, giorno);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // id activity sbagliato 66
        int idActivity2 = 66;
        expected = "[{\"nome\":\"Pippo\",\"skill_match\":\"0/0\",\"avail_8_9\":\"20\",\"avail_9_10\":\"30\",\"avail_10_11\":\"50\",\"avail_11_12\":\"60\",\"avail_12_13\":\"35\",\"avail_13_14\":\"55\",\"avail_14_15\":\"60\"}]";
        try {
            String actual = plannedActivity.getTimeSlotAvailability(dbConnection, idActivity2, idMaintainer, week, giorno);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // id maintainer/week/giorno errato indexOutOfBoundException
        int idMaintainer2 = 14;
        assertThrows(IndexOutOfBoundsException.class, () -> plannedActivity.getTimeSlotAvailability(dbConnection, idActivity, idMaintainer2, week, giorno));

        int week2 = 56;
        assertThrows(IndexOutOfBoundsException.class, () -> plannedActivity.getTimeSlotAvailability(dbConnection, idActivity, idMaintainer, week2, giorno));

        String giorno2 = "ppppspss";
        assertThrows(IndexOutOfBoundsException.class, () -> plannedActivity.getTimeSlotAvailability(dbConnection, idActivity, idMaintainer, week, giorno2));
    }
}