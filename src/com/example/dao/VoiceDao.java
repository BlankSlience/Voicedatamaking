package com.example.dao;

import java.util.Collections;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.LogUtil.log;
import com.example.model.Voice;

public class VoiceDao {
	
	public static List<Voice> findVoiceByNotTrans() {
		AVQuery<Voice> query = AVQuery.getQuery(Voice.class);
		query.whereEqualTo("IsTrans", false);
		query.orderByDescending("updatedAt");
		
		query.limit(1000);
		try{
			return query.find();
		} catch(AVException e) {
			log.e("tag", "Query voices failed.", e);
			return Collections.emptyList();
		}
	}

}
