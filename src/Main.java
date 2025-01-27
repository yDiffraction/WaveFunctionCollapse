import OutputGenerator.OutputGenerator;
import PatternDetermination.PatternDeterminatiom;
import PatternDetermination.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        PatternDetermination patternGen = new PatternDetermination();
        Pattern[] patterns = {new Pattern(
            {{0, 1, 1},
             {1, 1, 1},
             {1, 1, 1}},
        1)};
        Set colors;
        
        OutputGenerator OutputGen = new OutputGenerator(patterns, colors);
    }
}
