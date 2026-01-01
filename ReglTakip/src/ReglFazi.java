// Dosya Adı: ReglFazi.java

/**
 * Menstrüel döngünün dört ana fazını, gün aralıklarını ve istediğiniz detaylı açıklamaları tanımlar.
 */
public enum ReglFazi {

    REGL_FAZI(1, 5, "Regl dönemi. Kanama başlar. Vücut dinlenmeye odaklanmalıdır. Egzersiz yoğunluğunu düşürün."),
    FOLLIKULER_FAZ(6, 14, "Foliküller gelişir. Östrojen yükselir. Enerji artışı hissedilebilir. Egzersiz için uygun ve güçlü dönem."),
    OVULASYON_FAZI(15, 17, "Yumurta serbest bırakılır. En doğurgan dönem. Bazı kişilerde hafif ağrı olabilir. Enerji zirvededir."),
    LUTEAL_FAZ(18, 28, "Vücut olası gebeliğe hazırlanır. Progesteron yükselir. PMS belirtileri (şişkinlik, hassasiyet) görülebilir. Daha düşük yoğunluklu egzersizler önerilir.");

    private final int baslangicGun;
    private final int bitisGun;
    private final String aciklama;

    ReglFazi(int baslangicGun, int bitisGun, String aciklama) {
        this.baslangicGun = baslangicGun;
        this.bitisGun = bitisGun;
        this.aciklama = aciklama;
    }

    public int getBaslangicGun() { return baslangicGun; }
    public int getBitisGun() { return bitisGun; }
    public String getAciklama() { return aciklama; }
}