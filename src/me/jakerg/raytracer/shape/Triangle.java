package me.jakerg.raytracer.shape;

import me.jakerg.raytracer.HitRecord;
import me.jakerg.raytracer.Ray;
import me.jakerg.raytracer.Vector3D;
import me.jakerg.raytracer.material.Material;

public class Triangle extends Shape{

    Vector3D[] p;

    public Triangle(Material m, Vector3D p1, Vector3D p2, Vector3D p3) {
        super(m);
        p = new Vector3D[3];
        p[0] = p1;
        p[1] = p2;
        p[2] = p3;
    }

    @Override
    public Vector3D getNormal(Vector3D p) {
        return null;
    }

    @Override
    public HitRecord hit(Ray r, double tMin, double tMax) {
        HitRecord rec = new HitRecord();
        Vector3D e1 = p[1].sub(p[0]);
        Vector3D e2 = p[2].sub(p[0]);
        Vector3D ortho = Vector3D.cross(r.direction, e2);
        double det = Vector3D.dot(ortho, e1);

        if(Math.abs(det) < 0.0001) return null;

        double invDet = 1/ det;

        Vector3D tVec = r.origin.sub(p[0]);
        double u = Vector3D.dot(tVec, ortho) * invDet;
        if(u < 0 || u > 1) return null;

        Vector3D qVec = Vector3D.cross(tVec, e1);
        double v = Vector3D.dot(r.direction, qVec) * invDet;
        if(v < 0 || u + v > 1) return null;

        double t = Vector3D.dot(e2, qVec) * invDet;
        if(t > tMax || t < tMin) return null;

        Vector3D normal = Vector3D.normalize(Vector3D.cross(e1, e2));

        if(det < 0)
            normal = normal.scale(-1);

        rec.t = t;
        rec.p = r.pointAt(rec.t);
        rec.normal = normal;
        rec.material = material;

        return rec;

    }
}
