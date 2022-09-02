import bagel.*;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2022
 * @author David Sha
 */

public class ShadowDimension extends AbstractGame {
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;

    private static final String BACKGROUND_PATH = "res/background0.png";
    private static final String FONT_PATH = "res/frostbite.ttf";
    private final Image BACKGROUND_IMAGE = new Image(BACKGROUND_PATH);
    private final Font FONT75 = new Font(FONT_PATH, 75);
    private final Font FONT40 = new Font(FONT_PATH, 40);

    private static final double GAME_TITLE_X = 260;
    private static final double GAME_TITLE_Y = 250;
    private static final String GAME_TITLE = "SHADOW DIMENSION";
    private static final String GAME_INSTRUCTION = "PRESS SPACE TO START\nUSE ARROW KEYS TO FIND GATE";
    private final Message gameTitle = new Message(FONT75, GAME_TITLE, new Point(GAME_TITLE_X, GAME_TITLE_Y));
    private final Message gameInstruction = new Message(FONT40, GAME_INSTRUCTION, new Point(GAME_TITLE_X + 90, GAME_TITLE_Y + 190));


    public ShadowDimension() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }

    /**
     * Method used to read file and create objects (You can change this
     * method as you wish).
     */
    private void readCSV(){

    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        if (input.wasPressed(Keys.SPACE)) {
            // readCSV();
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        }

        
        gameTitle.draw();
        gameInstruction.draw();

    }
}
