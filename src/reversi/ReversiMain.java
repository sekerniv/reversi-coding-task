package reversi;

import java.util.Scanner;

public class ReversiMain {
	public static void main(String[] args) {
		twoBots();
	}

	public static void twoPlayers() {
		Scanner scanner = new Scanner(System.in);
		ReversiGame rg = new ReversiGame();
		while(!rg.isGameOver()) {
			rg.printBoard();
			System.out.println("Player " + rg.getCurPlayer() + " please enter your row and column");
			int row = scanner.nextInt();
			int column = scanner.nextInt();
			while (!rg.placeDisk(row, column)) {
				System.out.println("Illegal move! Player " + rg.getCurPlayer() + " please enter your row and column");
				row = scanner.nextInt();
				column = scanner.nextInt();
			}
		}

		if (rg.getWinner() == ReversiGame.PLAYER_ONE) {
			System.out.println("Player 1 wins!");
		} else if (rg.getWinner() == ReversiGame.PLAYER_TWO) {
			System.out.println("Player 2 wins!");
		} else {
			System.out.println("It's a tie!");
		}

		scanner.close();
	}

	public static void twoBots() {
		int player1Wins = 0;
		int player2Wins = 0;
		for (int i = 0; i < 1000; i++) {

			ReversiGame rg = new ReversiGame();

			TwoStepsWithLocationBot bot = new TwoStepsWithLocationBot(rg);

			while (!rg.isGameOver()) {
				rg.printBoard();
				if (rg.getPossibleMoves().length == 0) {
					System.out.println("No possible moves for player " + rg.getCurPlayer() + " skipping turn");
					rg.skipTurn();
				}

				if (rg.getCurPlayer() == ReversiGame.PLAYER_ONE) {
					// System.out.println("Player " + rg.getCurPlayer() + " please enter your row
					// and column");
					// int row = scanner.nextInt();
					// int column = scanner.nextInt();
					// while (!rg.placeDisk(row, column)) {
					// System.out.println("Illegal move! Player " + rg.getCurPlayer() + " please
					// enter your row and column");
					// row = scanner.nextInt();
					// column = scanner.nextInt();
					// }

					System.out.println("getTwoStepsPreferLocationMove bot is playing...");
					MoveScore mb = bot.getTwoStepsPreferLocationMove();
					if (mb != null) {
						rg.placeDisk(mb.getRow(), mb.getColumn());
					} else {
						System.out.println("No possible moves for player " + rg.getCurPlayer());
					}
				} else {
					System.out.println("getTwoStepsGreedyMove bot is playing...");
					MoveScore mb = bot.getTwoStepsGreedyMove();
					if (mb != null) {
						rg.placeDisk(mb.getRow(), mb.getColumn());
					} else {
						System.out.println("No possible moves for player " + rg.getCurPlayer());
					}
				}
			}

			rg.printBoard();
			System.out.println("Game #" + i + " is over!");
			if (rg.getWinner() == ReversiGame.PLAYER_ONE) {
				player1Wins++;
				System.out.println("Player 1 wins!");
			} else if (rg.getWinner() == ReversiGame.PLAYER_TWO) {
				player2Wins++;
				System.out.println("Player 2 wins!");
			} else {
				System.out.println("It's a tie!");
			}
		}
		System.out.println("Player 1 won " + player1Wins + " times and player2 won " + player2Wins + " times");

	}

	

}
