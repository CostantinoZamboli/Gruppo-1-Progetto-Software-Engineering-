package main;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
class EmailTest {

    private Email email;
    private String username;
    private String password;
    private String recipient;

    @BeforeEach
    void setUp() {
         username = "ciostantino1999@gmail.com";
         password = "costantino.Zamboli99";
         recipient = "c.villani17@studenti.unisa.it";
         email = new Email(username, password);
    }

    @Order(1)
    @Test
    void setEmail() {

        email.setRecipient(recipient);
        assertEquals(recipient, email.getRecipient());
    }

    @Order(2)
    @Disabled
    @Test
    void sendEmail() {

        String content = "test email";
        email.setRecipient(recipient);
        String actual = email.sendEmail(content);
        String expected = "Email inviata con successo!";
        assertEquals(expected, actual);

        //password errata
        Email email1 = new Email(username, "ppppp");
        email1.setRecipient(recipient);
        expected = " Fail! Username or Password are incorrect ... exiting !";
        actual = email1.sendEmail(content);
        assertEquals(expected, actual);

        // username errato
        Email email2 = new Email("usernameerrato", password);
        email2.setRecipient(recipient);
        expected = " Fail! Username or Password are incorrect ... exiting !";
        actual = email2.sendEmail(content);
        assertEquals(expected, actual);
    }
}