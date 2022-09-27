package hu.petrik.aukcioprojekt;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Festmeny> festmenyek = new ArrayList<>();

    public static void main(String[] args) {
        Festmeny festmeny = new Festmeny("Hómező", "Bálint Ferenc", "Expresszionizmus");
        Festmeny festmeny1 = new Festmeny("Havas", "Balint Ferenc", "Reneszánsz");

        Festmeny a = new Festmeny("Hómező", "Bálint Ferenc", "Expresszionizmus");

        a.getKerekitettLicitMatematikaiMuveletekkel(10);

        try {
            festmenyekFelveteleKonzolról();
        } catch (InputMismatchException e) {
            System.out.println("Nem megfelelő számot adott meg, nem lesz felvéve a festmény");
        }

        try {
            Festmenyek emberek2 = new Festmenyek("festmenyek.csv");
            System.out.println(emberek2);
        } catch (FileNotFoundException e) {
            System.err.printf("Hiba nem található az %s fájl\n", "festmenyek.csv");
        } catch (IOException e) {
            System.err.println("Ismeretlen hiba történt a fájl beolvasása során");
        }
        veletlenszeruLicit();
        konzolosLicitalas();
        for (Festmeny f: festmenyek){
            System.out.println(f);
        }
    }
    private static void konzolosLicitalas() {
        Scanner sc = new Scanner(System.in);
        int festmenyIndex = -1;
        while(festmenyIndex == 0){
            System.out.println("Kérem adja meg a festmény sorszámát amivel licitálna (kilépéshez írjon be '0'-át): ");
            try {
                festmenyIndex = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Nem számot adott meg alicit");
                festmenyIndex = 0;
            }
            if (festmenyIndex < 0){
                System.out.println("A sorszám nem lehet negatív");
            }else if(festmenyIndex > festmenyek.size()){
                System.out.printf("Érvénytelen sorszám a listában csak %d festmény található\n", festmenyek.size());
            }else if (festmenyIndex > 0){
                Festmeny festmeny = festmenyek.get(festmenyIndex - 1);
                if (festmeny.getLegutolsoLicitIdeje() != null){
                    Duration legutolsoLicitOtaElteltIdo = Duration.between(festmeny.getLegutolsoLicitIdeje(), LocalDateTime.now());
                    if (legutolsoLicitOtaElteltIdo.getSeconds() > 120){
                        //festmeny.setElkelt();
                    }
                }
                if (festmeny.isElkelt()){
                    System.out.println("A festmény már elkelt");
                }else{
                    System.out.println("Kérem adja meg a licit mértékét (10-100, nem kötelező): ");
                    String megadottMertek = sc.nextLine();

                    if (megadottMertek.isEmpty()){
                        festmenyek.get(festmenyIndex - 1).licit();
                    }else{
                        try{
                            int mertek = Integer.parseInt(megadottMertek);
                            festmeny.licit(mertek);

                        }catch(IllegalArgumentException e){
                            System.out.println("Csak 10 és 100 közötti számt adhat meg");
                            festmenyIndex = 0;
                        }

                    }
                }
            }
            else{
                System.out.println("A licitálás lezárult");
            }

        }
    }

    private static void festmenyekHozzaadasaFajlbol(String fajlNev) throws IOException {
        FileReader fr = new FileReader(fajlNev);
        BufferedReader br = new BufferedReader(fr);
        String sor = br.readLine();
        while (sor != null && !sor.equals("")) {
            String[] adatok = sor.split(";");
            Festmeny festmeny = new Festmeny(adatok[1], adatok[0], adatok[2]);
            festmenyek.add(festmeny);
            sor = br.readLine();
        }
        br.close();
        fr.close();
    }

    private static void veletlenszeruLicit(){
        for (int i = 0; i < 20; i++) {
            int festmenyIndex = (int)(Math.random()*festmenyek.size());
            festmenyek.get(festmenyIndex).licit();
        }
    }


    private static void festmenyekFelveteleKonzolról(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Adja meg mennyi fetményt szeretne hozzáadni");
        int db = sc.nextInt();
        for (int i = 0; i < db; i++) {
            System.out.printf("Kérem adja meg a %d festmény címét", (i +1));
            String cim = sc.nextLine();
            System.out.printf("Kérem adja meg a %d festmény festőjét", (i +1));
            String festo = sc.nextLine();
            System.out.printf("Kérem adja meg a %d festmény stylusát", (i +1));
            String stilus = sc.nextLine();
            festmenyek.add(new Festmeny(cim, festo, stilus));
        }

    }

}