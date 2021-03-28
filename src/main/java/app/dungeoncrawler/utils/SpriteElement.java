package app.dungeoncrawler.utils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class SpriteElement {
    private String image;
    private int positionAtX;
    private int positionAtY;
    private int prevPositionAtX;
    private int prevPositionAtY;
    private int elementHeight;
    private int elementWidth;
    private boolean preserveRatio;
    private boolean smooth;
    private Image imageCached;

    /**
     * constructs sprite element
     * @param imagePath image path used to construct sprite elements
     * @param width width of the element width
     * @param height height of the element height
     */
    public SpriteElement(String imagePath, int width, int height) {
        this(imagePath, width, height, false, false);
    }

    /**
     * constructs sprite elements
      * @param imagePath image path used to construct sprite elements
     * @param width width of the element width
     * @param height height of the element height
     * @param preserveRatio ratio to preserve the image
     * @param smooth smooth to construct sprite elements
     */
    public SpriteElement(
            String imagePath, 
            int width, 
            int height,
            boolean preserveRatio, 
            boolean smooth
    ) {
        this.elementHeight = height;
        this.elementWidth = width;
        this.preserveRatio = preserveRatio;
        this.smooth = smooth;
        
        if (imagePath != "") {
            this.image = imagePath;
        }

    }

    /**
     * sets player position
     * @param posX x position of the character
     * @param posY y position of the character
     */
    public void setPosition(int posX, int posY) {
        this.prevPositionAtX = this.positionAtX;
        this.prevPositionAtY = this.positionAtY;
        this.positionAtX = posX;
        this.positionAtY = posY;
    }

    /**
     * gets x position of the character
     * @return x position of the character
     */
    public int getPositionAtX() {
        return this.positionAtX;
    }
    
    /**
     * gets y position of the character
     * @return y position of the character
     */
    public int getPositionAtY() {
        return this.positionAtY;
    }

    /**
     * draws graphics context
     */
    public void draw(GraphicsContext graphicsContext) {
        this.clear(graphicsContext);
        this.imageCached = this.imageCached == null 
            ? new Image(
                getClass()
                        .getResource(this.image)
                        .toExternalForm(),
                this.elementWidth,
                this.elementHeight,
                this.preserveRatio,
                this.smooth
            ) : this.imageCached;
        
        graphicsContext.drawImage(this.imageCached, this.positionAtX, this.positionAtY);
    }
    
    /**
     * clears graphics context
     * @param graphicsContext graphics context which will be cleared
     */
    public void clear(GraphicsContext graphicsContext) {
        double porcentage = (double) 20 / (double) 100;
        int widthWithExtraPadding = (int) ((double) this.elementWidth * porcentage)
                + this.elementWidth;
        int heightWithExtraPadding = (int) ((double) this.elementHeight * porcentage)
                + this.elementHeight;
        
        graphicsContext.restore();
        graphicsContext.clearRect(
                this.prevPositionAtX,
                this.prevPositionAtY,
                widthWithExtraPadding,
                heightWithExtraPadding
        );
    }

    @Override
    public boolean equals(Object obj) {
        SpriteElement spriteElement = (SpriteElement) obj;
        
        return this.image == spriteElement.image 
                && this.elementWidth == spriteElement.elementWidth 
                && this.elementHeight == spriteElement.elementHeight
                && this.smooth == spriteElement.smooth
                && this.preserveRatio == spriteElement.preserveRatio;
    }
    public void setImage(String image) {
        this.image = image;
        this.imageCached = null;
    }
}
