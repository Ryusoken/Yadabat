package de.ryusoken.yadabat;

import de.ryusoken.yadabat.EAN.EAN;
import de.ryusoken.yadabat.ISBN.ISBN;
import de.ryusoken.yadabat.Misc.Config;
import de.ryusoken.yadabat.Misc.LoadConfig;
import de.ryusoken.yadabat.Misc.Utils;
import de.ryusoken.yadabat.mysql.MySQL;

import java.util.Scanner;

public class Main {

    static private Scanner scan = new Scanner(System.in);

    public static void main (String[] args) {
        LoadConfig.load();
        MySQL.connect(Config.Host, Config.Database, Config.User, Config.Password);
        start();
    }

    static void start() {

        String input;

        System.out.println("Geben Sie eine EAN13 Nummer oder eine ISBN Nummer ein: ");

        input = scan.nextLine();

        if(input.startsWith("978") || input.startsWith("979")) {

            if(ISBN.getVolumeInformation(input, "title").equals("Error") || ISBN.getVolumeInformation(input, "title") == null) {
                System.err.println("Irgendetwas ist schief gelaufen. Überprüfe nochmals alle Angaben");
                System.exit(0);
            }

            System.out.println(ISBN.getVolumeInformation(ISBN.ISBNRemoveDash(input), "title"));
            System.out.println(ISBN.getVolumeInformation(ISBN.ISBNRemoveDash(input), "authors"));
            System.out.println(ISBN.getVolumeInformation(ISBN.ISBNRemoveDash(input), "publishedDate"));

            MySQL.insertIntoBooks(Utils.RemoveQuotes(ISBN.getVolumeInformation(input, "title")),
                    Utils.RemoveQuotes(ISBN.getVolumeInformation(input, "authors")),
                    Utils.RemoveQuotes(ISBN.getVolumeInformation(input,"publishedDate")),
                    input);

        } else {
            if (EAN.getInformationByVersion(input, "titel").equals("Error") || EAN.getInformationByVersion(input, "titel") == null) {
                System.err.println("Irgendetwas ist schief gelaufen. Überprüfe alle Angaben");
                System.exit(0);
            }
            System.out.println(EAN.getInformationByVersion(input, "titel"));
            System.out.println(EAN.getInformationByVersion(input, "label"));
            System.out.println(EAN.getInformationByVersion(input, "erscheinungart"));
            System.out.println(EAN.getInformationByVersion(input, "veroeffentlicht"));

            MySQL.insertIntoDvd(Utils.RemoveQuotes(EAN.getInformationByVersion(input, "titel")),
                    Utils.RemoveQuotes(EAN.getInformationByVersion(input, "label")),
                    Utils.RemoveQuotes(EAN.getInformationByVersion(input, "erscheinungart")),
                    Utils.RemoveQuotes(EAN.getInformationByVersion(input, "veroeffentlicht")),
                    input);

        }
        start();
    }
}
