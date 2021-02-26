/**
 * Класс координат с полями x - int и y - double.
 * @author я
 */
public class Coordinates {
    private int x;
    private Double y; //Поле не может быть null

    public int getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public  Coordinates (int x, Double y) {
        this.x = x;
        this.y = y;
    }
}