package hr.java.vjezbe.entitet;

/**
 * Bazna klasa za obrazovne ustanove.
 */

public abstract class ObrazovnaUstanova {
    private String naziv;
    private Predmet[] Predmeti;
    private Profesor[] Profesori;
    private Student[] Studenti;
    private Ispit[] Ispiti;

    public ObrazovnaUstanova(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
        this.naziv = naziv;
        Predmeti = predmeti;
        Profesori = profesori;
        Studenti = studenti;
        Ispiti = ispiti;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Predmet[] getPredmeti() {
        return Predmeti;
    }

    public void setPredmeti(Predmet[] predmeti) {
        Predmeti = predmeti;
    }

    public Profesor[] getProfesori() {
        return Profesori;
    }

    public void setProfesori(Profesor[] profesori) {
        Profesori = profesori;
    }

    public Student[] getStudenti() {
        return Studenti;
    }

    public void setStudenti(Student[] studenti) {
        Studenti = studenti;
    }

    public Ispit[] getIspiti() {
        return Ispiti;
    }

    public void setIspiti(Ispit[] ispiti) {
        Ispiti = ispiti;
    }


    /**
     * Pronalazi najuspješnijeg studenta na godini.
     * @param godina Godina za pretraživanje.
     * @return Najuspješniji student.
     */
    abstract Student odrediNajuspjesnijegStudentaNaGodini(Integer godina);

}
