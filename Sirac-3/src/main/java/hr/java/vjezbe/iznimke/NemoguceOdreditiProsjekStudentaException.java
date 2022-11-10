package hr.java.vjezbe.iznimke;

/**
 * Iznimka koja se baci kada nije moguće odrediti prosjek studenta.
 */
public class NemoguceOdreditiProsjekStudentaException extends Exception{

    public NemoguceOdreditiProsjekStudentaException() {
        super("Dogodila se pogreška u radu programa!");
    }
    public NemoguceOdreditiProsjekStudentaException(String message) {
        super(message);
    }

    public NemoguceOdreditiProsjekStudentaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NemoguceOdreditiProsjekStudentaException(Throwable cause) {
        super(cause);
    }
}
