package me.koeb.ResPlan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import me.koeb.ResPlan.core.AvailableDate;
import org.joda.time.LocalDate;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class AvailableDateResultMapper implements ResultSetMapper<AvailableDate> {
	public AvailableDate map (int index, ResultSet r, StatementContext ctx) throws SQLException
	{
		LocalDate day = new LocalDate(r.getDate("day"));
		return new AvailableDate(r.getLong("available_date_id"),
				r.getString("availability_type"), r.getString("description"), 
				r.getLong("date_id"), r.getString("weekday"), day, 
				r.getString("type"), r.getTime("start_time"), r.getInt("duration"));
	}
}
