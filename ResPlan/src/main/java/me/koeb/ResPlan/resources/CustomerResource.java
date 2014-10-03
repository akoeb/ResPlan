package me.koeb.ResPlan.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.codahale.metrics.annotation.Timed;

import me.koeb.ResPlan.core.Customer;
import me.koeb.ResPlan.dao.CustomerDAO;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
	
	private CustomerDAO customerDAO;
	
	public CustomerResource(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}
	

	@GET
    @Timed
	public List<Customer>getAllCustomers() {
		List<Customer> retval = customerDAO.getAllCustomers();
		return retval;
	}

	@GET
    @Timed
	@Path("/{id}")
	public Customer getCustomer(@PathParam("id") long id){
		Customer retval = customerDAO.findCustomerById(id);
		if (retval == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return retval;
	}
	
	@POST
	@Timed
	public Response createCustomer(@Valid Customer customer) {
		Customer returnedCustomer = customerDAO.createCustomer(customer);
		long id = returnedCustomer.getCustomerId();
		URI location = UriBuilder.fromPath("/").path("/" + id).build();
		return Response.created(location).build();
	}
		 
	@PUT
    @Timed
	@Path("/{id}")
	public Response updateCustomer(@PathParam("id") long id, @Valid Customer customer){
		Customer retval = customerDAO.findCustomerById(id);
		if (retval == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		// check whether the id and the references are correct:
		// (otherwise a user could send us manipulated ids and we would simply update
		// the referencing tables...)
		if( retval.getCustomerId() != id ||
			retval.getPersonId() != customer.getPersonId() ||
			retval.getAddress().getId() != customer.getAddress().getId()) 
		{
			throw new WebApplicationException(Response.Status.CONFLICT);
		}
		
		
		customerDAO.updateCustomer(customer);
		return Response.noContent().build();
	}
	
}
