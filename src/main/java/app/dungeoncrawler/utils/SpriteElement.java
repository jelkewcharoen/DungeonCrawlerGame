package app.dungeoncrawler.utils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class SpriteElement {
    private Image image;
    private int positionAtX, positionAtY;
    private int prevPositionAtX, prevPositionAtY;
    private int elementHeight, elementWidth;
    private GraphicsContext graphicsContext;

    private SpriteElement(Image image) {}
    public SpriteElement(String imagePath, int width, int height) {
        this(imagePath, width, height,false,false);
    }
    public SpriteElement(
            String imagePath, 
            int width, 
            int height,
            boolean preserveRatio, 
            boolean smooth
    ) {
        this.elementHeight = height;
        this.elementWidth = width;
        
        if (imagePath != "") {
            this.image = new Image(
                    getClass()
                            .getResource(imagePath)
                            .toExternalForm(),
                    width,
                    height,
                    preserveRatio,
                    smooth
            );

        }

    }
    
    public void setPosition(int posX, int posY) {
        this.prevPositionAtX = this.positionAtX;
        this.prevPositionAtY = this.positionAtY;
        this.positionAtX = posX;
        this.positionAtY = posY;
    }

    public int getPositionAtX() {
        return this.positionAtX;
    }

    public int getPositionAtY() {
        return this.positionAtY;
    }

    public void draw(GraphicsContext graphicsContext)
    {
        if (graphicsContext != null) {
            this.graphicsContext = graphicsContext;
        }
        
        double porcentage = (double)20/(double)100;
        int widthWithExtraPadding = (int)((double)this.elementWidth * porcentage) + this.elementWidth;
        int heightWithExtraPadding = (int)((double)this.elementHeight * porcentage) + this.elementHeight;
        
        this.graphicsContext.restore();
        this.graphicsContext.clearRect(
                this.prevPositionAtX, 
                this.prevPositionAtY,
                widthWithExtraPadding, 
                heightWithExtraPadding
        );

        this.graphicsContext.drawImage(image, this.positionAtX, this.positionAtY);
    }
}
