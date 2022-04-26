package pl.pp.sapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static pl.pp.sapper.GameData.*;
import static pl.pp.sapper.Utils.*;

public class ButtonField extends JButton {
    private int coordX;
    private int coordY;
    private int neighborBombAmount;
    private static boolean stopRoll = false;

    private boolean bomb;
    private boolean clicked;
    private boolean marked;

    private final String bombCharacter = "!";
    private final String questionMark = "?";
    private final String emptyMark = "";
    private String signField = "";


    public ButtonField(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
        bomb = false;


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                int button = mouseEvent.getButton();

                if ( button == MouseEvent.BUTTON1 ) {
                    fieldClick();

                } else if ( button == MouseEvent.BUTTON3 ) {
                    markField();
                }

            }
        });

    }


    private void markField() {
        if ( !gameRunning ) {
            return;
        }

        if ( !clicked ) {

            switch (signField) {
                case emptyMark:
                    marked = true;
                    signField = bombCharacter;
                    setText(bombCharacter);
                    break;
                case bombCharacter:
                    marked = false;
                    signField = questionMark;
                    setText(questionMark);
                    break;
                case questionMark:
                    marked = false;
                    signField = emptyMark;
                    setText(emptyMark);
                    break;

            }

        }
        updateBombInfo();
    }


    private void fieldClick() {
        stopRoll = false;
        if ( marked ) {
            return;
        }

        if ( !gameRunning ) {
            return;
        }

        clicked = true;
        timer.start();
        stopButton.setEnabled(true);

        if ( bomb ) {
            timer.stop();
            setText(bombCharacter);
            setBackground(Color.RED);
            JOptionPane.showMessageDialog(mainFrame, "You lost the game!", "Game over", JOptionPane.ERROR_MESSAGE);
            resetGame();
        } else if ( neighborBombAmount == 0 ) {
            setBackground(Color.LIGHT_GRAY);
            setText("");
            rollNoBombFields();
        } else {
            setText(String.valueOf(neighborBombAmount));
            colorField();
        }

        if ( isWin() ) {
            timer.stop();
            stopButton.setEnabled(false);
            JOptionPane.showMessageDialog(mainFrame, "Congratulations! You won!");
            stopRoll = true;
            resetGame();

        }
    }

    private void colorField() {
        if ( neighborBombAmount == 1 ) {
            setBackground(Color.GRAY);
        } else if ( neighborBombAmount == 2 ) {
            setBackground(Color.DARK_GRAY);
        } else if ( neighborBombAmount == 3 ) {
            setBackground(Color.YELLOW);
        } else if ( neighborBombAmount == 4 ) {
            setBackground(Color.ORANGE);
        } else {
            setBackground(Color.MAGENTA);
        }
    }


    public void setAsBomb() {   //metoda ustawiająca pole z bombą
        bomb = true;
    }

    public boolean isBomb() {   //metoda zwracająca bombę
        return bomb;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isMarked() {
        return marked;
    }


    public void reset() {
        clicked = false;
        bomb = false;
        marked = false;
        setText(emptyMark);
        setBackground(null);
    }


    public void startShow() {
        setBackground(null);

        if ( clicked ) {
            fieldClick();
        }

        if ( marked && signField.equals(bombCharacter) ) {
            setText(bombCharacter);
        }
        if ( !marked && signField.equals(questionMark) ) {
            setText(questionMark);
        }
    }


    public void stopShow() {
        setBackground(Color.GREEN);
        setText(emptyMark);
    }


    public boolean isNeighbor(ButtonField buttonField) {
        return Math.abs(coordX - buttonField.coordX) <= 1 && Math.abs(coordY - buttonField.coordY) <= 1;
    }


    public void calculateNeighborBombAmount(List<ButtonField> buttonFieldList) {
        neighborBombAmount = (int) buttonFieldList.stream()
                .filter(this::isNeighbor)
                .filter(ButtonField::isBomb)
                .count();

    }


    public void rollNoBombFields() {
        buttonFieldList.stream()
                .filter(this::isNeighbor)
                .filter(field -> !field.clicked)
                .filter(field -> !field.stopRoll)
                .forEach(ButtonField::fieldClick);
    }

}