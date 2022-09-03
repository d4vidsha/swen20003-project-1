import bagel.*;
import bagel.util.*;

public class Player extends GameObject {

    private static final int SPEED = 10;

    private Image imageLeft;
    private Image imageRight;

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

    public void move(Point position) {
        Point oldPos = getPosition();
        if (position.x > oldPos.x) {
            setImage(imageRight);
        } else if (position.x < oldPos.x) {
            setImage(imageLeft);
        }
        setPosition(position);
    }

    public void update(Input input) {
        if (input.isDown(Keys.LEFT)) {
            this.moveLeft();
        }

        if (input.isDown(Keys.RIGHT)) {
            this.moveRight();
        }

        if (input.isDown(Keys.UP)) {
            this.moveUp();
        }

        if (input.isDown(Keys.DOWN)) {
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

}
