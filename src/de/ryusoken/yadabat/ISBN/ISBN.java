package de.ryusoken.yadabat.ISBN;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.ryusoken.yadabat.Misc.Config;
import de.ryusoken.yadabat.Misc.Utils;
import java.io.IOException;
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

        String s = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + ISBN + "&key=" + Utils.RemoveQuotes(Config.API_Key);
        URL url;

        try {

            JsonElement root = Utils.readUrl(s);
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

}
