package OutputGeneration;

import PatternDetermination.Pattern;

import java.util.Arrays;

public class Superstate implements State {
  private int xCoord;
  private int yCoord;
  private GeneratorMain generatorMain;

  private boolean[] possiblePatterns;
  private int patternSize;
  private int mid;

  public Superstate(int xCoord, int yCoord, GeneratorMain generatorMain, int numPatterns) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.generatorMain = generatorMain;

    patternSize = generatorMain.getPattern(0).getSize();
    mid = (int) patternSize / 2;

    //initialize possible_patterns and possible_colors
    this.possiblePatterns = new boolean[numPatterns];
    for (int i = 0; i < numPatterns; i++) {
      possiblePatterns[i] = true;
    }
  }

  //calculate how "well-defined" the pixel is
  public int getNumberOfPossibilities() {
    int n = 0;
    for (boolean i : possiblePatterns) {
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

    for (int i = 0; i < possiblePatterns.length; i++) {
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

    if (generatorMain.debugMode) {
      generatorMain.debugMap();
    }

    for (int x = 0; x < patternSize; x++) {
      for (int y = 0; y < patternSize; y++) {
        getSTByDeltaCoords(x - mid, y - mid).updateState(0);
      }
    }
  }

  public void updateState(int depth) {
    if (generatorMain.debugMode) {
      for (int i = 0; i < depth; i++) {
        System.out.print("-");
      }
      System.out.println("State updated: " + xCoord + " " + yCoord);
    }

    int startPossibilities = getNumberOfPossibilities();

    //Update own possible patterns
    for (int i = 0; i < possiblePatterns.length; i++) {
      if (!possiblePatterns[i]) {
        continue;
      }
      Pattern p = generatorMain.getPattern(i);
      boolean possible = true;
      for (int x = -mid; x <= mid; x++) {
        for (int y = -mid; y <= mid; y++) {
          boolean[] p2List = getSTByDeltaCoords(x, y).getPossiblePatterns();
          boolean possible2 = false;
          for (int pIndex = 0; pIndex < possiblePatterns.length; pIndex++) {
            if (!p2List[pIndex]) {
              continue;
            }
            boolean possible3 = true;
            Pattern p2 = generatorMain.getPattern(pIndex);
            for (int x2 = x==1 ? 0 : -1; x2<=x+1 && x2<=1; x2++) {
              for (int y2 = y==1 ? 0 : -1; y2<=y+1 && y2<=1; y2++) {
                if (p.map[x2+mid][y2+mid] != p2.map[x2-x+mid][y2-y+mid]) {
                  possible3 = false;
                }
              }
            }
            if (possible3) {
              possible2 = true;
              break;
            }
          }
          if (!possible2) {
            possible = false;
          }
        }
      }
      if (!possible) {
        possiblePatterns[i] = false;
      }
    }

    if (generatorMain.debugMode) {
      generatorMain.debugMap();
    }

    if (getNumberOfPossibilities() < startPossibilities) {
      for (int x = 0; x < patternSize; x++) {
        for (int y = 0; y < patternSize; y++) {
          if (x == mid && y == mid) {
            continue;
          }
          getSTByDeltaCoords(x - mid, y - mid).updateState(depth+1);
        }
      }
    }
  }

  private State getSTByDeltaCoords(int deltaX, int deltaY) {
    int x = xCoord + deltaX;
    int y = yCoord + deltaY;
    if (!generatorMain.wraparound) {
      if (x < 0 || y < 0 || x >= generatorMain.map.length || y >= generatorMain.map.length) {
        return new FakeSuperstate(generatorMain, possiblePatterns.length);
      }
    }else {
      x = ((x % generatorMain.map.length) + generatorMain.map.length) % generatorMain.map.length;
      y = ((y % generatorMain.map.length) + generatorMain.map.length) % generatorMain.map.length;
    }
    return generatorMain.map[x][y];
    //return generatorMain.map[(((xCoord + deltaX) % generatorMain.map.length) + generatorMain.map.length) % generatorMain.map.length][(((yCoord + deltaY) % generatorMain.map[0].length) + generatorMain.map.length) % generatorMain.map.length];
  }

  public boolean[] getPossiblePatterns() {
    return possiblePatterns;
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
}
