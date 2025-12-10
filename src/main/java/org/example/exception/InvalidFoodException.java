package org.example.exception;

/**
 * Exception thrown when a food item is considered invalid within the context
 * of the application.
 * <p>
 * This exception extends {@link RuntimeException}, meaning it does not need
 * to be explicitly declared in method signatures.
 * </p>
 * <p>
 * It can be used to indicate:
 * </p>
 * <ul>
 *     <li>A non-existent or unrecognized food item;</li>
 *     <li>An incorrect value (null, empty, or invalid format);</li>
 *     <li>A business-rule violation related to food processing.</li>
 * </ul>
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * if (foodName == null || foodName.isEmpty()) {
 *     throw new InvalidFoodException("Food name is invalid");
 * }
 * }
 * </pre>
 */
public class InvalidFoodException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message explaining why the food item is invalid
     */
    public InvalidFoodException(String message) {
        super(message);
    }
}
