/**
 * 
 */
package me.koeb.ResPlan.core;

import org.joda.time.LocalDate;

/**
 * @author Alexander Köb <nerdkram@koeb.me>
 *
 */
public class Customer extends Person{
	private long customerId;
	private String statusCode;
	//private Set<RequiredDate> requiredDates;

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
	//public Set<RequiredDate> getRequiredDates() {
	//	return requiredDates;
	//}
	/**
	 * @param requiredDates the requiredDates to set
	 */
	//public void setRequiredDates(Set<RequiredDate> requiredDates) {
	//	this.requiredDates = requiredDates;
	//}

	
	public String toString() {
		return "{customerId: "+ customerId +
			   ", statusCode: "+ statusCode +
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
		Customer other = (Customer) obj;
		if (customerId != other.customerId)
			return false;
		//if (requiredDates == null) {
		//	if (other.requiredDates != null)
		//		return false;
		//} else if (!requiredDates.equals(other.requiredDates))
		//	return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
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
		result = prime * result + (int) (customerId ^ (customerId >>> 32));
		//result = prime * result
		//		+ ((requiredDates == null) ? 0 : requiredDates.hashCode());
		result = prime * result
				+ ((statusCode == null) ? 0 : statusCode.hashCode());
		return result;
	}
}
