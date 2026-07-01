import java.awt.*;
import java.util.List;

public abstract class Kule {
    // LOGLAMA İÇİN ID SİSTEMİ
    public String id;
    private static int kuleSayac = 1;

    protected int x, y;
    protected int menzil;
    protected int hasar;
    protected int fiyat;
    protected int atesHizi;
    protected long sonAtesZamani = 0;

    public Kule(int x, int y, int menzil, int hasar, int fiyat, int atesHizi) {
        this.x = x;
        this.y = y;
        this.menzil = menzil;
        this.hasar = hasar;
        this.fiyat = fiyat;
        this.atesHizi = atesHizi;

        // ID OLUŞTURMA
        // Sınıf ismini Türkçeleştirip ID veriyoruz
        String kisaAd = this.getClass().getSimpleName()
                .replace("CuceMancinik", "TopcuKulesi")
                .replace("AkBuyuKulesi", "BuzKulesi")
                .replace("ElfKulesi", "OkcuKulesi");

        this.id = kisaAd + "-ID00" + kuleSayac++;
    }

    public abstract void ciz(Graphics g);

    protected abstract void hedefSecVeAtesEt(List<Dusman> dusmanlar, List<Mermi> mermiler);

    public void atesKontrol(List<Dusman> dusmanlar, List<Mermi> mermiler) {
        if (System.currentTimeMillis() - sonAtesZamani >= atesHizi) {
            hedefSecVeAtesEt(dusmanlar, mermiler);
        }
    }

    protected double mesafeHesapla(Dusman d) {
        return Math.sqrt(Math.pow(d.getX() - x, 2) + Math.pow(d.getY() - y, 2));
    }

    public int getFiyat() { return fiyat; }
}