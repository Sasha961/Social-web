package ru.skillbox.group39.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.skillbox.group39.telegrambot.config.BotConfig;
import ru.skillbox.group39.telegrambot.dto.authenticate.AuthenticateDto;
import ru.skillbox.group39.telegrambot.dto.posts.PostWithUser;
import ru.skillbox.group39.telegrambot.feign.AuthorizationController;
import ru.skillbox.group39.telegrambot.service.actions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.skillbox.group39.telegrambot.service.Constants.*;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private AuthenticateDto authenticateDto;
    private AuthorizationAction authorizationAction;
    private final MyPostsAction myPostsAction;
    private final FriendsAction friendsAction;
    public final BotConfig config;
    private final MyProfileAction myProfileAction;
    private final AuthorizationController authorizationService;
    private final FriendsPostsAction friendsPostsAction;

    private static Map<Long, String> users = new HashMap<>();

    public TelegramBot(MyPostsAction myPostsAction, FriendsAction friendsAction, BotConfig config, AuthorizationController authorizationService, MyProfileAction myProfileAction, FriendsPostsAction friendsPostsAction) {
        this.myPostsAction = myPostsAction;
        this.friendsAction = friendsAction;
        this.config = config;
        this.authorizationService = authorizationService;
        this.myProfileAction = myProfileAction;
        this.friendsPostsAction = friendsPostsAction;
        List<BotCommand> listOfCommands = KeyboardFactory.listOfCommands();
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (authorizationAction != null) {
            if (checkWord(authenticateDto.getEmail())) {
                authenticateDto.setEmail(update.getMessage().getText());
            } else if (checkWord(authenticateDto.getPassword())) {
                authenticateDto.setPassword(update.getMessage().getText());
            }
            authorizationUser(update.getMessage().getChatId(), update.getMessage());
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId);
                    choose(chatId, update.getMessage());
                    break;
                case "/info":
                case "info":
                    getHelp(chatId, INFO_TEXT);
                    break;
                case "my profile":
                case "/my_profile":
                    checkToken(chatId, update.getMessage());
                    myProfile(chatId, users.get(chatId));
                    break;
                case "/registration":
                case "registration":
                    registration(chatId, update.getMessage());
                    break;
                case "/authorization":
                case "authorization":
                    authorizationAction = new AuthorizationAction(authorizationService);
                    authenticateDto = new AuthenticateDto();
                    authorizationUser(chatId, update.getMessage());
                    break;
                case "/get_my_posts":
                case "get my posts":
                    getMyPosts(chatId, update.getMessage());
                    break;
                case "/get_posts_friends":
                case "get posts friends":
                    getFriendsPosts(chatId, update.getMessage());
                    break;
                case "/friends_list":
                case "friends list":
                    getFriendsList(chatId, update.getMessage());
                    break;
                case "/exit":
                case "exit":
                    stopBot(chatId);
                    break;
                default:
                    mainMenu(chatId, NOT_FOUND);
                    checkToken(chatId, update.getMessage());
                    break;
            }
        } else if (update.hasCallbackQuery()) {
            String callBackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            if (callBackData.equalsIgnoreCase("yes")) {
                users.put(chatId, null);
                startCommandReceived(chatId);
                choose(chatId, update.getMessage());
            } else if (callBackData.equalsIgnoreCase("no")) {
                if (users.get(chatId) == null) {
                    choose(chatId, update.getMessage());
                } else {
                    mainMenu(chatId, RETURN);
                }
            }
        }
    }

    private void getFriendsPosts(long chatId, Message message) {
        checkToken(chatId, message);
        List<PostWithUser> friendsPosts = friendsPostsAction.getFriendsPosts(users.get(chatId));
        if (friendsPosts.size() < 1) {
            sendMessages(chatId, "No posts found.");
        } else {
            friendsPosts.forEach(p -> sendMessages(chatId, p.toString().replaceAll("^\\[|\\]$", "")
                    .replaceAll("<p>", "")
                    .replaceAll("</p>", "")));
        }
    }

    private void getFriendsList(long chatId, Message message) {
        checkToken(chatId, message);
        sendMessages(chatId, friendsAction.getFriends(users.get(chatId)));
    }

    private synchronized void authorizationUser(Long chatId, Message message) {
        if (checkWord(authenticateDto.getEmail())) {
            sendMessages(chatId, ENTER_EMAIL);
            return;
        }
        if (checkWord(authenticateDto.getPassword())) {
            sendMessages(chatId, ENTER_PASSWORD);
            return;
        }
        authorizationAction.authenticateDto = authenticateDto;
        boolean isAuth = authorizationAction.isValid();
        if (isAuth) {
            users.put(chatId, authorizationAction.getToken());
            authorizationAction = null;
            mainMenu(chatId, AUTHENTICATION_SUCCESSFUL);
        } else {
            authorizationAction = null;
            sendMessages(chatId, USER_NOT_FOUND);
            choose(chatId, message);
        }
    }

    private void choose(Long chatId, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(CHOOSE);
        ReplyKeyboardMarkup keyboardMarkup = KeyboardFactory.choose();
        sendMessage.setReplyMarkup(keyboardMarkup);
        getExecute(sendMessage);
    }

    private void mainMenu(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        ReplyKeyboardMarkup keyboardMarkup = KeyboardFactory.mainMenu();
        sendMessage.setReplyMarkup(keyboardMarkup);
        getExecute(sendMessage);
    }

    private void registration(Long chatId, Message message) {
//        in developing
//        AccountSecureDto accountSecureDto = new AccountSecureDto();
        choose(chatId, message);
    }

    private void getMyPosts(Long chatId, Message message) {
        checkToken(chatId, message);
        List<PostWithUser> myPosts = myPostsAction.getPosts(users.get(chatId));
        if (myPosts.size() < 1) {
            sendMessages(chatId, "No posts found.");
        } else {
            myPosts.forEach(p -> sendMessages(chatId, p.toString().replaceAll("^\\[|\\]$", "")
                    .replaceAll("<p>", "")
                    .replaceAll("</p>", "")));
        }
    }

    private void stopBot(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(ARE_YOU_SURE);
        InlineKeyboardMarkup markupInLine = KeyboardFactory.stopBot();
        message.setReplyMarkup(markupInLine);
        getExecute(message);
    }

    private void myProfile(Long chatId, String token) {
        sendMessages(chatId, myProfileAction.getMyProfile(token));
    }

    private void startCommandReceived(long chatId) {
        getHelp(chatId, WELCOME);
    }

    private void getHelp(long chatId, String text) {
        sendMessages(chatId, text);
    }

    private void getExecute(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void checkToken(Long chatId, Message message) {
        if (checkWord(users.get(chatId))) {
            choose(chatId, message);
        }
    }

    private void sendMessages(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setParseMode(ParseMode.HTML);
        getExecute(sendMessage);
    }

    private boolean checkWord(String word) {
        return word == null || word.isEmpty();
    }
}
