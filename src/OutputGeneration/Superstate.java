package OutputGeneration;

import PatternDetermination.Pattern;

import java.util.Arrays;

public class Superstate {
  int xCoord;
  int yCoord;
  GeneratorMain generatorMain;

  boolean[] possiblePatterns;
  public boolean[] possibleColors;
  int patternSize;

  public Superstate(int xCoord, int yCoord, GeneratorMain generatorMain, int numPatterns, int numColors) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.generatorMain = generatorMain;

    patternSize = generatorMain.getPattern(0).getSize();

    //initialize possible_patterns and possible_colors
    this.possiblePatterns = new boolean[numPatterns];
    this.possibleColors = new boolean[numColors];
    for (int i = 0; i < numPatterns; i++) {
      possiblePatterns[i] = true;
    }
    for (int i = 0; i < numColors; i++) {
      possibleColors[i] = true;
    }
  }

  //calculate how "well-defined" the pixel is
  public int getNumberOfPossibilities() {
    int n = 0;
    for (boolean i : possibleColors) {
      if (i) {
        n++;
      }
    }
    return n;
  }

  //collapses the pixel. runs a recursion for collapse of nearby pixels after
  public void collapse() {
    //choose random color for the Superstate out of available
    System.out.println("Pixel collapsed: " + xCoord + " " + yCoord);
    int numPossiblePattern = 0;
    int patternID = 0;
    for (boolean i : possiblePatterns) {
      if (i) {
        numPossiblePattern++;
      }
    }

    int rand = (int) (Math.random() * numPossiblePattern);

    for (int i = 0; i < possiblePatterns.length; i++) { // farbe? oder nicht eher pattern?
      if (!possiblePatterns[i]) {
        continue;
      }
      if (rand == 0) {
        Arrays.fill(possiblePatterns, false); // pattern wird ausgewählt
        possiblePatterns[i] = true;
        patternID = i;
        break;
      }
      rand--;
    }

    System.out.println("Pattern chosen: " + patternID);

    // after choosing the Pattern, we set the colors of the neighbouring Superstates to the color of the chosen pattern

    Pattern pattern = generatorMain.patterns[patternID];
    int mid = patternSize / 2;
    for (int x = 0; x < patternSize; x++) {
      for (int y = 0; y < patternSize; y++) {
        Arrays.fill(getSTByDeltaCoords(x - mid, y - mid).possibleColors, false);
        getSTByDeltaCoords(x - mid, y - mid).possibleColors[pattern.map[x][y]] = true;
      }
    }

    for (int x = 0; x < patternSize; x++) {
      for (int y = 0; y < patternSize; y++) {
        getSTByDeltaCoords(x - mid, y - mid).updateState(0, true);
      }
    }
  }

  public void updateState(int depth, boolean changedColor) {
    if (generatorMain.debugMode) {
      for (int i = 0; i < depth; i++) {
        System.out.print("-");
      }
      System.out.println("State updated: " + xCoord + " " + yCoord);
    }
    int mid = patternSize / 2;

    //Update own possible patterns
    for (int i = 0; i < possiblePatterns.length; i++) {
      if (!possiblePatterns[i]) {
        continue;
      }
      Pattern p = generatorMain.getPattern(i);
      boolean possible = true;
      for (int x = 0; x < p.getSize(); x++) {
        for (int y = 0; y < p.getSize(); y++) {
          if (!getSTByDeltaCoords(x - mid, y - mid).getColorPossible(p.map[x][y])) {
            possible = false;
          }
        }
      }
      if (!possible) {
        possiblePatterns[i] = false;
      }
    }

    //Update surrounding possible colors
    int[][] numPossibleColorsMap = new int[patternSize][patternSize];

    for (int x = 0; x < patternSize; x++) {
      for (int y = 0; y < patternSize; y++) {
        int numPossibleColors = 0;
        for (boolean i : getSTByDeltaCoords(x - mid, y - mid).possibleColors) {
          if (i) {
            numPossibleColors++;
          }
        }
        numPossibleColorsMap[x][y] = numPossibleColors; // how many possible colors before update
        Arrays.fill(getSTByDeltaCoords(x - mid, y - mid).possibleColors, false); // all colors to false, in order to correct the possible colors later
      }
    }

    for (int i = 0; i < possiblePatterns.length; i++) {
      if (!possiblePatterns[i]) {
        continue;
      }
      Pattern p = generatorMain.getPattern(i);
      for (int x = 0; x < p.getSize(); x++) {
        for (int y = 0; y < p.getSize(); y++) {
          getSTByDeltaCoords(x - mid, y - mid).possibleColors[p.map[x][y]] = true; // go through all patterns and ST, to correct possible colors
        }
      }
    }

    if (generatorMain.debugMode) {
      generatorMain.debugMap();
    }

    for (int x = 0; x < patternSize; x++) {
      for (int y = 0; y < patternSize; y++) {
        int numPossibleColors = 0;
        for (boolean i : getSTByDeltaCoords(x - mid, y - mid).possibleColors) {
          if (i) {
            numPossibleColors++;
          }
        }
        if (numPossibleColors < numPossibleColorsMap[x][y] || changedColor) {
          getSTByDeltaCoords(x - mid, y - mid).updateState(depth+1, numPossibleColors < numPossibleColorsMap[x][y]);
        }
      }
    }
  }

  private Superstate getSTByDeltaCoords(int deltaX, int deltaY) {
    return generatorMain.map[(((xCoord + deltaX) % generatorMain.map.length) + generatorMain.map.length) % generatorMain.map.length][(((yCoord + deltaY) % generatorMain.map[0].length) + generatorMain.map.length) % generatorMain.map.length];
  }

  public boolean getColorPossible(int index) {
    return possibleColors[index];
  }

  public int printColor() {
    if (getNumberOfPossibilities() != 1) {
      return -1;
    }
    for (int i = 0; i < possibleColors.length; i++) {
      if (possibleColors[i]) {
        return i;
      }
    }
    return -2;
  }
}
