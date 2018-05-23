import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Line {
    public ArrayList<Point> stroke;
    public Color color;
    public int size=5;
    public boolean reflection;
    //constructor for the Line class
    public Line(Color color, int size, boolean reflection) {

        stroke = new ArrayList<>();
        this.color = color;
        this.size = size;
        this.reflection = reflection;
    }
    //another constructor used when I only want to set another color
    public Line(Color color){
        this.color=color;
    }

    //it adds points to the arraylist
    public void add(Point p){
        stroke.add(p);
    }

    //returns the whole ArrayList
    public ArrayList<Point> getStroke() {
        return stroke;
    }

    public Color returnColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public boolean getReflect() {
        return reflection;
    }
}
