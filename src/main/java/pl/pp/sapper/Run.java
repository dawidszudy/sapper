package pl.pp.sapper;

import java.awt.*;

import static pl.pp.sapper.GameData.mainFrame;

public class Run {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            mainFrame.setVisible(true);
        });
    }
}
