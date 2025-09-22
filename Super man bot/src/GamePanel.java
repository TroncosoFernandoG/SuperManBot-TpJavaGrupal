import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList; 
import java.util.List; 

public class GamePanel extends JPanel implements KeyListener, Runnable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private int camX;
    private int camY;

    private enum GameState {
        MENU,
        PLAYING,
        WIN_SCREEN
    }
    private GameState gameState = GameState.MENU;

    private Player player;
    private boolean leftPressed, rightPressed;

    private final int WORLD_WIDTH = 2000;

    private Image backgroundImage;
    private Image blockImage2; 
    private Image blockImage3; 
    private Image enemyImage;

    private int currentLevelIndex = 1;
    private BaseLevel currentLevel;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setBackground(new Color(200, 128, 128));

        player = new Player(WIDTH / 4 - Player.BASE_WIDTH, HEIGHT - Player.BASE_HEIGHT * 4);
        
        loadLevel(currentLevelIndex);

        loadBackground();
        loadBlockImages();
        loadEnemyImage();
        
        Thread gameThread = new Thread(this);
        gameThread.start();
    }
    
    private void loadLevel(int levelIndex) {
        switch (levelIndex) {
            case 1:
                currentLevel = new level();
                break;
            case 2:
                currentLevel = new level2();
                break;
            case 3:
                currentLevel = new level3();
                break;
            default:
                gameState = GameState.WIN_SCREEN;
                break;
        }
        // NUEVO: Resetea la piel del personaje al cargar un nuevo nivel
        player.changeToNormalSkin();
        
        player.setX(WIDTH / 4 - Player.BASE_WIDTH);
        player.setY(HEIGHT - Player.BASE_HEIGHT * 4);
        player.resetSpeed();
        camX = 0;
        camY = 0;
    }

    private void resetLevel() {
        player.changeToNormalSkin();
        player.resetSpeed();
        camX = 0;
        camY = 0;
        leftPressed = false;
        rightPressed = false;
        loadLevel(currentLevelIndex);
    }
    
    private void loadBlockImages() {
        ImageIcon ii2 = new ImageIcon("resource/velocidad.png"); 
        blockImage2 = ii2.getImage();
        
        ImageIcon ii3 = new ImageIcon("resource/bala.png");
        blockImage3 = ii3.getImage();
    }

    private void loadBackground() {
        ImageIcon ii = new ImageIcon("resource/fondo.png");
        backgroundImage = ii.getImage();
    }
    
    private void loadEnemyImage() {
        ImageIcon ii = new ImageIcon("resource/enemigo.png");
        this.enemyImage = ii.getImage();
    }
    
    private void menu(Graphics g) {
        g.setColor(new Color(0, 0, 51));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Roboto", Font.BOLD, 60));
        String title = "SUPER MAN BOT";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (WIDTH / 2) - (titleWidth / 2), 150);

        g.setFont(new Font("Roboto", Font.PLAIN, 30));
        String startMessage = "Presione ESPACIO para jugar";
        int messageWidth = g.getFontMetrics().stringWidth(startMessage);
        g.drawString(startMessage, (WIDTH / 2) - (messageWidth / 2), HEIGHT / 2 + 50);
    }
    
    private void winScreen(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Roboto", Font.BOLD, 60));
        String winMessage = "¡GANASTE!";
        int messageWidth = g.getFontMetrics().stringWidth(winMessage);
        g.drawString(winMessage, (WIDTH / 2) - (messageWidth / 2), HEIGHT / 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameState == GameState.MENU) {
            menu(g);
        } else if (gameState == GameState.PLAYING) {

            Graphics2D g2d = (Graphics2D) g;

            g2d.translate(-camX, -camY);

            if (backgroundImage != null) {
                int bgImageWidth = backgroundImage.getWidth(null);
                if (bgImageWidth > 0) {
                    for (int i = 0; i < WORLD_WIDTH; i += bgImageWidth) {
                        g.drawImage(backgroundImage, i, 0, this);
                    }
                }
            } else {
                g.setColor(new Color(135, 206, 235));
                g.fillRect(0, 0, WORLD_WIDTH, HEIGHT);
            }
            
            List<Rectangle> bloquesDePiel = currentLevel.getBloquesDePiel();
            for (Rectangle block : bloquesDePiel) {
                g.drawImage(blockImage2, block.x, block.y, block.width, block.height, this);
            }
            
            List<enemigo> enemigos = currentLevel.getEnemigos();
            for (enemigo enemigo : enemigos) {
                g.drawImage(enemyImage, enemigo.getX(), enemigo.getY(), enemigo.getAncho(), enemigo.getAlto(), this);
            }

            Rectangle meta = currentLevel.getMeta();
            g.setColor(Color.RED);
            g.fillRect(meta.x, meta.y, meta.width, meta.height);
            
            g.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), this);
            
            g2d.translate(camX, camY);

            g.setColor(Color.BLACK);
            g.drawString("Nivel: " + currentLevelIndex, 20, 30);
            g.drawString("Vida: 3", 30, 50);

        } else if (gameState == GameState.WIN_SCREEN) {
            winScreen(g);
        }
    }

    public void update() {
        if (gameState != GameState.PLAYING) {
            return;
        }

        player.update();

        if (leftPressed) { player.moveLeft(); }
        if (rightPressed) { player.moveRight(); }

        if (player.getX() < 0) {
            player.setX(0);
        }
        if (player.getX() + player.getWidth() > WORLD_WIDTH) {
            player.setX(WORLD_WIDTH - player.getWidth());
        }

        if (player.getY() < 0) {
            player.setY(0);
            player.setVelocityY(0);
        }

        if (player.getY() + player.getHeight() >= HEIGHT) {
            player.setY(HEIGHT - player.getHeight());
            player.setVelocityY(0);
            player.setJumping(false);
        }
        
        Rectangle meta = currentLevel.getMeta();
        if (player.getBounds().intersects(meta)) {
            currentLevelIndex++;
            loadLevel(currentLevelIndex);
        }

        List<Rectangle> bloquesDePiel = currentLevel.getBloquesDePiel();
        for (Rectangle block : bloquesDePiel) {
            if (player.getBounds().intersects(block)) {
                player.changeToSkin2();
                player.setSpeed(player.baseSpeed * 2);
            }
        }
        
        List<enemigo> enemigos = currentLevel.getEnemigos();
        for (enemigo enemigo : enemigos) {
            if (player.getBounds().intersects(enemigo.getCajaDeColision())) {
                resetLevel();
                break; 
            }
        }

        int deadZoneX = (int) (WIDTH * 0.4); 

        if (player.getX() - camX > WIDTH - deadZoneX) {
            camX = player.getX() - (WIDTH - deadZoneX);
        }
        else if (player.getX() - camX < deadZoneX) {
            camX = player.getX() - deadZoneX;
        }

        if (camX < 0) {
            camX = 0;
        }
        if (camX + WIDTH > WORLD_WIDTH) {
            camX = WORLD_WIDTH - WIDTH;
        }
    }

    @Override
    public void run() {
        while (true) {
            update();
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (gameState == GameState.MENU) {
            if (key == KeyEvent.VK_SPACE) {
                gameState = GameState.PLAYING;
            }
        } else if (gameState == GameState.PLAYING) {
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) {
                player.jump();
            }
            if (key == KeyEvent.VK_A) { leftPressed = true; }
            if (key == KeyEvent.VK_D) { rightPressed = true; }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) { leftPressed = false; }
        if (key == KeyEvent.VK_D) { rightPressed = false; }
    }
}