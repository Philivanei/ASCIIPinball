package asciipinball.objects.physicobject.circular.Teleporter;

import asciipinball.Coordinate;
import asciipinball.playersandscore.PlayerManager;

import java.util.ArrayList;

public class TeleporterFactory {
    public static ArrayList<Teleporter> getTeleporter(PlayerManager playerManager, Coordinate cor1, Coordinate cor2){
        Teleporter teleporter1 = new Teleporter(playerManager, cor1);
        Teleporter teleporter2 = new Teleporter(playerManager, cor2);

        teleporter1.addLink(teleporter2);
        teleporter2.addLink(teleporter1);

        ArrayList<Teleporter> returnArrayList = new ArrayList<>();

        returnArrayList.add(teleporter1);
        returnArrayList.add(teleporter2);

        return returnArrayList;
    }
}
