package infrastructure.controllers.in;

import app.api.IAuthService;

import app.api.IRegisterService;
import infrastructure.builder.Built;
import app.dtos.DTO;
import infrastructure.dtos.ResponseDTO;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.*;


import jakarta.ws.rs.core.Response;


@Path("/auth")
public class AuthController {
    @Inject
    @Built
    private IAuthService IAS;

    @Inject
    @Built
    private IRegisterService IRS;

    @GET
    @Path("/")
    @Produces("application/json")
    public Response login(@QueryParam("login") String login, @QueryParam("password") String password) {
        Jsonb jsonb = JsonbBuilder.create();
        System.out.println("AUTH: " + login + " " + password);
        try {
            String resultJSON;
            DTO user = new DTO();
            user.login = login;
            user.password = password;
            String token = IAS.login(user);
            if (token != null) {
                resultJSON = jsonb.toJson(new ResponseDTO().setMessage(token));
                return Response.ok(resultJSON).build();
            } else {
                throw new Exception("invalid credentials");
            }
        } catch (Exception e) {
            ResponseDTO response = new ResponseDTO().setMessage(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(response)).build();
        }
    }

    @POST
    @Path("/")
    @Consumes("application/json")
    public Response register(String bodyJSON) throws Exception {
        Jsonb jsonb = JsonbBuilder.create();
        try {
            String resultJSON;
            DTO user = jsonb.fromJson(bodyJSON, DTO.class);
            System.out.println("REGISTER: " + user.login + " " + user.password);
            String token = IRS.register(user);
            resultJSON = jsonb.toJson(new ResponseDTO().setMessage(token));
            return Response.ok(resultJSON).build();
        } catch (JsonbException e) {
            ResponseDTO response = new ResponseDTO().setMessage(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(response)).build();
        }
    }
}
