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
	
		double angle1;
		double speed=0.01;

		public void paint(Graphics g)
	    { 
			super.paint(g);
			Graphics2D g_tri = (Graphics2D)g;
			Graphics2D g_ray = (Graphics2D)g;
			int[] triX={300,400,500}; 
	        int[] triY={200,400,200};

	        angle1 = angle1*Math.PI/180;
	        
	        double ray_startX = 100;
	        double ray_startY = 300;
	        double ray_endX  = 100000* Math.sin(angle1);
	        double ray_endY  = 100000* Math.cos(angle1);
	        
	        int iX[];
	        int iY[];
	        iX = new int[2];
	        iY = new int[2];
	    	iX[0] = (int)ray_startX;
        	iY[0] = (int)ray_startY;
        	iX[1] = (int)ray_endX;
        	iY[1] = (int)ray_endY;
	        
	        g_tri.setColor(Color.RED); 
	        g_tri.translate(-100, 20);
	        g_tri.setStroke(new BasicStroke(5));
	        g_tri.drawPolygon(triX, triY, 3); 
	        
	        g_ray.setColor(Color.BLUE);
	        g_ray.setStroke(new BasicStroke(2));
	        g_ray.drawPolygon(iX, iY, 2); 		
	    }
		
		
				
		void Animation() {

				ScheduledExecutorService scheduler = Executors
						.newScheduledThreadPool(1);

				scheduler.scheduleAtFixedRate((new Runnable() {

					@Override
					public void run() {
					
							setAngle(getAngle()+getSpeed());
							Bounce();
							repaint();
					
					}
				}), 0, 20, MILLISECONDS);

		}	
			
		
		public void Bounce()
		{
			if(angle1==0)
			{
				speed=speed;
			}
			
			if(angle1==18000)
			{
				speed=-speed;;
			}
		}
		
		public void setAngle(double angle1) 
		{
			this.angle1 = angle1;
		}
		
		public double getSpeed()
		{
			return speed;
		}
		
		public double getAngle()
		{
			return angle1;
		}

}





