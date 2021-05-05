package com.codecool.shop.util;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class JavaMailUtil {

    public static void SendEmail(String targetEmail, String password)
    {

        try{

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

            Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("codecoolshopmasters@gmail.com", password);
                }
            });

            mailSession.setDebug(true); // Enable the debug mode

            Message message = new MimeMessage( mailSession );

            message.setFrom( new InternetAddress( "codecoolshopmasters@gmail.com" ) );
            message.setRecipients( Message.RecipientType.TO,InternetAddress.parse(targetEmail) );
            message.setSentDate( new Date());
            message.setSubject( "Registration confirmation" );

            message.setText( "" );
            String htmlCode = "<h1> CONFIRMATION </h1> <h2> Your registration was successful at CodeCool Shop. <br>Enjoy " +
                    "the shopping!\n</h2><h3>Team of ShopMasters </h3>";
            message.setContent(htmlCode, "text/html");

            Transport.send( message );

        } catch (Exception ex) {
        Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
}
