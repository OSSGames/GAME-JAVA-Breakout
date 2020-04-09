import java.awt.*;
import java.applet.*;
import java.util.*;

class Paddle {
    int xpos;
    int ypos;
    int width;
    int height;
    Color color;
    Canvas canvas1;

    Paddle(int width, int height, int ypos, Canvas canvas1) {
        this.ypos = ypos;
        this.width = width;
        this.height = height;
        this.canvas1 = canvas1;

    }
    
    void setColor() {
            color = new Color((int)Math.floor(Math.random() * 250),
                    (int)Math.floor(Math.random() * 250),
                    (int)Math.floor(Math.random() * 250));
    }

    void setColor(Color color) {

           this.color = color;

    }

    Color getColor() {

           return this.color;

    }

    boolean  collision(int ballxpos,int ballypos, int ballwidth, int ballheight) {

        if((ballxpos+ballwidth < xpos || ballxpos > xpos+width) ||
           (ballypos+ballheight < ypos || ballypos > ypos+height))
           return false;
         else
           return true;

    }

    void paint(Graphics g) {

        g.setColor(color);
        g.fillRect(xpos,ypos,width,height);

    }

    void setXpos(int x) {
         xpos = x;

    }

    void setWidth(int width) {
         this.width = width;

    }

    int getXpos() {
         return xpos;

    }

    int getWidth() {
         return width;

    }

}

