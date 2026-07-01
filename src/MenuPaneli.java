import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MenuPaneli extends JPanel {
    private Image arkaplanResmi;
    private JButton btnBaslat;
    private JButton btnCikis;

    private JLabel lblBaslik;
    private JLabel lblAltBaslik;

    public MenuPaneli(Main mainFrame) {
        this.setLayout(null);

        // RESMİ YÜKLE
        try {
            arkaplanResmi = new ImageIcon("menu_bg.jpg").getImage();
        } catch (Exception e) {
            System.out.println("Menü resmi bulunamadı.");
        }

        // --- EPİK FONT YÜKLEME ---
        // 65f = Yazı Büyüklüğü
        Font baslikFont = ozelFontYukle("epik.ttf", 65f);
        Font altBaslikFont = ozelFontYukle("epik.ttf", 35f);

        // --- BAŞLIK ---
        lblBaslik = new JLabel("YUZUKLERIN EFENDISI", SwingConstants.CENTER); // Türkçe karakter sorunu olmaması için
        lblBaslik.setFont(baslikFont);
        lblBaslik.setForeground(new Color(255, 215, 0)); // Altın Sarısı
        lblBaslik.setBounds(0, 80, 850, 80);

        // Gölge Efekti (Yazının aynısını siyah ve hafif kaydırarak arkaya koyuyoruz)
        JLabel golge = new JLabel("YUZUKLERIN EFENDISI", SwingConstants.CENTER);
        golge.setFont(baslikFont);
        golge.setForeground(Color.BLACK);
        golge.setBounds(3, 83, 850, 80);

        this.add(lblBaslik); // Önce parlak yazı
        this.add(golge);     // Sonra eklenirse üstte kalır, o yüzden sıraya dikkat etmeyelim, paint'te hallederiz veya böyle kalsın.
        // Swing'de son eklenen en üste gelir diye bir kural yoktur, Z-order vardır.
        // Basit gölge için en kolayı: Önce gölgeyi ekle, sonra asıl yazıyı.
        this.remove(lblBaslik);
        this.add(lblBaslik); // Şimdi en üstte

        lblAltBaslik = new JLabel("KULE SAVUNMA", SwingConstants.CENTER);
        lblAltBaslik.setFont(altBaslikFont);
        lblAltBaslik.setForeground(new Color(220, 220, 220));
        lblAltBaslik.setBounds(0, 160, 850, 50);
        this.add(lblAltBaslik);

        // --- BUTONLAR ---
        btnBaslat = ozelButonOlustur("OYUNU BASLAT", 300);
        btnBaslat.addActionListener(e -> mainFrame.oyunEkraninaGec());
        this.add(btnBaslat);

        btnCikis = ozelButonOlustur("CIKIS", 380);
        btnCikis.addActionListener(e -> System.exit(0));
        this.add(btnCikis);
    }

    // --- ÖZEL FONT YÜKLEME METODU ---
    private Font ozelFontYukle(String dosyaAdi, float boyut) {
        try {
            File fontDosyasi = new File(dosyaAdi);
            if (fontDosyasi.exists()) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, fontDosyasi);
                return font.deriveFont(boyut);
            }
        } catch (FontFormatException | IOException e) {
            System.out.println("Font yüklenemedi, varsayılan kullanılıyor.");
        }
        // Eğer dosya yoksa veya hata varsa "Algerian" kullan
        return new Font("Algerian", Font.BOLD, (int)boyut);
    }

    private JButton ozelButonOlustur(String yazi, int y) {
        JButton btn = new JButton(yazi);
        btn.setBounds(300, y, 250, 50);
        // Butonlarda da epik font kullanalım mı? Okunurluk için Serif daha iyi olabilir.
        // Ama istersen: btn.setFont(ozelFontYukle("epik.ttf", 20f)); yapabilirsin.
        btn.setFont(new Font("Serif", Font.BOLD, 20));
        btn.setBackground(new Color(0, 0, 0, 150));
        btn.setForeground(new Color(255, 215, 0));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(50, 50, 50, 200));
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0, 0, 0, 150));
            }
        });

        return btn;
    }

    public void mesajGoster(boolean kazandi) {
        if (kazandi) {
            lblBaslik.setText("TEBRIKLER!");
            lblBaslik.setForeground(Color.GREEN);
            lblAltBaslik.setText("ORTA DUNYA KURTARILDI");
        } else {
            lblBaslik.setText("KAYBETTINIZ...");
            lblBaslik.setForeground(Color.RED);
            lblAltBaslik.setText("MORDOR KAZANDI");
        }
        btnBaslat.setText("TEKRAR OYNA");
    }

    public void menuyuSifirla() {
        lblBaslik.setText("YUZUKLERIN EFENDISI");
        lblBaslik.setForeground(new Color(152, 132, 40));
        lblAltBaslik.setText("KULE SAVUNMA");
        btnBaslat.setText("OYUNU BASLAT");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (arkaplanResmi != null) {
            g.drawImage(arkaplanResmi, 0, 0, 850, 650, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 850, 650);
        }

        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, 850, 650);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(new Color(255, 215, 0));
        g2.drawRect(20, 20, 795, 570);
    }
}