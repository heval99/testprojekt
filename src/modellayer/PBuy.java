package modellayer;

import java.time.LocalDate;

/**
 * Inspired by the book: Flexible, Reliable Software Henrik B�rbak Christensen:
 * Flexible, Reliable Software. Taylor and Francis Group, LLC 2010
 */

public class PBuy {

	// Buy ident
	private long id;
	// Time of buy
	private LocalDate buyTime;
	// Parkingtime in minutes
	private int duration;
	// Payed amount in cents
	private double payedAmount;
	// PayStation that generated buy 
	private PPayStation associatedPaystation;
	
	// Constructor
	public PBuy() {
		
	}
	public PBuy(int foundId) {
		id = foundId;
	}	
	public PBuy(LocalDate buyTime, int parkingDuration, double payedCentAmount) {
		this.buyTime = buyTime;
		this.duration = parkingDuration;
		this.payedAmount = payedCentAmount;
	}

	
	@Override
	public String toString() {
		return "PBuy [id=" + id + ", buyTime=" + buyTime + ", duration=" + duration + ", payedAmount=" + payedAmount
				+ ", associatedPaystation=" + associatedPaystation + "]";
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(LocalDate buyTime) {
		this.buyTime = buyTime;
	}

	public int getParkingDuration() {
		return duration;
	}

	public void setParkingDuration(int parkingDuration) {
		this.duration = parkingDuration;
	}

	public double getPayedCentAmount() {
		return payedAmount;
	}

	public void setPayedCentAmount(double payedCentAmount) {
		this.payedAmount = payedCentAmount;
	}

	public PPayStation getAssociatedPaystation() {
		return associatedPaystation;
	}

	public void setAssociatedPaystation(PPayStation associatedPaystation) {
		this.associatedPaystation = associatedPaystation;
	}
	
	public PBuy(String ifStringIsTest) {
		LocalDate buyLocalDateTime = LocalDate.of(0,1,1);
		
		if(ifStringIsTest.equals("test")) {
			this.buyTime=buyLocalDateTime;
			this.duration  =  0;
			this.payedAmount = 0;
			this.associatedPaystation = new PPayStation(1,"Pay Station Test");
		}
	}
	
}
