import java.awt.*;
import java.applet.*;
import java.util.*;

class Ball2 {

    int xpos;
    int ypos;
    int width;
    int height;
    int xvel;
    int yvel;
    Color color;
    Vector v;
    Canvas canvas1;
    Paddle p;
    Paddle p1;
    Paddle p2;
    AudioClip hitEdge;
    int velSave;
    AudioClip hitPaddle;
    int halfPaddle;
    int fullPaddle;
    
  
    Ball2(int xpos, int ypos, int xvel, int yvel, int width, int height, Paddle p, Canvas canvas1,
         Paddle p1, Paddle p2, AudioClip hitEdge, AudioClip hitPaddle) {

    this.xpos = xpos;
    this.ypos = ypos;
    this.xvel = xvel;
    this.yvel = yvel;
    this.width = width;
    this.height = height;
    this.v = v;
    this.p = p;
    this.canvas1 = canvas1;
    this.p1 = p1;
    this.p2 = p2;
    this.hitEdge = hitEdge;
    this.velSave = Math.abs(xvel);
    this.hitPaddle = hitPaddle;
    
    }

   boolean testCollision(Vector v) {

        boolean value;
        Block block;
        Enumeration e = v.elements();

        while(e.hasMoreElements()){
            block = (Block)e.nextElement();
            if( block.collision(xpos,ypos,width,height))   {
                yvel *= -1;
                v.removeElement(block);

                return true;
            }
        }

        if(p1.collision(xpos,ypos,width,height)) {

            if(xvel < 0)
               xvel = -velSave;
            else
              xvel = velSave;

            if(xvel < 0)
             xvel *= -1;

            yvel = velSave;
            xvel *= -1;
            yvel *= -1;
            
            int hvel = xvel;
            int svel = Math.abs(xvel/2);
                   
            halfPaddle = (p1.width+p2.width+p.width)/2;
         
            if(xpos+width < p1.xpos+halfPaddle) {
                        
                int ballInPaddle = (p1.xpos+halfPaddle) - (xpos+width);
                float percent = (float)ballInPaddle/(float)halfPaddle; 

                xvel *= percent;
                xvel = Math.abs(xvel);
                
                if (hvel < 0 )
                    xvel = -xvel;
                          
                if (hvel < 0 )
                    svel = -svel;
                    
                xvel +=svel;
           
                if(xvel < hvel-3)
                  xvel = hvel-3;
                  
            }

            hitPaddle.play();

            return true;
        }

        if(p2.collision(xpos,ypos,width,height)) {
                   

           if(xvel < 0)
               xvel = -velSave;
           else
              xvel = velSave;

           if(xvel < 0)
             xvel *= -1;

            yvel = velSave;
            yvel *= -1;
            
            halfPaddle = (p1.width+p2.width+p.width)/2;
            fullPaddle = p1.width+p2.width+p.width;
           
            int hvel = xvel;
            int svel = Math.abs(xvel/2);  
                              
            if(xpos < p1.xpos+fullPaddle) {
         
                int ballInPaddle =  (p1.xpos+fullPaddle) - xpos;
                ballInPaddle = halfPaddle - ballInPaddle;
                
                float percent = ((float)ballInPaddle)/(float)halfPaddle; 
           
                xvel *= percent;
                xvel = Math.abs(xvel);
                
                if (hvel < 0 )
                    xvel = -xvel;
                          
                if (hvel < 0 )
                    svel = -svel;
                    
                xvel +=svel;     
                 
                if(xvel > hvel+3) 
                    xvel = hvel+3;
              
            }

            hitPaddle.play();

            return true;
        }

        if(p.collision(xpos,ypos,width,height)) {

           if(xvel < 0)
                xvel = -1;
            else
                xvel = 1;

            yvel = velSave;
            yvel *= -1;

            hitPaddle.play();

            return true;
        }

        return false;
    }

    void moveBall() {

           if(xpos <= 0 ) {
               xvel *= -1;

            if(xvel > 0 && Math.abs(xvel)< velSave+6 )
               xvel = xvel +2;
            else
               xvel = xvel -2;

            xpos += xvel;
            xpos += 1;
            hitEdge.play();
        }

            if(xpos+width >= canvas1.bounds().width){
               xvel *= -1;

            if(xvel > 0 && Math.abs(xvel)< velSave+6 )
               xvel = xvel +2;
            else
               xvel = xvel -2;

            xpos += xvel;
            xpos -= 1;

            hitEdge.play();

            }

           if(ypos <= 0 ) {

               yvel *= -1;
               ypos += 1;

               hitEdge.play();

            }

            xpos += xvel;
            ypos += yvel;

        }

    void setColor() {

        color = new Color((int)Math.floor(Math.random() * 250),
            (int)Math.floor(Math.random() * 250),
            (int)Math.floor(Math.random() * 250));
    }

    void pause (int time) {

        try { Thread.sleep(time); }
        catch (InterruptedException e) { }

    }


    void paint(Graphics g) {

        g.setColor(color);
        g.fillRect(xpos,ypos,width,height);

    }

    void setXvel(int xvel) {

         this.xvel = xvel;
         this.velSave = Math.abs(xvel);
    }

    void setYvel(int yvel) {

        this.yvel = yvel;

    }

    int getXpos() {

        return xpos;

    }
  
     void setXpos(int ypos) {

      //  this.xpos = xpos;

    } 
    
    int getYpos() {

        return ypos;

    }

    void setYpos(int ypos) {

        this.ypos = ypos;

    }

    void setColor(Color color) {

        this.color = color;

    }

    void setSize(int width, int height) {

        this.width = width;
        this.height = height;

    }

    void setVel(int xvel, int yvel) {

         this.xvel = xvel;
         this.yvel = yvel;
         velSave = Math.abs(xvel);

    }

    int getXvel() {

        return xvel;

    }

    int getYvel() {

        return yvel;

    }

}



