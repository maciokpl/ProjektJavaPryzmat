package paka1;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DrawPrismPanel extends JPanel{
	
		double prismAngle=Math.PI/3;
		double incidenceAngle=Math.PI/3;
		double beta2;
		double beta1;
		double alpha2;
		double delta;
		double alpha_gr1=180;
		double alpha_gr2=180;
		double nP=1;
		double nO=1;
		long speed=5;
		int[] triX={256,400,544}; 
        int[] triY={400,150,400};
        Color color = Color.BLUE;
        int iX1[];
        int iY1[];
        int iX2[];
        int iY2[];
        int iX3[];
        int iY3[];
        
        Graphics2D g_tri;
		Graphics2D g_ray1;
		Graphics2D g_ray2;
		Graphics2D g_ray3;
        
        Ray ray2; 
        Ray ray3;
        
        Boolean isAnimation = false;
    	Boolean isDone = false;
    	Boolean isRay2 = true;
        
        DrawPrismPanel(){
        	init();
        }
        
//ustawianie potrzebnych wielkosci
        public void init() {
        	isRay2 = true;
        	isAnimation = false;
        	delta=0;
        	
        	iX1 = new int[2];
  	        iY1 = new int[2];
  	        iX2 = new int[2];
  	        iY2 = new int[2];
  	        iX3 = new int[2];
  	        iY3 = new int[2];
  	        
  	        int x = (int) ((int) 250 * Math.tan(prismAngle/2));
			triX[0] = 600-x;
			triX[1] = 600;
			triX[2] = 600+x;
  	        
  	        beta1 = Math.asin(nO*Math.sin(incidenceAngle)/nP);
  	        
			if(nO>nP) {
				alpha_gr1 = Math.asin(nP/nO);
			}
			else {
				alpha_gr1 = 180;
			}
  	        
			if(prismAngle - incidenceAngle*nO/nP <= Math.PI/3) {
				beta2 = prismAngle - beta1;
				alpha2 = Math.asin(nP*Math.sin(beta2)/nO);
				delta = incidenceAngle + alpha2 - prismAngle;
				
	  	    	iX1[0] = (int) (triX[1]-125*Math.tan(prismAngle/2) - 150);
	          	iY1[0] = (int) (275 - 150*Math.tan(prismAngle/2 - incidenceAngle));
	          	iX1[1] = (int) (triX[1]-125*Math.tan(prismAngle/2));
	          	iY1[1] = 275;
	          	
	          	iX2[0] = iX1[1];
	          	iY2[0] = iY1[1];
	          	iX2[1] = (int) (iX2[0] + (250*Math.sin(prismAngle)/(2*Math.cos(prismAngle/2)*Math.sin(Math.PI/2 - beta2)))*Math.sin(Math.PI/2 - beta2 + prismAngle/2));
	          	iY2[1] = (int) (iY2[0] + (250*Math.sin(prismAngle)/(2*Math.cos(prismAngle/2)*Math.sin(Math.PI/2 - beta2)))*Math.cos(Math.PI/2 - beta2 + prismAngle/2));
	          	
	          	iX3[0] = iX2[1];
	          	iY3[0] = iY2[1];
	          	iX3[1] = (int) (iX3[0] + 200); 
	          	iY3[1] = (int) (iY3[0] + 200* Math.tan(prismAngle/2 - alpha2)); 
	          	
	          	ray2 = new Ray(iX2[0], iX2[1], iY2[0], iY2[1], -prismAngle/2+beta1, color);
	          	ray3 = new Ray(iX3[0], iX3[1], iY3[0], iY3[1], prismAngle/2-alpha2, color);
			}
			else {
				iX1[0] = (int) (triX[1]-125*Math.tan(prismAngle/2) - 150);
	          	iY1[0] = (int) (275 - 150*Math.tan(prismAngle/2 - incidenceAngle));
	          	iX1[1] = (int) (triX[1]-125*Math.tan(prismAngle/2));
	          	iY1[1] = 275;
	          	
	          	iX2[0] = iX1[1];
	          	iY2[0] = iY1[1];
	          	iX2[1] = (int) (iX1[1] + 125/Math.tan(prismAngle/2 - beta1));
	          	iY2[1] = 400;
	          	
	          	beta2 = Math.PI/2 - prismAngle/2 + beta1;
	          	alpha2 = Math.asin(nP*Math.sin(beta2)/nO);
				delta = incidenceAngle - beta1 - beta2 + alpha2;
	          	
	          	iX3[0] = iX2[1];
	          	iY3[0] = iY2[1];
	          	iX3[1] = (int) (iX3[0] + 200); 
	          	iY3[1] = (int) (iY3[0] - 200/Math.tan(alpha2));
	          	
	          	ray2 = new Ray(iX2[0], iX2[1], iY2[0], iY2[1], -prismAngle/2+beta1, color);
	          	ray3 = new Ray(iX3[0], iX3[1], iY3[0], iY3[1], -Math.PI/2+alpha2, color);
			}
        }
        
		public void paint(Graphics g)
	    { 
			super.paint(g);
			Graphics2D g_tri = (Graphics2D)g;
			Graphics2D g_ray1 = (Graphics2D)g;
			Graphics2D g_ray2 = (Graphics2D)g;
			Graphics2D g_ray3 = (Graphics2D)g;
	        
	        g_tri.setColor(Color.RED); 
	        g_tri.translate(-100, 20);
	        g_tri.setStroke(new BasicStroke(5));
	        g_tri.drawPolygon(triX, triY, 3); 
	        
	        g_ray1.setColor(color);
	        g_ray1.setStroke(new BasicStroke(4));
	        g_ray1.drawPolygon(iX1, iY1, 2); 
	        if (ray2.isDrawn==false) {
	        	g_ray3.setColor(Color.RED);
	        	g_ray3.drawLine(ray3.xStart, ray3.yStart, ray3.currentX, ray3.currentY);
	        	g_ray2.setColor(color);
	        	g_ray2.drawLine(ray2.xStart, ray2.yStart, ray2.currentX, ray2.currentY);
	        	
	        }
	        else {
	        	g_ray2.drawLine(ray2.xStart, ray2.yStart, ray2.currentX, ray2.currentY);
	        	g_ray3.drawLine(ray3.xStart, ray3.yStart, ray3.currentX, ray3.currentY); 
	        }
	        
			if(isAnimation == true) {
				g_ray1.setColor(color);
		        if (ray2.isDrawn==false) {
		        	g_ray2.drawLine(ray2.xStart, ray2.yStart, ray2.currentX, ray2.currentY);  
		        }
		        
		        if (ray2.isDrawn==true && isRay2 == true) {
		        	g_ray2.drawLine(ray2.xStart, ray2.yStart, ray2.xEnd, ray2.yEnd); 
		        	g_ray3.drawLine(ray3.xStart, ray3.yStart, ray3.currentX, ray3.currentY); 
		        }
		    }
	    }
		
		
		public void setColor(Color wavecolor)
		{
			color=wavecolor;
			repaint();
		}
		
		
				
		void Animation() {
				if(incidenceAngle>=alpha_gr1) {
					delta = 0;
					JOptionPane.showMessageDialog(getRootPane(), Prism.Refl());
					return;
				}
				if(prismAngle - incidenceAngle*nO/nP <= Math.PI/3) {
					beta2 = prismAngle - beta1;
					alpha2 = Math.asin(nP*Math.sin(beta2)/nO);
					delta = incidenceAngle + alpha2 - prismAngle;
					
					if(nP>nO) {
						alpha_gr2 = Math.asin(nO/nP);
						if (alpha_gr2<beta2) {
							isRay2 = false;
						}
					}
				}
			
				else {
					beta2 = Math.PI/2 - prismAngle/2 - beta1;
		          	alpha2 = Math.asin(nP*Math.sin(beta2)/nO);
					delta = incidenceAngle + alpha2 - prismAngle;

					if(nP>nO) {
						alpha_gr2 = Math.asin(nO/nP);
						if (alpha_gr2<(Math.PI/2 - prismAngle/2 + beta1)) {
							isRay2 = false;
						}
					}
				}
				
				if (isDone==true) {
					init();
					isDone=false;
				}
				ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
				scheduler.scheduleAtFixedRate((new Runnable() {

					@Override
					public void run() {
							if(ray2.isDrawn==false) ray2.run();
							if (ray2.isDrawn==true && isRay2 == false) {
					        	JOptionPane.showMessageDialog(getRootPane(), Prism.Refl());
					        	delta = 0;
					        	scheduler.shutdownNow();
							}
							if(ray2.isDrawn==true) ray3.run();
							if(ray3.isDrawn == true) {
								scheduler.shutdown();
								isDone=true;
							}
							if (isAnimation == true)
							repaint();
							else {
								try {
									this.wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
					}
					
				}), 0, speed, MILLISECONDS);
				isAnimation = true;
		}	
		
		public void AnimationStop() throws InterruptedException
		
		{
			isAnimation = false;
			repaint();
		}
		
		public void AnimationStart() 
		{
			isAnimation=true;
		}
		
		public void setPrismAngle(double angle) 
		{
			isAnimation = false;
			this.prismAngle = angle*Math.PI/180;
			init();		
		}
		
		public void setNo(double no) {
			nO=no;
			
		}
		
		public void setNp (double np) {
			nP = np;
		}
		
		public void setIncidenceAngle(double ang) {
			isAnimation = false;
			this.incidenceAngle = ang*Math.PI/180;
			init();
		}
		
		public void setSpeed(long speed)
		{
			this.speed = speed;
		}
		
		public double getPrismAngle()
		{
			return prismAngle;
		}
		
		public double getIncidenceAngle() {
			return incidenceAngle;
		}
		
		public double getDelta() {
			return delta*180/Math.PI;
		}
}





