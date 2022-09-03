import bagel.util.*;

public class Boundary {
    private Point topLeft;
    private Point bottomRight;

    public Boundary(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    public boolean contains(Point point) {
        return point.x >= topLeft.x && point.x <= bottomRight.x 
            && point.y >= topLeft.y && point.y <= bottomRight.y;
    }
}
