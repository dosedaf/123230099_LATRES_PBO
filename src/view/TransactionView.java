package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.TransactionController;
import model.TransactionModel;

public class TransactionView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField fieldNamaPelanggan;
    private JTextField fieldNamaObat;
    private JTextField fieldHargaSatuan;
    private JTextField fieldJumlahBeli;

    private JButton addButton, deleteButton;

    private TransactionController controller;

    public TransactionView() {
        setTitle("Aplikasi Transaksi Penjualan Obat");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Tambah Transaksi"));

        inputPanel.add(new JLabel("Nama Pelanggan:"));
        fieldNamaPelanggan = new JTextField();
        inputPanel.add(fieldNamaPelanggan);

        inputPanel.add(new JLabel("Nama Obat:"));
        fieldNamaObat = new JTextField();
        inputPanel.add(fieldNamaObat);

        inputPanel.add(new JLabel("Harga Satuan:"));
        fieldHargaSatuan = new JTextField();
        inputPanel.add(fieldHargaSatuan);

        inputPanel.add(new JLabel("Jumlah Beli:"));
        fieldJumlahBeli = new JTextField();
        inputPanel.add(fieldJumlahBeli);

        addButton = new JButton("Tambah");
        deleteButton = new JButton("Hapus");
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{
            "ID", "Nama Pelanggan", "Nama Obat", "Harga Satuan", "Jumlah Beli", "Total Bayar"
        }, 0);

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener((ActionEvent e) -> {
            try {
                String namaPelanggan = fieldNamaPelanggan.getText().trim();
                String namaObat = fieldNamaObat.getText().trim();
                int hargaSatuan = Integer.parseInt(fieldHargaSatuan.getText().trim());
                int jumlahBeli = Integer.parseInt(fieldJumlahBeli.getText().trim());

                if (namaPelanggan.isEmpty() || namaObat.isEmpty()) {
                    showMessage("Isi semua field dengan benar.");
                    return;
                }

                controller.addTransaction(namaPelanggan, namaObat, hargaSatuan, jumlahBeli);

                // Kosongkan field
                fieldNamaPelanggan.setText("");
                fieldNamaObat.setText("");
                fieldHargaSatuan.setText("");
                fieldJumlahBeli.setText("");

            } catch (NumberFormatException ex) {
                showMessage("Harga dan jumlah beli harus berupa angka.");
            }
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                controller.deleteTransaction(id);
            } else {
                showMessage("Pilih transaksi yang ingin dihapus.");
            }
        });
    }

    public void setController(TransactionController controller) {
        this.controller = controller;
    }

    public void showTransactions(ArrayList<TransactionModel.Transaction> transactions) {
        tableModel.setRowCount(0); // clear table
        for (TransactionModel.Transaction t : transactions) {
            tableModel.addRow(new Object[]{
                t.id,
                t.namaPelanggan,
                t.namaObat,
                t.hargaSatuan,
                t.jumlahBeli,
                t.getTotalBayar()
            });
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
