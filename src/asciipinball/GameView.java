package asciipinball;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Ein Fenster, dass die Anzeige von ASCI-Spielen erlaubt, Ton ausgibt und
 * Tastendr�cke und MausKlicks als Events zur�ckliefert.<br>
 * <br>
 *
 * @author Andreas Berl
 * @version 4.25(28.05.2019)
 * <p>
 * Updates:
 * Neuer Canvas
 * Buchstaben mittig Ausgerichtet
 * JavaDoc �berarbeitet
 * Gro�e Buchstaben
 * Kein Gr��enlimit
 */
public class GameView {

    /**
     * Kleine Festergr��e
     */
    public static final int WINDOWSIZE_SMALL = 0;
    /**
     * Normale Festergr��e (Standard)
     */
    public static final int WINDOWSIZE_NORMAL = 1;
    /**
     * Gro�e Festergr��e
     */
    public static final int WINDOWSIZE_LARGE = 2;
    /**
     * Fester maximiert
     */
    public static final int WINDOWSIZE_MAXIMIZED = 3;
    final static String STANDARDTITEL = "GameView IV";
    private final static String STATUSRECHTS = "GameView IV (2019) - Prof. Dr. Andreas Berl - TH Deggendorf ";
    private final static String SPLASH = "      ASCII-\n\n" + "GameView IV (2019)";
    private final static char STANDARDNONTRANSPARENTSPACE = ';';
    private static GameView gameView;
    private final String framesLost = " --> Frames verloren!";
    private LinkedBlockingQueue<KeyEvent> keyboardEvents;
    private LinkedBlockingQueue<MouseEvent> mousePointerEvents;

    private JFrame frame;
    private TextPanel area;
    private String statusTextLinks;
    private JLabel statusLabelLinks;

    private int zeilen;
    private int spalten;
    private int windowSize;
    private boolean useMouse;
    private boolean nullCursor;

    private Canvas canvas;
    private HashMap<Character, Color> colormap;
    private Color foregroundColor;
    private Color backgroundColor;
    private char nonTransparentSpace;

    private long lastTime;
    private int countPrints;
    private boolean moved;
    private Timer mouseTimer;
    private long mouseMovementTimer;
    private boolean isSplashFinished;
    private Timer resizeTimer;

    /**
     * Erzeugt ein Fenster zur Anzeige von ASCII-Spielen, mit Sound, Tastatur- und
     * Maus-Eingaben. Es wird ein Fenster mit <code>lines</code> Zeilen und <code>rows</code> Spalten erzeugt. Bitte initialisieren Sie
     * das Fenster mit der Aufl�sung, die am h�ufigsten ben�tigt wird, da die Form des Fensters davon abh�ngt. Sie k�nnen
     * die Aufl�sung sp�ter mit der Methode <code>changeResolution(int lines, int rows)</code> �ndern.
     *
     * @param lines Die gew�hlte Aufl�sung (Zeilen).
     * @param rows  Die gew�hlte Aufl�sung (Spalten).
     * @param title Der Titel f�r die Titelzeile des Fensters.
     */
    public GameView(int lines, int rows, String title) {
        // Check parameters
        if (gameView != null) {
            System.err.println("GameView wurde bereits gestartet!");
            System.exit(1);
        } else {
            GameView.gameView = this;
        }
        // Initialize variables
        setResolution(lines, rows);
        this.foregroundColor = Color.white;
        this.backgroundColor = Color.black;
        this.useMouse = false;
        this.nullCursor = false;
        this.nonTransparentSpace = STANDARDNONTRANSPARENTSPACE;
        this.lastTime = 0;
        this.countPrints = 0;
        this.moved = true;
        this.mouseTimer = null;
        this.mouseMovementTimer = 0;
        this.isSplashFinished = false;
        this.keyboardEvents = new LinkedBlockingQueue<>();
        this.mousePointerEvents = new LinkedBlockingQueue<>();
        this.windowSize = WINDOWSIZE_NORMAL;
        fillColormap();
        title = (title == "") ? STANDARDTITEL : title;
        intitFrame(title);
    }

    /**
     * Wandelt ein char[][] in einen String um.
     *
     * @param chars Das char[][], welches umgewandelt werden soll.
     * @return Das umgewandelte char[][] als String
     */
    public static String convertCharArrayToString(char[][] chars) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[i].length; j++) {
                sb.append(chars[i][j]);
            }
            if (i < chars.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Wandelt einen String in ein rechteckiges char[][] um. L�cken werden mit
     * Leerzeichen aufgef�llt.
     *
     * @param string Der String der umgewandelt werden soll.
     * @return Der umgewandelte String als Rechteck.
     */
    public static char[][] convertStringToCharArray(String string) {
        if (string.endsWith("\n")) {
            string = string + " ";
        }
        String[] lines = string.split("\r\n|\n");
        int z = lines.length;
        int s = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() > s) {
                s = lines[i].length();
            }
        }
        char[][] ca = new char[z][s];
        for (int i = 0; i < z; i++) {
            for (int j = 0; j < s; j++) {
                if (j < lines[i].length() && lines[i].length() > 0) {
                    ca[i][j] = lines[i].charAt(j);
                } else {
                    ca[i][j] = ' ';
                }
            }
        }
        return ca;
    }

    /**
     * Legt ein Symbol f�r die Titelleiste fest. Das Symbolfile muss in einem
     * Verzeichnis "src/resources" liegen.Bitte den Namen des Files ohne
     * Verzeichnisnamen angeben, z.B."Symbol.png". Diese Methode muss aufgerufen
     * werden, bevor das Fenster sichtbar gemacht wird.
     *
     * @param windowIcon Das Symbol.
     */
    public void setWindowIcon(String windowIcon) {
        checkConfigurationMethods("setWindowIcon()");
        Image fensterSymbol = null;
        try {
            fensterSymbol = new ImageIcon(GameView.class.getResource("/resources/" + windowIcon)).getImage();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Symbolfile \"" + windowIcon + "\" konnte nicht gefunden werden!");
            fensterSymbol = null;
        }
        frame.setIconImage(fensterSymbol);
    }

    /**
     * Text, der in der Statuszeile angezeigt wird. Diese Methode muss aufgerufen
     * werden, bevor das Fenster sichtbar gemacht wird.
     *
     * @param status Text der Statuszeile.
     */
    public void setStatus(String status) {
        checkConfigurationMethods("setStatus()");
        this.statusTextLinks = status;
        statusLabelLinks.setText(gameView.statusTextLinks);
    }

    /**
     * Die Gr��e des Fensters kann festgelegt werden. Es gibt WINDOWSIZE_SMALL, WINDOWSIZE_NORMAL, WINDOWSIZE_LARGE und
     * WINDOWSIZE_MAXIMIZED. Diese Methode muss aufgerufen werden, bevor das Fenster
     * sichtbar gemacht wird.
     *
     * @param windowSize Gr��e des Fensters.
     */
    public void setWindowsSize(int windowSize) {
        checkConfigurationMethods("setWindowsSize()");
        this.windowSize = windowSize;
    }

    /**
     * Das GameView-Fenster wird angezeigt.
     */
    public void show() {
        if (windowSize == WINDOWSIZE_MAXIMIZED) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else if (windowSize == WINDOWSIZE_NORMAL) {
            frame.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 3d * 2d), (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 3d * 2d));
        } else if (windowSize == WINDOWSIZE_LARGE) {
            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width - 100, Toolkit.getDefaultToolkit().getScreenSize().height - 100);
        } else if (windowSize == WINDOWSIZE_SMALL) {
            frame.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 3.5d * 2d), (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 3.5 * 2d));
        }
        frame.revalidate();
        area.setFontsizeThatFitsWindow();
        frame.pack();
        frame.setLocationRelativeTo(null);
        new Thread(() -> frame.setVisible(true)).run();
        splashScreen();
        while (!isSplashFinished) {
            Thread.onSpinWait();
        }
    }

    /**
     * �ndert die Aufl�sung im Fenster. Die Fenstergr��e wird dabei beibehalten.
     *
     * @param lines Die gew�hlte Aufl�sung (Zeilen).
     * @param rows  Die gew�hlte Aufl�sung (Spalten).
     */
    public void changeResolution(int lines, int rows) {
        setResolution(lines, rows);
        this.area.setText();
        area.setFontsizeThatFitsWindow();
    }

    /**
     * Gibt die aktuelle Zeilenanzahl zur�ck;
     *
     * @return Aktuelle Zeilenanzahl
     */
    public int getRows() {
        return zeilen;
    }

    /**
     * Gibt die aktuelle Spaltenanzahl zur�ck;
     *
     * @return Aktuelle Spaltenanzahl
     */
    public int getColumns() {
        return spalten;
    }

    /**
     * Gibt den aktuellen char an der Stelle (line, row) vom Canvas zur�ck.
     *
     * @return Aktueller Canvas.
     */
    public char getCharacter(int line, int row) {
        return canvas.getCharacter(line, row);
    }

    /**
     * Legt fest, ob die Maus im Fenster benutzt werden soll. Falls sie nicht
     * benutzt wird, wird der Cursor der Maus auf den Default-Ansicht zur�ckgesetzt
     * und die Maus wird ausgeblendet. Falls sie benutzt wird, werden Maus-Events
     * erzeugt, die verwendet werden k�nnen. Die Standardeinstellung ist
     * <code>false</code>.
     *
     * @param useMouse Legt fest, ob die Maus im Fenster benutzt werden soll.
     */
    public void useMouse(boolean useMouse) {
        this.useMouse = useMouse;
        if (useMouse) {
            setStandardMouseCursor();
            if (mouseTimer != null) {
                mouseTimer.cancel();
                mouseTimer = null;
            }
        } else {
            if (mouseTimer == null) {
                setNullCursor();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        if (!moved) {
                            if (!nullCursor) {
                                setNullCursor();
                            }
                        } else {
                            nullCursor = false;
                        }
                        moved = false;

                    }
                };
                mouseTimer = new Timer(true);
                mouseTimer.schedule(task, 500, 500);
            }
        }
    }

    /**
     * Legt ein neues Symbol f�r den Maus-Cursor fest. Das Bildfile muss in einem
     * Verzeichnis "src/resources" liegen. Bitte den Namen des Files ohne
     * Verzeichnisnamen angeben, z.B. "Cursor.png".
     *
     * @param cursor   Name des Bildfiles. Das Bildfile muss in einem Verzeichnis
     *                 "src/resources" liegen. Bitte den Namen des Files ohne
     *                 Verzeichnisnamen angeben, z.B. "Cursor.png".
     * @param centered Gibt an, ob der Hotspot des Cursors in der Mitte des Symbols
     *                 oder oben links liegen soll. Ansonsten ist der Hotspot oben
     *                 links.
     */
    public void setMouseCursor(String cursor, boolean centered) {
        this.nullCursor = false;
        Image im = null;
        try {
            im = new ImageIcon(GameView.class.getResource("/resources/" + cursor)).getImage();
        } catch (Exception e) {
            System.out.println("Cursorfile konnte nicht gefunden werden!");
            System.exit(1);
        }
        frame.setCursor(createCursor(im));
    }

    private void setNullCursor() {
        this.nullCursor = true;
        Image im = new ImageIcon("").getImage();
        frame.setCursor(createCursor(im));
    }

    private Cursor createCursor(Image im) {
        Toolkit toolkit = frame.getToolkit();
        Point cursorHotSpot = new Point(15, 15);
        Cursor imageCursor = toolkit.createCustomCursor(im, cursorHotSpot, "Cross");
        return imageCursor;
    }

    /**
     * Der Maus-Cursor wird auf das Standard-Icon zur�ckgesetzt.
     */
    public void setStandardMouseCursor() {
        frame.setCursor(Cursor.getDefaultCursor());
    }


    /**
     * Gibt den �bergebenen <code>String</code> zentriert im Fenster aus. Diese Methode sollte
     * h�chstens alle 16 ms aufgerufen werden (60 Frames pro Sekunde).
     *
     * @param string Der anzuzeigende String.
     */
    public void printCentred(String string) {
        char[][] chars = convertStringToCharArray(string);
        printCentred(chars);
    }

    /**
     * Das �bergebene <code>char[][]</code> (mit Zeilen, Spalten) wird zentriert im Fenster
     * ausgegeben. Diese Methode sollte h�chstens alle 16 ms aufgerufen werden (60
     * Frames pro Sekunde).
     *
     * @param chars Das anzuzeigende <code>char[][]</code>.
     */
    public void printCentred(char[][] chars) {
        print(chars, true);
    }


    /**
     * Gibt den �bergebenen <code>String</code> im Fenster aus. Diese Methode sollte
     * h�chstens alle 16 ms aufgerufen werden (60 Frames pro Sekunde).
     *
     * @param string Der anzuzeigende String.
     */
    public void print(String string) {
        char[][] chars = convertStringToCharArray(string);
        print(chars);
    }

    /**
     * Das �bergebene <code>char[][]</code> (mit Zeilen, Spalten) wird im Fenster
     * ausgegeben. Diese Methode sollte h�chstens alle 16 ms aufgerufen werden (60
     * Frames pro Sekunde).
     *
     * @param chars Das anzuzeigende <code>char[][]</code>.
     */
    public void print(char[][] chars) {
        print(chars, false);
    }

    private void print(char[][] chars, boolean centred) {
        clearCanvas();
        char temp = getNonTransparentSpace();
        setNonTransparentSpace('\u1000');
        if (centred) {
            addToCanvasCentered(chars);
        } else {
            addToCanvas(chars, 0, 0);
        }
        printCanvas();
        setNonTransparentSpace(temp);
    }

    /**
     * L�scht alle Inhalte auf dem Canvas.
     */
    public void clearCanvas() {
        canvas.clearCanvas();
    }

    /**
     * Schreibt den �bergebenen <code>String</code> zentriert auf das Canvas, ohne
     * die bisherigen Inhalte zu l�schen.
     * Achtung: In dieser Methode ist das
     * Leerzeichen durchsichtig (Objekte im Hintergrund sind zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird (die Hintergrundfarbe ist zu sehen), kann statt dessen das Semikolon
     * ";" verwendet werden.
     * Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param string Der zu schreibende <code>String</code>.
     * @param color  Farbe, die verwendet werden soll.
     */
    public void addToCanvasCentered(String string, Color color) {
        char[][] chars = convertStringToCharArray(string);
        addToCanvasCentered(chars, color);
    }

    /**
     * Schreibt den �bergebenen <code>String</code> zentriert auf das Canvas, ohne
     * die bisherigen Inhalte zu l�schen.
     * Achtung: In dieser Methode ist das
     * Leerzeichen durchsichtig (Objekte im Hintergrund sind zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird (die Hintergrundfarbe ist zu sehen), kann statt dessen das Semikolon
     * ";" verwendet werden.
     * Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param string Der zu schreibende <code>String</code>.
     */
    public void addToCanvasCentered(String string) {
        addToCanvasCentered(string, foregroundColor);
    }

    /**
     * Schreibt das �bergebene <code>char[][]</code> (mit Zeilen, Spalten) zentriert
     * auf das Canvas, ohne die bisherigen Inhalte zu l�schen.
     * Achtung: In dieser Methode ist das
     * Leerzeichen durchsichtig (Objekte im Hintergrund sind zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird (die Hintergrundfarbe ist zu sehen), kann statt dessen das Semikolon
     * ";" verwendet werden.
     * Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param chars Das anzuzeigende <code>char[][]</code>.
     * @param color Farbe, die verwendet werden soll.
     */
    public void addToCanvasCentered(char[][] chars, Color color) {
        addToCanvas(chars, zeilen / 2 - chars.length / 2, spalten / 2 - chars[0].length / 2, color);
    }

    /**
     * Schreibt das �bergebene <code>char[][]</code> (mit Zeilen, Spalten) zentriert
     * auf das Canvas, ohne die bisherigen Inhalte zu l�schen.
     * Achtung: In dieser Methode ist das
     * Leerzeichen durchsichtig (Objekte im Hintergrund sind zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird (die Hintergrundfarbe ist zu sehen), kann statt dessen das Semikolon
     * ";" verwendet werden.
     * Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param chars Das anzuzeigende <code>char[][]</code>.
     */
    public void addToCanvasCentered(char[][] chars) {
        addToCanvasCentered(chars, foregroundColor);
    }

    /**
     * Schreibt den �bergebenen <code>String</code> auf das Canvas, ohne die
     * bisherigen Inhalte zu l�schen. Zus�tzlich werden Koordinaten ausgewertet: (0,
     * 0) ist oben links. Negative Koordinaten k�nnen verwendet werden um Objekte
     * teilweise anzuzeigen.
     * Achtung: In dieser Methode ist das
     * Leerzeichen durchsichtig (Objekte im Hintergrund sind zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird (die Hintergrundfarbe ist zu sehen), kann statt dessen das Semikolon
     * ";" verwendet werden.
     * Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param string Der zu schreibende <code>String</code>.
     * @param row    Zeile in der der <code>String</code> angezeigt werden soll.
     *               Zeile. ist oben.
     * @param column Spalte, in der der <code>String</code> angezeigt werden soll.
     *               Spalte 0 ist links.
     * @param color  Farbe, die verwendet werden soll.
     */
    public void addToCanvas(String string, int row, int column, Color color) {
        char[][] chars = convertStringToCharArray(string);
        addToCanvas(chars, row, column, color);
    }

    /**
     * Schreibt den �bergebenen <code>String</code> auf das Canvas, ohne die
     * bisherigen Inhalte zu l�schen. Zus�tzlich werden Koordinaten ausgewertet: (0,
     * 0) ist oben links. Negative Koordinaten k�nnen verwendet werden um Objekte
     * teilweise anzuzeigen.
     * Achtung: In dieser Methode ist das
     * Leerzeichen durchsichtig (Objekte im Hintergrund sind zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird (die Hintergrundfarbe ist zu sehen), kann statt dessen das Semikolon
     * ";" verwendet werden.
     * Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param string Der zu schreibende <code>String</code>.
     * @param row    Zeile in der der <code>String</code> angezeigt werden soll.
     *               Zeile. ist oben.
     * @param column Spalte, in der der <code>String</code> angezeigt werden soll.
     *               Spalte 0 ist links.
     */
    public void addToCanvas(String string, int row, int column) {
        addToCanvas(string, row, column, foregroundColor);
    }

    /**
     * Schreibt das �bergebene <code>char[][]</code> (mit Zeilen, Spalten) auf das
     * Canvas, ohne die bisherigen Inhalte zu l�schen. Zus�tzlich werden Koordinaten
     * ausgewertet: (0, 0) ist oben links. Negative Koordinaten k�nnen verwendet
     * werden um Objekte teilweise anzuzeigen.
     * Achtung: In dieser Methode ist das
     * Leerzeichen durchsichtig (Objekte im Hintergrund sind zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird (die Hintergrundfarbe ist zu sehen), kann statt dessen das Semikolon
     * ";" verwendet werden.
     * Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param chars  Das anzuzeigende <code>char[][]</code>.
     * @param row    Zeile in der das Array angezeigt werden soll. Zeile 0 ist oben.
     * @param column Spalte, in der das Array angezeigt werden soll. Spalte 0 ist
     *               links.
     * @param color  Farbe, die verwendet werden soll.
     */
    public void addToCanvas(char[][] chars, int row, int column, Color color) {
        addToCanvas(chars, row, column, color, false);
    }

    private void addToCanvas(char[][] chars, int row, int column, Color color, boolean colorString) {
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                if (i >= row && j >= column // nur schreiben, wenn Koorinaten erf�llt sind.
                        && i - row < chars.length && j - column < chars[i - row].length // nur wenn char vorhanden
                        && chars[i - row][j - column] != ' ') { // transparent
                    if (chars[i - row][j - column] == nonTransparentSpace) {
                        if (colorString) {
                            canvas.add(' ', foregroundColor, backgroundColor, i, j);
                        } else {
                            canvas.add(' ', color, backgroundColor, i, j);
                        }
                    } else {
                        if (colorString) {
                            canvas.add(' ', foregroundColor, colormap.get(chars[i - row][j - column]), i, j);
                        } else {
                            canvas.add(chars[i - row][j - column], color, i, j);
                        }
                    }
                }
            }
        }
    }

    /**
     * Schreibt das �bergebene <code>char[][]</code> (mit Zeilen, Spalten) auf das
     * Canvas, ohne die bisherigen Inhalte zu l�schen. Zus�tzlich werden Koordinaten
     * ausgewertet: (0, 0) ist oben links. Negative Koordinaten k�nnen verwendet
     * werden um Objekte teilweise anzuzeigen.
     * Achtung: In dieser Methode ist das
     * Leerzeichen durchsichtig (Objekte im Hintergrund sind zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird (die Hintergrundfarbe ist zu sehen), kann statt dessen das Semikolon
     * ";" verwendet werden.
     * Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param chars  Das anzuzeigende <code>char[][]</code>.
     * @param row    Zeile in der das Array angezeigt werden soll. Zeile 0 ist oben.
     * @param column Spalte, in der das Array angezeigt werden soll. Spalte 0 ist
     *               links.
     */
    public void addToCanvas(char[][] chars, int row, int column) {
        addToCanvas(chars, row, column, foregroundColor);
    }

    /**
     * Der �bergebene String aus Farbcodes wird ohne Zeichen auf das Canvas �bertragen, ohne die bisherigen Inhalte zu l�schen.
     * Die darin enthaltenen Buchstaben werden als Farben interpretiert.
     * Dazu wird eine Colormap ausgewertet, die durch eine Methode <code>setColormap()</code> ge�ndert
     * werden kann.
     * <p>
     * HashMap<Character, Color> colormap = new HashMap<>();
     * colormap.put('R', Color.RED);
     * colormap.put('r', Color.RED.brighter());
     * colormap.put('G', Color.GREEN);
     * colormap.put('g', Color.GREEN.brighter());
     * colormap.put('B', Color.BLUE);
     * colormap.put('b', Color.BLUE.brighter());
     * colormap.put('Y', Color.YELLOW);
     * colormap.put('P', Color.PINK);
     * colormap.put('C', Color.CYAN);
     * colormap.put('M', Color.MAGENTA);
     * colormap.put('O', Color.ORANGE);
     * colormap.put('W', Color.WHITE);
     * <p>
     * Zus�tzlich werden Koordinaten
     * ausgewertet: (0, 0) ist oben links. Negative Koordinaten k�nnen verwendet
     * werden um Objekte teilweise anzuzeigen.
     * Achtung: In dieser Methode ist das
     * Leerzeichen durchsichtig (Objekte im Hintergrund sind zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird (die Hintergrundfarbe ist zu sehen), kann statt dessen das Semikolon
     * ";" verwendet werden.
     * Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param colorString Der Farb-Codierte String.
     * @param row         Zeile in der der String angezeigt werden soll. Zeile 0 ist oben.
     * @param column      Spalte, in der der String angezeigt werden soll. Spalte 0 ist
     *                    links.
     */
    public void addColorStringToCanvas(String colorString, int row, int column) {
        addColorStringToCanvas(convertStringToCharArray(colorString), row, column);
    }

    /**
     * Der �bergebene String aus Farbcodes wird ohne Zeichen auf das Canvas �bertragen, ohne die bisherigen Inhalte zu l�schen.
     * Die darin enthaltenen Buchstaben werden als Farben interpretiert.
     * Dazu wird eine Colormap ausgewertet, die durch eine Methode <code>setColormap()</code> ge�ndert
     * werden kann.
     * <p>
     * HashMap<Character, Color> colormap = new HashMap<>();
     * colormap.put('R', Color.RED);
     * colormap.put('r', Color.RED.brighter());
     * colormap.put('G', Color.GREEN);
     * colormap.put('g', Color.GREEN.brighter());
     * colormap.put('B', Color.BLUE);
     * colormap.put('b', Color.BLUE.brighter());
     * colormap.put('Y', Color.YELLOW);
     * colormap.put('P', Color.PINK);
     * colormap.put('C', Color.CYAN);
     * colormap.put('M', Color.MAGENTA);
     * colormap.put('O', Color.ORANGE);
     * colormap.put('W', Color.WHITE);
     * <p>
     * Zus�tzlich werden Koordinaten
     * ausgewertet: (0, 0) ist oben links. Negative Koordinaten k�nnen verwendet
     * werden um Objekte teilweise anzuzeigen.
     * Achtung: In dieser Methode ist das
     * Leerzeichen durchsichtig (Objekte im Hintergrund sind zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird (die Hintergrundfarbe ist zu sehen), kann statt dessen das Semikolon
     * ";" verwendet werden.
     * Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param colorString Das Farb-Codierte Array.
     * @param row         Zeile in der das Array angezeigt werden soll. Zeile 0 ist oben.
     * @param column      Spalte, in der das Array angezeigt werden soll. Spalte 0 ist
     *                    links.
     */
    public void addColorStringToCanvas(char[][] colorString, int row, int column) {
        addToCanvas(colorString, row, column, foregroundColor, true);
    }

    /**
     * F�llt alle Stellen des Canvas mit dem gegebenen Zeichen. Vorhandene Inhalte werden �berschrieben.
     *
     * @param c     Zeichen, das auf den Canvas geschrieben werden soll.
     * @param color Farbe, die verwendet werden soll.
     */
    public void fillCanvas(char c, Color color) {
        canvas.fillCanvas(c, color);
    }

    /**
     * F�llt alle Stellen des Canvas mit gleichfarbigen Pixeln. Vorhandene Inhalte werden �berschrieben.
     *
     * @param color Farbe, die verwendet werden soll.
     */
    public void fillCanvas(Color color) {
        canvas.fillCanvas(color);
    }

    /**
     * F�llt alle Stellen des Canvas mit dem gegebenen Zeichen. Vorhandene Inhalte werden �berschrieben.
     *
     * @param c Zeichen, das auf den Canvas geschrieben werden soll.
     */
    public void fillCanvas(char c) {
        fillCanvas(c, foregroundColor);
    }

    /**
     * Zeigt den aktuellen Inhalt des Canvas im Fenster an. Diese Methode sollte
     * h�chstens alle 16 ms aufgerufen werden (60 Frames pro Sekunde).
     */
    public void printCanvas() {
        countPrints++;
        area.setText();
        // Check FPS
        long now = System.currentTimeMillis();
        if (now - lastTime > 1000) {
            if (countPrints >= 70) {
                setStatusLabel(Color.RED, "Prints pro Sekunde: " + countPrints + ((area.canvasList.size() > 4) ? framesLost : ""));
            } else {
                setStatusLabel(Color.BLACK, statusTextLinks);
            }
            countPrints = 0;
            lastTime = now;
        }
    }

    /**
     * In den Methoden <code>addToCanvas(...)</code> ist das
     * normale Leerzeichen durchsichtig (Objekte im Hintergrund sind zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird (die Hintergrundfarbe ist zu sehen), kann statt dessen das Semikolon
     * ";" verwendet werden.
     * Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden. Diese Methode gibt das aktell gesetzte Zeichen zur�ck
     *
     * @return Das undurchsichtige Leerzeichen.
     */
    public char getNonTransparentSpace() {
        return nonTransparentSpace;
    }

    /**
     * Das undurchsichtige Leerzeichen kann mit dieser Methode auf ein anderes
     * Zeichen gesetzt werden. In den Methoden <code>addToCanvas(...)</code> ist das
     * normale Leerzeichen durchsichtig (Objekte im Hintergrund sind zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird (die Hintergrundfarbe ist zu sehen), kann statt dessen das Semikolon
     * ";" verwendet werden. Diese Methode erlaubt es, die Voreinstellung zu �ndern
     * und ein anderes Zeichen zu w�hlen.
     *
     * @param c Das neue undurchsichtige Leerzeichen.
     */
    public void setNonTransparentSpace(char c) {
        nonTransparentSpace = c;
    }

    /**
     * Liefert alle Tastendr�cke (java.awt.event.Keyevent) die seit dem letzten Aufruf dieser Methode
     * aufgelaufen sind als Array zur�ck. Es werden maximal die neuesten 25
     * Ereignisse zur�ckgegeben, alte Ereignisse werden gel�scht.
     * <p>
     * Das Array enth�lt Ereignisse vom Typ <code>java.awt.event.KeyEvent;</code>.
     * Der Typ des Events ist entweder <code>KeyEvent.KEY_PRESSED</code> (f�r nicht
     * sichtbare Zeichen), <code>KeyEvent.KEY_RELEASED</code> (f�r nicht sichtbare
     * Zeichen) oder <code>KeyEvent.KEY_TYPED</code>(f�r sichtbare Zeichen).
     * Sichtbare Zeichen lassen sich mit der Methode <code>getKeyChar()</code>
     * auswerten.
     *
     * <pre>
     * <code>
     * package keyevents;
     * import java.awt.event.KeyEvent;
     *
     * public class Test {
     *   GameView gameView;
     *
     *   public Test(){
     *     gameView = new GameView(20, 40,"Test");
     *     gameView.show();
     *   }
     *
     *   public void loop() {
     *     while(true) {
     *       KeyEvent[] keyEvents = gameView.getKeyEvents();
     *       for (int i = 0; i < keyEvents.length; i++) {
     *         KeyEvent keyEvent = keyEvents[i];
     *         if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
     *           if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
     *             System.out.println("Eingabetaste");
     *           }
     *         } else if (keyEvent.getID() == KeyEvent.KEY_TYPED) {
     *           System.out.println(keyEvent.getKeyChar());
     *         }
     *       }
     *     }
     *   }
     * }
     * </code>
     * </pre>
     *
     * <br>
     *
     * @return Alle <code>KeyEvent</code> Ereignisse seit dem letzten Aufruf dieser
     * Methode.
     */
    public KeyEvent[] getKeyEvents() {
        KeyEvent[] keyEvents = new KeyEvent[keyboardEvents.size()];
        for (int i = 0; i < keyEvents.length; i++) {
            keyEvents[i] = keyboardEvents.remove();
        }
        return keyEvents;

    }

    /**
     * Setzt die Default-Farbe der Schrift.
     *
     * @param foregroundColor Schriftfarbe
     */
    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    /**
     * Setzt die Default-Farbe des Hintergrunds.
     *
     * @param foregroundColor Hintergrundfarbe
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Liefert alle Mausereignisse, seit dem letzten Aufruf dieser Methode
     * aufgelaufen sind als Array zur�ck. Es werden maximal die neuesten 25
     * Ereignisse zur�ckgegeben, alte Ereignisse werden gel�scht.
     * <p>
     * Das Array enth�lt Ereignisse vom Typ <code>java.awt.event.MouseEvent;</code>
     * Das <code>MouseEvent</code> enth�lt Koordinaten(des Fensters) und die
     * Information ob die Maus gedr�ckt, losgelassen, gecklickt oder nur bewegt
     * wurde. Um festzustellen, wie die Maus bet�tigt wurde, kann der Typ des
     * <code>MouseEvent</code> abgefragt werden. Folgende <code>MouseEvent</code>
     * werden weitergeleitet: <br>
     * <code>MouseEvent.MOUSE_PRESSED</code> <br>
     * <code>MouseEvent.MOUSE_RELEASED</code> <br>
     * <code>MouseEvent.MOUSE_CLICKED</code> <br>
     * <code>MouseEvent.MOUSE_MOVED</code> <br>
     * <br>
     * Die Fensterkoordinaten k�nnen mit den Methoden<br>
     * <code>getX()</code> = Spalten<br>
     * <code>getY()</code> = Zeilen<br>
     * abgerufen werden, um Zeile und Spalte des Events zu bestimmen.<br>
     * <br>
     * Beispiel zur Erkennung einer geklickten Maustaste:<br>
     *
     * <pre>
     * <code>
     * package mouseevents;
     *
     * import java.awt.event.MouseEvent;
     *
     * public class Test {
     *   GameView gameView;
     *
     *   public Test() {
     *     gameView = new GameView(20, 40, "Test");
     *     gameView.useMouse(true);
     *     gameView.show();
     *   }
     *
     *   public void loop() {
     *     while (true) {
     *       MouseEvent[] mouseEvents = gameView.getMouseEvents();
     *       for (int i = 0; i < mouseEvents.length; i++) {
     *         MouseEvent mouseEvent = mouseEvents[i];
     *         if (mouseEvent.getID() == MouseEvent.MOUSE_CLICKED) {
     *         System.out.println("Geklickt in Spalte: " + mouseEvent.getX());
     *         }
     *       }
     *     }
     *   }
     * }
     *
     * </code>
     * </pre>
     *
     * @return Alle <code>MouseEvent</code> Ereignisse seit dem letzten Aufruf
     * dieser Methode.
     */
    public MouseEvent[] getMouseEvents() {
        MouseEvent[] mouseEvents = new MouseEvent[mousePointerEvents.size()];
        for (int i = 0; i < mouseEvents.length; i++) {
            mouseEvents[i] = mousePointerEvents.remove();
        }
        return mouseEvents;
    }

    /**
     * Spielt einen Sound ab (wav.-Datai). Das Soundfile muss in einem Verzeichnis
     * "src/resources" liegen. Bitte den Namen des Files ohne Verzeichnisnamen
     * angeben, z.B. "Sound.wav". Um den Sound sofort abzuspielen, bitte als
     * Verz�gerung 0 angeben.
     *
     * @param sound Name des Soundfiles. Das Soundfile muss in einem Verzeichnis
     *              "src/resources" liegen. Bitte den Namen des Files ohne
     *              Verzeichnisnamen angeben, z.B. "Sound.wav".
     * @param delay Verz�gerung bis zum Zeitpunkt des Abspielens in Millisekunden.
     *              Bei 0 wird der Sound sofort abgespielt.
     */
    public void playSound(String sound, int delay) {
        Runnable run = () -> {
            try {
                File file = new File(GameView.class.getClassLoader().getResource("resources/" + sound).toURI().getPath());
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                System.err.println("Soundfile \"" + sound + "\" konnte nicht abgespielt werden!");
                e.printStackTrace();
                System.exit(1);
            }
        };
        new Thread(run).start();
    }

    /**
     * Hier kann die Farb-Assoziation f�r die Methode <code> addColorStringToCanvas() </code> festgelegt werden.
     * Als Standard sind folgede Farben definiert:
     * <p>
     * colormap.put('R', Color.RED);
     * colormap.put('r', Color.RED.brighter());
     * colormap.put('G', Color.GREEN);
     * colormap.put('g', Color.GREEN.brighter());
     * colormap.put('B', Color.BLUE);
     * colormap.put('b', Color.BLUE.brighter());
     * colormap.put('Y', Color.YELLOW);
     * colormap.put('P', Color.PINK);
     * colormap.put('C', Color.CYAN);
     * colormap.put('M', Color.MAGENTA);
     * colormap.put('O', Color.ORANGE);
     * <p>
     *
     * @param colormap Die Farbzuordnungen.
     */
    public void setColormap(HashMap<Character, Color> colormap) {
        this.colormap = colormap;
    }

    private void fillColormap() {
        this.colormap = new HashMap<>();
        colormap.put('R', Color.RED);
        colormap.put('r', Color.RED.brighter());
        colormap.put('G', Color.GREEN);
        colormap.put('g', Color.GREEN.brighter());
        colormap.put('B', Color.BLUE);
        colormap.put('b', Color.BLUE.brighter());
        colormap.put('Y', Color.YELLOW);
        colormap.put('P', Color.PINK);
        colormap.put('C', Color.CYAN);
        colormap.put('M', Color.MAGENTA);
        colormap.put('O', Color.ORANGE);
        colormap.put('W', Color.WHITE);
    }

    private void intitFrame(String fensterName) {

        Box box = new Box(BoxLayout.Y_AXIS);
        area = new TextPanel(box);
        area.requestFocus();

        box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box.add(Box.createVerticalGlue());
        box.add(area);
        box.add(Box.createVerticalGlue());
        box.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        statusLabelLinks = new JLabel();
        statusLabelLinks.setBackground(Color.WHITE);
        statusLabelLinks.setForeground(Color.BLACK);
        statusLabelLinks.setText(gameView.statusTextLinks);
        statusLabelLinks.setHorizontalAlignment(JLabel.LEFT);

        JLabel statusr = new JLabel(STATUSRECHTS);
        statusr.setBackground(Color.WHITE);
        statusr.setForeground(Color.BLACK);
        statusr.setHorizontalAlignment(JLabel.RIGHT);

        JPanel statuszeile = new JPanel(new BorderLayout());
        statuszeile.add(statusLabelLinks, BorderLayout.WEST);
        statuszeile.add(statusr, BorderLayout.EAST);
        statuszeile.setBorder(BorderFactory.createRaisedBevelBorder());
        statuszeile.setBackground(Color.WHITE);
        statuszeile.setForeground(Color.BLACK);

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(backgroundColor);
        outer.add(box, BorderLayout.CENTER);
        outer.add(statuszeile, BorderLayout.SOUTH);

        frame = new JFrame(fensterName);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(outer);

        frame.pack();
        frame.setResizable(true);

        // Small size
        frame.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 3d * 2d), (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 3d * 2d));
        area.setFontsizeThatFitsWindow();
        frame.pack();

        // Listener
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if (isSplashFinished) {
                    area.setFontsizeThatFitsWindow();
                    if (resizeTimer != null) {
                        resizeTimer.cancel();
                    }
                    resizeTimer = new Timer();
                    TimerTask resizeTask = new TimerTask() {

                        @Override
                        public void run() {
                            if (!((frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH)) {
                                SwingUtilities.invokeLater(() -> {
                                    try {
                                        Robot robot = new Robot();
                                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                    } catch (AWTException e) {
                                    }
                                    frame.pack();
                                });
                            }
                        }
                    };
                    resizeTimer.schedule(resizeTask, 100);
                }
            }
        });

        addKeyEventListener();
        addMouseEventListener();
        useMouse(false); // init Mouse-Timer
    }

    private void splashScreen() {
        int lines = this.zeilen;
        int rows = this.spalten;
        changeResolution(9, 22);
        area.setFont(area.getFont().deriveFont(Font.BOLD));
        fillCanvas(Color.BLACK);
        addToCanvasCentered(SPLASH, Color.cyan.darker());
        printCanvas();
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
        }
        changeResolution(lines, rows);
        isSplashFinished = true;
    }

    private void checkConfigurationMethods(String method) {
        if (frame.isVisible()) {
            System.err.println("Die Methode \"" + method + "\" darf nur aufgerufen werden, bevor das Fenster angezeigt wird.");
            System.exit(1);
        }
    }

    private void setResolution(int lines, int rows) {
        this.zeilen = lines;
        this.spalten = rows;
        canvas = new Canvas(zeilen, spalten);
    }

    private void addMouseEventListener() {
        area.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                processMouseEvent(e);
            }
        });
        area.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                processMouseEvent(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                processMouseEvent(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                processMouseEvent(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                processMouseEvent(e);
            }
        });
    }

    private void processMouseEvent(MouseEvent e) {
        if (gameView.useMouse) {
            if (e.getID() == MouseEvent.MOUSE_PRESSED || e.getID() == MouseEvent.MOUSE_RELEASED || e.getID() == MouseEvent.MOUSE_MOVED || e.getID() == MouseEvent.MOUSE_CLICKED) {
                int z = (int) Math.floor(gameView.zeilen * e.getY() / area.getHeight());
                int s = (int) Math.floor(gameView.spalten * e.getX() / area.getWidth());
                if (s >= 0 && s < gameView.spalten && z >= 0 && z < gameView.zeilen) {

                    MouseEvent mouseEvent = new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiersEx(), s, z, e.getClickCount(), e.isPopupTrigger());

                    if (mouseEvent.getID() == MouseEvent.MOUSE_MOVED) {
                        long millies = System.currentTimeMillis();
                        if (millies - mouseMovementTimer >= 1000d / 30) { // Jedes zweite Frame ein Event
                            mousePointerEvents.add(mouseEvent);
                            mouseMovementTimer = millies;
                        }
                    } else {
                        mousePointerEvents.add(mouseEvent);
                    }
                    while (mousePointerEvents.size() > 500) {
                        mousePointerEvents.remove();
                    }
                }
            }
        } else {
            if (e.getID() == MouseEvent.MOUSE_MOVED && !gameView.moved) {
                frame.setCursor(Cursor.getDefaultCursor());
                gameView.moved = true;
            }
        }
    }

    private void addKeyEventListener() {
        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                any(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                any(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                any(e);
            }

            private void any(KeyEvent e) {
                int code = e.getKeyCode();
                if (KeyEvent.VK_ESCAPE == code) {
                    System.exit(0);
                }
                keyboardEvents.add(e);
                while (keyboardEvents.size() > 500) {
                    keyboardEvents.remove();
                }
            }
        });
    }

    private void setStatusLabel(Color color, String fpsMessage) {
        SwingUtilities.invokeLater(() -> {
            statusLabelLinks.setText(fpsMessage);
            statusLabelLinks.setForeground(color);
        });
    }

    private class Canvas implements Cloneable {
        private int zeilen;
        private int spalten;
        private boolean resolutionChanged;
        private char[][] chars;
        private Color[][] foreground;
        private Color[][] background;
        private String[][] difference;
        private String[][] clear;
        private boolean allNull = true;

        public Canvas(int zeilen, int spalten) {
            this.zeilen = zeilen;
            this.spalten = spalten;
            this.resolutionChanged = false;
            chars = new char[zeilen][spalten];
            foreground = new Color[zeilen][spalten];
            background = new Color[zeilen][spalten];
            difference = new String[zeilen][spalten];
            clear = new String[zeilen][spalten];
            clearCanvas();
        }

        public int getZeilen() {
            return zeilen;
        }

        public int getSpalten() {
            return spalten;
        }

        public void add(char character, Color foreground, Color background, int zeile, int spalte) {
            add(character, foreground, zeile, spalte);
            this.background[zeile][spalte] = background;
        }

        public void add(char character, Color foreground, int zeile, int spalte) {
            chars[zeile][spalte] = character;
            this.foreground[zeile][spalte] = foreground;
        }

        public void fillCanvas(char c, Color color) {
            for (int i = 0; i < zeilen; i++) {
                for (int j = 0; j < spalten; j++) {
                    add(c, color, i, j);
                }
            }
        }

        public void fillCanvas(Color color) {
            for (int i = 0; i < zeilen; i++) {
                for (int j = 0; j < spalten; j++) {
                    add(' ', foregroundColor, color, i, j);
                }
            }
        }

        public void clearCanvas() {
            for (int zeile = 0; zeile < zeilen; zeile++) {
                for (int spalte = 0; spalte < spalten; spalte++) {
                    chars[zeile][spalte] = ' ';
                    foreground[zeile][spalte] = foregroundColor;
                    background[zeile][spalte] = backgroundColor;
                    difference[zeile][spalte] = null;
                    clear[zeile][spalte] = null;
                }
            }
        }

        public char getCharacter(int zeile, int spalte) {
            return chars[zeile][spalte];
        }

        public Color getForeground(int zeile, int spalte) {
            return foreground[zeile][spalte];
        }

        public Color getBackground(int zeile, int spalte) {
            return background[zeile][spalte];
        }

        public String getDifference(int zeile, int spalte) {
            return difference[zeile][spalte];
        }

        public String getClear(int zeile, int spalte) {
            return clear[zeile][spalte];
        }

        @Override
        public Canvas clone() {
            Canvas copy = new Canvas(this.zeilen, this.spalten);
            for (int zeile = 0; zeile < zeilen; zeile++) {
                for (int spalte = 0; spalte < spalten; spalte++) {
                    copy.chars[zeile][spalte] = chars[zeile][spalte];
                    copy.foreground[zeile][spalte] = foreground[zeile][spalte];
                    copy.background[zeile][spalte] = background[zeile][spalte];
                    copy.difference[zeile][spalte] = difference[zeile][spalte];
                    copy.clear[zeile][spalte] = clear[zeile][spalte];
                }
            }
            return copy;
        }


        public void calculateDifferencesTo(Canvas canvas) {
            if (canvas == null || this.zeilen != canvas.zeilen || this.spalten != canvas.spalten) {
                resolutionChanged = true;
            }
            for (int zeile = 0; zeile < this.zeilen; zeile++) {
                for (int spalte = 0; spalte < this.spalten; spalte++) {
                    if (resolutionChanged || this.chars[zeile][spalte] != canvas.chars[zeile][spalte] || this.foreground[zeile][spalte] != canvas.foreground[zeile][spalte] || this.background[zeile][spalte] != canvas.background[zeile][spalte]) {
                        allNull = false;
                        this.difference[zeile][spalte] = String.valueOf(chars[zeile][spalte]);
                        if (!resolutionChanged) {
                            this.clear[zeile][spalte] = String.valueOf(canvas.getCharacter(zeile, spalte));
                        }
//                        if (zeile < zeilen - 1) {
//                            this.difference[zeile + 1][spalte] = String.valueOf(chars[zeile + 1][spalte]);
//                            if (!resolutionChanged) {
//                                this.clear[zeile + 1][spalte] = String.valueOf(canvas.getCharacter(zeile + 1, spalte));
//                            }
//                        }
//                        if (zeile > 0) {
//                            this.difference[zeile - 1][spalte] = String.valueOf(chars[zeile - 1][spalte]);
//                            if (!resolutionChanged) {
//                                this.clear[zeile - 1][spalte] = String.valueOf(canvas.getCharacter(zeile - 1, spalte));
//                            }
//                        }
//                        if (spalte < spalten - 1) {
//                            this.difference[zeile][spalte + 1] = String.valueOf(chars[zeile][spalte + 1]);
//                            if (!resolutionChanged) {
//                                this.clear[zeile][spalte + 1] = String.valueOf(canvas.getCharacter(zeile, spalte + 1));
//                            }
//                        }
//                        if (spalte > 0) {
//                            this.difference[zeile][spalte - 1] = String.valueOf(chars[zeile][spalte - 1]);
//                            if (!resolutionChanged) {
//                                this.clear[zeile][spalte - 1] = String.valueOf(canvas.getCharacter(zeile, spalte - 1));
//                            }
//                        }
                    }
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\"");
            for (int i = 0; i < zeilen; i++) {
                for (int j = 0; j < spalten; j++) {
                    sb.append(difference[i][j]);
                }
                sb.append("\n");
            }
            sb.append("\"");
            return sb.toString();
        }
    }

    private class TextPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        boolean resized;
        private BufferedImage image;
        private Graphics2D g2;
        private int lineHeight;
        private int charWidth;
        private int height;
        private int width;
        private Map<TextAttribute, Object> fontMap;
        private Box box;
        private Font font;
        private Rectangle2D bounds;
        private int x;
        private int y;
        private int yForString;
        private Canvas currentCanvas;
        private Canvas lastCanvas;
        private Canvas printCanvas;
        private LinkedBlockingQueue<Canvas> canvasList;


        public TextPanel(Box box) {
            this.box = box;
            setBackground(backgroundColor);
            setForeground(foregroundColor);
            fontMap = new HashMap<>();
            fontMap.put(TextAttribute.FAMILY, "Monospaced");
            fontMap.put(TextAttribute.WIDTH, 2.25);
            calculateDimensionsForFont(20);
            setFont(new Font(fontMap));
            canvasList = new LinkedBlockingQueue<>();
        }

        @Override
        public void setFont(Font font) {
            super.setFont(font);
            this.font = font;
            if (g2 != null) {
                g2.setFont(font);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            if (canvasList.isEmpty()) {
                if (printCanvas == null) {
                    return;
                }
            } else {
                printCanvas = canvasList.remove();
            }

            if (resized) {
                printCanvas.resolutionChanged = true;
                printCanvas.calculateDifferencesTo(null);
                resized = false;
            }

            if (printCanvas.resolutionChanged) {
                if (g2 != null) {
                    g2.dispose();
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                g2 = image.createGraphics();
                g2.setFont(font);
            }

            for (int i = 0; i < printCanvas.getZeilen(); i++) {
                for (int j = 0; j < printCanvas.getSpalten(); j++) {
                    if (printCanvas.getDifference(i, j) != null) {
                        x = j * charWidth;
                        y = (i + 1) * lineHeight;
                        yForString = y - (int) Math.rint(lineHeight * 0.25);
                        g2.setColor(printCanvas.getBackground(i, j));
                        g2.fillRect(x, y - lineHeight, charWidth, lineHeight);
                        if (printCanvas.getClear(i, j) != null && !printCanvas.getClear(i, j).isBlank()) {
                            g2.drawString(printCanvas.getClear(i, j), x, yForString);
                        }
                        if (!printCanvas.getDifference(i, j).isBlank()) {
                            g2.setColor(printCanvas.getForeground(i, j));
                            g2.drawString(printCanvas.getDifference(i, j), x, yForString);
                        }
                    }
                }
            }

            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
            if (!canvasList.isEmpty()) {
                repaint();
            }
            g.dispose();
        }

        public void setText() {
            if (canvasList.size() < 5) {
                lastCanvas = currentCanvas;
                currentCanvas = canvas.clone();
                currentCanvas.calculateDifferencesTo(lastCanvas);
                if (!currentCanvas.allNull) {
                    canvasList.add(currentCanvas);
                    SwingUtilities.invokeLater(() -> this.repaint());
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(width, height);
        }

        @Override
        public Dimension getMaximumSize() {
            return getPreferredSize();
        }

        private void calculateDimensionsForFont(int fontsize) {
            fontMap.put(TextAttribute.SIZE, fontsize);
            font = new Font(fontMap);
            bounds = getFontMetrics(font).getStringBounds("Q", 0, 1, getGraphics());
            this.lineHeight = (int) (bounds.getHeight());
            this.charWidth = (int) bounds.getWidth();
            this.height = (int) (canvas.getZeilen() * lineHeight);
            this.width = (int) (charWidth * canvas.getSpalten() + (charWidth * 0.02));
        }

        public void setFontsizeThatFitsWindow() {
            int fontsize = 1;
            int step = 256;
            while (true) {
                calculateDimensionsForFont(fontsize + step);
                if (width < box.getWidth() && height < box.getHeight()) {
                    fontsize = fontsize + step;
                } else {
                    if (step == 1) {
                        // fontsize passt jetzt
                        break;
                    }
                }
                step = Math.max(step / 2, 1);
            }
            calculateDimensionsForFont(fontsize);
            setFont(new Font(fontMap));
            if (isSplashFinished) {
                SwingUtilities.invokeLater(() -> {
                    resized = true;
                    repaint();
                });
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
