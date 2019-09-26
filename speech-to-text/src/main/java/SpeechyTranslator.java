import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SpeechyTranslator {
    // TODO: If you have your own Premium account credentials, put them down here:
    private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
    private static final String CLIENT_SECRET = "PUBLIC_SECRET";
    private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

    /**
     * Entry Point
     */
    public static boolean start() {
        boolean exit = false;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter text to be translated:");
            String text = sc.next();
            System.out.println("Enter text language. Example: 'en'.");
            String fromLang = sc.next();
            System.out.println("Enter text language to be translated in. Example: 'bg'.");
            String toLang = sc.next();

            try {
                translate(fromLang, toLang, text);
            } catch (Exception e) {
                System.err.println("Unsupported translation.");
            }
            System.out.println("Do you want to translate another text? (y/n)");
            String userChoice = "";
            do {
                userChoice = sc.next();
            } while (userChoice.equals("y") || userChoice.equals("n"));
            if (userChoice.equals("n")) {
                break;
            }
        }
        return exit;
    }

    /**
     * Sends out a WhatsApp message via WhatsMate WA Gateway.
     */
    private static void translate(String fromLang, String toLang, String text) throws Exception {
        // TODO: Should have used a 3rd party library to make a JSON string from an object
        String jsonPayload = new StringBuilder()
                .append("{")
                .append("\"fromLang\":\"")
                .append(fromLang)
                .append("\",")
                .append("\"toLang\":\"")
                .append(toLang)
                .append("\",")
                .append("\"text\":\"")
                .append(text)
                .append("\"")
                .append("}")
                .toString();

        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
        conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(jsonPayload.getBytes());
        os.flush();
        os.close();

        int statusCode = conn.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
        ));
        String output;
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }
        conn.disconnect();
    }
}