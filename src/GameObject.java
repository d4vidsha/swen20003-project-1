import bagel.*;
import bagel.util.*;

public abstract class GameObject {
    
    private Image image;
    private Point position;
    protected static Boundary boundary;
    private Rectangle rectangle;

    public GameObject(String image, Point position) {
        this.image = new Image(image);
        this.position = position;
        this.rectangle = deriveRectangle(position, this.image);
    }

    public GameObject(String image, Point position, Boundary boundary) {
        this.image = new Image(image);
        this.position = position;
        this.rectangle = deriveRectangle(position, this.image);
        GameObject.boundary = boundary;
    }

    public void draw() {
        if (boundary.contains(position)) {
            image.drawFromTopLeft(position.x, position.y);
        } else {
            throw new RuntimeException("Position is outside of boundary");
        }
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
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

    public static Boundary getBoundary() {
        return boundary;
    }

    public static void setBoundary(Boundary boundary) {
        GameObject.boundary = boundary;
    }

    private Rectangle deriveRectangle(Point position, Image image) {
        return new Rectangle(position, image.getWidth(), image.getHeight());
    }
}
