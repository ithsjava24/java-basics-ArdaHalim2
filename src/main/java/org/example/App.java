package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private int[] priser = new int[24];
    private Scanner scanner;

    public App(Scanner scanner) {
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        App app = new App(new Scanner(System.in));
        app.run();
    }

    public void run() {
        while (true) {
            skrivMeny();
            String val = scanner.nextLine();

            switch (val.toLowerCase()) {
                case "1":
                    inmatning();
                    break;
                case "2":
                    minMaxMedel();
                    break;
                case "3":
                    sortera();
                    break;
                case "4":
                    laddningsTid();
                    break;
                case "e":
                    System.out.println("Avslutar programmet...");
                    return;
                default:
                    System.out.println("Ogiltigt val, försök igen.");
            }
        }
    }

    private void skrivMeny() {
        System.out.println("""
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                e. Avsluta
                """);
    }

    private void inmatning() {
        System.out.println("Ange elpriser för varje timme (i öre): ");
        for (int i = 0; i < 24; i++) {
            System.out.print("Timme " + i + "-" + (i + 1) + ": ");
            priser[i] = Integer.parseInt(scanner.nextLine());
        }
    }

    private void minMaxMedel() {
        int minPris = Integer.MAX_VALUE;
        int maxPris = Integer.MIN_VALUE;
        int sum = 0;
        int minTimme = 0, maxTimme = 0;

        for (int i = 0; i < priser.length; i++) {
            if (priser[i] < minPris) {
                minPris = priser[i];
                minTimme = i;
            }
            if (priser[i] > maxPris) {
                maxPris = priser[i];
                maxTimme = i;
            }
            sum += priser[i];
        }
        double medelPris = sum / 24.0;

        System.out.println("Lägsta pris: " + String.format("%02d-%02d", minTimme, minTimme + 1) + ", " + minPris + " öre/kWh");
        System.out.println("Högsta pris: " + String.format("%02d-%02d", maxTimme, maxTimme + 1) + ", " + maxPris + " öre/kWh");
        System.out.printf("Medelpris: %.2f öre\n", medelPris);
    }

    private void sortera() {
        List<int[]> timmePrisLista = new ArrayList<>();
        for (int i = 0; i < priser.length; i++) {
            timmePrisLista.add(new int[]{i, priser[i]});
        }

        timmePrisLista.sort((a, b) -> b[1] - a[1]);

        for (int[] par : timmePrisLista) {
            int timme = par[0];
            int pris = par[1];
            System.out.println(String.format("%02d-%02d %d öre", timme, timme + 1, pris));
        }
    }

    private void laddningsTid() {
        int billigasteStart = 0;
        int billigastePrisSumma = Integer.MAX_VALUE;

        for (int i = 0; i <= priser.length - 4; i++) {
            int prisSumma = priser[i] + priser[i + 1] + priser[i + 2] + priser[i + 3];
            if (prisSumma < billigastePrisSumma) {
                billigastePrisSumma = prisSumma;
                billigasteStart = i;
            }
        }

        double medelPris = billigastePrisSumma / 4.0;
        System.out.println("Påbörja laddning klockan " + billigasteStart);
        System.out.printf("Medelpris 4h: %.1f öre/kWh\n", medelPris);
    }
}
