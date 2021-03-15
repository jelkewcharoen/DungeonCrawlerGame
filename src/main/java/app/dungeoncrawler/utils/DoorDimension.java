package app.dungeoncrawler.utils;

public class DoorDimension extends Dimension {
    private int positionXForPlayer;
    private int positionYForPlayer;

    /**
     * constructs door dimension
     * @param x1 one of two x coordinates
     * @param x2 one another of two x coordinates
     * @param y1 one of two y coordinates
     * @param y2 one another of two y coordinates
     */
    public DoorDimension(int x1, int x2, int y1, int y2) {
        super(x1, x2, y1, y2);
    }

    /**
     * constructs door dimension
     * @param x1 one of two x coordinates
     * @param x2 one another of two x coordinates
     * @param y1 one of two y coordinates
     * @param y2 one another of two y coordinates
     * @param positionXForPlayer x position of the player
     * @param positionYForPlayer y position of the player
     */
    public DoorDimension(int x1, int x2, int y1, int y2,
                         int positionXForPlayer, int positionYForPlayer) {
        super(x1, x2, y1, y2);
        this.positionXForPlayer = positionXForPlayer;
        this.positionYForPlayer = positionYForPlayer;
    }

    /**
     * gets x coordinates of player
     * @return returns x coordinates of player
     */
    public int getPositionXForPlayer() {
        return positionXForPlayer == 0 ? super.averageX() : positionXForPlayer;
    }

    /**
     * gets y coordinates of player
     * @return returns y coordinates of player
     */
    public int getPositionYForPlayer() {
        return positionYForPlayer == 0 ? super.averageY() : positionYForPlayer;
    }
}
