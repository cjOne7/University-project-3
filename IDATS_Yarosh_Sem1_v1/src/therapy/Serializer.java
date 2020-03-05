package therapy;

import colection.AbstrDoubleList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import sprava.SpravceException;

public class Serializer {

    private static final String STATES_FILE = "States.bin";

    private Serializer() {
    }

    public static void saveBinary(final AbstrDoubleList<Term> doubleList, final String fileName, final boolean[][] isBusy) {
        try (ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(new FileOutputStream(fileName))) {
            System.out.println("\nStart writing in file " + fileName + "... .");
            objectOutputStream.writeInt(doubleList.getMohutnost());
            Iterator<Term> it = doubleList.iterator();
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
            System.out.println("Your file is successfully written.\n" + "Written: " + doubleList.getMohutnost() + " objects.");
        } catch (IOException ex) {
            new SpravceException("File " + fileName + " do not exist");
        }
    }

    public static void loadBinary(final AbstrDoubleList<Term> doubleList, final String filePath, GenerateTerms generate) {
        doubleList.zrus();
        try (ObjectInputStream objectInputStream
                = new ObjectInputStream(new FileInputStream(filePath))) {
            System.out.println("\nStart reading file " + filePath + "... .");
            int size = objectInputStream.readInt();
            for (int i = 0; i < size; i++) {
                Term prvek = (Term) objectInputStream.readObject();
                doubleList.vlozPosledni(prvek);
            }
            int numberOfColumns = objectInputStream.readInt();
            int numberOfRows = objectInputStream.readInt();

            boolean[][] isBusy = new boolean[numberOfColumns][numberOfRows];
            for (int i = 0; i < numberOfColumns; i++) {
                for (int j = 0; j < numberOfRows; j++) {
                    isBusy[i][j] = objectInputStream.readBoolean();
                }
            }
            generate.setIsBusy(isBusy);
            System.out.println("Your file is successfully read.\n" + "Read: " + doubleList.getMohutnost() + " objekts.");
        } catch (IOException ex) {
            new SpravceException("File " + filePath + " do not exist");
        } catch (ClassNotFoundException ex) {
            new SpravceException("Class not found");
        }
    }
    
//    public static void saveBinary(final AbstrDoubleList<Term> doubleList, final String fileName, final boolean[][] isBusy) {
//        try (ObjectOutputStream objectOutputStream
//                = new ObjectOutputStream(new FileOutputStream(fileName))) {
//            System.out.println("\nStart writing in file " + fileName + "... .");
//            objectOutputStream.writeInt(doubleList.getMohutnost());
//            Iterator<Term> it = doubleList.iterator();
//            while (it.hasNext()) {
//                objectOutputStream.writeObject(it.next());
//            }
//            serializeStates(isBusy);
//            System.out.println("Your file is successfully written.\nWritten: " + doubleList.getMohutnost() + " objects.");
//        } catch (IOException ex) {
//            new SpravceException("File " + fileName + " do not exist");
//        }
//    }
//
//    public static void loadBinary(final AbstrDoubleList<Term> doubleList, final String filePath, final GenerateTerms generate) {
//        doubleList.zrus();
//        try (ObjectInputStream objectInputStream
//                = new ObjectInputStream(new FileInputStream(filePath))) {
//            System.out.println("\nStart reading file " + filePath + "... .");
//            int size = objectInputStream.readInt();
//
//            for (int i = 0; i < size; i++) {
//                Term prvek = (Term) objectInputStream.readObject();
//                doubleList.vlozPosledni(prvek);
//            }
//            deserializeStates(generate);
//            System.out.println("Your file is successfully read.\nRead: " + doubleList.getMohutnost() + " objects.");
//        } catch (IOException ex) {
//            new SpravceException("File " + filePath + " do not exist");
//        } catch (ClassNotFoundException ex) {
//            new SpravceException("Class not found");
//        }
//        
//    }
//
//    private static void serializeStates(final boolean[][] states) throws IOException {
//        ObjectOutputStream objectOutputStream
//                = new ObjectOutputStream(new FileOutputStream(STATES_FILE));
//        objectOutputStream.writeInt(states.length);//columns
//        objectOutputStream.writeInt(states[0].length);//rows
//
//        for (boolean[] busy : states) {
//            for (boolean busy1 : busy) {
//                objectOutputStream.writeBoolean(busy1);
//            }
//        }
//    }
//
//    private static void deserializeStates(final GenerateTerms generate) throws IOException, ClassNotFoundException {
//        ObjectInputStream objectInputStream
//                = new ObjectInputStream(new FileInputStream(STATES_FILE));
//        int numberOfColumns = objectInputStream.readInt();
//        int numberOfRows = objectInputStream.readInt();
//
//        boolean[][] isBusy = new boolean[numberOfColumns][numberOfRows];
//        for (int i = 0; i < numberOfColumns; i++) {
//            for (int j = 0; j < numberOfRows; j++) {
//                isBusy[i][j] = objectInputStream.readBoolean();
//            }
//        }
//        generate.setIsBusy(isBusy);
//    }
}
