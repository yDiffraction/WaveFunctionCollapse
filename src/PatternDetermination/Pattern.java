package PatternDetermination;

public class Pattern {
  public int[][] map; // int are colores(they are spanish!)
  public float weight;

  public Pattern(int pattern_size) {
    map = new int[pattern_size][pattern_size];
    weight = 1;
  }
  public Pattern(int[][] map, int weight) {
	 this.map = map;
	 this.weight = weight;
	 }
  
  public void setPixelColor(int x, int y, int colorID) {
	  map[x][y] = colorID;
  }
  public void addWeight() {
	  weight++;
	 
  }

  public int getSize() {
    return map.length;
  }
}
