package soundMaking;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;

public class SineWaveSample extends InputStream {
	boolean signed = true;
	boolean bigEndian = true;
	float sampleRate = 44100;
	int sampleSizeByte = 2;
	int channels = 1;
	private double freq;
	private double seconds;

	ArrayList<Byte> list;
	double index;
	double volume;

	public SineWaveSample(double freq, double seconds) {
		this.freq = freq;
		this.seconds = seconds;
		list = new ArrayList<>();
		index = 0;
		volume = Math.pow(2, sampleSizeByte * 8 - 1) - 1;
	}

	@Override
	public int read() throws IOException {
		double time = seconds / (double) sampleRate;
		double omegaT = freq * 2.0 * Math.PI * time;
		ByteBuffer buffer = ByteBuffer.allocate(2);
		if (list.isEmpty()) {
			double deltaT = omegaT * index;
			short value = (short) (Math.sin(deltaT) * volume);
			index++;

			buffer = ByteBuffer.allocate(2);
			buffer.putShort(value);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			buffer.flip();
			list.add(buffer.get());
			list.add(buffer.get());
		}
		int ret = Byte.toUnsignedInt(list.remove(0));
		return ret;
	}

	public long length() {
		return (long) (sampleRate * channels * seconds);
	}

	public AudioFormat getFormat() {
		return new AudioFormat(sampleRate, sampleSizeByte * 8, channels, signed, bigEndian);
	}

}
