package asciipinball.objects.nophysicsobject.pointdoor;

import asciipinball.Coordinate;
import asciipinball.corelogic.playersandscore.PlayerManager;

/**
 * Fabrik für PointDoors
 */
public abstract class PointDoorFactory {

    /**
     * Erstellt eine anzahl an PointDoors die sich gegenseitig kennen
     * @param playerManager playerManager des Spiels
     * @param coordinates Koordinaten an denen Point doors erstellt werden sollen.
     * @param radius Radius der Pointdoors
     * @param scoreIfTriggered Punkte die dem Spieler hinzugefügt werden sollen, wenn er alle zusammengehörigen Pointdoors eingesammelt hat
     * @return
     */
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

    /**
     * Teilt jeder PointDoor aus pointDoors alle anderen PointDoors aus pointDoors mit
     * @param pointDoors Array an pointDoors die sich gegenseitig kennen sollen
     */
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
