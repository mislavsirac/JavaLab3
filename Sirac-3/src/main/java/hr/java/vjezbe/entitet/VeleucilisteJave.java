package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Visokoškolska obrazovna ustanova za Javu.
 */
public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public VeleucilisteJave(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }


    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {
        BigDecimal najvecaOcjena = new BigDecimal(0);
        Student najboljiStudent = new Student();
        Ispit[] ispitiOdOveGodine = new Ispit[getIspiti().length];
        Integer i = 0;
        for (Ispit ispit: getIspiti()) {
            if(ispit.getDatumIVrijeme().getYear() == godina){
                ispitiOdOveGodine[i] = ispit;
                i++;
            }
        }

        for (Student student: getStudenti()) {
            Ispit[] ispitiOdStudenta = filtrirajIspitePoStudentu(ispitiOdOveGodine, student);
            BigDecimal konacnaOcjena = izracunajKonacnuOcjenuStudijaZaStudenta(ispitiOdStudenta, student.getOcjenaZavrsnogRada(), student.getOcjenaObraneZavrsnogRada());
            if(najvecaOcjena.compareTo(konacnaOcjena) == -1 || najvecaOcjena.compareTo(konacnaOcjena) == 0){
                najvecaOcjena = konacnaOcjena;
                najboljiStudent = student;
            }
        }

        return najboljiStudent;
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, Integer ocjenaPismenogDijela, Integer ocjenaObraneZavrsnogRada) {
        BigDecimal prosjekOcjena = null;
        try {
            prosjekOcjena = odrediProsjekOcjenaNaIspitima(ispiti);
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            logger.warn(e.getMessage());
            System.out.println(e.getMessage());
            BigDecimal konacnaOcjena1 = BigDecimal.valueOf(1);
            return konacnaOcjena1;
        }
        BigDecimal konacnaOcjena = (prosjekOcjena.multiply(BigDecimal.valueOf(2)).add(BigDecimal.valueOf(ocjenaPismenogDijela)).add(BigDecimal.valueOf(ocjenaObraneZavrsnogRada))).divide(BigDecimal.valueOf(4));
        return konacnaOcjena;
    }


}
