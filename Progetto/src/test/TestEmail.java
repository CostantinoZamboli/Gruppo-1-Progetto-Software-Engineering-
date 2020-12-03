package test;

import main.Email;

public class TestEmail {

    public static void main(String[] args){

        Email email = new Email(3, "Pippo",2, "Monday", "8:00-9:00", 49, "02/12/2020");
        System.out.println(email.sendEmail());

    }
}
