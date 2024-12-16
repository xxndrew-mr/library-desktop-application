import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormPemesanan extends JFrame {
    private JTextField tfNamaPemesan, tfNamaBarang, tfJumlah, tfHarga;
    private JButton btnSubmit, btnClear;
    private JTextArea taOutput;

    public FormPemesanan() {
        setTitle("Form Pemesanan - Kasir");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel input
        JPanel panelInput = new JPanel(new GridLayout(5, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelInput.add(new JLabel("Nama Pemesan:"));
        tfNamaPemesan = new JTextField();
        panelInput.add(tfNamaPemesan);

        panelInput.add(new JLabel("Nama Barang:"));
        tfNamaBarang = new JTextField();
        panelInput.add(tfNamaBarang);

        panelInput.add(new JLabel("Jumlah:"));
        tfJumlah = new JTextField();
        panelInput.add(tfJumlah);

        panelInput.add(new JLabel("Harga Satuan:"));
        tfHarga = new JTextField();
        panelInput.add(tfHarga);

        btnSubmit = new JButton("Submit");
        btnClear = new JButton("Clear");

        panelInput.add(btnSubmit);
        panelInput.add(btnClear);

        add(panelInput, BorderLayout.NORTH);

        // Output area
        taOutput = new JTextArea();
        taOutput.setEditable(false);
        add(new JScrollPane(taOutput), BorderLayout.CENTER);

        // Event handling
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleClear();
            }
        });
    }

    private void handleSubmit() {
        try {
            String namaPemesan = tfNamaPemesan.getText();
            String namaBarang = tfNamaBarang.getText();
            int jumlah = Integer.parseInt(tfJumlah.getText());
            double harga = Double.parseDouble(tfHarga.getText());

            PemesanModel pemesan = new PemesanModel(namaPemesan, namaBarang, jumlah, harga);

            taOutput.append("Nama Pemesan: " + pemesan.getNamaPemesan() + "\n");
            taOutput.append("Nama Barang: " + pemesan.getNamaBarang() + "\n");
            taOutput.append("Jumlah: " + pemesan.getJumlah() + "\n");
            taOutput.append("Harga Satuan: Rp" + pemesan.getHarga() + "\n");
            taOutput.append("Total Harga: Rp" + pemesan.getTotalHarga() + "\n");
            taOutput.append("---------------------------\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid. Harap masukkan angka untuk jumlah dan harga!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleClear() {
        tfNamaPemesan.setText("");
        tfNamaBarang.setText("");
        tfJumlah.setText("");
        tfHarga.setText("");
        taOutput.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormPemesanan().setVisible(true);
        });
    }
}