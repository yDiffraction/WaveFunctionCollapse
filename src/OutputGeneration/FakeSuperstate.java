package OutputGeneration;

import java.util.Arrays;

public class FakeSuperstate implements State {
    int xCoord;
    int yCoord;
    GeneratorMain generatorMain;

    boolean[] possiblePatterns;
    int patternSize;
    int mid;

    public FakeSuperstate(GeneratorMain generatorMain, int numPatterns) {
        this.xCoord = 0;
        this.yCoord = 0;
        this.generatorMain = generatorMain;

        patternSize = generatorMain.getPattern(0).getSize();
        mid = patternSize / 2;

        //initialize possible_patterns and possible_colors
        this.possiblePatterns = new boolean[numPatterns];
        for (int i = 0; i < numPatterns; i++) {
            possiblePatterns[i] = true;
        }
    }

    public int getNumberOfPossibilities() {
        int n = 0;
        for (boolean i : possiblePatterns) {
            if (i) {
                n++;
            }
        }
        return n;
    }

    public void collapse() {
        return;
    }

    public void updateState(int depth) {
        return;
    }

    public int printColor() {
        if (getNumberOfPossibilities() != 1) {
            return -1;
        }
        for (int i = 0; i < possiblePatterns.length; i++) {
            if (possiblePatterns[i]) {
                return generatorMain.getPattern(i).map[mid][mid];
            }
        }
        return -2;
    }


    public boolean[] getPossiblePatterns() {
        return possiblePatterns;
    }

}
