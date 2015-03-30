package ca.uqac.pat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class CBot extends CLightCycle {
    public enum Difficulty {RANDOM, COWARD, CHASER}

    Difficulty botDifficulty;
    Random rnd = new Random();


    public CBot(CEcranGUI ecran, int posX, int posY, Direction direction, int vitesse, String color, Difficulty difficulty) {
        super(ecran, posX, posY, direction, vitesse, color);

        botDifficulty = difficulty;
    }

    @Override
    public void run() {
        super.run();
        super.display();

        int moveLength = 0;

        while (isRunning) {
            long startTime = System.currentTimeMillis();
            long fps = 1000 / 10;

            if (botDifficulty == Difficulty.RANDOM) {
                if (moveLength == 0) {
                    moveLength = rnd.nextInt(10);
                    setDirection(botDifficulty);
                }

                if (getAdjacentTileName(direction).equals("Black.jpg")) {
                    move();
                    display();

                }
                moveLength--;
            } else {
                setDirection(botDifficulty);
                move();
                display();
            }


            long endTime = System.currentTimeMillis();

            if (endTime < startTime + fps)
                try {
                    sleep((fps - (endTime - startTime)) / Vitesse);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

        System.out.println("Dead");

    }

    private void setDirection(Difficulty opponentDifficulty) {
        JLabel[][] grid = Ecran.getGrille();
        switch (opponentDifficulty) {
            case RANDOM:
                direction = getRandomDirection(grid);
                break;

            case COWARD:
                double value = Math.sqrt(Math.pow(2,Ecran.getPlayer().getPosY()-PosY)
                        + Math.pow(2, Ecran.getPlayer().getPosX()-PosX));
                ArrayList<Direction> correctDirections = new ArrayList<>();

                if (grid[PosY][PosX + 1].getIcon().toString().equals("Black.jpg")) {
                    double vector = Math.sqrt(Math.pow(2,Ecran.getPlayer().getPosY()-PosY)
                            + Math.pow(2, Ecran.getPlayer().getPosX()-PosX+1));

                    if (vector > value) {
//                        value = vector;
                        correctDirections.add (Direction.RIGHT);
                    }
                }

                if (grid[PosY][PosX - 1].getIcon().toString().equals("Black.jpg")) {
                    double vector = Math.sqrt(Math.pow(2,Ecran.getPlayer().getPosY()-PosY)
                            + Math.pow(2, Ecran.getPlayer().getPosX()-PosX-1));

                    if (vector > value) {
//                        value = vector;
                        correctDirections.add (Direction.LEFT);
                    }
                }

                if (grid[PosY + 1][PosX].getIcon().toString().equals("Black.jpg")) {
                    double vector = Math.sqrt(Math.pow(2,Ecran.getPlayer().getPosY()-PosY+1)
                            + Math.pow(2, Ecran.getPlayer().getPosX()-PosX));

                    if (vector > value) {
//                        value = vector;
                        correctDirections.add (Direction.DOWN);
                    }
                }

                if (grid[PosY - 1][PosX].getIcon().toString().equals("Black.jpg")) {
                    double vector = Math.sqrt(Math.pow(2,Ecran.getPlayer().getPosY()-PosY-1)
                            + Math.pow(2, Ecran.getPlayer().getPosX()-PosX));

                    if (vector > value) {
                        correctDirections.add (Direction.UP);
                    }
                }

                if (correctDirections.size() == 0) {
                    isRunning = false;
                    getRandomDirection(grid);
                } else {
                    Random randomDirection = new Random();
                    int newDirection = randomDirection.nextInt(correctDirections.size());
                    direction = correctDirections.get(newDirection);
                }

                break;

            case CHASER:
                break;
        }
    }

    private Direction getRandomDirection(JLabel[][] grid) {
        ArrayList<Direction> correctDirections = new ArrayList<>();
        // Get coprrect surrounding positions
        if (getAdjacentTileName(Direction.RIGHT).equals("Black.jpg"))
            correctDirections.add(Direction.RIGHT);

        if (getAdjacentTileName(Direction.LEFT).equals("Black.jpg"))
            correctDirections.add(Direction.LEFT);

        if (getAdjacentTileName(Direction.DOWN).equals("Black.jpg"))
            correctDirections.add(Direction.DOWN);

        if (getAdjacentTileName(Direction.UP).equals("Black.jpg"))
            correctDirections.add(Direction.UP);

        if (correctDirections.size() == 0) {
            isRunning = false;
            return Direction.UP;
        } else {
            Random randomDirection = new Random();
            int newDirection = randomDirection.nextInt(correctDirections.size());
            return correctDirections.get(newDirection);
        }
    }

    private String getAdjacentTileName(Direction dir) {
        if (dir == Direction.UP)
            return Ecran.getGrille()[PosY - 1][PosX].getIcon().toString();

        else if (dir == Direction.DOWN)
            return Ecran.getGrille()[PosY + 1][PosX].getIcon().toString();

        else if (dir == Direction.LEFT)
            return Ecran.getGrille()[PosY][PosX - 1].getIcon().toString();

        else // if (dir == Direction.RIGHT)
            return Ecran.getGrille()[PosY][PosX + 1].getIcon().toString();
    }
}
