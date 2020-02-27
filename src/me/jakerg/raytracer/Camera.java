package me.jakerg.raytracer;

public class Camera {
    Vector3D origin;
    Vector3D lowerLeftCorner;
    Vector3D horizontal;
    Vector3D vertical;
    Vector3D u, v, w;
    double lensRadius;

    public Camera(Vector3D lookFrom, Vector3D lookAt, Vector3D vup, double vFov, double aspect, double aperature,
                  double focusDist){
        lensRadius = 1/(aperature*2);
        double theta = vFov*Math.PI/180;
        double halfWidth = Math.tan(theta/2);
        double halfHeight = halfWidth / aspect;
        origin = lookFrom;
        w = Vector3D.normalize(lookFrom.sub(lookAt));
        u = Vector3D.normalize(Vector3D.cross(vup, w));
        v = Vector3D.cross(w, u);
        lowerLeftCorner = origin.sub(u.scale(halfWidth*focusDist));
        lowerLeftCorner = lowerLeftCorner.sub(v.scale(halfHeight*focusDist));
        lowerLeftCorner = lowerLeftCorner.sub(w.scale(focusDist));
        System.out.println(lowerLeftCorner);
        horizontal = u.scale(halfWidth*2*focusDist);
        vertical = v.scale(halfHeight*2*focusDist);
    }

    public Ray getRay(double s, double t){
        Vector3D rayD = lowerLeftCorner.add(horizontal.scale(s));
        rayD = rayD.add(vertical.scale(t));
        rayD = rayD.sub(origin);
        return new Ray(origin, rayD);
    }

    public static Vector3D randomInUnitDisc(){
        Vector3D p;
        do{
            p = new Vector3D(Math.random(), Math.random(), 0).scale(2)
;           p = p.sub(new Vector3D(1, 1, 0));
        } while(Vector3D.dot(p, p) >= 1.0);
        return p;
    }
}
