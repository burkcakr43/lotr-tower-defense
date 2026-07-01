import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AkBuyuKulesi extends Kule {
    // RESMİ YÜKLE
    private static Image img = new ImageIcon("akbuyu.png").getImage();

    public AkBuyuKulesi(int x, int y) {
        super(x, y, 150, 10, 70, 2000);
    }

    @Override
    protected void hedefSecVeAtesEt(List<Dusman> dusmanlar, List<Mermi> mermiler) {
        Dusman hedef = null;
        double enKisaMesafeUsse = Double.MAX_VALUE;

        for (Dusman d : dusmanlar) {
            if (mesafeHesapla(d) <= menzil) {
                double mesafeUsse = Math.sqrt(Math.pow(d.getX() - 100, 2) + Math.pow(d.getY() - 600, 2));
                if (mesafeUsse < enKisaMesafeUsse) {
                    enKisaMesafeUsse = mesafeUsse;
                    hedef = d;
                }
            }
        }

        if (hedef != null) {
            sonAtesZamani = System.currentTimeMillis();
            mermiler.add(new Mermi(x, y, hedef, this.hasar, "AKBUYU"));

            // Loglama Hesabı
            double zirh = (hedef instanceof UrukHai) ? 100.0 : 0.0;
            int netHasar = (int)(this.hasar * (1.0 - (zirh/(zirh+100.0))));
            int kalanCan = hedef.getMevcutCan() - netHasar;
            if(kalanCan < 0) kalanCan = 0;

            Logger.log("Kule '" + this.id + "', '" + hedef.id + "'i hedefledi; Net Hasar " + netHasar + ", Yavaşlatma %50 (3 sn) uygulandı. Kalan Can: " + kalanCan + "/" + hedef.getBaslangicCan() + ".");
        }
    }

    @Override
    public void ciz(Graphics g) {
        // RESİM DEVASA OLDU (80x80)
        // x-40, y-40 yaparak tam merkeze oturtuyoruz
        g.drawImage(img, x - 40, y - 40, 80, 80, null);
    }
}