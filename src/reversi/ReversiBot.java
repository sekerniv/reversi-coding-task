package reversi;

/**
 * An interface for a reversi bot. A bot is a player that can play reversi.
 * * PLEASE DON'T CHANGE THIS FILE!
 * I"ll explain it in class.
 */
public interface ReversiBot {
    /**
     * @return the next move that the bot wants to make or null if the bot has no possible moves.
     */ 
    public MoveScore getNextMove();  

}
