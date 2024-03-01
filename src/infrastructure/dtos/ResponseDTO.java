package infrastructure.dtos;

public class ResponseDTO {
    private String message;

    public ResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }
}
