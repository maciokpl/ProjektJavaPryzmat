package paka1;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Prism extends JFrame implements ActionListener {

	static final int SLIDER_MIN = 3;
	static final int SLIDER_MAX = 29;
	static final int SLIDER_INIT = 3;
	
	static final int SLIDER_RAY_MIN = 0;
	static final int SLIDER_RAY_MAX = 180;
	static final int SLIDER_RAY_INIT = 0;
	
	JSlider slider;
	JSlider slider_ray;
	
	JTextField field1;
	JTextField field2;
	JTextField field3;
	JTextField field4;
	JTextField wavelengthField;
	
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	JLabel label5;
	JLabel label6;
	JLabel label7;
	JLabel labelsuwak;
	JLabel wavelengthLabel;
	
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel panel4;
	JPanel panel5;
	DrawPrismPanel panel_pryzmat;
	
	JPanel panelA;
	JPanel panelB;
	JPanel panelC;
	JPanel panelD;
	
	JButton button1;
	JButton button2;
	
	JButton startAnimation;
	JButton changeLanguage;
	JButton stopAnimation;
	JButton changeBackgroundColor;
	
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem saveFile;
	JMenuItem openFile;
	JMenuItem authors;
	
	ExecutorService exec;

	
	
	public Prism() throws HeadlessException {
		this.setSize(800,800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		exec = Executors.newFixedThreadPool(2);
		this.setLayout(new BorderLayout());
//Ustawienie paneli	
		panel1 = new JPanel();					
		this.add(panel1, BorderLayout.LINE_START);
		panel1.setLayout(new GridLayout(17,1));
		panel1.setBackground(Color.LIGHT_GRAY);
		panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		panel2 = new JPanel();					
		this.add(panel2, BorderLayout.LINE_END);
		panel2.setLayout(new GridLayout(12,1));
		
		panel3 = new JPanel();					
		this.add(panel3, BorderLayout.PAGE_START);
		panel3.setBackground(Color.LIGHT_GRAY);
		panel3.setLayout(new GridBagLayout());
		panel3.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		panel4 = new JPanel();					
		this.add(panel4, BorderLayout.PAGE_END);
//stworzony panel do rysowania pryzmatu i promienia		
		panel_pryzmat = new DrawPrismPanel();					
		this.add(panel_pryzmat, BorderLayout.CENTER);
		panel_pryzmat.setBackground(Color.white);
//Lewy panel		
		labelsuwak = new JLabel("Prędkość animacji");
		panel1.add(labelsuwak);
		
		slider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
		panel1.add(slider, BorderLayout.PAGE_START);
		slider.setMajorTickSpacing(5);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBackground(Color.LIGHT_GRAY);
		
		panelA = new JPanel();					
		panel1.add(panelA);
		panelA.setBackground(Color.LIGHT_GRAY);
		
		label6 = new JLabel("Kąt załamania promienia: ");
		panel1.add(label6);
		label7 = new JLabel("0");
		panel1.add(label7);
		
		
		panelB = new JPanel();	
		panelB.setBackground(Color.LIGHT_GRAY);
		panel1.add(panelB);
		
		label1 = new JLabel("Kąt załamania pryzmatu:");
		panel1.add(label1);
		field1 = new JTextField(" ");
		panel1.add(field1);
		
		label2 = new JLabel("Współczynnik załamania pryzmatu:");
		panel1.add(label2);
		field2 = new JTextField(" ");
		panel1.add(field2);
		
		label3 = new JLabel("Współczynnik załamania ośrodka:");
		panel1.add(label3);
		field3 = new JTextField(" ");
		panel1.add(field3);
		
		label4 = new JLabel("Kąt padania promienia:");
		panel1.add(label4);
		slider_ray = new JSlider(JSlider.HORIZONTAL, SLIDER_RAY_MIN, SLIDER_RAY_MAX, SLIDER_RAY_INIT);
		panel1.add(slider_ray, BorderLayout.PAGE_START);
		slider_ray.setMajorTickSpacing(30);
		slider_ray.setMinorTickSpacing(10);
		slider_ray.setPaintTicks(true);
		slider_ray.setPaintLabels(true);
		slider_ray.setBackground(Color.LIGHT_GRAY);
		slider_ray.addChangeListener(new SliderRay());
		
		wavelengthLabel = new JLabel("Długość fali:");
		panel1.add(wavelengthLabel);
		wavelengthField = new JTextField(" ");
		panel1.add(wavelengthField);
		
		panelD = new JPanel();		
		panelD.setLayout(new FlowLayout());
		panelD.setBackground(Color.LIGHT_GRAY);
		panel1.add(panelD);
		
		button1 = new JButton("Wyłącz");
		button1.setBackground(Color.RED);
		button1.setForeground(Color.BLACK);
		panelD.add(button1);	
		ActionListener listener_wylacz = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(1);
			}	
		};
		button1.addActionListener(listener_wylacz);
		
		button2 = new JButton("Resetuj");
		button2.setBackground(Color.YELLOW);
		button2.setForeground(Color.BLACK);
		panelD.add(button2);
		
		
//Górny panel	
		startAnimation = new JButton ("Uruchom animację");
		panel3.add(startAnimation);
		startAnimation.addActionListener(new AnimationListener());
		
		changeLanguage = new JButton ("Change language");
		panel3.add(changeLanguage);
		
		
		changeBackgroundColor = new JButton("Zmień kolor tła");
		ActionListener chooseColorListener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JColorChooser tcc = new JColorChooser();
				
				ActionListener cancelListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
							tcc.setVisible(false);
					}
				};
						
				ActionListener okListener = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Color color = tcc.getColor();
						panel_pryzmat.setBackground(color);
					}
				};
				JDialog dialog = JColorChooser.createDialog(changeBackgroundColor, "title", true, tcc, okListener, cancelListener);
				dialog.setVisible(true);
			}
		};
		changeBackgroundColor.addActionListener(chooseColorListener);
		panel3.add(changeBackgroundColor);
//Menu
		menuBar = new JMenuBar();
		menu = new JMenu("Opcje");
		saveFile=new JMenuItem("Zapisz do pliku");
		openFile= new JMenuItem("Wczytaj z pliku");
		authors = new JMenuItem("Informacje o programie");
		authors.addActionListener(new authorsListener());
		menu.add(saveFile);
		menu.add(openFile);
		menu.add(authors);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}
//Listenery
			public class authorsListener implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JFrame authorFrame = new JFrame();
					authorFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					authorFrame.setSize(400, 200);
					JLabel author = new JLabel ("autorzy: Maciej Kołakowski, Dominika Węgrzyniak");
					author.setHorizontalAlignment(JLabel.CENTER);
					authorFrame.add(author);
					authorFrame.setVisible(true);
				}
			};
			
			public class SliderRay implements ChangeListener{
				
				@Override
				public void stateChanged(ChangeEvent arg0) 
				{
					
					double angle = slider_ray.getValue();		
					panel_pryzmat.setAngle(angle);
					repaint();
				}
				
			}
			
			public class AnimationListener implements ActionListener{
				
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					panel_pryzmat.Animation();
					exec.shutdown();
				}
				
			}
			
			
			
			

			
			
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Prism okno1 = new Prism();
		okno1.setVisible(true);	

	}

}
