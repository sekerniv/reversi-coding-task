package reversi;

import java.util.Arrays;

public class ReversiGame {
	public static int PLAYER_ONE = 1;
	public static int PLAYER_TWO = 2;

	private int[][] board;

	private int curPlayer;

	/**
	 * Initializes the board: all squares are 0 except the four disks in the
	 * middle
	 * Task 1: Implement this method
	 * 
	 * @return the initialized board
	 */
	public ReversiGame() {
		curPlayer = PLAYER_ONE;
		int size = 8;
		int[][] board = new int[size][size];
		board[size / 2 - 1][size / 2 - 1] = 1;
		board[size / 2][size / 2] = 1;
		board[size / 2][size / 2 - 1] = 2;
		board[size / 2 - 1][size / 2] = 2;
		this.board = board;
	}
	
	/**
	 * Initializes the board by copying the fields from the given game
	 * @param game
	 */
	public ReversiGame(ReversiGame game) {
		this.curPlayer = game.curPlayer;
		this.board = new int[game.board.length][game.board[0].length];
		for (int i = 0; i < game.board.length; i++) {
			for (int j = 0; j < game.board.length; j++) {
				this.board[i][j] = game.board[i][j];
			}
		}
	}

	/**
	 * Task 1: Implement this method
	 * 
	 * @return
	 */
	public int[][] getBoard() {
		return this.board;
	}

	public int getCurPlayer() {
		return this.curPlayer;
	}

	/**
	 * Task 2: Implement this method
	 * 
	 * Prints the board with the row/column indices to the console in the following
	 * format
	 * 
	 * 0 1 2 3 4 5 6 7
	 * 0 0 0 0 1 0 0 0 0
	 * 1 0 0 0 1 0 0 0 0
	 * 2 0 0 1 2 2 2 0 0
	 * 3 0 0 0 1 2 0 0 0
	 * 4 0 0 0 2 1 0 0 0
	 * 5 0 0 0 0 0 0 0 0
	 * 6 0 0 0 0 0 0 0 0
	 * 7 0 0 0 0 0 0 0 0
	 * 
	 * *
	 */

	public void printBoard() {
		System.out.println("Current Board State: ");
		System.out.print("  ");
		for (int i = 0; i < board.length; i++) {
			System.out.print(i + " ");
		}
		System.out.println();

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length + 1; j++) {
				if (j == 0) {
					System.out.print(i + " ");
				} else {
					System.out.print(board[i][j - 1] + " ");
				}
			}
			System.out.println();
		}
	}

	/**
	 * * Task 3: Implement this method
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isOnBoard(int row, int col) {
		return ((row < this.board.length) && (col < this.board[0].length) && (row >= 0) && (col >= 0));
	}

	/**
	 * Task 3: Implement this method
	 * Returns the opponets player number (1 or 2) for a given player number (1 or
	 * 2)
	 * 
	 * @param player
	 * @return
	 */
	public static int opponentPlayer(int player) {
		return player == PLAYER_ONE ? PLAYER_TWO : PLAYER_ONE;
	}

	/**
	 * Task 4: Implement this method
	 * 
	 * @param row    row of the move
	 * @param col    column of the move
	 * @param rowInc row increment (-1, 0, or 1)
	 * @param colInc
	 * @return the number of disks that were flipped in the given direction
	 */
	private int updateMoveDisksInSingleDirection(int row, int col, int rowInc, int colInc) {

		int numOfFlips = calcFlipsInDirection(this.curPlayer, row, col, rowInc, colInc);
		if (numOfFlips == 0) {
			return 0;
		}

		int curRow = row + rowInc;
		int curCol = col + colInc;

		while (this.board[curRow][curCol] != this.curPlayer) {
			this.board[curRow][curCol] = this.curPlayer;
			curRow += rowInc;
			curCol += colInc;
		}

		return numOfFlips;
	}

	/**
	 * Task 4: Implement this method
	 * 
	 * @param player    player number (1 or 2)
	 * @param row       row of the move
	 * @param column    column of the move
	 * @param rowInc    row increment (-1, 0, or 1)
	 * @param columnInc column increment (-1, 0, or 1)
	 * @return the number of disks that would be flipped in the given direction
	 */
	private int calcFlipsInDirection(int player, int row, int col, int rowInc, int columnInc) {
		if (!isOnBoard(row, col)) {
			throw new RuntimeException("Position is off board");
		}

		int numOfFlips = 0;
		int curRow = row + rowInc;
		int curColumn = col + columnInc;

		while (isOnBoard(curRow, curColumn) && this.board[curRow][curColumn] == opponentPlayer(player)) {
			numOfFlips++;
			curRow += rowInc;
			curColumn += columnInc;
		}

		if (isOnBoard(curRow, curColumn) && this.board[curRow][curColumn] == player) {
			return numOfFlips;
		}

		return 0;
	}

	/**
	 * Task 5: Implement this method
	 * 
	 * @param row row of the move
	 * @param col column of the move
	 * @return true if the move was played, false if it failed to be played
	 */
	public boolean placeDisk(int row, int col) {
		// System.out.println("Place disk: " + this.curPlayer + " at row: " + row + " column: " + col);

		if (this.board[row][col] != 0) {
			return false;
		}
		boolean success = false;

		for (int rowInc = -1; rowInc <= 1; rowInc++) {
			for (int colInc = -1; colInc <= 1; colInc++) {
				if (updateMoveDisksInSingleDirection(row, col, rowInc, colInc) > 0) {
					this.board[row][col] = this.curPlayer;
					success = true;
				}
			}
		}
		if (success) {
			switchToNextPlayablePlayer();
		}

		return success;
	}

	// ================================================================================
	// END OF PART 1
	// ===============================================================================


	// ========================================
	// START of PART 2
	// ========================================

	/**
	 * Task 7: Implement this method
	 * @param player - the player that is making the move
	 * @param row - the row of the move
	 * @param col
	 * @return
	 */
	private int calcMoveBenefit(int player, int row, int col) {
		int benefit = 0;
		if (this.board[row][col] != 0) {
			return 0;
		}

		for (int rowInc = -1; rowInc <= 1; rowInc++) {
			for (int colInc = -1; colInc <= 1; colInc++) {
				benefit += calcFlipsInDirection(player, row, col, rowInc, colInc);
			}
		}
		return benefit;
	}


	/**
	 * Task 8: Implement this method
	 * 
	 * @return an array of all possible moves for the current player (the array
	 *         doesn't contain nulls). If there are no possible moves, return an
	 *         empty array
	 */
	private MoveScore[] getPossibleMoves(int player) {
		MoveScore[] possibleMoves = new MoveScore[this.board.length * this.board[0].length];
		int index = 0;
		for (int row = 0; row < this.board.length; row++) {
			for (int col = 0; col < this.board[row].length; col++) {
				int benefit = calcMoveBenefit(player, row, col);
				if (benefit > 0) {
					possibleMoves[index] = new MoveScore(row, col, benefit);
					index++;
				}
			}
		}
		return Arrays.copyOf(possibleMoves, index);
	}

	/**
	 * 
	 * @return an array of all possible moves for the current player (the array
	 */
	public MoveScore[] getPossibleMoves() {
		return this.getPossibleMoves(this.curPlayer);
	}

	/**
	 * Task 9: Implement this method
	 * 
	 * A game is over if none of the players have a move to play (i.e. no empty
	 * squares left on the board or none of the players have a valid move)
	 * @return true if the game is over, false otherwise
	 */
	public boolean isGameOver() {
		return this.getPossibleMoves(PLAYER_ONE).length == 0 && this.getPossibleMoves(PLAYER_TWO).length == 0;
	}

	/**
	 * Task 10: Implement this method
	 * This method switches to the opponent player. If there's no move for the opponent player to play, the current player remains the same
	 * @return the current player after the switch
	 */
	public int switchToNextPlayablePlayer() {
		this.curPlayer = opponentPlayer(this.curPlayer);
		if (this.getPossibleMoves().length == 0) {
			// System.out.println("There are no possible moves for player " + this.curPlayer + " skipping turn");
			this.curPlayer = opponentPlayer(this.curPlayer);
		}

		return this.curPlayer;
	}

	/**
	 * Task 11: Implement this method
	 * A player wins if the game is over and the player has more pieces on the board
	 * than the opponent
	 * 
	 * @return -1 if the game is not over, 0 for tie, 1 if player 1 wins, 2 if
	 *         player 2 wins
	 */
	public int getWinner() {
		if (!isGameOver()) {
			return -1;
		}
		int playerOneCount = 0;
		int playerTwoCount = 0;
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				if (this.board[i][j] == PLAYER_ONE) {
					playerOneCount++;
				}
				if (this.board[i][j] == PLAYER_TWO) {
					playerTwoCount++;
				}
			}
		}

		if (playerOneCount > playerTwoCount) {
			return PLAYER_ONE;
		} else if (playerTwoCount > playerOneCount) {
			return PLAYER_TWO;
		} else {
			return 0;
		}
	}
}
