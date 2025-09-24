import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {

    private final int FRAME_WIDTH = 32;
    private final int FRAME_HEIGHT = 32;
    private final double SCALE_FACTOR = 1.5; 

    public final int BASE_WIDTH = (int) (FRAME_WIDTH * SCALE_FACTOR);
    public final int BASE_HEIGHT = (int) (FRAME_HEIGHT * SCALE_FACTOR);

    private int x, y;
    private int speed;
    public final int baseSpeed = 5;
    private Rectangle hitbox;

    private double velocityY = 0;
    private final double gravity = 0.5;
    private final double jumpStrength = -15;
    
    private boolean isJumping = false;
    private boolean isMoving = false;
    private boolean facingRight = true;
    
   
    private enum Skin { MANBOT, SPEEDBOT }
    private Skin currentSkin = Skin.MANBOT;

   
    private BufferedImage manbotStandFrame;  
    private BufferedImage[] manbotRunFrames; 
    private BufferedImage manbotJumpFrame;

    
    private BufferedImage speedbotStandFrame;
    private BufferedImage[] speedbotRunFrames;
    private BufferedImage speedbotJumpFrame;
    
    private int currentFrameIndex = 0;
    private int animationTick = 0;
    private final int animationSpeed = 5; 

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = baseSpeed;
        loadAnimations(); 
        hitbox = new Rectangle(x, y, BASE_WIDTH, BASE_HEIGHT);
    }
    
    private void loadAnimations() {
        try {
            
            BufferedImage manbotSheet = ImageIO.read(new File("resource/manbot spritesheet (6).png"));
            manbotStandFrame = manbotSheet.getSubimage(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
            manbotRunFrames = new BufferedImage[3];
            for (int i = 0; i < 3; i++) {
                manbotRunFrames[i] = manbotSheet.getSubimage((i + 1) * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
            }
            manbotJumpFrame = manbotSheet.getSubimage(4 * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);

           
            BufferedImage speedbotSheet = ImageIO.read(new File("resource/speedbot spritesheet (1).png"));
            speedbotStandFrame = speedbotSheet.getSubimage(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
            speedbotRunFrames = new BufferedImage[3];
            for (int i = 0; i < 3; i++) {
                speedbotRunFrames[i] = speedbotSheet.getSubimage((i + 1) * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
            }
            speedbotJumpFrame = speedbotSheet.getSubimage(4 * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar los spritesheets del jugador.");
        }
    }

    public void update() {
        velocityY += gravity;
        y += velocityY;
        hitbox.x = x;
        hitbox.y = y;

        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            currentFrameIndex++;
        }
    }

    public void jump() {
        if (!isJumping) {
            velocityY = jumpStrength;
            isJumping = true;
        }
    }

    public void moveLeft() {
        x -= speed;
        isMoving = true;
        facingRight = false;
    }

    public void moveRight() {
        x += speed;
        isMoving = true;
        facingRight = true;
    }
    
    public void setJumping(boolean jumping) { this.isJumping = jumping; }
    public void setMoving(boolean moving) { this.isMoving = moving; }
    public boolean isFacingRight() { return facingRight; }

    public BufferedImage getImage() {
        BufferedImage[] runFrames;
        BufferedImage standFrame;
        BufferedImage jumpFrame;
        

        switch(currentSkin) {
            case SPEEDBOT:
                runFrames = speedbotRunFrames;
                standFrame = speedbotStandFrame;
                jumpFrame = speedbotJumpFrame;
                break;
            case MANBOT:
            default:
                runFrames = manbotRunFrames;
                standFrame = manbotStandFrame;
                jumpFrame = manbotJumpFrame;
                break;
        }

        if (isJumping) {
            return jumpFrame;
        } else if (isMoving) {
            currentFrameIndex %= runFrames.length;
            return runFrames[currentFrameIndex];
        } else {
            return standFrame;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return (int) (FRAME_WIDTH * SCALE_FACTOR); }
    public int getHeight() { return (int) (FRAME_HEIGHT * SCALE_FACTOR); }
    public Rectangle getBounds() { return hitbox; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public double getVelocityY() { return velocityY; }
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }
    
    public void changeToNormalSkin() {
        this.currentSkin = Skin.MANBOT;
    }

    public void changeToSkin2() {
        this.currentSkin = Skin.SPEEDBOT;
    }
    
    public void setSpeed(int newSpeed) { this.speed = newSpeed; }
    public void resetSpeed() { this.speed = baseSpeed; }
}