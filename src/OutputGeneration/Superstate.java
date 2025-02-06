package OutputGeneration;

import PatternDetermination.Pattern;

import java.util.Arrays;

public class Superstate {
  int x;
  int y;
  GeneratorMain generatorMain;

  boolean[] possible_patterns;
  public boolean[] possible_colors;
  int pattern_size;

  public Superstate(int x, int y, GeneratorMain generatorMain, int num_patterns, int num_colors) {
    this.x = x;
    this.y = y;
    this.generatorMain = generatorMain;

    pattern_size = generatorMain.getPattern(0).getSize();

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
    int n = 0;
    for (boolean i : possible_colors) {
      if (i) {
        n++;
      }
    }
    return n;
  }

  //collapses the pixel. runs a recursion for collapse of nearby pixels after
  public void collapse() {
    //choose random color for the Superstate out of available
    System.out.println("Pixel collapsed: " + x + " " + y);
    int num_possible_Pattern = 0;
    int pattern_ID = 0;
    for (boolean i : possible_patterns) {
      if (i) {
        num_possible_Pattern++;
      }
    }

    int rand = (int) (Math.random() * num_possible_Pattern);

    for (int i = 0; i < possible_patterns.length; i++) { // farbe? oder nicht eher pattern?
      if (!possible_patterns[i]) {
        continue;
      }
      if (rand == 0) {
        Arrays.fill(possible_patterns, false); // pattern wird ausgewählt
        possible_patterns[i] = true;
        pattern_ID = i;
        break;
      }
      rand--;
    }

    System.out.println("Pattern chosen: " + pattern_ID);

    // after choosing the Pattern, we set the colors of the neighbouring Superstates to the color of the chosen pattern

    Pattern pattern = generatorMain.patterns[pattern_ID];
    int mid = pattern_size / 2;
    for (int x = 0; x < pattern_size; x++) {
      for (int y = 0; y < pattern_size; y++) {
        Arrays.fill(getST_By_Delta_Coords(x - mid, y - mid).possible_colors, false);
        getST_By_Delta_Coords(x - mid, y - mid).possible_colors[pattern.map[x][y]] = true;
      }
    }

    for (int x = 0; x < pattern_size; x++) {
      for (int y = 0; y < pattern_size; y++) {
        getST_By_Delta_Coords(x - mid, y - mid).update_state(0, true);
      }
    }
    //bis hier hin funktioniert der code
  }

  public void update_state(int depth, boolean changedColor) {
    for (int i = 0; i < depth; i++) {
      System.out.print("-");
    }
    System.out.println("State updated: " + x + " " + y);
    int mid = pattern_size / 2;

    //Update own possible patterns
    for (int i = 0; i < possible_patterns.length; i++) {
      if (!possible_patterns[i]) {
        continue;
      }
      Pattern p = generatorMain.getPattern(i);
      boolean possible = true;
      for (int x = 0; x < p.getSize(); x++) {
        for (int y = 0; y < p.getSize(); y++) {
          if (!getST_By_Delta_Coords(x - mid, y - mid).get_color_possible(p.map[x][y])) {
            possible = false;
          }
        }
      }
      if (!possible) {
        possible_patterns[i] = false;
      }
    }

    //Update surrounding possible colors
    int[][] num_possible_colors_map = new int[pattern_size][pattern_size];

    for (int x = 0; x < pattern_size; x++) {
      for (int y = 0; y < pattern_size; y++) {
        int num_possible_colors = 0;
        for (boolean i : getST_By_Delta_Coords(x - mid, y - mid).possible_colors) {
          if (i) {
            num_possible_colors++;
          }
        }
        num_possible_colors_map[x][y] = num_possible_colors;
        Arrays.fill(getST_By_Delta_Coords(x - mid, y - mid).possible_colors, false);
      }
    }

    for (int i = 0; i < possible_patterns.length; i++) {
      if (!possible_patterns[i]) {
        continue;
      }
      Pattern p = generatorMain.getPattern(i);
      for (int x = 0; x < p.getSize(); x++) {
        for (int y = 0; y < p.getSize(); y++) {
          getST_By_Delta_Coords(x - mid, y - mid).possible_colors[p.map[x][y]] = true;
        }
      }
    }

    generatorMain.debugMap();

    for (int x = 0; x < pattern_size; x++) {
      for (int y = 0; y < pattern_size; y++) {
        int num_possible_colors = 0;
        for (boolean i : getST_By_Delta_Coords(x - mid, y - mid).possible_colors) {
          if (i) {
            num_possible_colors++;
          }
        }
        if (num_possible_colors < num_possible_colors_map[x][y] || changedColor) {
          getST_By_Delta_Coords(x - mid, y - mid).update_state(depth+1, num_possible_colors < num_possible_colors_map[x][y]);
        }
      }
    }
  }

  private Superstate getST_By_Delta_Coords(int deltX, int deltY) {
    return generatorMain.map[(((x + deltX) % generatorMain.map.length) + generatorMain.map.length) % generatorMain.map.length][(((y + deltY) % generatorMain.map[0].length) + generatorMain.map.length) % generatorMain.map.length];
  }

  public boolean get_color_possible(int index) {
    return possible_colors[index];
  }

  public int print_color() {
    if (getNumberOfPossibilities() != 1) {
      return -1;
    }
    for (int i = 0; i < possible_colors.length; i++) {
      if (possible_colors[i]) {
        return i;
      }
    }
    return -2;
  }
}
