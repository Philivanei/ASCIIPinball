package asciipinball.corelogic.playersandscore;

import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Speichern und Laden des HighScores
 */
public class HighScoreManager {

    private String pathToFile = System.getProperty("user.home")+"\\.ASCIIPinball\\highscore.json";

    /**
     * Erstellt einen HighScoreManager
     */
    public HighScoreManager(){

    }



    /**
     * Ließt den Highscore aus Highscore Datei ein
     * @return gespeicherter HighScore
     */
    public long getHighScore(){
        JSONObject jsonObject = getJsonObjectFromFile();

        if(jsonObject == null){
            return 0;
        }
        return (long) getJsonObjectFromFile().get("score");
    }

    /**
     * Ließt den Namen aus Highscore Datei ein
     * @return gespeicherter Name
     */
    public String getHighScoreName(){
        JSONObject jsonObject = getJsonObjectFromFile();

        if(jsonObject == null){
            return "";
        }
        return (String) getJsonObjectFromFile().get("name");
    }

    private JSONObject getJsonObjectFromFile(){

        System.out.println("OPENIING");

        String fileString = "";
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fileString = sb.toString();
        } catch (FileNotFoundException e) {
            new File(System.getProperty("user.home")+"/.ASCIIPinball").mkdir();
            setHighScore(new Player(),"");
            getJsonObjectFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // parsing from File
        Object obj = null;
        try {
            obj = new JSONParser().parse(fileString);
        } catch (ParseException ignore) {
            /*If this exception get's called Highscore will not work - And That's Ok - If Windows don't want us to have
            fun, then it shall be like that */
        }

        // typecasting obj to JSONObject
        return (JSONObject) obj;
    }

    /**
     * Speichert den übergebenen Spieler in die Highscoredatei
     * @param player Spieler dessen Score gespeichert werden soll
     * @param name Name des Spielers
     */
    //The error that gets suppresst is created by Yidong Fang - not by us - Yes i know that sounds odd -
    //But it's the truth - it's simple JSONs fault
    @SuppressWarnings("unchecked")
    public void setHighScore(Player player, String name){

        System.out.println("WRITING");

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name",name);
        jsonObject.put("score", player.getScore());

        String jsonString = jsonObject.toJSONString();

        try {
            FileWriter writer = new FileWriter(pathToFile);
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
