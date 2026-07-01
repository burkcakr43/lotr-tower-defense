import java.awt.*;

public abstract class Dusman {
    // LOGLAMA İÇİN ID SİSTEMİ
    public String id;
    private static int sayac = 100;

    protected double x, y;
    protected int baslangicCan;
    protected int mevcutCan;
    protected int zirh;
    protected int hiz;
    protected int odul;
    protected boolean ucan;
    protected int usseHasari;

    // Yavaşlatma değişkeni
    protected long yavaslamaBitisZamani = 0;
    protected int yolIndeksi = 0;

    public Dusman(double x, double y, int can, int zirh, int hiz, int odul, boolean ucan, int usseHasari) {
        this.x = x;
        this.y = y;
        this.baslangicCan = can;
        this.mevcutCan = can;
        this.zirh = zirh;
        this.hiz = hiz;
        this.odul = odul;
        this.ucan = ucan;
        this.usseHasari = usseHasari;

        // ID OLUŞTURMA
        sayac++;
        this.id = this.getClass().getSimpleName() + "-ID" + sayac;
    }

    public void yavaslat(int sureMs) {
        this.yavaslamaBitisZamani = System.currentTimeMillis() + sureMs;
    }

    public void hareketEt(int[][] yolNoktalari) {
        if (yolIndeksi < yolNoktalari.length) {
            int hedefX = yolNoktalari[yolIndeksi][0];
            int hedefY = yolNoktalari[yolIndeksi][1];

            double guncelHiz = this.hiz;
            if (System.currentTimeMillis() < yavaslamaBitisZamani) {
                guncelHiz = this.hiz / 2.0;
            }

            double adim = guncelHiz / 20.0;
            if (x < hedefX) x += adim;
            if (x > hedefX) x -= adim;
            if (y < hedefY) y += adim;
            if (y > hedefY) y -= adim;

            if (Math.abs(x - hedefX) < 5 && Math.abs(y - hedefY) < 5) {
                yolIndeksi++;
            }
        }
    }

    public void hasarAl(int kuleHasari) {
        double hasarCarpan = 1.0 - (zirh / (zirh + 100.0));
        int netHasar = (int) (kuleHasari * hasarCarpan);
        this.mevcutCan -= netHasar;
        if (this.mevcutCan < 0) this.mevcutCan = 0;
    }

    public abstract void ciz(Graphics g);

    public boolean yasiyorMu() { return mevcutCan > 0; }

    public boolean hedefeUlastiMi(int sonX, int sonY) {
        return Math.abs(x - sonX) < 10 && Math.abs(y - sonY) < 10;
    }

    // --- GETTER METOTLARI (HATAYI ÇÖZEN KISIM) ---
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isUcan() { return ucan; }
    public int getOdul() { return odul; }
    public int getUsseHasari() { return usseHasari; }
    public int getBaslangicCan() { return baslangicCan; } // EKLENDİ
    public int getMevcutCan() { return mevcutCan; }       // EKLENDİ

    public boolean isYavasladi() {
        return System.currentTimeMillis() < yavaslamaBitisZamani;
    }

    protected void cizCanBari(Graphics g) {
        int barX = (int)x - 15;
        int barY = (int)y - 20;
        int barGenislik = 30;
        int barYukseklik = 4;

        g.setColor(Color.RED);
        g.fillRect(barX, barY, barGenislik, barYukseklik);

        double yuzde = (double)mevcutCan / baslangicCan;
        if (yuzde < 0) yuzde = 0;

        g.setColor(Color.GREEN);
        g.fillRect(barX, barY, (int)(barGenislik * yuzde), barYukseklik);

        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barGenislik, barYukseklik);
    }
}