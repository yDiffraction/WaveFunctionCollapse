package PatternDetermination;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PatternDetermination {
  public Color[] allColors;//die erste Farbe, die ein ein Pattern hinzugefügt wird ist 0 -> immer so weiter -> sepertate Liste mit Farben der Farbindexwerten, um später aus Indesxwerten wieder farben zu machen
  public ArrayList<Pattern> patterns = new ArrayList<Pattern>();
  File file = new File("./fls/Angular.png");
  BufferedImage image;
  
  public PatternDetermination(){
    // Bild laden
	  loadImg();

    

  }
  public Pattern[] loadPatterns() {
	  ArrayList<Color> allColors = new ArrayList<>();

	  // determinate Patterns from inpImg
	  for (int x = 0; x < image.getHeight(); x++) {
		  for (int y = 0; y < image.getWidth(); y++) {
			  Pattern pattern = new Pattern(3);
			  for (int dX = -1; dX < 2; dX++) {
				  for (int dY = -1; dY < 2; dY++) {
					  int[] coords = getPxlCoordsByDeltaCoords(x, y, dX, dY);
					  if(!allColors.contains(new Color(image.getRGB(coords[0], coords[1])))) {
						  allColors.add(new Color(image.getRGB(coords[0], coords[1])));
					  }
					  pattern.setPixelColor(dX+1, dY+1, allColors.indexOf(new Color(image.getRGB(coords[0], coords[1]))));
				  }
			  }


			  boolean addPattern = false;
			  for (int i = 0; i < patterns.size(); i++) {
				  if (Arrays.deepEquals(patterns.get(i).map, pattern.map)) {
					  patterns.get(i).addWeight();
					  addPattern = true;
					  break;
				  }
			  }
			  if(!addPattern) {
				  patterns.add(pattern);
			  }
		  }
	  }

	  this.allColors = new Color[allColors.size()];
	  this.allColors = allColors.toArray(this.allColors);

	  Pattern[] returnPats = new Pattern[patterns.size()];
	  returnPats = patterns.toArray(returnPats);
	  return returnPats;
  }
  
  private int[] getPxlCoordsByDeltaCoords(int x, int y, int deltX, int deltY) {
	  int[] coords = new int[2];
	  coords[0] = (((x + deltX) % image.getWidth()) + image.getWidth()) % image.getWidth();
	  coords[1] = (((y + deltY) % image.getHeight()) + image.getHeight()) % image.getHeight();
	  return coords;
  }
  private void loadImg() {
	  this.image = null;
	  try {
		  image = ImageIO.read(file);
	  } catch (IOException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
  }

}
