package Constructors;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import patterns.KeyboardPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vanderkast on 04.07.2017.
 * <p>
 * This class uses to construct inline keyboard
 */
public class InlineKeyboardConstructor {
    //returns keyboard with one button at row (buttonText = callBackData)
    public InlineKeyboardMarkup getKeyboard(ArrayList<String> buttons) {
        return getKeyboard(buttons, buttons, KeyboardPattern.ONE_BUTTON_AT_ROW);
    }

    //returns keyboard with one button at row and callBackData != buttonText)
    public InlineKeyboardMarkup getKeyboard(ArrayList<String> buttons, ArrayList<String> callBackData) {
        return getKeyboard(buttons, callBackData, KeyboardPattern.ONE_BUTTON_AT_ROW);
    }

    //returns keyboard with buttons at row by pattern (buttonText = callBackData)
    public InlineKeyboardMarkup getKeyboard(ArrayList<String> buttons, KeyboardPattern pattern) {
        return getKeyboard(buttons, buttons, pattern);
    }

    //returns keyboard with button at row by pattern and custom callBackData
    public InlineKeyboardMarkup getKeyboard(ArrayList<String> buttons, ArrayList<String> callBackData, KeyboardPattern pattern) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<List<InlineKeyboardButton>>();
        if (ButtonTool.completeLines(buttons.size(), pattern) != 0) {
            for (int i = 0; i < ButtonTool.completeLines(buttons.size(), pattern); i++) {
                rows.add(setButtonsInRow(buttons, callBackData, pattern));

                buttons = ButtonTool.removeSetButtons(buttons, pattern);
                callBackData = removeSetCallBackData(callBackData, pattern);
            }
        }
        rows.add(setButtonsInRow(buttons, callBackData, getPatternByButtonsAtLine(ButtonTool.buttonsInUncompleteLine(buttons.size(), pattern))));
        keyboard.setKeyboard(rows);

        return keyboard;
    }

    //returns call back data without firs line by pattern
    public ArrayList<String> removeSetCallBackData(ArrayList<String> callBackData, KeyboardPattern pattern) {
        for (int i = 0; i < ButtonTool.getButtonsAtLine(pattern); i++) {
            callBackData.remove(0);
        }
        return callBackData;
    }

    //returns pattern by count of buttons in line
    public KeyboardPattern getPatternByButtonsAtLine(int buttonsAtLine) {
        switch (buttonsAtLine) {
            case 1:
                return KeyboardPattern.ONE_BUTTON_AT_ROW;
            case 2:
                return KeyboardPattern.TWO_BUTTON_AT_ROW;
            case 3:
                return KeyboardPattern.THREE_BUTTON_AT_ROW;
            default:
                return null;
        }
    }

    //Constructing row
    public List<InlineKeyboardButton> setButtonsInRow(ArrayList<String> buttons, ArrayList<String> callBackData, KeyboardPattern pattern) {
        List<InlineKeyboardButton> row = new ArrayList<InlineKeyboardButton>();
        for (int i = 0; i < ButtonTool.getButtonsAtLine(pattern); i++) {
            row.add(new InlineKeyboardButton()
                    .setCallbackData(callBackData.get(i))
                    .setText(buttons.get(i)));
        }
        return row;
    }

    //uses when tou don't know pattern const
    public InlineKeyboardMarkup getKeyboard(ArrayList<String> buttons, int buttonsAtRow) {
        return getKeyboard(buttons, buttons, getPatternByButtonsAtLine(buttonsAtRow));
    }

    public InlineKeyboardMarkup getKeyboard(ArrayList<String> buttons, ArrayList<String> callBackData, int buttonsAtRow) {
        return getKeyboard(buttons, callBackData, getPatternByButtonsAtLine(buttonsAtRow));
    }

}
