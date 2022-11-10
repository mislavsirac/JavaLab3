package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

/**
 * Interface za smjerove na visokoškolskoj razini.
 */

public interface Visokoskolska {
    /**
     * Računa konačnu ocjenu iz danih ispita. Nije dozvoljena negativna ocjena.
     * @param ispiti Ispiti iz kojih se uzimaju ocjene. Očekuje se filtriran popis ispita za jednog studenta.
     * @param ocjenaPismenogDijela Ocjena dobivena za završni rad.
     * @param ocjenaObraneZavrsnogRada Ocjena dobivena za obranu završnog rada.
     * @return Konačna ocjena studenta.
     */
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, Integer ocjenaPismenogDijela, Integer ocjenaObraneZavrsnogRada);

    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti){
        return null;
    }
    /**
     * Računa prosječnu ocjenu iz danih ispita. Nije dozvoljena negativna ocjena.
     * @param ispiti Ispiti iz kojih se uzimaju ocjene. Očekuje se filtriran popis ispita za jednog studenta.
     * @return Prosječna ocjena studenta na ispitima.
     * @throws NemoguceOdreditiProsjekStudentaException Ako se pronađe ispit s negativnom ocjenom.
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti) throws NemoguceOdreditiProsjekStudentaException {
        BigDecimal prosjekOcjena = BigDecimal.valueOf(0);
        BigDecimal zbrojOcjena = BigDecimal.valueOf(0);
        BigDecimal brojOcjena = BigDecimal.valueOf(ispiti.length);
        for (Ispit ispit: ispiti) {
            if(ispit.ocjena == 1){
                throw new NemoguceOdreditiProsjekStudentaException("Student " + ispit.student.getIme() + "je dobio ocjenu 1 na ispitu te se ne moze izraditi prosjek ocjena");
            }
            zbrojOcjena = BigDecimal.valueOf(ispit.ocjena).add(zbrojOcjena);
        }
        prosjekOcjena = zbrojOcjena.divide(brojOcjena);
        return prosjekOcjena;
    }
    /**
     * Vraća sve ispite koje je pisao određen student.
     * @param ispiti Svi ispiti.
     * @param student Student prema kojem se filtriraju ispiti.
     * @return Filtrirani ispiti.
     */
    default Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti, Student student){
        Ispit[] ispitiOdStudenta = new Ispit[ispiti.length];
        Integer i = 0;
        for (Ispit ispit: ispiti) {
            if(ispit.getStudent().equals(student)){
                ispitiOdStudenta[i] = ispit;
                i++;
            }
        }
        Ispit[] cleanedIspiti = Arrays.stream(ispitiOdStudenta).filter(Objects::nonNull).toArray(Ispit[]::new);
        return cleanedIspiti;
    }
}
