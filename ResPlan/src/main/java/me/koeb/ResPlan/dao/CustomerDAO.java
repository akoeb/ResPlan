/**
 * 
 */
package me.koeb.ResPlan.dao;



import java.util.List;

import me.koeb.ResPlan.core.Address;
import me.koeb.ResPlan.core.Customer;
import me.koeb.ResPlan.core.RequiredDate;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * @author Alexander Köb
 *
 */
@RegisterMapper(CustomerResultMapper.class)
public abstract class CustomerDAO implements PersonDAO {

	@SqlQuery("select p.id as person_id, "
			+ "p.first_name, p.last_name, p.birthday, "
			+ "c.id as customer_id, c.status_code, "
			+ "a.id as address_id, a.line_1, a.line_2, a.zip, "
			+ "a.city, a.country_code, a.phone, a.fax "
			+ "from customers c, persons p, addresses a "
			+ "where c.person_id = p.id "
			+ "and p.address_id = a.id")
	public abstract List<Customer> getAllCustomers();
	
	
	@SqlQuery("select p.id as person_id, "
			+ "p.first_name, p.last_name, p.birthday, "
			+ "c.id as customer_id, c.status_code, "
			+ "a.id as address_id, a.line_1, a.line_2, a.zip, "
			+ "a.city, a.country_code, a.phone, a.fax "
			+ "from customers c, persons p, addresses a "
			+ "where c.id = :id "
			+ "and c.person_id = p.id "
			+ "and p.address_id = a.id")
	public abstract Customer findCustomerById(@Bind("id") long id);
	

	
	/**
	 * select a list of all requiredDates for one Customer
	 * 
	 * @param id
	 * @return
	 */
	@SqlQuery("SELECT r.id as required_date_id, "
			+ "r.work_category_id, "
			+ "c.id as category_id, c.name, c.description, c.colour "
			+ "d.id as date_id, "
			+ "d.weekday, d.date, d.type, d.start_time, d.duration, "
			+ "a.id as address_id, a.line_1, a.line_2, a.zip, "
			+ "a.city, a.country_code, a.phone, a.fax "
			+ "FROM addresses a, dates d, customer_required_dates r, work_categories c "
			+ "WHERE r.customer_id = :id "
			+ "AND r.date_id = d.id "
			+ "AND d.address_id = a.id "
			+ "AND r.work_category_id = c.id")
	@Mapper(RequiredDateResultMapper.class)
	public abstract List<RequiredDate> getRequiredDatesForCustomer(@Bind("id") long id);

	
	@Transaction()
	public Customer createCustomer(Customer customer) {
		 
		 // three tables are involved: address, person and customer
		 
		 Address address = customer.getAddress();
		 long addressId = insertAddress(
				 address.getLine1(), address.getLine2(), address.getZip(), 
				 address.getCity(), address.getCountryCode(), address.getPhone(), 
				 address.getFax());
		 
		 address.setId(addressId);
		 customer.setAddress(address);
		 
		 long personId = insertPerson(customer.getFirstName(), customer.getLastName(),
				 addressId, new java.sql.Date(customer.getBirthday().toDate().getTime()));
		 
		 customer.setPersonId(personId);
		 
		 long customerId = insertCustomer(personId, customer.getStatusCode());
		 customer.setCustomerId(customerId);
		 
		 return customer;
	}
	
	@Transaction()
	public void updateCustomer(Customer customer) {
		 
		 // three tables are involved: address, person and customer
		 
		 Address address = customer.getAddress();
		 updateAddress(address.getId(),
				 address.getLine1(), address.getLine2(), address.getZip(), 
				 address.getCity(), address.getCountryCode(), address.getPhone(), 
				 address.getFax());
		 		 
		 updatePerson(customer.getPersonId(), customer.getFirstName(), 
				 customer.getLastName(), address.getId(), 
				 new java.sql.Date(customer.getBirthday().toDate().getTime()));
		 
		 
		 updateCustomer(customer.getPersonId(), customer.getPersonId(), customer.getStatusCode());
		 
	}
	
	
	
	
	/**
	 * close with no args is used to close the connection
	 */
	abstract void close();
}
