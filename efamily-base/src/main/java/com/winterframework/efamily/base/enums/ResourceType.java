package com.winterframework.efamily.base.enums;


public class ResourceType {
 
	public static final String IMAGE = "image"; // 图片
	public static final String AUDIO = "audio"; // 音频
	public static final String VIDEO = "video";   // 视频
	public static final String TEXT = "text";     // 文本 
	public static final String CLIENT = "notify"; // 推送
	public static final String TIPS = "tips"; // 推送
	
	public class VideoType {
		public static final String MP4 = "mp4";
		public static final String RM = "rm";
		public static final String RMVB = "rmvb";
		public static final String MPG = "mpg";
		public static final String SGP = "3gp";
	}

	public class AudioType {
		public static final String MP3 = "mp3";
		public static final String AVI = "avi";
		public static final String MIDI = "midi";
		public static final String WMA = "wma";
		public static final String AMR = "amr";
	}

	public class ImageType {
		public static final String BMP = "bmp";
		public static final String JPG = "jpg";
		public static final String GIF = "gif";
		public static final String PNG = "png";
	}
	public class TextType {
		public static final String TXT = "txt";
		public static final String PDF = "pdf";
	}
	
	public class ClientType {
		public static final String INVITE = "invite";
	}
	public class TipsType {
		public static final String LOW_POWER_REMIND = "low_poer_remind";
		public static final String ON_OFF_REMIND = "lowpoerremind";
		public static final String FENCE_REMIND = "fence_remind";
	}
	public class DefinitionType {
		public static final String LOW = "low";
		public static final String MIDDLE = "middle";
		public static final String HIGH = "high";
	}
}
