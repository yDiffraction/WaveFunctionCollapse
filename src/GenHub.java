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

        patternGen = new PatternDetermination();
        patterns = patternGen.loadPatterns();
        countColors = patternGen.allColors.length;

        outputGen = new GeneratorMain(patterns, countColors, 11, 11);
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
        outputGen.saveImg(patternGen.allColors);
    }

    public void setPatterns(Pattern[] patterns) {
        this.patterns = patterns;
    }
}
