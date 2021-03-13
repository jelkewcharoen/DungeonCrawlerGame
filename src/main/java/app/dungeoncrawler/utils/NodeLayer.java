package app.dungeoncrawler.utils;

import app.dungeoncrawler.models.Game;
import javafx.scene.image.Image;

public class NodeLayer extends SpriteElement{
    public Dimension dimension;
    
    public NodeLayer(String imagePath, int width, int height, Dimension dimension) {
        super(imagePath, width, height, true, false);
        this.dimension = dimension;
    }
    
    public NodeLayer(String imagePath, Dimension dm) {
        this(imagePath, 0, 0, dm);
    }

    public Dimension getDimension() {
        return dimension;
    }
}
