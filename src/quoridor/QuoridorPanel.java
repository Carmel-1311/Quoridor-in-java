package quoridor;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class QuoridorPanel extends JPanel{
    private List<Wall> walls;
    private static final int BOARD_SIZE = 9;
    private static final int CELL_SIZE = 50;
    private static final int WALL_THICKNESS = 8;

    public QuoridorPanel() {
        walls = new ArrayList<>();
        setPreferredSize(new Dimension(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE));
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });
     
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawWalls(g);
    }

    private void drawBoard(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i <= BOARD_SIZE; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, BOARD_SIZE * CELL_SIZE);
            g.drawLine(0, i * CELL_SIZE, BOARD_SIZE * CELL_SIZE, i * CELL_SIZE);
        }
    }

    private void drawWalls(Graphics g) {
        g.setColor(Color.RED);
        for (Wall wall : walls) {
            int x = wall.x * CELL_SIZE;
            int y = wall.y * CELL_SIZE;
            if (wall.isHorizontal) {
                g.fillRect(x, y - WALL_THICKNESS / 2, CELL_SIZE * 2, WALL_THICKNESS);
            } else {
                g.fillRect(x - WALL_THICKNESS / 2, y, WALL_THICKNESS, CELL_SIZE * 2);
            }
        }
    }

    public void addWall(int x, int y, boolean isHorizontal) {
        walls.add(new Wall(x, y, isHorizontal));
        repaint();
    }

    private void handleMouseClick(MouseEvent e) {
        int x = e.getX() / CELL_SIZE;
        int y = e.getY() / CELL_SIZE;
        
        System.out.println("Clicked " + x + "," + y);
    }
    

}
