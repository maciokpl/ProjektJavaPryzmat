package paka1;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.JPanel;

public class DrawPrismPanel extends JPanel{
	
		private static final boolean isAnim = false;
		double prismAngle=Math.PI/3;
		double incidenceAngle=Math.PI/3;
		double beta2;
		double beta1;
		double alpha2;
		double delta;
		double nP=1;
		double nO=1;
		double speed=0.01;
		int[] triX={256,400,544}; 
        int[] triY={400,150,400};
        Color color = Color.BLUE;
        int iX1[];
        int iY1[];
        int iX2[];
        int iY2[];
        int iX3[];
        int iY3[];
        
        Ray ray2; 
        Ray ray3;
        
        Boolean isAnimation = false;
        
        DrawPrismPanel(){
        	init();
        }
        
//ustawianie potrzebnych wielkosci
        public void init() {
        	isAnimation = false;
        	
        	iX1 = new int[2];
  	        iY1 = new int[2];
  	        iX2 = new int[2];
  	        iY2 = new int[2];
  	        iX3 = new int[2];
  	        iY3 = new int[2];
  	        
  	        int x = (int) ((int) 250 * Math.tan(prismAngle/2));
			triX[0] = 400-x;
			triX[1] = 400;
			triX[2] = 400+x;
  	        
  	        beta1 = Math.asin(nO*Math.sin(incidenceAngle)/nP);
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
          	iY3[1] = (int) (iY3[0] - 200* Math.tan(prismAngle/2 - alpha2)); 
          	
          	ray2 = new Ray(iX2[0], iX2[1], iY2[0], iY2[1], -prismAngle/2+beta1, color);
          	ray3 = new Ray(iX3[0], iX3[1], iY3[0], iY3[1], prismAngle/2-alpha2, color);
          	
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
	        g_ray1.setStroke(new BasicStroke(2));
	        g_ray1.drawPolygon(iX1, iY1, 2); 
	        
			if(isAnimation == true) {
		        if (ray2.isDrawn==false) {
		        	g_ray2.drawLine(ray2.xStart, ray2.yStart, ray2.currentX, ray2.currentY);  
		        }
		        
		        if (ray2.isDrawn==true) {
		        	g_ray2.drawLine(ray2.xStart, ray2.yStart, ray2.xEnd, ray2.yEnd); 
		        	g_ray3.drawLine(ray3.xStart, ray3.yStart, ray3.currentX, ray3.currentY); 
		        }
		    }
	    }
		
		
				
		void Animation() {

				ScheduledExecutorService scheduler = Executors
						.newScheduledThreadPool(1);

				scheduler.scheduleAtFixedRate((new Runnable() {

					@Override
					public void run() {
							isAnimation = true;
							if(ray2.isDrawn==false) ray2.run();
							if(ray2.isDrawn==true) ray3.run();
							if(ray3.isDrawn == true) scheduler.shutdown();
							repaint();
					}
				}), 0, 20, MILLISECONDS);

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
		
		public double getSpeed()
		{
			return speed;
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





