package me.jakerg.raytracer;

import me.jakerg.raytracer.light.Light;
import me.jakerg.raytracer.shape.Shape;

import java.util.List;

public class Scene {

    List<Shape> shapes;
    List<Light> lights;

    public Scene(List<Shape> shapes, List<Light> lights){
        this.shapes = shapes;
        this.lights = lights;
    }

    public HitRecord hit(Ray r, double tMin, double tMax){
        HitRecord hit = new HitRecord();
        hit.t = tMax;

        for(Shape shape : shapes){
            HitRecord rec = shape.hit(r, tMin, hit.t);
            if(rec != null){
                hit = rec;
            }
        }


        return hit;
    }
}
