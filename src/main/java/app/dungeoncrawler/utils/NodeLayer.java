package app.dungeoncrawler.utils;

public class NodeLayer extends SpriteElement {
    private int id;
    private Dimension dimension;

    /**
     * constructs node layer
     * @param id id of the node layer
     * @param imagePath image path to be used for creating node layer
     * @param width width of the sprite element
     * @param height height of the sprite element
     * @param dimension dimension used to create the sprite element
     */
    public NodeLayer(int id, String imagePath, int width, int height, Dimension dimension) {
        super(imagePath, width, height, true, false);
        this.dimension = dimension;
        this.id = id;
    }

    /**
     * constructs node layer
     * @param id id of the node layer
     * @param imagePath image path to be used for creating node layer
     * @param dm dimension to be used for creating node layer
     */
    public NodeLayer(int id, String imagePath, Dimension dm) {
        this(id, imagePath, 0, 0, dm);
    }

    /**
     * returns dimension
     * @return dimension of the node layer
     */
    public Dimension getDimension() {
        return dimension;
    }
    
    public int getAverageX() {
        return this.dimension.averageX();
    }   
    
    public int getAverageY() {
        return this.dimension.averageY();
    }
    /**
     * sets id
     * @param id id of the node layer
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets id
     * @return id of the node layer
     */
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        NodeLayer nodeLayer = (NodeLayer) obj;
        
        return this.id == nodeLayer.id 
                && this.dimension.equals(nodeLayer.dimension)
                && super.equals(nodeLayer);
    }
}
