import bagel.*;
import bagel.util.*;

public abstract class GameObject {
    
    private Image image;
    protected static Boundary boundary;
    protected Rectangle rectangle;

    public GameObject(String image, Point position) {
        this.image = new Image(image);
        this.rectangle = deriveRectangle(position, this.image);
    }

    public GameObject(String image, Point position, Boundary boundary) {
        this.image = new Image(image);
        this.rectangle = deriveRectangle(position, this.image);
        GameObject.boundary = boundary;
    }

    public void draw() {
        Point position = getPosition();
        if (boundary.contains(position)) {
            image.drawFromTopLeft(position.x, position.y);
        } else {
            throw new RuntimeException("Position is outside of boundary");
        }
    }

    public Point getPosition() {
        return rectangle.topLeft();
    }

    public void setPosition(Point position) {
        rectangle.moveTo(position);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    private Rectangle deriveRectangle(Point position, Image image) {
        return new Rectangle(position, image.getWidth(), image.getHeight());
    }

    public boolean collides(GameObject[] gameObjects) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getRectangle().intersects(this.getRectangle())) {
                return true;
            }
        }
        return false;
    }

    public GameObject getCollidedObject(GameObject[] gameObjects) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getRectangle().intersects(this.getRectangle())) {
                return gameObject;
            }
        }
        return null;
    }
}
