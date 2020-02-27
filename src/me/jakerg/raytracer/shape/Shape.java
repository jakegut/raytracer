package me.jakerg.raytracer.shape;

import me.jakerg.raytracer.HitRecord;
import me.jakerg.raytracer.Ray;
import me.jakerg.raytracer.Vector3D;
import me.jakerg.raytracer.material.Material;

public abstract class Shape {

    public Shape(Material m){
        this.material = m;
    }

    Material material;

    public abstract Vector3D getNormal(Vector3D p);

    public abstract HitRecord hit(Ray r, double tMin, double tMax);

    public Material getMaterial() { return this.material; }


}
