package me.jakerg.raytracer;

import me.jakerg.raytracer.color.Color;

public class Vector3D {
    private double x;

    public static Vector3D makePositive(Vector3D v) {
        return new Vector3D(
                (v.x > 0 ? v.x : -v.x),
                (v.y > 0 ? v.y : -v.y),
                (v.z > 0 ? v.z : -v.z)
        );
    }

    public double x() { return this.x; }
    public void setX(double x) { this.x = x;}
    private double y;
    public double y() { return this.y; }
    public void setY(double y) { this.y = y; }
    private double z;
    public double z() { return this.z; }
    public void setZ(double z) { this.z = z;}

    public Vector3D(double n){
        this(n, n, n);
    }

    public Vector3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public <T extends Vector3D> Vector3D add(T other){
        return new Vector3D(this.x + other.x(), this.y + other.y(), this.z + other.z());
    }

    public <T extends Vector3D> Vector3D sub(T other){
        return new Vector3D(this.x - other.x(), this.y - other.y(), this.z - other.z());
    }

    public <T extends Vector3D> Vector3D mult(T other){
        return new Vector3D(this.x() * other.x(), this.y() * other.y(), this.z() * other.z());
    }

    public Vector3D scale(double scalar) {
        return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3D div(double scalar){
        return new Vector3D(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    public static <T extends Vector3D> double dot(T v, T other){
        return v.x()*other.x() + v.y()*other.y() + v.z()*other.z();
    }

    private double lengthSquared(){
        return dot(this, this);
    }

    public double length(){
        return Math.sqrt(lengthSquared());
    }

    public static Vector3D normalize(Vector3D v){
        double len = v.length();

        return new Vector3D(v.x / len, v.y / len, v.z / len);
    }

    public static Vector3D cross(Vector3D v1, Vector3D v2){
        return new Vector3D((v1.y*v2.z - v1.z*v2.y),
                -(v1.x*v2.z - v1.z*v2.x),
                (v1.x*v2.y - v1.y*v2.x));
    }

    public Color toColor(){
        return new Color(this.x(), this.y(), this.z());
    }

    public String toString(){
        return "(" + x + ", " + y + ", " + z + ")";
    }


}
