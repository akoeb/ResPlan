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
import me.koeb.ResPlan.core.Customer;
import me.koeb.ResPlan.dao.CustomerDAO;

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
public class CustomerIntegrationTest {
	
    @ClassRule
    public static final DropwizardAppRule<ResPlanConfiguration> RULE =
            new DropwizardAppRule<>(ResPlanApplication.class, resourceFilePath("/home/koebi/projects/einsatzplanung/ResPlan/ResPlan.yml"));
    
    private String URL;

    private Client client;
    private Customer customer;
    private CustomerDAO dao;
    
    @Before
    public void setup() throws ClassNotFoundException {
    	URL = String.format("http://localhost:%d/customer", RULE.getLocalPort());
    	client = new Client();
    	ResPlanConfiguration configuration = RULE.getConfiguration();
    	Environment environment = RULE.getEnvironment();
    	final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");
        dao = jdbi.onDemand(CustomerDAO.class);

        Customer firstCustomer = customerGenerator();
         // make sure we have at least one customer before tests begin
        customer = dao.createCustomer(firstCustomer);
    }
    
    
    @Test
    public void createCustomerWorksCorrectly() {
    	
    	Customer newCustomer = customerGenerator();
    	
    	// insert the class object customer:
    	ClientResponse response = client.resource(URL)
    			.type(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.post(ClientResponse.class, newCustomer);
    	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    	assertTrue(response.getHeaders().containsKey("Location"));
    	
    	// read the Location Header, get the created id:
    	URI redirect = response.getLocation();
    	assertNotNull(redirect);
    	
    	// read id from redirect path:
    	Pattern pattern = Pattern.compile("^/customer/(\\d+)$");
    	Matcher m = pattern.matcher(redirect.getPath());
    	long id = 0;
    	if(m.matches()) {
    	    id = Long.parseLong(m.group(1));
    	}
    	
    	// and check it is a valid id
    	assertTrue(id > 0);
    	
    	Customer retrievedCustomer = dao.findCustomerById(id);
    	assertNotNull(retrievedCustomer);
    	assertTrue(retrievedCustomer.getCustomerId() > 0);
    	assertTrue(retrievedCustomer.getPersonId() > 0);
    	assertTrue(retrievedCustomer.getAddress().getId() > 0);
    	
    	// set the missing ids in customer object:
    	newCustomer.setCustomerId(id);
    	newCustomer.setPersonId(retrievedCustomer.getPersonId());
    	newCustomer.setAddressId(retrievedCustomer.getAddress().getId());
    	
    	// and finally compare the two objects
    	assertEquals(newCustomer.toString(), retrievedCustomer.toString());
 
    }
    @Test
    public void updateOnNonExistentCustomer() {
    	Customer newCustomer = customerGenerator();

    	// check status code when querying a customer that does not exist
    	ClientResponse response = client.resource(URL +  "/87432498" )
    			.type(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.put(ClientResponse.class, newCustomer);

       	assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String resultString = response.getEntity(String.class);
        assertTrue(resultString == null || resultString.length() == 0);
    }

    @Test
    public void updateErrorsOnWrongReferenceIds() {
    	// get a new customer and DON'T set the correct referencial IDs:
    	Customer newCustomer = customerGenerator();
    	// run an update query with this customer object
    	ClientResponse response = client.resource(URL +  "/" + customer.getCustomerId())
    			.type(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.put(ClientResponse.class, newCustomer);

       	assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }
    
    @Test 
    public void updateCustomerWorksCorrectly() {
    	
    	// get a new customer and set the correct referencial IDs:
    	Customer newCustomer = customerGenerator();
    	newCustomer.setCustomerId(customer.getCustomerId());
    	newCustomer.setPersonId(customer.getPersonId());
    	newCustomer.setAddressId(customer.getAddress().getId());

    	// run an update query with this customer object
    	ClientResponse response = client.resource(URL +  "/" + customer.getCustomerId())
    			.type(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.put(ClientResponse.class, newCustomer);

       	assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    	
    	
    	Customer retrievedCustomer = dao.findCustomerById(customer.getCustomerId());
    	assertNotNull(retrievedCustomer);
    	
    	// and finally compare the two objects
    	assertEquals(newCustomer.toString(), retrievedCustomer.toString());
    	
    	// set the global customer for use in the next test
    	customer = newCustomer;
    }
    

    @Test
    public void viewCustomerWorksCorrectly() {
    	ClientResponse response = client.resource(URL +  "/" + customer.getCustomerId())
    			.type(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.get(ClientResponse.class);

       	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Customer receivedCustomer = response.getEntity(Customer.class);
        assertNotNull(receivedCustomer);
    	assertEquals(customer.toString(), receivedCustomer.toString());
    }

    @Test
    public void viewCustomerOnNonExistentCustomer() {
    	// check status code when querying a customer that does not exist
    	ClientResponse response = client.resource(URL +  "/87432498" )
    			.type(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON)
    			.get(ClientResponse.class);

       	assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String resultString = response.getEntity(String.class);
        assertTrue(resultString == null || resultString.length() == 0);
    }

    @Test
    public void viewAllCustomers() throws JsonParseException, JsonMappingException, IOException {
    	List<Customer> inserted = new ArrayList<Customer>();
    	inserted.add(customer);
    	
    	// insert another 5 customers
    	for (int i = 0; i <= 4; i++) {
    		Customer customer = dao.createCustomer(customerGenerator());
    		inserted.add(customer);
    	}
    	ClientResponse response = client.resource(URL).type(MediaType.APPLICATION_JSON)
    			.get(ClientResponse.class);

       	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String content = response.getEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<Customer> receivedList = mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(List.class, Customer.class));
        
        assertTrue(receivedList.size() > 6);
         
        // now check that every element in our inserted list is also in the received list
        for (int i = 0; i< inserted.size(); i++) {
        	Customer ins = inserted.get(i);
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
    
    private static Customer customerGenerator() {
    	AtomicInteger counter = new AtomicInteger();
    	int id = counter.getAndIncrement();
    	
  
    	LocalDate birthday = new LocalDate("1993-07-10");
     	
    	return new Customer(id, "ACTIVE", 0, "First Name #"+ id,
    			"Last Name #" + id,
    			new Address(0, "Line1 #" +id, "Line2 #"+id, 
    					"Zip #"+id, "City #"+id, "DE", 
    					"Phone #"+ id, "Fax #"+id), 
    			birthday);
    }
}
