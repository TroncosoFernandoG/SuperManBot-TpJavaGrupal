import java.awt.Rectangle;
import java.util.List;

public abstract class BaseLevel {
    public abstract List<enemigo> getEnemigos();
    public abstract List<Rectangle> getBloquesDePiel();
    public abstract Rectangle getMeta();
}