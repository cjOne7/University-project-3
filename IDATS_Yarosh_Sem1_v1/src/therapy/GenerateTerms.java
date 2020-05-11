package therapy;

import therapistData.WorkHours;
import java.time.*;
import java.util.Random;
import collection.AbstrDoubleList;
import idats_yarosh_sem1_v1.FXMLDocumentController;
import java.io.Serializable;
import javafx.scene.control.Alert;
//The engine of this generator is MAGIC. Don't change anything! NEVER!

public final class GenerateTerms implements Serializable {

    private boolean[][] isBusy;
    private AbstrDoubleList<Term> listOfTerms = new AbstrDoubleList<>();

    private Term generateTerm(
            final LocalDate localDateFrom,
            final LocalDate localDateTo,
            final WorkHours workHours) {
        final DurOfTherapy durOfTherapy = getRandomDurOfTherapy();

        final long periodOfWork = (long) (Math.random() * (localDateTo.toEpochDay() - localDateFrom.toEpochDay() + 1));
        final LocalDate localDate = localDateFrom.plusDays(periodOfWork);
        int hour = 0;
        if (workHours.getBeginOfWorkDay() % 2 == 0) {
            while (true) {
                hour = generateHour(
                        durOfTherapy.getDurOfTherapy(),
                        workHours.getBeginOfWorkDay(),
                        workHours.getDurOfWorkDay());
                if (hour % 2 == 0) {
                    break;
                }
            }
        } else {
            while (true) {
                hour = generateHour(
                        durOfTherapy.getDurOfTherapy(),
                        workHours.getBeginOfWorkDay(),
                        workHours.getDurOfWorkDay());
                if (hour % 2 != 0) {
                    break;
                }
            }
        }
        return new Term(
                getRandomTherapy(),
                durOfTherapy,
                getLocalDateTime(localDate, hour, 0),
                getLocalDateTime(localDate, hour, durOfTherapy.getDurOfTherapy()));
    }

    private int generateHour(
            final int durOfTherapy,
            final int beginOfTherapy,
            final int durOfWork) {
        return durOfTherapy == DurOfTherapy.SHORT.getDurOfTherapy()
                ? (int) (Math.random() * (durOfWork - DurOfTherapy.SHORT.getDurOfTherapy() + 1) + beginOfTherapy)
                : (int) (Math.random() * (durOfWork - DurOfTherapy.LONG.getDurOfTherapy() + 1) + beginOfTherapy);
    }

    public Therapy getRandomTherapy() {
        return Therapy.values()[new Random().nextInt(Therapy.values().length)];
    }

    public DurOfTherapy getRandomDurOfTherapy() {
        return DurOfTherapy.values()[new Random().nextInt(DurOfTherapy.values().length)];
    }

    private LocalDateTime getLocalDateTime(
            final LocalDate localDate,
            final int hour,
            final int durOfTherapy) {
        return LocalDateTime.of(localDate, LocalTime.of(hour + durOfTherapy, 0));
    }

    public AbstrDoubleList<Term> generateTerms(
            final int numberOfTerms,
            final WorkHours workHours,
            final LocalDate localDateFrom,
            final LocalDate localDateTo) {
        if (workHours.getDurOfWorkDay() < DurOfTherapy.SHORT.getDurOfTherapy()) {
            FXMLDocumentController.callAlertWindow(
                    "Don't enough work hours",
                    "You have a very short work day and no one therapy can't be created",
                    Alert.AlertType.ERROR);
            return listOfTerms;
        }
        listOfTerms.clear();
        final long rows = Math.abs(localDateTo.toEpochDay() - localDateFrom.toEpochDay()) + 1;
        final int columns = workHours.getDurOfWorkDay();
        isBusy = new boolean[columns][(int) rows];

        for (int i = 0; i < numberOfTerms; i++) {
            if (isAnyFreeSpace()) {
                return listOfTerms;
            }
            Term term = generateTerm(localDateFrom, localDateTo, workHours);
            long row = getIndexRow(term, localDateFrom);
            int column = getIndexColumn(term, workHours);
            while (true) {
                if (isFreeSpace(column, row)) {
                    isBusy[column][(int) row] = true;
                    isBusy[column + 1][(int) row] = true;
                    if (term.getDurOfTerm().getDurOfTherapy() == DurOfTherapy.LONG.getDurOfTherapy()) {
                        if (isBusy[column + 2][(int) row] && isBusy[column + 3][(int) row]) {
                            term.setDurOfTherapy(DurOfTherapy.SHORT);
                        } else {
                            isBusy[column + 2][(int) row] = true;
                            isBusy[column + 3][(int) row] = true;
                        }
                    }
                    break;
                } else {
                    term = generateTerm(localDateFrom, localDateTo, workHours);
                    row = getIndexRow(term, localDateFrom);
                    column = getIndexColumn(term, workHours);
                }
            }
            listOfTerms.addLast(term);
        }
        return listOfTerms;
    }

    private boolean isFreeSpace(final int i, final long j) {
        return !isBusy[i][(int) j];
    }

    public boolean isAnyFreeSpace() {
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

    private long getIndexRow(final Term term, final LocalDate localDateFrom) {
        return Math.abs(term.getStart().toLocalDate().toEpochDay() - localDateFrom.toEpochDay());
    }

    private int getIndexColumn(final Term term, final WorkHours workHours) {
        return Math.abs(term.getStart().getHour() - workHours.getBeginOfWorkDay());
    }
}
