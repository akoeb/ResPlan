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
public class User extends Person{
	private long userId;
	private String statusCode;
	private Account account;
	private Set<AvailableDate> availableDates;
	private Set <Category> possibleWorkCategories;

	public User(long userId, String statusCode, long personId, 
			String firstName, String lastName,
			Address address, LocalDate birthday) 
	{
		super(personId, firstName, lastName, address, birthday);
		this.userId = userId;
		this.statusCode = statusCode;
	}
	public User() {
		super();
	}
	/**
	 * @return the customerId
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCUserId(long userId) {
		this.userId = userId;
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
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	/**
	 * @return the requiredDates
	 */
	public Set<AvailableDate> getAvailableDates() {
		return availableDates;
	}
	/**
	 * @param requiredDates the requiredDates to set
	 */
	public void setAvailableDates(Set<AvailableDate> availableDates) {
		this.availableDates = availableDates;
	}

	
	public Set <Category> getPossibleWorkCategories() {
		return possibleWorkCategories;
	}
	public void setPossibleWorkCategories(Set <Category> possibleWorkCategories) {
		this.possibleWorkCategories = possibleWorkCategories;
	}
	
	// TODO ACCOUNT
	public String toString() {
		return "{customerId: "+ userId +
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
		User that = (User)obj;
		
		return this.toString().equals(that.toString());
	}

	public int hashCode()
	{
		return 23 * this.toString().hashCode();
	}
}
