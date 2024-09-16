package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {
    private final int[] priser = new int[24];
    private final Scanner scanner;

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
                case "5":
                    visualisera();
                    break;
                case "e":
                    System.out.print("Avslutar programmet...\n");
                    return;
                default:
                    System.out.print("Ogiltigt val, försök igen.\n");
            }
        }
    }

    private void skrivMeny() {
        System.out.print("""
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                5. Visualisera
                e. Avsluta
                """);
    }

    private void inmatning() {
        System.out.print("Ange elpriser för varje timme (i öre): \n");
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

        System.out.print("Lägsta pris: " + String.format("%02d-%02d", minTimme, minTimme + 1) + ", " + minPris + " öre/kWh\n");
        System.out.print("Högsta pris: " + String.format("%02d-%02d", maxTimme, maxTimme + 1) + ", " + maxPris + " öre/kWh\n");
        System.out.printf("Medelpris: %.2f öre/kWh\n", medelPris);
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
            System.out.printf("%02d-%02d %d öre\n", timme, timme + 1, pris);
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
        System.out.print("Påbörja laddning klockan " + billigasteStart + "\n");
        System.out.printf("Medelpris 4h: %.1f öre/kWh\n", medelPris);
    }

    private void visualisera() {
        int maxPris = Arrays.stream(priser).max().orElse(1);
        int height = 15;
        int skala = (int) Math.ceil(maxPris / (double) height);

        System.out.println("Visualisering av elpriser:");

        for (int level = maxPris; level >= 0; level -= skala) {
            System.out.printf("%4d| ", level);
            for (int pris : priser) {
                if (pris >= level) {
                    System.out.print(" x ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        System.out.print("    |");
        for (int i = 0; i < priser.length; i++) {
            System.out.print("---");
        }
        System.out.println("|");

        System.out.print("      ");
        for (int i = 0; i < priser.length; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println();
    }
}