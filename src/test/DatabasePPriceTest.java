package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import databaselayer.StubDatabasePPrice;
import databaselayer.DatabaseLayerException;
import modellayer.PPrice;
import modellayer.PZone;

class DatabasePPriceTest {
    
    private StubDatabasePPrice stubDatabasePPrice;

    @BeforeEach
    void setUp() {
        stubDatabasePPrice = new StubDatabasePPrice();
    }

    @Test
    void testSuccessfulPriceUpdate() throws DatabaseLayerException {
        // Test Case 1: Successful price update
        PPrice price = new PPrice(24, new PZone(2, "Downtown"));
        assertDoesNotThrow(() -> stubDatabasePPrice.updatePrice(price));
    }

    @Test
    void testConnectionFailureRetrySuccess() {
        // Test Case 2: Simulating a connection failure but retry succeeds
        stubDatabasePPrice.setSimulateFailure(true);
        
        assertThrows(DatabaseLayerException.class, () -> stubDatabasePPrice.updatePrice(new PPrice(24, new PZone(2, "Downtown"))));
        
        stubDatabasePPrice.setSimulateFailure(false);
        assertDoesNotThrow(() -> stubDatabasePPrice.updatePrice(new PPrice(24, new PZone(2, "Downtown"))));
    }

    @Test
    void testConnectionFailureRetryFailure() {
        // Test Case 3: Simulating persistent connection failure
        stubDatabasePPrice.setSimulateFailure(true);
        
        assertThrows(DatabaseLayerException.class, () -> stubDatabasePPrice.updatePrice(new PPrice(24, new PZone(2, "Downtown"))));
    }

    @Test
    void testInvalidNegativePrice() {
        // Test Case 4: Invalid data (negative price)
        PPrice invalidPrice = new PPrice(-2351236, new PZone(2, "Downtown"));
        
        assertThrows(IllegalArgumentException.class, () -> stubDatabasePPrice.updatePrice(invalidPrice));
    }

    @Test
    void testInvalidParkingZone() {
        // Test Case 5: Invalid parking zone
        PPrice invalidZone = new PPrice(24, new PZone(-1, "Invalid Zone"));
        
        assertThrows(IllegalArgumentException.class, () -> stubDatabasePPrice.updatePrice(invalidZone));
    }
}