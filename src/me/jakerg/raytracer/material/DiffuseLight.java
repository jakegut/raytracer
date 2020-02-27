package me.jakerg.raytracer.material;

import me.jakerg.raytracer.HitRecord;
import me.jakerg.raytracer.Ray;
import me.jakerg.raytracer.ScatterRecord;
import me.jakerg.raytracer.color.Color;

public class DiffuseLight extends Material{

    Color emit;

    public DiffuseLight(Color emit){
        this.emit = emit;
    }

    @Override
    public ScatterRecord scatter(Ray rayIn, HitRecord rec) {
        return new ScatterRecord();
    }

    @Override
    public Color emitted(HitRecord rec){
        return emit;
    }
}
