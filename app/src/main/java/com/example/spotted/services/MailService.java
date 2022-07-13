package com.example.spotted.services;

import com.squareup.okhttp.Protocol;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailService {
    final String emailPort = "587";// gmail's smtp port
    final String smtpAuth = "true";
    final String starttls = "true";
    final String smtpHost = "smtp.gmail.com";
    private Protocol protocol;

    private boolean auth;
    private String fromEmail;
    private String fromPassword;
    private String toEmail;
    private String emailSubject;
    private String emailContent;

    private Message message;

    public MailService(String toEmail, String emailSubject, String emailContent)
            throws MessagingException {

        fromEmail = "";
        fromPassword = "";


        this.toEmail = toEmail;
        this.emailSubject = emailSubject;
        this.emailContent = emailContent;

        message = createMessage();
        message.setFrom(new InternetAddress(this.fromEmail));

        setToCcBccRecipients();
        message.setSentDate(new Date());
        message.setSubject(this.emailSubject);
        message.setText(this.emailContent);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    sendMessage();
                }
                catch (MessagingException ex){
                    System.out.println(ex);
                }
            }
        });

        thread.start();

    }

    public void sendMessage() throws MessagingException{
        try{
            Transport.send(message);
        }
        catch(MessagingException ex){
            System.out.println(ex);
            throw ex;
        }
    }
    private Message createMessage(){

        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", emailPort);
        properties.put("mail.smtp.auth", smtpAuth);
        properties.put("mail.smtp.starttls.enable", starttls);


        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, fromPassword);
            }
        };

        Session session = Session.getDefaultInstance(properties, auth);
        return new MimeMessage(session);
    }
    private void setToCcBccRecipients() throws MessagingException{
        if(toEmail != null)
            setMessageRecipients(toEmail, Message.RecipientType.TO);
    }
    private void setMessageRecipients(String recipient,
                                      Message.RecipientType recipientType)
            throws AddressException, MessagingException{
        InternetAddress[] addressArray = buildInternetAddressArray(recipient);
        if((addressArray != null) && (addressArray.length > 0)){
            message.setRecipients(recipientType, addressArray);
        }
    }
    private InternetAddress[] buildInternetAddressArray(String address)
            throws AddressException{
        InternetAddress[] internetAddressArray = null;
        try{
            internetAddressArray = InternetAddress.parse(address);
        }
        catch(AddressException ex){
            throw ex;
        }
        return internetAddressArray;
    }
}
