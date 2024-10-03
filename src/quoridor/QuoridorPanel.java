package quoridor;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class QuoridorPanel extends JPanel{
    private List<Wall> walls;
    private Player player1;
    private Player player2;
    private Player currentPlayer; // ผู้เล่นคนที่กำลังมีสิทธิ์เดิน
    private boolean gameEnded = false;
	
    
    private static final int BOARD_SIZE = 9;
    private static final int CELL_SIZE = 50;
    private static final int WALL_THICKNESS = 8;

    //wall click sensitivity
    private static final int CLICK_TOLERANCE = 10;

    private boolean[][] horizontalWalls = new boolean[BOARD_SIZE][BOARD_SIZE];
    private boolean[][] verticalWalls = new boolean[BOARD_SIZE][BOARD_SIZE];

	

    public QuoridorPanel() {
        walls = new ArrayList<>();
        player1 = new Player(4,0);
        player2 = new Player(4,8); 
        currentPlayer = player1; // เริ่มต้นที่ player1
        
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
        drawPlayers(g);
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
    private void drawPlayers(Graphics g) {
    	// g.fillOval วงกลม
    	//(ตำเเหน่ง x,ตำเเหน่ง y,wihth,height)   
    	//(ตำเเหน่ง x,y ไว้เช็คตรงกลางของช่องเดินเช่น(x=4,cell_size=50 ===> 4*50/50/4 = 212.5คือตรงกลางเเกนxที่ตัวเดินวางอยู่))
        g.setColor(Color.BLUE);
        g.fillRect(player1.x * CELL_SIZE + CELL_SIZE / 4 , player1.y * CELL_SIZE + CELL_SIZE / 4, CELL_SIZE / 2, CELL_SIZE / 2);
        
        g.setColor(Color.GREEN);
        g.fillRect(player2.x * CELL_SIZE + CELL_SIZE / 4, player2.y * CELL_SIZE + CELL_SIZE / 4, CELL_SIZE / 2, CELL_SIZE / 2);
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
 	if (gameEnded) return;
        if (e.getButton() == MouseEvent.BUTTON1) {
        	// Clicked near a vertical line
            if (isCloseToVerticalLine(x)) {
                if (canPlaceVerticalWall(cellX, cellY)) {
                    placeVerticalWall(cellX, cellY);
                    addWall(cellX, cellY, false);
                    switchPlayer(); // สลับตา
                }
                System.out.println("Clicked Vertical Wall" + "(" + cellX + "," + cellY + ")");
             // Clicked near a horizontal line
            } else if (isCloseToHorizontalLine(y)) {
                if (canPlaceHorizontalWall(cellX, cellY)) {
                    placeHorizontalWall(cellX, cellY);
                    addWall(cellX, cellY, true);
                    switchPlayer(); // สลับตา
                }
                System.out.println("Clicked Horizontal Wall" + "(" + cellX + "," + cellY + ")");
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            // คลิกขวา: เดินผู้เล่น
            if (isMoveValid(currentPlayer, cellX, cellY)) {
                currentPlayer.x = cellX;
                currentPlayer.y = cellY;
                switchPlayer(); // สลับตา
                repaint(); // วาดใหม่หลังเดิน
                System.out.println("Player moved to " + "(" + cellX + "," + cellY + ")");
            } else {
                System.out.println("Invalid move");
            }
        }
	if (currentPlayer == player1 && cellY == 0) {
        	JOptionPane.showMessageDialog(this, "Player 1 Wins!");
        	gameEnded = true;
        } 
    	
        else if (currentPlayer == player2 && cellY == 8) {
        	JOptionPane.showMessageDialog(this, "Player 2 Wins!");
        	gameEnded = true;
        }
    }
   
    private boolean isMoveValid(Player player, int x, int y) {
        // ตรวจสอบว่าตำแหน่งที่คลิกอยู่ใกล้ตำแหน่งปัจจุบันของผู้เล่นและไม่มีสิ่งกีดขวาง
        int dx = Math.abs(player.x - x);
        int dy = Math.abs(player.y - y);
        
        // เดินได้เฉพาะในแนวตั้งหรือแนวนอนที่ระยะ 1 ช่อง
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    private void switchPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
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
        if (x < 0 || x >= BOARD_SIZE - 1 || y <= 0 || y > BOARD_SIZE - 1) {
            return false;
        }

        // Check for overlap with existing walls
        if (horizontalWalls[y][x] || (x > 0 && horizontalWalls[y][x-1]) || (x < BOARD_SIZE - 2 && horizontalWalls[y][x+1])) {
            return false;
        }
        if ( verticalWalls[y][x+1] || verticalWalls[y-1][x+1] ) {
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
        if (x <= 0 || x > BOARD_SIZE - 1 || y < 0 || y >= BOARD_SIZE - 1) {
            return false;
        }

        // Check for overlap with existing walls
        if (verticalWalls[y][x] || (y > 0 && verticalWalls[y-1][x]) || (y < BOARD_SIZE - 2 && verticalWalls[y+1][x])) {
            return false;
        }
        if (horizontalWalls[y+1][x] || horizontalWalls[y+1][x-1]) {
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
