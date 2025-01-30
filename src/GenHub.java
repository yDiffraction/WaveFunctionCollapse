import OutputGeneration.GeneratorMain;
import PatternDetermination.PatternDetermination;
import PatternDetermination.Pattern;

import java.util.HashSet;
import java.util.Set;

public class GenHub {
    public PatternDetermination patternGen;
    public GeneratorMain outputGen;

    public Set<Integer> colors;
    public Pattern[] patterns = new Pattern[0];


    public GenHub() {
        patternGen = new PatternDetermination();
        outputGen = new GeneratorMain(patterns, colors, 20, 20);

        //List of patterns for use in generating image
        patterns[0] = new Pattern(new int[][]{
                {0,0,0},
                {0,1,0},
                {0,0,0}
        }, 1);

        // all Colors stored in a Set
        colors = new HashSet<Integer>();for (Pattern x : patterns) {
            for (int[] y : x.map) {
                for (int z : y) {
                    colors.add(z);
                }
            }
        }
        outputGen.run();

    }

    public void setPatterns(Pattern[] patterns) {
        this.patterns = patterns;
    }
}
