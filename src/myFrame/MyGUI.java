package myFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Algorithm.ShortestPathAlgo;
import File_format.Map;
import GIS.Fruit;
import GIS.Game;
import GIS.Packmen;
import Geom.Point3D;
import Paths.Path;

import javax.swing.JFileChooser;

public class MyGUI extends JFrame implements MouseListener,ActionListener,ItemListener, Runnable {


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

	////////
	private JTextField filename = new JTextField();

	final Point3D start = new Point3D(32.10571,35.20237,0);
	final Point3D end = new Point3D(32.10189,35.21239,0);
	//////////

	private MyGUI()
	{
		initGUI();
		this.addMouseListener(this);
	}

	private void initGUI() 
	{
		image = Toolkit.getDefaultToolkit().getImage("Ariel1.png");
		packmenIcon = Toolkit.getDefaultToolkit().getImage("pacman.png");
		fruitIcon = Toolkit.getDefaultToolkit().getImage("fruit.png");
		///////////
		Container cp = getContentPane();
		filename.setEditable(false);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 1));
		p.add(filename);
		cp.add(p, BorderLayout.NORTH);
		//////////
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
				System.out.println("1)pacman (X,Y)= "+"("+x_pacman+","+y_pacman+")");
				x_pacman = (int)(((double)w)*((double)pacman.x_factor));
				y_pacman = (int)(((double)h)*((double)pacman.y_factor));
				pacman.setLocation(x_pacman, y_pacman);
				System.out.println("2)pacman (X,Y)= "+"("+x_pacman+","+y_pacman+")");
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

		if(event.getSource().equals(addItem))
		{
			JFileChooser c = new JFileChooser();
			// Demonstrate "Open" dialog:
			int rVal = c.showOpenDialog(MyGUI.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				String diraction = c.getCurrentDirectory().toString()+"\\"+c.getSelectedFile().getName();
				filename.setText(diraction);
				System.out.println(diraction);
				LoadGame(diraction);

			}
			if (rVal == JFileChooser.CANCEL_OPTION) {
				filename.setText("You pressed cancel");
			}	
		}
		if(event.getSource().equals(runProgramItem))
		{
			runGame();
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
			move(x, y,width, height);
		}
		
		public void move(int x, int y, int width, int height) {
			this.setLocation(x, y);
			x_factor = ((double)this.getX())/((double)width);
			y_factor = ((double)this.getY())/((double)height);
			
		}
	}
	private void LoadGame(String diraction) {

		int Width =this.getWidth();
		int Height= this.getHeight();
		Game g = new Game(diraction);
		Map m = new Map(image,Width,Height,start,end);

		ArrayList<Fruit> fruits = g.getFruits();
		ArrayList<Packmen> pacmans = g.getPackmens();

		for (int i = 0; i < fruits.size(); i++) {
			Point3D pointGps = (Point3D) fruits.get(i).getGeom();
			Point3D pointPixel = m.toPixel(pointGps);
			FruitLabel fru = new FruitLabel(Width,Height,(int)pointPixel.x(),(int)pointPixel.y() );
			fruitsLabels.add(fru);
			System.out.println("////fruit(x,y) "+(int)pointPixel.x()+","+(int)pointPixel.y());
			repaint();

		}

		for (int i = 0; i < pacmans.size(); i++) {
			Point3D pointGps = (Point3D) pacmans.get(i).getGeom();
			Point3D pointPixel = m.toPixel(pointGps);
			PacmanLabel pac = new PacmanLabel(Width,Height,(int)pointPixel.x(),(int)pointPixel.y() );
			pacmansLabels.add(pac);
			repaint();
		}

		//repaint();
	}




	//when save press
	private Game createGame() {


		Map m = new Map(image,this.getWidth(),this.getHeight(),start,end);
		ArrayList<Fruit> fruits = new ArrayList<Fruit>();
		ArrayList<Packmen> pacmans = new ArrayList<Packmen>();
		for(int i=0 ; i<fruitsLabels.size() ; i++)
		{
			FruitLabel fru = fruitsLabels.get(i);
			Point3D p =m.toGPS(new Point3D(fru.getX() ,fru.getY(), 0));
			System.out.println("////x"+fru.getX()+"/////y"+fru.getY());
			System.out.println("////x"+p.x()+"/////y"+p.y());
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

		Game game = new Game(pacmans ,fruits);//create output.csv
		return game;
	}
	private void runGame()
	{
		this.run();

		//		int Width =this.getWidth();
		//		int Height= this.getHeight();
		//		Game game = createGame(); 
		//		ShortestPathAlgo alg = new ShortestPathAlgo(game);
		//		Path[] ans = alg.theShortRoute();
		//		RunGame r = new RunGame(ans,Width,Height);
		//		r.run();
	}

	@Override
	public void run() {

		int Width =this.getWidth();
		int Height= this.getHeight();
		Game game = createGame(); 
		ShortestPathAlgo alg = new ShortestPathAlgo(game);
		Path[] ans = alg.theShortRoute();
		System.out.println(ans[0]);
		System.out.println(ans[0].toStringTimes());

		System.out.println("run started");
		Map m = new Map(image,Width,Height,start,end);
		double timer = 0;

		while(timer <= ans[0].getTime().get(ans[0].size()-1))
		{
			timer += 10;
			Point3D pointGps = ans[0].getPointInTime(timer);
			System.out.println("the current point is"+pointGps);
			Point3D pointPixel = m.toPixel(pointGps);
			System.out.println("the current point in pixel is"+pointPixel);
			pacmansLabels.get(0).move((int)pointPixel.x(), (int)pointPixel.y(), Width, Height);;
			repaint();
			try {
				Thread.sleep(200);					
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
	}


//	class RunGame implements Runnable{
//
//		Path[] paths ;
//		int w;
//		int h;
//		public RunGame(Path[] p, int w, int h)
//		{
//			this.paths = p;
//			this.w = w;
//			this.h = h;
//		}
//		@Override
//		public void run() {
//			System.out.println("run started");
//			Map m = new Map(image,this.w,this.h,start,end);
//			double timer = 0;
//
//			while(timer <= paths[0].getTime().get(paths[0].size()-1))
//			{
//				timer += 10;
//				Point3D pointGps = paths[0].getPointInTime(timer);
//				System.out.println("the current point is"+pointGps);
//				Point3D pointPixel = m.toPixel(pointGps);
//				System.out.println("the current point in pixel is"+pointPixel);
//				pacmansLabels.get(0).setLocation(pointPixel.ix(), pointPixel.iy());
//				try {
//					Thread.currentThread().sleep(200);					
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				repaint();
//
//			}
//		}
//	}

}
