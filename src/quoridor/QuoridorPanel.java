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

    //wall click sensitivity
    private static final int CLICK_TOLERANCE = 10;

    private boolean[][] horizontalWalls = new boolean[BOARD_SIZE - 1][BOARD_SIZE];
    private boolean[][] verticalWalls = new boolean[BOARD_SIZE][BOARD_SIZE - 1];

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
        drawPlayer(g);
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

    private void drawPlayer(Graphics g){
        g.setColor(Color.GREEN);
        g.fillOval(CELL_SIZE*4 ,CELL_SIZE*4,25,25);
    }

    public void addWall(int x, int y, boolean isHorizontal) {
        walls.add(new Wall(x, y, isHorizontal));
        repaint();
    }

    private void handleMouseClick(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        // Check if click is close to a vertical line
        int cellX = x / CELL_SIZE;
        int cellY = y / CELL_SIZE;
        
        if (isCloseToVerticalLine(x)) {
            // Clicked near a vertical line
            if (canPlaceVerticalWall(cellX, cellY)) {
                placeVerticalWall(cellX, cellY);
                addWall(cellX, cellY,false);
            }
            
            System.out.println("Clicked Vertical Wall" + "(" + cellX + "," + cellY + ")");
        } else if (isCloseToHorizontalLine(y)) {
            // Clicked near a horizontal line
            if (canPlaceHorizontalWall(cellX, cellY)) {
                placeHorizontalWall(cellX, cellY);
                addWall(cellX, cellY, true);
            }
            
            System.out.println("Clicked Horizontal Wall" + "(" + cellX + "," + cellY + ")");

        } else {
            // Clicked inside a cell, handle player movement or other actions
            System.out.println("Clicked " + "(" + cellX + "," + cellY + ")");
        }
    }
    
    private boolean isCloseToVerticalLine(int x) {
        return Math.abs(x % CELL_SIZE) < CLICK_TOLERANCE;
    }
    
    private boolean isCloseToHorizontalLine(int y) {
        return Math.abs(y % CELL_SIZE) < CLICK_TOLERANCE;
    }

    private boolean canPlaceHorizontalWall(int x,int y){
        // Check board boundaries
        if (x < 0 || x >= BOARD_SIZE - 1 || y < 0 || y >= BOARD_SIZE - 1) {
            return false;
        }

        // Check for overlap with existing walls
        if (horizontalWalls[y][x] || (x > 0 && horizontalWalls[y][x-1]) || (x < BOARD_SIZE - 2 && horizontalWalls[y][x+1])) {
            return false;
        }
        if (verticalWalls[y][x] || verticalWalls[y][x+1] || verticalWalls[y-1][x+1]) {
            return false;
        }

        // Temporarily place the wall
        // horizontalWalls[y][x] = true;

        // // Check if all players still have a path to their goal
        // boolean pathExists = checkPathsExist();

        // // Remove the temporary wall
        // horizontalWalls[y][x] = false;

        // return pathExists;
        return true;
    }

    private boolean canPlaceVerticalWall(int x,int y){
        // Check board boundaries
        if (x < 0 || x >= BOARD_SIZE - 1 || y < 0 || y >= BOARD_SIZE - 1) {
            return false;
        }

        // Check for overlap with existing walls
        if (verticalWalls[y][x] || (y > 0 && verticalWalls[y-1][x]) || (y < BOARD_SIZE - 2 && verticalWalls[y+1][x])) {
            return false;
        }
        if (horizontalWalls[y][x] || horizontalWalls[y+1][x] || horizontalWalls[y-1][x+1]) {
            return false;
        }

        // // Temporarily place the wall
        // verticalWalls[y][x] = true;

        // // Check if all players still have a path to their goal
        // boolean pathExists = checkPathsExist();

        // // Remove the temporary wall
        // verticalWalls[y][x] = false;

        //return pathExists;
        return true;
    }

    private void placeHorizontalWall(int x,int y){
        horizontalWalls[y][x] = true;
    }

    private void placeVerticalWall(int x,int y){
        verticalWalls[y][x] = true;
    }


}
