package client;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TestAudio {
	public static void main(String[] args){
		byte[] b=readAudio();
		System.out.println(b);
	}
	
	public static byte[] readAudio(){
		//定义一个AudioInputStream用于接收输入的音频数据
		AudioInputStream audioInputStream = null;
		//使用AudioSystem来获取音频的音频输入流
		try {
			File sourceFile = new File("C:\\Users\\ibm\\Downloads\\tip1.mp3");
		audioInputStream = AudioSystem.getAudioInputStream(sourceFile);
		} catch (UnsupportedAudioFileException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		//4,用AudioFormat来获取AudioInputStream的格式
		AudioFormat format = audioInputStream.getFormat();
		//5.源数据行SoureDataLine是可以写入数据的数据行
		SourceDataLine auline = null;
		//获取受数据行支持的音频格式DataLine.info
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		//获得与指定info类型相匹配的行
		try {
		auline = (SourceDataLine) AudioSystem.getLine(info);
		//打开具有指定格式的行，这样可使行获得所有所需系统资源并变得可操作
		auline.open();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//允许某一个数据行执行数据i/o
		auline.start();

		//写出数据
		int nBytesRead = 0;
		byte[] abData = new byte[2];
		//从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
		try {
		while (nBytesRead != -1) {
		nBytesRead = audioInputStream.read(abData, 0, abData.length);
		//通过此源数据行将数据写入混频器
		if (nBytesRead >= 0)
		auline.write(abData, 0, nBytesRead);
		}
		} catch (IOException e) {
		e.printStackTrace();

		} finally {
		auline.drain();
		auline.close();
		}
return abData;

		
	}
}
