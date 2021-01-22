package engine;

import java.util.ArrayList;

public class PhysicsThread implements Runnable{
    long timer = System.nanoTime();
    public State state;

    public PhysicsThread(){
        int maxR = 800;
        int minR = 1;
        int maxV = 30;
        int minV = -30;
        ArrayList<MassEntity> initials = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            double xRand = Math.random() * (maxR - minR + 1) + minR;
            double yRand = Math.random() * (maxR - minR + 1) + minR;
            double xVRand = Math.random() * (maxV - minV + 1) + minV;
            double yVRand = Math.random() * (maxV - minV + 1) + minV;

            initials.add(new MassEntity(new VectorD(xRand,yRand),new VectorD(xVRand,yVRand),new VectorD(0,0),10,10,i));
        }

        MassEntity[] initialsArray = new MassEntity[initials.size()];
        initialsArray = initials.toArray(initialsArray);
        state = new State(initialsArray);
    }

    @Override
    public void run() {
        while(true){
            long elapsed = System.nanoTime() - timer;
            timer = System.nanoTime();
            double deltaTime = elapsed/(1000.0*1000.0*1000.0);
            //System.out.println(deltaTime);
            state = new State(state.checkForCollisions());
            state = new State(state.stepMotion(deltaTime));

        }

    }
}
