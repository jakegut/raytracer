package me.jakerg.raytracer.material;

import me.jakerg.raytracer.HitRecord;
import me.jakerg.raytracer.Ray;
import me.jakerg.raytracer.ScatterRecord;
import me.jakerg.raytracer.Vector3D;
import me.jakerg.raytracer.color.Color;

public class Metal extends Material {

    private double fuzz;
    private Color albedo;

    public Metal(Color albedo, double fuzz){
        if (fuzz < 1)
            this.fuzz = fuzz;
        else
            this.fuzz = 1;
        this.albedo = albedo;
    }

    @Override
    public ScatterRecord scatter(Ray rayIn, HitRecord rec) {
        Vector3D reflected = MaterialUtil.reflect(Vector3D.normalize(rayIn.direction), rec.normal);
        Ray scatter = new Ray(rec.p, reflected.add(MaterialUtil.randomInUnitSphere().scale(fuzz)));
        ScatterRecord sRec = new ScatterRecord();
        sRec.scattered = scatter;
        sRec.attenuation = albedo;

        if(Vector3D.dot(Vector3D.normalize(scatter.direction), rec.normal) <= 0)
            sRec.scattered = null;

        return sRec;
    }

}
