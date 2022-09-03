import bagel.util.*;

public class Wall extends GameObject {
    
    public Wall(String image, Point position) {
        super(image, position);
    }

    public Wall(String image, Point position, Boundary boundary) {
        super(image, position, boundary);
    }

    public Player bounce(Player player) {
        Point prevPos = player.getPrevPos();
        player.setPosition(prevPos);
        return player;
    }
}
