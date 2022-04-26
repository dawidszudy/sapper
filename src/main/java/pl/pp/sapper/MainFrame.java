package pl.pp.sapper;

import javax.swing.*;
import java.awt.*;

import static pl.pp.sapper.GameData.*;
import static pl.pp.sapper.Utils.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sapper");
        setBounds(100, 100, 600, 400);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(sizeY, sizeX));


        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                ButtonField buttonField = new ButtonField(j, i);
                fieldsPanel.add(buttonField);
                buttonFieldList.add(buttonField);
            }
        }


        generateBoard();

        JPanel controlPanel = new JPanel();

        controlPanel.setPreferredSize(new Dimension(120, 0));

        controlPanel.setLayout(new GridLayout(4, 1));

        bombInfoLabel = new JLabel(0 + "/" + bombAmounts, JLabel.RIGHT);
        timeInfoLabel = new JLabel("00:00", JLabel.RIGHT);
        JButton resetButton = new JButton("Reset");
        stopButton = new JButton("Stop");


        resetButton.addActionListener(actionEvent -> {
            int resultReset = JOptionPane.showConfirmDialog
                    (this, "Do you want reset the game?", "Game reset", JOptionPane.YES_NO_OPTION);
            if ( resultReset == 0 ) {
                resetGame();
            }
        });


        stopButton.addActionListener(actionEvent -> {
            if ( gameRunning ) {
                timer.stop();
                stopButton.setText("Resume");
                gameRunning = false;
                buttonFieldList.forEach(ButtonField::stopShow);
            } else {
                timer.start();
                stopButton.setText("Stop");
                gameRunning = true;
                buttonFieldList.forEach(ButtonField::startShow);
            }
        });


        timer = new Timer(1000, actionEvent -> {
            time++;
            updateTimeLabel();
        });

        stopButton.setEnabled(false);

        controlPanel.add(bombInfoLabel);
        controlPanel.add(timeInfoLabel);
        controlPanel.add(resetButton);
        controlPanel.add(stopButton);

        add(fieldsPanel);
        add(controlPanel, BorderLayout.EAST);
    }

}