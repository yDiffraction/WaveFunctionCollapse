package PatternDetermination;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class PatternDetermination { // filme wie falsch das ist
  public ArrayList<Color> allcolors = new ArrayList<Color>();//die erste Farbe, die ein ein Pattern hinzugefügt wird ist 0 -> immer so weiter -> sepertate Liste mit Farben der Farbindexwerten, um später aus Indesxwerten wieder farben zu machen
  public ArrayList<Pattern> patterns = new ArrayList<Pattern>();
  File file = new File("./fls/Test.png");
  BufferedImage image;
  
  public PatternDetermination(){
    // Bild laden
	  this.image = null;
	  try {
	      image = ImageIO.read(file);
	  } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	  }
    
	  //Loop -> bestimmung aller Patterns -> die erste Farbe ist ID/Index 0 -> Arraylist(Colors).add(neue Farbe)
	  // Arraylist(Colors) -> allcolores 
    // allen farben einen wert zuweisen
    
    for (int x = 0; x < image.getHeight(); x++) {
      for (int y = 0; y < image.getWidth(); y++) {
    	  Pattern pattern = new Pattern(3);
	       for (int dX = -1; dX < 2; dX++) {
	    	   for (int dY = -1; dY < 2; dY++) {
	    		  int[] coords = get_pxlCoords_by_DeltaCoords(x, y, dX, dY);
	    		  if(!allcolors.contains(new Color(image.getRGB(coords[0], coords[1])))) {
	    			  allcolors.add(new Color(image.getRGB(coords[0], coords[1])));	   
	    		  }
	    		  pattern.setPixelColor(dX+1, dY+1, allcolors.indexOf(new Color(image.getRGB(coords[0], coords[1]))));
	    	  }
	       }
	       if(patterns.contains(pattern)) {
	    	   patterns.get(patterns.indexOf(pattern)).addWeight();
	       }else {
	    	   patterns.add(pattern);
	       }
      	}
    }
  }
  
  private int[] get_pxlCoords_by_DeltaCoords (int x,int y, int deltX, int deltY) {
	  int[] coords = new int[2];
	  coords[0] = (((x+deltX) % image.getHeight())+image.getHeight()) % image.getHeight();
	  coords[1] = (((y+deltY) % image.getWidth())+image.getWidth()) % image.getWidth();
	  return coords;
  }

}
