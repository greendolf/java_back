package infrastructure.controllers.in;


import app.api.IAuthService;
import app.api.ICalculationService;

import infrastructure.builder.Built;
import app.dtos.DTO;
import infrastructure.dtos.ResponseDTO;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;


import java.util.Arrays;
import java.util.Objects;

@Path("/calculation")
public class CalculationController {
    @Inject
    @Built
    private ICalculationService ICS;
    @Inject
    @Built
    private IAuthService IAS;
    @Context
    private HttpHeaders headers;


    @POST
    @Path("/sum")
    @Consumes("application/json")
    public Response startCalculationSum(String bodyJSON) throws Exception {
        Jsonb jsonb = JsonbBuilder.create();
        String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        if (!IAS.validateToken(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(jsonb.toJson(new ResponseDTO().setMessage("Bad authorization"))).build();
        }
        try {
            DTO requestDTO = jsonb.fromJson(bodyJSON, DTO.class);
            double[][] calc = ICS.calculate(requestDTO.id);
            if (!Objects.equals(calc, "")) {
                String resultJSON = jsonb.toJson(new ResponseDTO().setMessage(Arrays.deepToString(calc)));
                return Response.ok(resultJSON).build();
            } else {
                throw new Exception("calc is not defined");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(new ResponseDTO().setMessage(e.getMessage()))).build();
        }
    }
}

