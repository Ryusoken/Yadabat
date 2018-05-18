package de.ryusoken.yadabat.Misc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;


public class LoadConfig {

    public static void load() {

        File file = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Yadabat.json");

        if(!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Die Konfigurationsdatei konnte erfolgreich erstellt werden.");
                System.out.println("Bitte bearbeiten Sie diese unter: " + file.getAbsolutePath());
                System.exit(0);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                System.out.println("Der Pfad konnte nicht gefunden werden oder die Datei konnte nicht erstellt werden.");
            }
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            if(reader.readLine() == null) {
                System.out.println("Die Konfigurationsdatei ist leer oder in einem falschem Format.");
                System.exit(0);
            }

            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();

            while(line != null) {
                sb.append(line);
                line = reader.readLine();
            }

            String Json = "{" + sb.toString();

            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(Json);
            JsonObject cfg = root.getAsJsonObject();
            JsonObject mysql = cfg.getAsJsonObject("MySQL");

            Config.API_Key = Utils.RemoveQuotes(cfg.get("API_Key").toString());
            Config.Host = Utils.RemoveQuotes(mysql.get("Host").toString());
            Config.Database = Utils.RemoveQuotes(mysql.get("Database").toString());
            Config.User = Utils.RemoveQuotes(mysql.get("User").toString());
            Config.Password = Utils.RemoveQuotes(mysql.get("Password").toString());

            System.out.println("Die Konfigurationsdatei konnte erfolgreich eingelesen werden.");

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Die Datei konnte nicht gefunden.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
