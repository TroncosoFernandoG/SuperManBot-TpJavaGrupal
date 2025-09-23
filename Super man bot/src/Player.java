import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Player {

    public static final int BASE_WIDTH = 55;
    public static final int BASE_HEIGHT = 55;

    private int x, y;
    private int speed; 
    public final int baseSpeed = 5;
    private Rectangle hitbox;

    private double velocityY = 0;
    private final double gravity = 0.5;
    private final double jumpStrength = -15;
    private boolean isJumping = false;

    private Image normalImage;
    private Image skin2Image;
    private Image skin3Image;
    private Image currentImage;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = baseSpeed;
        loadImages();
        hitbox = new Rectangle(x, y, BASE_WIDTH, BASE_HEIGHT);
        this.currentImage = this.normalImage;
    }

    private void loadImages() {
        ImageIcon ii = new ImageIcon("resource/MANbot1.png");
        this.normalImage = ii.getImage();
        
        ImageIcon ii2 = new ImageIcon("resource/SPEEDbot1.png");
        this.skin2Image = ii2.getImage();
        
        ImageIcon ii3 = new ImageIcon("resource/GUNbot1.png");
        this.skin3Image = ii3.getImage();
    }

    public void update() {
        velocityY += gravity;
        y += velocityY;
        hitbox.x = x;
        hitbox.y = y;
    }

    public void jump() {
        if (!isJumping) {
            velocityY = jumpStrength;
            isJumping = true;
        }
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public int getWidth() { 
        return (int) (BASE_WIDTH * 1.5); 
    }

    public int getHeight() { 
        return (int) (BASE_HEIGHT * 1.5); 
    }
    
    public Image getImage() { return currentImage; }
    public Rectangle getBounds() { return hitbox; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    public double getVelocityY() {
        return velocityY;
    }

    public void setJumping(boolean jumping) { this.isJumping = jumping; }
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }

    public void changeToNormalSkin() {
        this.currentImage = this.normalImage;
    }

    public void changeToSkin2() {
        this.currentImage = this.skin2Image;
    }

    public void changeToSkin3() {
        this.currentImage = this.skin3Image;
    }

    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }
    
    public void resetSpeed() {
        this.speed = baseSpeed;
    }
}