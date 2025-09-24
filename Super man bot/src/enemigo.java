import java.awt.Rectangle;

public class enemigo {
    
    private int x, y, ancho, alto;
    private Rectangle cajaDeColision;

    public enemigo(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.cajaDeColision = new Rectangle(x, y, ancho, alto);
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getAncho() {
        return ancho;
    }
    
    public int getAlto() {
        return alto;
    }
    
    public Rectangle getCajaDeColision() {
        return cajaDeColision;
    }
}
