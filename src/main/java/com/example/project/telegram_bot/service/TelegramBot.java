package com.example.project.telegram_bot.service;

import com.example.project.telegram_bot.config.BotConfig;
import com.example.project.telegram_bot.entity.Ads;
import com.example.project.telegram_bot.entity.Fact;
import com.example.project.telegram_bot.entity.Joke;
import com.example.project.telegram_bot.entity.User;
import com.example.project.telegram_bot.repository.AdsRepository;
import com.example.project.telegram_bot.repository.FactRepository;
import com.example.project.telegram_bot.repository.JokeRepository;
import com.example.project.telegram_bot.repository.UserRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JokeRepository jokeRepository;
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private FactRepository factRepository;

    @Autowired
    EmailSenderService emailSenderService;

    static final String HELP_TEXT = "This bot is created to send a random joke from the database each time you request it.\n\n" +
            "You can execute commands from the main menu on the left or by typing commands manually\n\n" +
            "Type /start to see a welcome message\n\n" +
            "Type /shutdown to see shut down computer\n\n" +
            "Type /joke to get a random joke\n\n" +
            "Type /fact to get a random cat fact\n\n" +
            "Type /update_name <new name> to update your name\n\n"+
            "Type /update_email <new email> to update your email\n\n"+
            "Type /help to see this message again\n";
    static final String YES_BUTTON = "YES";
    static final String NO_BUTTON = "NO";
    static final String ERROR_TEXT = "Error occurred: ";
    static final int MAX_JOKE_ID_MINUS_ONE = 3772;
    static final String NEXT_JOKE = "NEXT_JOKE";
    static final String NEXT_FACT = "NEXT_FACT";

    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/shutdown", "shut down computer"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        listOfCommands.add(new BotCommand("/joke", "get a random joke"));
        listOfCommands.add(new BotCommand("/fact", "get a random fact"));
        listOfCommands.add(new BotCommand("/update_name", "update you name"));
        listOfCommands.add(new BotCommand("/update_email", "update you email"));

        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    public String getBotToken() {
        return config.getToken();
    }

    enum UserRegistrationState {
        NOT_REGISTERED,
        WAITING_FOR_NAME,
        WAITING_FOR_EMAIL,
        NOT_REGISTERED_EMAIL,
        REGISTERED
    }

    private Map<Long, UserRegistrationState> userRegistrationStateMap = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

//            if (messageText.equals("/start")) {
//                String name;
//                registerUser(update.getMessage());
//                if (userRepository.findById(chatId).get().getInputName() == null) {
//                    name = update.getMessage().getChat().getFirstName();
//                } else {
//                    name = userRepository.findById(chatId).get().getInputName();
//                }
//                showStart(chatId, name);
//            }
            if (messageText.contains("/send") && config.getOwnerId() == chatId) {
                String textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
                Iterable<User> users = userRepository.findAll();
                for (User user : users) {
                    prepareAndSendMessage(user.getChatId(), textToSend);
                }
            }
            else if (messageText.contains("/email_send") && config.getOwnerId() == chatId) {
                String text = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
                String subject="TELEGRAM BOT EMAIL";
                Iterable<User> users = userRepository.findAll();
                String email;
                for (User user : users) {
                    email=user.getEmail();
                    emailSenderService.sendEmail(email,subject, text);
                }
                prepareAndSendMessage(chatId, "Email sent.");
            }
            else if (messageText.startsWith("/update_name")) {
                String name = messageText.substring("/update_name ".length());
                saveName(chatId, name);
            }
            else if (messageText.startsWith("/update_email")) {
                String email = messageText.substring("/update_email ".length());
                saveEmail(chatId, email);
            }
            else if (messageText.equals("/shutdown")) {
                if (config.getOwnerId() == chatId) {
                    try {
                        // Execute the shutdown command (this might need admin/root privileges)
                        Process process = Runtime.getRuntime().exec("shutdown -s -t 0"); // Initiates immediate shutdown

                        // Optionally, you can retrieve the output of the command execution
                        // InputStream inputStream = process.getInputStream();
                        // BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        // String line;
                        // while ((line = reader.readLine()) != null) {
                        //     // Process and log the command output
                        // }

                        prepareAndSendMessage(chatId, "Initiating system shutdown...");
                    } catch (IOException e) {
                        // Handle exceptions that may occur during command execution
                        prepareAndSendMessage(chatId, "Error occurred while initiating shutdown: " + e.getMessage());
                        log.error("Error occurred while initiating shutdown: " + e.getMessage());
                    }
                } else {
                    prepareAndSendMessage(chatId, "You don't have permission to perform this operation.");
                }
            }
            else {
                UserRegistrationState registrationState = userRegistrationStateMap.getOrDefault(chatId, UserRegistrationState.NOT_REGISTERED);
                switch (registrationState) {
                    case NOT_REGISTERED:
                        if (userRepository.findById(chatId).isEmpty()) {
                            registerUser(update.getMessage());
                        }
                        if (userRepository.findById(chatId).get().getInputName() == null) {
                            askName(chatId);
                            userRegistrationStateMap.put(chatId, UserRegistrationState.WAITING_FOR_NAME);
                        } else if (userRepository.findById(chatId).get().getEmail() == null) {
                            userRegistrationStateMap.put(chatId, UserRegistrationState.NOT_REGISTERED_EMAIL);
                        } else {
                            userRegistrationStateMap.put(chatId, UserRegistrationState.REGISTERED);
                        }
                        //askName(chatId);
                        break;
                    case WAITING_FOR_NAME:
                        saveName(chatId, messageText);
                        userRegistrationStateMap.put(chatId, UserRegistrationState.NOT_REGISTERED_EMAIL);
                    case NOT_REGISTERED_EMAIL:
                        askEmail(chatId);
                        userRegistrationStateMap.put(chatId, UserRegistrationState.WAITING_FOR_EMAIL);
                        //askEmail(chatId);
                        break;
                    case WAITING_FOR_EMAIL:
                        saveEmail(chatId, messageText);
                        sendFirstEmail(chatId);
                        userRegistrationStateMap.put(chatId, UserRegistrationState.REGISTERED);
                        break;
                    case REGISTERED:
                        switch (messageText) {
                            case "/start":
                                    String name;
                                    registerUser(update.getMessage());
                                    if (userRepository.findById(chatId).get().getInputName() == null) {
                                        name = update.getMessage().getChat().getFirstName();
                                    } else {
                                        name = userRepository.findById(chatId).get().getInputName();
                                    }
                                    showStart(chatId, name);
                                break;
                            case "/help":
                                prepareAndSendMessage(chatId, HELP_TEXT);
                                break;
                            case "/joke":
                                Optional<Joke> joke = getRandomJoke();
                                joke.ifPresent(randomJoke -> addButtonAndSendMessage(randomJoke.getBody(), chatId, false));
                                break;
                            case "/fact":
                                Optional<Fact> fact = getRandomFact();
                                fact.ifPresent(randomFact -> addButtonAndSendMessage(randomFact.getFact(), chatId, true));
                                break;
                            default:
                                commandNotFound(chatId);
                        }
                }
            }

        }
        else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals(NEXT_JOKE)) {
                var joke = getRandomJoke();
                joke.ifPresent(randomJoke -> addButtonAndSendMessage(randomJoke.getBody(), chatId, false));
            }
            else if (callbackData.equals(NEXT_FACT)) {
                var fact = getRandomFact();
                fact.ifPresent(randomFact -> addButtonAndSendMessage(randomFact.getFact(), chatId,true));
            }
            else if (callbackData.equals(YES_BUTTON)) {
                boolean sendJoke = new Random().nextBoolean();
                if (sendJoke) {
                    Optional<Joke> joke = getRandomJoke();
                    joke.ifPresent(randomJoke -> addButtonAndSendMessage(randomJoke.getBody(), chatId, false));
                }
                else {
                    Optional<Fact> fact = getRandomFact();
                    fact.ifPresent(randomFact -> addButtonAndSendMessage(randomFact.getFact(), chatId, true));
                }
            }
            else if (callbackData.equals(NO_BUTTON)) {
                String text = "Okay, maybe next time! :)";
                prepareAndSendMessage(chatId,text);
            }
        }
    }

    private void showStart(long chatId, String name) {
        String text = EmojiParser.parseToUnicode(
                "Hi, " + name + "! :smile:" + " Nice to meet you!\n");
        prepareAndSendMessage(chatId,text);
    }

    private void showName(long chatId, String name) {
        String text = EmojiParser.parseToUnicode("Now I'll call you "+name+". :wink:");
        prepareAndSendMessage(chatId,text);
    }

    private void showEmail(long chatId) {
        String text = "I sent you something to the email address: "+userRepository.findById(chatId).get().getEmail();
        prepareAndSendMessage(chatId,text);
    }

    private void askName(long chatId) {
        String text = "Tell me your name, please.";
        prepareAndSendMessage(chatId,text);
    }

    private void saveName(Long chatId, String name) {
        Optional<User> userOptional = userRepository.findById(chatId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setInputName(name);
            userRepository.save(user);
            showName(chatId, name);
        }
    }

    private void askEmail(long chatId) {
        String text = "Tell me your email, please.";
        prepareAndSendMessage(chatId,text);
    }

    private void saveEmail(Long chatId, String email) {
        Optional<User> userOptional = userRepository.findById(chatId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmail(email);
            userRepository.save(user);
            showEmail(chatId);
        }
    }

    private void sendFirstEmail(Long chatId) {

        String email=userRepository.findById(chatId).get().getEmail();
        String text="Hello, "+userRepository.findById(chatId).get().getInputName()+".\nDid you know that...\n"+getRandomFact().get().getFact();
        String subject="TELEGRAM BOT EMAIL SENDING TEST";

        emailSenderService.sendEmail(email,subject, text);

//        Optional<User> userOptional = userRepository.findById(chatId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            String email = user.getEmail();
//            String name = user.getInputName();
//
//            // Email configuration
//            String senderEmail = "park.ksenia23@gmail.com"; // Replace with your sender email
//            String senderPassword = "your_sender_password"; // Replace with your sender email password
//            String smtpHost = "your_smtp_host"; // Replace with your SMTP host
//            int smtpPort = 587; // Replace with your SMTP port
//
//            Properties properties = new Properties();
//            properties.put("mail.smtp.auth", "true");
//            properties.put("mail.smtp.starttls.enable", "true");
//            properties.put("mail.smtp.host", smtpHost);
//            properties.put("mail.smtp.port", smtpPort);
//
//            Session session = Session.getInstance(properties, new Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(senderEmail, senderPassword);
//                }
//            });
//
//            try {
//                // Create a new message
//                Message message = new Message(session);
//
//                // Set the sender email
//                message.setFrom(new InternetAddress(senderEmail));
//
//                // Set the recipient email (user's email)
//                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
//
//                // Set the subject
//                message.setSubject("Hello, " + name);
//
//                // Set the message body
//                message.setText("Hello, " + name + "! This is a test email from the Telegram bot.");
//
//                // Send the email
//                Transport.send(message);
//
//                // Optionally, you can log the successful email sending
//                log.info("Email sent successfully to: " + email);
//            } catch (MessagingException e) {
//                // Handle any errors that occur during the email sending process
//                log.error("Error sending email: " + e.getMessage());
//            }
//        }

    }

    private void askUser(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Do you want to hear a joke or a cat fact?");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton yesButton = new InlineKeyboardButton();
        yesButton.setText(YES_BUTTON);
        yesButton.setCallbackData(YES_BUTTON);

        InlineKeyboardButton noButton = new InlineKeyboardButton();
        noButton.setText(NO_BUTTON);
        noButton.setCallbackData(NO_BUTTON);

        rowInline.add(yesButton);
        rowInline.add(noButton);

        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);

        message.setReplyMarkup(markupInline);

        executeMessage(message);
    }

    private Optional<Fact> getRandomFact(){
        var restTemplate=new RestTemplate();
        ResponseEntity<Fact> factEntity = restTemplate.getForEntity("https://catfact.ninja/fact", Fact.class);
        Fact fact = factEntity.getBody();
        factRepository.save(fact);
        return Optional.ofNullable(fact);
    }

    private Optional<Joke> getRandomJoke() {
        var r = new Random();
        var randomId = r.nextInt(MAX_JOKE_ID_MINUS_ONE) + 1;
        return jokeRepository.findById(randomId);
    }

    private void addButtonAndSendMessage(String text, long chatId, boolean isFact) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        var inlinekeyboardButton = new InlineKeyboardButton();
        if (!isFact) {
            inlinekeyboardButton.setCallbackData(NEXT_JOKE);
            inlinekeyboardButton.setText(EmojiParser.parseToUnicode("next joke " + ":rolling_on_the_floor_laughing:"));
        }
        else {
            inlinekeyboardButton.setCallbackData(NEXT_FACT);
            inlinekeyboardButton.setText(EmojiParser.parseToUnicode("next fact " + ":smiley_cat:"));
        }
        rowInline.add(inlinekeyboardButton);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        executeMessage(message);
    }

    private void commandNotFound(long chatId) {
        String answer = EmojiParser.parseToUnicode(
                "Command not recognized, please verify and try again :stuck_out_tongue_winking_eye: ");
        prepareAndSendMessage(chatId, answer);
    }

    private void executeEditMessageText(String text, Long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setText(text);
        message.setMessageId((int) messageId);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    private void registerUser(Message msg) {
        if (userRepository.findById(msg.getChatId()).isEmpty()) {
            Long chatId = msg.getChatId();
            Chat chat = msg.getChat();

            User user = new User();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            log.info("user saved: " + user);
        }
    }

//    private void startCommandReceived(Long chatId, String name) {
//        String answer = EmojiParser.parseToUnicode("Hi, " + name + ", nice to meet you!" + ":grin:");
//        log.info("Replied to user " + name);
//        sendMessage(answer,chatId);
//    }

//    private void sendMessage(String textToSend, Long chatId) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(textToSend);
//        send(message);
//
//    }

//    private void send(SendMessage msg) {
//        try {
//            execute(msg);
//        } catch (TelegramApiException e) {
//            log.error(Arrays.toString(e.getStackTrace()));
//        }
//    }

    private void prepareAndSendMessage(Long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }
    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }



    @Scheduled(cron = "${cron.scheduler}")
    private void sendAds() {
        Iterable<Ads> ads = adsRepository.findAll();
        Iterable<User> users = userRepository.findAll();

        for (Ads ad : ads) {
            for (User user : users) {
                prepareAndSendMessage(user.getChatId(), ad.getAd());
            }
        }
    }

    @Scheduled(cron = "0 0 * * * *")
    private void sendAskUser() {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            askUser(user.getChatId());
        }
    }

    public void sendCreateTaskNotification(String message) {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            prepareAndSendMessage(user.getChatId(),message);
        }
    }
}







