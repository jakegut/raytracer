package me.jakerg.raytracer.material;

import me.jakerg.raytracer.Vector3D;

public class MaterialUtil {

    public static Vector3D randomInUnitSphere(){
        Vector3D p;
        do{
            p = new Vector3D(Math.random(), Math.random(), Math.random()).scale(2).sub(new Vector3D(1));
        } while(Vector3D.dot(p, p) >= 1.);
        return p;
    }

    public static Vector3D reflect(Vector3D v, Vector3D normal){
        return v.sub(normal.scale(2 * Vector3D.dot(normal, v)));
    }

    public static Vector3D refract(Vector3D v, Vector3D n, double ni_over_nt) {
        Vector3D uv = Vector3D.normalize(v);
        double dt = Vector3D.dot(uv, n);
        double disc = 1. - ni_over_nt*ni_over_nt*(1-dt*dt);
        if(disc > 0)
            return uv.sub(n.scale(dt)).scale(ni_over_nt).sub(n.scale(Math.sqrt(disc)));

        return null;
    }

    public static double schlick(double cosine, double ref_idx) {
        double r0 = (1-ref_idx)/(1+ref_idx);
        r0 *= r0;
        return r0 + (1-r0)*Math.pow(1-cosine,5);
    }
}
