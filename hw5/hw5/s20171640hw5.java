package hw5;

import java.awt.*;
import java.awt.event.*;

class WindowDestroyer extends WindowAdapter
{
    public void windowClosing(WindowEvent e) 
    {
        System.exit(0);
    }
}

public class s20171640hw5 extends Frame implements ActionListener
{  	 
	private Canvas canvas; 
	private static int fheight = 300;
	private static int fwidth = 400;
	private boolean startflag = false;
	
	public s20171640hw5()
   	{  	
		canvas = new Canvas();
      	add("Center", canvas);
      	Panel p = new Panel();
		Button s = new Button("Start");
		Button c = new Button("Close");
		p.add(s);	p.add(c);
      	s.addActionListener(this);
		c.addActionListener(this);
      	add("South", p);
   	}
	
	public void actionPerformed(ActionEvent evt)
   	{  	
		if (evt.getActionCommand() == "Start" && startflag == false)
      	{  	
			canvas.repaint();
			Ball b = new Ball(canvas);
         	b.start();
         	startflag = true;
      	}
      	else if (evt.getActionCommand() == "Close")
         	System.exit(0);
   	}
	public static void main(String[] args)
   	{  	
		Frame f = new s20171640hw5();
      	f.setSize(fwidth, fheight);
      	WindowDestroyer listener = new WindowDestroyer();  
      	f.addWindowListener(listener);
      	f.setVisible(true);  
   }

	class BallClass{
		public int size;
	   	public double x, y;
	   	public double dx, dy;
	}
	
	class Ball extends Thread{
		private Canvas box;
		private BallClass b[] = new BallClass[5000];
		private int ballNum = 5;
		private int start = 0;
		
		public Ball(Canvas c) {
			box = c; 
			for (int i = 0; i < 5; i++) {
				b[i] = new BallClass();
				b[i].size = 20;
				b[i].x = fwidth / 2;
				b[i].y = fheight / 2;
			}
			
			b[0].dx = 2; b[0].dy = 2;
			b[1].dx = -1.28; b[1].dy = 2.52;
			b[2].dx = -2.79; b[2].dy = -0.44;
			b[3].dx = -0.44; b[3].dy = -2.79;
			b[4].dx = 1.29; b[4].dy = -2.52;
		}
		
		public void draw() {  	
			for (int i = 0; i < ballNum; i++) {
				Graphics g = box.getGraphics();
				g.fillOval((int)b[i].x, (int)b[i].y, b[i].size, b[i].size);
				g.dispose();
			}
	    }
			
	   	public void move() {
	   		Graphics g = box.getGraphics();
	   		g.setXORMode(box.getBackground());
	   	    for (int i = 0; i < ballNum; i++) {
	      		g.fillOval((int)b[i].x, (int)b[i].y, b[i].size, b[i].size);
		      	b[i].x += b[i].dx;	b[i].y += b[i].dy;
	   	    }
		    Dimension d = box.getSize();
		    for (int i = 0; i < ballNum; i++) {
		      	if (b[i].x < 0) { 
		      		b[i].x = 0; 
		      		b[i].dx = -b[i].dx; 
		      	}
		      	if (b[i].x + b[i].size >= d.width) { 
		      		b[i].x = d.width - b[i].size; 
		      		b[i].dx = -b[i].dx; 
		      	}
		      	if (b[i].y < 0) { 
		      		b[i].y = 0; 
		      		b[i].dy = -b[i].dy; 
		      	}
		      	if (b[i].y + b[i].size >= d.height) { 
		      		b[i].y = d.height - b[i].size; 
		      		b[i].dy = -b[i].dy; 
		      	}
		    }
	
		    int tmp = 0;
		    for (int i = 0; i < ballNum; i++) {
		      	for (int k = 0; k < ballNum; k++) {
		      		if (i == k)
		      			continue;
		      		if (start > 500 && ((b[i].size/2 + b[k].size/2) >= Math.sqrt((b[i].x - b[k].x)*(b[i].x - b[k].x) + (b[i].y - b[k].y)* (b[i].y - b[k].y)))) {
		      			b[i].dx = 2; b[i].dy = 2;
		      			b[k].dx = -2; b[k].dy = -2;
		      			b[i].size /= 2;
		      			b[k].size /= 2;
		      			
		      			b[ballNum] = new BallClass();
		      			b[ballNum + 1] = new BallClass();
		      			
		      			b[ballNum].dx = - b[i].dx; b[ballNum].dy = b[i].dy;
		      			b[ballNum + 1].dx = b[k].dx; b[ballNum + 1].dy = -b[k].dy;
		      			
		      			b[ballNum].size = b[i].size;
		      			b[ballNum + 1].size = b[k].size;
		      			
		      			b[ballNum].x = b[i].x - b[i].size; b[ballNum].y = b[i].y + b[i].size;
		      			b[i].x += b[i].size; b[i].y += b[i].size; 
		      			b[ballNum + 1].x = b[k].x + b[k].size; b[ballNum + 1].y = b[k].y - b[k].size;
		      			b[k].x -= b[k].size; b[k].x -= b[k].size; 
	
		      			tmp++;
		      			break;
		      		}
		      		start++;
		      	}
		      	if (tmp == 1)
		      		break;
		    }
		    ballNum += tmp * 2;
		    
		    for (int i = 0; i < ballNum; i++)
		      	g.fillOval((int)b[i].x, (int)b[i].y, b[i].size, b[i].size);
		    g.dispose();
	    }
	   		
		public void run(){
			draw();
	      	while (true){ 
	      		int end = 0;
	      		for (int i = 0; i < ballNum; i++) {
	      			if (b[i].size <= 1) {
	      				end++;
	      			}
	      		}
	      	
	      		if (ballNum == end) {
	      			/*
	      			Graphics g = box.getGraphics();
	      			g.drawString("Done! (All sizes are 1)", fwidth / 2 - 70, fheight /2 - 50);
	      			startflag = false;
	      			break;*/
	      			System.exit(0);
	      		}
	      		move();
	      		try { Thread.sleep(5); } catch(InterruptedException e) {}
	      	}
	    }
	}
}