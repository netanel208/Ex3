package myFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import File_format.Map;
import GIS.Fruit;
import GIS.Game;
import GIS.Packmen;
import Geom.Point3D;

public class MyGUI extends JFrame implements MouseListener,ActionListener,ItemListener{
	
	
	Image image;
	Image packmenIcon;
	Image fruitIcon;
	MenuBar menuBar;
	Menu elementItem;
	Menu fileItem;
	Menu runItem;
	MenuItem packmenItem;
	MenuItem fruitItem;
	MenuItem addItem;
	MenuItem saveItem;
	MenuItem runProgramItem;
	int x = -1;
	int y = -1;
	int width = -1;
	int height = -1;
	boolean fruit_addable = false;
	boolean packmen_addable = false;
	ArrayList<FruitLabel> fruitsLabels;
	ArrayList<PacmanLabel> pacmansLabels;

	private MyGUI()
	{
		initGUI();
		this.addMouseListener(this);
	}

	private void initGUI() 
	{
		image = Toolkit.getDefaultToolkit().getImage("E:\\שנה ב\\OOP\\מטלות\\מטלה 3\\Ex3_data\\data\\Ariel1.png");
		packmenIcon = Toolkit.getDefaultToolkit().getImage("E:\\שנה ב\\OOP\\מטלות\\מטלה 3\\Ex3_data\\data\\pacmen.png");
		fruitIcon = Toolkit.getDefaultToolkit().getImage("E:\\שנה ב\\OOP\\מטלות\\מטלה 3\\Ex3_data\\data\\ananas.png");
		menuBar = new MenuBar();
		fileItem = new Menu("File");
		elementItem = new Menu("Element");
		runItem = new Menu("Start");

		packmenItem = new MenuItem("Pacman");
		packmenItem.addActionListener(this);

		fruitItem = new MenuItem("Fruit");
		fruitItem.addActionListener(this);
		
		addItem = new MenuItem("Add Csv");
		addItem.addActionListener(this);
		
		saveItem = new MenuItem("Save");
		saveItem.addActionListener(this);
		
		runProgramItem = new MenuItem("Run");
		runProgramItem.addActionListener(this);
		
		menuBar.add(fileItem);
		fileItem.add(addItem);
		fileItem.add(saveItem);
		
		menuBar.add(elementItem);
		elementItem.add(packmenItem);
		elementItem.add(fruitItem);
		
		menuBar.add(runItem);
		runItem.add(runProgramItem);

		this.setMenuBar(menuBar);

		fruitsLabels = new ArrayList<FruitLabel>();
		pacmansLabels = new ArrayList<PacmanLabel>();
		
		width = this.getWidth();
		height = this.getHeight();
		
	}
	public void paint(Graphics g){
		int w = this.getWidth();
		int h = this.getHeight();
		g.drawImage(image, 0, 0, w, h, this);
		g.setColor(Color.YELLOW);
		String s = " ["+w+","+h+"]";
		g.drawString(s, w/3, h/2);
		System.out.println("size fruits ="+fruitsLabels.size());
		System.out.println("new width = "+w);
		System.out.println("old width = "+ width);
		if(!fruitsLabels.isEmpty())
		{
			for(int i=0 ; i<fruitsLabels.size() ; i++)
			{
				FruitLabel fruit = fruitsLabels.get(i);
				int x_fruit = fruitsLabels.get(i).getX();
				int y_fruit = fruitsLabels.get(i).getY();
				x_fruit = (int)(((double)w)*((double)fruit.x_factor));
				y_fruit = (int)(((double)h)*((double)fruit.y_factor));
				System.out.println("fruit (X,Y)= "+"("+x_fruit+","+y_fruit+")");
				fruit.setLocation(x_fruit, y_fruit);
				g.drawImage(fruitIcon,x_fruit, y_fruit, 25, 25,this);
			}
		}
		if(!pacmansLabels.isEmpty())
		{
			for(int i=0 ; i<pacmansLabels.size() ;i++)
			{
				PacmanLabel pacman = pacmansLabels.get(i);
				int x_pacman = pacmansLabels.get(i).getX();
				int y_pacman = pacmansLabels.get(i).getY();
				x_pacman = (int)(((double)w)*((double)pacman.x_factor));
				y_pacman = (int)(((double)h)*((double)pacman.y_factor));
				//System.out.println("fruit (X,Y)= "+"("+x_fruit+","+y_fruit+")");
				g.drawImage(packmenIcon,x_pacman, y_pacman, 25, 25,this);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(packmenItem))
		{
			packmen_addable = true;
			fruit_addable = false;
			System.out.println("Press packmen");
		}
		if(event.getSource().equals(fruitItem))
		{
			packmen_addable = false;
			fruit_addable = true;
			System.out.println("Press fruit");
		}
		if(event.getSource().equals(saveItem))
		{
			createGame();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("mouse Clicked");
		System.out.println("("+ arg0.getX() + "," + arg0.getY() +")");
		x = arg0.getX();
		y = arg0.getY();
		if(fruit_addable)
		{
			fruitsLabels.add(new FruitLabel(this.getWidth(), this.getHeight(), x, y));
			System.out.println("location = "+fruitsLabels.get(fruitsLabels.size()-1).getLocation());
		}
		if(packmen_addable)
		{ 
			pacmansLabels.add(new PacmanLabel(this.getWidth(), this.getHeight(), x, y));
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		MyGUI window = new MyGUI();
		window.setVisible(true);
		window.setSize(1433, 642);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class FruitLabel extends JLabel{
		
		double x_factor;
		double y_factor;
		
		public FruitLabel(int width,int height,int x,int y)
		{
			this.setLocation(x, y);
			x_factor = ((double)this.getX())/((double)width);
			y_factor = ((double)this.getY())/((double)height);
		}
	}
	
class PacmanLabel extends JLabel{
		
		double x_factor;
		double y_factor;
		
		public PacmanLabel(int width,int height,int x,int y)
		{
			this.setLocation(x, y);
			x_factor = ((double)this.getX())/((double)width);
			y_factor = ((double)this.getY())/((double)height);
		}
	}

//when save press
private void createGame() {
//	 Image _m = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);

	final Point3D start = new Point3D(32.10571,35.20237,0);
	final Point3D end = new Point3D(32.10189,35.21239,0);
	Map m = new Map(image,this.getWidth(),this.getHeight(),start,end);
	ArrayList<Fruit> fruits = new ArrayList<Fruit>();
	ArrayList<Packmen> pacmans = new ArrayList<Packmen>();
	for(int i=0 ; i<fruitsLabels.size() ; i++)
	{
		FruitLabel fru = fruitsLabels.get(i);
		Point3D p =m.toGPS(new Point3D(fru.getX() ,fru.getY(), 0));
		System.out.println("////x"+fru.getX()+"/////y"+fru.getY());
		String[] lineF = {"F",i+"",p.x()+"", p.y()+"", p.z()+"", "1"};
		fruits.add(new Fruit(lineF));
	}
	
	for(int i=0 ; i<pacmansLabels.size() ; i++)
	{
		PacmanLabel pac = pacmansLabels.get(i);
		Point3D p =m.toGPS(new Point3D(pac.getX() ,pac.getY(), 0));
		String[] lineP = {"P",i+"",p.x()+"", p.y()+"", p.z()+"", "1","1"};
		pacmans.add(new Packmen(lineP));
	}
	
	try{
		Game game = new Game(pacmans ,fruits);//create output.csv
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return;
	}
}

}
