package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controllayer.ControlPayStation;
import controllayer.IllegalCoinException;
import modellayer.Currency;
import modellayer.Currency.ValidCoinType;
import modellayer.Currency.ValidCurrency;
import modellayer.PPayStation;

class ControlPayStationTest {

	private ControlPayStation psCtrl;

	@BeforeEach
	void setUp() throws Exception {
		psCtrl = new ControlPayStation();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void addPayment1EuroSucces40Min() throws IllegalCoinException {

		// Arrange
		int value = 1;
		ValidCurrency currency = Currency.ValidCurrency.EURO;
		ValidCoinType coinType = Currency.ValidCoinType.INTEGER;

		// Act
		psCtrl.addPayment(value, currency, coinType);

		// Assert
		assertEquals(psCtrl.readDisplay(), 40);
	}

	@Test
	void addPayment10CentSuccess4Min() throws IllegalCoinException {
		// Arrange
		int value = 10;
		ValidCurrency currency = Currency.ValidCurrency.EURO;
		ValidCoinType coinType = Currency.ValidCoinType.FRACTION;

		// Act
		psCtrl.addPayment(value, currency, coinType);

		// Assert
		assertEquals(psCtrl.readDisplay(), 4);
	}

	@Test
	void addPayment20KronerSuccess107Min() throws IllegalCoinException {
		// Arrange
		int value = 20;
		ValidCurrency currency = Currency.ValidCurrency.DKK;
		ValidCoinType coinType = Currency.ValidCoinType.INTEGER;

		// Act
		psCtrl.addPayment(value, currency, coinType);

		// Assert
		assertEquals(psCtrl.readDisplay(), 107);
	}

	@Test
	void addPayment50ØreSuccess3Min() throws IllegalCoinException {
		// Arrange
		int value = 50;
		ValidCurrency currency = Currency.ValidCurrency.DKK;
		ValidCoinType coinType = Currency.ValidCoinType.FRACTION;

		// Act
		psCtrl.addPayment(value, currency, coinType);

		// Assert
		assertEquals(psCtrl.readDisplay(), 3);
	}

	@Test
	void addPaymentInvalidCoin0Min() throws IllegalCoinException {
		// Arrange
		int value = 1;
		ValidCurrency currency = Currency.ValidCurrency.NOK;
		ValidCoinType coinType = Currency.ValidCoinType.INTEGER;

		// Act
		IllegalCoinException exception = assertThrows(IllegalCoinException.class, () -> {
			psCtrl.addPayment(value, currency, coinType);
		});

		// Assert
		assertEquals("Invalid coin: " + currency.toString() + ", " + coinType.toString() + ", " + value,
				exception.getMessage());
	}

	@Test
	void addPayment1Euro10Cent20Kroner50ØreSuccess154Min() throws IllegalCoinException {
		// Arrange

		ValidCurrency currencyDKK = Currency.ValidCurrency.DKK;
		ValidCurrency currencyEuro = Currency.ValidCurrency.EURO;

		ValidCoinType coinTypeInteger = Currency.ValidCoinType.INTEGER;
		ValidCoinType coinTypeFraction = Currency.ValidCoinType.FRACTION;

		// Act
		psCtrl.addPayment(1, currencyEuro, coinTypeInteger); // Adding 1 euro, value = 1;
		psCtrl.addPayment(10, currencyEuro, coinTypeFraction); // Adding 10 cent, value = 10;
		psCtrl.addPayment(20, currencyDKK, coinTypeInteger); // Adding 20 kroner, value = 20;
		psCtrl.addPayment(50, currencyDKK, coinTypeFraction); // Adding 50 øre, value = 50;

		// Assert
		assertEquals(psCtrl.readDisplay(), 154);
	}

	@Test
	void addPayment1Euro10Kroner2InvalidCoinsSuccess94Min() throws IllegalCoinException {

		// Arrange

		ValidCurrency currencyDKK = Currency.ValidCurrency.DKK;
		ValidCurrency currencyEuro = Currency.ValidCurrency.EURO;
		ValidCurrency currencyInvalidNOK = Currency.ValidCurrency.NOK;
		ValidCurrency currencyInvalidSEK = Currency.ValidCurrency.SEK;

		ValidCoinType coinTypeInteger = Currency.ValidCoinType.INTEGER;

		int value1Euro = 1;
		int value10Kroner = 10;
		int valueNOK = 1;
		int valueSEK = 1;

		// Act
		psCtrl.addPayment(value1Euro, currencyEuro, coinTypeInteger); // Adding 1 euro, value = 1;
		psCtrl.addPayment(value10Kroner, currencyDKK, coinTypeInteger); // Adding 10 kroner, value = 10;

		// Inserting invalid NOK
		IllegalCoinException exceptionNOK = assertThrows(IllegalCoinException.class, () -> {
			psCtrl.addPayment(valueNOK, currencyInvalidNOK, coinTypeInteger);
		});

		// Inserting invalid SEK
		IllegalCoinException exceptionSEK = assertThrows(IllegalCoinException.class, () -> {
			psCtrl.addPayment(valueSEK, currencyInvalidSEK, coinTypeInteger);
		});
		// Assert

		assertEquals("Invalid coin: " + currencyInvalidNOK.toString() + ", " + coinTypeInteger.toString() + ", " + 1,
				exceptionNOK.getMessage());

		assertEquals("Invalid coin: " + currencyInvalidSEK.toString() + ", " + coinTypeInteger.toString() + ", " + 1,
				exceptionSEK.getMessage());

		assertEquals(psCtrl.readDisplay(), 94);
	}

	@Test
	void addPayment1Euro1KroneCancelled0Min() throws IllegalCoinException {

		// Arrange

		ValidCurrency currencyDKK = Currency.ValidCurrency.DKK;
		ValidCurrency currencyEuro = Currency.ValidCurrency.EURO;

		ValidCoinType coinTypeInteger = Currency.ValidCoinType.INTEGER;

		int value = 1;

		// Act
		psCtrl.addPayment(value, currencyEuro, coinTypeInteger); // Adding 1 euro, value = 1;
		psCtrl.addPayment(value, currencyDKK, coinTypeInteger); // Adding 1 krone, value = 1;

		int timeBeforeCancel = psCtrl.readDisplay();
		psCtrl.cancel();
		int timeAfterCancel = psCtrl.readDisplay();

		// Assert

		assertEquals(timeBeforeCancel, 46); // Verify that the method didnt just do nothing
		assertEquals(timeAfterCancel, 0); // Verify that the cancel() method resets the time to 0
	}

	void addPayment2CentLowerBoundry1Min() throws IllegalCoinException {

		// Arrange
		
		ValidCurrency currency = Currency.ValidCurrency.EURO;
		ValidCoinType coinType = Currency.ValidCoinType.FRACTION;

		int value = 1;

		// Act
		psCtrl.addPayment(value, currency, coinType); // Adding 1 euro, value = 1;
		

		// Assert

		assertEquals(psCtrl.readDisplay(), 1); // Due to the rounding up, minimum is 1 min
	}
	
	

}
