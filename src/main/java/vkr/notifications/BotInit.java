package vkr.notifications;

import com.google.api.services.gmail.Gmail;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import vkr.notifications.commons.GmailCommons;

import javax.mail.internet.MimeMessage;

public class BotInit {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            botsApi.registerBot(new TelegramNotifications());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        try {
            String jsonString = FileUtils.readFileToString(new File(args[0]),Charset.defaultCharset());
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Gson gson = new Gson();
            Map<String, Object> gmailConfig = gson.fromJson(jsonString, type);
            GmailNotifications gmailNotifications = new GmailNotifications();
            Gmail gmailService = gmailNotifications.init(gmailConfig);
            MimeMessage mimeMessage = gmailNotifications.createEmail((Map<String, Object>)gmailConfig.get(GmailCommons.RECEIPEINTS),gmailConfig.get(GmailCommons.SENDER).toString(),"tihimomoann","adjoanmvoakmdsokvamokdvna;knv;a");
            MimeMessage mimeMessagewithAttachment  = gmailNotifications.createEmailWithAttachment((Map<String, Object>)gmailConfig.get(GmailCommons.RECEIPEINTS),gmailConfig.get(GmailCommons.SENDER).toString(),"test java mail","here's the body. can't u see?",
                    new File("/home/vamsi/Downloads/Get-Your-Life-Back.jpg"));
            gmailNotifications.sendMessage(gmailService,gmailConfig.get(GmailCommons.SENDER).toString(),mimeMessagewithAttachment);

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
