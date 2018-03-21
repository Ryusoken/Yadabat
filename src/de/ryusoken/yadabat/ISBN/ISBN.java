package de.ryusoken.yadabat.ISBN;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.ryusoken.yadabat.Misc.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ISBN {


    public static String ISBNRemoveDash(String ISBN) {

        String ISBNNew = "";

        for(int i = 0; i < ISBN.length(); i++) {

            if(ISBN.charAt(i) == '-') {
                continue;
            }
            ISBNNew = ISBNNew + ISBN.charAt(i);

        }
        return ISBNNew;
    }

    public static String getVolumeInformation(String ISBN, String info) {

        String ErrorMsg = "";

        String s = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + ISBN + Config.API_Key;
        URL url;

        try {

            JsonElement root = readUrl(s);
            JsonObject rootobj = root.getAsJsonObject();
            JsonObject items = rootobj.getAsJsonArray("items").get(0).getAsJsonObject();
            JsonObject volumeInfo = items.getAsJsonObject("volumeInfo");
            return volumeInfo.get(info).toString();

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            ErrorMsg = ex.getMessage();
        } catch (IOException ex) {
            ex.printStackTrace();
            ErrorMsg = ex.getMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorMsg = ex.getMessage();
        }

        return ErrorMsg;

    }

    private static JsonElement readUrl(String urlString) throws Exception {
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
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}
