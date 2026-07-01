import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CuceMancinik extends Kule {
    // RESMİ YÜKLE
    private static Image img = new ImageIcon("mancinik.png").getImage();

    public CuceMancinik(int x, int y) {
        super(x, y, 200, 50, 75, 3000);
    }

    @Override
    protected void hedefSecVeAtesEt(List<Dusman> dusmanlar, List<Mermi> mermiler) {
        Dusman anaHedef = null;
        double enKisaMesafeUsse = Double.MAX_VALUE;

        for (Dusman d : dusmanlar) {
            if (!d.isUcan() && mesafeHesapla(d) <= menzil) {
                double mesafeUsse = Math.sqrt(Math.pow(d.getX() - 100, 2) + Math.pow(d.getY() - 600, 2));
                if (mesafeUsse < enKisaMesafeUsse) {
                    enKisaMesafeUsse = mesafeUsse;
                    anaHedef = d;
                }
            }
        }

        if (anaHedef != null) {
            sonAtesZamani = System.currentTimeMillis();
            mermiler.add(new Mermi(x, y, anaHedef, this.hasar, "MANCINIK"));
            Logger.log("Kule '" + this.id + "' ateş etti (uçanları hedefleyemez). Alan vuruşu: merkez " + anaHedef.id + ".");
        }
    }

    @Override
    public void ciz(Graphics g) {
        // RESİM DEVASA OLDU (80x80)
        // x-40, y-40 yaparak tam merkeze oturtuyoruz
        g.drawImage(img, x - 40, y - 40, 80, 80, null);
    }
}