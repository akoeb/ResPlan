/**
 * 
 */
package me.koeb.ResPlan.core;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;

/**
 * @author Alexander Köb <nerdkram@koeb.me>
 *
 */
public class Person {
	@NotNull
	protected long personId;
	@Length(min=3, max=255)
	protected String firstName;
	@Length(min=3, max=255)
	protected String lastName;
	@Valid
	protected Address address;
	
	@Valid
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@JsonSerialize(using=LocalDateSerializer.class)
	protected LocalDate birthday;
	
	
	public Person(long personId, String firstName, String lastName,
			Address address, LocalDate birthday) {
		super();
		this.personId = personId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.birthday = birthday;
	}

	public Person() {
	}
	/**
	 * @return the personId
	 */
	public long getPersonId() {
		return personId;
	}
	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	/**
	 * @return the birthday
	 */
	public LocalDate getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	/**
	 * use this to directly set the embedded addresses id:
	 * @param id
	 */
	public void setAddressId(long id) {
		this.address.setId(id);
	}

	
}
