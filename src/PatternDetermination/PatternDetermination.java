import java.awt.*;
import java.io.*;
public class PatternDetermination {
  public PatternDetermination() {
    // Bild laden
    File file = new File(“WaveFunctionCollapse/src/PatternDetermination/Test.png”);
    BufferedImage image = ImageIO.read(file);
    height = image.getHeight();
    width = image.getWidth();
    
  }
}
