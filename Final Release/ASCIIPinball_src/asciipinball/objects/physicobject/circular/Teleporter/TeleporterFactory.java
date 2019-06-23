package asciipinball.objects.physicobject.circular.Teleporter;

import asciipinball.Coordinate;
import asciipinball.playersandscore.PlayerManager;

import java.util.ArrayList;

/**
 * Factory für Teleporter (Factory Designpattern)
 */
public class TeleporterFactory {

    /**
     * Erstellt zwei Teleporter, verlinkt sie untereinander und gibt beide in einem Array zurück
     *
     * @param playerManager playerManager des Spiels
     * @param cor1          Koordinate, an dem sich der erste Teleporter befinden soll
     * @param cor2          Koordinate, an dem sich der zweite Teleporter befinden soll
     * @return Zwei fertig verlinkte Teleporter
     */
    public static ArrayList<Teleporter> getTeleporter(PlayerManager playerManager, Coordinate cor1, Coordinate cor2) {
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
