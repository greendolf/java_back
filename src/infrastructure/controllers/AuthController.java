package infrastructure.controllers;

import app.IAuthService;

import infrastructure.builder.Built;
import infrastructure.dtos.ResponseDTO;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.GET;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;


@Path("/auth")
public class AuthController {
    @Inject
    @Built
    private IAuthService IAS;

    @GET
    @Path("/")
    @Produces("application/json")
    public Response login(@QueryParam("login") String login, @QueryParam("password") String password) {
        Jsonb jsonb = JsonbBuilder.create();
        System.out.println(login + " " + password);
        try {
            String resultJSON;
            String token = IAS.login(login, password);
            if (token != null) {
                resultJSON = jsonb.toJson(new ResponseDTO().setMessage(token));
                return Response.ok(resultJSON).build();
            } else {
                throw new Exception("invalid credentials");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(new ResponseDTO().setMessage(e.getMessage()))).build();
        }
    }
}
