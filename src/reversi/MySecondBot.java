package reversi;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MySecondBot implements ReversiBot {

    private final ReversiGame game;

    public MySecondBot(ReversiGame game) {
        this.game = game;
    }

    public MoveScore getNextMove() {
        return getRandomMove();
    }

    public MoveScore getNextGreedyMove() {

        MoveScore[] possibleMoves = this.game.getPossibleMoves();
        // adding randomness to the order of the moves
        Collections.shuffle(Arrays.asList(possibleMoves));

        if (possibleMoves.length == 0) {
            return null;
        }

        MoveScore bestMove = possibleMoves[0];
        for (int i = 1; i < possibleMoves.length; i++) {
            if (possibleMoves[i].getScore() > bestMove.getScore()) {
                bestMove = possibleMoves[i];
            }
        }
        return bestMove;
    }

    public MoveScore getRandomMove() {
        MoveScore[] possibleMoves = this.game.getPossibleMoves();
        if (possibleMoves.length == 0) {
            return null;
        }
        Random rand = new Random();
        return possibleMoves[rand.nextInt(possibleMoves.length)];
    }
}
