package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.rmi.UnexpectedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

/***
 * Glavna klasa s metodom main
 */

public class Glavna {

    private static final int BROJ_PROFESORA = 2;
    private static final int BROJ_PREDMETA = 2;
    private static final int BROJ_STUDENATA = 2;
    private static final int BROJ_ISPITA = 2;

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    /***
     * Početak programa
     * @param args argumenti iz komandne linije
     */

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        Integer brojUstanova = null;
        Boolean unos;
        do{
            unos = false;
            try{
                System.out.println("Unesite broj obrazovnih ustanova: ");
                brojUstanova = Integer.parseInt(sc.nextLine());
                logger.info("Unjeli ste broj ustanova: " + brojUstanova);

            }catch (NumberFormatException ex){
                logger.error("Unesena je ne numericka vrijednost");
                System.out.println("Morate unesti numericku vrijednust");
                unos = true;
            }
        }while (unos);
        ObrazovnaUstanova[] obrazovnaUstanova = new ObrazovnaUstanova[brojUstanova];

        for(int k=0;k<brojUstanova;k++) {

            Profesor[] profesori = new Profesor[BROJ_PROFESORA];
            Predmet[] predmeti = new Predmet[BROJ_PREDMETA];
            Student[] studenti = new Student[BROJ_STUDENATA];
            Ispit[] ispiti = new Ispit[BROJ_ISPITA];

            System.out.println("Unesite podatke za" + (k + 1) + ". obrazovnu ustanovu: ");

            for (int i = 0; i < BROJ_PROFESORA; i++) {
                System.out.println("Unesite " + (i + 1) + ". profesora: ");
                System.out.println("Unesite šifru profesora: ");
                String sifra = sc.nextLine();
                logger.info("Unesena sifra: " + sifra);
                System.out.println("Unesite ime profesora: ");
                String ime = sc.nextLine();
                System.out.println("Unesite prezime profesora: ");
                String prezime = sc.nextLine();
                System.out.println("Unesite titulu profesora: ");
                String titula = sc.nextLine();
                profesori[i] = new Profesor.Builder(sifra)
                        .saImenom(ime)
                        .saPrezimenom(prezime)
                        .saTitulom(titula)
                        .build();
            }

            for (int i = 0; i < BROJ_PREDMETA; i++) {
                System.out.println("Unesite " + (i + 1) + ". predmet: ");
                System.out.println("Unesite šifru predmeta: ");
                String sifra = sc.nextLine();
                System.out.println("Unesite naziv predmeta: ");
                String naziv = sc.nextLine();
                Integer brojBodova = null;

                do{
                    unos = false;
                    try{
                        System.out.println("Unesite broj ECTS bodova za predmet '" + naziv + "': ");
                        brojBodova = Integer.parseInt(sc.nextLine());
                        logger.info("Unjeli ste broj ECTS bodova: " + brojBodova);

                    }catch (NumberFormatException ex){
                        logger.error("Unesena je ne numericka vrijednost");
                        System.out.println("Morate unesti numeričku vrijednust");
                        unos = true;
                    }
                }while (unos);

                Integer brojProfesora = null;

                do{
                    unos = false;
                    try{
                        System.out.println("Odaberite profesora:");
                        for (int j = 0; j < BROJ_PROFESORA; j++) {
                            System.out.println((j + 1) + "." + profesori[j].getIme() + " " + profesori[j].getPrezime());
                        }
                        brojProfesora = Integer.parseInt(sc.nextLine());
                        logger.info("Unjeli ste broj profesora : " + brojProfesora);

                    }catch (NumberFormatException ex){
                        logger.error("Unesena je ne numericka vrijednost");
                        System.out.println("Morate unesti numeričku vrijednust");
                        unos = true;
                    }
                }while (unos);

                Profesor profesor = profesori[(brojProfesora - 1)];
                Integer brojStudenata = null;

                do{
                    unos = false;
                    try{
                        System.out.println("Unesite broj studenata za predmet '" + naziv + "': ");
                        brojStudenata = Integer.parseInt(sc.nextLine());
                        logger.info("Unjeli ste broj studenata : " + brojStudenata);

                    }catch (NumberFormatException ex){
                        logger.error("Unesena je ne numericka vrijednost");
                        System.out.println("Morate unesti numeričku vrijednust");
                        unos = true;
                    }
                }while (unos);

                predmeti[i] = new Predmet(sifra, naziv, brojBodova, profesor, brojStudenata);
            }


            for (int i = 0; i < BROJ_STUDENATA; i++) {
                System.out.println("Unesite " + (i + 1) + ". studenta: ");
                System.out.println("Unesite ime studenta: ");
                String imeStudenta = sc.nextLine();
                System.out.println("Unesite prezime studenta: ");
                String prezime = sc.nextLine();
                System.out.println("Unesite datum rođenja studenta " + imeStudenta + " " + prezime + " u formatu (dd.MM.yyyy.): ");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                LocalDate datum = LocalDate.parse(sc.nextLine(), dtf);
                System.out.println("Unesite JMBAG studenta: ");
                String jmbag = sc.nextLine();
                studenti[i] = new Student(imeStudenta, prezime, jmbag, datum);
            }


            for (int i = 0; i < BROJ_ISPITA; i++) {
                System.out.println("Unesite " + (i + 1) + ". ispitni rok: ");

                Integer brojPredmeta = null;

                do{
                    unos = false;
                    try{
                        System.out.println("Odaberite predmet: ");
                        for (int j = 0; j < BROJ_PREDMETA; j++) {
                            System.out.println((j + 1) + ". " + predmeti[j].getNaziv());
                        }
                        brojPredmeta = Integer.parseInt(sc.nextLine());
                        logger.info("Unjeli ste broj predmeta : " + brojPredmeta);

                    }catch (NumberFormatException ex){
                        logger.error("Unesena je ne numericka vrijednost");
                        System.out.println("Morate unesti numeričku vrijednust");
                        unos = true;
                    }
                }while (unos);

                Predmet predmet = predmeti[(brojPredmeta - 1)];
                System.out.println("Unesite naziv dvorane: ");
                String dvorana = sc.nextLine();
                System.out.println("Unesite zgradu dvorane: ");
                String zgrada = sc.nextLine();
                Dvorana dvorana1 = new Dvorana(dvorana,zgrada);

                Integer brojStudenta = null;

                do{
                    unos = false;
                    try{
                        System.out.println("Odaberite studenta: ");
                        for (int j = 0; j < BROJ_STUDENATA; j++) {
                            System.out.println((j + 1) + ". " + studenti[j].getIme() + " " + studenti[j].getPrezime());
                        }
                        brojStudenta = Integer.parseInt(sc.nextLine());
                        logger.info("Unjeli ste broj studenta : " + brojStudenta);

                    }catch (NumberFormatException ex){
                        logger.error("Unesena je ne numericka vrijednost");
                        System.out.println("Morate unesti numeričku vrijednust");
                        unos = true;
                    }
                }while (unos);

                Student student = studenti[(brojStudenta - 1)];

                Integer ocjena = null;

                do{
                    unos = false;
                    try{
                        System.out.println("Unesite ocjenu na ispitu (1-5): ");
                        ocjena = Integer.parseInt(sc.nextLine());
                        logger.info("Unjeli ste ocjena : " + ocjena);
                        if (ocjena < 1 || ocjena > 5){
                            throw new NumberFormatException("Unjeli ste broj koji je van ranga (1-5)");
                        }

                    }catch (NumberFormatException ex){
                        logger.error("Unesena je ne numericka vrijednost ili vrijednost van ranga (1-5)");
                        System.out.println("Morate unesti numeričku vrijednust");
                        unos = true;
                    }
                }while (unos);
                if(ocjena == 1){
                    student.setMogucnostIzlaskaNaZavrsniRad(false);
                }else{
                    student.setMogucnostIzlaskaNaZavrsniRad(true);
                }

                System.out.println("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");
                LocalDateTime datumIVrijeme = LocalDateTime.parse(sc.nextLine(), dtf);

                ispiti[i] = new Ispit(predmet, student, ocjena, datumIVrijeme, dvorana1);


                Integer input = null;

                do{
                    unos = false;
                    try{
                        System.out.println("Odrzava li se ispit online? ");
                        System.out.println("1. DA");
                        System.out.println("2. NE");

                        input = Integer.parseInt(sc.nextLine());
                        logger.info("Odabrali ste da se ispit odrzava : " + input);

                    }catch (NumberFormatException ex){
                        logger.error("Unesena je ne numericka vrijednost");
                        System.out.println("Morate unesti numeričku vrijednust");
                        unos = true;
                    }
                }while (unos);

                if(input == 1){

                    Integer input1 = null;

                    do{
                        unos = false;
                        try{
                            System.out.println("Molimo odaberite software u kojemu se odrzava ispit");
                            System.out.println("1. IntelliJ");
                            System.out.println("2. Rider");

                            input1 = Integer.parseInt(sc.nextLine());
                            logger.info("Odabrali ste software : " + input1);

                        }catch (NumberFormatException ex){
                            logger.error("Unesena je ne numericka vrijednost");
                            System.out.println("Morate unesti numeričku vrijednust");
                            unos = true;
                        }
                    }while (unos);

                    String program = "nista";
                    if(input1 == 1){
                        program = ispiti[i].imePrograma("intellij");
                    } else if (input1 == 2) {
                        program = ispiti[i].imePrograma("rider");
                    }
                    System.out.println("Programski jezik koristen na ispitu je: " + program);
                }



            }


            for (int i = 0; i < BROJ_ISPITA; i++) {
                if (ispiti[i].getOcjena() == 5) {
                    System.out.println("Student " + ispiti[i].getStudent().getIme() + " " + ispiti[i].getStudent().getPrezime() +
                            "  je ostvario ocjenu 'izvrstan' na predmetu '" + ispiti[i].getPredmet().getNaziv() + "' ");
                }
            }

            Integer ustanova = null;

            do{
                unos = false;
                try{
                    System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva): ");

                    ustanova = Integer.parseInt(sc.nextLine());
                    logger.info("Unjeli ste broj ustanove : " + ustanova);

                }catch (NumberFormatException ex){
                    logger.error("Unesena je ne numericka vrijednost");
                    System.out.println("Morate unesti numeričku vrijednust");
                    unos = true;
                }
            }while (unos);

            String nazivObrazovneUstanove;
            switch (ustanova) {
                case 1 -> {
                    System.out.println("Unesite naziv obrazovne ustanove: ");
                    nazivObrazovneUstanove = sc.nextLine();
                    obrazovnaUstanova[k] = new VeleucilisteJave(nazivObrazovneUstanove, predmeti, profesori, studenti, ispiti);
                    for (Student student : obrazovnaUstanova[k].getStudenti()) {
                        if(student.getMogucnostIzlaskaNaZavrsniRad()){

                        student.setOcjenaZavrsnogRada(null);

                        do{
                            unos = false;
                            try{
                                System.out.println("Unesite ocjenu završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");

                                student.setOcjenaZavrsnogRada(Integer.parseInt(sc.nextLine()));
                                logger.info("Unjeli ste OcjenaZavrsnogRada : " + student.getOcjenaZavrsnogRada());

                            }catch (NumberFormatException ex){
                                logger.error("Unesena je ne numericka vrijednost");
                                System.out.println("Morate unesti numeričku vrijednust");
                                unos = true;
                            }
                        }while (unos);

                        student.setOcjenaObraneZavrsnogRada(null);

                        do{
                            unos = false;
                            try{
                                System.out.println("Unesite ocjenu obrane završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");

                                student.setOcjenaObraneZavrsnogRada(Integer.parseInt(sc.nextLine()));
                                logger.info("Unjeli ste Ocjena Obrane Zavrsnog Rada : " + student.getOcjenaObraneZavrsnogRada());

                            }catch (NumberFormatException ex){
                                logger.error("Unesena je ne numericka vrijednost");
                                System.out.println("Morate unesti numeričku vrijednust");
                                unos = true;
                            }
                        }while (unos);

                        Ispit[] filtriraniIspiti = new Ispit[BROJ_ISPITA];
                        filtriraniIspiti = ((VeleucilisteJave) obrazovnaUstanova[k]).filtrirajIspitePoStudentu(obrazovnaUstanova[k].getIspiti(), student);
                        System.out.println("konacna ocjena studija studenta " + student.getIme() + " " + student.getPrezime() + ": "
                                + ((VeleucilisteJave) obrazovnaUstanova[k])
                                .izracunajKonacnuOcjenuStudijaZaStudenta(filtriraniIspiti, student.getOcjenaZavrsnogRada(), student.getOcjenaObraneZavrsnogRada()));

                        }else{
                            student.setOcjenaObraneZavrsnogRada(1);
                            student.setOcjenaZavrsnogRada(1);
                        }
                    }
                    Student najboljiStudent = ((VeleucilisteJave) obrazovnaUstanova[k]).odrediNajuspjesnijegStudentaNaGodini(2022);
                    System.out.println("Najbolji student 2022. godine je " + najboljiStudent.getIme() + " " + najboljiStudent.getPrezime() + " JMBAG: " + najboljiStudent.getJmbag());
                }
                case 2 -> {
                    System.out.println("Unesite naziv obrazovne ustanove: ");
                    nazivObrazovneUstanove = sc.nextLine();
                    obrazovnaUstanova[k] = new FakultetRacunarstva(nazivObrazovneUstanove, predmeti, profesori, studenti, ispiti);
                    for (Student student : obrazovnaUstanova[k].getStudenti()) {

                        if(student.getMogucnostIzlaskaNaZavrsniRad()){
                        student.setOcjenaZavrsnogRada(null);

                        do{
                            unos = false;
                            try{
                                System.out.println("Unesite ocjenu završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");

                                student.setOcjenaZavrsnogRada(Integer.parseInt(sc.nextLine()));
                                logger.info("Unjeli ste Ocjena Zavrsnog Rada : " + student.getOcjenaZavrsnogRada());

                            }catch (NumberFormatException ex){
                                logger.error("Unesena je ne numericka vrijednost");
                                System.out.println("Morate unesti numeričku vrijednust");
                                unos = true;
                            }
                        }while (unos);

                        student.setOcjenaObraneZavrsnogRada(null);

                        do{
                            unos = false;
                            try{
                                System.out.println("Unesite ocjenu obrane završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ": ");

                                student.setOcjenaObraneZavrsnogRada(Integer.parseInt(sc.nextLine()));
                                logger.info("Unjeli ste Ocjena Obrane Zavrsnog Rada : " + student.getOcjenaObraneZavrsnogRada());

                            }catch (NumberFormatException ex){
                                logger.error("Unesena je ne numericka vrijednost");
                                System.out.println("Morate unesti numeričku vrijednust");
                                unos = true;
                            }
                        }while (unos);

                        System.out.println("konacna ocjena studija studenta " + student.getIme() + " " + student.getPrezime() + ": "
                                + ((FakultetRacunarstva) obrazovnaUstanova[k])
                                .izracunajKonacnuOcjenuStudijaZaStudenta(((FakultetRacunarstva) obrazovnaUstanova[k])
                                        .filtrirajIspitePoStudentu(obrazovnaUstanova[k].getIspiti(), student), student.getOcjenaZavrsnogRada(), student.getOcjenaObraneZavrsnogRada()));
                        }else{
                            student.setOcjenaObraneZavrsnogRada(1);
                            student.setOcjenaZavrsnogRada(1);
                        }
                    }
                    Student najboljiStudentFakultet = ((FakultetRacunarstva) obrazovnaUstanova[k]).odrediNajuspjesnijegStudentaNaGodini(2022);
                    System.out.println("Najbolji student 2022. godine je " + najboljiStudentFakultet.getIme() + " " + najboljiStudentFakultet.getPrezime() + " JMBAG: " + najboljiStudentFakultet.getJmbag());
                    Student studentSaRektorovomNagradom = new Student();

                    try {
                        studentSaRektorovomNagradom = ((FakultetRacunarstva) obrazovnaUstanova[k]).odrediStudentaZaRektorovuNagradu();
                    } catch (PostojiViseNajmladjihStudenataException e) {
                        logger.warn(e.getMessage());
                        System.out.println(e.getMessage());
                        return;
                    }
                    System.out.println("Student koji je osvojio rektorovu nagradu je: " + studentSaRektorovomNagradom.getIme() + " " + studentSaRektorovomNagradom.getPrezime() + " JMBAG: " + studentSaRektorovomNagradom.getJmbag());
                }
                default -> System.out.println("Unesen je krivi broj obrazovne ustanove");
            }

        }
    }
}
