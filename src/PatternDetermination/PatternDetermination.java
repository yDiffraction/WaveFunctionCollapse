package PatternDetermination;

import java.awt.*;
import java.io.*;
import java.util.Set;

public class PatternDetermination { // filme wie falsch das ist
  public Set<Integer> allcolors;
  Pattern[] patterns = new Pattern[0];
  File file = new File(“WaveFunctionCollapse/src/PatternDetermination/Test.png”);
  
  public PatternDetermination() {
    // Bild laden
    BufferedImage image = ImageIO.read(file);
    
    // allen farben einen wert zuweisen
    for (int h = 0; h < image.getHeight(); h++) {
      for int w = 0; w < image.getWidth(); w++) {
        allcolors.add(getRGB(w,h));
      }
    }
    
    // alle felder mit mindestens einem abstand von 1 zum rand abgehen 
    for (int h = 1; h < image.getHeight()-1; h++) {
      for (int w = 1; w < image.getWidth()-1; w++) {
        patterns.push(new Pattern());
      }
    }
  }
}
