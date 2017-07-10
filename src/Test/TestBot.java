/**
 * Created by Vanderkast on 11.06.2017.
 */

import Charts.ChartConstructor;
import Charts.PieChartConstructor;
import Constructors.InlineKeyboardConstructor;
import Constructors.MessageConstructor;
import Constructors.ReplyKeyboardConstructor;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import patterns.KeyboardPattern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        return "legendarytest_bot";
    }//todo: set here your bot's name

    @Override
    public String getBotToken() {
        return "366056739:AAGSM6ZZ1EgApi19NJXt4G4XVcYI7ADznPk";
    }//todo: set here your bot's token


    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        long chatId;
        String messageText;
        if (message != null) {
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
            if (messageText.equals("/menu")) {
                showTestMenu(chatId);
            }
            if (messageText.equals("/showTestChart")) {
                sendPhotoOfChart(chatId);
            }
            if(messageText.equals("/pieChart")){
                sendPieChart(chatId);
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
        buttons.add("/нодгшже");
        buttons.add("/ваповап");
        buttons.add("/ековапо");

        ReplyKeyboardMarkup replyKeyboardMarkup =  ReplyKeyboardConstructor.getKeyboard(buttons, KeyboardPattern.TWO_BUTTON_AT_ROW);
        replyKeyboardMarkup = ReplyKeyboardConstructor.addContactRequestInKeyboard(replyKeyboardMarkup, "/ваповап");

        sendMessage(new MessageConstructor().getSendMessage("keyboard show", chatId, null, replyKeyboardMarkup));
    }

    private void showTestMenu(long chatId) throws TelegramApiException {
        ArrayList<String> buttons = new ArrayList<String>();
        buttons.add("/menu");
        buttons.add("/start");
        buttons.add("/showTestChart");
        buttons.add("/pieChart");

        InlineKeyboardMarkup inlineKeyboard = InlineKeyboardConstructor.getKeyboard(buttons);
        sendMessage(new MessageConstructor().getSendMessage("this is *inline* menu message", chatId, "Markdown", inlineKeyboard));
    }

    private void sendPhotoOfChart(long chatId) throws TelegramApiException {
        ArrayList<Integer> xData = new ArrayList<Integer>();
        xData.add(1);
        xData.add(2);
        xData.add(3);

        ArrayList<Integer> yData = new ArrayList<Integer>();
        yData.add(3);
        yData.add(4);
        yData.add(7);
        ChartConstructor chart = new ChartConstructor("E:/Unity/TB_OpenToolkit", "X", xData, "Y", yData, "test chart", 500, 500);


        FileInputStream fileInputStream = null;
        try {
            File file = new File(chart.getChartConstructed());
            fileInputStream = new FileInputStream(file + ".jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendPhoto(new SendPhoto()
                .setChatId(chatId)
                .setNewPhoto("photo", fileInputStream));
    }

    private void sendPieChart(long chatId) throws  TelegramApiException{
        ArrayList<Integer> values = new ArrayList<Integer>();
        values.add(15);
        values.add(7);
        values.add(9);

        ArrayList<String> series = new ArrayList<String>();
        series.add("hello");
        series.add("this");
        series.add("is pieChart");

        PieChartConstructor pieChart = new PieChartConstructor("E:/Unity/TB_OpenToolkit", "pie", series, values, 500, 500);
        FileInputStream fileInputStream = null;
        try {
            File file = new File(pieChart.getPieChartConstructed());
            fileInputStream = new FileInputStream(file + ".jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sendPhoto(new SendPhoto()
                .setChatId(chatId)
                .setNewPhoto("photo", fileInputStream));
    }
}
