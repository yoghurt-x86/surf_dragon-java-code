import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;


public class DragonViewer
{
    private JFrame frame;
    private DragonComponent dragonComponent;
    
    private JButton iterationButton;
    private int iterationCounter;
    private JLabel iterationLabel;
    
    public DragonViewer(int n)
    {
        makeFrame(n);
    }
    
    public DragonViewer()
    {
        this(0);
    }
    
    private void makeFrame(int n)
    {
        frame = new JFrame("Dragon");
        frame.setSize(720,480);
        frame.setMinimumSize(new Dimension(300,300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel content = (JPanel)frame.getContentPane();
        content.setLayout(new BorderLayout());
        
        dragonComponent = new DragonComponent(n);
        content.add(dragonComponent, BorderLayout.CENTER);
        
        iterationButton = new JButton("Iterate");
        iterationButton.addActionListener(e -> addIteration());
        content.add(iterationButton, BorderLayout.NORTH);
        
        iterationCounter = n;
        iterationLabel = new JLabel("Iteration: " + iterationCounter);
        content.add(iterationLabel, BorderLayout.SOUTH);
        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        frame.setVisible(true);
    }
    
    public void addIteration()
    {
        dragonComponent.iterateOnce();
        iterationCounter += 1;
        iterationLabel.setText("Iteration: " + iterationCounter);
        frame.repaint();
    }
    
}
