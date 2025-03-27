package OutputGeneration;

public interface State {
    public int getNumberOfPossibilities();
    public void collapse();
    public void updateState(int depth);
    public int printColor();
    public boolean[] getPossiblePatterns();

}
