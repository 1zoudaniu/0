package safebox.yiye.com.safebox.exception;

/**
 * Name: CustomException
 * Author: aina
 * Email:
 * Comment: //TODO
 * Date: 2016-11-10 09:54
 */
public class CustomException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CustomException() {
        super();
    }

    public CustomException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public CustomException(String detailMessage) {
        super(detailMessage);
    }

    public CustomException(Throwable throwable) {
        super(throwable);
    }
}
