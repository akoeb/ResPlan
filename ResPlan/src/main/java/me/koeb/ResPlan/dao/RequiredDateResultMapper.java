package me.koeb.ResPlan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.koeb.ResPlan.core.Address;
import me.koeb.ResPlan.core.Category;
import me.koeb.ResPlan.core.RequiredDate;

import org.joda.time.LocalDate;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class RequiredDateResultMapper implements ResultSetMapper<RequiredDate> {
	public RequiredDate map (int index, ResultSet r, StatementContext ctx) throws SQLException
	{
		LocalDate day = new LocalDate(r.getDate("day"));
		return new RequiredDate(r.getLong("required_date_id"), 
				new Category(r.getLong("category_id"), r.getString("name"), 
						r.getString("description"), r.getString("colour")), 
				new Address(r.getLong("address_id"), r.getString("line_1"),
	    				r.getString("line_2"),r.getString("zip"),r.getString("city"),
	    				r.getString("country_code"),r.getString("phone"),
	    				r.getString("fax")),
				r.getLong("date_id"), r.getString("weekday"), day, 
				r.getString("type"),	r.getTime("start_time"), r.getInt("duration"));
	}
}
