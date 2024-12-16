import javax.swing.*;
import java.awt.*;

public class AplikasiKasirRestoran extends JFrame {
    private JDesktopPane mdiDesktopPane; // Container untuk internal frame
    private JMenuBar menuBar;

    // Menu utama
    private JMenu masterDataMenu;
    private JMenu transaksiMenu;
    private JMenu laporanMenu;
    private JMenu userMenu; // Menu untuk user (untuk logout)

    // Item menu "Master Data"
    private JMenuItem menuItemMenu;

    // Item menu "Transaksi"
    private JMenuItem menuItemPemesanan;
    private JMenuItem menuItemPembayaran;

    // Item menu "Laporan"
    private JMenuItem menuItemRiwayatTransaksi;

    // Item menu "User" (untuk logout)
    private JMenuItem menuItemLogout;

    // Form internal
    private JInternalFrame formMenu;
    private JInternalFrame formPemesanan;
    private JInternalFrame formPembayaran;

    /**
     * Konstruktor utama aplikasi.
     */
    public AplikasiKasirRestoran() {
        if (!login()) {
            System.exit(0); // Jika login gagal, keluar dari aplikasi
        }
        initComponents();
    }

    /**
     * Inisialisasi komponen GUI.
     */
    private void initComponents() {
        setTitle("Aplikasi Kasir Restoran");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Atur ukuran window ke 85% dari layar
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double scale = 0.85;
        setSize((int) (scale * screenSize.getWidth()), (int) (scale * screenSize.getHeight()));
        setLocationRelativeTo(null);

        // Inisialisasi desktop pane dan menu bar
        mdiDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();

        // Inisialisasi menu utama
        masterDataMenu = new JMenu("Master Data");
        transaksiMenu = new JMenu("Transaksi");
        laporanMenu = new JMenu("Laporan");
        userMenu = new JMenu("User");

        // Inisialisasi item menu "Master Data"
        menuItemMenu = new JMenuItem("Menu Makanan & Minuman");

        // Inisialisasi item menu "Transaksi"
        menuItemPemesanan = new JMenuItem("Pemesanan");
        menuItemPembayaran = new JMenuItem("Pembayaran");

        // Inisialisasi item menu "Laporan"
        menuItemRiwayatTransaksi = new JMenuItem("Riwayat Transaksi");

        // Inisialisasi item menu "User" (untuk logout)
        menuItemLogout = new JMenuItem("Logout");

        // Tambahkan desktop pane ke konten utama
        setContentPane(mdiDesktopPane);

        // Tambahkan menu ke menu bar
        setJMenuBar(menuBar);
        menuBar.add(masterDataMenu);
        menuBar.add(transaksiMenu);
        menuBar.add(laporanMenu);
        menuBar.add(userMenu);

        // Tambahkan item ke menu "Master Data"
        masterDataMenu.add(menuItemMenu);

        // Tambahkan item ke menu "Transaksi"
        transaksiMenu.add(menuItemPemesanan);
        transaksiMenu.add(menuItemPembayaran);

        // Tambahkan item ke menu "Laporan"
        laporanMenu.add(menuItemRiwayatTransaksi);

        // Tambahkan item ke menu "User" (Logout)
        userMenu.add(menuItemLogout);

        // Tambahkan event listener
        menuItemMenu.addActionListener(evt -> openForm("Menu Makanan & Minuman"));
        menuItemPemesanan.addActionListener(evt -> openForm("Pemesanan"));
        menuItemPembayaran.addActionListener(evt -> openForm("Pembayaran"));
        menuItemRiwayatTransaksi.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Fitur laporan belum diimplementasikan."));
        menuItemLogout.addActionListener(evt -> logout());
    }

    /**
     * Proses login sederhana.
     * @return true jika login berhasil, false jika gagal.
     */
    private boolean login() {
        // Modifikasi ukuran form login lebih besar
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(
            null,
            message,
            "Login",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            return "admin".equals(username) && "123".equals(password);
        }
        return false; // Login gagal jika pengguna menekan cancel
    }

    /**
     * Buka form internal sederhana berdasarkan nama.
     * @param formName nama form
     */
    private void openForm(String formName) {
        JInternalFrame form = new JInternalFrame(formName, true, true, true, true);
        form.setSize(400, 300);
        form.setLocation((mdiDesktopPane.getWidth() - form.getWidth()) / 2, (mdiDesktopPane.getHeight() - form.getHeight()) / 2);
        form.setVisible(true);
        mdiDesktopPane.add(form);
    }

    /**
     * Proses logout yang menutup semua form dan kembali ke login.
     */
    private void logout() {
        int option = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Menutup semua form internal yang terbuka
            for (JInternalFrame frame : mdiDesktopPane.getAllFrames()) {
                frame.dispose();
            }

            // Kembali ke halaman login
            this.dispose(); // Menutup aplikasi kasir
            SwingUtilities.invokeLater(() -> new AplikasiKasirRestoran().setVisible(true)); // Memulai ulang aplikasi dengan login
        }
    }

    /**
     * Metode utama untuk menjalankan aplikasi.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AplikasiKasirRestoran().setVisible(true));
    }
}