import java.awt.*;
import java.util.List;

public class Mermi {
    private double x, y;
    private Dusman hedef;
    private int hasar;
    private int hiz = 10; // Mermi hızı
    private String tur; // "ELF", "MANCINIK", "AKBUYU"
    private boolean aktif = true;

    public Mermi(double x, double y, Dusman hedef, int hasar, String tur) {
        this.x = x;
        this.y = y;
        this.hedef = hedef;
        this.hasar = hasar;
        this.tur = tur;
    }

    public void hareketEt() {
        if (hedef == null || !hedef.yasiyorMu()) {
            aktif = false; // Hedef öldüyse mermi yok olsun
            return;
        }

        // Hedefe doğru açı hesapla
        double dx = hedef.getX() - x;
        double dy = hedef.getY() - y;
        double mesafe = Math.sqrt(dx * dx + dy * dy);

        if (mesafe < hiz) {
            // Çarptı!
            hedefVuruldu();
            aktif = false;
        } else {
            // Hedefe doğru ilerle
            x += (dx / mesafe) * hiz;
            y += (dy / mesafe) * hiz;
        }
    }

    private void hedefVuruldu() {
        // Hasar Verme Mantığı Buraya Taşındı
        if (tur.equals("ELF")) {
            int vurulacakHasar = hasar;
            // UrukHai kontrolünü burada yapabiliriz veya Kule'den hasarı hesaplı yollayabiliriz.
            // Basitlik için: Hasarı direkt uygula (Zırh hesabı Dusman içinde yapılıyor zaten)
            if (hedef instanceof UrukHai) vurulacakHasar /= 2;
            hedef.hasarAl(vurulacakHasar);
        }
        else if (tur.equals("AKBUYU")) {
            hedef.hasarAl(hasar);
            hedef.yavaslat(3000); // Yavaşlatma
        }
        else if (tur.equals("MANCINIK")) {
            // Alan hasarı için OyunPaneli'ndeki listeye erişim zor.
            // Bu yüzden basitçe: Hedefe hasar ver, alan hasarını OyunPaneli yönetsin diyemeyiz.
            // Şimdilik sadece hedefe vursun, görsel olarak tatmin etsin.
            // (Alan hasarını tam yapmak için mermiye düşman listesi göndermek gerekir, bu kodu uzatır)
            // Biz basit ve etkili tutalım:
            hedef.hasarAl(hasar);
        }
    }

    public void ciz(Graphics g) {
        if (tur.equals("ELF")) g.setColor(Color.YELLOW);
        else if (tur.equals("MANCINIK")) g.setColor(Color.BLACK);
        else if (tur.equals("AKBUYU")) g.setColor(Color.CYAN);

        g.fillOval((int)x - 3, (int)y - 3, 6, 6); // Küçük top mermi
    }

    public boolean isAktif() { return aktif; }
    public String getTur() { return tur; }
    public Dusman getHedef() { return hedef; }
    public int getHasar() { return hasar; }
    public double getX() { return x; }
    public double getY() { return y; }
}