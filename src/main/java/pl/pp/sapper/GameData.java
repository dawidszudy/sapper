package pl.pp.sapper;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameData {

    public static int sizeX = 8;
    public static int sizeY = 8;
    public static int bombAmounts = 10;

    public static int time = 0;
    public static boolean gameRunning = true;

    public static List<ButtonField> buttonFieldList = new ArrayList<>();
    public static Timer timer;
    public static JLabel bombInfoLabel;
    public static JLabel timeInfoLabel;
    public static JButton stopButton;
    public static JFrame mainFrame = new MainFrame();

}
