import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class level3 extends BaseLevel{ // Nombre de la clase: level3

    private List<enemigo> enemigos;
    private List<Rectangle> bloquesDePiel;
    private Rectangle meta;

    public level3() {
        this.enemigos = new ArrayList<>();
        this.bloquesDePiel = new ArrayList<>();
        
        enemigos.add(new enemigo(350, 500, 40, 40));
        enemigos.add(new enemigo(500, 500, 40, 40));
        enemigos.add(new enemigo(650, 500, 40, 40));
        enemigos.add(new enemigo(1000, 400, 40, 40));
        enemigos.add(new enemigo(1200, 300, 40, 40));
        enemigos.add(new enemigo(1400, 200, 40, 40));

        bloquesDePiel.add(new Rectangle(800, 400, 50, 50));
        
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