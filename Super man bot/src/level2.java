import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class level2 extends BaseLevel{ // Nombre de la clase: level2

    private List<enemigo> enemigos;
    private List<Rectangle> bloquesDePiel;
    private Rectangle meta;

    public level2() {
        this.enemigos = new ArrayList<>();
        this.bloquesDePiel = new ArrayList<>();
        
        enemigos.add(new enemigo(300, 500, 40, 40));
        enemigos.add(new enemigo(700, 400, 40, 40));
        enemigos.add(new enemigo(1200, 300, 40, 40));
        enemigos.add(new enemigo(1500, 200, 40, 40));

        bloquesDePiel.add(new Rectangle(600, 400, 50, 50));
        
        meta = new Rectangle(1800, 570, 50, 50);
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
