package exception;

public class NameFormatException extends Exception{
    public NameFormatException() {
        super ("Formato del nome non valido!");
    }
}