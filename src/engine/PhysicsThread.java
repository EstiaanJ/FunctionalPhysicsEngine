package engine;

public class PhysicsThread implements Runnable{
    long timer = System.nanoTime();
    public State state;

    public PhysicsThread(){
        MassEntity[] initials = {new MassEntity(new VectorD(100,100),new VectorD(5,100),new VectorD(0,0),10,10,0)};
        state = new State(initials);
    }

    @Override
    public void run() {
        while(true){
            long elapsed = System.nanoTime() - timer;
            timer = System.nanoTime();
            double deltaTime = elapsed/(1000.0*1000.0*1000.0);
            //System.out.println(deltaTime);
            state = new State(state.stepMotion(deltaTime));
        }

    }
}
