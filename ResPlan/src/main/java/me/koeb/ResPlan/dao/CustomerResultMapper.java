package me.koeb.ResPlan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.koeb.ResPlan.core.Address;
import me.koeb.ResPlan.core.Customer;

import org.joda.time.LocalDate;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class CustomerResultMapper implements ResultSetMapper<Customer>{
	public Customer map(int index, ResultSet r, StatementContext ctx) throws SQLException
	{
		LocalDate birthday = new LocalDate(r.getDate("birthday"));
	    return new Customer(r.getInt("customer_id"), r.getString("status_code"),
	    		r.getLong("person_id"),r.getString("first_name"),r.getString("last_name"),
	    		new Address(r.getLong("address_id"), r.getString("line_1"),
	    				r.getString("line_2"),r.getString("zip"),r.getString("city"),
	    				r.getString("country_code"),r.getString("phone"),
	    				r.getString("fax")),
	    		birthday);
	  }
}
