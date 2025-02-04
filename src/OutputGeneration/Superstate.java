package OutputGeneration;

import PatternDetermination.Pattern;

import java.util.Arrays;

public class Superstate {
  int x;
  int y;
  GeneratorMain generatorMain;
  boolean[] possible_patterns;
  public boolean[] possible_colors;

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
    int pattern_size = generatorMain.getPattern(0).getSize();
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

    for (int i = 0; i < possible_colors.length; i++) {
      if (!possible_colors[i]) {
        continue;
      }
      Pattern p = generatorMain.getPattern(i);
      for (int x = 0; x < p.getSize(); x++) {
        for (int y = 0; y < p.getSize(); y++) {
          getST_By_Delta_Coords(x - mid, y - mid).possible_colors[p.map[x][y]] = true;
        }
      }
    }

    for (int x = 0; x < pattern_size; x++) {
      for (int y = 0; y < pattern_size; y++) {
        int num_possible_colors = 0;
        for (boolean i : getST_By_Delta_Coords(x - mid, y - mid).possible_colors) {
          if (i) {
            num_possible_colors++;
          }
        }
        if (num_possible_colors < num_possible_colors_map[x][y]) {
          getST_By_Delta_Coords(x - mid, y - mid).update_state();
        }
      }
    }
  }

  private Superstate getST_By_Delta_Coords(int deltX, int deltY) {
    return generatorMain.map[(x + deltX) % generatorMain.map.length][(y + deltY) % generatorMain.map[0].length];
  }

  public boolean get_color_possible(int index) {
    return possible_colors[index];
  }
}
