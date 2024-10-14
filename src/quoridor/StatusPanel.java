package quoridor;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel{
    //TODO : make it better GUI 
    private JLabel timeLabel;
    private JLabel wallsLabel;
    private int elapsedTime = 0;

    public void updateStatusPanel() {
        int minutes = elapsedTime / 60;
        int seconds = elapsedTime % 60;
        timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
        //TODO : wall count implement
        wallsLabel.setText(String.format("Player 1 Walls: %d | Player 2 Walls: %d", 10, 10));
    }



    public StatusPanel(){
        setLayout(new BorderLayout());
        timeLabel = new JLabel("Time: 00:00");
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        wallsLabel = new JLabel("Player 1 Walls: 10 | Player 2 Walls: 10");
        add(timeLabel, BorderLayout.NORTH);
        add(wallsLabel, BorderLayout.EAST);

        Timer timer = new Timer(1000, e -> {
            elapsedTime++;
            updateStatusPanel();
        });
        timer.start();
    }
    
}
