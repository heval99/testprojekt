package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import controllayer.*;
import modellayer.*;

/**
 * Inspired by the book: Flexible, Reliable Software Henrik B�rbak Christensen:
 * Flexible, Reliable Software. Taylor and Francis Group, LLC 2010
 */

public class TestCalculationCurrencyEuro {

	private static ControlPayStation ps;

	/** Fixture for pay station testing. */
	@BeforeAll
	public static void setUp() {
		ps = new ControlPayStation();
	}
	
	/**
	 * Entering no coins should make the display report 0 minutes parking time.
	 */
	@Test
	public void shouldDisplay0() throws IllegalCoinException {
		
		// Arrange
		int expectedParkingTime = 0;
		
		// Act
		// No action
		
		// Assert
		assertEquals(expectedParkingTime, ps.readDisplay(), "Should display 0 min for no coins");
	}	

	/**
	 * Entering 5 cents should make the display report 2 minutes parking time
	 */
	@Test
	public void shouldDisplay2MinFor5Cents() throws IllegalCoinException {
		
//		// Arrange
//		int expectedParkingTime = 2;	// In minutes		
//		int coinValue = 5;
//		Currency.ValidCurrency coinCurrency = Currency.ValidCurrency.EURO;
//		Currency.ValidCoinType coinType = Currency.ValidCoinType.FRACTION;
		
		// Act
		
		// Assert

	}

	
}
