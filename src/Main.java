import OutputGenerator.OutputGenerator;
import PatternDetermination.PatternDeterminatiom;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        PatternDetermination patternGen = new PatternDetermination();
        Pattern[] patterns = {new Pattern({{0, 1, 1}, {1, 1, 1}, {1, 1, 1}}, 1)};
        OutputGenerator OutputGen = new OutputGenerator();
    }
}
