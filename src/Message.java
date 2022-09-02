import bagel.*;

public class Message {    
    private Point position;
    private String text;
    private Font font;

    public Message(Font font, String text) {
        this.position = new Point(Window.getWidth()/2.0 - font.getWidth(text)/2.0, Window.getHeight()/2.0);
        this.text = text;
        this.font = font;
    }

    /**
     * Constructor for Message class
     * @param font Font of the message
     * @param text Text of the message
     * @param position Bottom left position of the message
     */
    public Message(Font font, String text, Point position) {
        this.position = position;
        this.text = text;
        this.font = font;
    }
    
    public void draw() {
        font.drawString(text, position.getX(), position.getY());
    }
}
