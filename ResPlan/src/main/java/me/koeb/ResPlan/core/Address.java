/**
 * 
 */
package me.koeb.ResPlan.core;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexander Köb <nerdkram@koeb.me>
 *
 */
public class Address {
	@NotNull
	private long id;
	@Length(min=3, max=255)
	private String line1;
	@Length(min=3, max=255)
	private String line2;
	@Length(min=3, max=255)
	private String zip;
	@Length(min=3, max=255)
	private String city;
	@Length(min=2, max=3)
	private String countryCode;
	@Length(min=3, max=255)
	private String phone;
	@Length(min=3, max=255)
	private String fax;
	
	
	public Address(long id, String line1, String line2, String zip,
			String city, String countryCode, String phone, String fax) {
		super();
		this.id = id;
		this.line1 = line1;
		this.line2 = line2;
		this.zip = zip;
		this.city = city;
		this.countryCode = countryCode;
		this.phone = phone;
		this.fax = fax;
	}
	
	public Address() {
	}
	/**
	 * @return the id
	 */
	@JsonProperty
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	@JsonProperty
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the line1
	 */
	@JsonProperty
	public String getLine1() {
		return line1;
	}
	/**
	 * @param line1 the line1 to set
	 */
	@JsonProperty
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	/**
	 * @return the line2
	 */
	@JsonProperty
	public String getLine2() {
		return line2;
	}
	/**
	 * @param line2 the line2 to set
	 */
	@JsonProperty
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	/**
	 * @return the zip
	 */
	@JsonProperty
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	@JsonProperty
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the city
	 */
	@JsonProperty
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	@JsonProperty
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the countryCode
	 */
	@JsonProperty
	public String getCountryCode() {
		return countryCode.toString();
	}
	/**
	 * @param countryCode the countryCode to set
	 */
	@JsonProperty
	public void setCountryCode(String countryString) {
		this.countryCode = countryString;
	}
	/**
	 * @return the phone
	 */
	@JsonProperty
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	@JsonProperty
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the fax
	 */
	@JsonProperty
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	@JsonProperty
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String toString() {
		return "{id: "+ id +
		   ", line1: " + line1 +
		   ", line2: " + line2 +
		   ", zip: " + zip +
		   ", city: " + city +
		   ", countryCode: " + countryCode +
		   ", phone: " + phone +
		   ", fax: " + fax + "}";
	}
	
	
	// need this for comparison and contains searches:
	public boolean equals(Object obj){
		
		if(this == obj)
			return true;
		if((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		
		// object must be Customer at this point
		Address that = (Address)obj;

	
		if (this.id == that.id &&
			  ( this.line1 == that.line1 ||
				this.line1 != null && this.line1.equals(that.line1)) &&
			  (	this.line2 == that.line2 ||
				this.line2 != null && this.line2.equals(that.line2)) &&
			  (	this.zip == that.zip ||
				this.zip != null && this.zip.equals(that.zip)) &&
			  (	this.city == that.city ||
				this.city != null && this.city.equals(that.city)) &&
			  (	this.countryCode == that.countryCode ||
				this.countryCode != null && this.countryCode.equals(that.countryCode)) &&
			  (	this.phone == that.phone ||
				this.phone != null && this.phone.equals(that.phone)) &&
			  (	this.fax == that.fax ||
				this.fax != null && this.fax.equals(that.fax)) ) {
			return true;
		}
		return false;
	}

	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + (int)(this.id ^ (this.id >>> 32));
		hash = 31 * hash + (null == line1 ? 0 : line1.hashCode());
		hash = 31 * hash + (null == line2 ? 0 : line2.hashCode());
		hash = 31 * hash + (null == zip ? 0 : zip.hashCode());
		hash = 31 * hash + (null == city ? 0 : city.hashCode());
		hash = 31 * hash + (null == countryCode ? 0 : countryCode.hashCode());
		hash = 31 * hash + (null == phone ? 0 : phone.hashCode());
		hash = 31 * hash + (null == fax ? 0 : fax.hashCode());
		return hash;
	}
}
