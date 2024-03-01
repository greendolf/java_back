package infrastructure.controllers;


import app.IAuthService;
import app.ICalculationService;

import infrastructure.builder.Builder;
import infrastructure.builder.Built;
import infrastructure.dtos.ResponseDTO;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;


import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;


import java.util.Objects;

@Path("/calc")
public class CalculationController {
    @Inject
    @Built
    private ICalculationService ICS;
    @Inject
    @Built
    private IAuthService IAS;
    @Context
    private HttpHeaders headers;

    static class RequestDTO {
        String token;
        int id;
    }

    @POST
    @Path("/sum")
    public Response startCalculationSum(String bodyJSON) {
        Jsonb jsonb = JsonbBuilder.create();
        String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        if (!IAS.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(jsonb.toJson(new ResponseDTO().setMessage("Bad authorization"))).build();
        }
        try {
            RequestDTO requestDTO = jsonb.fromJson(bodyJSON, RequestDTO.class);
            String calc = ICS.sum(requestDTO.id);
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
            String calc = ICS.sub(requestDTO.id);
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

