package ca.uqac.pat;

public class COpponent extends CLightCycle {
    public enum Difficulty {EASY, MEDIUM, HARD}
    Difficulty opponentDifficulty;


    public COpponent(IEcranGUI ecran, int posX, int posY, CLightCycle.Direction direction, int vitesse, String color, Difficulty difficulty) {
        super(ecran, posX, posY, direction, vitesse, color);

        opponentDifficulty = difficulty;
    }


}
