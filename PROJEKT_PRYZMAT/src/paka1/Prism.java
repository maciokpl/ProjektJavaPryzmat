package paka1;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Prism extends JFrame implements ActionListener {

	static final int SLIDER_MIN = 3;
	static final int SLIDER_MAX = 29;
	static final int SLIDER_INIT = 3;
	
	JSlider slider;
	
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

	
	public Prism() throws HeadlessException {
		this.setSize(800,800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
//Ustawienie paneli	
		panel1 = new JPanel();					
		this.add(panel1, BorderLayout.LINE_START);
		panel1.setLayout(new GridLayout(18,1));
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
		
		panel5 = new JPanel();					
		this.add(panel5, BorderLayout.CENTER);
		panel5.setBackground(Color.white);
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
		field4 = new JTextField(" ");
		panel1.add(field4);
		
		wavelengthLabel = new JLabel("Długość fali:");
		panel1.add(wavelengthLabel);
		wavelengthField = new JTextField(" ");
		panel1.add(wavelengthField);
		
		panelC = new JPanel();	
		panelC.setBackground(Color.LIGHT_GRAY);
		panel1.add(panelC);
		
		panelD = new JPanel();		
		panelD.setLayout(new FlowLayout());
		panelD.setBackground(Color.LIGHT_GRAY);
		panel1.add(panelD);
		
		button1 = new JButton("Wyłącz");
		button1.setBackground(Color.RED);
		button1.setForeground(Color.BLACK);
		panelD.add(button1);	
		
		button2 = new JButton("Resetuj");
		button2.setBackground(Color.YELLOW);
		button2.setForeground(Color.BLACK);
		panelD.add(button2);	
//Górny panel	
		startAnimation = new JButton ("Uruchom animację");
		panel3.add(startAnimation);
		
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
						panel5.setBackground(color);
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
		authors.addActionListener(authorsListener);
		menu.add(saveFile);
		menu.add(openFile);
		menu.add(authors);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}
//Listenery
			ActionListener authorsListener = new ActionListener() {
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Prism okno1 = new Prism();
		okno1.setVisible(true);	

	}

}
