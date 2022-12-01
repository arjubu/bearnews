package com.baylor.se.project.bearnews.Service;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSenderService {

    public void zohoSendMail(String to, String body){

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "smtp.zoho.com");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "true");
        properties.setProperty("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.ssl.trust", "smtp.zoho.COM");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.debug.auth", "true");
        properties.setProperty( "mail.pop3.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(properties,new javax.mail.Authenticator()
        {   @Override
        protected PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication("bearfeed@zohomail.com","amisadia07#");
        }
        });
        try
        {   MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("bearfeed@zohomail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO,InternetAddress.parse("rahmanashfakur@gmail.com"));
            message.setSubject("OTP For BearFeed");
            message.setText("Authentication code for your Email is: " +body);
            Transport.send(message);
        }
        catch (MessagingException e)
        {   e.printStackTrace();
        }
    }
}
