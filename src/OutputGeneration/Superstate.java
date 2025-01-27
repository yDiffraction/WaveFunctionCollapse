public class Superstate {
  boolean[] possible_patterns;
  boolean[] possible_colors;
  
  public Superstate(int num_patterns, int num_colors) {
    this.possible_patterns = new boolean[num_patterns]();
  }
}
