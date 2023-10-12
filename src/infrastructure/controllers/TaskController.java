package infrastructure.controllers;

import app.ITaskService;
import infrastructure.builder.Builder;

import infrastructure.dtos.ResponseDTO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;


@Path("/task")
public class TaskController {

    static class RequestDTO {
        public String login;
        public String token;
        public int id;
        public int value1;
        public int value2;
    }
    @POST
    @Path("/")
    public Response getTask(String bodyJSON) {
        Jsonb jsonb = JsonbBuilder.create();
        try {
            RequestDTO requestDTO = jsonb.fromJson(bodyJSON, RequestDTO.class);
            ITaskService ts = Builder.buildTaskService();
            int result = -1;
            result = ts.createTask(requestDTO.login, requestDTO.value1, requestDTO.value2);
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
            ITaskService ts = Builder.buildTaskService();
            int result = ts.deleteTask(requestDTO.id) ? 1 : -1;
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
