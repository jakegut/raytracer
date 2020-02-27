package me.jakerg.raytracer;

import me.jakerg.raytracer.material.Material;

public class HitRecord {
    public double t;
    public Vector3D p;
    public Vector3D normal;
    public Material material;
}
