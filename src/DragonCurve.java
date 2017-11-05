import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DragonCurve
{
    private Turn[] curveArray;
    private final String leftTurn = "instances/left_turn.vmf";
    private final String rightTurn = "instances/right_turn.vmf";
    
    public DragonCurve()
    {
        curveArray = new Turn[0];
    }
    
    public DragonCurve(int n)
    {
        this();
        iterateMany(n);
    }
    
    public Turn[] getCurve()
    {
        return curveArray;
    }
    
    public void printVmfEntities()
    {
        try{
            PrintWriter out = new PrintWriter("map.txt");
            char lastDirection = 'E';
            int x, y, z;
            //origin:
            x = -10240;
            y = 5632;
            z = 15360 - 2048;

            Gradient g = new Gradient(new Color(255, 78, 178), new Color(255, 78, 178));
            g.addColor(new Color(17, 155, 247),25);
            g.addColor(new Color(255, 188, 81), 75);
            g.addColor(new Color(65, 255, 143), 50);

            for(int i = 0 ; i < curveArray.length ; i++){
                float p = (float) ((((double) i) / ((double)curveArray.length)) * 100);
                Color c = g.getColor(p);
                switch(curveArray[i]) {
                    case RIGHT:
                        switch(lastDirection) {
                            case 'N'    : printVmfEntity(out, x, y, z, 0, 0, 0, 69, rightTurn,c.getRed(),c.getGreen(),c.getBlue())   ; x+=512 ; y+=512 ; z-=48; lastDirection = 'E'; break ;
                            case 'S'    : printVmfEntity(out, x, y, z, 0, 180, 0, 69, rightTurn,c.getRed(),c.getGreen(),c.getBlue()) ; x-=512 ; y-=512 ; z-=48; lastDirection = 'W'; break ;
                            case 'E'    : printVmfEntity(out, x, y, z, 0, -90, 0, 69, rightTurn,c.getRed(),c.getGreen(),c.getBlue()) ; x+=512 ; y-=512 ; z-=48; lastDirection = 'S'; break ;
                            case 'W'    : printVmfEntity(out, x, y, z, 0, 90, 0, 69, rightTurn,c.getRed(),c.getGreen(),c.getBlue())  ; x-=512 ; y+=512 ; z-=48; lastDirection = 'N'; break ;
                        }
                    break;
                    case LEFT:
                        switch(lastDirection) {
                            case 'N'    : printVmfEntity(out, x, y, z, 0, 0, 0, 69, leftTurn,c.getRed(),c.getGreen(),c.getBlue())    ; x-=512 ; y+=512 ; z-=48; lastDirection = 'W'; break ;
                            case 'S'    : printVmfEntity(out, x, y, z, 0, 180, 0, 69, leftTurn,c.getRed(),c.getGreen(),c.getBlue())  ; x+=512 ; y-=512 ; z-=48; lastDirection = 'E'; break ;
                            case 'E'    : printVmfEntity(out, x, y, z, 0, -90, 0, 69, leftTurn,c.getRed(),c.getGreen(),c.getBlue())  ; x+=512 ; y+=512 ; z-=48; lastDirection = 'N'; break ;
                            case 'W'    : printVmfEntity(out, x, y, z, 0, 90, 0, 69, leftTurn,c.getRed(),c.getGreen(),c.getBlue())   ; x-=512 ; y-=512 ; z-=48; lastDirection = 'S'; break ;
                        }
                    break;
                }
            }
            out.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void printVmfEntity(PrintWriter out, int x, int y, int z, int pitch, int yaw, int roll, int id, String instanceName, int R, int G, int B)//int x, int y, int z)
    {
        out.println("entity");
        out.println("{");
        out.println("    \"id\" \"" + id + "\"");
        out.println("    \"classname\" \"func_instance\"");
        out.println("    \"angles\" \"" + pitch + " " + yaw + " " + roll + "\"");
        out.println("    \"file\" \"" + instanceName + "\"");
        out.println("    \"fixup_style\" \"0\"");
        out.println("    \"replace01\" \"$color " + R + " " + G + " " + B + "\"");
        out.println("    \"origin\" \"" + x + " " + y + " " + z + "\"");
        out.println("    editor");
        out.println("    {");
        out.println("        \"color\" \"220 30 220\"");
        out.println("        \"visgroupshown\" \"1\"");
        out.println("        \"visgroupautoshown\" \"1\"");
        out.println("        \"logicalpos\" \"[0 0]\"");
        out.println("    }");
        out.println("}");
    }
    
    public Coordinate[] getSimpleCoordinates()
    {
        Coordinate[] c = new Coordinate[ curveArray.length + 2 ];
        c[0] = new Coordinate(0,0);
        c[1] = new Coordinate(1,0);
        char lastDirection = 'E';
        
        for(int i = 2 ; i < c.length ; i++){
            switch(curveArray[i-2]) {
                case RIGHT:
                    switch(lastDirection) {
                        case 'N'    : c[i] = new Coordinate(c[i-1].x + 1, c[i-1].y) ; lastDirection = 'E'; break ;
                        case 'S'    : c[i] = new Coordinate(c[i-1].x - 1, c[i-1].y) ; lastDirection = 'W'; break ;
                        case 'E'    : c[i] = new Coordinate(c[i-1].x, c[i-1].y - 1) ; lastDirection = 'S'; break ;
                        case 'W'    : c[i] = new Coordinate(c[i-1].x, c[i-1].y + 1) ; lastDirection = 'N'; break ;
                    }
                break;
                case LEFT:
                    switch(lastDirection) {
                        case 'N'    : c[i] = new Coordinate(c[i-1].x - 1, c[i-1].y) ; lastDirection = 'W'; break ;
                        case 'S'    : c[i] = new Coordinate(c[i-1].x + 1, c[i-1].y) ; lastDirection = 'E'; break ;
                        case 'E'    : c[i] = new Coordinate(c[i-1].x, c[i-1].y + 1) ; lastDirection = 'N'; break ;
                        case 'W'    : c[i] = new Coordinate(c[i-1].x, c[i-1].y - 1) ; lastDirection = 'S'; break ;
                    }
                break;
            }
        }
        
        return c;
    }
    
    public String getCurveString(){
        String s = "";
        for(Turn t: curveArray){
            switch(t){
                case RIGHT  : s = s + "R"   ; break;
                case LEFT   : s = s + "L"   ; break;
            }
        }
        return s;
    }
    
    public void iterateOnce()
    {
        Turn[] newArray = new Turn[(2 * curveArray.length) + 1];
        
        for(int i = 0 ; i < curveArray.length ; i++)
        {
            newArray[i] = curveArray[i];
        }
        
        newArray[curveArray.length] = Turn.RIGHT;
        
        for(int i = 0 ; i < curveArray.length ; i++)
        {
            switch(curveArray[curveArray.length - i - 1]) {
                case RIGHT  : newArray[i + curveArray.length + 1 ] = Turn.LEFT   ;   break;
                case LEFT   : newArray[i + curveArray.length + 1 ] = Turn.RIGHT  ;   break;
            }
        }
        
        curveArray = newArray;
    }
    
    public void iterateMany(int n)
    {
        for(int i = 0 ; i < n ; i++)
        {
            iterateOnce();
        }
    }
}

