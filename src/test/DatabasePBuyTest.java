package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllayer.IllegalCoinException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

import databaselayer.DBConnection;
import databaselayer.DatabaseLayerException;
import databaselayer.DatabasePBuy;
import modellayer.PBuy;

class DatabasePBuyTest {

	private PBuy pBuy = new PBuy("test"); // Pre defined stub for PBuy with values:
	// buyTime = 2025,2,19
	// duration = 80
	// payedAmount = 200
	// associatedPaystation = new PPayStation(id = 1,"Pay Station Test")
	private DatabasePBuy dbBuy;
	static Connection con;
	
	
	@BeforeEach
	void setUp() {
		con = DBConnection.getInstance().getDBcon();
		dbBuy = new DatabasePBuy();
	}

	@AfterEach
	void cleanupTestRecord() throws SQLException {
		String resetTable = "DELETE FROM PBuy; DBCC CHECKIDENT ('PBuy', RESEED, 0);";
	    try (PreparedStatement pstmt = con.prepareStatement(resetTable)) {
	        pstmt.executeUpdate();
	    }
    }

	@Test
	void succesTest() throws DatabaseLayerException {

		// Arrange
		LocalDate date = LocalDate.of(2025, 2, 21);
		int duration = 16;
		double amount = 40;

		pBuy.setBuyTime(date);
		pBuy.setParkingDuration(duration);
		pBuy.setPayedCentAmount(amount);

		// Act

		int expectedResId = 0;
		int resId = 0;
		
		String query = "SELECT * FROM PBuy where id = ?";
		int id = dbBuy.insertParkingBuy(pBuy);
		
		try (PreparedStatement pstmt = con.prepareStatement(query)){
				pstmt.setInt(1, id);
				
				ResultSet rs = pstmt.executeQuery();
		
			while (rs.next()) {
				 resId = rs.getInt("id");
			}
			rs.close();
		} catch (SQLException ex) {}

			// Assert
			System.out.println("RES ID = " + resId);

		}
	}



