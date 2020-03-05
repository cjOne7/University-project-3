package therapy;

import therapistData.WorkHours;
import java.time.*;
import java.util.Random;
import colection.AbstrDoubleList;
import java.io.Serializable;
//The engine of this generator is MAGIC. Don't change anything!

public class GenerateTerms implements Serializable{

    private boolean[][] isBusy;
    private AbstrDoubleList<Term> listOfTerms = new AbstrDoubleList<>();

    private Term generateTerm(
            final LocalDate datePickerFrom,
            final LocalDate datePickerTo,
            final WorkHours pracovniDoba) {
        DurOfTherapy trvaniTerapie = getRandomTrvaniTerapie();

        long periodOfWork = (long) (Math.random() * (datePickerTo.toEpochDay() - datePickerFrom.toEpochDay() + 1));
        LocalDate localDate = datePickerFrom.plusDays(periodOfWork);
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
            final LocalDate datePickerFrom,
            final LocalDate datePickerTo) {
        listOfTerms.zrus();
        long rows = Math.abs(datePickerTo.toEpochDay() - datePickerFrom.toEpochDay()) + 1;
        int columns = pracovniDoba.getDurOfWorkDay();
        isBusy = new boolean[columns][(int) rows];

        for (int i = 0; i < numberOfTerms; i++) {
            if (isAnyFreeSpace()) {
                return listOfTerms;
            }
            Term termin = generateTerm(datePickerFrom, datePickerTo, pracovniDoba);
            long row = Math.abs(termin.getStart().toLocalDate().toEpochDay() - datePickerFrom.toEpochDay());
            int column = Math.abs(termin.getStart().getHour() - pracovniDoba.getBeginOfWorkDay());
            while (true) {
                if (isFreeSpace(column, row)) {
                    isBusy[column][(int) row] = true;
                    isBusy[column + 1][(int) row] = true;
                    if (termin.getDurOfTerm().getDurOfTherapy() == 4) {
                        isBusy[column + 2][(int) row] = true;
                        isBusy[column + 3][(int) row] = true;
                    }
                    break;
                } else {
                    termin = generateTerm(datePickerFrom, datePickerTo, pracovniDoba);
                    row = Math.abs(termin.getStart().toLocalDate().toEpochDay() - datePickerFrom.toEpochDay());
                    column = Math.abs(termin.getStart().getHour() - pracovniDoba.getBeginOfWorkDay());
                }
            }
            listOfTerms.vlozPosledni(termin);
        }
        return listOfTerms;
    }

    private boolean isFreeSpace(final int i, final long j) {
        return !isBusy[i][(int) j];
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

    public void setIsBusy(final boolean[][] isBusy) {
        this.isBusy = isBusy;
    }
}