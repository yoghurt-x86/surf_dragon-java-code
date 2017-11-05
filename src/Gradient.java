import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class Gradient {
    private TreeMap<Float, Color> colors;

    public Gradient(Color a, Color b){
        this.colors = new TreeMap<>();
        colors.put(0F, a);
        colors.put(100F, b);
    }

    public void addColor(Color c, float percentage){
        if(percentage > 100 || percentage < 0) throw new IllegalArgumentException();
        colors.put(percentage, c);
    }

    public Color getColor(float percentage){
        if(percentage > 100 || percentage < 0) throw new IllegalArgumentException();
        if(percentage == 100) return colors.lastEntry().getValue();
        if(percentage == 0) return colors.firstEntry().getValue();
        Map.Entry a = colors.floorEntry(percentage);
        Map.Entry b = colors.ceilingEntry(percentage);

        float range = (float) b.getKey() - (float) a.getKey();
        float in = Math.abs((percentage - ((float)a.getKey())) / range);

        int cR = (int) ((((Color) b.getValue()).getRed() * in) + (((Color) a.getValue()).getRed() * (1F-in)));
        int cG = (int) ((((Color) b.getValue()).getGreen() * in) + (((Color) a.getValue()).getGreen() * (1F-in)));
        int cB = (int) ((((Color) b.getValue()).getBlue() * in) + (((Color)  a.getValue()).getBlue() * (1F-in)));

        return new Color(cR, cG, cB);
    }

    public static void main(String[] args) {
        Gradient g = new Gradient(Color.BLACK, Color.WHITE);
        Color c = g.getColor(80);
        System.out.println("r: "+ c.getRed() + " g: " + c.getGreen() + " b: " + c.getBlue());
    }
}
