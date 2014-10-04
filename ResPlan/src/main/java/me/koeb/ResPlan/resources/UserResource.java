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

import me.koeb.ResPlan.core.User;
import me.koeb.ResPlan.dao.UserDAO;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
	
	private UserDAO userDAO;
	
	public UserResource(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	

	@GET
    @Timed
	public List<User>getAllUsers() {
		List<User> retval = userDAO.getAllUsers();
		return retval;
	}

	@GET
    @Timed
	@Path("/{id}")
	public User getUser(@PathParam("id") long id){
		User retval = userDAO.findUserById(id);
		if (retval == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return retval;
	}
	
	@POST
	@Timed
	public Response createUser(@Valid User user) {
		User returnedUser = userDAO.createUser(user);
		long id = returnedUser.getUserId();
		URI location = UriBuilder.fromPath("/").path("/" + id).build();
		return Response.created(location).build();
	}
		 
	@PUT
    @Timed
	@Path("/{id}")
	public Response updateUser(@PathParam("id") long id, @Valid User user){
		User retval = userDAO.findUserById(id);
		if (retval == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		// check whether the id and the references are correct:
		// (otherwise a user could send us manipulated ids and we would simply update
		// the referencing tables...)
		if( retval.getUserId() != id ||
			retval.getPersonId() != user.getPersonId() ||
			retval.getAddress().getId() != user.getAddress().getId() ||
			retval.getAccountId() != user.getAccountId())
		{
			throw new WebApplicationException(Response.Status.CONFLICT);
		}
		
		// ok, perform the update:
		userDAO.updateUser(user);
		return Response.noContent().build();
	}
	
}
