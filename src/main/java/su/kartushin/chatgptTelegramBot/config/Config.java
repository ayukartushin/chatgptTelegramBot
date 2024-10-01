package su.kartushin.chatgptTelegramBot.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Config {

    private static final Logger logger = LogManager.getLogger(Config.class);

    public static String getTelegramBotName(){
        return getValue("TelegramBotName");
    }

    public static String getTelegramBotToken(){
        return getValue("TelegramBotToken");
    }

    public static String getOpenAIAPIKey(){
        return getValue("OpenAIAPIKey");
    }

    public static String getOpenAIURL(){
        return getValue("OpenAIURL");
    }

    private static String getValue(String variableName){
        // Получение значения переменной
        String value = System.getenv(variableName);

        if (value != null) {
            return value;
        } else {
            logger.error("Переменная окружения " + variableName + " не найдена.");
            return null;
        }
    }
}
