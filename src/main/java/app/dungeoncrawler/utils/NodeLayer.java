package app.dungeoncrawler.utils;

import app.dungeoncrawler.models.Game;
import javafx.scene.image.Image;

public class NodeLayer extends SpriteElement{
    public int id;
    public Dimension dimension;

    public NodeLayer(int id, String imagePath, int width, int height, Dimension dimension) {
        super(imagePath, width, height, true, false);
        this.dimension = dimension;
        this.id = id;
    }
    
    public NodeLayer(int id, String imagePath, Dimension dm) {
        this(id, imagePath, 0, 0, dm);
    }
    
    public Dimension getDimension() {
        return dimension;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
