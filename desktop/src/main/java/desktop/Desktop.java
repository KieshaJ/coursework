package desktop;

import javax.swing.*;

public class Desktop{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui().createGUI());
    }
}
