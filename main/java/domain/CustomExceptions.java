package domain;

public class CustomExceptions extends Exception {

    public CustomExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomExceptions(String message) {
        super(message);
    }
}
