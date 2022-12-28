import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.log;

public class Main {

    static void updateTables(String line, int diff) {
        boolean isRep = line.charAt(0) == 'r';

        if (isRep) {
            cntRep += diff;
        } else {
            cntDem += diff;
        }

        int posRep = 11;
        int posDem = 9;

        int initPos = isRep ? posRep : posDem;

        int cnt = 0;
        int i = initPos;

        while (i < line.length()) {
            if (line.charAt(i) == 'y') {
                if (isRep) {
                    republicans[0][cnt] += diff;
                } else {
                    democrats[0][cnt] += diff;
                }
            } else if (line.charAt(i) == 'n') {
                if (isRep) {
                    republicans[1][cnt] += diff;
                } else {
                    democrats[1][cnt] += diff;
                }
            } else if (line.charAt(i) == '?') {
                if (isRep) {
                    republicans[2][cnt] += diff;
                } else {
                    democrats[2][cnt] += diff;
                }
            }

            cnt++;
            i += 2;
        }
    }

    static int processLine(String line) {
        int posRep = 11;
        int posDem = 9;

        boolean isRep = line.charAt(0) == 'r';
        int initPos = isRep ? posRep : posDem;

        double repProb = 0;
        double demProb = 0;

        int index = 0;

        int i = initPos;
        while (i < line.length()) {
            if (line.charAt(i) == 'n') {
                repProb += log((republicans[1][index] + 1) / (cntRep + 2));
                demProb += log((democrats[1][index] + 1) / (cntDem + 2));
            } else if (line.charAt(i) == 'y') {
                repProb += log((republicans[0][index] + 1) / (cntRep + 2));
                demProb += log((democrats[0][index] + 1) / (cntDem + 2));
            } else if (line.charAt(i) == '?') {
                repProb += log((republicans[2][index] + 1) / (cntRep + 2));
                demProb += log((democrats[2][index] + 1) / (cntDem + 2));
            }

            i += 2;
            ++index;
        }

        repProb += log((cntRep + 1) / (cntDem + cntRep + 2));
        demProb += log((cntDem + 1) / (cntDem + cntRep + 2));

        if ((isRep && repProb >= demProb) ||
                (!isRep && repProb <= demProb)) {
            return 1;
        }

        return 0;
    }

    static double accuracy(int testinit) {
        double rightPredicts = 0;

        for (int i = 0; i < testedSize; i++) {
            rightPredicts += processLine(data.get(testinit + i));
        }

        return rightPredicts / testedSize * 100;
    }

    static List<String> data = new ArrayList<>();

    static int[][] democrats = new int[3][16];
    static int[][] republicans = new int[3][16];

    static double cntDem = 0;
    static double cntRep = 0;

    static int testedSize;

    public static void main(String[] args) throws IOException {

        String line;
        String path = "house-votes-84.data";

        try (BufferedReader buff = new BufferedReader(new FileReader(path))) {
            while ((line = buff.readLine()) != null) {
                data.add(line);
                updateTables(line, 1);
            }
        }

        // ten-fold cross-validation
        testedSize = data.size() / 10;
        int testInit;
        double sumperc = 0;
        final int diff = 1;
        double accuracy;

        for (int i = 0; i < 10; ++i) {
            testInit = i * testedSize;

            for (int j = testInit; j < testInit + testedSize; ++j) {
                if (testInit != 0) {
                    updateTables(data.get(j - testedSize), diff);
                }

                updateTables(data.get(j), -1);
            }

            accuracy = accuracy(testInit);
            System.out.printf("%.4f%s \n", accuracy, "%");
            sumperc += accuracy;
        }

        System.out.printf("\n%.4f%s", sumperc / 10, "%");

    }
}
