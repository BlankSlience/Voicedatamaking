package com.example.dao;

import java.util.Collections;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.LogUtil.log;
import com.example.model.Voice;

public class VoiceDao{
	public static List<Voice> findVoiceByNotTrans() {	
		AVQuery<Voice> query = AVQuery.getQuery(Voice.class);
		query.whereEqualTo("IsTrans", false);
		query.orderByDescending("updatedAt");
		
		 try {
			 log.e("Query Voice success");
		      return query.find();
		  } catch (AVException exception) {
		      log.e("Query Voice failed.", exception);
		      return Collections.emptyList();
		  }
	}

}
