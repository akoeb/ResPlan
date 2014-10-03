package me.koeb.ResPlan.dao;


import java.sql.Date;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface PersonDAO  {
	
	@SqlUpdate("insert into addresses "
			+ "(line_1, line_2, zip, city, country_code, phone, fax)"
			+ " values "
			+ "(:line1, :line2, :zip, :city, :countryCode, :phone, :fax)")
	@GetGeneratedKeys
	public long insertAddress(
			@Bind("line1") String line1, 
			@Bind("line2") String line2,
			@Bind("zip") String zip,
			@Bind("city") String city,
			@Bind("countryCode") String countryCode,
			@Bind("phone") String phone,
			@Bind("fax") String fax);
	
	@SqlUpdate("update addresses set "
			+ "line_1 = :line1, line_2 = :line2, "
			+ "zip = :zip, city = :city, country_code = :countryCode, "
			+ "phone = :phone, fax = :fax "
			+ "WHERE id = :id ")
	public void updateAddress(
			@Bind("id") long id,
			@Bind("line1") String line1, 
			@Bind("line2") String line2,
			@Bind("zip") String zip,
			@Bind("city") String city,
			@Bind("countryCode") String countryCode,
			@Bind("phone") String phone,
			@Bind("fax") String fax);
	
	@SqlUpdate("delete from addresses where id = :id")
	public void deleteAddresses(@Bind("id") long id);
	
	
	@SqlUpdate("insert into persons "
			+ "(first_name, last_name, address_id, birthday)"
			+ " values "
			+ "(:firstname, :lastname, :addressId, :birthday)")
	@GetGeneratedKeys
	public long insertPerson(
			@Bind("firstname") String firstname, 
			@Bind("lastname") String lastname,
			@Bind("addressId") long addressId,
			@Bind("birthday") Date birthday);
	
	@SqlUpdate("update persons set "
			+ "first_name = :firstname, last_name = :lastname, "
			+ "address_id = :addressId, birthday = :birthday "
			+ " where id = :id ")
	public void updatePerson(
			@Bind("id") long id,
			@Bind("firstname") String firstname, 
			@Bind("lastname") String lastname,
			@Bind("addressId") long addressId,
			@Bind("birthday") Date Birthday);

	@SqlUpdate("delete from persons where id = :id")
	public void deletePersons(@Bind("id") long id);
	
	
	@SqlUpdate("insert into customers "
			+ "(person_id, status_code)"
			+ " values "
			+ "(:personId, :statusCode)")
	@GetGeneratedKeys
	public long insertCustomer(@Bind("personId") long personId, @Bind("statusCode") String statusCode); 


	@SqlUpdate("update customers "
			+ "set "
			+ "person_id=:personId, "
			+ "status_code = :statusCode "
			+ "where id = :id")
	public void updateCustomer(@Bind("id") long id, @Bind("personId") long personId, @Bind("statusCode") String statusCode); 
	

	@SqlUpdate("delete from customers where id = :id")
	public void deleteCustomer(@Bind("id") long id);

	
}
