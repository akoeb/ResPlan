/**
 * 
 */
package me.koeb.ResPlan.core;

import java.util.Set;

import org.joda.time.LocalDate;

/**
 * @author Alexander Köb <nerdkram@koeb.me>
 *
 */
public class Customer extends Person{
	private long customerId;
	private String statusCode;
	private Set<RequiredDate> requiredDates;

	public Customer(long customerId, String statusCode, long personId, 
			String firstName, String lastName,
			Address address, LocalDate birthday) 
	{
		super(personId, firstName, lastName, address, birthday);
		this.customerId = customerId;
		this.statusCode = statusCode;
	}
	public Customer() {
		super();
	}
	/**
	 * @return the customerId
	 */
	public long getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the requiredDates
	 */
	public Set<RequiredDate> getRequiredDates() {
		return requiredDates;
	}
	/**
	 * @param requiredDates the requiredDates to set
	 */
	public void setRequiredDates(Set<RequiredDate> requiredDates) {
		this.requiredDates = requiredDates;
	}

	
	public String toString() {
		return "{customerId: "+ customerId +
			   ", statusCode: "+ statusCode +
			   ", person: { id: " + personId +
			   ", firstName: "+ firstName +
			   ", lastName: "+ lastName +
			   ", birthday: " + birthday +
			   ", address: { id: "+ address.getId() +
			   ", line1: " + address.getLine1() +
			   ", line2: " + address.getLine2() +
			   ", zip: " + address.getZip() +
			   ", city: " + address.getCity() +
			   ", countryCode: " + address.getCountryCode() +
			   ", phone: " + address.getPhone() +
			   ", fax: " + address.getFax() +
			   "}}}";
	}
	
	// need this for comparison and contains searches:
	public boolean equals(Object obj){
		
		if(this == obj)
			return true;
		if((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		
		// object must be Customer at this point
		Customer that = (Customer)obj;
		
		return this.toString().equals(that.toString());
	}

	public int hashCode()
	{
		return 7 * this.toString().hashCode();
	}
}
