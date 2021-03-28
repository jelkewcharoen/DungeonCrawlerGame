package app.dungeoncrawler.utils;

public class ObserverObject<T> {
    private T field;

    public ObserverObject(T field) {
        this.field = field;
    }

    public T getField() {
        return field;
    }

    public void setField(T field) {
        this.field = field;
    }
}
