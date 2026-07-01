import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ElfKulesi extends Kule {
    // RESMİ YÜKLE
    private static Image img = new ImageIcon("elf.png").getImage();

    public ElfKulesi(int x, int y) {
        super(x, y, 150, 20, 50, 1000);
    }

    @Override
    protected void hedefSecVeAtesEt(List<Dusman> dusmanlar, List<Mermi> mermiler) {
        Dusman secilenHedef = null;
        double enKisaMesafeUsse = Double.MAX_VALUE;

        // Hedef Seçimi (Mevcut mantık)
        for (Dusman d : dusmanlar) {
            if (mesafeHesapla(d) <= menzil) {
                double mesafeUsse = Math.sqrt(Math.pow(d.getX() - 100, 2) + Math.pow(d.getY() - 600, 2));
                if (mesafeUsse < enKisaMesafeUsse) {
                    enKisaMesafeUsse = mesafeUsse;
                    secilenHedef = d;
                }
            }
        }

        // Ateş ve Loglama
        if (secilenHedef != null) {
            sonAtesZamani = System.currentTimeMillis();
            mermiler.add(new Mermi(x, y, secilenHedef, this.hasar, "ELF"));

            // Loglama Mantığı (Aynen kalsın)
            int vurulacakHasar = this.hasar;
            String zirhLog = "";
            if (secilenHedef instanceof UrukHai) {
                vurulacakHasar /= 2;
                zirhLog = " -> Zırhlı cezası %50 -> " + vurulacakHasar;
            }
            double zirhMiktari = (secilenHedef instanceof UrukHai) ? 100.0 : 0.0;
            double hasarCarpan = 1.0 - (zirhMiktari / (zirhMiktari + 100.0));
            int netHasar = (int) (vurulacakHasar * hasarCarpan);
            int kalanCan = secilenHedef.getMevcutCan() - netHasar;
            if(kalanCan < 0) kalanCan = 0;

            Logger.log("Kule '" + this.id + "', '" + secilenHedef.id + "'i hedefledi (öncelik: üss'e en yakın).");
            Logger.log("Okçu atışı: Taban " + this.hasar + zirhLog + "; Zırh formülü ile Net Hasar " + netHasar + ". Kalan Can: " + kalanCan + "/" + secilenHedef.getBaslangicCan() + ".");
        }
    }

    @Override
    public void ciz(Graphics g) {
        // RESİM DEVASA OLDU (80x80)
        // x-40, y-40 yaparak tam merkeze oturtuyoruz
        g.drawImage(img, x - 40, y - 40, 80, 80, null);
    }
}