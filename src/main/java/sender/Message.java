package sender;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Message {

    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String CHAT_ID = System.getenv("CHAT_ID");

    public static void sendMessage(String mensaje) {
        try {
            //Mensaje Codificado para que admita emojis etc
            String encodedMessage = URLEncoder.encode(mensaje, String.valueOf(StandardCharsets.UTF_8));
            String urlString = "https://api.telegram.org/bot" + BOT_TOKEN +
                    "/sendMessage?chat_id=" + CHAT_ID +
                    "&text=" + encodedMessage; // Url del request

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); //Metodo Http mas inicializador de la peticion

            //Manejo response
            int answer = conn.getResponseCode();
            if (answer == 200) {
                System.out.println("Message Successfully sent.");
            } else {
                System.out.println("Error Sending Message HTTP: " + answer);
            }

            conn.disconnect(); //Liberar Recursos
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
