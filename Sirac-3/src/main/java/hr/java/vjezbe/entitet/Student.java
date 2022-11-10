package hr.java.vjezbe.entitet;

import java.time.LocalDate;

/**
 * Student na tercijarnoj obrazovnoj ustanovi.
 */

public class Student extends Osoba{
    private String ime;
    private String prezime;
    private String jmbag;
    private LocalDate datumRodjenja;
    private Integer ocjenaZavrsnogRada;
    private Integer ocjenaObraneZavrsnogRada;
    private Boolean mogucnostIzlaskaNaZavrsniRad;

    public void setMogucnostIzlaskaNaZavrsniRad(Boolean mogucnostIzlaskaNaZavrsniRad) {
        this.mogucnostIzlaskaNaZavrsniRad = mogucnostIzlaskaNaZavrsniRad;
    }

    public Boolean getMogucnostIzlaskaNaZavrsniRad() {
        return mogucnostIzlaskaNaZavrsniRad;
    }

    public Student() {
        super();
    }

    public Integer getOcjenaZavrsnogRada() {
        return ocjenaZavrsnogRada;
    }

    public void setOcjenaZavrsnogRada(Integer ocjenaZavrsnogRada) {
        this.ocjenaZavrsnogRada = ocjenaZavrsnogRada;
    }

    public Integer getOcjenaObraneZavrsnogRada() {
        return ocjenaObraneZavrsnogRada;
    }

    public void setOcjenaObraneZavrsnogRada(Integer ocjenaObraneZavrsnogRada) {
        this.ocjenaObraneZavrsnogRada = ocjenaObraneZavrsnogRada;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public Student(String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(ime, prezime);
        this.ime = ime;
        this.prezime = prezime;
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
    }
}
