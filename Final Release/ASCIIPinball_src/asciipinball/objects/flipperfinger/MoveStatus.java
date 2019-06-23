package asciipinball.objects.flipperfinger;

/**
 * Bewegungsstatus der Flipperfinger
 */
public enum MoveStatus {
    /**
     * Flipperfinger ist in Bewegung nach oben
     */
    UP,
    /**
     * Flipperfinger ist nicht in Bewegung (ganz oben oder ganz unten)
     */
    STOP,
    /**
     * Flipperfinger ist in Bewegung nach unten
     */
    DOWN
}
