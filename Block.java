import java.awt.*;
import java.applet.*;
import java.util.*;

class Block {
    int xpos;
    int ypos;
    int width;
    int height;
    Color color;
    AudioClip hitBlock1;
    AudioClip hitBlock2;
    AudioClip hitBlock3;
    AudioClip hitBlock4;
    AudioClip hitBlock5;
    int yStart;
     
    Block(int xpos, int ypos, int width, int height, Color color,  int yStart, 
          AudioClip hitBlock1, AudioClip hitBlock2,AudioClip hitBlock3, AudioClip hitBlock4 ,AudioClip hitBlock5 ) {
                
        this.xpos = xpos;
        this.ypos = ypos;
        this.width = width;
        this.height = height;
        this.color = color;
        this.hitBlock1 = hitBlock1;
        this.hitBlock2 = hitBlock2;
        this.hitBlock3 = hitBlock3;
        this.hitBlock4 = hitBlock4; 
        this.hitBlock5 = hitBlock5; 
        this.yStart = yStart;
        
       }

     void setColor(Color color) {

           this.color = color;
    }

    boolean  collision(int ballxpos,int ballypos, int ballwidth, int ballheight) {
        
        if((ballxpos+ballwidth < xpos || ballxpos > xpos+width) ||
           (ballypos+ballheight<ypos || ballypos > ypos+height))
           return false;
         else {
                     
           switch((ypos-yStart)/height) {
            
                case 4:
                    hitBlock5.play();
                    break;
                
                case 3:
                    hitBlock4.play();
                    break;
                
                case 2:
                    hitBlock3.play();
                    break;
                
                case 1:
                    hitBlock2.play();
                    break;
                    
                case 0:
                    hitBlock1.play();
                    break;
               
           }        
               
            return true;
         }   

    }

     void paint(Graphics g) {

        g.setColor(color);
        g.fillRect(xpos,ypos,width,height);
        
    }

}

