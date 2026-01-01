// Dosya Adı: GunlukKayit.java

import java.time.LocalDate;

/**
 * Kullanıcının belirli bir güne ait girdiği verileri tutar:
 * Regl durumu, Ağrı ve Akıntı dereceleri.
 */
public class GunlukKayit {

    private final LocalDate tarih;
    private final boolean reglMi;
    private final int agriMiktari;     // 1-5 arası
    private final int akintiYogunlugu; // 1-5 arası
    private final String not;

    public GunlukKayit(LocalDate tarih, boolean reglMi, int agriMiktari, int akintiYogunlugu, String not) {
        this.tarih = tarih;
        this.reglMi = reglMi;
        // Basit validasyon ile değerleri 1-5 arasında tutar
        this.agriMiktari = Math.min(5, Math.max(1, agriMiktari));
        this.akintiYogunlugu = Math.min(5, Math.max(1, akintiYogunlugu));
        this.not = not;
    }

    // Getter Metotları
    public LocalDate getTarih() { return tarih; }
    public boolean isReglMi() { return reglMi; }
    public int getAgriMiktari() { return agriMiktari; }
    public int getAkintiYogunlugu() { return akintiYogunlugu; }
    public String getNot() { return not; }

    @Override
    public String toString() {
        return String.format(
                "Tarih: %s, Regl: %s, Ağrı: %d/5, Akıntı: %d/5, Not: %s",
                tarih, reglMi ? "Evet" : "Hayır", agriMiktari, akintiYogunlugu, not
        );
    }
}