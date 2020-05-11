package therapy;

import collection.AbstrDoubleList;
import idats_yarosh_sem1_v1.FXMLDocumentController;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import javafx.scene.control.Alert;

public final class Serializer {

    private Serializer() {
    }

    public static void saveBinary(final AbstrDoubleList<Term> doubleList, final String fileName, final boolean[][] isBusy) {
        try (final ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(new FileOutputStream(fileName))) {
            System.out.println("\nStart writing in file " + fileName + "... .");
            objectOutputStream.writeInt(doubleList.getSize());
            final Iterator<Term> it = doubleList.iterator();
            while (it.hasNext()) {
                objectOutputStream.writeObject(it.next());
            }
            objectOutputStream.writeInt(isBusy.length);//columns
            objectOutputStream.writeInt(isBusy[0].length);//rows

            for (boolean[] busy : isBusy) {
                for (boolean busy1 : busy) {
                    objectOutputStream.writeBoolean(busy1);
                }
            }
            System.out.println("Your file is successfully written.\n" + "Written: " + doubleList.getSize() + " objects.");
        } catch (IOException ex) {
            FXMLDocumentController.callAlertWindow("Error 404", "File " + fileName + " do not exist", Alert.AlertType.ERROR);
        }
    }

    public static void loadBinary(final AbstrDoubleList<Term> doubleList, final String filePath, GenerateTerms generate) {
        doubleList.clear();
        try (final ObjectInputStream objectInputStream
                = new ObjectInputStream(new FileInputStream(filePath))) {
            System.out.println("\nStart reading file " + filePath + "... .");
            final int size = objectInputStream.readInt();
            for (int i = 0; i < size; i++) {
                Term prvek = (Term) objectInputStream.readObject();
                doubleList.addLast(prvek);
            }
            final int numberOfColumns = objectInputStream.readInt();
            final int numberOfRows = objectInputStream.readInt();

            boolean[][] isBusy = new boolean[numberOfColumns][numberOfRows];
            for (int i = 0; i < numberOfColumns; i++) {
                for (int j = 0; j < numberOfRows; j++) {
                    isBusy[i][j] = objectInputStream.readBoolean();
                }
            }
            generate.setIsBusy(isBusy);
            System.out.println("Your file is successfully read.\n" + "Read: " + doubleList.getSize() + " objekts.");
        } catch (IOException ex) {
            FXMLDocumentController.callAlertWindow("Error 404", "File " + filePath + " do not exist", Alert.AlertType.ERROR);
        } catch (ClassNotFoundException ex) {
            FXMLDocumentController.callAlertWindow("Class exception", "Class is not found", Alert.AlertType.ERROR);
        }
    }
}
