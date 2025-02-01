import OutputGeneration.GeneratorMain;
import PatternDetermination.PatternDetermination;
import PatternDetermination.Pattern;

import java.util.HashSet;
import java.util.Set;

public class GenHub {
    public PatternDetermination patternGen;
    public GeneratorMain outputGen;

    public int colors;//die anzahl an Farben in einem Int reichen aus als Info, weil bei z.B. 5 Farben gibt es Farben 0, 1, 2, 3, 4 ->
    public Pattern[] patterns = new Pattern[1];


    public GenHub() {
        //List of patterns for use in generating image
        patterns[0] = new Pattern(new int[][]{
                {0,0,0},
                {0,1,0},
                {0,0,0}
        }, 1);
        colors = 2;

        patternGen = new PatternDetermination();
        outputGen = new GeneratorMain(patterns, colors, 20, 20);


        //all colors stored in a Set
        /*colors = new HashSet<Integer>(); -> useless
        for (Pattern x : patterns) {
            for (int[] y : x.map) {
                for (int z : y) {
                    colors.add(z);
                }
            }
        }*/
    }

    public void genOutput() {
        outputGen.run();
    }

    public void setPatterns(Pattern[] patterns) {
        this.patterns = patterns;
    }
}
