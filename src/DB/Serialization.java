package DB;

import java.io.*;

/**
 * interface provides serialization capabilities for implementing class
 */
public interface Serialization {
    /**
     * @param object a to be serialized object
     * @param fileName a name of the file
     * serializes an object to the chosen file
     */
    static void save(Object object, StringBuffer fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName.toString());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            fileOut.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param fileName a name of the fileb
     * @return deserialized object
     * deserializes an object from provided file
     */
    static Object read(StringBuffer fileName) {
        Object object = new Object();
        try {
            FileInputStream fileIn = new FileInputStream(fileName.toString());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            object = in.readObject();
            in.close();
            fileIn.close();
            return object;
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException | ClassNotFoundException e) {
            e.getMessage();
        } finally {

        }
        return object;
    }
}
