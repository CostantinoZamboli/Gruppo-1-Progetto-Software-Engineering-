package main;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {

    private String email;
    private final String userName;
    private final String password;
    private String title;

    public Email(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRecipient(String email) {
        this.email = email;
    }

    public String getRecipient() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }


    /**
     * Invia una e-mail utilizzando come from: username e come recipient email.
     * Viene lanciata un'eccezione se il recipient address e null, oppure se username o password non sono corretti.
     * @param content
     * @return
     */
    public String sendEmail(String content){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(this.email));
            message.setSubject(this.title);
            message.setText(content);

            Transport.send(message);

        }
        catch (NullPointerException e){
            return "Failed to send email. Recipient address is null";
        }
        catch (MessagingException e)
        {
            return "Failed to send email. Username or password is incorrect";
        }

        return "Email inviata con successo!";
    }

    }
