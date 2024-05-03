package app.api;

import app.dtos.DTO;

public interface IRegisterService {
    public String register(DTO user) throws Exception;
}
