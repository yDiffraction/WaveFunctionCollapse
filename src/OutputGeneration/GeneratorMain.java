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
    while (!collapsed) {
      collapsed = !collapse();
      //System.out.println("run once");
      //for (Superstate[] x : map) {
      //  for (Superstate y : x) {
      //    System.out.print(y.getNumberOfPossibilities() + " ");
      //  }
      //  System.out.print("\n");
      //}
      //System.out.print("\n");
    }
  }
  
  //choose the right pixel to collapse, then collapse it
  private boolean collapse () {
    int chosenSt_X = 0;
    int chosenSt_Y = 0;

    int minPossibilities = colorsCount;
    int maxPossibilities = 0;
    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[0].length; y++) {
        int possibilities = map[x][y].getNumberOfPossibilities();

        if (possibilities <= minPossibilities) {
          chosenSt_X = x;
          chosenSt_Y = y;
          minPossibilities = possibilities;
        }
        maxPossibilities = Math.max(maxPossibilities, possibilities);
      }
    }

    //tell the state to collapse. it will run a recursion collapsing all affected states
    if (maxPossibilities == 1) {
      return false;
    }
    map[chosenSt_X][chosenSt_Y].collapse();
    return true;
  }

  public Pattern getPattern(int index) {
    return patterns[index];
  }
}
