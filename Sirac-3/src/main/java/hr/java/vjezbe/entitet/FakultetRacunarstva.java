package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Diplomska obrazovna ustanova za računarstvo.
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    public FakultetRacunarstva(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    @Override
    public Student odrediStudentaZaRektorovuNagradu() throws PostojiViseNajmladjihStudenataException{

        BigDecimal[] najvecaOcjena = new BigDecimal[getStudenti().length];

        Arrays.fill(najvecaOcjena,BigDecimal.ZERO);

        BigDecimal najvecaOcjena1 = new BigDecimal(0);
        Student[] najboljiStudenti = new Student[getStudenti().length];
        Student najboljiStudent = new Student();
        Integer i = 0;



        for (Student student: getStudenti()) {
            Ispit[] ispitiOdStudenta = filtrirajIspitePoStudentu(getIspiti(), student);
            BigDecimal konacnaOcjena = izracunajKonacnuOcjenuStudijaZaStudenta(ispitiOdStudenta, student.getOcjenaZavrsnogRada(), student.getOcjenaObraneZavrsnogRada());
            if(najvecaOcjena[i].compareTo(konacnaOcjena) == -1){
                najvecaOcjena1 = konacnaOcjena;
            }
            i++;
        }
        i=0;
        for (Student student: getStudenti()) {
            Ispit[] ispitiOdStudenta = filtrirajIspitePoStudentu(getIspiti(), student);
            BigDecimal konacnaOcjena = izracunajKonacnuOcjenuStudijaZaStudenta(ispitiOdStudenta, student.getOcjenaZavrsnogRada(), student.getOcjenaObraneZavrsnogRada());
            if(konacnaOcjena.compareTo(najvecaOcjena1) == 0){
                najvecaOcjena[i] = najvecaOcjena1;
                najboljiStudenti[i] = student;
            }
            i++;
        }
        Student[] cleanedStudenti = Arrays.stream(najboljiStudenti).filter(Objects::nonNull).toArray(Student[]::new);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String datum = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        LocalDate trenutniDatum = LocalDate.parse(datum, dtf);

        for (Student student: cleanedStudenti) {
            if(student.getDatumRodjenja().isBefore(trenutniDatum)){
                najboljiStudent = student;
                trenutniDatum = student.getDatumRodjenja();
            }
        }

        Student[] istiStudenti = new Student[cleanedStudenti.length];

        Integer counter = 0;
        for (Student student: cleanedStudenti) {
            if(student.getDatumRodjenja().equals(trenutniDatum)){
                istiStudenti[counter] = student;
                counter++;
            }
        }

        Student[] cleanedIstiStudenti = Arrays.stream(istiStudenti).filter(Objects::nonNull).toArray(Student[]::new);

        String outputMessage ="";

        for (Student student: cleanedIstiStudenti) {
            outputMessage = outputMessage + " " + student.getIme();
        }


        if(counter>1){
            logger.error("Ima vise najmladih studenata koji su : " + outputMessage);
            throw new PostojiViseNajmladjihStudenataException("Ima vise najmladih studenata koji su : " + outputMessage);
        }


        return najboljiStudent;
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {
        Student najboljiStudent = new Student();
        Ispit[] ispitiOdOveGodine = new Ispit[getIspiti().length];
        Integer i = 0;
        Integer brojOcjena = 0;
        for (Ispit ispit: getIspiti()) {
            if(ispit.getDatumIVrijeme().getYear() == godina){
                ispitiOdOveGodine[i] = ispit;
                i++;
            }
        }

        for (Student student: getStudenti()) {
            Ispit[] ispitiOdStudenta = filtrirajIspitePoStudentu(ispitiOdOveGodine, student);
            Integer brojPetica = 0;
            for (Ispit ispit: ispitiOdStudenta) {
                if (ispit.getOcjena() == 5){
                    brojPetica++;
                }
            }
            if(brojPetica > brojOcjena){
                brojOcjena = brojPetica;
                najboljiStudent = student;
            }
        }

        return najboljiStudent;
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, Integer ocjenaPismenogDijela, Integer ocjenaObraneDiplomskogRada) {
        BigDecimal prosjekOcjena = null;
        try {
            prosjekOcjena = odrediProsjekOcjenaNaIspitima(ispiti);
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            logger.warn(e.getMessage());
            System.out.println(e.getMessage());;
            BigDecimal konacnaOcjena1 = BigDecimal.valueOf(1);
            return konacnaOcjena1;
        }
        BigDecimal konacnaOcjena = (prosjekOcjena.multiply(BigDecimal.valueOf(3)).add(BigDecimal.valueOf(ocjenaPismenogDijela)).add(BigDecimal.valueOf(ocjenaObraneDiplomskogRada))).divide(BigDecimal.valueOf(5));
        return konacnaOcjena;
    }
}
