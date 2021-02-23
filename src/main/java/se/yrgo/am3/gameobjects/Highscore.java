package se.yrgo.am3.gameobjects;

import java.io.*;


public class Highscore {
    private int[] points;
    private String[] names;
    private File textfile;
    private boolean failedToReadFile;
    private String latestEntry;

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
                        String[] inputStrings = line.split(" ");
                        if (inputStrings.length > 2) {
                            points[i] = Integer.valueOf(inputStrings[inputStrings.length-1]);
                            StringBuilder builder = new StringBuilder();
                            for (int j = 0; j < inputStrings.length - 1; j++) {
                                builder.append(inputStrings[j]);
                                builder.append(" ");
                            }
                            names[i] = builder.toString().trim();
                        } else {
                            points[i] = Integer.valueOf(inputStrings[1]);
                            names[i] = inputStrings[0];
                        }
                    }
                }
                //throw new IOException();
            } catch (IOException exc) {
                System.err.println("Failed to read highscore");
                exc.printStackTrace();
                failedToReadFile = true;
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
        latestEntry = str;
        int placHInt = 0;
        String placHString = null;
        if (str == null || str.matches(" +") || str.length()<1) {
            str = "Anonymous";
        }
        str = ((str.length() > 15) ? str.substring(0,15).toUpperCase() : str.toUpperCase());
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


    /**
     * function used to print the highscore data to the screen
     * @return strings, a string array, the values of which alternates between placement and name,
     * and the associated points
      */
    public String[] printHighscore() {
        String[] strings = new String[points.length*2];

        for (int i = 0; i < points.length; i++) {
            StringBuilder builder = new StringBuilder();

            int place = i +1;
            builder.append(place);
            builder.append(". ");
            builder.append((names[i] == null || names[i].equals("null"))? "" : names[i]);

            strings[i*2] = builder.toString();

            strings[i*2+1] = ((points[i] == 0)? "" : String.valueOf(points[i]));
        }
        return strings;

    }

    public boolean fileNotRead() {
        return failedToReadFile;
    }

    public int[] getPoints() {
        return points;
    }

    public String getLatestEntry() {
        return latestEntry;
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
            System.err.println("Failed to write to highscore.txt");
            exc.printStackTrace();
            failedToReadFile = true;
        }
    }


}
