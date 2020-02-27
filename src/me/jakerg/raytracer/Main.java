package me.jakerg.raytracer;

import me.jakerg.raytracer.color.Color;
import me.jakerg.raytracer.light.Light;
import me.jakerg.raytracer.material.Dialectric;
import me.jakerg.raytracer.material.DiffuseLight;
import me.jakerg.raytracer.material.Lambertian;
import me.jakerg.raytracer.material.Metal;
import me.jakerg.raytracer.shape.Shape;
import me.jakerg.raytracer.shape.Sphere;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        int nx = 400;
        int ny = 300;
        int ns = 1000;

        Scene scene = generateScene();

        Vector3D lookFrom = new Vector3D(0,0,1);
        Vector3D lookAt = new Vector3D(0,0,-1);
        double distToFocus = lookFrom.sub(lookAt).length();
        double aperture = 7.1;

        Camera cam = new Camera(lookFrom, lookAt, new Vector3D(0,1,0), 90, (double) nx / (double) ny,
                aperture, distToFocus);

        DrawingPanel panel = new DrawingPanel(nx, ny);
        Graphics g = panel.getGraphics();
        BufferedImage img = new BufferedImage(nx, ny, BufferedImage.TYPE_INT_ARGB);

        for(int y = 0; y < ny; y++) {
            final int iY = y;
            IntStream.range(0, nx).parallel().forEach(iX->{
                double delta = 1.0/ns;

                double[] hs = new double[ns];
                double[] vs = new double[ns];
                for(int i = 0; i < ns; i++){
                    hs[i] = (i+Math.random()) * delta;
                    vs[i] = (i+Math.random()) * delta;
                }

                permute(hs);
                permute(vs);

                Color col = new Color(0);
                for (int s = 0; s < ns; s++) {
                    double u = (iX + hs[s]) / (double) nx;
                    double v = (iY + vs[s]) / (double) ny;
                    Ray r = cam.getRay(u, v);
//                    System.out.println(r.direction);
                    col = col.add(getColor(r, scene, 0)).toColor();
                }
                col = col.div(ns).toColor();
                col = new Color(Math.sqrt(col.x()), Math.sqrt(col.y()), Math.sqrt(col.z()));
                if(col.x() > 1) col.setX(1);
                if(col.y() > 1) col.setY(1);
                if(col.z() > 1) col.setZ(1);
                java.awt.Color colr = new java.awt.Color((int) (255 * col.r()), (int) (255 * col.g()), (int) (255 * col.b()));
                img.setRGB(iX, ny - iY - 1, colr.getRGB());
            });
            g.drawImage(img, 0, 0, null);
        }



        g.drawImage(img, 0, 0, null);



        try {
            ImageIO.write(img, "png", new File("test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void permute(double[] p) {
        for(int i = p.length-1; i > 0; i--){
            int target = (int)(Math.random()*(i+1));
            double temp = p[i];
            p[i] = p[target];
            p[target] = temp;
        }
    }

    private static Scene generateScene() {
        List<Shape> shapes = new LinkedList<>();
//        shapes.add(new Sphere(new Lambertian(new Color(0.5)), new Vector3D(0, -1000, 0), 1000));
//
//        shapes.add(new Sphere(new Lambertian(new Color(0.4, 0.2, 0.1)), new Vector3D(0, 1, 0), 1.));
//        shapes.add(new Sphere(new Lambertian(new Color(0.2, 0.7, 0.1)), new Vector3D(-4, 1, 0), 1.));
//        shapes.add(new Sphere(new Lambertian(new Color(0.8, 0.1, 0.1)), new Vector3D(4, 1, 0), 1.));

        shapes.add(new Sphere(new Lambertian(new Color(0.1, 0.2, 0.5)), new Vector3D(0, 0, -1), 0.5));
        shapes.add(new Sphere(new Lambertian(new Color(0.8, 0.8, 0.0)), new Vector3D(0,-100.5,-1), 100));
        shapes.add(new Sphere(new Metal(new Color(0.8, 0.6, 0.2), 0), new Vector3D(1,0,-1), 0.5));
        shapes.add(new Sphere(new Dialectric(new Vector3D(0.8, 0.6, 0.2), 1.5), new Vector3D(-1, 0, -1), 0.5));
//        shapes.add(new Triangle(new Dialectric(new Vector3D(0.8, 0.2, 0.2), 1.5), new Vector3D(-1, 0.5, -1),
//                new Vector3D(-1.5, -0.5, -1), new Vector3D(-0.5, -0.5, -1)));
        shapes.add(new Sphere(new DiffuseLight(new Color(10)), new Vector3D(0, 3, -1), 0.5 ));
        List<Light> lights = new LinkedList<>();
        return new Scene(shapes, lights);
    }

    private static Color getColor(Ray r, Scene scene, int depth) {
        HitRecord rec = scene.hit(r, 0.001, Double.MAX_VALUE);
        if(rec.material != null){
            ScatterRecord scattered = rec.material.scatter(r, rec);
            Color attenuation = scattered.attenuation;
            Color emitted = rec.material.emitted(rec);
            if(depth < 50 && scattered.scattered != null){

                return getColor(scattered.scattered, scene, depth+1).mult(attenuation).add(emitted).toColor();
            } else {
                return emitted;
            }
        } else {
//            Vector3D unit_dir = Vector3D.normalize(r.direction);
//			double t = 0.5*(unit_dir.y() + 1.0);
//			return new Vector3D(1.0,1.0,1.0).scale(1.0-t).add(new Vector3D(0.5,0.7,1.0).scale(t)).toColor(); //create a gradient
            return new Color(0.);
        }
    }
}
