package databaselayer;

import modellayer.PPrice;
import modellayer.PZone;

// Stub class implementing IDbPPrice
public class StubDatabasePPrice implements IDbPPrice {
    private boolean simulateFailure = false;

    public void setSimulateFailure(boolean simulateFailure) {
        this.simulateFailure = simulateFailure;
    }

    @Override
    public PPrice getPriceByZoneId(int zoneId) throws DatabaseLayerException {
        if (simulateFailure) {
            throw new DatabaseLayerException("Connection failure");
        }
        return new PPrice(24, new PZone(zoneId, "Zone " + zoneId));
    }

    public void updatePrice(PPrice price) throws DatabaseLayerException {
        if (simulateFailure) {
            throw new DatabaseLayerException("Connection failure");
        }
        if (price.getParkingPrice() < 0 || price.getParkingZone() == null || price.getParkingZone().getpZoneId() < 0) {
            throw new IllegalArgumentException("Invalid price or zone");
        }
    }

    
	@Override
	public PPrice getCurrentPrice() {
		// TODO Auto-generated method stub
		return null;
	}
}
