/**
 * 
 */
package me.koeb.ResPlan.dao;


import java.util.List;

import me.koeb.ResPlan.core.Address;
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
	private static final String USER_FIELDLIST = "p.id as person_id, "
			+ "p.first_name, p.last_name, p.birthday, "
			+ "u.id as user_id, u.status_code, u.account_id, "
			+ "a.id as address_id, a.line_1, a.line_2, a.zip, "
			+ "a.city, a.country_code, a.phone, a.fax ";

	@SqlQuery("select " + USER_FIELDLIST
			+ "from users u, persons p, addresses a "
			+ "where u.person_id = p.id "
			+ "and p.address_id = a.id ")
	public abstract List<User> getAllUsers();
	
	
	@SqlQuery("select " + USER_FIELDLIST
			+ "from users u, persons p, addresses a "
			+ "where u.person_id = p.id "
			+ "and p.address_id = a.id "
			+ "and u.id = :id ")
	public abstract User findUserById(@Bind("id") long id);
	

	
	/**
	 * select a list of all availableDates for one Customer
	 * 
	 * @param id
	 * @return
	 */
	@SqlQuery("SELECT ad.id as available_date_id, "
			+ "ad.availability_type, ad.description, "
			+ "d.id as date_id, "
			+ "d.weekday, d.date, d.type, d.start_time, d.duration, "
			+ "a.id as address_id, a.line_1, a.line_2, a.zip, "
			+ "a.city, a.country_code, a.phone, a.fax "
			+ "FROM addresses a, dates d, user_available_dates ad "
			+ "WHERE r.customer_id = :id "
			+ "AND ad.date_id = d.id "
			+ "AND d.address_id = a.id ")
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
		 
		 // the accountId may be null, in which case the primitive accountId would be 0
		 Long accountId = user.getAccountId();
		 if (accountId == 0) {
			 accountId = null;
		 }
		 
		 long userId = insertUser(personId, user.getStatusCode(), accountId);
		 user.setUserId(userId);
		 
		 return user;
	}
	
	@Transaction()
	public void updateUser(User user) {
		 
		 // three tables are involved: address, person and customer
		 
		 Address address = user.getAddress();
		 updateAddress(address.getId(),
				 address.getLine1(), address.getLine2(), address.getZip(), 
				 address.getCity(), address.getCountryCode(), address.getPhone(), 
				 address.getFax());
		 		 
		 updatePerson(user.getPersonId(), user.getFirstName(), 
				 user.getLastName(), address.getId(), 
				 new java.sql.Date(user.getBirthday().toDate().getTime()));
		 
		 
		 updateUser(user.getPersonId(), user.getPersonId(), user.getStatusCode(), user.getAccountId());
		 
	}
	
	
	
	
	/**
	 * close with no args is used to close the connection
	 */
	abstract void close();
}
