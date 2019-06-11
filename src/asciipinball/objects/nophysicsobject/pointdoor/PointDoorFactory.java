package asciipinball.objects.nophysicsobject.pointdoor;

import asciipinball.Coordinate;
import asciipinball.corelogic.playersandscore.PlayerManager;

public abstract class PointDoorFactory {

    public static PointDoor[] createPointDoors(PlayerManager playerManager, Coordinate[] coordinates, float radius , int scoreIfTriggered){

        int numberOfPointDoorsToGenerate = 0;
        for (Coordinate cor: coordinates) {
            if(cor != null){
                numberOfPointDoorsToGenerate++;
            }
        }

        PointDoor[] returnPointDoors = new PointDoor[numberOfPointDoorsToGenerate];

        int returnPointDoorsIterator = 0;

        for (Coordinate coordinate : coordinates) {
            if (coordinate != null) {
                returnPointDoors[returnPointDoorsIterator] = new PointDoor(playerManager, coordinate, radius, scoreIfTriggered);
                returnPointDoorsIterator++;
            }
        }

        linkAll(returnPointDoors);

        return  returnPointDoors;
    }

    private static void linkAll(PointDoor[] pointDoors){
        for(int i = 0; i < pointDoors.length; i++){
            for(int j = 0; j < pointDoors.length; j++){
                if(i != j){ //This is used so the point Door does not links himself
                    pointDoors[i].addLink(pointDoors[j]);
                }
            }
        }
    }

}
