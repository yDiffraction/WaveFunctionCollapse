import PatternDetermination.Pattern;

public class GeneratorMain {
  Patterns[] patterns;
  Set<Int> colors;
  
  public GeneratorMain(Patterns[] patterns, Set<Int> colors) {
    System.out.println("Generator started");
    this.patterns = patterns;
    this.colors = colors;
  }
}
