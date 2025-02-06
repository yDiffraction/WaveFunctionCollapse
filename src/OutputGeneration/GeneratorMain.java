package OutputGeneration;

import PatternDetermination.Pattern;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


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
    //debugMap();
    while (!collapsed) {
      collapsed = !collapse();
      /*if (!collapsed) {
        System.out.println("----------------------------------Code Ran----------------------------------");
        debugMap();
      }*/
    }
  }

  public void debugMap() {
    for (Superstate[] x : map) {
      for (Superstate y : x) {
        System.out.print("[");
        for (boolean i : y.possibleColors) {
          System.out.print(i ? "1" : "0");
        }
        System.out.print("][");
        for (boolean i : y.possiblePatterns) {
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
    int chosenStX = 0;
    int chosenStY = 0;

    int minPossibilities = colorsCount;
    int maxPossibilities = 0;
    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[0].length; y++) {
        int possibilities = map[x][y].getNumberOfPossibilities();

        if (possibilities <= minPossibilities && possibilities != 1) {
          chosenStX = x;
          chosenStY = y;
          minPossibilities = possibilities;
        }
        maxPossibilities = Math.max(maxPossibilities, possibilities);
      }
    }

    //tell the state to collapse. it will run a recursion collapsing all affected states
    if (maxPossibilities == 1) {
      return false;
    }
    map[chosenStX][chosenStY].collapse();
    return true;
  }

  public Pattern getPattern(int index) {
    return patterns[index];
  }

  public void saveImg(Color[] colors) {
    BufferedImage img = new BufferedImage(map.length, map[0].length, BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[0].length; y++) {

        for (int i = 0; i < map[x][y].possibleColors.length; i++) {
          if (map[x][y].possibleColors[i]) {
            img.setRGB(x,y, colors[i].getRGB());
          }
        }
      }
    }

    try {
      BufferedImage bi = img;  // retrieve image
      File outputfile = new File("./res/saved.png");
      ImageIO.write(bi, "png", outputfile);
    } catch (IOException e) {
      // handle exception
    }
  }
}
