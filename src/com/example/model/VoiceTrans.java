package com.example.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName(VoiceTrans.VOICETRANS_CLASS)
public class VoiceTrans extends AVObject{

	static final String VOICETRANS_CLASS = "VoiceTrans";
	
	private static final String VOICEID = "VoiceId";
	private static final String INPUTCONTENT = "InputContent";
	private static final String USERID = "UserId";
	
	public String getVoiceId() {
		return this.getString(VOICEID);
	}
	
	public void setVoiceId(String voiceId) {
		this.put(VOICEID, voiceId);;
	}
	
	public String getInputContent() {
		return this.getString(INPUTCONTENT);
	}
	
	public void setInputContent(String content) {
		this.put(INPUTCONTENT, content);;
	}
	
	public String getUserId() {
		return this.getString(USERID);
	}
	
	public void setUsereId(String UserId) {
		this.put(USERID, UserId);;
	}
}
