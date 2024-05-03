package app.api;

import app.dtos.DTO;

import java.util.Map;

public interface IStorage {
    public int findUser(DTO user) throws Exception;
    public boolean addUser(DTO user) throws Exception;
    public String getTasks(int id) throws Exception;
    public int createTask(int id, int Nx, int Ny, double time_end, double length, double width, double lambda, double ro, double C, double T_left, double T_right, double T_start) throws Exception;
    public boolean deleteTask(int id);
    public boolean modifyTask(int id, double[][] result, String status);
    public Map<String, Double> getTaskValues(int id);

}
