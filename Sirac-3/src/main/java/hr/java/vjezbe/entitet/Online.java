package hr.java.vjezbe.entitet;

/**
 * Interface za ispite koji se održavaju online.
 */

public sealed interface Online permits Ispit{
    String imePrograma(String imePrograma);
}
