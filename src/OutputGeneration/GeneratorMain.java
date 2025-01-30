package OutputGeneration;

import PatternDetermination.Pattern;

import java.util.Set;

public class GeneratorMain {
  Pattern[] patterns;
  Set<Integer> colors;
  Superstate[][] map;

  public GeneratorMain(Pattern[] patterns, Set<Integer> colors, int width, int height) {
    System.out.println("Generator started");
    this.patterns = patterns;
    this.colors = colors;
    this.map = new Superstate[height][width];
    // this.map = new Superstate[height][width](patterns.length, colors.length);??
  }

  //main loop
  public void run() {
    while (true) {
      collapse();
    }
    //choose the right pixel to collapse, then collapse it

  }
  private void collapse () {
    Superstate minState = new Superstate(5,5);// muss noch initialisiert werden -> Variabeln nicht korrekt
    int minPossibilities = colors.size();
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