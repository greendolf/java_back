package infrastructure.controllers.in;


import app.api.IAuthService;
import app.api.ITasksService;

import infrastructure.builder.Built;
import infrastructure.dtos.ResponseDTO;

import jakarta.inject.Inject;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

@Path("/tasks")
public class TasksController {
    @Inject
    @Built
    private ITasksService ITsS;
    @Inject
    @Built
    private IAuthService IAS;
    @Context
    private HttpHeaders headers;

    @GET
    @Path("/")
    @Produces("application/json")
    public Response service() throws Exception {
        Jsonb jsonb = JsonbBuilder.create();
        String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        System.out.println("TOKEN: " + token);
        if (!IAS.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(jsonb.toJson(new ResponseDTO().setMessage("Bad authorization"))).build();
        }
        try {
            int id = IAS.getUserInfo(token).id;
            String result = ITsS.getTasks(id);
            if (result != null) {
                String resultJSON = jsonb.toJson(new ResponseDTO().setMessage(result));
                return Response.ok(resultJSON).build();
            } else {
                throw new Exception("error while getting tasks or tasks are empty");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(new ResponseDTO().setMessage(e.getMessage()))).build();
        }
    }
}
