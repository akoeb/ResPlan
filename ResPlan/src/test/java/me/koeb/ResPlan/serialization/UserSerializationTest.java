package me.koeb.ResPlan.serialization;
import static io.dropwizard.testing.FixtureHelpers.*;
import static org.fest.assertions.api.Assertions.assertThat;
import io.dropwizard.jackson.Jackson;
import me.koeb.ResPlan.core.Address;
import me.koeb.ResPlan.core.User;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserSerializationTest {
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

	// test serialization
	@Test
	public void serializesUserToJSON() throws Exception {
		final User user = new User(177, "ACTIVE", 777, 355,
				"First Name",
				"Last Name",
				new Address(687, "Address Line 1", "Address Line 2", 
						"Zip", "City", "DE", 
						"Phone", "Fax"), 
						new LocalDate("1993-07-10"));
		
		String jacksonJSON = MAPPER.writeValueAsString(user);
        String jsonFixture = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/user.json"), User.class));
        assertThat(jacksonJSON).isEqualTo(jsonFixture);
	}
}
