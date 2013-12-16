package ru.koleslena.shop.exception;

/**
 * @author koleslena
 *
 */
public class ShopException extends Exception {

	private static final long serialVersionUID = 1L;

	public ShopException(String message) {
        super(message);
    }

    public ShopException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShopException(Throwable cause) {
        super(cause);
    }
}
