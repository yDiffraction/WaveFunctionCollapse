package OutputGeneration;

import PatternDetermination.Pattern;

import java.util.Set;

public class GeneratorMain {
  Pattern[] patterns;
  int colors;
  Superstate[][] map;
  public boolean collapsed = false;

  public GeneratorMain(Pattern[] patterns, int colors, int width, int height) {
    System.out.println("Generator started");
    this.patterns = patterns;
    this.colors = colors;
    this.map = new Superstate[height][width];

    for (int i = 0; i < this.map.length; i++) {
      for (int j = 0; j < this.map[0].length; j++) {
        this.map[i][j] = new Superstate(i, j, this, patterns.length, this.colors);
      }
    }
  }

  //main loop
  public void run() {
    while (!collapsed) {
      collapse();
    }
  }
  
  //choose the right pixel to collapse, then collapse it
  private void collapse () {
    Superstate minState = new Superstate(0,0, this, patterns.length, colors);// muss noch initialisiert werden -> Variabeln nicht korrekt
    int minPossibilities = colors;
    for (Superstate[] x : map) {
      for (Superstate y : x) {
        if (y.getNumberOfPossibilities() <= minPossibilities) {
          minState = y;
          minPossibilities = y.getNumberOfPossibilities();
        }
      }
    }

    //tell the state to collapse. it will run a recursion collapsing all affected states
    minState.collapse();
  }
}
