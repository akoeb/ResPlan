package me.koeb.ResPlan.core;

import java.sql.Time;
import java.util.Date;




public abstract class AbstractDate {
	 private long abstractDateId;
	 private String weekday;
	 private Date date;
	 private String type; // (REPEATING or ONE_TIME)
	 private Time startTime;
	 private int duration;
	 
	 
	public AbstractDate(long abstractDateId, String weekday, Date date, String type,
			Time startTime, int duration) {
		super();
		this.abstractDateId = abstractDateId;
		this.weekday = weekday;
		this.date = date;
		this.type = type;
		this.startTime = startTime;
		this.duration = duration;
	}
	/**
	 * @return the date_id
	 */
	public long getAbstractDateId() {
		return abstractDateId;
	}
	/**
	 * @param date_id the date_id to set
	 */
	public void setAbstractDateId(long abstractDateId) {
		this.abstractDateId = abstractDateId;
	}
	/**
	 * @return the weekday
	 */
	public String getWeekday() {
		return weekday;
	}
	/**
	 * @param weekdatay the weekday to set
	 */
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the startTime
	 */
	public Time getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

}
