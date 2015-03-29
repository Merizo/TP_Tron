package ca.uqac.pat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class COpponent extends CLightCycle {
    public enum Difficulty {RANDOM, COWARD, CHASER}

    Difficulty opponentDifficulty;
    Random rnd = new Random();


    public COpponent(CEcranGUI ecran, int posX, int posY, Direction direction, int vitesse, String color, Difficulty difficulty) {
        super(ecran, posX, posY, direction, vitesse, color);

        opponentDifficulty = difficulty;
    }

    @Override
    public void run() {
        super.run();
        super.display();

        int moveLength = 0;

        while (alive) {
            long startTime = System.currentTimeMillis();
            long fps = 1000 / 10;

            if (opponentDifficulty == Difficulty.RANDOM) {
                if (moveLength == 0) {
                    moveLength = rnd.nextInt(10);
                    setDirection(opponentDifficulty);
                }

                if (getAdjacentTileName(direction).equals("Black.jpg")) {
                    move();
                    display();

                }
                moveLength--;
            } else {
                setDirection(opponentDifficulty);
                move();
                display();
            }


            long endTime = System.currentTimeMillis();

            if (endTime < startTime + fps)
                try {
                    sleep((fps - (endTime - startTime)) / vitesse);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

        System.out.println("Dead");

    }

    private void setDirection(Difficulty opponentDifficulty) {
        JLabel[][] grid = ecran.getGrille();
        switch (opponentDifficulty) {
            case RANDOM:
                direction = getRandomDirection(grid);
                break;

            case COWARD:
                double value = Math.sqrt(Math.pow(2,ecran.getPlayer().getPosY()-posY)
                        + Math.pow(2, ecran.getPlayer().getPosX()-posX));
                ArrayList<Direction> correctDirections = new ArrayList<>();

                if (getAdjacentTileName(Direction.RIGHT).equals("Black.jpg")) {
                    double vector = Math.sqrt(Math.pow(2,ecran.getPlayer().getPosY()-posY)
                            + Math.pow(2, ecran.getPlayer().getPosX()-posX+1));

                    if (vector > value) {
//                        value = vector;
                        correctDirections.add (Direction.RIGHT);
                    }
                }

                if (grid[posY][posX - 1].getIcon().toString().equals("Black.jpg")) {
                    double vector = Math.sqrt(Math.pow(2,ecran.getPlayer().getPosY()-posY)
                            + Math.pow(2, ecran.getPlayer().getPosX()-posX-1));

                    if (vector > value) {
//                        value = vector;
                        correctDirections.add (Direction.LEFT);
                    }
                }

                if (grid[posY + 1][posX].getIcon().toString().equals("Black.jpg")) {
                    double vector = Math.sqrt(Math.pow(2,ecran.getPlayer().getPosY()-posY+1)
                            + Math.pow(2, ecran.getPlayer().getPosX()-posX));

                    if (vector > value) {
//                        value = vector;
                        correctDirections.add (Direction.DOWN);
                    }
                }

                if (grid[posY - 1][posX].getIcon().toString().equals("Black.jpg")) {
                    double vector = Math.sqrt(Math.pow(2,ecran.getPlayer().getPosY()-posY-1)
                            + Math.pow(2, ecran.getPlayer().getPosX()-posX));

                    if (vector > value) {
                        correctDirections.add (Direction.UP);
                    }
                }

                if (correctDirections.size() == 0) {
                    alive = false;
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
            alive = false;
            return Direction.UP;
        } else {
            Random randomDirection = new Random();
            int newDirection = randomDirection.nextInt(correctDirections.size());
            return correctDirections.get(newDirection);
        }
    }

    private String getAdjacentTileName(Direction dir) {
        if (dir == Direction.UP)
            return ecran.getGrille()[posY - 1][posX].getIcon().toString();

        else if (dir == Direction.DOWN)
            return ecran.getGrille()[posY + 1][posX].getIcon().toString();

        else if (dir == Direction.LEFT)
            return ecran.getGrille()[posY][posX - 1].getIcon().toString();

        else // if (dir == Direction.RIGHT)
            return ecran.getGrille()[posY][posX + 1].getIcon().toString();
    }
}
