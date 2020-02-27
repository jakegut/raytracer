package me.jakerg.raytracer.material;

import me.jakerg.raytracer.HitRecord;
import me.jakerg.raytracer.Ray;
import me.jakerg.raytracer.ScatterRecord;
import me.jakerg.raytracer.Vector3D;
import me.jakerg.raytracer.color.Color;

public class Lambertian extends Material{

    Color albedo;

    public Lambertian(Color c){
        albedo = c;
    }


    @Override
    public ScatterRecord scatter(Ray rayIn, HitRecord rec) {
        ScatterRecord sRec = new ScatterRecord();
        Vector3D target = rec.p.add(rec.normal).add(MaterialUtil.randomInUnitSphere());
        sRec.scattered =  new Ray(rec.p, target.sub(rec.p));
        sRec.attenuation = albedo;
        return sRec;
    }

}
