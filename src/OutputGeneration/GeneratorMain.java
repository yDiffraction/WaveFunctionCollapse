package OutputGeneration;

import PatternDetermination.Pattern;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GeneratorMain {
  public boolean debugMode = false;
  private int countCollapse = 0;
  Pattern[] patterns;
  public Superstate[][] map;
  public boolean collapsed = false;
  private String name;

  public GeneratorMain(Pattern[] patterns, int colorsCount, int width, int height, String name) {
    this.patterns = patterns;
    this.map = new Superstate[height][width];
    this.name = name;

    for (int i = 0; i < this.map.length; i++) {
      for (int j = 0; j < this.map[0].length; j++) {
        this.map[i][j] = new Superstate(i, j, this, patterns.length);
      }
    }
  }

  //main loop
  public boolean run() {
    // debugMap();
    countCollapse = 0;
    while (!collapsed) {
      int flag = collapse();
      if (flag==2) {
        return false;
      }
      collapsed = flag == 0;
      if (!collapsed && debugMode) {
        System.out.println("----------------------------------Code Ran----------------------------------");
        debugMap();
      }
    }
    return true;
  }

  public void debugMap() {
    for (Superstate[] x : map) {
      for (Superstate y : x) {
        System.out.print("[");
        for (boolean i : y.getPossiblePatterns()) {
          System.out.print(i ? "1" : "0");
        }
        System.out.print("], ");
      }
      System.out.print("\n");
    }
    System.out.print("\n");
  }

  //choose the right pixel to collapse, then collapse it
  private int collapse () {
    countCollapse++;
    if (countCollapse>=map.length*map[0].length) {
      debugMode = true;
    }
    int chosenStX = 0;
    int chosenStY = 0;

    int minPossibilities = patterns.length;
    int maxPossibilities = 0;
    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[0].length; y++) {
        int possibilities = map[x][y].getNumberOfPossibilities();
        //System.out.print(possibilities + " ");

        if (possibilities <= minPossibilities && possibilities != 1) {
          chosenStX = x;
          chosenStY = y;
          minPossibilities = possibilities;
        }
        maxPossibilities = Math.max(maxPossibilities, possibilities);
      }
      //System.out.print("\n");
    }

    //tell the state to collapse. it will run a recursion collapsing all affected states
    if (maxPossibilities == 1) {
      return 0;
    }else if(maxPossibilities == 0) {
      return 2;
    }

    map[chosenStX][chosenStY].collapse();
    return 1;
  }

  public Pattern getPattern(int index) {
    return patterns[index];
  }

  public void saveImg(Color[] colors) {
    BufferedImage img = new BufferedImage(map.length, map[0].length, BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[0].length; y++) {

        for (int i = 0; i < map[x][y].getPossiblePatterns().length; i++) {
          if (map[x][y].getPossiblePatterns()[i]) {
            int mid = patterns[0].map.length / 2;
            int rbg = patterns[i].map[mid][mid];
            img.setRGB(x,y, colors[rbg].getRGB());
          }
        }
      }
    }

    try {
      BufferedImage bi = img;  // retrieve image
      File outputfile = new File("./res/" + name + ".png");
      ImageIO.write(bi, "png", outputfile);
    } catch (IOException e) {
      System.out.println("hat nicht funktioniert");
    }
  }
}
