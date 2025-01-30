import OutputGenerator.OutputGenerator;
import PatternDetermination.PatternDeterminatiom;
import PatternDetermination.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        PatternDetermination patternGen = new PatternDetermination();

        //List of patterns for use in generating image
        Pattern[] patterns = {new Pattern(
            {{0, 1, 1},
             {1, 1, 1},
             {1, 1, 1}},
        1)};

        //Determine a set of all colors in use
        Set colors = new HashSet<Int>();
        for (Pattern x : patterns) {
            for (int[] y : x.map) {
                for (int z : y) {
                    colors.add(z)
                }
            }   
        }
        
        OutputGenerator OutputGen = new OutputGenerator(patterns, colors);
    }
}
