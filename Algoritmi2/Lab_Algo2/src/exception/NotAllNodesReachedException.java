/*
  @author Luca Borsalino
 * date: 2020-03-22
 *
 * Seconda esercitazione di laboratorio di Algoritmi2
 * Universita' del Piemonte Orientale (Alessandria) DISIT / Informatica.
 */

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
