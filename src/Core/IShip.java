package Core;

interface IShip {

    boolean isShipPlaced();

    void setShipLocation(int row, int column);

    int getShipLength();

    void setShipLength(int inLength);

    boolean isShipSunk();

    String getShipName();

    void setShipName(String inName);

    void setMaxHits(int inHits);

    int getShipDirection();

    void setShipDirection(int inDirection);

    void setShipPlaced(boolean isShipPlaced);

    int getShipStartRow();

    int getShipStartCol();
}
