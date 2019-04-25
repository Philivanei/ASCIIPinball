package asciipinball;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Ein Fenster, dass die Anzeige von ASCI-Spielen erlaubt, Ton ausgibt und
 * Tastendr�cke und MausKlicks als Events zur�ckliefert.<br>
 * <br>
 *
 * @author Andreas Berl
 * @version 3.05 (10.04.2019) // Splashscreen fixed
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
    final static String STANDARDTITEL = "GameView III";
    private final static String STATUSRECHTS = "GameView III (2019) - Prof. Dr. Andreas Berl - TH Deggendorf ";
    private final static String SPLASH = "\n       ASCII-\n\n" + " GameView III (2019) \n";
    private final static char STANDARDNONTRANSPARENTSPACE = ';';
    private static GameView gameView;

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

    private char[][] canvas;
    private char nonTransparentSpace;
    private String printThis;

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
     * @param lines Die gew�hlte Aufl�sung (Zeilen von mindestens 9 bis h�chstens 135).
     * @param rows  Die gew�hlte Aufl�sung (Spalten von mindestens 16 bis h�chstens 240).
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
        title = (title == "") ? STANDARDTITEL : title;
        // Init JFrame
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
     * Legt ein Symbol f�r die Titelleiste fest. Das Symbolfile* muss in einem
     * Verzeichnis"src/resources" liegen.Bitte den* Namen des Files ohne
     * Verzeichnisnamen angeben, z.B.*"Symbol.png". Diese Methode muss aufgerufen
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
            frame.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width / 3d * 2d), (int) (Toolkit.getDefaultToolkit().getScreenSize().width / 3d * 2d));
        } else if (windowSize == WINDOWSIZE_LARGE) {
            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width - 100, Toolkit.getDefaultToolkit().getScreenSize().height - 100);
        }
        frame.revalidate();
        area.setFontsizeThatFitsWindow();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        splashScreen();
    }

    /**
     * �ndert die Aufl�sung im Fenster. Die Fenstergr��e wird dabei beibehalten. Es
     * k�nnen Aufl�sungen ausgew�hlt werden, z.B.:
     * <code>Resolution.R04_064_x_036</code> erzeugt ein Fenster mit 36 Zeilen und
     * 64 Spalten.
     *
     * @param lines Die gew�hlte Aufl�sung (Zeilen von mindestens 9 bis h�chstens 135).
     * @param rows  Die gew�hlte Aufl�sung (Spalten von mindestens 16 bis h�chstens 240).
     */
    public void changeResolution(int lines, int rows) {
        setResolution(lines, rows);
        this.area.setText(printThis);
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
        clearCanvas();
        addToCanvas(chars, 0, 0);
        printCanvas();
    }

    /**
     * L�scht alle Inhalte auf dem Canvas.
     */
    public void clearCanvas() {
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                canvas[i][j] = ' ';
            }
        }
    }

    /**
     * Schreibt den �bergebenen <code>String</code> zentriert auf das Canvas, ohne
     * die bisherigen Inhalte zu l�schen. Achtung: In dieser Methode ist das
     * Leerzeichen durchsichtig (der Hintergrund ist zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird, bitte statt dessen das Semikolon
     * ";" verwenden. Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param string Der zu schreibende <code>String</code>.
     */
    public void addToCanvasCentered(String string) {
        char[][] chars = convertStringToCharArray(string);
        addToCanvasCentered(chars);
    }

    /**
     * Schreibt das �bergebene <code>char[][]</code> (mit Zeilen, Spalten) zentriert
     * auf das Canvas, ohne die bisherigen Inhalte zu l�schen. Achtung: In dieser
     * Methode ist das Leerzeichen durchsichtig (der Hintergrund ist zu sehen).
     * Falls ein undurchsichtiges Leerzeichen ben�tigt wird, bitte statt dessen das
     * Semikolon ";" verwenden. Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param chars Das anzuzeigende <code>char[][]</code>.
     */
    public void addToCanvasCentered(char[][] chars) {
        addToCanvas(chars, zeilen / 2 - chars.length / 2, spalten / 2 - chars[0].length / 2);
    }

    /**
     * Schreibt den �bergebenen <code>String</code> auf das Canvas, ohne die
     * bisherigen Inhalte zu l�schen. Zus�tzlich werden Koordinaten ausgewertet: (0,
     * 0) ist oben links. Negative Koordinaten k�nnen verwendet werden um Objekte
     * teilweise anzuzeigen. Achtung: In dieser Methode ist das Leerzeichen
     * durchsichtig (der Hintergrund ist zu sehen). Falls ein undurchsichtiges
     * Leerzeichen ben�tigt wird, bitte statt dessen das Semikolon ";" verwenden.
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
        char[][] chars = convertStringToCharArray(string);
        addToCanvas(chars, row, column);
    }

    /**
     * Schreibt das �bergebene <code>char[][]</code> (mit Zeilen, Spalten) auf das
     * Canvas, ohne die bisherigen Inhalte zu l�schen. Zus�tzlich werden Koordinaten
     * ausgewertet: (0, 0) ist oben links. Negative Koordinaten k�nnen verwendet
     * werden um Objekte teilweise anzuzeigen. Achtung: In dieser Methode ist das
     * Leerzeichen durchsichtig (der Hintergrund ist zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird, bitte statt dessen das Semikolon
     * ";" verwenden. Das undurchsichtige Leerzeichen kann mit der Methode
     * <code>setNonTransparentSpace(char c)</code> auf ein anderes Zeichen gesetzt
     * werden.
     *
     * @param chars  Das anzuzeigende <code>char[][]</code>.
     * @param row    Zeile in der das Array angezeigt werden soll. Zeile 0 ist oben.
     * @param column Spalte, in der das Array angezeigt werden soll. Spalte 0 ist
     *               links.
     */
    public void addToCanvas(char[][] chars, int row, int column) {
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                if (i >= row && j >= column // nur schreiben, wenn Koorinaten erf�llt sind.
                        && i - row < chars.length && j - column < chars[i - row].length // nur wenn char vorhanden
                        && chars[i - row][j - column] != ' ') { // transparent
                    if (chars[i - row][j - column] == nonTransparentSpace) {
                        canvas[i][j] = ' ';
                    } else {
                        canvas[i][j] = chars[i - row][j - column];
                    }
                }
            }
        }
    }

    /**
     * Zeigt den aktuellen Inhalt des Canvas im Fenster an. Diese Methode sollte
     * h�chstens alle 16 ms aufgerufen werden (60 Frames pro Sekunde).
     */
    public void printCanvas() {
        countPrints++;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < canvas.length; i++) {
            for (int j = 0; j < canvas[i].length; j++) {
                sb.append(canvas[i][j]);
            }
            if (i < canvas.length - 1) {
                sb.append("\n");
            }
        }
        printThis = sb.toString();
        SwingUtilities.invokeLater(() -> area.setText(printThis));

        // Check FPS
        long now = System.currentTimeMillis();
        if (now - lastTime > 1000) {
            if (countPrints >= 65) {
                setStatusLabel(Color.RED, " Achtung: Zu viele Frames pro Sekunde: " + countPrints + " FPS!");
            } else {
                setStatusLabel(Color.BLACK, statusTextLinks);
            }
            countPrints = 0;
            lastTime = now;
        }
    }

    /**
     * Gibt den aktuellen Inhalt des Canvas zur�ck.
     *
     * @return Aktueller Canvas.
     */
    public char[][] getCanvas() {
        return canvas;
    }

    /**
     * Das undurchsichtige Leerzeichen kann mit dieser Methode auf ein anderes
     * Zeichen gesetzt werden. In den Methoden <code>addToCanvas(...)</code> ist das
     * normale Leerzeichen durchsichtig (der Hintergrund ist zu sehen). Falls ein
     * undurchsichtiges Leerzeichen ben�tigt wird, kann statt dessen das Semikolon
     * ";" verwendet werden. Diese Methode erlaubt es, die Voreinstellung zu �ndern
     * und ein anderes Zeichen zu w�hlen.
     *
     * @param c Das neue undurchsichtige Leerzeichen.
     */
    public void setNonTransparentSpaceForCanvas(char c) {
        nonTransparentSpace = c;
    }

    /**
     * Liefert alle Tastendr�cke die seit dem letzten Aufruf dieser Methode
     * aufgelaufen sind als Liste zur�ck. Es werden maximal die neuesten 25
     * Ereignisse zur�ckgegeben, alte Ereignisse werden gel�scht.
     * <p>
     * Die Liste enth�lt Ereignisse vom Typ <code>java.awt.event.KeyEvent;</code>.
     * Der Typ des Events ist entweder <code>KeyEvent.KEY_PRESSED</code> (f�r nicht
     * sichtbare Zeichen), <code>KeyEvent.KEY_RELEASED</code> (f�r nicht sichtbare
     * Zeichen) oder <code>KeyEvent.KEY_TYPED</code>(f�r sichtbare Zeichen).
     * Sichtbare Zeichen lassen sich mit der Methode <code>getKeyChar()</code>
     * auswerten.
     *
     * <pre>
     * <code>
     * if (keyEvent != null) {
     *   if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
     *     if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
     *       System.out.println("Eingabetaste");
     *     }
     *   } else if (keyEvent.getID() == KeyEvent.KEY_TYPED) {
     * 		 System.out.println(keyEvent.getKeyChar());
     *     }
     * }
     * </code>
     * </pre>
     *
     * <br>
     *
     * @return Alle <code>KeyEvent</code> Ereignisse seit dem letzten Aufruf dieser
     * Methode.
     */
    public LinkedList<KeyEvent> getKeyEvents() {
        int size = keyboardEvents.size();
        LinkedList<KeyEvent> keyEvents = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            keyEvents.add(keyboardEvents.remove());
        }
        return keyEvents;
    }

    /**
     * Liefert alle Mausereignisse, seit dem letzten Aufruf dieser Methode
     * aufgelaufen sind als Liste zur�ck. Es werden maximal die neuesten 25
     * Ereignisse zur�ckgegeben, alte Ereignisse werden gel�scht.
     * <p>
     * Die Liste enth�lt Ereignisse vom Typ <code>java.awt.event.MouseEvent;</code>
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
     * if (mouseEvent != null) {
     *   if (mouseEvent.getID() == MouseEvent.MOUSE_CLICKED) {
     *     System.out.println("Geklickt in Spalte: " + mouseEvent.getX());
     *   }
     * }
     * </code>
     * </pre>
     *
     * @return Alle <code>MouseEvent</code> Ereignisse seit dem letzten Aufruf
     * dieser Methode.
     */
    public LinkedList<MouseEvent> getMouseEvents() {
        int size = mousePointerEvents.size();
        LinkedList<MouseEvent> mouseEvents = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            mouseEvents.add(mousePointerEvents.remove());
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

    private void intitFrame(String fensterName) {

        Box box = new Box(BoxLayout.Y_AXIS);
        area = new TextPanel(printThis, box);
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
        outer.setBackground(Color.black);
        outer.add(box, BorderLayout.CENTER);
        outer.add(statuszeile, BorderLayout.SOUTH);

        frame = new JFrame(fensterName);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(outer);

        frame.pack();
        frame.setResizable(true);

        // Small size
        frame.setSize(960, 540);
        area.setFontsizeThatFitsWindow();
        frame.pack();
        frame.setMinimumSize(frame.getSize());

        // Listener
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if (isSplashFinished) {
                    SwingUtilities.invokeLater(() -> {
                        area.setFontsizeThatFitsWindow();
                    });
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
                    resizeTimer.schedule(resizeTask, 400);
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
        changeResolution(18, 32);
        area.setFont(area.getFont().deriveFont(Font.BOLD));
        addToCanvasCentered(SPLASH);
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
        if (lines < 9 || lines > 135 || rows < 16 || rows > 240) {
            System.err.println("Fenster hat falsche Gr��e: Zeilen von 9 bis 135, Spalten von 16 bis 240");
            System.exit(1);
        }
        this.zeilen = lines;
        this.spalten = rows;
        initCanvas(zeilen, spalten);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                sb.append(" ");
            }
            if (i < zeilen - 1) {
                sb.append("\n");
            }
        }
        this.printThis = sb.toString();
    }

    private void initCanvas(int zeilen, int spalten) {
        canvas = new char[zeilen][spalten];
        clearCanvas();
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

    private class TextPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private String[] drawMe;
        private int lineHeight;
        private int height;
        private int width;
        private Map<TextAttribute, Object> fontMap;
        private Box box;
        private Font font;

        public TextPanel(String text, Box box) {
            this.drawMe = text.split("\n");
            this.box = box;
            setBackground(Color.black);
            setForeground(Color.white);
            fontMap = new HashMap<>();
            fontMap.put(TextAttribute.FAMILY, "Monospaced");
            fontMap.put(TextAttribute.WIDTH, 1.35);
            setFontsize(20);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < drawMe.length; i++) {
                g.drawString(drawMe[i], 0, (i + 1) * lineHeight);
            }

        }

        public void setText(String text) {
            this.drawMe = text.split("\n");
            this.repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(width, height);
        }

        @Override
        public Dimension getMaximumSize() {
            return getPreferredSize();
        }

        public void setFontsize(int fontsize) {
            calculateDimensionsForFont(fontsize);
            setFont(new Font(fontMap));
        }

        private void calculateDimensionsForFont(int fontsize) {
            fontMap.put(TextAttribute.SIZE, fontsize);
            font = new Font(fontMap);
            this.lineHeight = (int) Math.round(getFontMetrics(font).getHeight() * 0.6);
            this.height = 2 + drawMe.length * lineHeight;
            this.width = 2 + drawMe[0].length() * getFontMetrics(font).getWidths()[100];
        }

        public void setFontsizeThatFitsWindow() {
            int fontsize = 5;
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
            setFontsize(fontsize);
        }
    }
}
