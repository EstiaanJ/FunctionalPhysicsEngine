package math;

public class VectorIntegration {
    public static VectorDouble stepPosition(VectorDouble velocity, VectorDouble pos, double time) {
        return pos.$plus(velocity.scale(time));
    }

    public static VectorDouble stepVelocity(VectorDouble acceleration, VectorDouble velocity, double time){
        return velocity.$plus(acceleration.scale(time));
    }

    public static VectorDouble stepAcceleration(VectorDouble netForce, double mass){
        return new VectorDouble(netForce.x()/mass,netForce.y()/mass);
    }

    public static double stepRotation(double angularVelocity, double bearing, double time){
        return bearing + (angularVelocity/time);
    }

    public static double stepAngularVelocity(double angularAcceleration, double angularVelocity, double time){
        return angularVelocity + (angularAcceleration/time);
    }

    public static double stepAngularAcceleration(double torque, double momentOfInertia){
        return torque/momentOfInertia;
    }
}
