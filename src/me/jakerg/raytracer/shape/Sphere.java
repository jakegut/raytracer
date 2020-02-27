package me.jakerg.raytracer.shape;

import me.jakerg.raytracer.HitRecord;
import me.jakerg.raytracer.Ray;
import me.jakerg.raytracer.Vector3D;
import me.jakerg.raytracer.material.Material;

public class Sphere extends Shape {

    Vector3D center;
    double radius;

    public Sphere(Material m, Vector3D center, double r) {
        super(m);
        this.center = center;
        this.radius = r;
    }

    @Override
    public Vector3D getNormal(Vector3D p) {
        return p.sub(center).div(radius);
    }

    @Override
    public HitRecord hit(Ray r, double tMin, double tMax) {
        HitRecord rec = new HitRecord();
        Vector3D oc = r.origin.sub(center);
        double a = Vector3D.dot(r.direction, r.direction);
        double b = Vector3D.dot(oc, r.direction);
        double c = Vector3D.dot(oc, oc) - radius*radius;
        double disc = b*b - a*c;

        if(disc > 0){
            double temp = (-b - Math.sqrt(disc))/a;
            if(temp < tMax && temp > tMin){
                rec.t = temp;
                rec.p = r.pointAt(rec.t);
                rec.normal = rec.p.sub(center).div(radius);
                rec.material = material;
                return rec;
            }


            temp = (-b + Math.sqrt(disc))/a;
            if(temp < tMax && temp > tMin){
                rec.t = temp;
                rec.p = r.pointAt(rec.t);
                rec.normal = rec.p.sub(center).div(radius);
                rec.material = material;
                return rec;
            }
        }

        return null;

    }
}
