package core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import userInterface.BattleshipUI;
import userInterface.Player;

public class BattleshipClient {

    private final Player[] players;
    private final BattleshipUI userInterface;
    private final Player playerOne;
    private final Player playerTwo;
    private final JButton[][] playerOneButtonBoard;
    private final JButton[][] playerTwoButtonBoard;
    private int currentPlayer;

    public BattleshipClient(Player[] playerArray, BattleshipUI userInterface) {
        players = playerArray;
        this.userInterface = userInterface;
        playerOne = players[Constants.PLAYER_ONE];
        playerTwo = players[Constants.PLAYER_TWO];
        playerOneButtonBoard = playerOne.getButtonBoard();
        playerTwoButtonBoard = playerTwo.getButtonBoard();

        play();
    }

    private void setupButtonListeners() {
        // This removes the ability to click to place ships on playerTwoButtonBoard
        // Then adds the ability to click playerTwoButtonBoard to attack playerTwo
        for (int row = 0; row < playerTwo.getRows(); row++) {
            for (int col = 0; col < playerTwo.getCols(); col++) {
                playerTwoButtonBoard[row][col].removeActionListener(playerTwoButtonBoard[row][col].getActionListeners()[0]);
                playerTwoButtonBoard[row][col].addActionListener(new ButtonListener());
            }
        }

        // This removes the ability to click to place ships on playerOneButtonBoard
        // To add a 'Player vs Player' mode, this needs to have a new ButtonListener() added
        for (int row = 0; row < playerOne.getRows(); row++) {
            for (int col = 0; col < playerOne.getCols(); col++) {
                playerOneButtonBoard[row][col].removeActionListener(playerOneButtonBoard[row][col].getActionListeners()[0]);
            }
        }
    }

    public class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int rowClick = (int) button.getClientProperty("row");
            int colClick = (int) button.getClientProperty("col");

            if (playerTwoButtonBoard[rowClick][colClick].getBackground() == Color.red) {
                // Was already a hit; player loses turn for selecting twice
                appendGameStatus(Constants.REPEAT_CLICK);
            } else if (playerTwoButtonBoard[rowClick][colClick].getBackground() == Color.blue) {
                // Was already a miss; player loses turn for selecting twice
                appendGameStatus(Constants.REPEAT_CLICK);
            } else if (playerTwo.isHit(rowClick, colClick)) {
                // A new hit
                playerTwoButtonBoard[rowClick][colClick].setBackground(Color.red);
                // isHit() will handle the HIT or SUNK message
            } else {
                // A new miss
                playerTwoButtonBoard[rowClick][colClick].setBackground(Color.blue);
                appendGameStatus(Constants.MISS);
            }

            // Skips this for the first turn
            if (currentPlayer != Constants.PLAYER_NULL) {
                switchPlayers();
            }
        }
    }

    private void computerPick() {
        Random random = new Random();

        int rowClick = random.nextInt(playerTwo.getRows());
        int colClick = random.nextInt(playerTwo.getCols());

        if (playerOneButtonBoard[rowClick][colClick].getBackground() == Color.red) {
            // Was already a hit; player loses turn for selecting twice
            appendGameStatus(Constants.REPEAT_CLICK);
        } else if (playerOneButtonBoard[rowClick][colClick].getBackground() == Color.blue) {
            // Was already a miss; player loses turn for selecting twice
            appendGameStatus(Constants.REPEAT_CLICK);
        } else if (playerOne.isHit(rowClick, colClick)) {
            // A new hit
            playerOneButtonBoard[rowClick][colClick].setBackground(Color.red);
            // isHit() will handle the HIT or SUNK message
        } else {
            // A new miss
            playerOneButtonBoard[rowClick][colClick].setBackground(Color.blue);
            appendGameStatus(Constants.MISS);
        }

        // Skips this for the first turn
        if (currentPlayer != Constants.PLAYER_NULL) {
            switchPlayers();
        }
    }

    private void switchPlayers() {
        if (checkForWinner()) {
            endGame();
            return;
        }
        
        if (currentPlayer == Constants.PLAYER_ONE) {
            currentPlayer = Constants.PLAYER_TWO;
            appendGameStatus(Constants.PLAYER_TWO_NAME + Constants.NEXT_TURN);
            computerPick();
        } else {
            currentPlayer = Constants.PLAYER_ONE;
            appendGameStatus(Constants.LINE_BREAK);
            appendGameStatus(Constants.PLAYER_ONE_NAME + Constants.NEXT_TURN);
        }
    }

    private Boolean checkForWinner() {
        int shipsSunk = 0;

        for (Player player : players) {
            for (Ship ship : player.getShips()) {
                // Adds 1 to shipSunk count if ship is sunk
                shipsSunk += ship.isShipSunk() ? 1 : 0;
            }

            // Checks to see if all ships have been sunk
            if (shipsSunk == player.getShips().size()) {
                appendGameStatus(player.getPlayerName() + Constants.LOST_GAME);
                JOptionPane.showMessageDialog(null, player.getPlayerName() + Constants.LOST_GAME, Constants.GAME_OVER, JOptionPane.ERROR_MESSAGE);
                return true;
            } else {
                // Reset this value to 0 for the next interation of the loop
                shipsSunk = 0;
            }
        }
        // If the above loop fails to find a winner, then there is no winner yet
        return false;
    }
    
    private void endGame() {
        // Disables clicking on player 
        for (Player player : players) {
            for (int row = 0; row < player.getRows(); row++) {
                for (int col = 0; col < player.getCols(); col++) {
                    player.getButtonBoard()[row][col].setEnabled(false);
                }
            }
        }
    }

    public void appendGameStatus(String string) {
        BattleshipUI.gameStatusTextArea.append(string + "\n");
    }

    private void play() {
        // Disables switchPlayers() until after setButtonListener()
        currentPlayer = Constants.PLAYER_NULL;

        appendGameStatus(Constants.START_GAME);
        appendGameStatus(Constants.LINE_BREAK);
        
        // Sets up the button listeners for the HUMAN player
        setupButtonListeners();
        
        // Sets up the ship layout for the COMPTER player
        playerTwo.autoLayout();

        // Sets up the first player
        // If PLAYER_TWO is first, then computerPick() starts the game
        if (playerOne.getIsFirst()) {
            currentPlayer = Constants.PLAYER_ONE;
            appendGameStatus(Constants.PLAYER_ONE_NAME + Constants.NEXT_TURN);
        } else {
            currentPlayer = Constants.PLAYER_TWO;
            appendGameStatus(Constants.PLAYER_TWO_NAME + Constants.NEXT_TURN);
            computerPick();
        }
    }
    
}
