package Exceptions;

public class NotAllNodesReachedException extends Throwable{
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
