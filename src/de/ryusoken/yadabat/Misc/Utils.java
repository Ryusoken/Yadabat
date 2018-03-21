package de.ryusoken.yadabat.Misc;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Utils {

    public static JsonElement readUrl(String urlString) throws IOException {

        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            JsonParser jp = new JsonParser();
            return jp.parse(buffer.toString());
        } catch (IOException ex) {
            ex.getMessage();
        } finally {
            if (reader != null)
                reader.close();
        }

        return null;
    }


    public static String RemoveQuotes(String ISBN) {

        String ISBNNew = "";

        for(int i = 0; i < ISBN.length(); i++) {

            if(ISBN.charAt(i) == '"') {
                continue;
            }
            ISBNNew = ISBNNew + ISBN.charAt(i);

        }
        return ISBNNew;
    }
}
