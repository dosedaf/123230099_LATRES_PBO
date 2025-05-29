package controller;

import java.util.ArrayList;

import model.TransactionModel;
import model.TransactionModel.Transaction;
import view.TransactionView;

public class TransactionController {
    private TransactionModel model;
    private TransactionView view;

    public TransactionController(TransactionModel model, TransactionView view) {
        this.model = model;
        this.view = view;
        view.setController(this);
        loadTransactionList();
    }

    public void loadTransactionList() {
        ArrayList<Transaction> transactions = model.getAllTransactions();
        view.showTransactions(transactions);     
    }

    public void addTransaction(String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
        if (model.addTransaction(namaPelanggan, namaObat, hargaSatuan, jumlahBeli)) {
            view.showMessage("Transaksi berhasil ditambahkan!");
            loadTransactionList();
        } else {
            view.showMessage("Gagal menambahkan transaksi.");
        }
    }

    public void deleteTransaction(int id) {
        if (model.deleteTransaction(id)) {
            view.showMessage("Transaksi berhasil dihapus!");
            loadTransactionList();
        } else {
            view.showMessage("Gagal menghapus transaksi.");
        }
    }

    public void updateTransaction(int id, String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
        if (model.updateTransaction(id, namaPelanggan, namaObat, hargaSatuan, jumlahBeli)) {
            view.showMessage("Transaksi berhasil diperbarui!");
            loadTransactionList();
        } else {
            view.showMessage("Gagal memperbarui transaksi.");
        }
    }
}
