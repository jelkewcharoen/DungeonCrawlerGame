package app.dungeoncrawler.utils;

public class Dimension {
    public int lowX;
    public int highX;
    public int lowY;
    public int highY;
    
    public Dimension(int x1, int x2, int y1, int y2) {
        this.lowX = Math.min(x1, x2);     
        this.highX = Math.max(x1, x2);         
        this.lowY = Math.min(y1, y2);     
        this.highY = Math.max(y1, y2);     
    }
    
    public boolean isInsideCoordinates(int x, int y) {
        return x <= this.highX 
                && x >= this.lowX 
                && y >= this.lowY 
                && y <= this.highY;
    }
    
    public int averageX() {
        return this.average(this.highX, this.lowX);
    }
    
    public int averageY() {
        return this.average(this.highY, this.lowY);
    }
    
    public int average(int highNumber, int lowNumber) {
        return  (highNumber + lowNumber) / 2;
    }
}
