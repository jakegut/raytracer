package me.jakerg.raytracer.light;

import me.jakerg.raytracer.HitRecord;
import me.jakerg.raytracer.Vector3D;
import me.jakerg.raytracer.color.Color;

public class PointLight extends Light{

    Vector3D pos;

    public PointLight(Color color, double intensity, Vector3D pos) {
        super(color, intensity);
        this.pos = pos;
    }

    @Override
    public LightRecord illuminate(HitRecord rec) {
        LightRecord lRec = new LightRecord();
        lRec.lightDir = rec.p.sub(pos);
        double r2 = lRec.lightDir.length();
        lRec.distance = Math.sqrt(r2);
        lRec.lightDir = Vector3D.normalize(lRec.lightDir);
        lRec.lightIntensity = color.scale(intensity).div(4 * Math.PI * r2);
        return lRec;
    }
}
