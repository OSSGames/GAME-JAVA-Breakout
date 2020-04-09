/*
        Fireworks.java, (c)1996 ami. All Rights Reserved.

        Permission to use, copy, modify, and distribute this software and its
        documentation for NON-COMMERCIAL purposes and without fee is hereby
        granted provided that this copyright notice and appropiate documention
        appears in all copies.

        AMI MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
        SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
        IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE,
        OR NON-INFRINGEMENT. AMI SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
        LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE
        OR ITS DERIVATIVES.

        Comments and bug reports please send to:
        rlee@j-link.or.jp
        URL: http://www.j-link.or.jp/~rlee

        Ver 0.95        Scrolling bug fixed.
                        Thanks to Jos van Uden(paridaan@hvision.nl) for
                        his bug report.
        Ver 0.96        Add parameter:"bgcolor","rockets","stars".
*/

import java.awt.*;

class Star
{
        final int TAILLEN=5;
        Firework fw;
        int x[];
        int y[];
        double vx;
        double vy;
        Color color;
        int maxlife;
        int age;

        Star(Firework fw, int x, int y, Color color, int maxlife, int energy)

        {

                int i;
                this.fw = fw;
                this.x = new int[TAILLEN];
                this.y = new int[TAILLEN];
                for (i=0; i<TAILLEN; i++) this.x[i] = x;
                for (i=0; i<TAILLEN; i++) this.y[i] = y;
                do
                {
                        vx = Math.random()*energy*11/10;
                        vy = Math.random()*energy*11/10;
                        vx *= (1-2*Math.random());
                        vy *= (1-2*Math.random());
                        vy -= 5;
                }while (Math.abs(vx)+Math.abs(vy) > energy*2);
                this.color = color;
                this.maxlife = maxlife*2/3 + (int)(Math.random()*maxlife/3);
                age = 0;
        }

        public void sparks(Graphics g)
        {
                if (age > 4)
                {
                        g.setColor(color);
                        for (int i=TAILLEN-1; i>0; i--)
                        {
                                int head=age%TAILLEN;
                                int p1=(head+i+1)%TAILLEN;
                                int p2=(head+i)%TAILLEN;
                                if (i == 1) g.setColor(fw.app.bgcolor);
                                g.drawLine(x[p1], y[p1], x[p2], y[p2]);
                        }
                }



        }

        public int flys(Graphics g)

        {

                if (age < maxlife)
                {
                        sparks(g);
                        if (maxlife-age > TAILLEN)
                        {
                                //vx += 0;
                                vy += 0.5f;
                        }
                        else
                        {
                                color = color.brighter();
                                vx = 0;
                                vy = 0;
                        }
                        int head=age%TAILLEN;
                        x[(head+1)%TAILLEN] = x[head] + (int)(vx+0.5f);
                        y[(head+1)%TAILLEN] = y[head] + (int)(vy+0.5f);
                        vx = vx*9/10;
                        vy = vy*9/10;
                        age++;
                }
                return maxlife-age;
        }

}

class Firework
{
        Fireworks app;
        Star stars[];
        int nstars;
        int x;
        int y;
        Color color;
        int maxlife;
        int energy;
        int width;
        int height;

        Firework(Fireworks app, int width, int height, int nstars)
        {
                this.app = app;
                this.width = width;
                this.height = height;
                this.nstars = nstars;
                stars = new Star[nstars];
                reinitf();
        }

        public void reinitf()

        {

                int i;
                x = width/4+(int)(Math.random()*width/2);
                y = height/4+(int)(Math.random()*height/2);
                color = new Color(200+(int)(Math.random()*55),
                                55+(int)(Math.random()*200),
                                55+(int)(Math.random()*200));
                maxlife = 20 + (int)(Math.random()*15);
                energy = width/30 + (int)(Math.random()*height/30);
                for (i=0; i<nstars; i++)
                {
                        stars[i] = new Star(this, x, y, color, maxlife, energy);
                }

        }

        public int firef(Graphics g)
        {
                int i;
                int j=0;
                for (i=0; i<nstars; i++)
                {
                        if (stars[i].flys(g) != 0) j++;
                }
                return j;
        }
}


public class Fireworks implements Runnable
{
        int rockets;
        Firework f[];
        Thread kicker=null;
        Color bgcolor;
        Canvas c;
        Paddle p;
        Paddle p1;
        Paddle p2;
        FontMetrics fm;
        int fs;
        breakout b;
        Graphics g1;
        int pauseValue = 35;
        long diffTime;
        long startTime;
        String startMessage = "Press Start";
        String pauseMessage = "Press Resume to Start";
        Color startColor = Color.red;
        boolean paused = false;
        boolean first = true;

        Fireworks(Canvas c, Paddle p, Paddle p1, Paddle p2, breakout b)

        {
                this.c = c;
                this.p = p;
                this.p1 = p1;
                this.p2 = p2;
                this.b = b;



                bgcolor = Color.black;
                rockets = 2;
                int nstars = 60;

                Dimension d=   c.size();

                f = new Firework[rockets];
                for (int i=0; i<rockets; i++)
                   f[i] = new Firework(this, d.width, d.height, nstars);


        }

        public void paused() {


            startColor = Color.black;

            pause(100);

            paused = true;

        }


        public void paintfire(Graphics g)

        {

            if(first){
                b.fire.play();
                pause(100);
                b.fire.play();
                first = false;
            }


                for (int i=0; i<rockets; i++)
                {

                    if (f[i].firef(g) == 0) {


                        f[i].reinitf();
                        b.fire.play();

                    }


                g.setColor(b.borderColor);
                g.drawRect(0,0,c.size().width-1,c.size().height-1);

                p.paint(g);
                p1.paint(g);
                p2.paint(g);


                g.setColor(Color.yellow);
                g.setFont(new Font("TimesRoman", Font.BOLD, 37));
                g.drawString("You Win!",91,204);

               
                if(!paused) {

                    g.setColor(startColor);
                    g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                    fm = g.getFontMetrics();
                    fs = fm.stringWidth(startMessage);
                    g.drawString(startMessage,(c.bounds().width-fs)/2, 30);

                }
                else {

                    g.setColor(Color.red);
                    g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                    fm = g.getFontMetrics();
                    fs = fm.stringWidth(pauseMessage);
                    g.drawString(pauseMessage,(c.bounds().width-fs)/2, 30);

                }

                b.repaint();


                }


        }

         void pause (int time) {

         try { Thread.sleep(time); }
            catch (InterruptedException e) { }
        }





        public void run()


        {

                while(kicker != null)
                {
                        startTime = System.currentTimeMillis();

                        paintfire(c.getGraphics());


                        long thisTime = System.currentTimeMillis();
                        diffTime = thisTime - startTime;

                        try
                        {

                        if(diffTime < pauseValue)
                                kicker.sleep((int)pauseValue-(int)diffTime);

                        } catch(Exception e) {
                        }
                 }
                 kicker.stop();

        }

        public void start()
        {

                paused = false;
                startColor = Color.red;

                if(kicker == null)
                {
                        kicker = new Thread(this);
                        kicker.start();
                }
        }

        public void stop()
        {
                if(kicker != null)
                {
                        kicker.stop();
                        kicker = null;
                }
        }
}


