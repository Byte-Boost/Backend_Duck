package net.byteboost.duck.utils;

import net.byteboost.duck.keys.EmailKeys;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailUtils {
    public static void sendMail(String recipient) throws Exception {
    System.out.println("Preparing to send email");
    Properties properties = new Properties();

    //Enable authentication
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");

    //Your gmail address password
    String myAccountEmail = EmailKeys.email;
    String password = EmailKeys.password;

    //Create a session with account credentials
    Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(myAccountEmail, password);
        }
    });

    //Prepare email message
    Message message = prepareMessage(session, myAccountEmail, recipient);

    //Send mail
    Transport.send(message);
    System.out.println("Message sent successfully");
}

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Assunto do email");
            String htmlCode = "<h1> titulo </h1> <p> o código é <b>123523</b> </p>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(EmailUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
