import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;

public class DragonComponent extends JComponent
{
    private DragonCurve dragon;
    private Coordinate[] simpleDragon;
    private Coordinate[] complexDragon;
    private Dimension canvasSize;
    
    public DragonComponent(int n)
    {
        super();
        dragon = new DragonCurve(n);
        
        //for(Coordinate c : simpleDragon) {System.out.println(c);}
        
    }
    
    public void iterateOnce()
    {
        dragon.iterateOnce();
    }
   
    public void paint(Graphics g)
    {
        canvasSize = this.getSize();
        
        simpleDragon = dragon.getSimpleCoordinates();
        makeComplexDragon();
        
        g.setColor(new Color(40,40,40));
        g.fillRect(0,0,canvasSize.width,canvasSize.height);
        
        g.translate(getStartPoint().width , getStartPoint().height);
        
        
        for(int i = 1 ; i < simpleDragon.length ; i++)
        {
            int cr = (int)Math.round((double)i / (double) simpleDragon.length * 255);
            int cg = 255 - cr;
            int cb = 128 + (cr / 2);
            
            g.setColor(new Color(cr,cg,cb));
            g.drawLine((int)complexDragon[i-1].x , (int)complexDragon[i-1].y , (int)complexDragon[i].x , (int)complexDragon[i].y);
        }
    }
    
    private void makeComplexDragon()
    {
        complexDragon = new Coordinate[simpleDragon.length];
        
        
        double scale = getTranslateScale(getStartPoint(), getEndPoint(), simpleDragon[simpleDragon.length - 1]);
        double angle = getTranslateAngle(simpleDragon[simpleDragon.length - 1]);

        //System.out.println("scale: " + scale);
        //System.out.println("angle: " + angle);
        
        
        for(int i = 0 ; i < complexDragon.length ; i++)
        {
            complexDragon[i] = translatePoint(simpleDragon[i], (2 * Math.PI) - angle, scale);
        }
    }   
    
    private Coordinate translatePoint(Coordinate c, double angle, double scale)
    {
        return new Coordinate(
            ((c.x * (Math.cos(angle))) - ((c.y * Math.sin(angle)))) * scale
           ,((c.x * (Math.sin(angle))) + ((c.y * Math.cos(angle)))) * scale);
    }
    
    private double getTranslateAngle(Coordinate c)
    {
        return Math.atan2(c.y,c.x);
    }
    
    private double getTranslateScale(Dimension a, Dimension b, Coordinate c)
    {
        Dimension d = new Dimension(b.width - a.width, b.height - a.height);
        return  (Math.sqrt((d.width * d.width) + (d.height * d.height))) / (Math.sqrt((c.x * c.x) + (c.y * c.y)));
    }
    
    private Dimension getStartPoint()
    {
        return new Dimension(canvasSize.width / 4, canvasSize.height / 3);
    }
    
    private Dimension getEndPoint()
    {
        return new Dimension(canvasSize.width - canvasSize.width / 4, canvasSize.height / 3);
    }
}