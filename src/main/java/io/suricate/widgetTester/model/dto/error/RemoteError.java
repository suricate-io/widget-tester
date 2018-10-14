package io.suricate.widgetTester.model.dto.error;

public class RemoteError extends Exception {

    public RemoteError() {
        super();
    }

    public RemoteError(String message) {
        super(message);
    }

    public RemoteError(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteError(Throwable cause) {
        super(cause);
    }

    protected RemoteError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
