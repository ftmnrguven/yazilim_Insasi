import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Backend: ReglTakipUygulamasi, GunlukKayit, ReglFazi ile birlikte çalışır.
 * Dışarıdan ekstra kütüphane gerektirmez.
 */
public class ReglAppGUI extends JFrame {

    private ReglTakipUygulamasi app;
    private CardLayout layout;
    private JPanel anaPanel;

    public static void main(String[] args) {
        new ReglAppGUI();
    }

    public ReglAppGUI() {

        setTitle("🎀 Regl Takip Uygulaması 🎀");
        setSize(600, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(255, 210, 225));

        layout = new CardLayout();
        anaPanel = new JPanel(layout);

        anaPanel.add(kayitEkrani(), "KAYIT");
        anaPanel.add(girisEkrani(), "GIRIS");

        add(anaPanel);
        setVisible(true);
    }

    // ---------------------------------------------------------------
    // KAYIT EKRANI
    // ---------------------------------------------------------------
    private JPanel kayitEkrani() {

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(255, 210, 225));

        JLabel baslik = new JLabel("💗 Kayıt Ol");
        baslik.setFont(new Font("Arial", Font.BOLD, 24));
        baslik.setBounds(230, 20, 200, 40);
        panel.add(baslik);

        JTextField txtAd = new JTextField();
        JTextField txtSoyad = new JTextField();
        JTextField txtYas = new JTextField();
        JTextField txtUser = new JTextField();
        JTextField txtPass = new JTextField();

        JLabel l1 = new JLabel("Ad:");
        JLabel l2 = new JLabel("Soyad:");
        JLabel l3 = new JLabel("Yaş:");
        JLabel l4 = new JLabel("Kullanıcı Adı:");
        JLabel l5 = new JLabel("Şifre:");

        l1.setBounds(70, 90, 130, 30);
        l2.setBounds(70, 140, 130, 30);
        l3.setBounds(70, 190, 130, 30);
        l4.setBounds(70, 240, 130, 30);
        l5.setBounds(70, 290, 130, 30);

        txtAd.setBounds(190, 90, 250, 30);
        txtSoyad.setBounds(190, 140, 250, 30);
        txtYas.setBounds(190, 190, 250, 30);
        txtUser.setBounds(190, 240, 250, 30);
        txtPass.setBounds(190, 290, 250, 30);

        panel.add(l1); panel.add(txtAd);
        panel.add(l2); panel.add(txtSoyad);
        panel.add(l3); panel.add(txtYas);
        panel.add(l4); panel.add(txtUser);
        panel.add(l5); panel.add(txtPass);

        JButton btnKayit = new JButton("Kaydı Oluştur");
        btnKayit.setBounds(230, 350, 150, 40);
        btnKayit.setBackground(Color.PINK);
        panel.add(btnKayit);

        btnKayit.addActionListener(e -> {
            try {
                int yas = Integer.parseInt(txtYas.getText());

                app = new ReglTakipUygulamasi(
                        txtAd.getText(),
                        txtSoyad.getText(),
                        yas,
                        txtUser.getText(),
                        txtPass.getText()
                );

                JOptionPane.showMessageDialog(
                        null,
                        "💗 Kayıt başarılı! Artık giriş yapabilirsiniz.\n" +
                                "Bu uygulama döngünüzü anlamanıza ve kendinize daha iyi bakmanıza yardım edecek."
                );

                layout.show(anaPanel, "GIRIS");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "⚠ Yaş sayı olmalı!");
            }
        });

        return panel;
    }

    // ---------------------------------------------------------------
    // GİRİŞ EKRANI
    // ---------------------------------------------------------------
    private JPanel girisEkrani() {

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(255, 210, 225));

        JLabel baslik = new JLabel("💗 Giriş Yap");
        baslik.setFont(new Font("Arial", Font.BOLD, 24));
        baslik.setBounds(240, 20, 200, 40);
        panel.add(baslik);

        JTextField user = new JTextField();
        JTextField pass = new JTextField();

        JLabel u = new JLabel("Kullanıcı Adı:");
        JLabel p = new JLabel("Şifre:");

        u.setBounds(100, 140, 140, 30);
        p.setBounds(100, 200, 140, 30);

        user.setBounds(230, 140, 200, 30);
        pass.setBounds(230, 200, 200, 30);

        JButton btnGiris = new JButton("Giriş");
        btnGiris.setBounds(250, 270, 120, 40);
        btnGiris.setBackground(Color.PINK);

        btnGiris.addActionListener(e -> {

            if (app != null && app.girisYap(user.getText(), pass.getText())) {

                anaPanel.add(menuEkrani(), "MENU");
                layout.show(anaPanel, "MENU");

            } else {
                JOptionPane.showMessageDialog(null, "⚠ Hatalı giriş!");
            }
        });

        panel.add(u); panel.add(user);
        panel.add(p); panel.add(pass);
        panel.add(btnGiris);

        return panel;
    }

    // ---------------------------------------------------------------
    // MENÜ EKRANI
    // ---------------------------------------------------------------
    private JPanel menuEkrani() {

        JPanel panel = new JPanel(new GridLayout(10, 1));
        panel.setBackground(new Color(255, 210, 225));

        JLabel baslik = new JLabel("🌸 Hoş Geldin " + app.getAd() + " 🌸", SwingConstants.CENTER);
        baslik.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(baslik);

        JButton b1 = new JButton("📍 Regl Başlangıç Tarihi Seç");
        JButton b2 = new JButton("⛅ Regl Bitiş Tarihi Seç");
        JButton b3 = new JButton("🩸 Ağrı / Akıntı Kaydet");
        JButton b4 = new JButton("📋 Kayıtları Gör");
        JButton b5 = new JButton("🌙 Bugünkü Fazı Gör");
        JButton b6 = new JButton("🗓️ Sonraki Regl Tahmini");
        JButton b7 = new JButton("🎀 Renkli Döngü Çizelgesi");
        JButton b8 = new JButton("📊 Ağrı & Akıntı Grafiği");
        JButton b9 = new JButton("Çıkış");

        JButton[] btns = {b1,b2,b3,b4,b5,b6,b7,b8,b9};
        for (JButton b : btns) {
            b.setBackground(Color.PINK);
            b.setFont(new Font("Arial", Font.BOLD, 15));
            panel.add(b);
        }

        b1.addActionListener(e -> reglBaslangicSec());
        b2.addActionListener(e -> reglBitisSec());
        b3.addActionListener(e -> veriKaydet());
        b4.addActionListener(e -> kayitListele());
        b5.addActionListener(e -> fazGoster());
        b6.addActionListener(e -> tahminGoster());
        b7.addActionListener(e -> donguCizelgesi());
        b8.addActionListener(e -> grafikEkrani());
        b9.addActionListener(e -> System.exit(0));

        return panel;
    }

    // ---------------------------------------------------------------
    // TARİH SEÇİMİ (GÜN + AY, YIL OTOMATİK BUGÜNÜN YILI)
    // ---------------------------------------------------------------
    private LocalDate tarihSec(String mesaj) {

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.setBackground(new Color(255, 210, 225));

        JComboBox<Integer> gun = new JComboBox<>();
        JComboBox<Integer> ay = new JComboBox<>();

        for (int i = 1; i <= 31; i++) gun.addItem(i);
        for (int i = 1; i <= 12; i++) ay.addItem(i);

        panel.add(new JLabel("Gün:")); panel.add(gun);
        panel.add(new JLabel("Ay:")); panel.add(ay);

        int sonuc = JOptionPane.showConfirmDialog(
                null,
                panel,
                mesaj,
                JOptionPane.OK_CANCEL_OPTION
        );

        if (sonuc != JOptionPane.OK_OPTION) {
            return null;
        }

        int g = (int) gun.getSelectedItem();
        int a = (int) ay.getSelectedItem();

        return LocalDate.of(LocalDate.now().getYear(), a, g);
    }

    // ---------------------------------------------------------------
    // İŞLEMLER
    // ---------------------------------------------------------------
    private void reglBaslangicSec() {

        LocalDate tarih = tarihSec("Regl Başlangıç Tarihi Seç");
        if (tarih == null) return;

        app.kayitEkle(tarih, true, 3, 3, "Regl başlangıcı");

        JOptionPane.showMessageDialog(
                null,
                "💗 Regl başlangıcı kaydedildi!\nTarih: " + tarih +
                        "\n\nBu tarih, sonraki regl tahmininde ve faz hesabında kullanılacak."
        );
    }

    private void reglBitisSec() {

        LocalDate tarih = tarihSec("Regl Bitiş Tarihi Seç");
        if (tarih == null) return;

        app.kayitEkle(tarih, false, 2, 2, "Regl bitişi");

        JOptionPane.showMessageDialog(
                null,
                "💗 Regl bitişi kaydedildi!\nTarih: " + tarih +
                        "\n\nBu kayıt, bu döngünün tamamlandığını gösterir."
        );
    }

    private void veriKaydet() {

        try {
            int agri = Integer.parseInt(
                    JOptionPane.showInputDialog("Ağrı şiddeti (1=çok az, 5=çok fazla):"));

            int akinti = Integer.parseInt(
                    JOptionPane.showInputDialog("Akıntı yoğunluğu (1=çok az, 5=çok fazla):"));

            String not = JOptionPane.showInputDialog("Bugüne özel bir not (opsiyonel):");

            app.kayitEkle(LocalDate.now(), false, agri, akinti,
                    not == null ? "Günlük veri" : not);

            StringBuilder msg = new StringBuilder();
            msg.append("💗 Kayıt alındı!\n\n");
            msg.append("Aşağıda, ağrı ve akıntı durumuna göre sana yardımcı olabilecek detaylı öneriler var:\n\n");

            // AĞRI TAVSİYESİ
            if (agri == 5) {
                msg.append("⚠ Çok şiddetli ağrı (5/5):\n")
                        .append("- Bu düzeyde ağrı, her regl döneminde normal sayılmaz.\n")
                        .append("- Eğer ağrın her ay bu kadar şiddetliyse veya günlük hayatını tamamen kilitliyorsa, bir kadın doğum uzmanına görünmen çok iyi olur.\n")
                        .append("- Sıcak su torbası veya sıcak duş, kasları gevşetip ağrıyı hafifletebilir.\n")
                        .append("- Ağrı kesici kullanıyorsan, mutlaka prospektüse uygun kullan ve gerekirse doktoruna danış.\n")
                        .append("- Bugün kendini zorlamaman, mümkünse dinlenmen önemli.\n\n");
            } else if (agri == 4) {
                msg.append("⚠ Yüksek ağrı (4/5):\n")
                        .append("- Ağrın oldukça yoğun, bu yüzden kendine yüklenmemelisin.\n")
                        .append("- Sıcak duş, karın bölgesine sıcak uygulama ve hafif esneme hareketleri (dizleri karnına çekmek, hafif yoga) rahatlatabilir.\n")
                        .append("- Kafein, tuz ve çok şekerli yiyecekler şişkinlik ve ağrıyı artırabilir; bu dönemde biraz azaltmak iyi gelebilir.\n")
                        .append("- Ağrı üst üste birkaç gün bu seviyede devam ederse bir uzmana danışmayı düşünebilirsin.\n\n");
            } else if (agri == 3) {
                msg.append("ℹ Orta düzey ağrı (3/5):\n")
                        .append("- Bu seviye birçok kişide görülen regl ağrısı düzeyinde.\n")
                        .append("- Magnezyumdan zengin besinler (bitter çikolata, badem, yeşil yapraklı sebzeler) kas gevşemesine yardımcı olabilir.\n")
                        .append("- Hafif yürüyüş ya da esneme hareketleri, kan dolaşımını artırarak ağrıyı azaltabilir.\n")
                        .append("- Yine de ağrı seni rahatsız ediyorsa, doktoruna danışarak uygun bir ağrı kesici planı belirleyebilirsin.\n\n");
            } else if (agri == 2) {
                msg.append("🙂 Hafif ağrı (2/5):\n")
                        .append("- Hafif seviyede ağrı olması sık görülen bir durumdur.\n")
                        .append("- Bol su içmek, hafif hareket etmek ve karın bölgesini sıcak tutmak seni rahatlatabilir.\n")
                        .append("- Kendine küçük mola alanları yaratman gününü daha konforlu hale getirir.\n\n");
            } else {
                msg.append("😊 Çok hafif / neredeyse yok ağrı (1/5):\n")
                        .append("- Harika! Bu dönemi, bedenini yormadan keyif aldığın aktivitelere ayırabilirsin.\n")
                        .append("- Yine de düzenli uyku, bol su ve dengeli beslenme hormon dengen için önemli.\n\n");
            }

            // AKINTI TAVSİYESİ
            if (akinti >= 4) {
                msg.append("💧 Yüksek akıntı / kanama (4-5/5):\n")
                        .append("- Kanama yoğun olduğunda, vücut demir kaybeder; bu da halsizlik ve baş dönmesine neden olabilir.\n")
                        .append("- Ispanak, mercimek, kırmızı et, kuru üzüm gibi demir bakımından zengin gıdalar tüketmen iyi gelir.\n")
                        .append("- Çok yoğun ped değişimi (örneğin 1-2 saatte bir pedin dolması) yaşıyorsan, bunu mutlaka bir doktorla paylaşmalısın.\n\n");
            } else if (akinti == 3) {
                msg.append("💧 Orta düzey akıntı (3/5):\n")
                        .append("- Normal sayılabilecek bir yoğunluk.\n")
                        .append("- Gün içinde pedini düzenli değiştirmeyi ve genital bölge temizliğine dikkat etmeyi unutma.\n\n");
            } else {
                msg.append("💧 Hafif akıntı (1-2/5):\n")
                        .append("- Oldukça hafif bir yoğunluk, genelde endişe verici değildir.\n")
                        .append("- Yine de kokuda, renkte veya yapıda alışılmışın dışında bir değişiklik fark edersen bir uzmana danışman iyi olur.\n\n");
            }

            msg.append("Unutma: Bu uygulama tıbbi tanı koymak için değil, seni desteklemek ve bedenini daha iyi tanıman için tasarlandı. Endişe duyduğun bir belirti varsa her zaman bir sağlık profesyoneline danışmalısın. 💗");

            JOptionPane.showMessageDialog(null, msg.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "⚠ Lütfen 1 ile 5 arasında sayısal değer gir.");
        }
    }

    private void kayitListele() {

        if (app.getTakvimKayitlari().isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠ Kayıt yok.");
            return;
        }

        JTextArea alan = new JTextArea();
        app.getTakvimKayitlari().values().forEach(k -> alan.append(k + "\n\n"));

        JScrollPane pane = new JScrollPane(alan);

        JOptionPane.showMessageDialog(
                null,
                pane,
                "📋 Tüm Kayıtlar",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private void fazGoster() {

        String faz = app.hangiFazdayız(LocalDate.now());

        JOptionPane.showMessageDialog(
                null,
                faz.replace("*", "")
        );
    }

    // ---------------------------------------------------------------
    // SONRAKİ REGL TAHMİNİ (DAHA ANLAŞILIR)
    // ---------------------------------------------------------------
    private void tahminGoster() {

        if (app.getTakvimKayitlari().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "⚠ Henüz regl başlangıcı kaydı yok.\n" +
                            "En az iki regl başlangıç tarihini işaretlersen tahmin daha sağlıklı olur."
            );
            return;
        }

        LocalDate sonBaslangic = app.sonReglBaslangiciniBul().orElse(LocalDate.now());
        int ort = app.ortalamaDonguUzunlugunuHesapla(); // Genelde ~28 gün civarı

        LocalDate tahmin = sonBaslangic.plusDays(ort);
        long fark = ChronoUnit.DAYS.between(LocalDate.now(), tahmin);

        StringBuilder sb = new StringBuilder();
        sb.append("📅 Son regl başlangıcın: ").append(sonBaslangic).append("\n");
        sb.append("⏱ Ortalama döngü süren: ").append(ort).append(" gün civarı.\n");
        sb.append("📌 Tahmini bir sonraki regl başlangıç tarihin: ").append(tahmin).append("\n\n");

        if (fark > 0) {
            sb.append("⏳ Tahmini başlangıca yaklaşık ")
                    .append(fark)
                    .append(" gün var.\n")
                    .append("Bu sadece ortalama bir hesaplama, birkaç gün kayma olması normaldir.");
        } else if (fark == 0) {
            sb.append("✨ Tahmini regl başlangıç günün BUGÜN!\n")
                    .append("Kendini yormamaya ve vücudunu dinlemeye çalış.");
        } else {
            sb.append("⚠ Tahmini regl başlama tarihin ")
                    .append(Math.abs(fark))
                    .append(" gün geçmiş görünüyor.\n")
                    .append("Bu bazen stresten, uykudan, kilo değişiminden etkilenebilir.\n")
                    .append("Eğer birkaç döngüdür önemli gecikmeler yaşıyorsan bir kadın doğum uzmanıyla görüşmen iyi olur.");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // ---------------------------------------------------------------
    // RENKLİ DÖNGÜ ÇİZELGESİ (AÇIKLAMALI)
    // ---------------------------------------------------------------
    private void donguCizelgesi() {

        JOptionPane.showMessageDialog(
                null,
                "Bu grafik, 28 günlük tipik bir döngüyü renkli olarak gösterir:\n\n" +
                        "🔴 1-5. gün: Regl dönemi (kanama)\n" +
                        "💗 6-14. gün: Foliküler faz (enerjinin yavaş yavaş yükseldiği dönem)\n" +
                        "💚 15-17. gün: Ovulasyon (en doğurgan olduğun dönem)\n" +
                        "💙 18-28. gün: Luteal faz (PMS belirtilerinin görülebildiği dönem)\n\n" +
                        "Bu sadece genel bir şablondur; her vücudun ritmi kendine özeldir."
        );

        JFrame frame = new JFrame("🎀 Renkli Döngü Çizelgesi 🎀");
        frame.setSize(860, 220);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("28 Günlük Örnek Menstrüel Döngü Şeması", 220, 25);

                int x = 20;
                int width = 25;
                int height = 80;
                int gap = 5;

                for (int i = 1; i <= 28; i++) {

                    if (i <= 5) g.setColor(Color.RED);
                    else if (i <= 14) g.setColor(Color.PINK);
                    else if (i <= 17) g.setColor(Color.GREEN);
                    else g.setColor(Color.CYAN);

                    g.fillRect(x, 50, width, height);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, 50, width, height);
                    g.drawString(String.valueOf(i), x + 8, 45);

                    x += width + gap;
                }

                g.setColor(Color.BLACK);
                g.drawString("🔴 Regl  |  💗 Foliküler  |  💚 Ovulasyon  |  💙 Luteal", 220, 160);
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    }

    // ---------------------------------------------------------------
    // AĞRI & AKINTI GRAFİĞİ (AÇIKLAMALI)
    // ---------------------------------------------------------------
    private void grafikEkrani() {

        if (app.getTakvimKayitlari().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "⚠ Henüz günlük veri girilmemiş.\n" +
                            "Ağrı/akıntı kayıtları ekledikçe bu grafikte görebilirsin."
            );
            return;
        }

        JOptionPane.showMessageDialog(
                null,
                "Bu grafik, girdiğin günlerdeki ağrı ve akıntı şiddetini karşılaştırır:\n\n" +
                        "💜 Mor çubuk: Ağrı şiddeti (1-5 arası)\n" +
                        "💙 Mavi çubuk: Akıntı yoğunluğu (1-5 arası)\n\n" +
                        "Her grup iki çubuktan oluşur ve bir günü temsil eder.\n" +
                        "Böylece hangi günlerde şikâyetlerinin arttığını görüp, düzen fark edebilirsin."
        );

        JFrame frame = new JFrame("📊 Ağrı & Akıntı Grafiği 📊");
        frame.setSize(900, 420);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("Günlere Göre Ağrı ve Akıntı Şiddeti", 280, 30);

                int x = 60;
                int width = 20;
                int gap = 10;
                int index = 1;

                for (GunlukKayit k : app.getTakvimKayitlari().values()) {

                    int a1 = k.getAgriMiktari() * 25;
                    int a2 = k.getAkintiYogunlugu() * 25;

                    // Ağrı (mor)
                    g.setColor(new Color(200, 0, 200));
                    g.fillRect(x, 300 - a1, width, a1);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, 300 - a1, width, a1);

                    // Akıntı (mavi)
                    g.setColor(Color.BLUE);
                    g.fillRect(x + 25, 300 - a2, width, a2);
                    g.setColor(Color.BLACK);
                    g.drawRect(x + 25, 300 - a2, width, a2);

                    // Gün numarası
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(index), x + 10, 320);

                    x += width + 40;
                    index++;
                }

                g.setColor(Color.BLACK);
                g.drawString("💜 Ağrı  |  💙 Akıntı  (Her grup bir günü temsil eder)", 220, 360);
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    }
}
