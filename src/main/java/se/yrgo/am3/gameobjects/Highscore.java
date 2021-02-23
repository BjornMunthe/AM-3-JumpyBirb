package se.yrgo.am3.gameobjects;

import java.io.*;
import java.util.Locale;

public class Highscore {
    private int[] points;
    private String[] names;
    private File textfile;

    /*
    constructor that reads highscoredata from the appropriate file, if it exists, into the points and names arrays.
     */
    public Highscore() {
        points = new int[10];
        names = new String[10];
        textfile = new File("src/main/resources/highscore.txt");
        if (textfile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(textfile))) {
                for (int i = 0; i < points.length; i++) {
                    String line = reader.readLine();
                    if (line != null) {
                        String[] inputString = line.split(" ");
                        if (inputString.length > 2) {
                            points[i] = Integer.valueOf(inputString[inputString.length-1]);
                            StringBuilder builder = new StringBuilder();
                            for (int j = 0; j < inputString.length - 1; j++) {
                                builder.append(inputString[j]);
                                builder.append(" ");
                            }
                            names[i] = builder.toString().trim();
                        } else {
                            points[i] = Integer.valueOf(inputString[1]);
                            names[i] = inputString[0];
                        }
                    }
                }
            } catch (IOException exc) {
                System.err.println("something went wrog");
                exc.printStackTrace();
            }
        }
    }

    /**
     * Function that places the entered values in the appropriate places of the names and points arrays.
     * discards the previous last entries of each array
     * @param in the points reached
     * @param str the name entered by the player
     */
    public void newEntry(int in, String str) {
        int placHInt = 0;
        String placHString = null;
        if (str == null || str.equals("")) {
            str = "Anonymous";
        }
        for (int i = 0; i < points.length; i++) {
            if ((i == points.length-1) && (points[i] <= in)) {
                placHInt = points[i];
                placHString = names[i];
                points[i] = in;
                names[i] = str;
                in = placHInt;
                str = placHString;
            }
            if (points[i] < in) {
                placHInt = points[i];
                placHString = names[i];
                points[i] = in;
                names[i] = str;
                in = placHInt;
                str = placHString;
            }
        }
        writeFile();
    }

    //function for calculating number of characters of each highscore entry
    private int entryLength(String name, int point, int placement) {
        int nameLength = name.length();
        int pointLength = String.valueOf(point).length();
        int placementLength = String.valueOf(placement).length() + 2;
        return nameLength + pointLength;
    }

    /**
     * function returning a string containing the entries of the highscore arrays.
     *     dots are appended to each row between the name and point values until the rowlength integer
     *     passed to this function is reached
     * @param rowlength the desired length of each line in the string returned
     * @return a string starting with a line reading "Highscore" followed by ten numbered lines containing
     * the highscore data
     */
    public String[] printHighscore(int rowlength) {
        String[] strings = new String[10];

        for (int i = 0; i < points.length; i++) {
            StringBuilder builder = new StringBuilder();
            int place = i +1;
            builder.append(place);
            builder.append(". ");
            if (points[i] != 0) {
                builder.append(names[i]);
                for (int j = 0; j < (rowlength - entryLength(names[i], points[i], place)); j++) {
                    builder.append("-");
                }
                builder.append(points[i]);
            } else {
                for (int j = 0; j < (1 + rowlength - entryLength("", 0, place)); j++) {
                    builder.append("-");
                }
            }
            strings[i] = builder.toString().toUpperCase();
        }
        return strings;

    }

    public int[] getPoints() {
        return points;
    }

    public String[] getNames() {
        return names;
    }

    //returns the lowest of the highscore to determine if the points reached qualifies for the highscore
    public int getLowscore() {
        return points[9];
    }
    //writes the current highscoredata to a file after each entry. called by the newEntry function
    private void writeFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(textfile))) {

            for (int i = 0; i < 10; i++) {

                writer.write(String.format("%s %d%n", names[i], points[i]));
            }
        } catch (IOException exc) {
            System.err.println("something went wrog");
            exc.printStackTrace();
        }
    }


}
