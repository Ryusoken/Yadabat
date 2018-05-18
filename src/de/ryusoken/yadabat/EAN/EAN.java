package de.ryusoken.yadabat.EAN;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.ryusoken.yadabat.Misc.Utils;
import java.io.IOException;
import java.net.MalformedURLException;

public class EAN {

    public static String getInformationByName(String EAN13, String info) {

        String s = "http://ofdbgw.metawave.ch/searchean_json/" + EAN13;

        try {
            JsonElement root = Utils.readUrl(s);
            JsonObject rootobj = root.getAsJsonObject();
            JsonObject ofdbgw = rootobj.getAsJsonObject("ofdbgw");
            JsonObject status = ofdbgw.getAsJsonObject("status");
            if(getErrorMessage(status) != 0) {
                System.exit(0);
            }
            JsonObject resultat = ofdbgw.getAsJsonObject("resultat");
            JsonObject eintrag = resultat.getAsJsonArray("eintrag").get(0).getAsJsonObject();
            return eintrag.get(info).toString();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            System.err.println("Die URL: " + s + " ist ungültig.");
        }
        return "Error";
    }


    public static String getInformationByVersion(String EAN13, String info) {

        String s = "http://ofdbgw.metawave.ch/fassung_json/" + Utils.RemoveQuotes(getInformationByName(EAN13, "fassungid"));

        try {
            JsonElement root = Utils.readUrl(s);
            JsonObject rootobj = root.getAsJsonObject();
            JsonObject ofdbgw = rootobj.getAsJsonObject("ofdbgw");
            JsonObject status = ofdbgw.getAsJsonObject("status");
            if(getErrorMessage(status) != 0) {
                System.exit(0);
            }
            JsonObject resultat = ofdbgw.getAsJsonObject("resultat");

            return resultat.get(info).toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Error";
    }

    private static int getErrorMessage(JsonObject status) {

        if(Integer.parseInt(status.get("rcode").toString()) == 4) {
            System.err.println("Es wurde kein Produkt zu ihrer Anfrage gefunden.");
            System.err.println("Es könnte sein das kein Eintrag in der OFDB existiert oder die EAN ungültig ist.");
            return 4;
        } else if(Integer.parseInt(status.get("rcode").toString()) == 3) {
            System.err.println("Es konnte keine Verbindung mit der OFDB hergestellt werden.");
            System.err.println("Bitte versuchen Sie es später noch einmal.");
            return 3;
        } else if(Integer.parseInt(status.get("rcode").toString()) == 9) {
            System.err.println("Das OFDBGW ist derweil nicht erreichbar");
            return 9;
        } else {
            return 0;
        }
    }




}
