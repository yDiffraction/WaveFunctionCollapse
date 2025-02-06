package OutputGeneration;

import PatternDetermination.Pattern;


public class GeneratorMain {
  Pattern[] patterns;
  int colorsCount;
  public Superstate[][] map;
  public boolean collapsed = false;

  public GeneratorMain(Pattern[] patterns, int colorsCount, int width, int height) {
    this.patterns = patterns;
    this.colorsCount = colorsCount;
    this.map = new Superstate[height][width];

    for (int i = 0; i < this.map.length; i++) {
      for (int j = 0; j < this.map[0].length; j++) {
        this.map[i][j] = new Superstate(i, j, this, patterns.length, this.colorsCount);
      }
    }
  }

  //main loop
  public void run() {
    debugMap();
    while (!collapsed) {
      collapsed = !collapse();
      if (!collapsed) {
        System.out.println("----------------------------------Code Ran----------------------------------");
        debugMap();
      }
    }
  }

  public void debugMap() {
    for (Superstate[] x : map) {
      for (Superstate y : x) {
        System.out.print("[");
        for (boolean i : y.possible_colors) {
          System.out.print(i ? "1" : "0");
        }
        System.out.print("][");
        for (boolean i : y.possible_patterns) {
          System.out.print(i ? "1" : "0");
        }
        System.out.print("], ");
      }
      System.out.print("\n");
    }
    System.out.print("\n");
  }
  
  //choose the right pixel to collapse, then collapse it
  private boolean collapse () {
    int choosenSt_X = 0;
    int choosenSt_Y = 0;

    int minPossibilities = colorsCount;
    int maxPossibilities = 0;
    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[0].length; y++) {
        int possibilities = map[x][y].getNumberOfPossibilities();

        if (possibilities <= minPossibilities && possibilities != 1) {
          choosenSt_X = x;
          choosenSt_Y = y;
          minPossibilities = possibilities;
        }
        maxPossibilities = Math.max(maxPossibilities, possibilities);
      }
    }

    //tell the state to collapse. it will run a recursion collapsing all affected states
    if (maxPossibilities == 1) {
      return false;
    }
    map[choosenSt_X][choosenSt_Y].collapse();
    return true;
  }

  public Pattern getPattern(int index) {
    return patterns[index];
  }
}
