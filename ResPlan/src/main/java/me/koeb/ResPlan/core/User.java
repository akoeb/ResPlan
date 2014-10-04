/**
 * 
 */
package me.koeb.ResPlan.core;

import org.joda.time.LocalDate;

/**
 * @author Alexander Köb <nerdkram@koeb.me>
 *
 */
public class User extends Person{
	private long userId;
	private String statusCode;
	private long accountId;
	//private Set<AvailableDate> availableDates;
	//private Set <Category> possibleWorkCategories;

	public User(long userId, String statusCode, long accountId, long personId, 
			String firstName, String lastName,
			Address address, LocalDate birthday) 
	{
		super(personId, firstName, lastName, address, birthday);
		this.accountId = accountId;
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
	public void setUserId(long userId) {
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
	/**
	 * get account id. This can return null in case accountid is 0
	 * @return the account id or null
	 */
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	/**
	 * @return the requiredDates
	 */
	//public Set<AvailableDate> getAvailableDates() {
	//	return availableDates;
	//}
	/**
	 * @param requiredDates the requiredDates to set
	 */
	//public void setAvailableDates(Set<AvailableDate> availableDates) {
	//	this.availableDates = availableDates;
	//}

	
	//public Set <Category> getPossibleWorkCategories() {
	//	return possibleWorkCategories;
	//}
	//public void setPossibleWorkCategories(Set <Category> possibleWorkCategories) {
	//	this.possibleWorkCategories = possibleWorkCategories;
	//}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{userId: "+ userId +
				   ", statusCode: "+ statusCode +
				   ", accountId: " + accountId +
				   ", person: " + super.toString() +
				   ", address: " + address.toString() + "}";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (accountId != other.accountId)
			return false;
		//if (availableDates == null) {
		//	if (other.availableDates != null)
		//		return false;
		//} else if (!availableDates.equals(other.availableDates))
		//	return false;
		//if (possibleWorkCategories == null) {
		//	if (other.possibleWorkCategories != null)
		//		return false;
		//} else if (!possibleWorkCategories.equals(other.possibleWorkCategories))
		//	return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (accountId ^ (accountId >>> 32));
		//result = prime * result
		//		+ ((availableDates == null) ? 0 : availableDates.hashCode());
		//result = prime
		//		* result
		//		+ ((possibleWorkCategories == null) ? 0
		//				: possibleWorkCategories.hashCode());
		result = prime * result
				+ ((statusCode == null) ? 0 : statusCode.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}
}
