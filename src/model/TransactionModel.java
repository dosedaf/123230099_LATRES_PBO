package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionModel {

    public static class Transaction {
        public int id;
        public String namaPelanggan;
        public String namaObat;
        public int hargaSatuan;
        public int jumlahBeli;

        public Transaction(int id, String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
            this.id = id;
            this.namaPelanggan = namaPelanggan;
            this.namaObat = namaObat;
            this.hargaSatuan = hargaSatuan;
            this.jumlahBeli = jumlahBeli;
        }

        public int getTotalBayar() {
            int total = hargaSatuan * jumlahBeli;
            if (jumlahBeli > 5) {
                total -= total * 0.1;
            }
            return total;
        }
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM transactions";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(new Transaction(
                    rs.getInt("id"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("nama_obat"),
                    rs.getInt("harga_satuan_obat"),
                    rs.getInt("jumlah_beli")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public boolean addTransaction(String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO transactions (nama_pelanggan, nama_obat, harga_satuan_obat, jumlah_beli) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, namaPelanggan);
            stmt.setString(2, namaObat);
            stmt.setInt(3, hargaSatuan);
            stmt.setInt(4, jumlahBeli);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTransaction(int id) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM transactions WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTransaction(int id, String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE transactions SET nama_pelanggan = ?, nama_obat = ?, harga_satuan_obat = ?, jumlah_beli = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, namaPelanggan);
            stmt.setString(2, namaObat);
            stmt.setInt(3, hargaSatuan);
            stmt.setInt(4, jumlahBeli);
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
