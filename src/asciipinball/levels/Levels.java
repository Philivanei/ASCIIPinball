package asciipinball.levels;

import asciipinball.Settings;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.objects.physicobject.circular.Bumper;
import asciipinball.objects.physicobject.polygonial.LineWall;
import asciipinball.objects.physicobject.polygonial.TriangleWall;

public abstract class Levels {

    public static final PhysicEntity[] LEVEL1 = new PhysicEntity[]{
            new LineWall(20,40,40,20),
            new LineWall(80,40,60,20),
            new TriangleWall(0,25, (((float)Settings.WIDTH/2) - (Settings.HOLE_WIDTH/2)), 15,0,15),
            new TriangleWall(100,25, (((float)Settings.WIDTH/2) + (Settings.HOLE_WIDTH/2)), 15,100,15),
            new LineWall((((float)Settings.WIDTH/2) - (Settings.HOLE_WIDTH/2)),15,(((float)Settings.WIDTH/2) - (Settings.HOLE_WIDTH/2)),0),
            new LineWall((((float)Settings.WIDTH/2) + (Settings.HOLE_WIDTH/2)),15,(((float)Settings.WIDTH/2) + (Settings.HOLE_WIDTH/2)),0),
            new Bumper(Settings.WIDTH/2,Settings.HEIGHT/2, 4)
    };

}
