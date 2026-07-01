import javax.swing.*;
import java.awt.*;

public class Nazgul extends Dusman {
    // BU SATIR DEĞİŞTİ: "nazgul.png"
    private static Image img = new ImageIcon("nazgul.png").getImage();

    public Nazgul(double x, double y) {
        // Can: 50, Zırh: 0, Hız: 75, Ödül: 15, Uçan: true, Üsse Hasar: 5
        super(x, y, 50, 0, 75, 15, true, 5);
    }

    @Override
    public void ciz(Graphics g) {
        if (isYavasladi()) {
            g.setColor(new Color(0, 255, 255, 100));
            // Mavi efekt de büyüdü (80x80)
            g.fillOval((int)x - 40, (int)y - 40, 80, 80);
        }

        // RESİM DEVASA OLDU (70x70)
        // x-35, y-35 yaparak tam merkeze oturtuyoruz
        g.drawImage(img, (int)x - 35, (int)y - 35, 70, 70, null);

        super.cizCanBari(g);
    }
}