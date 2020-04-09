import java.awt.*;
import java.applet.*;
import java.util.*;

public class breakout extends Applet implements Runnable{

    static int ballCnt = 3;
    int pauseValue = 20;
    int ballVelY = 6;
    int ballVelX = ballVelY;
    Graphics canGraf;
    int blockWidth = 20;
    int blockHeight = 20;
    int xstart = 1; //x start pos of blocks
    int ystart = 50;  //y start pos of blocks
    int xincr = 21;  //x block increment
    int yincr = 21;  //y block increment
    int blocksPerLine = 20;
    int numberOfLines = 5; //lines of blocks
    int ballYstart = 220;
    int ballXstart = 40;
    Color color;
    int paddleSize;
    Paddle paddle;
    int paddleWidth = 40;
    int paddleHeight = 12;
    Paddle paddle1;
    int paddle1Width = 13;
    int paddle1Height = 12;
    Paddle paddle2;
    int paddle2Width = 13;
    int paddle2Height = 12;
    Ball2 ball2;
    int ballWidth = 10;
    int ballHeight = 10;
    int count;
    int xcnt;
    int ycnt;
    Image offscreenImg;
    Graphics offscreenG;
    Thread thread;
    int z;
    Vector v = new Vector();
    Enumeration e = v.elements();
    Block[] blockArray;
    long startTime;
    Date date;
    int seconds;
    int minutes;
    boolean start = false;
    int xexit;
    AudioClip hitEdge;
    AudioClip fire;
    AudioClip missBall;
    AudioClip hitPaddle;
    AudioClip hitBlock1;
    AudioClip hitBlock2;
    AudioClip hitBlock3;
    AudioClip hitBlock4;
    AudioClip hitBlock5;
    AudioClip win;
    int oldVsize;
    Font f;
    FontMetrics fm;
    int fs;
    int endCnt = 30;
    int cnt = endCnt+1;
    Font f2;
    boolean first = true;
    int y = 0;
    int x = 0;
    boolean showWin = false;
    boolean pressStart = false;
    boolean paint = true;
    Fireworks FW = null;
    Color borderColor = Color.red;
    Color borderColor2 = Color.red;
    boolean grow = false;

    public void init() {
        super.init();

         //{{INIT_CONTROLS
		setLayout(null);
		addNotify();
		resize(466,472);
		setBackground(new Color(16776960));
		colorBtn = new java.awt.Button("Change Color");
		colorBtn.reshape(360,108,94,24);
		add(colorBtn);
		choBlockSize = new java.awt.Choice();
		choBlockSize.addItem("");
		choBlockSize.addItem("10");
		choBlockSize.addItem("15");
		choBlockSize.addItem("20");
		choBlockSize.addItem("25");
		choBlockSize.addItem("30");
		choBlockSize.addItem("35");
		choBlockSize.addItem("40");
		choBlockSize.addItem("50");
		choBlockSize.addItem("60");
		add(choBlockSize);
		choBlockSize.reshape(384,252,48,17);
		choBlockSize.setBackground(new Color(16777215));
		choBallSpeed = new java.awt.Choice();
		choBallSpeed.addItem("");
		choBallSpeed.addItem("1");
		choBallSpeed.addItem("2");
		choBallSpeed.addItem("3");
		choBallSpeed.addItem("4");
		choBallSpeed.addItem("5");
		choBallSpeed.addItem("6");
		choBallSpeed.addItem("7");
		choBallSpeed.addItem("8");
		choBallSpeed.addItem("9");
		choBallSpeed.addItem("10");
		choBallSpeed.addItem("11");
		choBallSpeed.addItem("12");
		choBallSpeed.addItem("13");
		choBallSpeed.addItem("14");
		add(choBallSpeed);
		choBallSpeed.reshape(384,156,48,17);
		choBallSpeed.setBackground(new Color(16777215));
		txtNumBlocks = new java.awt.TextField();
		txtNumBlocks.setEditable(false);
		txtNumBlocks.reshape(384,360,36,26);
		txtNumBlocks.setBackground(new Color(16777215));
		add(txtNumBlocks);
		txtBalls = new java.awt.TextField();
		txtBalls.setEditable(false);
		txtBalls.reshape(384,408,36,26);
		txtBalls.setBackground(new Color(16777215));
		add(txtBalls);
		btnPause = new java.awt.Button("Resume");
		btnPause.reshape(360,72,94,24);
		add(btnPause);
		choBallSize = new java.awt.Choice();
		choBallSize.addItem("");
		choBallSize.addItem("1");
		choBallSize.addItem("2");
		choBallSize.addItem("3");
		choBallSize.addItem("4");
		choBallSize.addItem("5");
		choBallSize.addItem("6");
		choBallSize.addItem("7");
		choBallSize.addItem("8");
		choBallSize.addItem("9");
		choBallSize.addItem("10");
		choBallSize.addItem("11");
		choBallSize.addItem("12");
		choBallSize.addItem("13");
		choBallSize.addItem("14");
		choBallSize.addItem("15");
		choBallSize.addItem("16");
		choBallSize.addItem("17");
		choBallSize.addItem("18");
		choBallSize.addItem("19");
		choBallSize.addItem("20");
		add(choBallSize);
		choBallSize.reshape(384,300,48,17);
		choBallSize.setBackground(new Color(16777215));
		choPaddle = new java.awt.Choice();
		choPaddle.addItem("");
		choPaddle.addItem("10");
		choPaddle.addItem("20");
		choPaddle.addItem("30");
		choPaddle.addItem("40");
		choPaddle.addItem("50");
		choPaddle.addItem("60");
		choPaddle.addItem("70");
		choPaddle.addItem("80");
		choPaddle.addItem("90");
		choPaddle.addItem("100");
		choPaddle.addItem("150");
		choPaddle.addItem("200");
		add(choPaddle);
		choPaddle.reshape(384,204,48,17);
		choPaddle.setBackground(new Color(16777215));
		btnStart = new java.awt.Button("Start");
		btnStart.reshape(360,36,94,24);
		add(btnStart);
		txtTime = new java.awt.TextField();
		txtTime.setEditable(false);
		txtTime.reshape(144,0,72,26);
		txtTime.setBackground(new Color(16777215));
		add(txtTime);
		canvas1 = new java.awt.Canvas();
		canvas1.reshape(12,36,337,408);
		canvas1.setBackground(new Color(0));
		add(canvas1);
		//}}



        hitEdge  = getAudioClip(getCodeBase(),"edgeHit.au");
        fire  = getAudioClip(getCodeBase(),"fire.au");
        hitBlock1  = getAudioClip(getCodeBase(),"blockHit1.au");
        hitBlock2  = getAudioClip(getCodeBase(),"blockHit2.au");
        hitBlock3  = getAudioClip(getCodeBase(),"blockHit3.au");
        hitBlock4  = getAudioClip(getCodeBase(),"blockHit4.au");
        hitBlock5  = getAudioClip(getCodeBase(),"blockHit5.au");
        missBall = getAudioClip(getCodeBase(),"missBall.au");
        hitPaddle = getAudioClip(getCodeBase(),"paddleHit.au");
        win = getAudioClip(getCodeBase(),"win.au");


        choBlockSize.select(Integer.toString(blockWidth));
        choBallSpeed.select(Integer.toString(ballVelY));
        choBallSize.select(Integer.toString(ballWidth));
        choPaddle.select(Integer.toString(paddleWidth));

        blocksPerLine = canvas1.size().width/blockWidth;
        while(blockWidth*blocksPerLine+blocksPerLine-blockWidth+1 > canvas1.size().width)
             blocksPerLine -=1;

        offscreenImg = createImage(canvas1.size().width, canvas1.size().height);
        offscreenG = offscreenImg.getGraphics();

        paddle = new Paddle(paddleWidth,paddleHeight, canvas1.size().height-paddleHeight-28, canvas1);
        paddle1 = new Paddle(paddle1Width,paddle1Height, canvas1.size().height-paddle1Height-28, canvas1);
        paddle2 = new Paddle(paddle2Width,paddle2Height, canvas1.size().height-paddle2Height-28, canvas1);

        ball2 = new Ball2(ballXstart, ballYstart, ballVelX, ballVelY, ballWidth, ballHeight,
                paddle, canvas1, paddle1, paddle2, hitEdge, hitPaddle);

        setUpBlocks();

        paddleSize = paddleWidth/3;
        paddleWidth = paddleSize*2;
        paddle1Width = paddleSize;
        paddle2Width = paddleSize;

        paddle.setWidth(paddleWidth);
        paddle1.setWidth(paddle1Width);
        paddle2.setWidth(paddle2Width);

        paddle.setXpos(canvas1.size().width/2-paddleWidth/2);
        paddle1.setXpos(paddle.getXpos()-paddle1Width);
        paddle2.setXpos(paddle.getXpos()+paddle.width);

        ball2.setColor(Color.red);
        paddle.setColor(Color.green);
        paddle1.setColor(Color.green);
        paddle2.setColor(Color.green);

        setText();

        txtTime.setText("00:00:00");

        FW = new Fireworks(canvas1, paddle, paddle1,paddle2, this);

        getSound();

        btnStart.disable();

        pauseValue = 15;

        int temp = (int)(Math.floor(Math.random()*2-1));
        if (temp == 0) temp = 1;
            ball2.setXvel(ballVelX*temp);

        temp = (int)(Math.floor(Math.random()*2-1));
        if (temp == 0) temp = 1;

        int temp2 = (int)(Math.floor(Math.random() * canvas1.size().width/2-ball2.width/2));

        temp2 *= temp;

        ball2.xpos = temp2+canvas1.size().width/2-ball2.width/2;

        btnPause.disable();

        setColor();
        ball2.setColor();
        paddle.setColor();
        paddle1.setColor(paddle.getColor());
        paddle2.setColor(paddle.getColor());


    }

    public void start() {

        if (thread == null)
            thread = new Thread(this);
            thread.start();


        if (FW == null) {
           FW = new Fireworks(canvas1, paddle, paddle1,paddle2, this);
           paint = true;

    }



    }

    public void stop() {

        if (thread != null)
            thread.stop();
            thread = null;

        if (FW != null)
            FW.stop();
            FW = null;

    }

     public void run() {

         if(first) {

            grow = true;
            growString("Breakout!");
            btnPause.enable();
            first = false;
            grow = false;

        }

        while (thread.isAlive()) {

            if(start && cnt > endCnt) {

                  setText();
                  ball2.moveBall();
                  synchronized(v) {ball2.testCollision(v);}
                  testNoBlocks();
                  checkBall();

                  pressStart = false;


            }


            repaint();
            pause(pauseValue);

            if(cnt <= endCnt)cnt++;

            if(v.size()>0) showWin = false;

        }
    }

    void setColor() {

        Graphics g;

        color = new Color((int)Math.floor(Math.random() * 256),
                    (int)Math.floor(Math.random() * 256),
                    (int)Math.floor(Math.random() * 256));

        g = getGraphics();
        g.setColor(color);
        g.fillRect(0,0,size().width, size().height);
        setBackground(color);

        borderColor = new Color((int)Math.floor(Math.random() * 256),
                    (int)Math.floor(Math.random() * 256),
                    (int)Math.floor(Math.random() * 256));


        borderColor2 = new Color((int)Math.floor(Math.random() * 256),
                    (int)Math.floor(Math.random() * 256),
                    (int)Math.floor(Math.random() * 256));


    }

    void setText() {

        txtTime.setText(setTime());

        int newVsize = v.size();
        if(oldVsize != newVsize)
           txtNumBlocks.setText(Integer.toString(newVsize));
        oldVsize = v.size();

    }


    void checkBall() {

        int ypos;

        ypos = ball2.getYpos();

        if(ypos >= canvas1.bounds().height+10){

            missBall.play();

            for (int x = 0; x < 30; x++) {
                repaint();
                pause(20);

            }

            cnt = 0;

            ball2.setYpos(ballYstart);

            if(ball2.xpos < 2)
                ball2.xpos = 2;

            if(ball2.xpos+ball2.width > canvas1.size().width-2)
                ball2.xpos = canvas1.size().width-ball2.width-2;

            ball2.setYvel(ballVelY);

            setBallCnt(-1);

            txtBalls.setText(Integer.toString(ballCnt));


            if (ball2.getXvel() > 0)
                ball2.setXvel(ballVelX);
            else
                ball2.setXvel(ballVelX*-1);

        }

        if(ballCnt <= 0) {

            start = false;
            txtBalls.setText(Integer.toString(ballCnt));
            ball2.setVel(ballVelY,ballVelY);
            btnPause.disable();
            btnPause.setLabel("Pause");
            btnStart.enable();
            System.gc();

        }

    }

    void setUpBlocks() {
        int z;

        z = 0;
        xcnt = xstart;
        ycnt = ystart;

        setBallCnt(3);

        Date date = new Date();
        startTime = date.getTime();
        seconds = 0;
        minutes = 0;

        txtBalls.setText(Integer.toString(ballCnt));
        blockArray = new Block[numberOfLines*blocksPerLine];

        if(v != null)
           v.removeAllElements();

           int r = (int)Math.floor(Math.random() * 256);
           int g = (int)Math.floor(Math.random() * 84);
           int b = (int)Math.floor(Math.random() * 256);

           for (int i = 0; i < numberOfLines;  i++){
            color = new Color(r,g,b);

            for (int x = 0; x < blocksPerLine; x++) {
                v.addElement (new Block(xcnt, ycnt,  blockWidth, blockHeight, color, ystart,
                                        hitBlock1, hitBlock2, hitBlock3, hitBlock4, hitBlock5));

                blockArray[z]= (Block)v.lastElement();
                z++;
                xcnt += xincr;
            }

            ycnt += yincr;
            xcnt = xstart;
            g = g + 43;

        }

        txtNumBlocks.setText(Integer.toString(v.size()));
        oldVsize = v.size();


    }

    void testNoBlocks() {

        if(v.size() > 0)
            return;

        txtNumBlocks.setText(Integer.toString(v.size()));
        start = false;
        btnPause.disable();
        btnStart.disable();

        for (int x = 0; x < 30; x++) {
            repaint();
            pause(20);
        }

        win.play();

        grow = true;
        growString("You Win!");
        grow = false;
        btnStart.enable();

        showWin = true;

        paint = false;

        FW.start();
        System.gc();

    }

    void pause (int time) {

        try { Thread.sleep(time); }
        catch (InterruptedException e) { }
    }

    public void update(Graphics g) {

        offscreenG.setColor(Color.black);


        if(!grow )
            offscreenG.fillRect(0,0,canvas1.size().width,canvas1.size().height);

        paint(g);
    }


    void drawApplet(Graphics g) {

        g.setColor(borderColor2);
        g.drawRect(0,0, size().width-1,size().height-1);

        g.setColor(color.black);

        g.drawString("Ball Speed", 380,150);
        g.drawString("Paddle Size", 380,195);
        g.drawString("Block Size", 380,240);
        g.drawString("Ball Size", 380, 290);
        g.drawString("Blocks Left", 380, 350);
        g.drawString("Balls Left", 380, 400);

    }




    public void paint(Graphics g) {

        drawApplet(g);

        if(!first) {

                if(ballCnt > 0 && v.size() > 0 )
                    ball2.paint(offscreenG);



                    paddle.paint(offscreenG);
                    paddle1.paint(offscreenG);
                    paddle2.paint(offscreenG);



                if(ballCnt <= 0) pressStart();
                else{
                    if(cnt <= endCnt ) getReady();
                    else{
                        if((btnPause.getLabel()).equals("Resume")) resume();
                        else
                            if(v.size() <= 0 && showWin) youWin();

                    }

                }

                  synchronized(v) {

                       for (int i = 0; i < v.size(); i++){
                         Block block;
                            block = (Block)v.elementAt(i);
                            block.paint(offscreenG);
                       }
                 }

           }


        if (paint) {

            offscreenG.setColor(borderColor);
            offscreenG.drawRect(0,0,canvas1.size().width-1,canvas1.size().height-1);

            canGraf = canvas1.getGraphics();
            canGraf.drawImage(offscreenImg, 0, 0, this);

        }


    }

     void pressStart() {

            offscreenG.setColor(Color.red);
            f = new Font("TimesRoman", Font.BOLD, 36);
            offscreenG.setFont(f);
            fm = offscreenG.getFontMetrics();
            fs = fm.stringWidth("Game Over");
            offscreenG.drawString("Game Over",(canvas1.bounds().width-fs)/2, 225);
            f = new Font("TimesRoman", Font.BOLD, 20);
            offscreenG.setFont(f);
            fm = offscreenG.getFontMetrics();
            fs = fm.stringWidth("Press Start");
            offscreenG.drawString("Press Start",(canvas1.bounds().width-fs)/2, 30);

     }

     void getReady() {

           offscreenG.setColor(Color.yellow);
           f = new Font("TimesRoman", Font.BOLD, 20);
           offscreenG.setFont(f);
           fm = offscreenG.getFontMetrics();
           fs = fm.stringWidth("Get Ready!");
           offscreenG.drawString("Get Ready!",(canvas1.bounds().width-fs)/2, 30);

     }

     void resume() {

           offscreenG.setColor(Color.red);
           f = new Font("TimesRoman", Font.BOLD, 20);
           offscreenG.setFont(f);
           fm = offscreenG.getFontMetrics();
           fs = fm.stringWidth("Press Resume to Start");
           offscreenG.drawString("Press Resume to Start",(canvas1.bounds().width-fs)/2, 30);

     }

     void youWin() {



          offscreenG.setColor(Color.yellow);
          offscreenG.setFont(f2);
          offscreenG.drawString("You Win!",x,y);

          offscreenG.setColor(Color.red);
          f = new Font("TimesRoman", Font.BOLD, 20);
          offscreenG.setFont(f);
          fm = offscreenG.getFontMetrics();
          fs = fm.stringWidth("Press Start");
          offscreenG.drawString("Press Start",(canvas1.bounds().width-fs)/2, 30);

     }


    String setTime() {

        long thisTime;

        long diffTime;
        long hundOfSec;
        String sec;
        String hund;
        String mil;
        String min;

        date = new Date();

        thisTime = date.getTime();
        diffTime = thisTime-startTime;

        hundOfSec = diffTime/10;

       if (hundOfSec > 99){
            startTime = thisTime;
            hundOfSec = 0;
            seconds +=1;

        }

        if (seconds > 59){
            seconds = 0;
            minutes +=1;

        }

        sec = Long.toString(seconds);
        if (seconds <= 9)
            sec = "0"+sec;

        hund = Long.toString(hundOfSec);
        if (hundOfSec <= 9)
            hund = "0"+hund;

        min = Long.toString(minutes);
        if (minutes <= 9)
            min = "0"+min;

        return min + ":" + sec + ":" + hund;
    }

    public boolean action(Event event, Object arg) {

       Color saveColor = null;

        if (event.target == choPaddle &&  choPaddle.getSelectedItem() != ""){

            paddleWidth =  Integer.valueOf(choPaddle.getSelectedItem()).intValue();



            if(!paint) {

                saveColor =  paddle.getColor();
                paddle.setColor(Color.black);
                paddle1.setColor(color.black);
                paddle2.setColor(Color.black);
            }

            pause(100);

            paddleSize = paddleWidth/3;
            paddleWidth = paddleSize*2;
            paddle1Width = paddleSize;
            paddle2Width = paddleSize;

            paddle.setWidth(paddleWidth);
            paddle1.setWidth(paddle1Width);
            paddle2.setWidth(paddle2Width);

            paddle.setXpos(canvas1.bounds().width-paddle.getWidth()-paddle1.getWidth()-2);
            paddle1.setXpos(paddle.getXpos()-paddle1.getWidth());
            paddle2.setXpos(paddle.getXpos()+paddle.getWidth());

           if(!paint) {

                paddle.setColor(saveColor);
                paddle1.setColor(saveColor);
                paddle2.setColor(saveColor);

           }


        }

        if (event.target == btnPause && ballCnt > 0){

           if((btnPause.getLabel()).equals("Pause")){
                start = false;
                btnPause.setLabel("Resume");
                btnStart.disable();

         }

            else {

                FW.stop();

                paint = true;


                start = true;
                btnPause.setLabel("Pause");
                startTime = date.getTime();
                btnStart.enable();

                cnt = 0;
            }

        }

        if (event.target == choBallSize &&  choBallSize.getSelectedItem() != ""){

                 ballWidth = Integer.valueOf(choBallSize.getSelectedItem()).intValue();
                 ballHeight = Integer.valueOf(choBallSize.getSelectedItem()).intValue();
                 ball2.setSize(ballWidth,ballHeight);

        }

        if (event.target == choBallSpeed &&  choBallSpeed.getSelectedItem() != ""){

         int ballVelY;
         int ballVelX;

               ballVelY = Integer.valueOf(choBallSpeed.getSelectedItem()).intValue();
               ballVelX = ballVelY;

               this.ballVelY = ballVelY;
               this.ballVelX = ballVelX;

               if(ball2.getYvel() < 0)
                    ballVelY *= -1;

               if(ball2.getXvel() < 0)
                    ballVelX *= -1;

               ball2.setVel(ballVelX,ballVelY);

        }

        if (event.target == choBlockSize &&  choBlockSize.getSelectedItem() != ""){

                blockWidth = Integer.valueOf(choBlockSize.getSelectedItem()).intValue();
                blockHeight = Integer.valueOf(choBlockSize.getSelectedItem()).intValue();
                xincr =  blockWidth+1;
                yincr =  blockHeight+1;
                blocksPerLine = canvas1.size().width/blockWidth;

                while(blockWidth*blocksPerLine+blocksPerLine-blockWidth+1 > canvas1.size().width)
                   blocksPerLine -=1;

                setUpBlocks();

                btnPause.setLabel("Resume");
                btnPause.enable();
                btnStart.disable();

                ball2.setYpos(ballYstart);

                int temp = Integer.valueOf(choBallSpeed.getSelectedItem()).intValue();
                ball2.setVel(temp, temp);

                txtTime.setText("00:00:00");
                start = false;
                FW.paused();


        }

        if (event.target == btnStart){

            FW.stop();

            paint = true;


            setBallCnt(3);
            pressStart = true;

            int temp = 0;

            setUpBlocks();
            start = true;


            ball2.setYpos(ballYstart);

            temp = (int)(Math.floor(Math.random()*2-1));
            if (temp == 0) temp = 1;

            int temp2 = (int)(Math.floor(Math.random() * canvas1.size().width/2-ball2.width/2));

            temp2 *= temp;

            ball2.xpos = temp2+canvas1.size().width/2-ball2.width/2;

            int speed = Integer.valueOf(choBallSpeed.getSelectedItem()).intValue();
            ball2.setYvel(speed);

            temp = (int)(Math.floor(Math.random()*2-1));
            if (temp == 0) temp = 1;
                ball2.setXvel(speed*temp);

            paddle.setXpos(canvas1.size().width/2-paddleWidth/2);
            paddle1.setXpos(paddle.getXpos()-paddle1Width);
            paddle2.setXpos(paddle.getXpos()+paddle.width);

            btnPause.enable();
            btnPause.setLabel("Pause");

            cnt = 0;

            txtTime.setText("00:00:00");

        }

        if (event.target == colorBtn) {

            Block block;
            int vcnt;
            int acnt;
            Block blockA;
            Block blockV;
            vcnt = 0;
            acnt = 0;

            setColor();
            ball2.setColor();
            paddle.setColor();
            paddle1.setColor(paddle.getColor());
            paddle2.setColor(paddle.getColor());

            int r = (int)Math.floor(Math.random() * 256);
            int g = (int)Math.floor(Math.random() *84);
            int b = (int)Math.floor(Math.random() * 256);

            for (int i = 0; i < numberOfLines;  i++){

                color = new Color(r,g,b);

                for (int x = 0; x < blocksPerLine; x++) {

                    if(vcnt+x >= v.size()) return true;

                    blockV = (Block)v.elementAt(vcnt+x);
                    blockA = (blockArray[acnt+x]);

                   if (blockSame(  blockV, blockA  ))
                      blockV.setColor(color);
                   else
                       vcnt = vcnt-1;
               }

               vcnt += blocksPerLine;
               acnt += blocksPerLine;
               g = g + 43;

            }


        }

          return super.action(event, arg);
    }

    boolean blockSame(Block v, Block a) {

        if(v.xpos == a.xpos && v.ypos == a.ypos)
            return true;

        return false;
    }

    public boolean mouseMove(Event evt, int x, int y) {

        int paddleStart;
        int paddleEnd;

        if(!start )
            return false;

        paddleStart = x-paddle.getWidth()/2-paddle1.getWidth()-12;
        paddleEnd =   x+paddle.getWidth()/2+paddle1.getWidth()-12;

          if(paddleStart <= 0 ){

            paddle.setXpos(paddle1.getWidth()+2);
            paddle1.setXpos(paddle.getXpos()-paddle1.getWidth());
            paddle2.setXpos(paddle.getXpos()+paddle.getWidth());


            return true;

         }

          if(paddleEnd >= canvas1.bounds().width){

            paddle.setXpos(canvas1.bounds().width-paddle.getWidth()-paddle1.getWidth()-2);
            paddle1.setXpos(paddle.getXpos()-paddle1.getWidth());
            paddle2.setXpos(paddle.getXpos()+paddle.getWidth());

            return true;


          }

        x-=paddle.getWidth()/2+12;

        paddle.setXpos(x);
        paddle1.setXpos(x-paddle1.getWidth());
        paddle2.setXpos(x+paddle.getWidth());

        return true;

    }



    void getSound() {

            hitBlock1.play();
            hitBlock1.stop();

            fire.play();
            fire.stop();


            hitBlock2.play();
            hitBlock2.stop();

            hitBlock3.play();
            hitBlock3.stop();

            hitBlock4.play();
            hitBlock4.stop();

            hitBlock5.play();
            hitBlock5.stop();

            hitPaddle.play();
            hitPaddle.stop();

            hitEdge.play();
            hitEdge.stop();

            missBall.play();
            missBall.stop();

            win.play();

        }

      void growString(String string) {

        int fs;
        int fs2;
        FontMetrics fm;
        Graphics g;

        Font f = new Font("TimesRoman", Font.BOLD, 1);
        fm = getFontMetrics(f);
        fs = fm.stringWidth(string);
        y = canvas1.size().height/2;

        int growSize = canvas1.size().width/2;

        offscreenG.setColor(Color.black);
        offscreenG.fillRect(0,0,canvas1.size().width,canvas1.size().height);

        offscreenG.setColor(borderColor);
        offscreenG.drawRect(0,0,canvas1.size().width-1,canvas1.size().height-1);

        if(!first) pause(500);

        while (fs < growSize){

            offscreenG.setColor(Color.black);
            offscreenG.fillRect(0,0,canvas1.size().width,canvas1.size().height);

            if(first) resume();
                else {

                    paddle.paint(offscreenG);
                    paddle1.paint(offscreenG);
                    paddle2.paint(offscreenG);

                }

            offscreenG.setFont(f);
            offscreenG.setColor(Color.yellow);

            x = (canvas1.size().width-fs)/ 2;

            offscreenG.drawString(string,x,y);

            repaint();

            pause(20);

            f = new Font(f.getName(),f.getStyle(),f.getSize()+4);

            fm = getFontMetrics(f);
            fs  = fm.stringWidth(string);

         }

        f2 = new Font(f.getName(),f.getStyle(),f.getSize()-4);
        f = new Font("TimesRoman", Font.BOLD + Font.ITALIC, 20);
        offscreenG.setFont(f);

        fm = offscreenG.getFontMetrics();
        fs = fm.stringWidth("Lou Schiano");
        fs2 = fm.stringWidth("by: ");

        int currentLeftX  = -fs;
        int currentRightX = canvas1.size().width;

       if(!first)return;

       while(currentLeftX <= canvas1.size().width/2-fs/2-fs2/2) {


            offscreenG.setColor(Color.black);
            offscreenG.fillRect(0,0,canvas1.size().width,canvas1.size().height);

            resume();

            offscreenG.setFont(f2);
            offscreenG.setColor(Color.yellow);
            offscreenG.drawString(string,x,y);

            offscreenG.setFont(f);
            offscreenG.setColor(color);
            offscreenG.drawString("by: ", currentLeftX, canvas1.size().height-30);

            repaint();

            pause(20);
            currentLeftX  += 5;

         }


        while(currentRightX > canvas1.size().width/2-fs/2+fs2/2) {


            offscreenG.setColor(Color.black);
            offscreenG.fillRect(0,0,canvas1.size().width,canvas1.size().height);


            resume();

            offscreenG.setFont(f2);
            offscreenG.setColor(Color.yellow);
            offscreenG.drawString(string,x,y);

            offscreenG.setFont(f);
            offscreenG.setColor(color);
            offscreenG.drawString("by: ", currentLeftX, canvas1.size().height-30);
            offscreenG.drawString("Lou Schiano", currentRightX, canvas1.size().height-30);

            Font f4 = new Font("TimesRoman", Font.BOLD + Font.ITALIC, 10);
            offscreenG.setFont(f4);

            repaint();

            pause(20);
            currentRightX -= 3;


         }

        int currentBottom = canvas1.size().height;

        Font f3 = new Font("TimesRoman", Font.BOLD + Font.ITALIC, 1);

        while(currentBottom > canvas1.size().height-15) {

            offscreenG.setColor(Color.black);
            offscreenG.fillRect(0,0,canvas1.size().width,canvas1.size().height);

            resume();

            offscreenG.setFont(f2);
            offscreenG.setColor(Color.yellow);
            offscreenG.drawString(string,x,y);

            offscreenG.setFont(f);
            offscreenG.setColor(color);
            offscreenG.drawString("by: ", currentLeftX, canvas1.size().height-30);
            offscreenG.drawString("Lou Schiano", currentRightX, canvas1.size().height-30);

            Font f4 = new Font("TimesRoman", Font.BOLD + Font.ITALIC, 10);
            offscreenG.setFont(f4);

            offscreenG.setColor(borderColor);
            offscreenG.drawString("Fireworks by Ami", currentRightX-5, currentBottom);

            repaint();

            pause(20);
            currentBottom -= 1;

         }

       pause(500);

    }

      synchronized void setBallCnt ( int x) {

        if(pressStart)
            return;

        if(x < 0)
          ballCnt--;
        else
          ballCnt = x;

     }


    //{{DECLARE_CONTROLS
	java.awt.Button colorBtn;
	java.awt.Choice choBlockSize;
	java.awt.Choice choBallSpeed;
	java.awt.TextField txtNumBlocks;
	java.awt.TextField txtBalls;
	java.awt.Button btnPause;
	java.awt.Choice choBallSize;
	java.awt.Choice choPaddle;
	java.awt.Button btnStart;
	java.awt.TextField txtTime;
	java.awt.Canvas canvas1;
	//}}

}



