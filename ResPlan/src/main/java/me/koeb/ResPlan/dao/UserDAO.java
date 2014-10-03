/**
 * 
 */
package me.koeb.ResPlan.dao;


import java.util.List;

import me.koeb.ResPlan.core.Address;
import me.koeb.ResPlan.core.Customer;
import me.koeb.ResPlan.core.RequiredDate;
import me.koeb.ResPlan.core.User;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * @author Alexander Köb
 *
 */
@RegisterMapper(UserResultMapper.class)
public abstract class UserDAO implements PersonDAO {

	@SqlQuery("select p.id as person_id, "
			+ "p.first_name, p.last_name, p.birthday, "
			+ "u.id as user_id, u.status_code, "
			+ "a.id as address_id, a.line_1, a.line_2, a.zip, "
			+ "a.city, a.country_code, a.phone, a.fax, "
			+ "ac.id as account_id, ac.login, ac.password, "
			+ "ac.role_code, ac.email "
			+ "from users u, persons p, addresses a, account ac "
			+ "where c.person_id = p.id "
			+ "and p.address_id = a.id "
			+ "and u.account_id = ac.id")
	public abstract List<User> getAllUsers();
	
	
	@SqlQuery("select p.id as person_id, "
			+ "p.first_name, p.last_name, p.birthday, "
			+ "u.id as user_id, u.status_code, "
			+ "a.id as address_id, a.line_1, a.line_2, a.zip, "
			+ "a.city, a.country_code, a.phone, a.fax, "
			+ "ac.id as account_id, ac.login, ac.password, "
			+ "ac.role_code, ac.email "
			+ "from users u, persons p, addresses a, account ac "
			+ "where c.person_id = p.id "
			+ "and p.address_id = a.id "
			+ "and u.account_id = ac.id "
			+ "and u.id = :id ")
	public abstract User findUserById(@Bind("id") long id);
	

	
	/**
	 * select a list of all availableDates for one Customer
	 * 
	 * @param id
	 * @return
	 */
	@SqlQuery("SELECT r.id as required_date_id, "
			+ "ad.type, ad.description "
			+ "c.id as category_id, c.name, c.description, c.colour "
			+ "d.id as date_id, "
			+ "d.weekday, d.date, d.type, d.start_time, d.duration, "
			+ "a.id as address_id, a.line_1, a.line_2, a.zip, "
			+ "a.city, a.country_code, a.phone, a.fax "
			+ "FROM addresses a, dates d, user_available_dates ad, work_categories c "
			+ "WHERE r.customer_id = :id "
			+ "AND r.date_id = d.id "
			+ "AND d.address_id = a.id "
			+ "AND r.work_category_id = c.id")
	@Mapper(AvailableDateResultMapper.class)
	public abstract List<RequiredDate> getAvailableDatesForUser(@Bind("id") long id);

	
	@Transaction()
	public User createUser(User user) {
		 
		 // three tables are involved: address, person and customer
		 
		 Address address = user.getAddress();
		 long addressId = insertAddress(
				 address.getLine1(), address.getLine2(), address.getZip(), 
				 address.getCity(), address.getCountryCode(), address.getPhone(), 
				 address.getFax());
		 
		 address.setId(addressId);
		 user.setAddress(address);
		 
		 long personId = insertPerson(user.getFirstName(), user.getLastName(),
				 addressId, new java.sql.Date(user.getBirthday().toDate().getTime()));
		 
		 user.setPersonId(personId);
		 
		 long userId = insertCustomer(personId, user.getStatusCode());
		 user.setUserId(userId);
		 
		 return user;
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
