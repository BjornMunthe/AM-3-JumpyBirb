package se.yrgo.am3.gameobjects;

import java.io.*;

public class Highscore {
    private int[] points = new int[10];
    private String[] names = new String[10];
    private File textfile;

    public Highscore() {
        textfile = new File("highscore.txt");
        if (textfile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(textfile))) {
                for (int i = 0; i < 10; i++) {
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

    public void newEntry(int in, String str) {
        int placHInt = 0;
        String placHString = null;
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

    public int getLowscore() {
        return points[9];
    }

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
