package test;

import main.Email;
import main.FacadeEmail;

public class TestEmail {

    public static void main(String[] args){

        String jsonInfo = "[{\"id_maintainer\":\"2\",\"week\":\"51\",\"day\":\"Tuesday\",\"time1\":\"11:00-12:00\",\"time2\":\"12:00-13:00\",\"name\":\"Pluto\",\"timestamp\":\"2020-12-15T18:21:32.323Z\"}]";
        String username = "ciostantino1999@gmail.com";
        String password = "costantino.Zamboli99";
        int idActivity = 24;
        FacadeEmail facade = new FacadeEmail(username, password, idActivity, jsonInfo);
        System.out.println(facade.sendEmailEwo());
    }
}
