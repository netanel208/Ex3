package GUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Coords.MyCoords;
import File_format.Convert2Kml;
import File_format.Map;
import GIS.Fruit;
import GIS.Game;
import GIS.Packmen;
import Geom.Point3D;
import Solution.Path;
import Solution.ShortestPathAlgo;

import javax.swing.JFileChooser;

/**
 * This class represents an application of the pacman game to the user.
 * @author Netanel
 * @author Carmel
 *
 */
public class MyFrame extends JFrame implements MouseListener,ActionListener,ItemListener {


	Image image;                                        //screen image
	Image packmenIcon;                                  //packmen icon
	MenuBar menuBar;                                    
	Menu elementItem;
	Menu fileItem;
	Menu runItem;
	MenuItem packmenItem;
	MenuItem fruitItem;
	MenuItem addItem;
	MenuItem saveItem;
	MenuItem runProgramItem;
	MenuItem kmlItem;
	int x = -1;                                         //location of pixel(x,y)
	int y = -1;                                         //location of pixel(x,y)
	int width = -1;
	int height = -1;
	boolean fruit_addable = false;
	boolean packmen_addable = false;
	boolean runTheGame = false;
	boolean saveDone = false;
	boolean threadTerminated = false;                   //An indication that thread has been terminated
	ArrayList<FruitLabel> fruitsLabels;                 //collection of Jlabel(any element represent fruit in screen)
	ArrayList<PacmanLabel> pacmansLabels;               //collection of Jlabel(any element represent pacman in screen)
	Game game;           
	ShortestPathAlgo alg;
	Path[] ans;          

	private JTextField filename = new JTextField();

	final Point3D start = new Point3D(32.10571,35.20237,0);
	final Point3D rightup = new Point3D(32.10571,35.21239);
	final Point3D leftdown = new Point3D(32.10189,35.20237);
	final Point3D end = new Point3D(32.10189,35.21239,0);
	MyCoords c = new MyCoords();

	/**
	 * 
	 */
	private MyFrame()
	{
		initGUI();
		this.addMouseListener(this);
	}

	/**
	 * Initializes class variables
	 */
	private void initGUI() 
	{
		image = Toolkit.getDefaultToolkit().getImage("Ariel1.png");
		packmenIcon = Toolkit.getDefaultToolkit().getImage("pacman.png");
		Container cp = getContentPane();
		filename.setEditable(false);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 1));
		p.add(filename);
		cp.add(p, BorderLayout.NORTH);

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

		kmlItem = new MenuItem("Convert to kml");
		kmlItem.addActionListener(this);

		saveItem = new MenuItem("Save");
		saveItem.addActionListener(this);

		runProgramItem = new MenuItem("Run");
		runProgramItem.addActionListener(this);

		menuBar.add(fileItem);
		fileItem.add(addItem);
		fileItem.add(saveItem);
		fileItem.add(kmlItem);

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

	/**
	 * {@inheritDoc}
	 */
	public void paint(Graphics g){
		width = this.getWidth(); 
		height = this.getHeight();
		g.drawImage(image, 0, 0, width, height, this);
		if(!fruitsLabels.isEmpty())
		{
			for(int i=0 ; i<fruitsLabels.size() ; i++)//draw all fruits label if there is a change or is said so(by repaint) 
			{
				FruitLabel fruit = fruitsLabels.get(i);
				int x_fruit = fruitsLabels.get(i).getX();
				int y_fruit = fruitsLabels.get(i).getY();
				x_fruit = (int)(((double)width)*((double)fruit.x_factor));//The position X of the fruit relative to the screen size
				y_fruit = (int)(((double)height)*((double)fruit.y_factor));//The position Y of the fruit relative to the screen size
				fruit.setLocation(x_fruit, y_fruit);
				if(!fruit.isEaten)//If the fruit is eaten by the pacman on you will paint it more
				{
					g.setColor(Color.WHITE);
					g.fillOval(x_fruit, y_fruit, 20, 20);
				}
			}
		}
		if(!pacmansLabels.isEmpty())
		{
			for(int i=0 ; i<pacmansLabels.size() ;i++)//draw all pacman label if there is a change or is said so(by repaint)
			{
				PacmanLabel pacman = pacmansLabels.get(i);
				int x_pacman = pacmansLabels.get(i).getX();
				int y_pacman = pacmansLabels.get(i).getY();
				x_pacman = (int)(((double)width)*((double)pacman.x_factor));//The position X of the pacman relative to the screen size
				y_pacman = (int)(((double)height)*((double)pacman.y_factor));//The position Y of the pacman relative to the screen size
				pacman.setLocation(x_pacman, y_pacman);
				g.drawImage(packmenIcon,x_pacman, y_pacman, 28, 28,this);
			}
		}

		//if running algorithm done, draw the paths
		if (threadTerminated) {
			drawLines(g);
		}



	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(packmenItem))//If pressed on Pacman, you will avoid adding Fruit
		{
			packmen_addable = true;
			fruit_addable = false;
		}
		if(event.getSource().equals(fruitItem))//If pressed on Fruit, you will avoid adding Pacman
		{
			packmen_addable = false;
			fruit_addable = true;
		}
		if(event.getSource().equals(saveItem))//If pressed on Save
		{
			createGame();
			saveDone = true;
		}
		if(event.getSource().equals(addItem))//If pressed on Add csv file
		{
			JFileChooser c = new JFileChooser();

			// Demonstrate "Open" dialog:
			int rVal = c.showOpenDialog(MyFrame.this);
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

		if(event.getSource().equals(kmlItem)) {//If pressed on convert to kml file after run the game

			if (runTheGame) {

				Convert2Kml k = new Convert2Kml(game,ans);
				try {
					k.convert();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		if(event.getSource().equals(runProgramItem))//If press on Run (after saving the game)
		{
			runTheGame = true;
			if(!fruitsLabels.isEmpty() && !pacmansLabels.isEmpty())
			{
				alg = new ShortestPathAlgo(game);   
				ans = alg.theShortRoute();          //array of paths 

				runGame();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		x = arg0.getX();
		y = arg0.getY();

		//if select first Fruit and pressed on screen 
		if(fruit_addable)
		{
			fruitsLabels.add(new FruitLabel(this.getWidth(), this.getHeight(), x, y));
		}

		//if select first Pacman and pressed on screen
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

	/**
	 * The method receives a CSV file address, and extracts necessary data from it to build a game.
	 * The method updates the appropriate drawings of fruits and pacmans on the screen.
	 * @param diraction
	 */
	//when Add csv pressed
	public void LoadGame(String diraction) {

		int Width =this.getWidth();
		int Height= this.getHeight();
		game = new Game(diraction);
		Map m = new Map(image,Width,Height,start,end);

		ArrayList<Fruit> fruits = game.getFruits();
		ArrayList<Packmen> pacmans = game.getPackmens();

		for (int i = 0; i < fruits.size(); i++) {
			//add fruit in right pixel
			Point3D pointGps = (Point3D) fruits.get(i).getGeom();
			Point3D pointPixel = m.toPixel(pointGps);
			FruitLabel fru = new FruitLabel(Width,Height,(int)pointPixel.x(),(int)pointPixel.y() );
			fruitsLabels.add(fru);
			repaint();
		}

		for (int i = 0; i < pacmans.size(); i++) {
			//add pacman in right pixel
			Point3D pointGps = (Point3D) pacmans.get(i).getGeom();
			Point3D pointPixel = m.toPixel(pointGps);
			PacmanLabel pac = new PacmanLabel(Width,Height,(int)pointPixel.x(),(int)pointPixel.y() );
			pacmansLabels.add(pac);
			repaint();
		}
	}

	/**
	 * The method takes data that the user puts on the screen and converts them into the game.
	 * Also creates a CSV file accordingly(output.csv).
	 */
	//when save pressed
	public void createGame() {

		Map m = new Map(image,this.getWidth(),this.getHeight(),start,end);
		ArrayList<Fruit> fruits = new ArrayList<Fruit>();
		ArrayList<Packmen> pacmans = new ArrayList<Packmen>();

		//convert fruit-Label to fruit-game.
		for(int i=0 ; i<fruitsLabels.size() ; i++)
		{
			FruitLabel fru = fruitsLabels.get(i);
			Point3D p =m.toGPS(new Point3D(fru.getX() ,fru.getY(), 0));//pixel to gps
			String[] lineF = {"F",i+"",p.x()+"", p.y()+"", p.z()+"", "1"};
			fruits.add(new Fruit(lineF));
		}

		//convert pacman-label to pacman-game
		for(int i=0 ; i<pacmansLabels.size() ; i++)
		{
			PacmanLabel pac = pacmansLabels.get(i);
			Point3D p =m.toGPS(new Point3D(pac.getX() ,pac.getY(), 0));//pixel to gps
			String[] lineP = {"P",i+"",p.x()+"", p.y()+"", p.z()+"", "1","1"};
			pacmans.add(new Packmen(lineP));
		}

		game = new Game(pacmans ,fruits);//override old object and create output.csv
	}

	/**
	 * The method runs the game (thread)
	 */
	//when Run pressed
	public void runGame()
	{
		runTheGame = true;
		Thread th = new Thread(new Runnable() {
			public void run() {
				runAlgo();
			}
		});
		th.start();
	}

	/**
	 * The method is part of the thread's run method.
	 * It runs the game and causes the pacman to move and "eat" the fruits.
	 * The Pacmans move according to their position at that point in time.
	 */
	public void runAlgo() {

		Map m = new Map(image,width,height,start,end);
		double timer = 0;                     //current time(increases by iteration)
		double totalTime = alg.getTotalTime();// total time of all paths

		while(timer <= totalTime)
		{
			for(int i=0 ; i<ans.length ; i++)
			{
				double speed = game.getPackmens().get(i).getSpeed();
				double partTime = 1.0/speed;

				// If the speed is greater than 1, you should check every part of the second that the pacman went what he did
				for(int k=0 ; k<speed ; k++)
				{
					Point3D pointGps = ans[i].getPointInTime(timer+(partTime*k));
					Point3D pointPixel = m.toPixel(pointGps);


					//if the fruit in the radius eat
					for(int j=0 ; j<fruitsLabels.size() ; j++)
					{
						Point3D pac = new Point3D(pacmansLabels.get(i).getX(), pacmansLabels.get(i).getY());
						Point3D fru = new Point3D(fruitsLabels.get(j).getX(), fruitsLabels.get(j).getY());
						Point3D fru_gps = ((Point3D)game.getFruits().get(j).getGeom());
						if(c.distance3d(pointGps, fru_gps) <= game.getPackmens().get(i).getRadius() || m.distance(pac, fru) <= game.getPackmens().get(i).getRadius())
						{
							pacmansLabels.get(i).move((int)pac.x(), (int)pac.y(), width, height);
							fruitsLabels.get(j).isEaten = true;//if the fruit is in the pacman path then eat it
						}
					}
					pacmansLabels.get(i).move((int)pointPixel.x(), (int)pointPixel.y(), width, height);
					repaint();
				}
			}
			try {
				Thread.sleep(10);					
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			timer = timer+1;
		}
		threadTerminated = true;
		repaint();
	}

	/**
	 * this method draw lines that represent paths of solution of algorithem
	 * @param g    graphics of this
	 */
	public void drawLines(Graphics g) {
		Map m = new Map(image,width,height,start,end);
		Line2D lin;


		for (int i = 0; i < ans.length; i++) {

			Path path = ans[i];


			Color ran =new Color((int)(Math.random() * 0x1000000));
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(ran);


			for (int j = 0; j < path.size()-1; j++) {
				Point3D p0 = m.toPixel(path.get(j));
				Point3D p1 = m.toPixel(path.get(j+1));

				lin = new Line2D.Float(p0.ix(), p0.iy(), p1.ix(), p1.iy());

				g2.setStroke(new BasicStroke(3));
				g2.draw(lin);
			}
		}
	}

	public static void main(String[] args) {
		MyFrame window = new MyFrame();
		window.setVisible(true);
		window.setSize(1433, 642);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This class represents fruit as a frame label with height and width data. 
	 * @author Netanel
	 * @author Carmel
	 *
	 */
	class FruitLabel extends JLabel{

		double x_factor;//The relative portion of a point from the width of the frame
		double y_factor;//The relative part of a point from the height of the frame
		boolean isEaten;//if the pacman eat this fruit or not
		/**
		 * The constructor placed the point in the expected place 
		 * and calculated the position ratio of the point in the frame.
		 * @param width
		 * @param height
		 * @param x
		 * @param y
		 */
		public FruitLabel(int width,int height,int x,int y)
		{
			isEaten = false;
			this.setLocation(x, y);
			x_factor = ((double)this.getX())/((double)width);
			y_factor = ((double)this.getY())/((double)height);
		}
	}

	/**
	 * This class represents pacman as a frame label with height and width data.
	 * @author Netanel
	 * @author Carmel
	 *
	 */
	class PacmanLabel extends JLabel{

		double x_factor;
		double y_factor;

		public PacmanLabel(int width,int height,int x,int y)
		{
			move(x, y,width, height);
		}

		/**
		 * This method placed the point in the expected place 
		 * and calculated the position ratio of the point in the frame.
		 * @param x
		 * @param y
		 * @param width
		 * @param height
		 */
		public void move(int x, int y, int width, int height) {
			this.setLocation(x, y);
			x_factor = ((double)this.getX())/((double)width);
			y_factor = ((double)this.getY())/((double)height);

		}
	}
}
