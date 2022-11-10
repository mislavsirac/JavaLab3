package hr.java.vjezbe.entitet;

/**
 * Predmet koji se izvodi u tercijarnoj obrazovnoj ustanovi.
 */

public class Predmet {
    String sifra;
    String naziv;
    Integer brojEctsBodova;
    Profesor nositelj;
    Integer studenti;

    public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj, Integer studenti) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.studenti = studenti;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrojEctsBodova() {
        return brojEctsBodova;
    }

    public void setBrojEctsBodova(Integer brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }

    public Profesor getNositelj() {
        return nositelj;
    }

    public void setNositelj(Profesor nositelj) {
        this.nositelj = nositelj;
    }

    public Integer getStudenti() {
        return studenti;
    }

    public void setStudenti(Integer studenti) {
        this.studenti = studenti;
    }
}
