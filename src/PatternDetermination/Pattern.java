package PatternDetermination;

public class Pattern {
    public int[][] map; // int are colores(they are spanish!)
    public float weight;

    public Pattern(int patternSize) {
        map = new int[patternSize][patternSize];
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
