package engine;

import processing.core.PApplet;
import scala.*;
import scala.collection.*;
import scala.collection.Iterable;
import scala.collection.immutable.*;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.immutable.SortedSet;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.util.Either;

import java.util.ArrayList;

public class Main extends PApplet {
    static PhysicsThread t = new PhysicsThread();
    Thread thread = new Thread(t);

    public static void main(String[] args) {
        PApplet.main(new String[]{"engine.Main"});



    }


    public void settings(){
        size(500,500);
    }

    public void setup(){
        thread.start();
    }

    public void draw(){
        background(0);
        t.state.draw(this);
    }
}
