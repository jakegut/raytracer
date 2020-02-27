package me.jakerg.raytracer.color;

import me.jakerg.raytracer.Vector3D;

public class Color extends Vector3D {

    public Color(double n) {
        super(n);
    }

    public Color(double r, double g, double b) {
        super(r, g, b);
    }

    public double r() { return this.x(); }
    public double g() { return this.y(); }
    public double b() { return this.z(); }

    public int toRGB(){
        return (int) (r()*255) << 16 | (int) (g()*255) << 8 | (int) (b()*255);
    }
}
