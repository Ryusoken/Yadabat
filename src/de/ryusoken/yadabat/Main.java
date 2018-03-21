package de.ryusoken.yadabat;

import de.ryusoken.yadabat.EAN.EAN;
import de.ryusoken.yadabat.Misc.LoadConfig;
import java.util.Scanner;

public class Main {

    static Scanner scan = new Scanner(System.in);

    public static void main (String[] args) {
        LoadConfig.load();
        start();
    }

    public static void start() {

        String input;

        System.out.println("Geben Sie eine EAN13 Nummer ein: ");

        input = scan.nextLine();

        if(EAN.getInformationByVersion(input, "titel").equals("Error") || EAN.getInformationByVersion(input, "titel") == null) {
            System.err.println("Irgendetwas ist schief gelaufen. Überprüfe nochmals alle Angaben");
            System.exit(0);
        }
        System.out.println(EAN.getInformationByVersion(input, "titel"));
        System.out.println(EAN.getInformationByVersion(input, "label"));
        System.out.println(EAN.getInformationByVersion(input, "erscheinungart"));
        System.out.println(EAN.getInformationByVersion(input, "veroeffentlicht"));

        System.out.println("Drücke Enter um fortzufahren...");
        scan.nextLine();

        start();

    }
}
