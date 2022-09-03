import bagel.util.*;

public class Sinkhole extends GameObject {

    private int damagePoints;
    
    public Sinkhole(String image, Point position) {
        super(image, position);
        this.damagePoints = 30;
    }

    public Sinkhole(String image, Point position, int damagePoints) {
        super(image, position);
        this.damagePoints = damagePoints;
    }

    public Sinkhole(String image, Point position, Boundary boundary) {
        super(image, position, boundary);
    }

    public int getDamagePoints() {
        return damagePoints;
    }
}
