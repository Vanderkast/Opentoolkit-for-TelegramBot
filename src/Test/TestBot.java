/**
 * Created by Vanderkast on 11.06.2017.
 */

import Constructors.InlineKeyboardConstructor;
import Constructors.MessageConstructor;
import Constructors.ReplyKeyboardConstructor;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import patterns.KeyboardPattern;

import java.util.ArrayList;

public class TestBot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TestBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public String getBotUsername() {
        return "";
    }//todo: set here your bot's name

    @Override
    public String getBotToken() {
        return "";
    }//todo: set here your bot's token


    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        long chatId;
        String messageText;
        if(message != null) {
            messageText = message.getText();
            chatId = message.getChatId();
        } else {
            messageText = update.getCallbackQuery().getData().toString();
            chatId = update.getCallbackQuery().getFrom().getId();
        }
        try {
            if (messageText.equals("/start")) {

                showTestText(chatId);
                showTestReplyKeyboard(chatId);
            }
            if(messageText.equals("/menu")){
                showTestMenu(chatId);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.out.println("telegramApiException was catched");
        }
    }

    private void showTestText(long chatId) throws TelegramApiException {
        sendMessage(new MessageConstructor().getSendMessage("Hello This is the *test* message", chatId, "Markdown"));
        System.out.println("message showed");
    }

    private void showTestReplyKeyboard(long chatId) throws TelegramApiException {
        ArrayList<String> buttons = new ArrayList<String>();
        buttons.add("/menu");
        buttons.add("/start");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardConstructor().getKeyboard(buttons, KeyboardPattern.TWO_BUTTON_AT_ROW);
        sendMessage(new MessageConstructor().getSendMessage("keyboard show", chatId, null, replyKeyboardMarkup));
    }

    private void showTestMenu(long chatId) throws TelegramApiException{
        ArrayList<String> buttons = new ArrayList<String>();
        buttons.add("/menu");
        buttons.add("/start");

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardConstructor().getKeyboard(buttons, KeyboardPattern.THREE_BUTTON_AT_ROW);
        sendMessage(new MessageConstructor().getSendMessage("this is *inline* menu message", chatId,"Markdown", inlineKeyboard));
    }

}
