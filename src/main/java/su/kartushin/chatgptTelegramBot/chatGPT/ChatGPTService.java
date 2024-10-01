package su.kartushin.chatgptTelegramBot.chatGPT;

import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import su.kartushin.chatgptTelegramBot.config.Config;

import java.io.IOException;

public class ChatGPTService {

    private static final Logger logger = LogManager.getLogger(ChatGPTService.class);
    private static final String OPENAI_API_KEY = Config.getOpenAIAPIKey(); // Вставьте свой API-ключ OpenAI
    private static final String OPENAI_API_URL = Config.getOpenAIURL();

    private final OkHttpClient client;

    public ChatGPTService() {
        this.client = new OkHttpClient();
    }

    public String getChatGPTResponse(String userMessage) throws IOException {
        // Создаем JSON-параметры для запроса
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("model", "gpt-4o");
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "user").put("content", userMessage));
        jsonBody.put("messages", messages);

        // Создаем тело запроса
        RequestBody body = RequestBody.create(
                jsonBody.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        logger.info(body);

        // Создаем сам запрос
        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                .build();

        // Отправляем запрос и получаем ответ
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Парсим JSON-ответ
            String responseBody = response.body().string();
            JSONObject responseJson = new JSONObject(responseBody);
            JSONArray choices = responseJson.getJSONArray("choices");
            return choices.getJSONObject(0).getJSONObject("message").getString("content");
        }
    }
}
