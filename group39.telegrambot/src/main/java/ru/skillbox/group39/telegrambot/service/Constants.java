package ru.skillbox.group39.telegrambot.service;

import com.vdurmont.emoji.EmojiParser;

public class Constants {

    public static final String INFO_TEXT = "/start - start the bot\n" +
                    "/authorization - authorization in social web\n" +
                    "/registration - registration in social web\n" +
                    "/get_my_posts - getting a list of my posts\n" +
                    "/get_post_friends - getting a list of friends posts\n" +
                    "/friends_list - my friends list\n" +
                    "/my_profile - view profile\n" +
                    "/exit - leaving the network";
    public static final String NOT_FOUND = EmojiParser.parseToUnicode("not found" + " :see_no_evil:");
    public static final String AUTHENTICATION_SUCCESSFUL = EmojiParser.parseToUnicode("Authentication successful" + " :sunglasses:");
    public static final String REGISTRATION_SUCCESSFUL = EmojiParser.parseToUnicode("Registration successful" + " :sunglasses:");
    public static final String ARE_YOU_SURE = "Are you sure?";
    public static final String WELCOME = EmojiParser.parseToUnicode("Welcome to social web " + ":blush:");
    public static final String RETURN = "return to menu";
    public static final String CHOOSE = "choose an action";
    public static final String ENTER_EMAIL = "Enter your email";
    public static final String ENTER_PASSWORD = "Enter password";
    public static final String USER_NOT_FOUND = EmojiParser.parseToUnicode("A user with this username and password was not found " + ":upside_down:");
}
