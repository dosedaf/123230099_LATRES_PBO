import controller.TransactionController;
import model.TransactionModel;
import view.TransactionView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            TransactionModel model = new TransactionModel();
            TransactionView view = new TransactionView();
            new TransactionController(model, view);
            view.setVisible(true);
        });
    }
}
