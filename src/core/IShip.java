package core;

public interface IShip {

    public boolean isShipPlaced();

    public void setShipLocation(int row, int column);

    public int getShipLength();

    public void setShipLength(int inLength);

    public boolean isShipSunk();

    public String getShipName();

    public void setShipName(String inName);

    public int getHitsLeft();

    public int getMaxHits();

    public void setMaxHits(int inHits);

    public int getShipDirection();

    public void setShipDirection(int inDirection);

    public void setShipPlaced(boolean isShipPlaced);

    public int getShipStartRow();

    public int getShipStartCol();
}
