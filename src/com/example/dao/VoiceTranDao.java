package com.example.dao;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import android.util.Log;

public class VoiceTranDao {
	
	public static void addOneTrans(String VoiceId, String Content, String UserId) {
		final AVObject object = new AVObject("VoiceTrans");
		object.put("VoiceId", VoiceId);
		object.put("InputContent", Content);
		object.put("UserId", UserId);
		
		object.saveInBackground(new SaveCallback(){
			@Override
			public void done(AVException e) {
				if (e==null) {
					Log.d("AddQuestion", object.getObjectId());
				}else {
					System.out.println("fail to add one voiceTrans");
				}
			}
		});
	}
}
