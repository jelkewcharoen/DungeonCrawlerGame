package app.dungeoncrawler.utils;

public abstract class AttachableItems {
    private String type;
    private String name;
    private String image;
    
    public abstract void addToPlayer(Fighter fighter);
    public abstract void removeFromPlayer(Fighter fighter);

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
