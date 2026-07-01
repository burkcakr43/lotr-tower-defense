import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String DOSYA_ADI = "savunma_gunlugu.txt";

    // ESKİSİ GİBİ SİLMİYORUZ, YENİ BAŞLIK EKLİYORUZ
    public static void yeniSeansBaslat() {
        // Tarih formatı: Gün-Ay-Yıl Saat:Dakika:Saniye
        String zaman = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        // FileWriter(DOSYA_ADI, true) -> 'true' demek "Dosyanın sonuna ekle" demektir.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOSYA_ADI, true))) {
            writer.newLine(); // Önceki oyundan sonra boşluk bırak
            writer.write("===============================================================");
            writer.newLine();
            writer.write("   YENİ OYUN OTURUMU BAŞLADI - " + zaman);
            writer.newLine();
            writer.write("===============================================================");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String mesaj) {
        // Loglarda sadece saati göstermek yeterli
        String zaman = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String formatliMesaj = "[" + zaman + "] " + mesaj;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOSYA_ADI, true))) {
            writer.write(formatliMesaj);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}