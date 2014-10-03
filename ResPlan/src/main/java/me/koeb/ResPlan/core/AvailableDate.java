/**
 * 
 */
package me.koeb.ResPlan.core;

import java.sql.Time;
import java.util.Date;


/**
 * @author Alexander Köb <nerdkram@koeb.me>
 *
 */
public class AvailableDate extends AbstractDate {
	
	private long availableDateId;
	private String availabilityType;
	private String description;
	
	
	
	public AvailableDate(long availableDateId, String availabilityType, String description, 
			long abstractDateId, String weekday, Date date, String type,
			Time startTime, int duration) {
		super(abstractDateId, weekday, date, type, startTime, duration);
		this.availableDateId = availableDateId;
		this.availabilityType = availabilityType;
		this.description = description;
	}
	/**
	 * @return the requiredDateId
	 */
	public long getAvailableDateId() {
		return availableDateId;
	}
	/**
	 * @param requiredDateId the requiredDateId to set
	 */
	public void setAvailableDateId(long availableDateId) {
		this.availableDateId = availableDateId;
	}
	/**
	 * @return the type
	 */
	public String getAvailabilityType() {
		return availabilityType;
	}
	/**
	 * @param type the type to set
	 */
	public void setAvailabilityType(String availabilityType) {
		this.availabilityType = availabilityType;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
