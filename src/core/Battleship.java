package core;

public class Battleship extends Ship {

    public Battleship() {
        setShipLength(Constants.BATTLESHIP_LENGTH);
        setShipName(Constants.BATTLESHIP_NAME);
        setMaxHits(Constants.BATTLESHIP_LENGTH);
    }
    
}