package reversi;

import java.util.Scanner;

public class ReversiPlay {

	//TODO: delete
	public static int size; // the board size.
	// public static ClassGrid grid;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		// 	TODO: remove the 8*8 requirement
		System.out.println("Please note that the main will work only with board of 8*8 !!!");
		System.out.println("Note that it does not check all the program, you should check it yourself !!");
		System.out.println("Please enter the size of the board: ");
		int size = scanner.nextInt();
		while (size % 2 != 0 | size < 3 | size > 32) {
			
			System.out.println("Please enter a valid board size");
			size = scanner.nextInt();
		}
		ReversiGame game = new ReversiGame();
		game.printBoard();
//        grid = new ClassGrid(size);
//        
//        
//        showBoard(board);
//        System.out.println("winner so far: "+findTheWinner(board));
//        Console.readString("press ENTER to continue");
//        System.out.println("play(board,1,3,2)");
//        play(board,1,3,2);
//        showBoard(board);
//        System.out.println("winner so far: "+findTheWinner(board));
//        Console.readString("press ENTER to continue");
//        System.out.println("isLegal(board,2,4,2): "+isLegal(board,2,4,2)+ " (should be true)");
//        System.out.println("play(board,2,4,2);");
//        play(board,2,4,2);
//        showBoard(board);
//        System.out.println("winner so far: "+findTheWinner(board));
//        Console.readString("press ENTER to continue");
//        System.out.println("isLegal(board,1,2,2): "+isLegal(board,1,2,2)+ " (should be false)");
//        System.out.println("play(board,1,2,2); //should not change the board");
//        play(board,1,2,2);
//        Console.readString("press ENTER to continue");
//        System.out.println("isLegal(board,1,3,2): "+isLegal(board,1,3,2)+ " (should be false)");
//        System.out.println("play(board,1,3,2); //should not change the board");
//        play(board,1,3,2);
//        Console.readString("press ENTER to continue");
//        System.out.println("benefit(board,1,5,1): "+benefit(board,1,5,1)+" (should be 1)");
//        Console.readString("press ENTER to continue");
//        System.out.println("player 1 possible moves: ");
//        printMoves(possibleMoves(board,1));
//        System.out.println("check out that it is all the possible moves !!");
//        System.out.println("random move of player 1: randomPlayer(board,1)");
//        showBoard(randomPlayer(board,1));
//        System.out.println("winner so far: "+findTheWinner(board));
//        Console.readString("press ENTER to continue");
//        System.out.println("player 2 possible moves: ");
//        printMoves(possibleMoves(board,2));
//        System.out.println("check out that it is all the possible moves !!");
//        System.out.println("greedy move of player 2: greedPlayer(board,2)");
//        showBoard(greedyPlayer(board,2));
//        System.out.println("winner so far: "+findTheWinner(board));
//        System.out.println("hasMoves(board,2): "+hasMoves(board,2)+" (should be true)");
//        System.out.println("gameOver(board): "+gameOver(board)+" (should be false)");
//        Console.readString("when you press ENTER the game will continue between randomPlayer (player 1 -red) and greedyPlayer (player 2 -blue):");
//        int counter = 0;
//        while(!gameOver(board) && counter < size*size){
//            board = randomPlayer(board,1);
//            showBoard(board);
//            board = greedyPlayer(board,2);
//            showBoard(board);
//            counter=counter+1;
//        }
//        System.out.println("the winner: "+findTheWinner(board));
//        System.out.println("gameOver(board): "+gameOver(board)+" (should be true)");

		// if you want to play an interactive play against the computer you should
		// remove the comment from the next line:
		// interactivePlay();

		// if you want to play against your friend you should
		// remove the comment from the next line:
		// twoPlayers();

	}

	// **************************************** other functions
	// ********************************

	public static int[][] createDirectionsArr(int[][] board, int player, int row, int column, boolean shortArray) {
		// builds a directions array that holds the possibles directions in which the
		// player can go to from the specified location on the board
		// if shortArray is true it stops after finding the first good direction, if it's
		// false it continues to look for all the possible directions
		// in the end it returns an array of the form [[the row increment][the column
		// increment][how many steps to do in this direction]...]

		int rowIn = -1; // the row increment
		int columnIn = -1; // the column inrement
		int oppPlayer = player % 2 + 1; // the opponent's number;
		int rowIx, colIx; // the row and column index
		int goodDirectionIndex = 0;// the index in the returned array
		int[][] goodDirections = new int[8][3]; // keeps the directions in which the player can "eat" the opponent
												// player's disks and by that play a ligal move
												// the array is of the form [row][column][how many steps can he do in
												// this direction]
		boolean foundOne = false;
		for (int i = 0; i < 8; i = i + 1) {// inits the array to -2 that will represent an empty cell

			goodDirections[i][0] = -2; // inits the array
			goodDirections[i][1] = -2; // inits the array
			goodDirections[i][2] = -2; // inits the array

		}

		if (isOnBoard(row, column) && board[row][column] == 0) {// if the spot is empty and it's on the board
			while (rowIn <= 1 && ((shortArray && !foundOne) || !shortArray)) {// both of this whiles check all of the 8
																				// diection possibilties by changing the
																				// increasment values
				// from -1 to 1 for both the row and column increasments
				// this while stops if it passed on all of the 8 possiblities or if we are
				// looking only for one directionand and we found it
				while (columnIn <= 1) {
					if (rowIn != 0 || columnIn != 0) {// skips on the increasment of 0 and 0
						rowIx = row + rowIn; // increases the row number by the value of rowIn
						colIx = column + columnIn;
						int counter = 0; // counts the number of steps to do in the direction until you hit your disk
						while (isOnBoard(rowIx, colIx) && (board[rowIx][colIx] == oppPlayer)) {
							// keep on runing in the direction while he is on board and he still pass on the
							// opponents disk
							counter = counter + 1;
							rowIx = rowIx + rowIn; // increases the row number by the value of rowIn
							colIx = colIx + columnIn;
							if (isOnBoard(rowIx, colIx) && (board[rowIx][colIx] == player)) {
								// if after another increasment he is still on the board and he found his own
								// disk it means that this direction is good
								foundOne = true;
								goodDirections[goodDirectionIndex][0] = rowIn;// keeps all the good values
								goodDirections[goodDirectionIndex][1] = columnIn;
								goodDirections[goodDirectionIndex][2] = counter;
								goodDirectionIndex = goodDirectionIndex + 1;
							} // if

						} // while
					} // if
					columnIn = columnIn + 1;// change the column increasment
				} // while
				columnIn = -1;
				rowIn = rowIn + 1;
			} // while
		}
		return goodDirections;

	}// end of createDirectionsArr

	public static boolean isOnBoard(int row, int column) {
		// returns true if the location row,column is inside the board otherwise returns
		// false
		return ((row < size) && (column < size) && (row >= 0) && (column >= 0));
	}// isOnBoard

	public static int[][] duplicateBoard(int[][] board) {
		// gets a board ,create a duplication of it and return it;
		int[][] newBoard = new int[size][size];// create the board that willl be returned
		for (int i = 0; i < size; i = i + 1)// take the value from the given array and puts it in the new one
			for (int j = 0; j < size; j = j + 1)
				newBoard[i][j] = board[i][j];
		return newBoard;
	}// duplicateBoard

	public static int[] bestBenefit(int[][] board, int player) {
		// finds the move in which he flip the maximum number of disks
		// return the move inside an array {row,column,number of disks}
		// if there are no possible moves return an empty array
		int maxIndex = 0, maxValue = 0;// the maximum eaten disks and the move place inside posMov
		int posMovLength, theBenefit;// how many moves are there,the benefit from a specific move
		int[][] posMov; // the possible moves
		int[] returnedArray = new int[3];// the array that will be returned in the end

		posMov = possibleMoves(board, player);
		posMovLength = posMov.length;
		if (posMovLength > 0) {// if there are any possible moves
			for (int i = 0; i < posMovLength; i = i + 1) {// run across all the moves and the finds the one with the
															// highest benefit
				theBenefit = benefit(board, player, posMov[i][0], posMov[i][1]);// gets the benefit of a specific move
				if (theBenefit > maxValue) {// this one is better
					maxValue = theBenefit;
					maxIndex = i;
				}
			} // for
			returnedArray = new int[3];// build the returned array
			returnedArray[0] = posMov[maxIndex][0];
			returnedArray[1] = posMov[maxIndex][1];
			returnedArray[2] = maxValue;
		} // if
		else // if there are no possible moves returns empty array
			returnedArray = new int[0];
		return returnedArray;
	}// end of BestBenefit

	/**
	 * 
	 * Creates an initialized board - all squares are 0 besides the four disks in the middle
	 * @return the initialized board
	 */
	public static int[][] createBoard(int size) {
		
		int[][] board = new int[size][size];
		board[size / 2 - 1][size / 2 - 1] = 2;
		board[size / 2][size / 2] = 2;
		board[size / 2][size / 2 - 1] = 1;
		board[size / 2 - 1][size / 2] = 1;

		return board;
	}
	
	
	

	/*
	 * -----------------------------------------------------------------------------
	 * ----- * Task 2 *
	 * -----------------------------------------------------------------------------
	 * -----
	 */
	public static boolean isLegal(int[][] board, int player, int row, int column) {
		// check if the move (row,column) is ligal and return a boolean answer
		int[][] directionsArray;// holds the directions in which he will flip the opponent player's
								// disks and the number of disks that will be flip
		boolean ans = false;// holds the returned answer
		if (isOnBoard(row, column)) {// if the move is inside the board
			directionsArray = createDirectionsArr(board, player, row, column, true);// gets the ligal fliping directions
																					// directions
			ans = directionsArray[0][0] != -2;// if there will be no possible option the return array will hold only -2
												// values[[-2][-2][-2]...]
		}
		return (ans);
	}// end of isLegal

	/*
	 * --------------------------------------------------------------------------- *
	 * Task 3 *
	 * ---------------------------------------------------------------------------
	 */
	public static int[][] play(int[][] board, int player, int row, int column) {

		int[][] directionsArray;// holds the direction in which he can go to and the number of steps that he can
								// do
		directionsArray = createDirectionsArr(board, player, row, column, false);// gets the directions
		if (directionsArray[0][0] != -2) {// if there are any possible moves possible moves
			board[row][column] = player;// place the player's disk in the spot
			boolean keepRuning = true;// true if there are still directions to change
			int rowIndx = row, columnIndx = column, rowInc, columnInc;// the row and column values and the increasments
			int loopsNum;// number of steps to do in a paticular direction;
			int i = 0;// index inside the directionsArray
			while (i < 8 && keepRuning) {// run across the possible fliping directions in the directionsArray;
				rowInc = directionsArray[i][0];
				if (rowInc == -2)// if there are no more directions to check
					keepRuning = false;
				else {
					loopsNum = directionsArray[i][2];// how many steps to do
					columnInc = directionsArray[i][1];
					rowIndx = row;
					columnIndx = column;
					for (int j = 0; j < loopsNum; j = j + 1) {// walk the number of steps in this direction
						rowIndx = rowIndx + rowInc;
						columnIndx = columnIndx + columnInc;
						board[rowIndx][columnIndx] = player;
					} // for

				} // else
				i = i + 1;
			} // while
		} // if

		return board;
	}// end of play

	/*
	 * ------------------------------------------------------------------------ *
	 * Task 4 *
	 * ------------------------------------------------------------------------
	 */
	public static int benefit(int[][] board, int player, int row, int column) {
		// Calculates the benefit from a specific move and returns it
		int ans = 0; // the returned value
		int index = 0; // keeps the index in the directionsArray
		int curValue;
		int[][] directionsArray;
		directionsArray = createDirectionsArr(board, player, row, column, false);// find all of the possible movements
		// and retrun it inside the array[[rowincresment][columnIncreasment][how many
		// steps to do in this direction]....]
		while (index < 8 && (directionsArray[index][2] != -2)) {// when it hits -2 it means that there are no more ligal
																// directions
			// 8 is the maximum directions
			// add the number of stpes that can be done in a directions which is the number
			// of disks which will be eaten
			// progress in this direction
			curValue = directionsArray[index][2]; // the number of steps that can be done in this direction
			ans = ans + curValue;
			index = index + 1;

		}
		return ans;
	}// end of benefit

	/*
	 * --------------------------------------------------------------------- * Task
	 * 5 - changed !! (the return statment + define ans) *
	 * ---------------------------------------------------------------------
	 */
	public static int[][] possibleMoves(int[][] board, int player) {
		int[][] preAns = new int[size * size][2]; // keeps the possible moves .the maximum possiblities is the number of
													// the squares on the board
		int[][] ans; // the returned answer
		int preAnsInx = 0; // the index in the preAns array where the next move will be add.

		for (int i = 0; i < (size); i = i + 1)// i indicates the row;
			for (int j = 0; j < (size); j = j + 1) // j indicate the column
				if (isLegal(board, player, i, j)) { // this for check for each place on the board if it's possible
					preAns[preAnsInx][0] = i; // for the player to put a disc there and keeps the doo places
					preAns[preAnsInx][1] = j; // inside the preAns array
					preAnsInx = preAnsInx + 1;
				}

		ans = new int[preAnsInx][2]; // in case of 0 it will be the same as an [0][0]array
		for (int i = 0; i < preAnsInx; i = i + 1) // put each option to from preAns to ans
			ans[i] = preAns[i]; // the goal is to make the ans array exactly in the right size

		return ans;

	}// end of possibleMoves

	/*
	 * ----------------------------------------------------------- * Task 6 *
	 * -----------------------------------------------------------
	 */
	public static boolean hasMoves(int[][] board, int player) {
		// return true if there are possible moves and false otherwise
		int[][] posMov;
		;// the possible moves
		boolean ans;
		posMov = possibleMoves(board, player);// gets the moves
		ans = (posMov.length != 0);// if there are no moves it false otherwise true
		return ans;
	}

	/*
	 * -------------------------------------------------------------------------*
	 * Task 7 *
	 * -------------------------------------------------------------------------
	 */
	public static int findTheWinner(int[][] board) {
		// gets a board , finds the winner of the game and return the player number or 0
		// if its a draw
		int ans = 0;
		int p1Counter = 0, p2Counter = 0;// counts how many disks each one have
		for (int i = 0; i < (size); i = i + 1)// i indicates the row;
			for (int j = 0; j < (size); j = j + 1) { // j indicate the column
				int cuVal = board[i][j];
				if (cuVal == 1)// if its player one disk
					p1Counter = p1Counter + 1;
				else if (cuVal == 2)// if its player two disk
					p2Counter = p2Counter + 1;
			} // for
		if (p2Counter > p1Counter)// player 2 got more disks
			ans = 2;
		else if (p1Counter > p2Counter)
			ans = 1;
		else
			ans = 0;// its a draw

		return ans;
	}// end of findTheWinner

	/*
	 * ------------------------------------------------------------------ * Task 8 *
	 * ------------------------------------------------------------------
	 */
	public static boolean gameOver(int[][] board) {
		// return true if the game is over which means that all the board is full or
		// that onr of the players
		// dont have any more disks
		boolean ans = false;// the returned value
		boolean found1 = false, found2 = false, foundZero = false;
		// found1-if he found a disk of player 1
		// found2-if he found adisk of player2
		// foundzero-if he found any empty spot;
		int curValue, i = 0, j = 0;
		while (!(found1 && found2 && foundZero) & (j < size)) {
			// stops when he found adisk of player one adisk of player two and an empty spot
			// or if he checked all of the board
			curValue = board[i][j];
			if (curValue == 1)
				found1 = true;
			else if (curValue == 2)
				found2 = true;
			else
				foundZero = true;

			if (i == size - 1) {
				i = 0;
				j = j + 1;
			} else
				i = i + 1;

		}
		ans = (!found1 || !found2 || !foundZero);
		// if he didnt found player 1 disks or didnt found player 2 disks or didnt found
		// an empty place
		return ans;
	}// end of gameOver

	/*
	 * -----------------------------------------------------------------------------
	 * ---* Task 9 *
	 * -----------------------------------------------------------------------------
	 * ---
	 */
	public static int[][] randomPlayer(int[][] board, int player) {
		// check all the possible moves and random one of them
		// returns the board after the change
		int[][] posMov; // keeps the possible moves
		int ranOption; // the option that have been chosen from the possible moves.
		int[] movToPlay; // the move that will be played(row,column)
		int posMovLength; // the number of possible moves
		posMov = possibleMoves(board, player); // gets the possible moves
		posMovLength = posMov.length;
		if (posMovLength > 0) { // if there are no possible moves nothing happen and the board return the same
			ranOption = (int) (Math.random() * posMovLength); // random a number between 0 and the number of possible
																// moves-1
			movToPlay = posMov[ranOption]; // the move that will be played
			board = play(board, player, movToPlay[0], movToPlay[1]); // playing the move that were chosen
		}

		return board;
	}// end of randomPlayer

	/*
	 * -----------------------------------------------------------------------------
	 * ---* Task 10 *
	 * -----------------------------------------------------------------------------
	 * ---
	 */

	public static int[][] greedyPlayer(int[][] board, int player) {
		// plays the move that gives the maximum profit and return the board after the
		// change
		int[] bestBenefitArr;

		bestBenefitArr = bestBenefit(board, player);// find the best move
		if (bestBenefitArr.length != 0)// there is a possible move
			board = play(board, player, bestBenefitArr[0], bestBenefitArr[1]);// plays the move

		return board;
	}// end of greedyPlayer

	/*
	 * -----------------------------------------------------------------------------
	 * ---* Task 11 *
	 * 
	 * -----------------------------------------------------------------------------
	 * ---
	 */

	public static int[][] prophetPlayer(int[][] board, int player) {

		// play the move in which he will get the maximum points from the ratio:
		// disks he will get now-disks that the opponent will get in his turn
		// returns the board after the change
		int bestColumn = 0, maxPoints = (-1) * (size * size);// the maximum points that he will from the his move-the
																// opponent reaction
																// i initialize it to this value because that this is
																// the lowest point that he can get from a move
		int posMovLength; // the highest number of disks that the opoonent will get from his move
		int BestOppBenefit; // the number of possible moves,the best benefit that the opponent can get from
							// a reaction to a move
		int bestRow = 0;// the row of the best move to play
		int oppPlayer = player % 2 + 1, curPlayerBenefit; // oppPlayer-the nuber that indicates the opoonent
		// curPlayerBenedfit-the benefit that the player will get from the move cur
		int curPoints; // the points that the player will have after his play and the opponents turn
		int[][] posMov; // keeps the opssible moves
		int[] arrBestOppBenefit; // the move of the opponent that will give him the maximum points
		int[][] newBoard; // a duplicated board to check the opoonent reaction to the player move

		posMov = possibleMoves(board, player); // gets the possible moves for the player
		posMovLength = posMov.length;
		if (posMovLength != 0) {// if there are any possible moves
			int i = 0;
			while (i < posMovLength) { // this loop find the best play for the player by finding the best ratio between
										// the player points and the opoonent reaction
				newBoard = duplicateBoard(board); // and finds the best move by calculating the number of points that he
													// will get and substract the opoonents best move
				newBoard = play(newBoard, player, posMov[i][0], posMov[i][1]);
				arrBestOppBenefit = bestBenefit(newBoard, oppPlayer);// finds the best move for the opoonent
				if (arrBestOppBenefit.length == 0) // the opponent dont have any moves after the player's move;
					BestOppBenefit = 0; // by doing that it will still be the best move
				else
					BestOppBenefit = arrBestOppBenefit[2];// the returned array is [row][column][number of points]
				curPlayerBenefit = benefit(board, player, posMov[i][0], posMov[i][1]);// the benefit that the player
																						// will get from the move
				curPoints = curPlayerBenefit - BestOppBenefit;// the total points from his move and the opoonent move
				if (curPoints > maxPoints) { // if this move is better
					maxPoints = curPoints; // keeping the new values
					bestRow = posMov[i][0];
					bestColumn = posMov[i][1];
				} // if

				i = i + 1;
			} // while
			play(board, player, bestRow, bestColumn);// playing the best move
		} // if

		return board; // the board after the change only if there were any possible moves
	}// end of prophetPlayer

	/*
	 * -----------------------------------------------------------------------------
	 * ---* Task 12 *
	 * -----------------------------------------------------------------------------
	 * ---
	 */
	public static int[][] byLocationPlayer(int[][] board, int player) {
		// plays first if there's amove in the center otherwise in the closest place to
		// the center
		int[][] cornersArray = new int[4][2];
		int corArIx = 0;// the corenr array index;
		int[][] posMov;
		int curRow, curCol;
		int centerRow, centerCol;// the location from which he counts the distance from the center
		int disFrmCenter, minDis = size;// the maximum size for minDis
		int minDisRow = 0, minDisCol = 0;
		posMov = possibleMoves(board, player);
		int posMovLength = posMov.length;
		if (posMovLength > 0) {// if there are no possible moves he dont do anything
			for (int i = 0; i < posMovLength; i = i + 1) {// run across all the possible moves
				curRow = posMov[i][0];
				curCol = posMov[i][1];
				if (curRow < (size / 2))// if the move is on the top part of the board
					centerRow = size / 2 - 1;
				else
					centerRow = size / 2;
				if (curCol < (size / 2))// if the move is on the left side of the board
					centerCol = size / 2 - 1;
				else
					centerCol = size / 2;
				disFrmCenter = Math.abs(curRow - centerRow) + Math.abs(curCol - centerCol) + 1;
				if (disFrmCenter == size - 1) { // if this position is a corner
					cornersArray[corArIx][0] = curRow;
					cornersArray[corArIx][1] = curCol;
					corArIx = corArIx + 1;
				} else {
					if (disFrmCenter < minDis) {// if this option is closer to the center
						minDis = disFrmCenter;
						minDisRow = curRow;
						minDisCol = curCol;
					}
				} // else

			} // for
			if (corArIx > 0) { // if there were any possible moves in the corners;
				int ranNum = (int) (Math.random() * (corArIx - 1));// randoms anumber between 0 and the number of vlaues
																	// in the array-1
				play(board, player, cornersArray[ranNum][0], cornersArray[ranNum][1]);// play the move
			} else // if there is no possible move in the corner he plays the closest to the center
				play(board, player, minDisRow, minDisCol);

		} // if

		return board;
	}// end of byLocationPlayer

	/*
	 * ---------------------------------------------------------- * play an
	 * interactive play between the computer and you *
	 * ----------------------------------------------------------
	 */
//	public static void interactivePlay() {
//
//		int[][] board = createBoard();
//		showBoard(board);
//		int counter = 0;
//		int level = -1;
//		System.out.println("Welcome to the most magnificent Reversi Game !");
//		while (level != 1 & level != 2 & level != 3 & level != 4) {
//			level = Console.readInt(
//					"choose: (1) for easy game, or (2) or (3) or (4) for more chalenging one (we can't say which one is better): ");
//		}
//		System.out.println("You are the red player... ");
//		while (!gameOver(board) && counter < size * size) {
//			if (hasMoves(board, 1)) {
//
//				System.out.println("play: ");
//				int row = Console.readInt("Enter a row number (starting from 0)");
//				int column = Console.readInt("Enter a column number (starting from 0)");
//				while (!(isLegal(board, 1, row, column))) {
//					System.out.println("This is an illegal move :(");
//					row = Console.readInt("Enter a new row number (starting from 0)");
//					column = Console.readInt("Enter a new column number (starting from 0)");
//				}
//				board = play(board, 1, row, column);
//				showBoard(board);
//			} else
//				System.out.println("You can't play, there are no legal move. Player 2 will play again");
//			Console.readString("May I play? (press ENTER for Yes)");
//			if (level == 1)
//				board = randomPlayer(board, 2);
//			else if (level == 2)
//				board = greedyPlayer(board, 2);
//			else if (level == 3)
//				board = byLocationPlayer(board, 2);
//			else
//				board = prophetPlayer(board, 2);
//			showBoard(board);
//
//		} // end of while(!gameOver(board....)
//		int winner = findTheWinner(board);
//		if (winner == 1) {
//			System.out.println();
//			System.out.println("\t *************************");
//			System.out.println("\t * You are the winner !! *");
//			System.out.println("\t *************************");
//		} else
//			System.out.println("You lost :( ");
//	}// end of interactivePlay

	/*
	 * --------------------------------------------------------- * A play between
	 * two players * ----------------------------------------------------------
	 */
//	public static void twoPlayers() {
//
//		int[][] board = createBoard();
//		showBoard(board);
//		int counter = 0;
//		System.out.println("Welcome to the most magnificent Reversi Game !");
//		int row, column;
//		while (!gameOver(board) && counter < size * size) {
//			if (hasMoves(board, 1)) {
//				System.out.println("Red player, please play: ");
//				row = Console.readInt("Enter a row number (starting from 0)");
//				column = Console.readInt("Enter a column number (starting from 0)");
//				while (!(isLegal(board, 1, row, column))) {
//					System.out.println("This is an illegal move :(");
//					row = Console.readInt("Enter a new row number (starting from 0)");
//					column = Console.readInt("Enter a new column number (starting from 0)");
//				}
//				board = play(board, 1, row, column);
//				showBoard(board);
//			} else
//				System.out
//						.println("The red player can't play, there are no legal move. The blue player will play again");
//			if (hasMoves(board, 2)) {
//				System.out.println("Blue player, please play: ");
//				row = Console.readInt("Enter a row number (starting from 0)");
//				column = Console.readInt("Enter a column number (starting from 0)");
//				while (!(isLegal(board, 2, row, column))) {
//					System.out.println("This is an illegal move :(");
//					row = Console.readInt("Enter a new row number (starting from 0)");
//					column = Console.readInt("Enter a new column number (starting from 0)");
//				}
//				board = play(board, 2, row, column);
//				showBoard(board);
//			} else
//				System.out
//						.println("The blue player can't play, there are no legal move. The red player will play again");
//
//		} // end of while(!gameOver(board....)
//		int winner = findTheWinner(board);
//		System.out.println();
//		System.out.println("\t ************************************");
//		if (winner == 1)
//			System.out.println("\t * The Red player is the winner !!  *");
//		else
//			System.out.println("\t * The Blue player is the winner !! *");
//		System.out.println("\t ************************************");
//	}// end of twoPlayers

	/*
	 * --------------------------------------- * print the possible moves *
	 * ---------------------------------------
	 */
	public static void printMoves(int[][] possibleMoves) {
		int numOfMoves = possibleMoves.length;
		for (int i = 0; i < 2; i = i + 1) {
			for (int j = 0; j < numOfMoves; j = j + 1)
				System.out.print(" " + possibleMoves[j][i]);
			System.out.println();
		}
	}// end of printMoves

	/*
	 * --------------------------------------------------------------------------- *
	 * shows the board in a grafic window - you can use it without understand how *
	 * it works. * get the board as its only parameter *
	 * ---------------------------------------------------------------------------
	 */
//	public static void showBoard(int[][] board) {
//		grid.showBoard(board);
//	}

}
