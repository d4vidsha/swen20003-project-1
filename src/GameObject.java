import bagel.*;
import bagel.util.*;

public abstract class GameObject {
    
    private Image image;
    private Point position;
    protected static Boundary boundary;

    public GameObject(String image, Point position) {
        this.image = new Image(image);
        this.position = position;
    }

    public GameObject(String image, Point position, Boundary boundary) {
        this.image = new Image(image);
        this.position = position;
        GameObject.boundary = boundary;
    }

    public void draw() {
        if (boundary.contains(position)) {
            image.draw(position.x, position.y);
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
}
