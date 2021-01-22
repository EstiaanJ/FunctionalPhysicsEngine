package engine;

import processing.core.PApplet;


public class Main extends PApplet {
    static PhysicsThread t = new PhysicsThread();
    Thread thread = new Thread(t);

    public static void main(String[] args) {
        PApplet.main(new String[]{"engine.Main"});



    }


    public void settings(){
        size(900,900);
    }

    public void setup(){
        thread.start();
    }

    public void draw(){
        background(0);
        t.state.draw(this);
    }
}
