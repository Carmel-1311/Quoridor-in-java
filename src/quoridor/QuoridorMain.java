package quoridor;
import javax.swing.*;

public class QuoridorMain extends JFrame {
    private QuoridorPanel gamePanel;

    public QuoridorMain() {
        setTitle("Quoridor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        gamePanel = new QuoridorPanel();
        add(gamePanel);

        pack();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuoridorMain game = new QuoridorMain();
            game.setVisible(true);
            
        });
    }
}