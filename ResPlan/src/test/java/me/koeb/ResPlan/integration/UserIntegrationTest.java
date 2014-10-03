package me.koeb.ResPlan.integration;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import me.koeb.ResPlan.ResPlanApplication;
import me.koeb.ResPlan.ResPlanConfiguration;
import me.koeb.ResPlan.core.Address;
import me.koeb.ResPlan.core.User;
import me.koeb.ResPlan.dao.UserDAO;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;

import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.junit.DropwizardAppRule;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



// .DropwizardAppRuleTest.;
public class UserIntegrationTest {
	
    @ClassRule
    public static final DropwizardAppRule<ResPlanConfiguration> RULE =
            new DropwizardAppRule<>(ResPlanApplication.class, resourceFilePath("/home/koebi/projects/einsatzplanung/ResPlan/ResPlan.yml"));
    
    private String URL;

    private Client client;
    private User user;
    private UserDAO dao;
    
    @Before
    public void setup() throws ClassNotFoundException {
    	URL = String.format("http://localhost:%d/user", RULE.getLocalPort());
    	client = new Client();
    	ResPlanConfiguration configuration = RULE.getConfiguration();
    	Environment environment = RULE.getEnvironment();
    	final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");
        dao = jdbi.onDemand(UserDAO.class);

        User firstUser = userGenerator();
         // make sure we have at least one user before tests begin
        user = dao.createUser(firstUser);
    }
    
    
    @Test
    public void createUserWorksCorrectly() {
    	
    	User newUser = userGenerator();
    	
    	// insert the class object user:
    	ClientResponse response = client.resource(URL)
    			.type(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.post(ClientResponse.class, newUser);
    	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    	assertTrue(response.getHeaders().containsKey("Location"));
    	
    	// read the Location Header, get the created id:
    	URI redirect = response.getLocation();
    	assertNotNull(redirect);
    	
    	// read id from redirect path:
    	Pattern pattern = Pattern.compile("^/user/(\\d+)$");
    	Matcher m = pattern.matcher(redirect.getPath());
    	long id = 0;
    	if(m.matches()) {
    	    id = Long.parseLong(m.group(1));
    	}
    	
    	// and check it is a valid id
    	assertTrue(id > 0);
    	
    	User retrievedUser = dao.findUserById(id);
    	assertNotNull(retrievedUser);
    	assertTrue(retrievedUser.getUserId() > 0);
    	assertTrue(retrievedUser.getPersonId() > 0);
    	assertTrue(retrievedUser.getAddress().getId() > 0);
    	
    	// set the missing ids in user object:
    	newUser.setUserId(id);
    	newUser.setPersonId(retrievedUser.getPersonId());
    	newUser.setAddressId(retrievedUser.getAddress().getId());
    	
    	// and finally compare the two objects
    	assertEquals(newUser.toString(), retrievedUser.toString());
 
    }
    @Test
    public void updateOnNonExistentUser() {
    	User newUser = userGenerator();

    	// check status code when querying a user that does not exist
    	ClientResponse response = client.resource(URL +  "/87432498" )
    			.type(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.put(ClientResponse.class, newUser);

       	assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String resultString = response.getEntity(String.class);
        assertTrue(resultString == null || resultString.length() == 0);
    }

    @Test
    public void updateErrorsOnWrongReferenceIds() {
    	// get a new user and DON'T set the correct referencial IDs:
    	User newUser = userGenerator();
    	// run an update query with this user object
    	ClientResponse response = client.resource(URL +  "/" + user.getUserId())
    			.type(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.put(ClientResponse.class, newUser);

       	assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }
    
    @Test 
    public void updateUserWorksCorrectly() {
    	
    	// get a new user and set the correct referencial IDs:
    	User newUser = userGenerator();
    	newUser.setUserId(user.getUserId());
    	newUser.setPersonId(user.getPersonId());
    	newUser.setAddressId(user.getAddress().getId());

    	// run an update query with this user object
    	ClientResponse response = client.resource(URL +  "/" + user.getUserId())
    			.type(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.put(ClientResponse.class, newUser);

       	assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    	
    	
    	User retrievedUser = dao.findUserById(user.getUserId());
    	assertNotNull(retrievedUser);
    	
    	// and finally compare the two objects
    	assertEquals(newUser.toString(), retrievedUser.toString());
    	
    	// set the global user for use in the next test
    	user = newUser;
    }
    

    @Test
    public void viewUserWorksCorrectly() {
    	ClientResponse response = client.resource(URL +  "/" + user.getUserId())
    			.type(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.get(ClientResponse.class);

       	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        User receivedUser = response.getEntity(User.class);
        assertNotNull(receivedUser);
    	assertEquals(user.toString(), receivedUser.toString());
    }

    @Test
    public void viewUserOnNonExistentUser() {
    	// check status code when querying a user that does not exist
    	ClientResponse response = client.resource(URL +  "/87432498" )
    			.type(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.get(ClientResponse.class);

       	assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String resultString = response.getEntity(String.class);
        assertTrue(resultString == null || resultString.length() == 0);
    }

    @Test
    public void viewAllUsers() throws JsonParseException, JsonMappingException, IOException {
    	List<User> inserted = new ArrayList<User>();
    	inserted.add(user);
    	
    	// insert another 5 users
    	for (int i = 0; i <= 4; i++) {
    		User user = dao.createUser(userGenerator());
    		inserted.add(user);
    	}
    	ClientResponse response = client.resource(URL).type(MediaType.APPLICATION_JSON)
    			.get(ClientResponse.class);

       	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String content = response.getEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<User> receivedList = mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        
        assertTrue(receivedList.size() > 6);
         
        // now check that every element in our inserted list is also in the received list
        for (int i = 0; i< inserted.size(); i++) {
        	User ins = inserted.get(i);
        	assertTrue(receivedList.contains(ins));
        }
    }
    

    public static String resourceFilePath(String resourceClassPathLocation) {
    	URI uri;
    	try {
    		// check whether file is in classpath:
    		uri = Resources.getResource(resourceClassPathLocation).toURI();
    		return new File(uri).getAbsolutePath();
    	} catch (Exception e) {
    		try {
    			// not found in classpath, look locally:
    			return new File(resourceClassPathLocation).getAbsolutePath();
    			
    		} catch (Exception e2) {
    			throw new RuntimeException(" resource file " + resourceClassPathLocation + "Not found, neither in classpath norlocally");
    		}    		
    	}
    }
    
    private static User userGenerator() {
    	AtomicInteger counter = new AtomicInteger();
    	int id = counter.getAndIncrement();
    	
  
    	LocalDate birthday = new LocalDate("1993-07-10");
     	
    	return new User(id, "ACTIVE", 0, "First Name #"+ id,
    			"Last Name #" + id,
    			new Address(0, "Line1 #" +id, "Line2 #"+id, 
    					"Zip #"+id, "City #"+id, "DE", 
    					"Phone #"+ id, "Fax #"+id), 
    			birthday);
    }
}
