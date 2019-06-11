package asciipinball.corelogic.playersandscore;

import asciipinball.GameView;

import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HighScoreManager {

    String pathToFile = "res/highscore.json";

    public HighScoreManager(){

    }

    public JSONObject getJsonObject(){

        String fileString = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(pathToFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fileString = sb.toString();
        } catch (Exception e){

        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // parsing from File
        Object obj = null;
        try {
            obj = new JSONParser().parse(fileString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // typecasting obj to JSONObject
        return (JSONObject) obj;
    }

    public long getHighScore(){
        return (long) getJsonObject().get("score");
    }

    public String getHighScoreName(){
        return (String) getJsonObject().get("name");
    }

    public void setHighScore(Player player, String name){

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
