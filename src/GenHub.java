import OutputGeneration.GeneratorMain;
import OutputGeneration.Superstate;
import PatternDetermination.PatternDetermination;
import PatternDetermination.Pattern;

public class GenHub {
    public PatternDetermination patternGen;
    public GeneratorMain outputGen;

    public int countColors;//die anzahl an Farben in einem Int reichen aus als Info, weil bei z.B. 5 Farben gibt es Farben 0, 1, 2, 3, 4 ->
    public Pattern[] patterns = new Pattern[10];


    public GenHub() {
        //List of patterns for use in generating image
        patterns[0] = new Pattern(new int[][]{
                {0,0,0},
                {0,1,0},
                {0,0,0}
        }, 1);
        patterns[1] = new Pattern(new int[][]{
                {1,0,0},
                {0,0,0},
                {0,0,0}
        }, 1);
        patterns[2] = new Pattern(new int[][]{
                {0,1,0},
                {0,0,0},
                {0,0,0}
        }, 1);
        patterns[3] = new Pattern(new int[][]{
                {0,0,1},
                {0,0,0},
                {0,0,0}
        }, 1);
        patterns[4] = new Pattern(new int[][]{
                {0,0,0},
                {1,0,0},
                {0,0,0}
        }, 1);
        patterns[5] = new Pattern(new int[][]{
                {0,0,0},
                {0,0,1},
                {0,0,0}
        }, 1);
        patterns[6] = new Pattern(new int[][]{
                {0,0,0},
                {0,0,0},
                {1,0,0}
        }, 1);
        patterns[7] = new Pattern(new int[][]{
                {0,0,0},
                {0,0,0},
                {0,1,0}
        }, 1);
        patterns[8] = new Pattern(new int[][]{
                {0,0,0},
                {0,0,0},
                {0,0,1}
        }, 1);
        patterns[9] = new Pattern(new int[][]{
                {0,0,0},
                {0,0,0},
                {0,0,0}
        }, 1);
        countColors = 2;

        patternGen = new PatternDetermination();
        patternGen.loadPatterns();

        outputGen = new GeneratorMain(patterns, countColors, 16, 16);
    }

    public void genOutput() {
        outputGen.run();
        System.out.println("Output");
        for (Superstate[] x : outputGen.map) {
            for (Superstate y : x) {
                System.out.print(y.printColor() + " ");
            }
            System.out.print("\n");
        }
    }

    public void setPatterns(Pattern[] patterns) {
        this.patterns = patterns;
    }
}
