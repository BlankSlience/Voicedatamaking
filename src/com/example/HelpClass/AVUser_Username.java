package com.example.HelpClass;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.avos.avoscloud.AVUser;
import com.example.UIhelper.BackgroundTask;
import com.example.dao.UserDao;
import com.example.model.User;

public class AVUser_Username {
	
	public final BlockingQueue<Object> result = new ArrayBlockingQueue<Object>(1);
	
	public void getUsername(final AVUser user) {
		new BackgroundTask() {
			@Override
			protected void doInBack() throws Exception {
				UserDao dao = new UserDao();
	    		User temp = dao.getUserById(user.getObjectId());
	    		String username = temp.getUsername();
	    		result.put(username);
			}
			
			@Override
			protected void onPost(Exception e) {
				
			}
		}.execute();
	}
}
