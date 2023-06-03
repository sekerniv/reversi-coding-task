package reversi;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class TwoStepsWithLocationBot implements ReversiBot {

    private final ReversiGame game;

    public TwoStepsWithLocationBot(ReversiGame game) {
		this.game = game;
	}

	public MoveScore getNextMove() {
		return getTwoStepsPreferLocationMove();
	}

	public MoveScore getNextGreedyMove(){
		
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

	public MoveScore getRandomMove(){
		MoveScore[] possibleMoves = this.game.getPossibleMoves();
		if (possibleMoves.length == 0) {
			return null;
		}
		Random rand = new Random();
		return possibleMoves[rand.nextInt(possibleMoves.length)];		
	}

	public MoveScore getTwoStepsPreferLocationMove() {
		MoveScore[] moveBenefits = getTwoStepsGreedyMoveBenefitsForMoves(this.game.getPossibleMoves());
		if (moveBenefits.length == 0) {
			return null;
		}
		// adding randomness to the order of the moves
		Collections.shuffle(Arrays.asList(moveBenefits));

		MoveScore[] updatedBenefits = new MoveScore[moveBenefits.length];
		for (int i = 0; i < moveBenefits.length; i++) {
			MoveScore curMoveBenefit = moveBenefits[i];
			if (isInCorner(curMoveBenefit)) {
				updatedBenefits[i] = new MoveScore(curMoveBenefit.getRow(), curMoveBenefit.getColumn(), curMoveBenefit.getScore() + 10);
			} else if (isOnEdge(curMoveBenefit)) {
				updatedBenefits[i] = new MoveScore(curMoveBenefit.getRow(), curMoveBenefit.getColumn(), curMoveBenefit.getScore() + 1);
			} else {
				updatedBenefits[i] = curMoveBenefit;
			}
		}
		Arrays.sort(updatedBenefits,0, updatedBenefits.length, new Comparator<MoveScore>() {
			@Override
			public int compare(MoveScore o1, MoveScore o2) {
				return  o2.getScore() - o1.getScore();
			}
		});
		return updatedBenefits[0];
	}

	private static boolean isOnEdge(MoveScore moveBenefit){
		if(moveBenefit.getRow() == 0 || moveBenefit.getColumn() == 0 || moveBenefit.getRow() == 7 || moveBenefit.getColumn() == 7){
			return true;
		} else {
			return false;
		}
	}
	private static boolean isInCorner(MoveScore moveBenefit){
		if(moveBenefit.getRow() == 0 && moveBenefit.getColumn() == 0){
			return true;
		} else if(moveBenefit.getRow() == 0 && moveBenefit.getColumn() == 7){
			return true;
		} else if(moveBenefit.getRow() == 7 && moveBenefit.getColumn() == 0){
			return true;
		} else if(moveBenefit.getRow() == 7 && moveBenefit.getColumn() == 7){
			return true;
		} else {
			return false;
		}
	}


	public MoveScore getTwoStepsGreedyMove() {
		MoveScore[] moveBenefits = getTwoStepsGreedyMoveBenefitsForMoves(this.game.getPossibleMoves());
		MoveScore bestMove = moveBenefits[0];
		for (int i = 1; i < moveBenefits.length; i++) {
			if (moveBenefits[i].getScore() > bestMove.getScore()) {
				bestMove = moveBenefits[i];
			}
		}
		return bestMove;
	}

	private MoveScore[] getTwoStepsGreedyMoveBenefitsForMoves(MoveScore[] moves) {
		MoveScore[] twoStepsGreedyMoveBenefits = new MoveScore[moves.length];
		for (int i = 0; i < moves.length; i++) {
			twoStepsGreedyMoveBenefits[i] = getTwoStepsGreedyMove(moves[i]);
		}
		return twoStepsGreedyMoveBenefits;

	}

	private MoveScore getTwoStepsGreedyMove(MoveScore moveToEvaluate) {
		
		ReversiGame gameCopy = new ReversiGame(this.game);
		TwoStepsWithLocationBot nextMoveGreedyBot = new TwoStepsWithLocationBot(gameCopy);	
		nextMoveGreedyBot.game.placeDisk(moveToEvaluate.getRow(), moveToEvaluate.getColumn());
		MoveScore nextGreedyMove = nextMoveGreedyBot.getNextGreedyMove();
		int score;
		if (nextGreedyMove == null) {
			// if the next player has no possible moves we score this move as the number of disks that will be flipped
			score = moveToEvaluate.getScore();
		} else {
			score = moveToEvaluate.getScore() - nextGreedyMove.getScore();
		}
		return new MoveScore(moveToEvaluate.getRow(), moveToEvaluate.getColumn(), score);
	}
}