package UI;

import Core.Battleship;
import Core.Carrier;
import Core.Constants;
import Core.Destroyer;
import Core.PatrolBoat;
import Core.Ship;
import Core.Submarine;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Player {

    private final String userName;
    private Color shipColor;
    private boolean isFirst;

    private JButton[][] buttonBoard;

    private int currentShip;
    private int currentShipLength;
    private int currentDirection;

    private ArrayList<Ship> ships;

    private final BattleshipUI parent;

    public Player(BattleshipUI parent, String userName) {
        this.parent = parent;
        this.userName = userName;
        initObjects();
        initComponents();

        // Defaults Player 1 to isFirst
        if (userName.equals(Constants.PLAYER_ONE_NAME))
            isFirst = true;
    }

    private void initObjects() {
        Battleship battleship = new Battleship();
        Carrier carrier = new Carrier();
        Destroyer destroyer = new Destroyer();
        PatrolBoat patrolBoat = new PatrolBoat();
        Submarine submarine = new Submarine();

        ships = new ArrayList<>();
        ships.add(Constants.BATTLESHIP_INDEX, battleship);
        ships.add(Constants.CARRIER_INDEX, carrier);
        ships.add(Constants.DESTROYER_INDEX, destroyer);
        ships.add(Constants.PATROLBOAT_INDEX, patrolBoat);
        ships.add(Constants.SUBMARINE_INDEX, submarine);

        // An arbitrary default color
        shipColor = Color.cyan;

        // An arbitrary default ship
        currentShip = Constants.BATTLESHIP_INDEX;
        currentShipLength = Constants.BATTLESHIP_LENGTH;
    }

    public Boolean isHit(int targetRow, int targetCol) {
        int shipStartRow;
        int shipStopRow;
        int shipStartCol;
        int shipStopCol;

        for (Ship searchShip : getShips()) {
            shipStartRow = searchShip.getShipStartRow();
            shipStopRow = searchShip.getShipStopRow();
            shipStartCol = searchShip.getShipStartCol();
            shipStopCol = searchShip.getShipStopCol();
            
            for (int searchRow = shipStartRow; searchRow <= shipStopRow; searchRow++) {
                for (int searchCol = shipStartCol; searchCol <= shipStopCol; searchCol++) {

                    // Checks if a ship is hit
                    if (searchRow == targetRow && searchCol == targetCol) {
                        searchShip.shipIsHit();

                        // Then checks if a ship is sunk
                        if (searchShip.isShipSunk())
                            parent.getClient().appendGameStatus(Constants.SUNK + userName + Constants.APOSTROPHE_S + searchShip.getShipName() + Constants.EXCLAMATION);
                        else
                            parent.getClient().appendGameStatus(Constants.HIT);
                        
                        // Returns true for isHit()
                        return true;
                    }
                }
            }
        }
        
        // Otherwise, it was a miss
        return false;
    }

    public void updateShipColor(Color previousColor, Color newColor) {
        setShipColor(newColor);
        
        // Goes through the player's buttonBoard to update colors
        for (int row = 0; row < getBoardLength(); row++)
            for (int col = 0; col < getBoardLength(); col++)
                if (getButtonBoard()[row][col].getBackground() == previousColor)
                    getButtonBoard()[row][col].setBackground(getShipColor());
    }

    public void autoLayout() {
        Random random = new Random();

        // This is set a non-null color
        // This allows isOccupied() to work when called by placeShip()
        setShipColor(Color.black);

        for (Ship ship : getShips()) {
            // Sets all ships to not placed to ensure all ships go through autoLayout()
            ship.setShipPlaced(false);
            
            setCurrentShip(getShips().indexOf(ship));
            setCurrentShipLength(ship.getShipLength());

            int rowClick;
            int colClick;

            // Try random values and see if they are valid ship placements
            do {
                rowClick = random.nextInt(getBoardLength());
                colClick = random.nextInt(getBoardLength());
                setCurrentDirection(random.nextInt(2));

                if (isValid(rowClick, colClick) && !isOccupied(rowClick, colClick)) {
                    placeShip(rowClick, colClick);
                    ship.setShipPlaced(true);
                }

                // Otherwise, continue until a set of random values are valid
            } while (!ship.isShipPlaced());
        }

        // shipColor is now set to null so the HUMAN player cannot see ships
        updateShipColor(getShipColor(), null);
    }

    private void initComponents() {
        buttonBoard = new JButton[getBoardLength()][getBoardLength()];

        // Create the buttonBoard
        for (int row = 0; row < getBoardLength(); row++) {
            for (int col = 0; col < getBoardLength(); col++) {
                buttonBoard[row][col] = new JButton();
                getButtonBoard()[row][col].putClientProperty("row", row);
                getButtonBoard()[row][col].putClientProperty("col", col);
                getButtonBoard()[row][col].setBackground(null);
                getButtonBoard()[row][col].setOpaque(true);
                buttonBoard[row][col].addActionListener(new BoardListener());
            }
        }
    }

    class BoardListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton button = (JButton) e.getSource();
                int rowClick = (int) button.getClientProperty("row");
                int colClick = (int) button.getClientProperty("col");

                if (!isValid(rowClick, colClick))
                    JOptionPane.showMessageDialog(null, "Ship will not fit", "Try Again", JOptionPane.ERROR_MESSAGE);
                else if (isOccupied(rowClick, colClick))
                    JOptionPane.showMessageDialog(null, "One or more spaces are occupied", "Try Again", JOptionPane.ERROR_MESSAGE);
                else
                    placeShip(rowClick, colClick);
            }

            // Checks to see if all ships have been placed
            isReadyToDeploy();
        }
    }

    private boolean isValid(int rowClick, int colClick) {
        // Checks to see if desired space is in the bounds of the board
        if (getCurrentDirection() == Constants.VERTICAL) {
            return (rowClick + getCurrentShipLength()) <= getBoardLength();
        } else if (getCurrentDirection() == Constants.HORIZONTAL) {
            return (colClick + getCurrentShipLength()) <= getBoardLength();
        }

        // Otherwise, placement is valid
        return true;
    }

    private boolean isOccupied(int rowClick, int colClick) {
        // Checks to see if the desired space is occupied; returns true if so
        if (getCurrentDirection() == Constants.VERTICAL) {
            for (int row = rowClick; row < rowClick + getCurrentShipLength(); row++)
                if (getButtonBoard()[row][colClick].getBackground() == getShipColor())
                    return true;
        } else if (getCurrentDirection() == Constants.HORIZONTAL) {
            for (int col = colClick; col < colClick + getCurrentShipLength(); col++)
                if (getButtonBoard()[rowClick][col].getBackground() == getShipColor())
                    return true;
        }
        
        // Otherwise, not occupied
        return false;
    }

    private void placeShip(int rowClick, int colClick) {
        Ship shipToPlace = getShips().get(getCurrentShip());
        
        // Checks if ship is already placed; if so, removes the ship first
        if (shipToPlace.isShipPlaced())
            removeShip(shipToPlace);

        // Add the ship to the board
        if (getCurrentDirection() == Constants.HORIZONTAL) {
            for (int col = colClick; col < (colClick + getCurrentShipLength()); col++)
                getButtonBoard()[rowClick][col].setBackground(getShipColor());
        } else if (getCurrentDirection() == Constants.VERTICAL) {
            for (int row = rowClick; row < (rowClick + getCurrentShipLength()); row++)
                getButtonBoard()[row][colClick].setBackground(getShipColor());
        }
                
        // Update ship's fields
        shipToPlace.setShipDirection(getCurrentDirection());
        shipToPlace.setShipLocation(rowClick, colClick);
        shipToPlace.setShipPlaced(true);
    }

    private void isReadyToDeploy() {
        // If any ship isn't placed, returns from this method
        for (Ship ship : getShips())
            if (!ship.isShipPlaced())
                return;
        
        // Otherwise, ships are ready to deploy
        parent.setDeployEnabled();
    }

    private void removeShip(Ship ship) {
        int startRow = ship.getShipStartRow();
        int startCol = ship.getShipStartCol();
        
        // Remove the ship from the board
        if(ship.getShipDirection() == Constants.VERTICAL) {
            for (int row = startRow; row < (startRow + ship.getShipLength()); row++)
                getButtonBoard()[row][startCol].setBackground(null);
        } else if (ship.getShipDirection() == Constants.HORIZONTAL) {
            for (int col = startCol; col < (startCol + ship.getShipLength()); col++)
                getButtonBoard()[startRow][col].setBackground(null);
        }

        // Then update the ship's shipPlaced field
        ship.setShipPlaced(false);
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    JButton[][] getBoard() {
        return buttonBoard;
    }

    public int getBoardLength() {
        return Constants.BOARD_LENGTH;
    }

    public boolean getIsFirst() {
        return isFirst;
    }

    Color getShipColor() {
        return shipColor;
    }

    private int getCurrentShip() {
        return currentShip;
    }

    private int getCurrentShipLength() {
        return currentShipLength;
    }

    private int getCurrentDirection() {
        return currentDirection;
    }

    public JButton[][] getButtonBoard() {
        return buttonBoard;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    private void setShipColor(Color shipColor) {
        this.shipColor = shipColor;
    }

    void setCurrentShip(int currentShip) {
        this.currentShip = currentShip;
    }

    void setCurrentShipLength(int currentShipLength) {
        this.currentShipLength = currentShipLength;
    }

    void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }

    public String getPlayerName() {
        return userName;
    }
}
