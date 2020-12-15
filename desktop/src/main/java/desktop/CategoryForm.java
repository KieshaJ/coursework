package desktop;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CategoryForm {
    private JPanel panel;
    private JTable table;
    private JScrollPane scrollPane;

    public JPanel getPanel() {
        panel = new JPanel(new GridLayout(1, 1));

        String[] columns = {"Category Name", "Earnings", "Expenses", "Balance"};
        table = new JTable(new String[][]{}, columns);

        table.setEnabled(false);

        scrollPane = new JScrollPane(table);

        panel.add(scrollPane);
        panel.setBorder(new TitledBorder("Categories"));

        return panel;
    }
}
