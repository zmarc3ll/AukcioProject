package hu.petrik.aukcioprojekt;

import java.time.LocalDateTime;

public class Festmeny {
    private String cim;
    private String festo;
    private String stilus;
    private int licitekSzama;
    private int legmagasabbLicit;
    private LocalDateTime legutolsoLicitideje;
    private boolean elkelt;

    public Festmeny(String cim, String festo, String stilus) {
        this.cim = cim;
        this.festo = festo;
        this.stilus = stilus;
        this.licitekSzama = 0;
        this.legmagasabbLicit = 0;
        this.legutolsoLicitideje = null;
        this.elkelt = false;
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

    public LocalDateTime getLegutolsoLicitideje() {
        return legutolsoLicitideje;
    }

    public boolean isElkelt() {
        return elkelt;
    }

    public void setElkelt(boolean elkelt) {
        this.elkelt = elkelt;
    }

    public void licit() {
        if (this.elkelt) {
            throw new RuntimeException("A festmény már elkelt");
        }
        if (this.licitekSzama == 0) {
            this.legmagasabbLicit = 100;
        } else {
            int ujLicit= (int) (this.legmagasabbLicit * 1.1);
            String szovegesLicit=String.valueOf(ujLicit);
            int hossz= szovegesLicit.length();
            StringBuilder veglegesLicit = new StringBuilder(szovegesLicit.substring(0,2));
            for (int i = 0; i < hossz-2; i++) {
                veglegesLicit.append("0".repeat(hossz-2));
            }
            int veglegesLicitOsszeg = Integer.parseInt(veglegesLicit.toString());
            this.legutolsoLicitideje=LocalDateTime.now();
        }
        this.licitekSzama++;
        this.legutolsoLicitideje = LocalDateTime.now();
    }

    public void licit(int mertek) {
        //TODO: eljárás megvalósítása
    }
}
