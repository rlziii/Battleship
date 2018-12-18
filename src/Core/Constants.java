package Core;

import java.awt.Color;

public class Constants {

    public static final int BATTLESHIP_LENGTH = 4;
    public static final String BATTLESHIP_NAME = "Battleship";
    public static final int BATTLESHIP_INDEX = 0;

    public static final int CARRIER_LENGTH = 5;
    public static final String CARRIER_NAME = "Carrier";
    public static final int CARRIER_INDEX = 1;

    public static final int DESTROYER_LENGTH = 3;
    public static final String DESTROYER_NAME = "Destroyer";
    public static final int DESTROYER_INDEX = 2;

    public static final int PATROLBOAT_LENGTH = 2;
    public static final String PATROLBOAT_NAME = "Patrol Boat";
    public static final int PATROLBOAT_INDEX = 3;

    public static final int SUBMARINE_LENGTH = 3;
    public static final String SUBMARINE_NAME = "Submarine";
    public static final int SUBMARINE_INDEX = 4;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    public static final int PLAYER_ONE = 0;
    public static final int PLAYER_TWO = 1;
    public static final int PLAYER_NULL = 2;

    public static final int HUMAN = 0;
    public static final int COMPUTER = 1;
    
    public static final int BOARD_LENGTH = 10;

    public static final String START_GAME = "Battleship...START!\n";
    public static final String NEXT_TURN = ", your turn!";
    public static final String LOST_GAME = " has lost the game!";
    public static final String REPEAT_CLICK = "You already clicked here!\n";
    public static final String HIT = "HIT!\n";
    public static final String MISS = "Miss.\n";
    public static final String LINE_BREAK = "-----\n";
    public static final String APOSTROPHE_S = "'s ";
    public static final String SUNK = "SUNK ";
    public static final String EXCLAMATION = "!\n";
    public static final String GAME_OVER = "Game Over";
    
    public static final String PLAYER_ONE_NAME = "Player 1";
    public static final String PLAYER_TWO_NAME = "Player 2";
    
    public static final String[] LEVEL_ARRAY = {"Normal", "Ridiculously Hard"};
    public static final String[] LAYOUT_ARRAY = {"Manual", "Automatic"};
    public static final String[] ROW_LETTERS_ARRAY = {" ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    public static final String[] COL_NUMBERS_ARRAY = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public static final String[] SHIP_NAMES_ARRAY = {"Battleship", "Carrier", "Destroyer", "Patrol Boat", "Submarine"};
    public static final String[] DIRECTION_NAMES_ARRAY = {"Horizontal", "Vertical"};
    
    public static final Color[] COLOR_ARRAY =  {Color.cyan, Color.green, Color.yellow, Color.magenta, Color.pink, Color.orange, Color.white};
    public static final String[] COLOR_NAME_ARRAY = {"Cyan",     "Green",     "Yellow",     "Magenta",     "Pink",     "Orange",     "White"};
}
