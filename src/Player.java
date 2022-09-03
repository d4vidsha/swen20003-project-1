import bagel.*;
import bagel.util.*;

public class Player extends GameObject {

    private static final int SPEED = 2;
    private static final double WIN_X = 950;
    private static final double WIN_Y = 670;

    private Image imageLeft;
    private Image imageRight;
    private int health = 100;

    public Player(String imageLeft, String imageRight, Point position) {
        super(imageLeft, position);
        this.imageLeft = new Image(imageLeft);
        this.imageRight = new Image(imageRight);
    }

    public Player(String imageLeft, String imageRight, Point position, Boundary boundary) {
        super(imageRight, position, boundary);
        this.imageLeft = new Image(imageLeft);
        this.imageRight = new Image(imageRight);
    }

    public void move(Point position, GameObject[] stationaryObjects) {
        Point oldPos = getPosition();
        if (position.x > oldPos.x) {
            setImage(imageRight);
        } else if (position.x < oldPos.x) {
            setImage(imageLeft);
        }
        setPosition(position);
        if (this.collides(stationaryObjects)) {
            setPosition(oldPos);
        }
    }

    public void update(Input input, GameObject[] stationaryObjects) {
        if (input.isDown(Keys.LEFT)) {
            this.moveLeft(stationaryObjects);
        }

        if (input.isDown(Keys.RIGHT)) {
            this.moveRight(stationaryObjects);
        }

        if (input.isDown(Keys.UP)) {
            this.moveUp(stationaryObjects);
        }

        if (input.isDown(Keys.DOWN)) {
            this.moveDown(stationaryObjects);
        }
    }

    public void moveLeft(GameObject[] stationaryObjects) {
        Point oldPos = getPosition();
        Point newPos = new Point(oldPos.x - SPEED, oldPos.y);
        if (boundary.contains(newPos)) {
            this.move(newPos, stationaryObjects);
        }
    }

    public void moveRight(GameObject[] stationaryObjects) {
        Point oldPos = getPosition();
        Point newPos = new Point(oldPos.x + SPEED, oldPos.y);
        if (boundary.contains(newPos)) {
            this.move(newPos, stationaryObjects);
        }
    }

    public void moveUp(GameObject[] stationaryObjects) {
        Point oldPos = getPosition();
        Point newPos = new Point(oldPos.x, oldPos.y - SPEED);
        if (boundary.contains(newPos)) {
            this.move(newPos, stationaryObjects);
        }
    }

    public void moveDown(GameObject[] stationaryObjects) {
        Point oldPos = getPosition();
        Point newPos = new Point(oldPos.x, oldPos.y + SPEED);
        if (boundary.contains(newPos)) {
            this.move(newPos, stationaryObjects);
        }
    }

    public boolean isAtGate() {
        Point pos = getPosition();
        return pos.x >= WIN_X && pos.y >= WIN_Y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean collides(GameObject[] stationaryObjects) {
        Rectangle playerRectangle = this.getRectangle();
        for (GameObject object : stationaryObjects) {
            Rectangle objectRectangle = object.getRectangle();
            if (playerRectangle.intersects(objectRectangle)) {
                return true;
            }
        }
        return false;
    }
}
