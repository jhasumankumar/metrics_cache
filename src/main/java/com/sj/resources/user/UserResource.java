package com.sj.resources.user;

import com.sj.model.UserModel;
import com.sj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Component
@Path("/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        UserModel userModel = userService.getUser(id);
        if(userModel != null) {
            return Response.status(200).entity(userModel).build();
        } else {
            return Response.status(404).entity("The UserModel with the id " + id + " does not exist").build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON})
    public Response createUserModelFromForm(
            @FormParam("username") String username,@FormParam("id") Long id ) {
        UserModel userModel = new UserModel(username);
        userModel.setId(id);
        userService.saveUser(userModel);
        return Response.status(201).entity(username).build();
    }
}
