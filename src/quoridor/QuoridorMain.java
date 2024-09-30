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

    public void addWall(int x, int y, boolean isHorizontal) {
        gamePanel.addWall(x, y, isHorizontal);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuoridorMain game = new QuoridorMain();
            game.setVisible(true);
            
            // Example: Add some walls
            game.addWall(1, 1, true);
            game.addWall(3, 4, false);
        });
    }
}