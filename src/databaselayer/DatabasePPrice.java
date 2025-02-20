package databaselayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import modellayer.*;

public class DatabasePPrice implements IDbPPrice {
	
	public PPrice getCurrentPrice() throws DatabaseLayerException {
		PPrice currentPrice = null;
		Calendar calendar = Calendar.getInstance();
		java.sql.Date dateNow = new java.sql.Date(calendar.getTimeInMillis());

		Connection con = DBConnection.getInstance().getDBcon();
		
		String query = "SELECT TOP 1 price, pZone_id, PZone.name AS pZone_name FROM PPrice INNER JOIN PZone ON PPrice.pZone_id = PZone.id WHERE starttime < ? ORDER BY starttime DESC";
		
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setDate(1, dateNow);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int price = rs.getInt("price");
				int pZoneId = rs.getInt("pZone_id");
				String pZoneName = rs.getString("pZone_name");
				PZone pZone = new PZone(pZoneId, pZoneName);
				currentPrice = new PPrice(price, pZone);
			}
			rs.close();
		} catch (SQLException ex) {
			throw new DatabaseLayerException("Error retrieving current price", ex);
		} catch (NullPointerException ex) {
			throw new DatabaseLayerException("Null pointer exception - possibly Connection object", ex);
		}
		return currentPrice;
	}
	
	public PPrice getPriceByZoneId(int zoneId) throws DatabaseLayerException {
		PPrice foundPrice = null;
		Calendar calendar = Calendar.getInstance();
		java.sql.Date dateNow = new java.sql.Date(calendar.getTimeInMillis());
		Connection con = DBConnection.getInstance().getDBcon();
		
		String query = "SELECT TOP 1 price, pZone_id, PZone.name AS pZone_name FROM PPrice INNER JOIN PZone ON PPrice.pZone_id = PZone.id WHERE pZone_id = ? AND starttime < ? ORDER BY starttime DESC";
		
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, zoneId);
			pstmt.setDate(2, dateNow);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int price = rs.getInt("price");
				int pZoneId = rs.getInt("pZone_id");
				String pZoneName = rs.getString("pZone_name");
				PZone pZone = new PZone(pZoneId, pZoneName);
				
				foundPrice = new PPrice(price, pZone);
			}
			rs.close();
		} catch (SQLException ex) {
			throw new DatabaseLayerException("Error retrieving data", ex);
		} catch (NullPointerException ex) {
			throw new DatabaseLayerException("Null pointer exception - possibly Connection object", ex);
		}
		return foundPrice;
	}
}
