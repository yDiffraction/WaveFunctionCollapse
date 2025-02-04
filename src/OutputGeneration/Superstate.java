package OutputGeneration;

import PatternDetermination.Pattern;

import java.util.Arrays;

public class Superstate {
  int x;
  int y;
  GeneratorMain generatorMain;
  boolean[] possible_patterns;
  boolean[] possible_colors;

  public Superstate(int x, int y, GeneratorMain generatorMain, int num_patterns, int num_colors) {
    this.x = x;
    this.y = y;
    this.generatorMain = generatorMain;

    //initialize possible_patterns and possible_colors
    this.possible_patterns = new boolean[num_patterns];
    this.possible_colors = new boolean[num_colors];
    for (int i = 0; i < num_patterns; i++) {
      possible_patterns[i] = true;
    }
    for (int i = 0; i < num_colors; i++) {
      possible_colors[i] = true;
    }
  }

  //calculate how "well-defined" the pixel is
  public int getNumberOfPossibilities() {
    int r = 0;
    for (boolean i : possible_colors) {
      if (i) {
        r++;
      }
    }
    return r;
  }

  //collapses the pixel. runs a recursion for collapse of nearby pixels after
  public void collapse() {
    //choose random color for the Superstate out of available
    int num_possibilities = 0;
    for (boolean i : possible_colors) {
      if (i) {
        num_possibilities++;
      }
    }

    int rand = (int) (Math.random() * num_possibilities);
    for (int i = 0; i < possible_colors.length; i++) {
      if (!possible_colors[i]) {
        continue;
      }
      if (rand == 0) {
        Arrays.fill(possible_colors, false);
        possible_colors[i] = true;
        break;
      }
      rand--;
    }
    update_state();
  }

  public void update_state() {
    //Update own possible patterns
    for (int i = 0; i < possible_patterns.length; i++) {
      if (!possible_patterns[i]) {
        continue;
      }
      Pattern p = generatorMain.getPatterns()[i];
      int mid = Pattern.map.length;
    }
  }
  private Superstate getST_By_Coords(int deltX, int deltY) {
    return generatorMain.map[(x + deltX) % generatorMain.map.length][(y + deltY) % generatorMain.map[0].length];
  }
}
