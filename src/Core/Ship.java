package Core;

public class Ship implements IShip {

    private boolean shipPlaced;
    private boolean shipSunk;
    private int hitsLeft;
    private int maxNumberOfHits;
    private int shipDirection;
    private int shipLength;
    private String shipName;
    private int shipStartRow;
    private int shipStartCol;
    private int shipStopRow;
    private int shipStopCol;

    public Ship() {
        shipPlaced = false;
    }
    
    @Override
    public boolean isShipPlaced() {
        return shipPlaced;
    }

    @Override
    public void setShipLocation(int row, int col) {
        setShipStartRow(row);
        setShipStartCol(col);

        if (getShipDirection() == Constants.HORIZONTAL) {
            setShipStopRow(shipStartRow);
            setShipStopCol(shipStartCol + (getShipLength() - 1));
        } else if (getShipDirection() == Constants.VERTICAL) {
            setShipStopRow(shipStartRow + (getShipLength() - 1));
            setShipStopCol(shipStartCol);
        }
    }

    public void shipIsHit() {
        hitsLeft--;

        if (hitsLeft == 0) {
            shipSunk = true;
        }
    }

    public void setShipStopRow(int row) {
        shipStopRow = row;
    }

    public int getShipStopRow() {
        return shipStopRow;
    }

    public void setShipStopCol(int col) {
        shipStopCol = col;
    }

    public int getShipStopCol() {
        return shipStopCol;
    }

    @Override
    public int getShipLength() {
        return shipLength;
    }

    @Override
    public final void setShipLength(int inLength) {
        shipLength = inLength;
    }

    @Override
    public boolean isShipSunk() {
        return shipSunk;
    }
    
    private void setShipSunk(Boolean bool) {
        shipSunk = bool;
    }

    @Override
    public String getShipName() {
        return shipName;
    }

    @Override
    public final void setShipName(String inName) {
        shipName = inName;
    }

    @Override
    public int getHitsLeft() {
        return hitsLeft;
    }

    @Override
    public int getMaxHits() {
        return maxNumberOfHits;
    }

    @Override
    public final void setMaxHits(int inHits) {
        maxNumberOfHits = inHits;
        hitsLeft = inHits;
    }

    @Override
    public int getShipDirection() {
        return shipDirection;
    }

    @Override
    public void setShipDirection(int inDirection) {
        shipDirection = inDirection;
    }

    @Override
    public void setShipPlaced(boolean isShipPlaced) {
        shipPlaced = isShipPlaced;
    }

    @Override
    public int getShipStartRow() {
        return shipStartRow;
    }

    private void setShipStartRow(int shipStartRow) {
        this.shipStartRow = shipStartRow;
    }

    private void setShipStartCol(int shipStartCol) {
        this.shipStartCol = shipStartCol;
    }

    @Override
    public int getShipStartCol() {
        return shipStartCol;
    }
}
