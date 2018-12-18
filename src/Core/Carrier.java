package Core;

public class Carrier extends Ship {

    public Carrier() {
        setShipLength(Constants.CARRIER_LENGTH);
        setShipName(Constants.CARRIER_NAME);
        setMaxHits(Constants.CARRIER_LENGTH);
    }
    
}
