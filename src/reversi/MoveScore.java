package reversi;

public class MoveScore {
    private final int row;
    private final int column;
    private final int score;
    
    public MoveScore(int row, int column, int score) {
        this.row = row;
        this.column = column;
        this.score = score;
    }

    public int getRow() {
        return row;
    }
    
    public int getColumn() {
        return column;
    }

    public int getScore() {
        return score;
    }
}