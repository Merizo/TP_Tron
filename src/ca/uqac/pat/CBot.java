package ca.uqac.pat;

import java.util.ArrayList;
import java.util.Random;

public class CBot extends CLightCycle {
    public enum Difficulty {RANDOM, COWARD, CHASER}

    private final Difficulty botDifficulty;
    private final Random     rnd;
    private       int        moveLength;

    public CBot(CEcranGUI ecran, int posX, int posY, Direction direction, int vitesse, String color, Difficulty difficulty) {
        super(ecran, posX, posY, direction, vitesse, color);

        botDifficulty = difficulty;
        rnd = new Random();
        moveLength = 0;
    }

    @Override
    public void run() {
        // super.run();
        // super.display();


        while (isRunning) {
            long startTime = System.currentTimeMillis();
            long fps = 1000 / 10;

            if (moveLength > 0) {
                if (isWall(direction)) {
                    direction = getRandomDirection();
                }

                move();
                display();
                moveLength--;

            } else if (moveLength == 0) {
                if (botDifficulty == Difficulty.RANDOM) {
                    moveLength = rnd.nextInt(16) + 10;

                    direction = getRandomDirection();
                    move();
                    display();
                    moveLength--;
                } else moveLength = -1;
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
        switch (opponentDifficulty) {
            case RANDOM:    // Deplacement totalement aleatoire
                direction = getRandomDirection();
                break;

            case COWARD:    // S'eloigne du joueur
                double base = 10.0E-324D;
                int diffX = PosX - Ecran.getPlayer().getPosX();
                int diffY = PosY - Ecran.getPlayer().getPosY();
                double currentVectorNorm = Math.sqrt((double) (diffX * diffX) + diffY * diffY);

                if (currentVectorNorm < 20) {

                    if (!isWall(Direction.UP)) {
                        diffX = PosX - Ecran.getPlayer().getPosX();
                        diffY = PosY - 1 - Ecran.getPlayer().getPosY();
                        double vectorLength = Math.sqrt((double) (diffX * diffX + diffY * diffY));

                        if (vectorLength > base) {
                            base = vectorLength;
                            direction = Direction.UP;
                        }
                    }

                    if (!isWall(Direction.DOWN)) {
                        diffX = PosX - Ecran.getPlayer().getPosX();
                        diffY = PosY + 1 - Ecran.getPlayer().getPosY();
                        double vectorLength = Math.sqrt((double) (diffX * diffX + diffY * diffY));

                        if (vectorLength > base) {
                            base = vectorLength;
                            direction = Direction.DOWN;
                        }
                    }

                    if (!isWall(Direction.RIGHT)) {
                        diffX = PosX + 1 - Ecran.getPlayer().getPosX();
                        diffY = PosY - Ecran.getPlayer().getPosY();
                        double vectorLength = Math.sqrt((double) (diffX * diffX + diffY * diffY));

                        if (vectorLength > base) {
                            base = vectorLength;
                            direction = Direction.RIGHT;
                        }
                    }

                    if (!isWall(Direction.LEFT)) {
                        diffX = PosX - 1 - Ecran.getPlayer().getPosX();
                        diffY = PosY - Ecran.getPlayer().getPosY();
                        double vectorLength = Math.sqrt((double) (diffX * diffX + diffY * diffY));

                        if (vectorLength > base) {
                            direction = Direction.LEFT;
                        }
                    }
                } else {
                    direction = getRandomDirection();
                    moveLength = rnd.nextInt(16) + 10;
                }
                break;

            case CHASER:
                break;
        }
    }

    private Direction getRandomDirection() {
        ArrayList<Direction> correctDirections = new ArrayList<>();
        // Get coprrect surrounding positions
        if (!isWall(Direction.RIGHT) && PosX < Ecran.getNbrColonnes() - 1)
            correctDirections.add(Direction.RIGHT);

        if (!isWall(Direction.LEFT) && PosX > 0)
            correctDirections.add(Direction.LEFT);

        if (!isWall(Direction.DOWN) && PosY < Ecran.getNbrLignes() - 1)
            correctDirections.add(Direction.DOWN);

        if (!isWall(Direction.UP) && PosY > 0)
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

    private boolean isWall(Direction dir) {
        return !getAdjacentTileName(dir).equals("Black.jpg");
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
