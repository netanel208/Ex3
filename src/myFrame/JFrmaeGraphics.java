package myFrame;
import java.awt.Color;
/**
 * Code taken from: https://javatutorial.net/display-text-and-graphics-java-jframe
 * 
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class JFrmaeGraphics extends JPanel{


	public void paint(Graphics g){
		 Image image = Toolkit.getDefaultToolkit().getImage("E:\\שנה ב\\OOP\\מטלות\\מטלה 3\\Ex3_data\\data\\Ariel1.png");
			int w = this.getWidth();
			int h = this.getHeight();
			g.drawImage(image, 0, 0, w, h, this);
			g.setColor(Color.YELLOW);
			String s = " ["+w+","+h+"]";
		    g.drawString(s, w/3, h/2);
	}
	
	public static void main(String[] args){
		JFrame frame= new JFrame("JavaTutoria");	
		frame.getContentPane().add(new JFrmaeGraphics());
		frame.setSize(1433, 642);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setName("JFrame example");
	}
}