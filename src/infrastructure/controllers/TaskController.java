package infrastructure.controllers;

import app.IAuthService;
import app.ITaskService;
import infrastructure.builder.Builder;

import infrastructure.builder.Built;
import infrastructure.dtos.RequestDTO;
import infrastructure.dtos.ResponseDTO;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;


@Path("/task")
public class TaskController {
    @Inject
    @Built
    private ITaskService ITS;
    @Inject
    @Built
    private IAuthService IAS;

    @Context
    private HttpHeaders headers;
    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response postTask(String bodyJSON) {
        Jsonb jsonb = JsonbBuilder.create();
        String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        if (!IAS.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(jsonb.toJson(new ResponseDTO().setMessage("Bad authorization"))).build();
        }
        try {
            System.out.println("posting task");
            RequestDTO requestDTO = jsonb.fromJson(bodyJSON, RequestDTO.class);
            requestDTO.login = IAS.getUserInfo(token).get("login");
            System.out.println("requestDTO created");
            System.out.println("login = " + requestDTO.login + " value1 = " + requestDTO.value1 + " value2 = " + requestDTO.value2);
            int result = ITS.createTask(requestDTO.login, requestDTO.value1, requestDTO.value2);
            if (result != -1) {
                String resultJSON = jsonb.toJson(new ResponseDTO().setMessage(Integer.toString(result)));
                return Response.ok(resultJSON).build();
            } else {
                throw new Exception("error while creating task");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(new ResponseDTO().setMessage(e.getMessage()))).build();
        }
    }

    @DELETE
    @Path("/")
    public Response deleteTask(String bodyJSON) {
        Jsonb jsonb = JsonbBuilder.create();
        try {
            RequestDTO requestDTO = jsonb.fromJson(bodyJSON, RequestDTO.class);
            int result = ITS.deleteTask(1) ? 1 : -1;
            if (result != -1) {
                String resultJSON = jsonb.toJson(new ResponseDTO().setMessage(Integer.toString(result)));
                return Response.ok(resultJSON).build();
            } else {
                throw new Exception("error while deleting task");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(new ResponseDTO().setMessage(e.getMessage()))).build();
        }
    }
}
