package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import databaselayer.DatabaseLayerException;
import databaselayer.DatabasePBuy;
import modellayer.PBuy;

class DatabasePBuyTest {
	
	private PBuy pBuyStub;	// Pre defined stub for PBuy with values:
							// buyTime = 2025,2,19
							// duration = 80
							// payedAmount = 200
							// associatedPaystation = new PPayStation(id = 1,"Pay Station Test")
	private DatabasePBuy dbBuy;

	@BeforeEach
	void setUp() throws Exception {
		pBuyStub = new PBuy("test");
		dbBuy = new DatabasePBuy();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() throws DatabaseLayerException {
	
		
		System.out.println(pBuyStub.getBuyTime());
		System.out.println(pBuyStub.getParkingDuration());
		System.out.println(pBuyStub.getPayedCentAmount());
		System.out.println(pBuyStub.getAssociatedPaystation());
		
		dbBuy.insertParkingBuy(pBuyStub);

	}

}
