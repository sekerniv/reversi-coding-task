package reversi;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ReversiTournament {

    private static final int NUM_OF_GAMES = 1000;
    private static boolean VERBOSE = false;

    public static void main(String[] args)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
        TournamentContestant[] contestants = loadContestants();
        if (contestants.length < 2) {
            System.out.println("Not enough contestants to run a tournament!");
            return;
        }

        // Shuffling so that we get a random order of competitions
        Collections.shuffle(Arrays.asList(contestants));

        competeRoundRobin(contestants);
        printLeaderboard(contestants);

    }

    private static void competeRoundRobin(TournamentContestant[] contestants) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
        for (int i = 0; i < contestants.length; i++) {
            for (int j = i + 1; j < contestants.length; j++) {
                int result = matchBots(contestants[i], contestants[j]);
                if (result > 0) {
                    contestants[i].score += 1;
                } else if (result < 0){
                    contestants[j].score += 1;
                } else { // no points for tie

                }
                printLeaderboard(contestants);    
                System.out.println("Press enter to continue");
                System.in.read();
            }
        }
    }

    private static void printLeaderboard(TournamentContestant[] contestants) {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("============== Leaderboard: ==================");
        System.out.println("==============================================");
        for (int i = 0; i < contestants.length; i++) {
            System.out.println(contestants[i].botClass.getSimpleName() + " " + contestants[i].score + " points");
        }
        System.out.println("==============================================");
    }


    private static int matchBots(
            TournamentContestant contestant1,
            TournamentContestant contestant2) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
        System.out.println("Starting match between " + contestant1.botClass.getSimpleName() + " and "
                + contestant2.botClass.getSimpleName());
                System.out.println("Are you ready to rubmble? Press enter to start");
        System.in.read();

        SingleRoundScore contestant1MatchScore1 = new SingleRoundScore(contestant1, 0);        
        SingleRoundScore contestant1MatchScore2 = new SingleRoundScore(contestant2, 0);        

        SingleRoundScore[] contestantsWithScores = new SingleRoundScore[2];
        contestantsWithScores[0] = contestant1MatchScore1;
        contestantsWithScores[1] = contestant1MatchScore2;

        int ties = 0;

        for (int i = 0; i < NUM_OF_GAMES; i++) {

            if (VERBOSE) {
                System.out.println("Game " + (i + 1) + " out of " + NUM_OF_GAMES);
            }
            
            ReversiGame game = new ReversiGame();

            // "flipping coin" to decide who plays first
            Collections.shuffle(Arrays.asList(contestantsWithScores));

            // Creating a new instance by calling the constructor with the game
            // The players order matches the order of the contestantsWithScores array in this single game 
            ReversiBot[] players = new ReversiBot[2];
            players[0] = contestantsWithScores[0].constructor.newInstance(game);
            players[1] = contestantsWithScores[1].constructor.newInstance(game);

            while (!game.isGameOver()) {
                if (VERBOSE)
                    game.printBoard();
                MoveScore nextMove = players[game.getCurPlayer() - 1].getNextMove();
                if (nextMove == null) {
                    game.skipTurn();
                } else {
                    game.placeDisk(nextMove.getRow(), nextMove.getColumn());
                }
            }

            if (game.getWinner() == ReversiGame.PLAYER_ONE) {
                contestantsWithScores[0].singleRoundScore ++;
            } else if (game.getWinner() == ReversiGame.PLAYER_TWO) {
                contestantsWithScores[1].singleRoundScore ++;
            } else {
                // no point for tie
            }

        }

        System.out.println("Match is over!");
        System.out.println(contestantsWithScores[0].singleRoundScore > contestantsWithScores[1].singleRoundScore? contestant1.botClass.getSimpleName() + " wins!" : contestant2.botClass.getSimpleName() + " wins!");
        System.out.println("Results: " + contestantsWithScores[0].contestant.botClass.getSimpleName() + " "
                + contestantsWithScores[0].singleRoundScore + " points, "
                + contestantsWithScores[1].contestant.botClass.getSimpleName() + " "
                + contestantsWithScores[1].singleRoundScore + " points, " + ties + " ties");

        return contestant1MatchScore1.singleRoundScore - contestant1MatchScore2.singleRoundScore;
    }

    private static class TournamentContestant {
        TournamentContestant(Class<? extends ReversiBot> botClass) {
            this.botClass = botClass;
            score = 0;
        }

        final Class<? extends ReversiBot> botClass;
        int score; // 3 points for win, 1 point for tie, 0 points for loss
    }

    private static TournamentContestant[] loadContestants()
            throws UnsupportedEncodingException, ClassNotFoundException {

        Package currentPackage = ReversiTournament.class.getPackage();

        String packageName = currentPackage.getName();

        String path = ReversiTournament.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = URLDecoder.decode(path, StandardCharsets.UTF_8.name());
        final File directory = new File(path, packageName.replace('.', '/'));

        File[] botFiles = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("Bot.class");
            }
        });

        List<TournamentContestant> contestants = new ArrayList<TournamentContestant>();
        for (int i = 0; i < botFiles.length; i++) {
            @SuppressWarnings("unchecked")
            Class<? extends ReversiBot> botClass = (Class<? extends ReversiBot>) Class
                    .forName(packageName + "." + botFiles[i].getName().replace(".class", ""));
            if (botClass.isInterface()) {
                continue;
            }
            contestants.add(new TournamentContestant(botClass));
        }
        return contestants.toArray(new TournamentContestant[contestants.size()]);
    }

    private static class SingleRoundScore {
        public SingleRoundScore(TournamentContestant contestant, int singleRoundScore)
                throws NoSuchMethodException, SecurityException {
            this.contestant = contestant;
            this.singleRoundScore = singleRoundScore;
            // tiny optimization. Storing the constructor instead of using reflection in
            // each iteration
            this.constructor = contestant.botClass.getConstructor(ReversiGame.class);
        }

        TournamentContestant contestant;
        private final Constructor<? extends ReversiBot> constructor;
        int singleRoundScore;

    }

}
