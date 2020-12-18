package main;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ejb.AfterCompletion;

import static org.junit.jupiter.api.Assertions.*;


class FacadeEmailTest {

    private String userName;
    private String password;
    private String jsonInfo;
    private int idActivity;
    private FacadeEmail facadeEmail;

    @BeforeEach
    void setUp() {
        userName = "ciostantino1999@gmail.com";
        password = "costantino.Zamboli99";
        jsonInfo = "[{\"id_maintainer\":\"2\",\"week\":\"51\",\"day\":\"Wednesday\",\"time1\":\"11:00-12:00\",\"time2\":\"12:00-13:00\",\"name\":\"Pluto\",\"timestamp\":\"2020-12-16T11:35:03.471Z\"}]";
        idActivity = 30;
        facadeEmail = new FacadeEmail(userName, password, idActivity, jsonInfo);
    }

    @Test
    void sendEmailEwo() {
        String actual = facadeEmail.sendEmailEwo();
        String expected = "Email inviata con successo!";
        assertEquals(expected, actual);

        // id_activity = 24 è gia assigned. getActivityByIdToJson fallisce.
        actual = facadeEmail.sendEmailEwo();
        expected = "Failed to send Email";
        assertEquals(expected, actual);
    }

    @Test
    void sendEmailPlanned() {

        idActivity = 6;
        jsonInfo = "[{\"id_maintainer\":\"2\",\"activity_id\":\"6\",\"week\":\"51\",\"day\":\"Wednesday\",\"time\":\"11:00-12:00\",\"name\":\"Pluto\",\"date\":\"2020-12-16T12:28:56.998Z\"}]";
        facadeEmail = new FacadeEmail(userName, password, idActivity, jsonInfo);
        String actual = facadeEmail.sendEmailPlanned();
        String expected = "Email inviata con successo!";
        assertEquals(expected, actual);

        /*
        id_activity =  è gia assigned. getActivityByIdToJson fallisce.
        actual = facadeEmail.sendEmailPlanned();
        expected = "Failed to send Email";
        assertEquals(expected, actual);
        */
    }
}