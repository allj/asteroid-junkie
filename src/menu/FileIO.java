package menu;

import java.io.*;

// The FileIO
public class FileIO {
//-----------------------------------------------------------------------------------------------------------

    //------------------------------------------------
    // FILE IO METHODS |||||||||||||||||||||||||||||||
    //------------------------------------------------
    // these methods are for reading and writing text to a file
    // this method reads text from file into a string
    public static String readFile(String fileName) {
        BufferedReader fin;
        String fileText = ""; // string that holds the text in file
        try {
            fin = new BufferedReader(new FileReader(fileName));
            for (int i = 0; true; i++) {
                try {
                    String s = fin.readLine();
                    if (s == null) { // if there is nothing to read
                        //System.out.println("---EOF---");
                        break;
                    } else {
                        fileText += s;
                        //System.out.println(s);
                        // dataOut.writeChars(s+"\n");
                    }
                } catch (IOException e) { // an error has occurred
                    System.out.println("File read error... ");
                    return null;
                }
                fileText += " "; // adds a new line character to indicate a new line
            }
            fileText = fileText.trim(); // gets rid of excess spaces on the end of string
            try {
                fin.close(); // close the file once done
            } catch (IOException e) { // an error has occurred
                System.out.println("File will not close...");
            }
        } catch (FileNotFoundException e) { // an error has occurred
            System.out.println("'" + fileName + "' not found...");
            return null;
        }
        return fileText;
    }

    // this method will write any valid string to a file, nothing fancy
    public static int writeStringToFile(String s, String fileName) {
        BufferedWriter fout; // the output stream
        try {
            fout = new BufferedWriter(new FileWriter(fileName));
            try {
                fout.write(s); // writes the string to the file
            } catch (IOException e) { // some error has occured
                System.out.println("Cannot write to file.");
                return -1;
            }
        } catch (Exception e) { // some error has occured
            System.out.println("Cannot create '" + fileName + "'");
            return -1;
        }
        try {
            fout.close();
        } catch (Exception e) { // some error has occured
            System.out.println("Cannot close '" + fileName + "'");
            return -1;
        }
        return 0;
    }
    //------------------------------------------------
    // END OF FILE IO METHODS ||||||||||||||||||||||||
    //------------------------------------------------

    // the string parsing method
    // this method will read a string into an array of strings
    // this allows for easy selection of separate words or numbers
    public static String[] stringToArray(String s, String seperators) {
        String text = s; // the string to be parsed
        String[] array; // the returned hex array
        String chars = seperators; // these are the acceptable separators of the hex values
        int temp2 = 0;
        int numElements = 0; // number of hex values or characters

        //text = text.replace(',', '`'); // replaces all spaces with comma
        text = " " + text; // sets up string to be read
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < chars.length(); j++) {
                if (text.charAt(i) == chars.charAt(j)) {
                    numElements++; // counts the number of hex values
                    break;
                }
            }
        }
        array = new String[numElements + 1];

        // this adds on characters one character at a time to a string in the array
        for (int stringNum = 0; stringNum < array.length; stringNum++) {
            array[stringNum] = ""; // Initialises the string
            b:
            for (int i = temp2; i < text.length() - 1; i++) {
                for (int j = 0; j < chars.length(); j++) { // checks for equality between all chars and the character in string
                    if (text.charAt(i + 1) == '?' || text.charAt(i + 1) == chars.charAt(j)) {
                        temp2 = i + 1; // holds the place in the string
                        break b; // breaks the b loop
                    }
                }

                array[stringNum] += text.charAt(i + 1); // adds a character onto element in array
            }
        }

        return array; // returns the array
    }
}
