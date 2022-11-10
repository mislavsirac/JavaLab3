package hr.java.vjezbe.entitet;

import java.time.LocalDateTime;

/**
 * Ispit proveden u tercijarnoj obrazovnoj ustanovi.
 */

public final class Ispit implements Online{
    Predmet predmet;
    Student student;
    Integer ocjena;
    LocalDateTime datumIVrijeme;

    Dvorana dvorana;

    public Ispit(Predmet predmet, Student student, Integer ocjena, LocalDateTime datumIVrijeme, Dvorana dvorana) {
        this.predmet = predmet;
        this.student = student;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
        this.dvorana = dvorana;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getOcjena() {
        return ocjena;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }

    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }

    /**
     * Vraća ime korištenog programskog jezika.
     * @param imePrograma Naziv korištenog softvera.
     */

    @Override
    public String imePrograma(String imePrograma) {
        String programskiJezik = "nista";
        if(imePrograma.equals("intellij")){
            programskiJezik = "java";
        } else if (imePrograma.equals("rider")) {
            programskiJezik = "c#";
        }
        return programskiJezik;
    }
}
