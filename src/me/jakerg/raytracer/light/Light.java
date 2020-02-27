package me.jakerg.raytracer.light;

import me.jakerg.raytracer.HitRecord;
import me.jakerg.raytracer.color.Color;

public abstract class Light {
    Color color;
    double intensity;

    public Light(Color color, double intensity){
        this.color = color;
        this.intensity = intensity;
    }

    public abstract LightRecord illuminate(HitRecord rec);
}
