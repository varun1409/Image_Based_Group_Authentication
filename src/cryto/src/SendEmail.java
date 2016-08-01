package cryto.src;

import java.util.Date;
import java.util.Properties;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
//for email logic
@SuppressWarnings("unused")
public class SendEmail {
    public static void sendEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message, File attachedFiles) throws AddressException,
            MessagingException {
 try{
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        //msg.setContent(message,"text/html; charset=utf-8");
        //msg.setText(message);
        BodyPart messageBodyPart = new MimeBodyPart();
        // Now set the actual message
        messageBodyPart.setText(message);
        // Create a multipar message
        Multipart multipart = new MimeMultipart();
        // Set text message part
        multipart.addBodyPart(messageBodyPart);        
                MimeBodyPart attachPart = new MimeBodyPart();
                try {
                    attachPart.attachFile(attachedFiles);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } 
                multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
 }
 catch(Exception e)
 {
	 System.out.println(e);
 }
 
    }
 
 public static void sendEmailW(String host, String port,
         final String userName, final String password, String toAddress,
         String subject, String message) throws AddressException,
         MessagingException {
     // sets SMTP server properties
     Properties properties = new Properties();
     properties.put("mail.smtp.host", host);
     properties.put("mail.smtp.port", port);
     properties.put("mail.smtp.auth", "true");
     properties.put("mail.smtp.starttls.enable", "true");
     // creates a new session with an authenticator
     Authenticator auth = new Authenticator() {
         public PasswordAuthentication getPasswordAuthentication() {
             return new PasswordAuthentication(userName, password);
         }
     };
     Session session = Session.getInstance(properties, auth);
     Message msg = new MimeMessage(session);
     msg.setFrom(new InternetAddress(userName));
     InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
     msg.setRecipients(Message.RecipientType.TO, toAddresses);
     msg.setSubject(subject);
     msg.setSentDate(new Date());
     msg.setContent(message,"text/html; charset=utf-8");
   Transport.send(msg);
 }
}