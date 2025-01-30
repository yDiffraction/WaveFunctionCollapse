public class Superstate {
  boolean[] possible_patterns;
  boolean[] possible_colors;
  
  public Superstate(int num_patterns, int num_colors) {
    //initialize possible_patterns and possible_colors
    this.possible_patterns = new boolean[num_patterns]();
    this.possible_colors = new boolean[num_colors]();
    for (int i=0;i<num_patterns;i++) {
      possible_patterns[i] = 1;
    }for (int i=0;i<num_colors;i++) {
      possible_colors[i] = 1;
    }
  }
}
