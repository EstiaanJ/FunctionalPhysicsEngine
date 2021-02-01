package ejvr.engine.testing;

import ejvr.engine.PhysicsThread;
import processing.core.PApplet;


public class Main extends PApplet {
    public final static int SCREEN_WIDTH = 1700;
    public final static int SCREEN_HEIGHT = 900;

    static PhysicsThread t = new PhysicsThread();
    Thread thread = new Thread(t);

    public static void main(String[] args) {
        PApplet.main(new String[]{"ejvr.engine.testing.Main"});
    }




    public void settings(){
        size(SCREEN_WIDTH,SCREEN_HEIGHT);
    }

    public void setup(){


        thread.start();
    }

    public void draw(){
        background(0);
        fill(255);
        stroke(255);

        circle(mouseX,mouseY,10);



        t.state.draw(this);
    }
}
