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
	File file;
	BufferedImage image;
	public int patternSize = 3;
	public int mid = patternSize / 2;

	public PatternDetermination(String name){
		// Bild laden
		file = new File("./fls/" + name + ".png");
		loadImg();
	}
	public Pattern[] loadPatterns() {
		ArrayList<Color> allColors = new ArrayList<>();

		// determinate Patterns from inpImg
		for (int x = 0; x < image.getHeight(); x++) {
			for (int y = 0; y < image.getWidth(); y++) {
				Pattern pattern = new Pattern(patternSize);
				for (int dX = -mid; dX <= mid; dX++) {
					for (int dY = -mid; dY <= mid; dY++) {
						int[] coords = getPxlCoordsByDeltaCoords(x, y, dX, dY);
						if(!allColors.contains(new Color(image.getRGB(coords[0], coords[1])))) {
							allColors.add(new Color(image.getRGB(coords[0], coords[1])));
						}
						pattern.setPixelColor(dX+mid, dY+mid, allColors.indexOf(new Color(image.getRGB(coords[0], coords[1]))));
					}
				}


				boolean addPattern = true;
				for (int i = 0; i < patterns.size(); i++) {
					if (Arrays.deepEquals(patterns.get(i).map, pattern.map)) {
						patterns.get(i).addWeight();
						addPattern = false;
						break;
					}
				}
				if(addPattern) {
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

	// den gesuchten Pixel der außerhalb des Bildes ist auf die andere Seite des Bildes schieben
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
