package ejvr.engine;

import ejvr.math.PointDouble;
import ejvr.math.VectorDouble;

public class PhysicsUtil {
    public static boolean circleContainsPoint(PointDouble point, PointDouble circleCenter, double circleRad){
        if(circleCenter.distanceTo(point) < circleRad) {
            return true;
        } else {
            return false;
        }
    }
}
