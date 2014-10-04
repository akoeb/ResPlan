/**
 * 
 */
package me.koeb.ResPlan.core;

import java.sql.Time;
import org.joda.time.LocalDate;


/**
 * @author Alexander Köb <nerdkram@koeb.me>
 *
 */
public class RequiredDate extends AbstractDate {
	
	private long requiredDateId;
	private Category workCategory;
	private Address location;
	
	
	public RequiredDate(long requiredDateId, Category workCategory, Address location, 
			long abstractDateId, String weekday, LocalDate day, String type,
			Time startTime, int duration) {
		super(abstractDateId, weekday, day, type, startTime, duration);
		this.requiredDateId = requiredDateId;
		this.workCategory = workCategory;
		this.location = location;
	}
	/**
	 * @return the requiredDateId
	 */
	public long getRequiredDateId() {
		return requiredDateId;
	}
	/**
	 * @param requiredDateId the requiredDateId to set
	 */
	public void setRequiredDateId(long requiredDateId) {
		this.requiredDateId = requiredDateId;
	}
	/**
	 * @return the workCategory
	 */
	public Category getWorkCategory() {
		return workCategory;
	}
	/**
	 * @param workCategory the workCategory to set
	 */
	public void setWorkCategory(Category workCategory) {
		this.workCategory = workCategory;
	}
	/**
	 * @return the location
	 */
	public Address getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Address location) {
		this.location = location;
	}
	
}
