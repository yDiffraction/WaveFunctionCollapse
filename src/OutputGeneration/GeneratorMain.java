import PatternDetermination.Pattern;
import Superstate;

public class GeneratorMain {
  Patterns[] patterns;
  Set<Int> colors;
  Superstate[][] map;
  
  public GeneratorMain(Patterns[] patterns, Set<Int> colors, int width, int height) {
    System.out.println("Generator started");
    this.patterns = patterns;
    this.colors = colors;
    this.map = new Superstate[height][width](patterns.length, colors.length);
  }

  //main loop
  public void run() {
    while (True) {
      collapse();
    }

  //choose the right pixel to collapse, then collapse it
  private void collapse() {
    Superstate minState;
    int minPossibilities = colors.length;
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
