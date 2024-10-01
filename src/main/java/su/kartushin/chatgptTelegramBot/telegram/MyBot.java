package su.kartushin.chatgptTelegramBot.telegram;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import su.kartushin.chatgptTelegramBot.chatGPT.ChatGPTService;
import su.kartushin.chatgptTelegramBot.config.Config;

import java.io.IOException;

public class MyBot extends TelegramLongPollingBot {

    private final ChatGPTService chatGPTService = new ChatGPTService();
    private static final Logger logger = LogManager.getLogger(MyBot.class);

    @Override
    public String getBotUsername() {
        // Укажите имя вашего бота
        return Config.getTelegramBotName();
    }

    @Override
    public String getBotToken() {
        // Укажите токен вашего бота, полученный от BotFather
        return Config.getTelegramBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Проверяем, есть ли сообщение и текст в нем
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            // Получаем ответ от ChatGPT
            try {
                String chatGPTResponse = chatGPTService.getChatGPTResponse(messageText);

                // Отправляем ответ пользователю
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText(chatGPTResponse);
                execute(message);
            } catch (IOException | TelegramApiException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
