package me.jakerg.raytracer.material;

import me.jakerg.raytracer.HitRecord;
import me.jakerg.raytracer.Ray;
import me.jakerg.raytracer.ScatterRecord;
import me.jakerg.raytracer.color.Color;

public abstract class Material {
    public abstract ScatterRecord scatter(Ray rayIn, HitRecord rec);

    public Color emitted(HitRecord rec){
        return new Color(0);
    }

}
