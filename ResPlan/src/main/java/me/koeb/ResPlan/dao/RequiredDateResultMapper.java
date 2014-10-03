package me.koeb.ResPlan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.koeb.ResPlan.core.Address;
import me.koeb.ResPlan.core.Category;
import me.koeb.ResPlan.core.RequiredDate;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class RequiredDateResultMapper implements ResultSetMapper<RequiredDate> {
	public RequiredDate map (int index, ResultSet r, StatementContext ctx) throws SQLException
	{
		return new RequiredDate(r.getLong("r.required_date_id"), 
				new Category(r.getLong("c.category_id"), r.getString("c.name"), 
						r.getString("c.description"), r.getString("c.colour")), 
				new Address(r.getLong("a.address_id"), r.getString("a.line_1"),
	    				r.getString("a.line_2"),r.getString("a.zip"),r.getString("a.city"),
	    				r.getString("a.country_code"),r.getString("a.phone"),
	    				r.getString("a.fax")),
				r.getLong("d.date_id"), r.getString("d.weekday"), r.getDate("d.date"), 
				r.getString("d.type"),	r.getTime("d.start_time"), r.getInt("d.duration"));
	}
}
