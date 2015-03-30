package ca.uqac.pat;

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
        int    diffX             = PosX - Ecran.getPlayer().getPosX();
        int    diffY             = PosY - Ecran.getPlayer().getPosY();
        double currentVectorNorm = getVectorLength(diffX, diffY);

        switch (opponentDifficulty) {
            case RANDOM:    // Deplacement totalement aleatoire
                direction = getRandomDirection();
                break;

            case COWARD:    // S'eloigne du joueur
                if (currentVectorNorm < 15) {

                    if (!isWall(Direction.UP)) {
                        diffX = PosX - Ecran.getPlayer().getPosX();
                        diffY = PosY - 1 - Ecran.getPlayer().getPosY();
                        double vectorLength = getVectorLength(diffX, diffY);

                        if (vectorLength > currentVectorNorm) {
                            currentVectorNorm = vectorLength;
                            direction = Direction.UP;
                        }
                    }

                    if (!isWall(Direction.DOWN)) {
                        diffX = PosX - Ecran.getPlayer().getPosX();
                        diffY = PosY + 1 - Ecran.getPlayer().getPosY();
                        double vectorLength = getVectorLength(diffX, diffY);

                        if (vectorLength > currentVectorNorm) {
                            currentVectorNorm = vectorLength;
                            direction = Direction.DOWN;
                        }
                    }

                    if (!isWall(Direction.RIGHT)) {
                        diffX = PosX + 1 - Ecran.getPlayer().getPosX();
                        diffY = PosY - Ecran.getPlayer().getPosY();
                        double vectorLength = getVectorLength(diffX, diffY);

                        if (vectorLength > currentVectorNorm) {
                            direction = Direction.RIGHT;
                        }
                    }

                    if (!isWall(Direction.LEFT)) {
                        diffX = PosX - 1 - Ecran.getPlayer().getPosX();
                        diffY = PosY - Ecran.getPlayer().getPosY();
                        double vectorLength = getVectorLength(diffX, diffY);

                        if (vectorLength > currentVectorNorm) {
                            direction = Direction.LEFT;
                        }
                    }
                } else {
                    direction = getRandomDirection();
                    moveLength = rnd.nextInt(16) + 10;
                }
                break;

            case CHASER:    // Se rapproche du joueur
                if (currentVectorNorm < 30) {
                    if (!isWall(Direction.UP)) {
                        diffX = PosX - Ecran.getPlayer().getPosX();
                        diffY = PosY - 1 - Ecran.getPlayer().getPosY();
                        double vectorLength = getVectorLength(diffX, diffY);

                        if (vectorLength < currentVectorNorm) {
                            currentVectorNorm = vectorLength;
                            direction = Direction.UP;
                        }
                    }

                    if (!isWall(Direction.DOWN)) {
                        diffX = PosX - Ecran.getPlayer().getPosX();
                        diffY = PosY + 1 - Ecran.getPlayer().getPosY();
                        double vectorLength = getVectorLength(diffX, diffY);

                        if (vectorLength < currentVectorNorm) {
                            currentVectorNorm = vectorLength;
                            direction = Direction.DOWN;
                        }
                    }

                    if (!isWall(Direction.RIGHT)) {
                        diffX = PosX + 1 - Ecran.getPlayer().getPosX();
                        diffY = PosY - Ecran.getPlayer().getPosY();
                        double vectorLength = getVectorLength(diffX, diffY);

                        if (vectorLength < currentVectorNorm) {
                            direction = Direction.RIGHT;
                        }
                    }

                    if (!isWall(Direction.LEFT)) {
                        diffX = PosX - 1 - Ecran.getPlayer().getPosX();
                        diffY = PosY - Ecran.getPlayer().getPosY();
                        double vectorLength = getVectorLength(diffX, diffY);

                        if (vectorLength < currentVectorNorm) {
                            direction = Direction.LEFT;
                        }
                    }
                } else {
                    direction = getRandomDirection();
                    moveLength = rnd.nextInt(16) + 10;
                }
                break;
        }
    }

    double getVectorLength(int diffX, int diffY) {
        return Math.sqrt((double) (diffX * diffX + diffY * diffY));
    }
}
