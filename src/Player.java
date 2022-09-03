import bagel.*;
import bagel.util.*;

public class Player extends GameObject {

    private static final int MAX_HEALTH = 100;

    private static final int SPEED = 2;
    private static final double WIN_X = 950;
    private static final double WIN_Y = 670;

    private Image imageLeft;
    private Image imageRight;
    private int health = MAX_HEALTH;
    private Point prevPos;

    public Player(String imageLeft, String imageRight, Point position) {
        super(imageLeft, position);
        this.imageLeft = new Image(imageLeft);
        this.imageRight = new Image(imageRight);
        this.prevPos = position;
    }

    public Player(String imageLeft, String imageRight, Point position, Boundary boundary) {
        super(imageRight, position, boundary);
        this.imageLeft = new Image(imageLeft);
        this.imageRight = new Image(imageRight);
        this.prevPos = position;
    }

    public void move(Point position) {
        prevPos = getPosition();

        if (position.x > prevPos.x) {
            setImage(imageRight);
        } else if (position.x < prevPos.x) {
            setImage(imageLeft);
        }

        setPosition(position);
    }

    public void update(Input input, GameObject[] stationaryObjects) {
        if (input.isDown(Keys.LEFT)) {
            this.moveLeft();
        } else if (input.isDown(Keys.RIGHT)) {
            this.moveRight();
        } else if (input.isDown(Keys.UP)) {
            this.moveUp();
        } else if (input.isDown(Keys.DOWN)) {
            this.moveDown();
        }
    }

    public void moveLeft() {
        Point oldPos = getPosition();
        Point newPos = new Point(oldPos.x - SPEED, oldPos.y);
        if (boundary.contains(newPos)) {
            this.move(newPos);
        }
    }

    public void moveRight() {
        Point oldPos = getPosition();
        Point newPos = new Point(oldPos.x + SPEED, oldPos.y);
        if (boundary.contains(newPos)) {
            this.move(newPos);
        }
    }

    public void moveUp() {
        Point oldPos = getPosition();
        Point newPos = new Point(oldPos.x, oldPos.y - SPEED);
        if (boundary.contains(newPos)) {
            this.move(newPos);
        }
    }

    public void moveDown() {
        Point oldPos = getPosition();
        Point newPos = new Point(oldPos.x, oldPos.y + SPEED);
        if (boundary.contains(newPos)) {
            this.move(newPos);
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
        if (health > MAX_HEALTH) {
            this.health = MAX_HEALTH;
        } else if (health < 0) {
            this.health = 0;
        } else {
            this.health = health;
        }
    }

    public int getHealthPercentage() {
        return (int) Math.round((double) health / MAX_HEALTH * 100);
    }

    public void inflictDamage(int damage) {
        this.setHealth(this.getHealth() - damage);
    }

    public Point getPrevPos() {
        return prevPos;
    }
}
