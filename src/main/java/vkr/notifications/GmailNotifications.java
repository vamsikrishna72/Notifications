package vkr.notifications;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.io.output.ByteArrayOutputStream;
import vkr.notifications.commons.GmailCommons;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class GmailNotifications {


    public Gmail init(Map<String,Object> gmailConfig) throws IOException, GeneralSecurityException {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = new JacksonFactory();
            GoogleCredential cr = new GoogleCredential.Builder()
                    .setTransport(httpTransport)
                    .setJsonFactory(jsonFactory)
                    .setClientSecrets(gmailConfig.get(GmailCommons.CLIENT_ID).toString(), gmailConfig.get(GmailCommons.CLIENT_SECRET).toString())
                    .build()
                    .setAccessToken(gmailConfig.get(GmailCommons.ACCESS_TOKEN).toString())
                    .setRefreshToken(gmailConfig.get(GmailCommons.REFRESH_TOKEN).toString());
            Gmail service = new Gmail.Builder( httpTransport,jsonFactory,cr)
                    .setApplicationName(gmailConfig.get(GmailCommons.APPLICATION_NAME).toString()).build();
            return service;
    }

    public MimeMessage createEmail(Map<String,Object> receipents,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        List<String> to = (ArrayList<String>)receipents.get(GmailCommons.TO);
        List<String> cc = (ArrayList<String>)receipents.get(GmailCommons.CC);
        List<String> bcc = (ArrayList<String>)receipents.get(GmailCommons.BCC);
        int i = 0;
        if(to.size() != 0 && to.size()>1){
            Address[] addresses = new Address[to.size()];
            for (String t : to) {
                addresses[i] = new InternetAddress(t);
                i++;
            }
            i=0;
            email.addRecipients(javax.mail.Message.RecipientType.TO,
                    addresses);
        }
        if(to.size() !=0 && to.size() ==1 ){
            email.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress(to.get(0)));
        }

        if(cc.size() != 0 && cc.size()>1){
            Address[] addresses = new Address[cc.size()];
            for (String t : cc) {
                addresses[i] = new InternetAddress(t);
                i++;
            }
            i=0;
            email.addRecipients(javax.mail.Message.RecipientType.CC,
                    addresses);
        }
        if(cc.size() !=0 && cc.size() ==1 ){
            email.addRecipient(javax.mail.Message.RecipientType.CC,
                    new InternetAddress(cc.get(0)));
        }

        if(bcc.size() != 0 && bcc.size()>1){
            Address[] addresses = new Address[bcc.size()];
            for (String t : bcc) {
                addresses[i] = new InternetAddress(t);
                i++;
            }
            i=0;
            email.addRecipients(javax.mail.Message.RecipientType.BCC,
                    addresses);
        }
        if(bcc.size() !=0 && bcc.size() ==1 ){
            email.addRecipient(javax.mail.Message.RecipientType.BCC,
                    new InternetAddress(bcc.get(0)));
        }

        email.setSubject(subject);

        email.setText(bodyText);
        return email;
    }

    public Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    public Message sendMessage(Gmail service,
                                      String userId,
                                      MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        return message;
    }

    public MimeMessage createEmailWithAttachment(Map<String,Object> receipents,
                                                        String from,
                                                        String subject,
                                                        String bodyText,
                                                        File file)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));

        List<String> to = (ArrayList<String>)receipents.get(GmailCommons.TO);
        List<String> cc = (ArrayList<String>)receipents.get(GmailCommons.CC);
        List<String> bcc = (ArrayList<String>)receipents.get(GmailCommons.BCC);
        int i = 0;
        if(to.size() != 0 && to.size()>1){
            Address[] addresses = new Address[to.size()];
            for (String t : to) {
                addresses[i] = new InternetAddress(t);
                i++;
            }
            i=0;
            email.addRecipients(javax.mail.Message.RecipientType.TO,
                    addresses);
        }
        if(to.size() !=0 && to.size() ==1 ){
            email.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress(to.get(0)));
        }

        if(cc.size() != 0 && cc.size()>1){
            Address[] addresses = new Address[cc.size()];
            for (String t : cc) {
                addresses[i] = new InternetAddress(t);
                i++;
            }
            i=0;
            email.addRecipients(javax.mail.Message.RecipientType.CC,
                    addresses);
        }
        if(cc.size() !=0 && cc.size() ==1 ){
            email.addRecipient(javax.mail.Message.RecipientType.CC,
                    new InternetAddress(cc.get(0)));
        }

        if(bcc.size() != 0 && bcc.size()>1){
            Address[] addresses = new Address[bcc.size()];
            for (String t : bcc) {
                addresses[i] = new InternetAddress(t);
                i++;
            }
            i=0;
            email.addRecipients(javax.mail.Message.RecipientType.BCC,
                    addresses);
        }
        if(bcc.size() !=0 && bcc.size() ==1 ){
            email.addRecipient(javax.mail.Message.RecipientType.BCC,
                    new InternetAddress(bcc.get(0)));
        }

        email.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(bodyText, "text/plain");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        mimeBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(file);

        mimeBodyPart.setDataHandler(new DataHandler(source));
        mimeBodyPart.setFileName(file.getName());

        multipart.addBodyPart(mimeBodyPart);
        email.setContent(multipart);

        return email;
    }

}
