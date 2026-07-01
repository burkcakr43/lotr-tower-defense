import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OyunPaneli extends JPanel implements ActionListener {
    private Main mainFrame;
    Timer timer;
    ArrayList<Dusman> dusmanlar = new ArrayList<>();
    ArrayList<Kule> kuleler = new ArrayList<>();
    ArrayList<Mermi> mermiler = new ArrayList<>();

    // ARKA PLAN RESMİ İÇİN DEĞİŞKEN
    private Image arkaplanResmi;
    private Image yuzukResmi;

    int[][] yol = {
            {0, 150}, {700, 150}, {700, 400}, {100, 400}, {100, 600}
    };

    int oyuncuPara = 200;
    int oyuncuCan = 100;
    int dalgaNo = 1;

    ArrayList<Dusman> bekleyenDusmanlar = new ArrayList<>();
    int spawnZamanlayici = 0;
    boolean dalgaBittiMi = false;

    String seciliKule = "ELF";
    JButton btnElf, btnMancinik, btnAkBuyu;

    public OyunPaneli(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(null);
        this.setBackground(Color.BLACK);

        // RESMİ YÜKLEME KISMI
        try {
            // Proje klasörüne 'harita.jpg' atarsan onu kullanır
            arkaplanResmi = new ImageIcon("harita.jpg").getImage();
            yuzukResmi = new ImageIcon("yuzuk.png").getImage();
        } catch (Exception e) {
            System.out.println("Harita resmi bulunamadı.");
        }

        guiOlustur();

        timer = new Timer(16, this);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getY() > 50) {
                    kuleInsaEt(e.getX(), e.getY());
                }
            }
        });
    }

    public void oyunuBaslat() {
        resetOyunu();
        timer.start();
        Logger.yeniSeansBaslat();
        Logger.log("Simülasyon Başladı. Can: " + oyuncuCan + ", Para: " + oyuncuPara);
    }

    private void resetOyunu() {
        oyuncuPara = 200;
        oyuncuCan = 100;
        dalgaNo = 1;
        spawnZamanlayici = 0;
        dalgaBittiMi = false;

        dusmanlar.clear();
        kuleler.clear();
        mermiler.clear();
        bekleyenDusmanlar.clear();

        dalga1Hazirla();
        repaint();
    }

    private void dalga1Hazirla() {
        int startX = -50;
        int startY = 150;
        bekleyenDusmanlar.add(new Ork(startX, startY));
        bekleyenDusmanlar.add(new Ork(startX, startY));
        bekleyenDusmanlar.add(new UrukHai(startX, startY));
        bekleyenDusmanlar.add(new Nazgul(startX, startY));
    }

    private void dalga2Hazirla() {
        int startX = -50;
        int startY = 150;
        bekleyenDusmanlar.add(new Ork(startX, startY));
        bekleyenDusmanlar.add(new Ork(startX, startY));
        bekleyenDusmanlar.add(new UrukHai(startX, startY));
        bekleyenDusmanlar.add(new UrukHai(startX, startY));
        bekleyenDusmanlar.add(new Nazgul(startX, startY));
        bekleyenDusmanlar.add(new Nazgul(startX, startY));
        bekleyenDusmanlar.add(new Ork(startX, startY));
    }

    private void guiOlustur() {
        btnElf = butonOlustur("Elf Kulesi (50)", 10, Color.GREEN, "ELF");
        btnMancinik = butonOlustur("Mancınık (75)", 170, Color.LIGHT_GRAY, "MANCINIK");
        btnAkBuyu = butonOlustur("Ak Büyü (70)", 330, Color.LIGHT_GRAY, "AKBUYU");
    }

    private JButton butonOlustur(String yazi, int x, Color renk, String kod) {
        JButton btn = new JButton(yazi);
        btn.setBounds(x, 10, 150, 30);
        btn.setBackground(renk);
        btn.addActionListener(e -> {
            seciliKule = kod;
            butonRenkleriniGuncelle();
        });
        this.add(btn);
        return btn;
    }

    private void butonRenkleriniGuncelle() {
        btnElf.setBackground(seciliKule.equals("ELF") ? Color.YELLOW : Color.LIGHT_GRAY);
        btnMancinik.setBackground(seciliKule.equals("MANCINIK") ? Color.YELLOW : Color.LIGHT_GRAY);
        btnAkBuyu.setBackground(seciliKule.equals("AKBUYU") ? Color.YELLOW : Color.LIGHT_GRAY);
    }

    private void kuleInsaEt(int x, int y) {
        if (y > 120 && y < 180) return;
        if (y > 370 && y < 430) return;
        if (x > 80 && x < 120 && y > 380) return;

        Kule yeniKule = null;
        if (seciliKule.equals("ELF") && oyuncuPara >= 50) yeniKule = new ElfKulesi(x, y);
        else if (seciliKule.equals("MANCINIK") && oyuncuPara >= 75) yeniKule = new CuceMancinik(x, y);
        else if (seciliKule.equals("AKBUYU") && oyuncuPara >= 70) yeniKule = new AkBuyuKulesi(x, y);

        if (yeniKule != null) {
            kuleler.add(yeniKule);
            oyuncuPara -= yeniKule.getFiyat();
            Logger.log("Kullanıcı kule inşa etti: " + seciliKule + ". Kalan Para: " + oyuncuPara);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        btnElf.setEnabled(oyuncuPara >= 50);
        btnMancinik.setEnabled(oyuncuPara >= 75);
        btnAkBuyu.setEnabled(oyuncuPara >= 70);

        if (oyuncuCan <= 0) {
            timer.stop();
            mainFrame.oyunBitti(false);
            return;
        }

        if (dusmanlar.isEmpty() && bekleyenDusmanlar.isEmpty()) {
            if (dalgaNo == 1 && !dalgaBittiMi) {
                dalgaBittiMi = true;
                dalgaNo = 2;
                dalga2Hazirla();
                dalgaBittiMi = false;
                Logger.log("Dalga 2 Başladı.");
            } else if (dalgaNo == 2 && !dalgaBittiMi) {
                timer.stop();
                mainFrame.oyunBitti(true);
                return;
            }
        }

        spawnZamanlayici++;
        if (spawnZamanlayici > 60 && !bekleyenDusmanlar.isEmpty()) {
            Dusman d = bekleyenDusmanlar.remove(0);
            dusmanlar.add(d);
            spawnZamanlayici = 0;
            Logger.log("Düşman girdi: " + d.getClass().getSimpleName());
        }

        Iterator<Mermi> mermiIt = mermiler.iterator();
        while(mermiIt.hasNext()) {
            Mermi m = mermiIt.next();
            m.hareketEt();
            if (!m.isAktif()) {
                if (m.getTur().equals("MANCINIK") && m.getHedef() != null) {
                    for (Dusman d : dusmanlar) {
                        if (!d.isUcan() && Math.abs(d.getX() - m.getX()) < 50 && Math.abs(d.getY() - m.getY()) < 50) {
                            d.hasarAl(m.getHasar());
                        }
                    }
                }
                mermiIt.remove();
            }
        }

        Iterator<Dusman> it = dusmanlar.iterator();
        while(it.hasNext()) {
            Dusman d = it.next();
            d.hareketEt(yol);

            if (d.hedefeUlastiMi(100, 600)) {
                oyuncuCan -= d.getUsseHasari();
                Logger.log(d.getClass().getSimpleName() + " üsse girdi. Can: " + oyuncuCan);
                it.remove();
            } else if (!d.yasiyorMu()) {
                oyuncuPara += d.getOdul();
                Logger.log(d.getClass().getSimpleName() + " öldü. Para: " + oyuncuPara);
                it.remove();
            }
        }

        for (Kule k : kuleler) {
            k.atesKontrol(dusmanlar, mermiler);
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. ARKA PLAN RESMİ (VARSA ÇİZ)
        if (arkaplanResmi != null) {
            g.drawImage(arkaplanResmi, 0, 0, 850, 650, null);
        } else {
            // Resim yoksa siyah yap
            g.setColor(Color.BLACK);
            g.fillRect(0,0,850,650);
        }

        Graphics2D g2 = (Graphics2D) g;

        // 2. YOLU ÇİZ (Üstüne)
        // Kahverengi toprak yol gibi görünmesi için kalın çizgi
        g2.setStroke(new BasicStroke(30));
        g2.setColor(new Color(101, 67, 33)); // Koyu kahverengi (Toprak)
        for (int i = 0; i < yol.length - 1; i++) {
            g2.drawLine(yol[i][0], yol[i][1], yol[i+1][0], yol[i+1][1]);
        }

        // Yolun kenarları (Opsiyonel - Daha şık görünür)
        g2.setStroke(new BasicStroke(2));
        g2.setColor(new Color(50, 30, 10));
        for (int i = 0; i < yol.length - 1; i++) {
            g2.drawLine(yol[i][0], yol[i][1], yol[i+1][0], yol[i+1][1]);
        }
        g2.setStroke(new BasicStroke(1));

        // 3. NESNELERİ ÇİZ
        for (Kule k : kuleler) k.ciz(g);
        for (Dusman d : dusmanlar) d.ciz(g);
        for (Mermi m : mermiler) m.ciz(g);

        // YENİ EKLENDİ: YÜZÜĞÜ ÇİZ (Sol Alt Köşe - Daha Büyük ve Yukarıda)
        if (yuzukResmi != null) {
            // Yolun sonu (100, 600).
            // Yeni Boyut: 90x90
            // x = 55 (Ortalamak için)
            // y = 520 (Biraz yukarı aldık ki yolun üstünde dursun)
            g.drawImage(yuzukResmi, 55, 520, 90, 90, null);
        }
        // 4. ARAYÜZ
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, 850, 50);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("PARA: " + oyuncuPara, 500, 30);
        g.drawString("CAN: " + oyuncuCan, 620, 30);
        g.drawString("DALGA: " + dalgaNo + "/2", 720, 30);

        // 5. TEMA REHBERİ (Eğer silindi ise buraya tekrar ekle)
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(600, 60, 230, 140);
        g.setColor(Color.WHITE);
        g.drawRect(600, 60, 230, 140);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("--- TEMA REHBERİ ---", 650, 80);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.drawString("Standart Düşman  =  ORK (Yeşil)", 610, 100);
        g.drawString("Zırhlı Düşman      =  URUK-HAI (Gri)", 610, 115);
        g.drawString("Uçan Düşman      =  NAZGUL (Siyah)", 610, 130);
        g.setColor(Color.GRAY);
        g.drawLine(610, 138, 820, 138);
        g.setColor(Color.WHITE);
        g.drawString("Okçu Kulesi  =  ELF KULESİ", 610, 155);
        g.drawString("Topçu Kulesi =  MANCINIK", 610, 170);
        g.drawString("Buz Kulesi    =  AK BÜYÜ", 610, 185);
    }
}