package desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Gui extends JFrame {
    private JFrame frame;
    private JTabbedPane tabbedPane;

    public void createGUI() {
        frame = new JFrame("Desktop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setPreferredSize(new Dimension(1200, 600));
        frame.setSize(1200, 600);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setSize(new Dimension(800, 600));

        tabbedPane = new JTabbedPane();
        tabbedPane.setSize(new Dimension(800, 600));

        tabbedPane.add("Login", new LoginForm().getPanel());
        tabbedPane.add("Categories", new CategoryForm().getPanel());

        GridBagConstraints tbpConstraint = new GridBagConstraints();
        tbpConstraint.fill = GridBagConstraints.BOTH;
        tbpConstraint.gridwidth = GridBagConstraints.REMAINDER;
        tbpConstraint.weighty = 2;

        JButton refresh = new JButton("refresh");

        refresh.addActionListener(getRefreshActionListener(panel));

        panel.add(tabbedPane, tbpConstraint);
        frame.add(panel);
        frame.setVisible(true);

        JFrame.setDefaultLookAndFeelDecorated(true);
    }

    private ActionListener getRefreshActionListener(final JPanel panel) {
        return e -> {
            int index = tabbedPane.getSelectedIndex();

            tabbedPane.removeAll();
//            tabbedPane.add("products", new ProductForm().getPanel());
//            tabbedPane.add("product table", new ProductListForm().getPanel());

//            panel.add(tabbedPane, FormUtilities.getTabbedPaneConstraints());

            tabbedPane.setSelectedIndex(index);
        };
    }
}
