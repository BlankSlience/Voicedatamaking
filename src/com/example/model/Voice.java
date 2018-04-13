package com.example.model;

import java.io.File;
import java.net.URISyntaxException;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

import android.net.Uri;

@AVClassName(Voice.VOICE_CLASS)
public class Voice extends AVObject{
	static final String VOICE_CLASS = "Voice";
	
	private static final String VOICEFILE = "VoiceFile";
	private static final String ISTRANS = "IsTrans";
	private static final String VOICEID = "objectId";

	public AVFile getVoice() {
		return this.getAVFile(VOICEFILE);
	}
	
	public void setVoice(AVFile voice) {
		this.put(VOICEFILE, voice);
	}	
	
	public Boolean getIsTrans() {
		return this.getBoolean(ISTRANS);
	}
	
	public void setIsTrans(Boolean trans) {
		this.put(ISTRANS, trans);
	}
	
	public Uri getVoiceURI() throws URISyntaxException {
		AVFile voiceFile = getAVFile(VOICEFILE);
		if(voiceFile != null) {
			String voiceurl =  voiceFile.getUrl();
			Uri uri = Uri.fromFile(new File(voiceurl));		
			return uri;
		} else {
			return null;
		}
	}
	
	public String getCurrentVoiceId() {
		return this.getObjectId();
	}
}
