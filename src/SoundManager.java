import javax.sound.sampled.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SoundManager {

	// 효과음 재생
	public static void playSound(String name) {
		try {
			InputStream inputStream = SoundManager.class.getResourceAsStream(name + ".wav");
            if (inputStream == null) {
                throw new IllegalArgumentException("Sound file not found: " + name);
            }
            BufferedInputStream bufferedIn = new BufferedInputStream(inputStream);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
			Clip clip;
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
