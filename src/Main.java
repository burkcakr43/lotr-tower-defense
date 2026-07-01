import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel anaPanel;
    private OyunPaneli oyunPaneli;
    private MenuPaneli menuPaneli;

    public Main() {
        super("Yüzüklerin Efendisi: Kule Savunma - Proje II");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(850, 650);
        this.setResizable(false);

        cardLayout = new CardLayout();
        anaPanel = new JPanel(cardLayout);

        // Panelleri oluştur
        menuPaneli = new MenuPaneli(this);
        oyunPaneli = new OyunPaneli(this);

        // Kartlara ekle (Sadece 2 kartımız var artık)
        anaPanel.add(menuPaneli, "MENU");
        anaPanel.add(oyunPaneli, "OYUN");

        this.add(anaPanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        cardLayout.show(anaPanel, "MENU");
        Ses.muzikCal("muzik.wav");
    }

    // Oyunu başlat ve RESETLE
    public void oyunEkraninaGec() {
        // Menüden oyuna geçerken menüyü de eski haline getirelim
        menuPaneli.menuyuSifirla();

        cardLayout.show(anaPanel, "OYUN");
        oyunPaneli.oyunuBaslat();
        oyunPaneli.requestFocusInWindow();
    }

    // Oyun bitince çağrılır
    public void oyunBitti(boolean kazandi) {
        // Menü panelindeki yazıları güncelle
        menuPaneli.mesajGoster(kazandi);

        // Tekrar menü kartını göster (Sonuç ekranı yerine geçen menü)
        cardLayout.show(anaPanel, "MENU");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}