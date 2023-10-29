package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class Config {

    GamePanel gp;

    public Config(GamePanel gp) {

        this.gp = gp;
    }
    public void loadConfig() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            gp.fullScreen = Boolean.parseBoolean(br.readLine().split("=")[1]);
            gp.music.volumeIndicator = Integer.parseInt(br.readLine().split("=")[1]);
            gp.se.volumeIndicator = Integer.parseInt(br.readLine().split("=")[1]);
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void saveConfig() {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            if (gp.fullScreen) { bw.write("fullscreen=true"); }
            else { bw.write("fullscreen=false"); } bw.newLine();
            bw.write("musicVolunme=" + String.valueOf(gp.music.volumeIndicator)); bw.newLine();
            bw.write("seVolunme=" + String.valueOf(gp.se.volumeIndicator)); bw.newLine();

            bw.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
