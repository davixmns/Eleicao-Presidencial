package utilidade;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    public static final String confirma = "sons/urna_confirma.wav";
    public static final String fim = "sons/urna_fim.wav";
    public static final String erro = "sons/erro.wav";

    public static void tocarSom(String caminho){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(caminho).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            System.out.println("Erro ao executar SOM!");
            ex.printStackTrace();
        }
    }
}
