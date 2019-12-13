package exception;

public class TimeFormatException extends Throwable {
    public TimeFormatException() {
        super ("Formato dell'ora non valido!");
    }
}
