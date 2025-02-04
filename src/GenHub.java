import OutputGeneration.GeneratorMain;
import PatternDetermination.PatternDetermination;
import PatternDetermination.Pattern;

public class GenHub {
    public PatternDetermination patternGen;
    public GeneratorMain outputGen;

    public int countColors;//die anzahl an Farben in einem Int reichen aus als Info, weil bei z.B. 5 Farben gibt es Farben 0, 1, 2, 3, 4 ->
    public Pattern[] patterns = new Pattern[1];


    public GenHub() {
        //List of patterns for use in generating image
        patterns[0] = new Pattern(new int[][]{
                {0,0,0},
                {0,1,0},
                {0,0,0}
        }, 1);
        countColors = 2;

        patternGen = new PatternDetermination();
        outputGen = new GeneratorMain(patterns, countColors, 20, 20);

    }

    public void genOutput() {
        outputGen.run();
    }

    public void setPatterns(Pattern[] patterns) {
        this.patterns = patterns;
    }
}
