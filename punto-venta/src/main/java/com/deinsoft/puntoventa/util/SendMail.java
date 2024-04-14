/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deinsoft.puntoventa.util;

import com.deinsoft.puntoventa.config.AppConfig;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.LoggerFactory;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author EDWARD-PC
 */
@Service
public class SendMail {

    @Autowired
    AppConfig appConfig;
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SendMail.class);

    public static boolean validaCorreo(String correo) {
        boolean result = false;
        if (correo.equals("")) {
            result = true;
            return result;
        }
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(correo);
        result = mather.find();
        return result;
    }

    public String sendEmail(MailBean mail) {

        String result = "0,Wrong";
        Session mailSession = null;
        try {
            Properties props = new Properties();
//            props.put("mail.smtp.host", appConfig.getMailHost());
//            props.put("mail.smtp.port", appConfig.getMailPort());
//            props.put("mail.smtp.auth", appConfig.getMailAuth());
//            
//            props.put("mail.smtp.socketFactory.port", appConfig.getMailPort());
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//            props.put("mail.smtp.starttls.enable","true");
            props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.socketFactory.fallback", "false");
            
//            props.put("mail.smtp.ssl.enable", true); 
//            props.put("mail.smtp.socketFactory.fallback", "true");
//            props.put("mail.smtp.starttls.enable", true);
            final String username = mail.getCorreoElectronicoFrom();
            final String password = mail.getCorreoElectronicoFromPass();
            mailSession = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            

//            mailSession = Session.getDefaultInstance(props, null);
            MimeMessage m = new MimeMessage(mailSession);
            InternetAddress from = new InternetAddress(mail.getCorreoElectronicoFrom());
            InternetAddress[] to = new InternetAddress[]{new InternetAddress(mail.getCorreoElectronicoTo())};
            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, to);
            m.setSubject(mail.getAsunto());
            m.setSentDate(new java.util.Date());
//            m.setContent(mail.getContenido(), "text/html");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart attachmentPart = null;

            MimeBodyPart textPart = null;

            try {

                textPart = new MimeBodyPart();
                textPart.setContent(mail.getContenido(), "text/html; charset=utf-8");
                multipart.addBodyPart(textPart);
                if (mail.getAdjuntos() != null) {
                    for (String object : mail.getAdjuntos()) {
                        attachmentPart = new MimeBodyPart();
                        File f = new File(object);
                        attachmentPart.attachFile(f);
                        multipart.addBodyPart(attachmentPart);

                    }
                }

                // adds inline image attachments
                if (mail.getMapInlineImages() != null && mail.getMapInlineImages().size() > 0) {
                    Set<String> setImageID = mail.getMapInlineImages().keySet();

                    for (String contentId : setImageID) {
                        MimeBodyPart imagePart = new MimeBodyPart();
                        imagePart.setHeader("Content-ID", "<" + contentId + ">");
                        imagePart.setDisposition(MimeBodyPart.INLINE);

                        String imageFilePath = mail.getMapInlineImages().get(contentId);
                        try {
                            imagePart.attachFile(imageFilePath);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        multipart.addBodyPart(imagePart);
                    }
                }

            } catch (IOException e) {

                e.printStackTrace();

            }
            m.setContent(multipart);
            Transport.send(m);
            result = "1,El correo se envio correctamente";
            LOG.info("sendEmail/".concat(mail.toString()).concat(result));
        } catch (NullPointerException e) {
            LOG.error("Error sendEmail/".concat(mail.toString()), e);
            result = "0," +e.getMessage();
        } catch (javax.mail.MessagingException e) {
            LOG.error("Error sendEmail/".concat(mail.toString()), e);
            result = "0," +e.getMessage();
        } catch (Exception e) {
            LOG.error("Error sendEmail/".concat(mail.toString()), e);
            result = "0," +e.getMessage();
        }
        return result;
    }
}
