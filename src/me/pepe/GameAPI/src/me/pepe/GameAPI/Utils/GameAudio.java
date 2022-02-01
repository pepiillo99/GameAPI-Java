package me.pepe.GameAPI.Utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public abstract class GameAudio extends Thread {
	private String fileName;
	private Clip clip;
	public GameAudio(String fileName) {
		this.fileName = fileName;
		start();
	}
	@Override
	public void run() {
		try {
			AudioInputStream audioInputStream;
			audioInputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("audio/" + fileName));
		    clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    clip.start();
		    sleep(100);
		    System.out.print("audio started " + clip.isRunning());
		    while (clip.isRunning()) {
		    	  sleep(1);
		    }
		    System.out.println("audio finished");
		    onFinish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public abstract void onFinish();
}
