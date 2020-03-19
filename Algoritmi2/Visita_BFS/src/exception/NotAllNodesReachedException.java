package exception;

public class NotAllNodesReachedException extends Throwable {
    private static final long serialVersionUID = 1L;

    String message;

    public NotAllNodesReachedException() {

    }

    public NotAllNodesReachedException(String messaggio) {
        message = messaggio;
    }

    public String getMessage() {
        return message;
    }
}
