package Exception;

public class InvalidFoodException extends RuntimeException {
    public InvalidFoodException(String message) {
        super(message);
    }
}
