package ejvr.engine;

import ejvr.engine.testing.Main;
import ejvr.math.VectorDouble;

import java.util.ArrayList;

public class PhysicsThread implements Runnable{
    public static int numOfCols = 0;
    long timer = System.nanoTime();
    public WorldState state;

    public PhysicsThread(){
        int maxM = 30;
        int minM = 3;
        int minR = 1;
        int maxV = 300;
        int minV = -1;
        int maxC = 255;
        int minC = 0;
        ArrayList<MassEntity> initials = new ArrayList<>();
        for(int i = 0; i < 600; i++){
            double xRand = Math.random() * (Main.SCREEN_WIDTH - minR + 1) + minR;
            double yRand = Math.random() * (Main.SCREEN_HEIGHT - minR + 1) + minR;
            double xVRand = Math.random() * (maxV - minV + 1) + minV;
            double yVRand = Math.random() * (maxV - minV + 1) + minV;
            int cRand = (int)(Math.random() * (maxC - minC + 1) + minC);
            double mass = Math.random() * (maxM - minM + 1) + minM;

            initials.add(new MassEntity(new VectorDouble(xRand,yRand),new VectorDouble(xVRand,yVRand),new VectorDouble(0,0),mass,mass,cRand,i));
        }

        MassEntity[] initialsArray = new MassEntity[initials.size()];
        initialsArray = initials.toArray(initialsArray);
        state = new WorldState(initialsArray);
    }

    @Override
    public void run() {
        ArrayList<Double> dtimes = new ArrayList<>();
        while(true){
            long elapsed = System.nanoTime() - timer;
            timer = System.nanoTime();
            double deltaTime = elapsed/(1000.0*1000.0*1000.0);
            dtimes.add(deltaTime);
            //System.out.println(deltaTime);
            state = new WorldState(state.resolveCollisions());
            state = new WorldState(state.stepMotion(deltaTime));

            if(dtimes.size() > 600){
                double total = 0;
                for (double deltaT:dtimes) {
                    total += deltaT;
                }
                double aveFrameTime = total/dtimes.size();
                double aveFPS = 1/aveFrameTime;
                System.out.println("ave FPS: " + aveFPS);
                dtimes = new ArrayList<>();
                System.out.println("Ave Speed: " + state.calculateEnergy());
                System.out.println("Num of Colls" + numOfCols);
            }

        }

    }
}
