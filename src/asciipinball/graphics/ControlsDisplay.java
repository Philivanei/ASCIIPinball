package asciipinball.graphics;

/**
 * Erklärung der Steuerung am rechten unteren Bildschirmrand
 */
public class ControlsDisplay {

    /**
     * Erstellt ein neues ControlsDisplay
     */
    public ControlsDisplay(){

    }

    /**
     * Gibt die Steuerungserklärung als String zurück
     * @return Steuerungserklärung
     */
    public String getString(){
        return "Steuerung:\n\n" +
                "Alt     L Finger \n" +
                "NUM0 R Finger\n" +
                "Leer  Abschuss\n" +
                "R          Reset";
    }
}
