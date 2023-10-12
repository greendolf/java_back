package infrastructure.controllers;


import app.ITasksService;
import infrastructure.builder.Builder;
import infrastructure.dtos.ResponseDTO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/tasks")
public class TasksController {

    @GET
    @Path("/")
    public Response service(@QueryParam("token") String token, @QueryParam("login") String login) {
        Jsonb jsonb = JsonbBuilder.create();
        try {
            ITasksService ts = Builder.buildTasksService();
            String result = ts.getTasks(login);
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
