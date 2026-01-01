// Dosya Adı: ReglTakipUygulamasi.java

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Regl Takip uygulamasının tüm iş mantığını içerir.
 */
public class ReglTakipUygulamasi {

    private final String ad;
    private final String soyad;
    private final int yas;
    private final String kullaniciAdi;
    private final String sifre;

    private final Map<LocalDate, GunlukKayit> takvimKayitlari;
    private final List<Integer> sonDonguUzunluklari;
    public Map<LocalDate, GunlukKayit> getTakvimKayitlari() {
        return takvimKayitlari;
    }


    private static final int ORTALAMA_DONGU_UZUNLUGU = 28;

    // --- Constructor ---
    public ReglTakipUygulamasi(String ad, String soyad, int yas, String kullaniciAdi, String sifre) {
        this.ad = ad;
        this.soyad = soyad;
        this.yas = yas;
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.takvimKayitlari = new HashMap<>();
        this.sonDonguUzunluklari = new ArrayList<>();
    }

    // --- Temel Giriş ve Çıktılar ---
    public boolean girisYap(String girilenKullaniciAdi, String girilenSifre) {
        return this.kullaniciAdi.equals(girilenKullaniciAdi) && this.sifre.equals(girilenSifre);
    }

    public String getAd() { return ad; }

    public void kayitEkle(LocalDate tarih, boolean reglMi, int agri, int akinti, String note) {
        // GunlukKayit nesnesi oluşturulur (GunlukKayit.java'dan gelir)
        GunlukKayit yeniKayit = new GunlukKayit(tarih, reglMi, agri, akinti, note);
        takvimKayitlari.put(tarih, yeniKayit);

        // Döngü uzunluğu hesaplaması için yeni bir başlangıç mı kontrolü yapılır
        if (reglMi && !tarih.minusDays(1).isBefore(LocalDate.MIN)) {
            GunlukKayit oncekiGunKaydi = takvimKayitlari.getOrDefault(tarih.minusDays(1), null);
            if (oncekiGunKaydi == null || !oncekiGunKaydi.isReglMi()) {
                donguUzunluklariniGuncelle(tarih);
            }
        }
    }

    // --- Analiz Metotları ---
    public String hangiFaz(int gun) {

        if (gun >= 1 && gun <= 5) {
            return "Menstruel Faz";
        }
        else if (gun >= 6 && gun <= 13) {
            return "Foliküler Faz";
        }
        else if (gun == 14) {
            return "Ovulasyon";
        }
        else if (gun >= 15 && gun <= 28) {
            return "Luteal Faz";
        }
        else {
            return "Hatalı gün girdiniz!";
        }
    }



    public String hangiFazdayız(LocalDate tarih) {
        Optional<LocalDate> sonBaslangicOpt = sonReglBaslangiciniBul();

        if (sonBaslangicOpt.isEmpty() || sonDonguUzunluklari.size() < 2) {
            return "Yeterli kayıt yok. Lütfen regl başlangıçlarınızı işaretlemeye devam edin.";
        }

        LocalDate sonBaslangic = sonBaslangicOpt.get();
        int ortalamaDongu = ortalamaDonguUzunlugunuHesapla();
        long donguGunuL = ChronoUnit.DAYS.between(sonBaslangic, tarih) + 1;
        int donguGunu = (int) (donguGunuL % ortalamaDongu);
        if (donguGunu == 0) donguGunu = ortalamaDongu;

        // Görsel Yardımcı: Fazların günlere göre dağılımını gösterir.


        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n--- Faz Bilgisi (%s) ---\n", tarih));
        sb.append(String.format("Döngünüzün ortalama uzunluğu: %d gün. Bugün, döngünün %d. günüdür.\n", ortalamaDongu, donguGunu));

        for (ReglFazi faz : ReglFazi.values()) {
            if (donguGunu >= faz.getBaslangicGun() && donguGunu <= faz.getBitisGun()) {
                sb.append(String.format(
                        "Şu an **%s** fazındayız.\n- Açıklama: %s",
                        faz.name().replace("_", " "), faz.getAciklama()
                ));
                return sb.toString();
            }
        }
        return sb.toString();
    }

    public void onerilerSun(LocalDate tarih) {
        GunlukKayit kayit = takvimKayitlari.get(tarih);

        if (kayit == null) return;

        System.out.printf("\n--- %s İçin Öneriler (Ağrı: %d, Akıntı: %d) ---\n", tarih, kayit.getAgriMiktari(), kayit.getAkintiYogunlugu());

        if (kayit.getAgriMiktari() >= 4) {
            System.out.println("**Ağrı Önerileri:** Isıtıcı ped kullanın. Hafif esneme egzersizleri yapın.");
        }
        if (kayit.getAkintiYogunlugu() >= 4) {
            System.out.println("**Akıntı Önerileri:** Demirden zengin gıdalar (kırmızı et, baklagil) tüketin.");
        }
        if (kayit.getAgriMiktari() >= 3 || kayit.isReglMi()) {
            String mesaj = ThreadLocalRandom.current().nextInt(2) == 0 ?
                    "Kendinize iyi bakın. Ruh halinizi dengelemek için bir parça **bitter çikolata** yiyebilirsiniz! 🍫" :
                    "Bugün dinlenme gününüz olabilir. Rahatlatıcı bir mesaj: 'Siz güçlüsünüz, bu da geçecek.'🧘‍♀️";
            System.out.println("\n**Rahatlatıcı Mesaj:** " + mesaj);
        }
    }

    // --- Yardımcı/Hesaplama Metotları ---

    /**
     * Kayıtlar arasından en son regl başlangıç tarihini bulur.
     */
    public Optional<LocalDate> sonReglBaslangiciniBul() {
        return takvimKayitlari.keySet().stream()
                .filter(t -> {
                    GunlukKayit mevcutKayit = takvimKayitlari.get(t);
                    if (mevcutKayit == null || !mevcutKayit.isReglMi()) return false;

                    LocalDate oncekiGun = t.minusDays(1);
                    GunlukKayit oncekiKayit = takvimKayitlari.get(oncekiGun);

                    // Önceki gün kayıtlı değilse VEYA önceki gün regl değilse, bu başlangıçtır.
                    return oncekiKayit == null || !oncekiKayit.isReglMi();
                })
                .max(Comparator.naturalOrder());
    }

    /**
     * Son 6 döngünün ortalama uzunluğunu hesaplar.
     */
    public int ortalamaDonguUzunlugunuHesapla() {
        if (sonDonguUzunluklari.isEmpty()) {
            return ORTALAMA_DONGU_UZUNLUGU;
        }
        return (int) sonDonguUzunluklari.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(ORTALAMA_DONGU_UZUNLUGU);
    }

    private void donguUzunluklariniGuncelle(LocalDate yeniBaslangic) {
        Optional<LocalDate> ikinciSonBaslangic = takvimKayitlari.keySet().stream()
                .filter(t -> {
                    GunlukKayit mevcutKayit = takvimKayitlari.get(t);
                    if (mevcutKayit == null || !mevcutKayit.isReglMi()) return false;

                    LocalDate oncekiGun = t.minusDays(1);
                    GunlukKayit oncekiKayit = takvimKayitlari.get(oncekiGun);

                    return oncekiKayit == null || !oncekiKayit.isReglMi();
                })
                .filter(t -> t.isBefore(yeniBaslangic))
                .max(Comparator.naturalOrder());

        if (ikinciSonBaslangic.isPresent()) {
            long donguUzunlugu = ChronoUnit.DAYS.between(ikinciSonBaslangic.get(), yeniBaslangic);
            if (donguUzunlugu > 0) {
                if (sonDonguUzunluklari.size() >= 6) {
                    sonDonguUzunluklari.remove(0);
                }
                sonDonguUzunluklari.add((int) donguUzunlugu);
            }
        }
    }

    // --- Görünüm/Konsol Metotları ---

    public void ayiGoruntule(int yil, int ay) {
        System.out.printf("\n--- %d/%d Ayı Kayıtları ---\n", ay, yil);
        takvimKayitlari.values().stream()
                .filter(k -> k.getTarih().getYear() == yil && k.getTarih().getMonthValue() == ay)
                .sorted(Comparator.comparing(GunlukKayit::getTarih))
                .forEach(System.out::println);
    }

    public void gecikmeKontrolu(LocalDate bugun) {
        Optional<LocalDate> sonBaslangicOpt = sonReglBaslangiciniBul();

        if (sonBaslangicOpt.isEmpty() || sonDonguUzunluklari.size() < 2) {
            System.out.println("\n[Gecikme Kontrolü] Yeterli geçmiş döngü kaydı yok.");
            return;
        }

        // ... (Gecikme kontrolü mantığı önceki gibi devam eder) ...
        LocalDate sonBaslangic = sonBaslangicOpt.get();
        int ortalamaDongu = ortalamaDonguUzunlugunuHesapla();
        LocalDate beklenenBaslangic = sonBaslangic.plusDays(ortalamaDongu);

        System.out.println("\n--- Regl Gecikme Kontrolü ---");

        if (bugun.isBefore(beklenenBaslangic)) {
            long kalanGun = ChronoUnit.DAYS.between(bugun, beklenenBaslangic);
            System.out.printf("Regl başlangıcına tahmini **%d gün** kaldı. Her şey yolunda görünüyor.\n", kalanGun);
            return;
        }

        long gecikmeGun = ChronoUnit.DAYS.between(beklenenBaslangic, bugun);
        System.out.printf("\n**⚠️ GECİKME TESPİT EDİLDİ!** Beklenen tarihten itibaren **%d gün** geçti.\n", gecikmeGun);

        if (gecikmeGun >= 7) {
            System.out.println("- **Durum:** 7 günü aşan gecikme yaşanmıştır. Uzmana danışmanız tavsiye edilir.");
        } else {
            System.out.println("- Kısa süreli (1-7 gün) gecikmeler genellikle normaldir.");
        }
    }
}