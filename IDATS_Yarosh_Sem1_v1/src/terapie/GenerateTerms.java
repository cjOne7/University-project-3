package terapie;

import java.time.*;
import java.util.Random;
import javafx.scene.control.DatePicker;
import kolekce.AbstrDoubleList;

public class GenerateTerms {

    private boolean[][] isBusy;
    private AbstrDoubleList<Term> listOfTerms = new AbstrDoubleList<>();

    private Term generateTerm(
            final DatePicker datePickerFrom,
            final DatePicker datePickerTo,
            final WorkHours pracovniDoba) {
        DurOfTherapy trvaniTerapie = getRandomTrvaniTerapie();

        long periodOfWork = (long) (Math.random() * (datePickerTo.getValue().toEpochDay() - datePickerFrom.getValue().toEpochDay() + 1));
        LocalDate localDate = datePickerFrom.getValue().plusDays(periodOfWork);
        int hour = (int) Double.NaN;
        if (pracovniDoba.getBeginOfWorkDay() % 2 == 0) {
            while (true) {
                hour = generateHour(
                        trvaniTerapie.getDurOfTherapy(),
                        pracovniDoba.getBeginOfWorkDay(),
                        pracovniDoba.getDurOfWorkDay());
                if (hour % 2 == 0) {
                    break;
                }
            }
        } else {
            while (true) {
                hour = generateHour(
                        trvaniTerapie.getDurOfTherapy(),
                        pracovniDoba.getBeginOfWorkDay(),
                        pracovniDoba.getDurOfWorkDay());
                if (hour % 2 != 0) {
                    break;
                }
            }
        }
        return new Term(
                getRandomTerapie(),
                trvaniTerapie,
                getLocalDateTime(localDate, hour, 0),
                getLocalDateTime(localDate, hour, trvaniTerapie.getDurOfTherapy()));
    }

    private int generateHour(
            final int durOfTherapy,
            final int beginOfTherapy,
            final int durOfWork) {
        return durOfTherapy == 2
                ? (int) (Math.random() * (beginOfTherapy - 1) + durOfWork)
                : (int) (Math.random() * (beginOfTherapy - 3) + durOfWork);
    }

    private Therapy getRandomTerapie() {
        return Therapy.values()[new Random().nextInt(Therapy.values().length)];
    }

    private DurOfTherapy getRandomTrvaniTerapie() {
        return DurOfTherapy.values()[new Random().nextInt(DurOfTherapy.values().length)];
    }

    private LocalDateTime getLocalDateTime(
            final LocalDate ld,
            final int hour,
            final int durOfTherapy) {
        return LocalDateTime.of(ld, LocalTime.of(hour + durOfTherapy, 0));
    }

    public AbstrDoubleList<Term> generateTerms(
            final int numberOfTerms,
            final WorkHours pracovniDoba,
            final DatePicker datePickerFrom,
            final DatePicker datePickerTo) {
        listOfTerms.zrus();
        long rows = Math.abs(datePickerTo.getValue().toEpochDay() - datePickerFrom.getValue().toEpochDay()) + 1;
        int columns = pracovniDoba.getDurOfWorkDay();
        isBusy = new boolean[(int) rows][columns];

        for (int i = 0; i < numberOfTerms; i++) {
            if (isAnyFreeSpace()) {
                return listOfTerms;
            }
            Term termin = generateTerm(datePickerFrom, datePickerTo, pracovniDoba);
            long row = Math.abs(termin.getStart().toLocalDate().toEpochDay() - datePickerFrom.getValue().toEpochDay());
            int column = termin.getStart().getHour() - pracovniDoba.getBeginOfWorkDay();
            while (true) {
                if (isFreeSpace(column, row)) {
                    isBusy[column][(int) row] = true;
                    isBusy[column + 1][(int) row] = true;
                    if (termin.getDurOfTherapy().getDurOfTherapy() == 4) {
                        isBusy[column + 2][(int) row] = true;
                        isBusy[column + 3][(int) row] = true;
                    }
                    break;
                } else {
                    termin = generateTerm(datePickerFrom, datePickerTo, pracovniDoba);
                    row = Math.abs(termin.getStart().toLocalDate().toEpochDay() - datePickerFrom.getValue().toEpochDay());
                    column = termin.getStart().getHour() - pracovniDoba.getBeginOfWorkDay();
                }
            }
            listOfTerms.vlozPosledni(termin);
        }
        return listOfTerms;
    }

    private boolean isFreeSpace(final long i, final long j) {
        return !isBusy[(int) i][(int) j];
    }

    private boolean isAnyFreeSpace() {
        int controlValue = 0;
        for (boolean[] busy : isBusy) {
            for (boolean busy1 : busy) {
                if (!busy1) {
                    controlValue++;
                }
            }
        }
        return controlValue == 0;
    }

    public boolean[][] getIsBusy() {
        return isBusy;
    }
}

