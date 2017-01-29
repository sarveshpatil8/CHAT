

import java.awt.Graphics;
public  class Draw extends ChatMessage 
{
        int a1,b1,a2,b2;	
	
	public Draw(int a1, int b1, int a2, int b2) 
	{
     this.a1 = a1;
     this.a2 = a2;
     this.b1 = b1;
     this.b2 = b2;
              
	}
        Draw(){
            
        }
	public void paint(Graphics g)
	{
		g.drawLine(a1, b1, a2, b2);
	}
   
 	public int geta1(){
		return a1;
	}
        public int getb1(){
		return b1;
	}
        public int geta2(){
		return a2;
	}
        public int getb2(){
		return b2;
	}
        
}