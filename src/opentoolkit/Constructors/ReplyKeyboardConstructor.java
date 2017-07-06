package Constructors;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import patterns.KeyboardPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vanderkast on 04.07.2017.
 * <p>
 * this class uses to construct reply keyboard
 */
public class ReplyKeyboardConstructor {
    //returns keyboard with one button at row
    public ReplyKeyboardMarkup getKeyboard(ArrayList<String> buttons) {
        return getKeyboard(buttons, KeyboardPattern.ONE_BUTTON_AT_ROW);
    }

    //returns keyboard with custom count of buttons st row by pattern
    public ReplyKeyboardMarkup getKeyboard(ArrayList<String> buttons, KeyboardPattern pattern) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<KeyboardRow>();

        for (int i = 0; i < ButtonTool.completeLines(buttons.size(), pattern); i++) {
            rows.add(setButtonsInRow(buttons, pattern));
        }

        keyboard.setKeyboard(rows);
        return keyboard;
    }

    //Constructing row
    public KeyboardRow setButtonsInRow(ArrayList<String> buttons, KeyboardPattern pattern) {
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < ButtonTool.getButtonsAtLine(pattern); i++) {
            row.add(new KeyboardButton()
                    .setText(buttons.get(0)));

            buttons.remove(0);
        }
        return row;
    }

    //updating keyboard: sets in Button (row : posInRow) request contact
    public ReplyKeyboardMarkup AddContactRequestInKeyboard(ReplyKeyboardMarkup keyboard, int row, int posInRow){
        List<KeyboardRow> rows = keyboard.getKeyboard();
        KeyboardRow selectedRow = rows.get(row);
        KeyboardButton button = selectedRow.get(posInRow);

        button.setRequestContact(true);
        selectedRow.set(posInRow, button);
        rows.set(row, selectedRow);

        return keyboard;
    }

    //updating keyboard: sets in Button (row : posInRow) request location
    public ReplyKeyboardMarkup AddLocationRequestInKeyboard(ReplyKeyboardMarkup keyboard, int row, int posInRow){
        List<KeyboardRow> rows = keyboard.getKeyboard();
        KeyboardRow selectedRow = rows.get(row);
        KeyboardButton button = selectedRow.get(posInRow);

        button.setRequestLocation(true);
        selectedRow.set(posInRow, button);
        rows.set(row, selectedRow);

        return keyboard;
    }
}
