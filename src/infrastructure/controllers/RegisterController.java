package infrastructure.controllers;

import app.IRegisterService;
import infrastructure.builder.Builder;
import infrastructure.dtos.ResponseDTO;
import infrastructure.dtos.User;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Path("/register")
public class RegisterController {
    @POST
    @Path("/register")
    @Consumes("application/json")
    @Produces("application/json")
    public Response register(String credJSON) throws Exception {
        Jsonb jsonb = JsonbBuilder.create();
        try {
            String resultJSON;
            User user = jsonb.fromJson(credJSON, User.class);
            System.out.println(user.getLogin() + " " + user.getPassword());
            IRegisterService ts = Builder.buildRegisterService();
            String token = ts.register(user.getLogin(), user.getPassword());
            resultJSON = jsonb.toJson(new ResponseDTO().setMessage(token));
            return Response.ok(resultJSON).build();
        } catch (JsonbException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(new ResponseDTO().setMessage(e.getMessage()))).build();
        }
    }
}


