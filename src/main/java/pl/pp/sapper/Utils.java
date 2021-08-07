package pl.pp.sapper;

import java.util.Collections;

import static pl.pp.sapper.GameData.*;

public class Utils {

    public static void resetGame() {

        timer.stop();
        time = 0;
        stopButton.setEnabled(false);
        updateTimeLabel();

        buttonFieldList.forEach(ButtonField::reset);
        updateBombInfo();
        generateBoard();
    }


    public static void generateBoard() {

        Collections.shuffle(buttonFieldList);

        buttonFieldList.stream()
                .limit(bombAmounts)
                .forEach(ButtonField::setAsBomb);

        buttonFieldList.stream()
                .filter(field -> !field.isBomb())
                .forEach(field -> field.calculateNeighborBombAmount(buttonFieldList));
    }


    public static boolean isWin() {
        return buttonFieldList.stream()
                .filter(field -> !field.isBomb())
                .allMatch(ButtonField::isClicked);
    }


    public static long markedAmount() { //zliczenie zaznaczonych bomb
        return buttonFieldList.stream().filter(ButtonField::isMarked).count();
    }

    public static void updateBombInfo() {   //update informacji ile bomb zaznaczonych
        bombInfoLabel.setText(markedAmount() + "/" + bombAmounts);
    }


    public static void updateTimeLabel() {
        int seconds = time % 60;
        int minutes = time / 60;

        StringBuilder timeString = new StringBuilder();
        if ( minutes <= 9 ) {
            timeString.append("0");
        }
        timeString.append(minutes);
        timeString.append(":");
        if ( seconds <= 9 ) {
            timeString.append("0");
        }

        timeString.append(seconds);

        timeInfoLabel.setText(timeString.toString());

    }
}
