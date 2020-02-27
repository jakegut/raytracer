package me.jakerg.raytracer.material;

import me.jakerg.raytracer.HitRecord;
import me.jakerg.raytracer.Ray;
import me.jakerg.raytracer.ScatterRecord;
import me.jakerg.raytracer.Vector3D;

public class Dialectric extends Material {

    Vector3D transparency;
    double ref_idx;

    public Dialectric(Vector3D a, double ri){
        transparency = a;
        ref_idx = ri;
    }

    @Override
    public ScatterRecord scatter(Ray rayIn, HitRecord rec) {
        ScatterRecord sRec = new ScatterRecord();

        Vector3D outwardNormal;
        Vector3D reflected = MaterialUtil.reflect(rayIn.direction, rec.normal);
        double ni_over_nt;
        sRec.attenuation = transparency.toColor();
        Vector3D refracted;
        double cosine;
        double reflect_prob;
        if(Vector3D.dot(rayIn.direction, rec.normal) > 0){
            outwardNormal = rec.normal.scale(-1);
            ni_over_nt = ref_idx;
            cosine = Vector3D.dot(rayIn.direction, rec.normal)/rayIn.direction.length();
            cosine = Math.sqrt(1 - ref_idx*ref_idx*(1-cosine*cosine));
        } else{
            outwardNormal = rec.normal;
            ni_over_nt = 1./ref_idx;
            cosine = -1*Vector3D.dot(rayIn.direction, rec.normal)/rayIn.direction.length();
        }
        refracted = MaterialUtil.refract(rayIn.direction, outwardNormal, ni_over_nt);
        if(refracted != null)
            reflect_prob = MaterialUtil.schlick(cosine, ref_idx);
        else
            reflect_prob = 1;

        if (Math.random() < reflect_prob)
            sRec.scattered = new Ray(rec.p, reflected);
        else
            sRec.scattered = new Ray(rec.p, refracted);


        return sRec;
    }
}
