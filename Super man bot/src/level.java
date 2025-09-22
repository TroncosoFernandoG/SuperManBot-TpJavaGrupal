import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class level extends BaseLevel{ // Nombre de la clase: level

    private List<enemigo> enemigos;
    private List<Rectangle> bloquesDePiel;
    private Rectangle meta;

    public level() {
        this.enemigos = new ArrayList<>();
        this.bloquesDePiel = new ArrayList<>();
        
        enemigos.add(new enemigo(400, 570, 40, 40));
        enemigos.add(new enemigo(450, 570, 40, 40));
        enemigos.add(new enemigo(1000, 570, 40, 40));
        enemigos.add(new enemigo(1100, 570, 40, 40));
        enemigos.add(new enemigo(1500, 570, 40, 40));
        enemigos.add(new enemigo(1600, 570, 40, 40));
        enemigos.add(new enemigo(1700, 570, 40, 40));

        bloquesDePiel.add(new Rectangle(800, 510, 50, 50));
        
        meta = new Rectangle(1900, 570, 50, 50);
    }
    
    public List<enemigo> getEnemigos() {
        return enemigos;
    }
    
    public List<Rectangle> getBloquesDePiel() {
        return bloquesDePiel;
    }

    public Rectangle getMeta() {
        return meta;
    }
    
}