package vkr.notifications;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TelegramNotifications extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.hasMessage() && update.getMessage().hasText()) {

                String receivedMessage = update.getMessage().getText();
                long chat_id = update.getMessage().getChatId();
                if (receivedMessage.equals("Command")) {
                    SendMessage message = new SendMessage() // Create a message object object
                            .setChatId(chat_id)
                            .setText("received "+receivedMessage);
                    try {
                        execute(message); // Sending our message object to user
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }  else if (receivedMessage.toLowerCase().equals("displaykeyboard")) {
                    SendMessage message = new SendMessage() // Create a message object object
                            .setChatId(chat_id)
                            .setText("Here is your keyboard");
                    // Create ReplyKeyboardMarkup object
                    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                    // Create the keyboard (list of keyboard rows)
                    List<KeyboardRow> keyboard = new ArrayList<>();
                    // Create a keyboard row
                    KeyboardRow row = new KeyboardRow();
                    // Set each button, you can also use KeyboardButton objects if you need something else than text
                    row.add("Command 1");
                    row.add("Command 2");
                    // Add the first row to the keyboard
                    keyboard.add(row);
                    // Create another keyboard row
                    row = new KeyboardRow();
                    row.add("Command 3");
                    row.add("hidekeyboard");
                    keyboard.add(row);
                    // Set the keyboard to the markup
                    keyboardMarkup.setKeyboard(keyboard);
                    // Add it to the message
                    message.setReplyMarkup(keyboardMarkup);
                    try {
                        execute(message); // Sending our message object to user
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                else if (receivedMessage.equals("Command 1")) {
                    SendMessage msg = new SendMessage()
                            .setChatId(chat_id)
                            .setText("Command 2 executed");
                    try {
                        execute(msg); // Call method to send the photo
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                }
                else if (receivedMessage.equals("Command 2")) {
                    SendMessage msg = new SendMessage()
                            .setChatId(chat_id)
                            .setText("Command 2 executed");
                    try {
                        execute(msg); // Call method to send the photo
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }else if (receivedMessage.equals("Command 3")) {
                    SendMessage msg = new SendMessage()
                            .setChatId(chat_id)
                            .setText("Command 3 executed");
                    try {
                        execute(msg); // Call method to send the photo
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                else if (receivedMessage.equals("Command 4")) {
                    SendMessage msg = new SendMessage()
                            .setChatId(chat_id)
                            .setText("Command 4 executed");
                    try {
                        execute(msg); // Call method to send the photo
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                else if (receivedMessage.equals("Command 5")) {
                    SendMessage msg = new SendMessage()
                            .setChatId(chat_id)
                            .setText("Command 5 executed");
                    try {
                        execute(msg); // Call method to send the photo
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                  else if (receivedMessage.toLowerCase().equals("hidekeyboard")) {
                    SendMessage msg = new SendMessage()
                            .setChatId(chat_id)
                            .setText("Keyboard hidden");
                    ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
                    msg.setReplyMarkup(keyboardMarkup);
                    try {
                        execute(msg); // Call method to send the photo
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    SendMessage message = new SendMessage() // Create a message object object
                            .setChatId(chat_id)
                            .setText("Unknown command");
                    try {
                        execute(message); // Sending our message object to user
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            } 
        }
    }


    public String getBotUsername() {
        return "testVamsi_bot";
    }

    public String getBotToken() {
        return "755906017:AAF42B9qZ5UqDFN9ThIpqQBw3Si4SV5iIgY";
    }

}
