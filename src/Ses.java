import javax.swing.*;
import java.io.File;
import javax.sound.sampled.*;

public class Ses {

    // Sadece Arka Plan Müziği İçin Metot
    public static void muzikCal(String dosyaAdi) {
        new Thread(() -> {
            try {
                File sesDosyasi = new File(dosyaAdi);
                if (sesDosyasi.exists()) {
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(sesDosyasi);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInput);

                    // Sesi biraz kısık başlatmak istersen (Opsiyonel)
                    // FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    // gainControl.setValue(-10.0f); // Desibel düşürür

                    clip.loop(Clip.LOOP_CONTINUOUSLY); // Sonsuz döngü
                    clip.start();
                } else {
                    System.out.println("Müzik dosyası bulunamadı: " + dosyaAdi);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}