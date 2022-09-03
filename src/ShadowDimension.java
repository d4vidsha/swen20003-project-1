import bagel.*;
import bagel.util.*;
import java.io.*;
import java.util.*;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2022
 * @author David Sha
 */

public class ShadowDimension extends AbstractGame {
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;
    private static final int MAX_OBJECTS = 60;

    private static final String LEVEL_PATH = "res/level0.csv";
    private static final String BACKGROUND_PATH = "res/background0.png";
    private static final String FONT_PATH = "res/frostbite.ttf";
    private static final String PLAYER_LEFT_PATH = "res/faeLeft.png";
    private static final String PLAYER_RIGHT_PATH = "res/faeRight.png";
    private static final String WALL_PATH = "res/wall.png";
    private static final String SINKHOLE_PATH = "res/sinkhole.png";

    private final Image BACKGROUND_IMAGE = new Image(BACKGROUND_PATH);
    private final Font FONT75 = new Font(FONT_PATH, 75);
    private final Font FONT40 = new Font(FONT_PATH, 40);

    private static final double GAME_TITLE_X = 260;
    private static final double GAME_TITLE_Y = 250;
    private static final String GAME_TITLE = "SHADOW DIMENSION";
    private static final String GAME_INSTRUCTION = "PRESS SPACE TO START\nUSE ARROW KEYS TO FIND GATE";
    private final Message gameTitle = new Message(FONT75, GAME_TITLE, new Point(GAME_TITLE_X, GAME_TITLE_Y));
    private final Message gameInstruction = new Message(FONT40, GAME_INSTRUCTION, new Point(GAME_TITLE_X + 90, GAME_TITLE_Y + 190));

    private static final int START_SCREEN = 0;
    private static final int GAME_SCREEN = 1;
    private static final int GAME_OVER_SCREEN = 2;
    private static final int GAME_WIN_SCREEN = 3;

    private static final Colour GREEN = new Colour(0, 0.8, 0.2);
    private static final Colour ORANGE = new Colour(0.9, 0.6, 0);
    private static final Colour RED = new Colour(1, 0, 0);

    private int stage = START_SCREEN;

    private static String[] OBJECT_NAMES = {"Player", "Wall", "Sinkhole"};

    private Boundary boundary = readBoundary(LEVEL_PATH);
    private GameObject[] objects = readObjects(LEVEL_PATH, boundary);
    private GameObject[] stationaryObjects = getStationaryGameObjects();

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
     * 
     * @param arr
     * @param targetValue
     * @return
     */
    private static boolean contains(String[] arr, String targetValue) {
        for(String s: arr){
            if(s.equals(targetValue))
                return true;
        }
        return false;
    }

    /**
     * Method used to read file and create objects.
     */
    private GameObject[] readObjects(String csv, Boundary boundary) {
        GameObject.boundary = boundary;
        GameObject[] objects = new GameObject[MAX_OBJECTS];
        
        String delimiter = ",";

        try {
            File file = new File(csv);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] values;
            int i = 0;
            while ((line = br.readLine()) != null) {
                values = line.split(delimiter);
                double x = Double.parseDouble(values[1]);
                double y = Double.parseDouble(values[2]);
                Point pos = new Point(x, y);
                if (contains(OBJECT_NAMES, values[0])) {
                    if (values[0].equals("Player")) {
                        Player player = new Player(PLAYER_LEFT_PATH, PLAYER_RIGHT_PATH, pos);
                        objects[i] = player;
                    } else if (values[0].equals("Wall")) {
                        Wall wall = new Wall(WALL_PATH, pos);
                        objects[i] = wall;
                    } else if (values[0].equals("Sinkhole")) {
                        Sinkhole sinkhole = new Sinkhole(SINKHOLE_PATH, pos);
                        objects[i] = sinkhole;
                    }
                    i++;
                }
            }
            
            // reduce size of array
            objects = Arrays.copyOf(objects, i);

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }

    /**
     * 
     * @param csv
     * @return
     */
    private Boundary readBoundary(String csv) {
        Boundary boundary = null;
        
        String delimiter = ",";

        try {
            File file = new File(csv);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] values;
            Point topLeft = null;
            Point bottomRight = null;
            while ((line = br.readLine()) != null) {
                values = line.split(delimiter);
                double x = Double.parseDouble(values[1]);
                double y = Double.parseDouble(values[2]);
                if (values[0].equals("TopLeft")) {
                    topLeft = new Point(x, y);
                } else if (values[0].equals("BottomRight")) {
                    bottomRight = new Point(x, y);
                }
            }
            
            if (topLeft != null && bottomRight != null) {
                boundary = new Boundary(topLeft, bottomRight);
            } else {
                System.out.println("No boundary specified");
                System.exit(1);
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boundary;
    }

    public GameObject[] getStationaryGameObjects() {
        // get all objects other than the first object
        GameObject[] stationaryObjects = Arrays.copyOfRange(objects, 1, objects.length);
        return stationaryObjects;
    }

    private void startStage() {
        gameTitle.draw();
        gameInstruction.draw();
    }

    private void gameStage(Input input, Player player) {
        drawBackground();
        drawHealthBar(player);
        player.draw();
        drawObjects(stationaryObjects);
        player.update(input, stationaryObjects);

        if (player.isAtGate()) {
            stage = GAME_WIN_SCREEN;
        }
    }

    private void gameOverStage() {
        Message lose = new Message(FONT75, "GAME OVER!");
        lose.draw();
    }

    private void gameWinStage() {
        Message win = new Message(FONT75, "CONGRATULATIONS!");
        win.draw();
    }

    public GameObject[] getStationaryObjects() {
        return stationaryObjects;
    }

    private void drawHealthBar(Player player) {
        int health = player.getHealthPercentage();
        Font font30 = new Font(FONT_PATH, 30);
        Message healthBar = new Message(font30, player.getHealthPercentage() + "%", new Point(20, 25));
        DrawOptions drawOptions = new DrawOptions();

        if (65 <= health && health <= 100) {
            drawOptions.setBlendColour(GREEN);
        } else if (35 <= health && health < 65) {
            drawOptions.setBlendColour(ORANGE);
        } else if (0 <= health && health < 35) {
            drawOptions.setBlendColour(RED);
        } else {
            System.out.println("Health percentage out of range");
            System.exit(1);
        }
        healthBar.draw(drawOptions);
    }

    private void drawObjects(GameObject[] objects) {
        for (GameObject object : objects) {
            object.draw();
        }
    }

    private void drawBackground() {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
    }

    public GameObject[] getWalls(GameObject[] stationaryObjects) {
        ArrayList<GameObject> walls = new ArrayList<>();
        for (GameObject gameObject : stationaryObjects) {
            if (gameObject instanceof Wall) {
                walls.add(gameObject);
            }
        }
        return walls.toArray(new GameObject[walls.size()]);
    }

    public GameObject[] getSinkholes(GameObject[] stationaryObjects) {
        ArrayList<GameObject> sinkholes = new ArrayList<>();
        for (GameObject gameObject : stationaryObjects) {
            if (gameObject instanceof Sinkhole) {
                sinkholes.add(gameObject);
            }
        }
        return sinkholes.toArray(new GameObject[sinkholes.size()]);
    }

    public GameObject[] removeGameObject(GameObject[] objects, GameObject gameObject) {
        ArrayList<GameObject> objectsList = new ArrayList<>(Arrays.asList(objects));
        objectsList.remove(gameObject);
        return objectsList.toArray(new GameObject[objectsList.size()]);
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        // assume player is the first object in the array
        Player player = (Player) objects[0];

        // exit game when escape key is pressed
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // start game when space key is pressed
        if (input.wasPressed(Keys.SPACE) && stage == START_SCREEN) {
            stage = GAME_SCREEN;
        }
        
        // check if player is dead
        if (player.getHealthPercentage() <= 0) {
            stage = GAME_OVER_SCREEN;
        }

        // check if player hit a wall or sinkhole
        GameObject[] sinkholes = getSinkholes(stationaryObjects);
        GameObject[] walls = getWalls(stationaryObjects);
        if (player.collides(sinkholes)) {
            // get specific sinkhole collided with
            Sinkhole sinkhole = (Sinkhole) player.getCollidedObject(sinkholes);
            int damagePoints = sinkhole.getDamagePoints();
            player.inflictDamage(damagePoints);
            // remove sinkhole from game
            stationaryObjects = removeGameObject(stationaryObjects, sinkhole);
        } else if (player.collides(walls)) {
            // bounce player off wall
            Wall wall = (Wall) player.getCollidedObject(walls);
            player = wall.bounce(player);
        }

        // the stages of the game
        if (stage == START_SCREEN) {
            startStage();
        } else if (stage == GAME_SCREEN) {
            gameStage(input, player);
        } else if (stage == GAME_OVER_SCREEN) {
            gameOverStage();
        } else if (stage == GAME_WIN_SCREEN) {
            gameWinStage();
        }
    }
}
