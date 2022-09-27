package hu.petrik.aukcioprojekt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Festmeny {
    private String cim;
    private String festo;
    private String stilus;
    private int licitekSzama;
    private int legmagasabbLicit;
    private LocalDateTime legutolsoLicitIdeje;
    private boolean elkelt;

    public Festmeny(String cim, String festo, String stilus) {
        this.cim = cim;
        this.festo = festo;
        this.stilus = stilus;
        this.licitekSzama = 0;
        this.legmagasabbLicit = 0;
        this.legutolsoLicitIdeje = null;
        this.elkelt = false;
    }

    public Festmeny() {

    }

    public String getCim() {
        return cim;
    }

    public String getFesto() {
        return festo;
    }

    public String getStilus() {
        return stilus;
    }

    public int getLicitekSzama() {
        return licitekSzama;
    }

    public int getLegmagasabbLicit() {
        return legmagasabbLicit;
    }

    public LocalDateTime getLegutolsoLicitIdeje() {
        return legutolsoLicitIdeje;
    }
    public boolean isElkelt() {
        return elkelt;
    }
    public void setElkelt(boolean elkelt) {
        this.elkelt = elkelt;
    }
    public void licit(){
        if (this.elkelt){
            System.out.println("Hibás próbálkozás, a tárgy már elkelt");
        }
        if (this.licitekSzama == 0) {
            this.legmagasabbLicit = 100;
        }else{
            //this.legmagasabbLicit = Integer.parseInt(String.valueOf(Math.round(legmagasabbLicit*1.1))); sajat megoldás
            int ujLicit = (int)(legmagasabbLicit * 1.1);
            String szovegesLicit = String.valueOf(ujLicit);
            int hossz = szovegesLicit.length();
            StringBuilder veglegesLicit = new StringBuilder(szovegesLicit.substring(0,2));
            for (int i = 0; i < hossz-2; i++) {
                veglegesLicit.append(0);
            }
            int veglegesLicitOsszeg = Integer.parseInt(veglegesLicit.toString());
            this.legmagasabbLicit = veglegesLicitOsszeg;
        }
        this.licitekSzama++;
        this.legutolsoLicitIdeje = LocalDateTime.now();
    }
    public void licit(int mertek){
        if (mertek <= 10 || mertek >= 100){
            throw new IllegalArgumentException("A licit mértéke 10% és 100% között kell hogy legyen");
            //System.out.println("Hibás próbálkozás,minimum 10% és maximum 100%-os növekedés lehetséges");
        }
        if (this.elkelt = true){
            throw new RuntimeException("A festmény már elkelt");
            //System.out.println("Hibás próbálkozás, a tárgy már elkelt"); Csak a mainben írunk ki
        } else if (this.licitekSzama == 0) {
            this.legmagasabbLicit = 100;
            this.licitekSzama++;
            this.legutolsoLicitIdeje = LocalDateTime.now();
        }else{
            //this.legmagasabbLicit = Integer.parseInt(String.valueOf(Math.round(legmagasabbLicit*((mertek/100)+1)));
            int ujLicit = (int)(legmagasabbLicit * ((mertek + 100) / 100));
        }
    }
    public int getKerekitettLicitMatematikaiMuveletekkel(int ujLicit){
        int osztasokSzama = 0;
        if (ujLicit > 100){
            ujLicit /= 10;
        }
        ujLicit *= Math.pow(10, osztasokSzama);
        return ujLicit;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatum = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        String s = String.format("%s: %s (%s)", this.festo, this.cim, this.stilus);
        if (this.licitekSzama > 0){
            return s;
        }
        return String.format("%s: %s (%s)\n" +
                        "%s" +
                        "%d $ - %s (összesen: %d db)\n",
                this.elkelt ? "elkelt\n" : "",
                this.legmagasabbLicit, this.legutolsoLicitIdeje.format(formatum), this.licitekSzama);
    }

}