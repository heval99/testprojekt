package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controllayer.ControlPrice;
import databaselayer.DatabasePPrice;
import databaselayer.DatabaseLayerException;
import modellayer.PPrice;

class ControlPriceIntegrationTest {

    private ControlPrice controlPrice;
    private DatabasePPrice databasePPrice;

    @BeforeEach
    void setUp() {
        controlPrice = new ControlPrice();
        databasePPrice = new DatabasePPrice();  // Ensure database is available
    }

    @Test
    void testGetPriceRemote_ValidZone() {
        int zoneId = 1; // Example zone ID

        try {
            PPrice expectedPrice = databasePPrice.getPriceByZoneId(zoneId); // Fetch expected value
            PPrice actualPrice = controlPrice.getPriceRemote(zoneId); // Call method being tested

            assertNotNull(actualPrice, "Price should not be null");
            assertEquals(expectedPrice.getParkingPrice(), actualPrice.getParkingPrice(), "Prices should match");
            assertEquals(expectedPrice.getParkingZone().getpZoneId(), actualPrice.getParkingZone().getpZoneId(), "Zone IDs should match");

        } catch (DatabaseLayerException e) {
            fail("DatabaseLayerException was thrown: " + e.getMessage());
        }
    }

    @Test
    void testGetPriceRemote_InvalidZone() {
        int invalidZoneId = -1; // Non-existent zone ID

        try {
            PPrice actualPrice = controlPrice.getPriceRemote(invalidZoneId);
            assertNull(actualPrice, "Should return null for invalid zone");

        } catch (DatabaseLayerException e) {
            fail("DatabaseLayerException was thrown: " + e.getMessage());
        }
    }
}
