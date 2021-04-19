package soundMaking;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		double freq = scanner.nextDouble();
		double seconds = scanner.nextDouble();
		SineWaveSample sws = new SineWaveSample(freq, seconds);
		AudioSystem.write(
				new AudioInputStream(sws, sws.getFormat(), sws.length()),
				AudioFileFormat.Type.WAVE, new File("sinwave.wav"));
		scanner.close();
	}

}
