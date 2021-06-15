package paka1;


import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Prism extends JFrame implements ActionListener {

	static final int SLIDER_MIN = 1;
	static final int SLIDER_MAX = 10;
	static final int SLIDER_INIT = 5;
	
	static final int SLIDER_RAY_MIN = 0;
	static final int SLIDER_RAY_MAX = 90;
	static final int SLIDER_RAY_INIT = 60;
	
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
	JButton button3;
	
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

	static String animtext;
	String text_authors="autorzy: Maciej Kołakowski, Dominika Węgrzyniak";
	long sliderspeed;
	int lambda=500;
	static int animation_option=0;
	static int language_option=0;
	ResourceBundle rb = ResourceBundle.getBundle("lang/cfg/resource_bundle_en_EN");
	
	public Prism() throws HeadlessException {
		this.setSize(1500,800);
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
		labelsuwak = new JLabel(rb.getString("animacja"));
		panel1.add(labelsuwak);
		
		slider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
		panel1.add(slider, BorderLayout.PAGE_START);
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBackground(Color.LIGHT_GRAY);
		slider.addChangeListener(new SliderSpeed());
		
		panelA = new JPanel();					
		panel1.add(panelA);
		panelA.setBackground(Color.LIGHT_GRAY);
		
		label6 = new JLabel("Kąt załamania promienia [deg]: ");
		panel1.add(label6);
		label7 = new JLabel("0");
		panel1.add(label7);
		
		
		panelB = new JPanel();	
		panelB.setBackground(Color.LIGHT_GRAY);
		panel1.add(panelB);
		
		label1 = new JLabel("Kąt załamania pryzmatu [deg]:");
		panel1.add(label1);
		field1 = new JTextField("60");
		field1.addActionListener(new prismAngleListener());
		panel1.add(field1);
		
		label2 = new JLabel("Współczynnik załamania pryzmatu:");
		panel1.add(label2);
		field2 = new JTextField("1");
		field2.addActionListener(new npListener());
		panel1.add(field2);
		
		label3 = new JLabel("Współczynnik załamania ośrodka:");
		panel1.add(label3);
		field3 = new JTextField("1");
		field3.addActionListener(new noListener());
		panel1.add(field3);
		
		label4 = new JLabel("Kąt padania promienia [deg]:");
		panel1.add(label4);
		slider_ray = new JSlider(JSlider.HORIZONTAL, SLIDER_RAY_MIN, SLIDER_RAY_MAX, SLIDER_RAY_INIT);
		panel1.add(slider_ray, BorderLayout.PAGE_START);
		slider_ray.setMajorTickSpacing(30);
		slider_ray.setMinorTickSpacing(10);
		slider_ray.setPaintTicks(true);
		slider_ray.setPaintLabels(true);
		slider_ray.setBackground(Color.LIGHT_GRAY);
		slider_ray.addChangeListener(new SliderRay());
		
		wavelengthLabel = new JLabel("Długość fali [nm]:");
		panel1.add(wavelengthLabel);
		wavelengthField = new JTextField("500");
		panel1.add(wavelengthField);
		wavelengthField.addActionListener(new WaveColorListener());
		WaveColor();
		
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
		button2.addActionListener(new ResetListener());
		
		button3 = new JButton("Aktualizuj");
		button3.setBackground(Color.GREEN);
		button3.setForeground(Color.BLACK);
		panelD.add(button3);
		button3.addActionListener(new UpdateListener());
		
		
//Górny panel	
		startAnimation = new JButton ("Uruchom animację");
		panel3.add(startAnimation);
		startAnimation.addActionListener(new StartAnimationListener());
		
		stopAnimation = new JButton ("Zatrzymaj animację");
		panel3.add(stopAnimation);
		stopAnimation.setVisible(false);
		stopAnimation.addActionListener(new StopAnimationListener());
		
		changeLanguage = new JButton ("Change language");
		panel3.add(changeLanguage);
		changeLanguage.addActionListener(new LanguageListener());
		
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
		saveFile.addActionListener(new SaveListener());
		
		openFile= new JMenuItem("Wczytaj z pliku");
		openFile.addActionListener(new OpenListener());
		
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
					JLabel author = new JLabel (text_authors);
					author.setHorizontalAlignment(JLabel.CENTER);
					authorFrame.add(author);
					authorFrame.setVisible(true);
				}
			};
			
			public class prismAngleListener implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent arg0) {
					double angle = Double.parseDouble(field1.getText());
					panel_pryzmat.setPrismAngle(angle);
					repaint();
					
				}
				
			}
			
			public class npListener implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent e) {
					double n = Double.parseDouble(field2.getText());
					panel_pryzmat.setNp(n);
					
				}
				
			}
			
			public class noListener implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent e) {
					double n = Double.parseDouble(field3.getText());
					panel_pryzmat.setNo(n);
				}
				
			}
			
			public class SliderRay implements ChangeListener{
				
				@Override
				public void stateChanged(ChangeEvent arg0) 
				{
					
					double angle = slider_ray.getValue();		
					panel_pryzmat.setIncidenceAngle(angle);
					repaint();
				}
				
			}
			
			public class SliderSpeed implements ChangeListener{
				
				@Override
				public void stateChanged(ChangeEvent arg0) 
				{
					
					sliderspeed = 11-slider.getValue();		
					panel_pryzmat.setSpeed(sliderspeed);
					repaint();
				}
				
			}
			
			public class StartAnimationListener implements ActionListener{
				
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					try {
						panel_pryzmat.AnimationStop();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					label7.setText(String.valueOf(panel_pryzmat.getDelta()));
					startAnimation.setVisible(false);
					stopAnimation.setVisible(true);
					panel_pryzmat.AnimationStart();
					panel_pryzmat.Animation();
					ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
					scheduler.scheduleAtFixedRate((new Runnable() {

						@Override
						public void run() {
							if (panel_pryzmat.isDone == true) {
								stopAnimation.setVisible(false);
								startAnimation.setVisible(true);
								scheduler.shutdown();
							}
							
						}
						
					}), 0, 1, MILLISECONDS);
					
			
					
				}
				
			};
			
			public class StopAnimationListener implements ActionListener{
							
							@Override
					public void actionPerformed(ActionEvent arg0) 
					{
							startAnimation.setVisible(true);
							stopAnimation.setVisible(false);
							try {
								panel_pryzmat.AnimationStop();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
			
				}
			};
			
			public class SaveListener implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					String text = field1.getText() + "\n" + field2.getText() + "\n" + field3.getText() + "\n" + String.valueOf(slider_ray.getValue()) + "\n" + wavelengthField.getText();
					
					JOptionPane.showMessageDialog(rootPane, "Dane zostaną zapisane w kolejności: \n kąt załamania pryzmatu \n współczynnik załamania pryzmatu \n współczynnik załamania ośrodka \n kąt padania promienia \n długość fali");
					
					JFileChooser fc = new JFileChooser();
					fc.showSaveDialog(rootPane);
					BufferedWriter writer = null;
			        try {
			            writer = new BufferedWriter(new FileWriter(fc.getSelectedFile().getPath()));
			            writer.write(text);
			        } 
			        catch (Exception e1) {
			            e1.printStackTrace();
			        } 
			        finally {
			            try {
			                if (null != writer) {
			                    writer.close();
			                }
			            } catch (Exception e1) {
			                e1.printStackTrace();
			            }
			        }
					
				}
				
			}
			
			public class OpenListener implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(rootPane, "Dane zostaną zapisane w kolejności: \n kąt załamania pryzmatu \n współczynnik załamania pryzmatu \n współczynnik załamania ośrodka \n kąt padania promienia \n długość fali");

					try {
						JFileChooser fc = new JFileChooser();
						fc.showOpenDialog(rootPane);
						FileReader reader = new FileReader(fc.getSelectedFile().getAbsolutePath());
			            
						BufferedReader original = new BufferedReader(reader);
						String textLine= original.readLine();
						field1.setText(textLine);
						textLine= original.readLine();
						field2.setText(textLine);
						textLine= original.readLine();
						field3.setText(textLine);
						textLine= original.readLine();
						int n = Integer.parseUnsignedInt(textLine);
						slider_ray.setValue(n);
						textLine= original.readLine();
						wavelengthField.setText(textLine);
						original.close();
						}
					catch (IOException e) {
						System.out.println(e.getMessage());
						JOptionPane.showMessageDialog(rootPane, "Nie udało się wczytać pliku");
					}
					
				}
				
			}
			
			public class WaveColorListener implements ActionListener{
				
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					WaveColor();
				}
				
			}
			
			public class ResetListener implements ActionListener{
				
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{				
					startAnimation.setVisible(true);
					stopAnimation.setVisible(false);
					Reset();
					WaveColor();
				}
				
			}
			
			public class LanguageListener implements ActionListener{
				
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{				
					
					
					if(language_option==0)
					{	
						Locale.setDefault(new Locale("en", "EN"));
						rb = ResourceBundle.getBundle("lang/cfg/resource_bundle");
						label6.setText(rb.getString("kat_zal_pro"));
						label4.setText(rb.getString("kat_pad_pro"));
						label1.setText(rb.getString("kat_zal_pryz"));
						label2.setText(rb.getString("wsp_zal_pryz"));
						label3.setText(rb.getString("wsp_zal_os"));
						labelsuwak.setText(rb.getString("animacja"));
						wavelengthLabel.setText(rb.getString("dl_fal"));
						button1.setText(rb.getString("wyl"));
						button2.setText(rb.getString("res"));
						changeLanguage.setText(rb.getString("jez"));
						changeBackgroundColor.setText(rb.getString("tlo"));
						menu.setText(rb.getString("opc"));
						saveFile.setText(rb.getString("zapis"));
						openFile.setText(rb.getString("wczytaj"));
						authors.setText(rb.getString("info"));
						text_authors=rb.getString("autor");
						button3.setText(rb.getString("akt"));
						startAnimation.setText(rb.getString("ur_anim"));				
						stopAnimation.setText(rb.getString("zatrz_anim"));
						
						language_option=1;
					
					}
					else if(language_option==1)
					{
						Locale.setDefault(new Locale("pl", "PL"));
						rb = ResourceBundle.getBundle("lang/cfg/resource_bundle");
						label6.setText(rb.getString("kat_zal_pro"));
						label4.setText(rb.getString("kat_pad_pro"));
						label1.setText(rb.getString("kat_zal_pryz"));
						label2.setText(rb.getString("wsp_zal_pryz"));
						label3.setText(rb.getString("wsp_zal_os"));
						labelsuwak.setText(rb.getString("animacja"));
						wavelengthLabel.setText(rb.getString("dl_fal"));
						button1.setText(rb.getString("wyl"));
						button2.setText(rb.getString("res"));
						changeLanguage.setText(rb.getString("jez"));
						changeBackgroundColor.setText(rb.getString("tlo"));
						menu.setText(rb.getString("opc"));
						saveFile.setText(rb.getString("zapis"));
						openFile.setText(rb.getString("wczytaj"));
						authors.setText(rb.getString("info"));
						text_authors=rb.getString("autor");
						button3.setText(rb.getString("akt"));
						startAnimation.setText(rb.getString("ur_anim"));				
						stopAnimation.setText(rb.getString("zatrz_anim"));
						
						
						language_option=0;
						
					}
					
					
				}
				
			}
			
			public class UpdateListener implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent e) {
					Update();				
				}
				
			}
			
			void Update()
			{
				panel_pryzmat.init();
				
				startAnimation.setVisible(true);
				stopAnimation.setVisible(false);
				
				WaveColor();
				
				double n1 = Double.parseDouble(field3.getText());
				panel_pryzmat.setNo(n1);
				
				double n2 = Double.parseDouble(field2.getText());
				panel_pryzmat.setNp(n2);
				
				double angle = Double.parseDouble(field1.getText());
				panel_pryzmat.setPrismAngle(angle);
				repaint();
			}
			
			void WaveColor()
			{
				if(Double.parseDouble(wavelengthField.getText())<=200)
				{
					panel_pryzmat.setColor(Color.BLACK);
				}
				
				if(Double.parseDouble(wavelengthField.getText())<=400 && Double.parseDouble(wavelengthField.getText())>200)
				{
					panel_pryzmat.setColor(Color.magenta);
				}
				
				if(Double.parseDouble(wavelengthField.getText())<=450 && Double.parseDouble(wavelengthField.getText())>400)
				{
					panel_pryzmat.setColor(Color.blue);
				}
				
				if(Double.parseDouble(wavelengthField.getText())<=500 && Double.parseDouble(wavelengthField.getText())>450)
				{
					panel_pryzmat.setColor(Color.cyan);
				}
				
				if(Double.parseDouble(wavelengthField.getText())<=570 && Double.parseDouble(wavelengthField.getText())>500)
				{
					panel_pryzmat.setColor(Color.green);
				}
				
				if(Double.parseDouble(wavelengthField.getText())<=600 && Double.parseDouble(wavelengthField.getText())>570)
				{
					panel_pryzmat.setColor(Color.yellow);
				}
				
				if(Double.parseDouble(wavelengthField.getText())<=800 && Double.parseDouble(wavelengthField.getText())>600)
				{
					panel_pryzmat.setColor(Color.red);
				}
				
				if(Double.parseDouble(wavelengthField.getText())>800)
				{
					panel_pryzmat.setColor(Color.black);
				}
			}
			
			void Reset()
			{
				field1.setText("60");
				field2.setText("1");
				field3.setText("1");
				wavelengthField.setText("500");	
				Update();
			}
			
			/*public static void setAnimText()
			{
				if(language_option==0)
				{
				startAnimation.setText("Uruchom animację");
				}
				else if(language_option==1)
				{
				startAnimation.setText("Start animation");
				}
				animation_option=0;
			}*/
			
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Prism okno1 = new Prism();
		okno1.setVisible(true);	

	}

}
