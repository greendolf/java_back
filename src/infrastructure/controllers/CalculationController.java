package infrastructure.controllers;



import app.ICalculationService;

import infrastructure.builder.Builder;
import infrastructure.dtos.ResponseDTO;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;


import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;


import java.util.Objects;

@Path("/calc")
public class CalculationController {
    static class RequestDTO {
        String token;
        int id;
    }

    @POST
    @Path("/sum")
    public Response startCalculationSum(String bodyJSON) {
        Jsonb jsonb = JsonbBuilder.create();
        try {
            RequestDTO requestDTO = jsonb.fromJson(bodyJSON, RequestDTO.class);
            ICalculationService cs = Builder.buildCalculationService();
            String calc = cs.sum(requestDTO.id);
            if (!Objects.equals(calc, "")) {
                String resultJSON = jsonb.toJson(new ResponseDTO().setMessage(calc));
                return Response.ok(resultJSON).build();
            } else {
                throw new Exception("calc is not defined");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(new ResponseDTO().setMessage(e.getMessage()))).build();
        }
    }
    @POST
    @Path("/sub")
    public Response startCalculationSub(String bodyJSON) {
        Jsonb jsonb = JsonbBuilder.create();
        try {
            RequestDTO requestDTO = jsonb.fromJson(bodyJSON, RequestDTO.class);
            ICalculationService cs = Builder.buildCalculationService();
            String calc = cs.sub(requestDTO.id);
            if (!Objects.equals(calc, "")) {
                String resultJSON = jsonb.toJson(new ResponseDTO().setMessage(calc));
                return Response.ok(resultJSON).build();
            } else {
                throw new Exception("calc is not defined");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(new ResponseDTO().setMessage(e.getMessage()))).build();
        }
    }
}

