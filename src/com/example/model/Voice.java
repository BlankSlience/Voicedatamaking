package com.example.model;

import java.io.File;
import java.io.Serializable;
import java.net.URISyntaxException;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.LogUtil.log;

import android.net.Uri;

@AVClassName(Voice.VOICE_CLASS)
public class Voice extends AVObject{
	static final String VOICE_CLASS = "Voice";
	
	public static final String VOICEFILE = "VoiceFile";
	public static final String ISTRANS = "IsTrans";
	public static final String VOICEID = "objectId";

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
		AVFile voiceFile = this.getAVFile(VOICEFILE);
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
