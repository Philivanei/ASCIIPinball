package asciipinball.levels;

import asciipinball.Coordinate;
import asciipinball.corelogic.players.PlayerManager;
import asciipinball.Settings;
import asciipinball.interfaces.Coverable;
import asciipinball.objects.nophysicsobject.NonPhysicEntity;
import asciipinball.objects.nophysicsobject.pointdoor.PointDoor;
import asciipinball.objects.nophysicsobject.pointdoor.PointDoorFactory;
import asciipinball.objects.physicobject.PhysicEntity;
import asciipinball.objects.physicobject.circular.Bumper;
import asciipinball.objects.physicobject.circular.JointCover;
import asciipinball.objects.physicobject.polygonial.LineWall;
import asciipinball.objects.physicobject.polygonial.RotatingCross;
import asciipinball.shapes.Line;

import java.util.ArrayList;

public class Levels {

    private PlayerManager playerManager;

    public Levels(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }


    public PhysicEntity[] getLevel1PhysicEntities() {
        return addJointCovers(new PhysicEntity[]{

                //diagonal lines in the middle
                new LineWall(playerManager, 20, 60, 40, 40),
                new LineWall(playerManager, 80, 60, 60, 40),

                //lines on the upper edges
                new LineWall(playerManager, 80, 135, 100, 115),
                new LineWall(playerManager, 20, 135, 0, 115),

                //lines next to the FlipperFingers
                new LineWall(playerManager, 10, 25, (((float) Settings.WIDTH / 2) - (Settings.HOLE_WIDTH / 2)), 15),
                new LineWall(playerManager, 90, 25, (((float) Settings.WIDTH / 2) + (Settings.HOLE_WIDTH / 2)), 15),

                //lines where the ball starts at the right side
                new LineWall(playerManager, 90, 25, 90, 0),
                new LineWall(playerManager, 90, 1, 100, 1),

                //Bumper in the middle
                new Bumper(playerManager, Settings.WIDTH / 2, Settings.HEIGHT / 2, 4),

                //rotating cross on the left side up
                new RotatingCross(playerManager, 30, 105, 8, 0.0008f, false),
                //rotating cross on the right side up
                new RotatingCross(playerManager, 70, 105, 8, 0.0008f, true)

        });
    }


    public NonPhysicEntity[] getLevel1NoPhysicEntities() {

        //creating the points, that get yellow by hitting them called PointDoors
        Coordinate[] coordinates = new Coordinate[]{
                new Coordinate(20, 80),
                new Coordinate(50, 120),
                new Coordinate(80, 80)};
        PointDoor[] pointDoors = PointDoorFactory.createPointDoors(playerManager, coordinates, 3, 1000);

        return new NonPhysicEntity[]{
                pointDoors[0],
                pointDoors[1],
                pointDoors[2],
        };
    }

    private PhysicEntity[] addJointCovers(PhysicEntity[] physicEntities) {
        ArrayList<PhysicEntity> arrayList = new ArrayList<>();

        for (PhysicEntity entity : physicEntities) {
            if (entity != null) {
                arrayList.add(entity);
                if (entity instanceof Coverable) {

                    Line[] linesToCover = ((Coverable) entity).getAllLines();

                    for (Line line : linesToCover) {
                        arrayList.add(new JointCover(playerManager, line.getX1(), line.getY1()));
                        arrayList.add(new JointCover(playerManager, line.getX2(), line.getY2()));
                    }


                }
            }
        }

        PhysicEntity[] returnArray = new PhysicEntity[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i++) {
            returnArray[i] = arrayList.get(i);
        }

        return returnArray;

    }

}
