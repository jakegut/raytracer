package me.jakerg.raytracer;

public class Ray {

    public Vector3D origin;
    public Vector3D direction;

    public Ray(Vector3D o, Vector3D d){
        origin = o;
        direction = d;
    }

    public Vector3D pointAt(double t){
        return origin.add(direction.scale(t));
    }

    public void setDirection(Vector3D direction){
        this.direction = direction;
    }

    public void setOrigin(Vector3D origin){
        this.origin = origin;
    }
}
