package ca.uqac.pat;

public class COpponent extends CLightCycle {
    public enum Difficulty {EASY, MEDIUM, HARD}
    Difficulty opponentDifficulty;


    public COpponent(IEcranGUI ecran, int posX, int posY, Direction direction, int vitesse, String color, Difficulty difficulty) {
        super(ecran, posX, posY, direction, vitesse, color);

        opponentDifficulty = difficulty;
    }

    @Override
    public void run() {
        super.display();

        while (alive){
            long startTime = System.currentTimeMillis();
            long fps = 1000/10;

            setDirection(opponentDifficulty);
            super.move();
            super.display();

            long endTime = System.currentTimeMillis();

            if (endTime < startTime + fps)
                try{
                    sleep ((fps - (endTime - startTime)) / vitesse);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
        }

    }

    private void setDirection(Difficulty opponentDifficulty) {
        switch (opponentDifficulty) {
            case EASY:
                break;

            case MEDIUM:
                break;

            case HARD:
                break;
        }
    }
}
