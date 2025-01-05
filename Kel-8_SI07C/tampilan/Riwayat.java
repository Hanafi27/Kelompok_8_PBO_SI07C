package tampilan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.*;

public class Riwayat extends JFrame {
    private JTable tableBarang, tableLokasi;
    private JScrollPane scrollPaneBarang, scrollPaneLokasi;

    private Connection conn;  // Koneksi database

    public Riwayat() {
        // Inisialisasi koneksi
        conn = new koneksi().connect();

        setTitle("Menu Riwayat");
        setLayout(new BorderLayout());

        // Tabel Barang
        tableBarang = new JTable();
        scrollPaneBarang = new JScrollPane(tableBarang);
        tableBarang.setFillsViewportHeight(true);
        
        // Tabel Lokasi
        tableLokasi = new JTable();
        scrollPaneLokasi = new JScrollPane(tableLokasi);
        tableLokasi.setFillsViewportHeight(true);

        // Menambahkan komponen ke frame
        add(scrollPaneBarang, BorderLayout.CENTER);
        add(scrollPaneLokasi, BorderLayout.SOUTH);

        // Mengatur ukuran frame
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Menampilkan data barang dan lokasi
        displayBarangData();
        displayLokasiData();
    }

    // Menampilkan data barang
    private void displayBarangData() {
        Object[] baris = {"Kode Barang", "Nama Barang", "Kategori Barang", "Merek", "Ukuran"};
        DefaultTableModel tabmode = new DefaultTableModel(null, baris);

        try {
            String sql = "SELECT * FROM mst_barang ORDER BY kd_barang ASC";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);

            while (hasil.next()) {
                tabmode.addRow(new Object[] {
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(3),
                        hasil.getString(4),
                        hasil.getString(5)
                });
            }
            tableBarang.setModel(tabmode);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Data gagal dipanggil: " + e.getMessage());
        }
    }

    // Menampilkan data lokasi
    private void displayLokasiData() {
        Object[] baris = {"Kode Lokasi", "Nama Lokasi"};
        DefaultTableModel tabmode = new DefaultTableModel(null, baris);

        try {
            String sql = "SELECT * FROM mst_lokasi ORDER BY kd_lokasi ASC";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);

            while (hasil.next()) {
                tabmode.addRow(new Object[] {
                        hasil.getString(1),
                        hasil.getString(2)
                });
            }
            tableLokasi.setModel(tabmode);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Data gagal dipanggil: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Riwayat());
    }

    // Kelas koneksi untuk mengatur koneksi ke database
    private static class koneksi {
        public Connection connect() {
            try {
                // Gantilah dengan konfigurasi koneksi database Anda
                String url = "jdbc:mysql://localhost/projek_visual";
                String user = "root";
                String password = "";
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.getMessage());
                return null;
            }
        }
    }
}
