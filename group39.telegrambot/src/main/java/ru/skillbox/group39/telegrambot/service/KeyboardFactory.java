package ru.skillbox.group39.telegrambot.service;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardFactory {

    public static ReplyKeyboardMarkup mainMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("get my posts");
        row.add("get posts friends");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("friends list");
        row.add("my profile");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("info");
        row.add("exit");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);

        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup choose() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("registration");
        row.add("authorization");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup stopBot() {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        InlineKeyboardButton yesBottom = new InlineKeyboardButton();
        yesBottom.setText("YES");
        yesBottom.setCallbackData("yes");
        InlineKeyboardButton noButton = new InlineKeyboardButton();
        noButton.setText("NO");
        noButton.setCallbackData("no");
        rowInLine.add(yesBottom);
        rowInLine.add(noButton);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        return markupInLine;
    }

    public static List<BotCommand> listOfCommands() {
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "start the bot"));
        listOfCommands.add(new BotCommand("/authorization", "authorization in social web"));
        listOfCommands.add(new BotCommand("/registration", "registration in the social web"));
        listOfCommands.add(new BotCommand("/info", "info"));
        listOfCommands.add(new BotCommand("/exit", "leaving the network"));
        return listOfCommands;
    }
}


//    String callBackData = update.getCallbackQuery().getData();
//    long messageId = update.getCallbackQuery().getMessage().getMessageId();
//    long chatId = update.getCallbackQuery().getMessage().getChatId();
//
//            if (callBackData.equalsIgnoreCase("yes")) {
//        String text = " reg";
//        EditMessageText editMessageText = new EditMessageText();
//        editMessageText.setChatId(chatId);
//        editMessageText.setText(text);
//        editMessageText.setMessageId((int) messageId);