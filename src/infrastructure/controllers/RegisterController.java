package infrastructure.controllers;

import app.IRegisterService;
import infrastructure.builder.Builder;
import infrastructure.builder.Built;
import infrastructure.dtos.ResponseDTO;
import infrastructure.dtos.User;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;


@Path("/register")
public class RegisterController {
    @Inject
    @Built
    private IRegisterService IRS;
    @POST
    @Path("/")
    @Consumes("application/json")
    public Response register(String credJSON) throws Exception {
        Jsonb jsonb = JsonbBuilder.create();
        try {
            String resultJSON;
            User user = jsonb.fromJson(credJSON, User.class);
            System.out.println(user.getLogin() + " " + user.getPassword());
            String token = IRS.register(user.getLogin(), user.getPassword());
            resultJSON = jsonb.toJson(new ResponseDTO().setMessage(token));
            return Response.ok(resultJSON).build();
        } catch (JsonbException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(new ResponseDTO().setMessage(e.getMessage()))).build();
        }
    }
}


