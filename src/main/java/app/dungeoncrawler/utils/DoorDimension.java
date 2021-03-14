package app.dungeoncrawler.utils;

public class DoorDimension extends Dimension {
    private int positionXForPlayer, positionYForPlayer;
    
    public DoorDimension(int x1, int x2, int y1, int y2) {
        super(x1, x2, y1, y2);
    }    
    
    public DoorDimension(int x1, int x2, int y1, int y2, int positionXForPlayer, int positionYForPlayer) {
        super(x1, x2, y1, y2);
        this.positionXForPlayer = positionXForPlayer;
        this.positionYForPlayer = positionYForPlayer;
    }

    public int getPositionXForPlayer() {
        return positionXForPlayer == 0 ? super.averageX() : positionXForPlayer;
    }

    public int getPositionYForPlayer() {
        return positionYForPlayer == 0 ? super.averageY() : positionYForPlayer;
    }
}
