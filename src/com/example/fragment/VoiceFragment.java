package com.example.fragment;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.URISyntaxException;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.LogUtil.log;
import com.example.UIhelper.LimitInputTextWatcher;
import com.example.dao.VoiceDao;
import com.example.dao.VoiceTranDao;
import com.example.model.LeanUser;
import com.example.model.Voice;
import com.example.renderer.FFTRenderer;
import com.example.renderer.LineRenderer;
import com.example.uiwork.R;
import com.example.utils.TunnelPlayerWorkaround;
import com.example.utils.UiHelper;
import com.example.widget.VisualizerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class VoiceFragment extends BasicFragment implements UncaughtExceptionHandler{
	private MediaPlayer mPlayer;
	private MediaPlayer nPlayer;
    private MediaPlayer mSilentPlayer;  /* to avoid tunnel player issue */
    private MediaPlayer nSilentPlayer; 
    private VisualizerView mVisualizerViewBopu;
    private VisualizerView mVisualizerViewPinpu;
    private Button requestData;
    private Button play;
    private Button stop;
    private Button replay;
    private EditText userInput;
    private Button saveBtn;
    private Button notSaveBtn;
    private List<Voice> voiceList;
    private LeanUser currentUser;
    private Voice currentVoice = null;
    private Boolean isComplete = false;
    private Boolean isLoadData = false;
    private int position = 0;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_tingxie);
    }
    
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		currentUser = LeanUser.getCurrentUser();
		
		//在此调用下面方法，才能捕获到线程中的异常
        Thread.setDefaultUncaughtExceptionHandler(this);
 	
		new Thread(new Runnable() {

			@Override
			public void run() {
				voiceList = VoiceDao.findVoiceByNotTrans();
				log.e("into thread");
				if(voiceList != null) {
					int size = voiceList.size();
					log.e("voiceList size:" + size);
				} else {
					log.e("voicelist null:" + voiceList);
				}
			}
			
		}).start();
		
			
		mPlayer = MediaPlayer.create(getActivity(), R.raw.jielun);
		nPlayer = MediaPlayer.create(getActivity(), R.raw.jielun);

         //We need to link the visualizer view to the media player so that
        // it displays something
        mVisualizerViewBopu = (VisualizerView) v.findViewById(R.id.visualizerViewBopu);
        mVisualizerViewBopu.link(mPlayer);
        
        mVisualizerViewPinpu = (VisualizerView) v.findViewById(R.id.visualizerViewPinpu);
        mVisualizerViewPinpu.link(nPlayer);
        
        requestData = (Button) v.findViewById(R.id.requestData);
        play = (Button) v.findViewById(R.id.startplay);
        stop = (Button) v.findViewById(R.id.stopplay);
        replay = (Button) v.findViewById(R.id.restart);
        userInput = (EditText) v.findViewById(R.id.input_edittext);
        saveBtn = (Button) v.findViewById(R.id.inputCommit);
        notSaveBtn = (Button) v.findViewById(R.id.callback);
        
//      if(voiceList.size() == 0) {
//			showVoiceListEmpty();
//			requestData.setClickable(false);
//		} 
        
//        if(currentVoice != null) {
//        	play.setClickable(true);
//        	stop.setClickable(true);
//        	replay.setClickable(true);
//        }
        
        requestData.setOnClickListener(requestDataListener);
        play.setOnClickListener(playListener);
        stop.setOnClickListener(stopListener);
        replay.setOnClickListener(replayListener);
        saveBtn.setOnClickListener(saveListener);
        notSaveBtn.setOnClickListener(notSaveListener);
        
        //使用LimitInputTextWatcher中默认的输入规则
        //userInput.addTextChangedListener(new LimitInputTextWatcher(userInput));
        initTunnelPlayerWorkaround();
         //Start with just line renderer
        addLineRenderer();
        addFFTRenderer();
    }
    
    @Override
	public void onDestroy()
    {
        cleanUp();
        super.onDestroy();
    }
    
    private void cleanUp()
    {
        if (mPlayer != null)
        {
            mVisualizerViewBopu.release();
            mPlayer.release();
            mPlayer = null;
        }
        

        if (nPlayer != null)
        {
            mVisualizerViewPinpu.release();
            nPlayer.release();
            nPlayer = null;
        }

        if (mSilentPlayer != null)
        {
            mSilentPlayer.release();
            mSilentPlayer = null;
        }
        
        if (nSilentPlayer != null)
        {
            nSilentPlayer.release();
            nSilentPlayer = null;
        }
    }
    
    OnClickListener requestDataListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			
//			currentVoice = voiceList.get(position);	
//			try {
//				Uri uri = currentVoice.getVoiceURI();
//			} catch (URISyntaxException e1) {
//				e1.printStackTrace();
//			}
//			try {
//				mPlayer = MediaPlayer.create(getActivity(),currentVoice.getVoiceURI());
//				nPlayer = MediaPlayer.create(getActivity(),currentVoice.getVoiceURI());
//			} catch (URISyntaxException e) {
//				e.printStackTrace();
//			}
//			mVisualizerViewBopu.link(mPlayer);
//			mVisualizerViewPinpu.link(nPlayer);
//			//Start with just line renderer
//	        addLineRenderer();
//	        addFFTRenderer();
//			isLoadData = true;
//			
//			if(isComplete){
//				requestData.setClickable(true);
//			}
//
//	        if(!isComplete || isLoadData){
//	        	requestData.setClickable(false);
//	        }
		}
    	
    };
    
    OnClickListener playListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if(mPlayer.isPlaying())
	        {
	            return;
	        }
			if(nPlayer.isPlaying())
	        {
	            return;
	        }
	        try {
				mPlayer.prepare();
				nPlayer.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        mPlayer.start();
	        nPlayer.start();
		}
	};
    
	OnClickListener stopListener = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mPlayer.pause();
				nPlayer.pause();
			}
	};
	
	OnClickListener replayListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			 mPlayer.setLooping(true);
			 if(mPlayer.isPlaying())
		        {
		            return;
		        }
		        try {
					mPlayer.prepare();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		     mPlayer.start();
		}
	};
	
    private void initTunnelPlayerWorkaround() {
        // Read "tunnel.decode" system property to determine
        // the workaround is needed
        if (TunnelPlayerWorkaround.isTunnelDecodeEnabled(getActivity())) {
            mSilentPlayer = TunnelPlayerWorkaround.createSilentMediaPlayer(getActivity());
            nSilentPlayer = TunnelPlayerWorkaround.createSilentMediaPlayer(getActivity());
        }
    }
    
    private void addLineRenderer() {
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(1f);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.argb(88, 0, 128, 255));

        Paint lineFlashPaint = new Paint();
        lineFlashPaint.setStrokeWidth(5f);
        lineFlashPaint.setAntiAlias(true);
        lineFlashPaint.setColor(Color.argb(188, 255, 255, 255));
        LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint, true);
        mVisualizerViewBopu.addRenderer(lineRenderer);
    }
    
    private void addFFTRenderer() {
    	Paint paint = new Paint();
    	paint.setStrokeWidth(1f);
    	paint.setAntiAlias(true);
    	paint.setColor(Color.argb(88, 0, 128, 255));
    	FFTRenderer fftRenderer = new FFTRenderer(paint);
    	mVisualizerViewPinpu.addRenderer(fftRenderer);
    }

    
    OnClickListener saveListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {			
			isComplete = saveToBackground();
			isLoadData = false;
		}
    };
    
    OnClickListener notSaveListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			
		}
    };
    
    public boolean saveToBackground() {
    	try{
        	String VoiceId = currentVoice.getCurrentVoiceId();
        	String Content = userInput.getText().toString();
        	String UserId = currentUser.getCurrentUserId();
        	VoiceTranDao.addOneTrans(VoiceId, Content, UserId);
        	return true;
    	} catch(Exception e) {
    		Log.e("saveintovoice", "fail", e);
    		return false;
    	}
    	
    }
    
    
    private void changeVoice(int position) {
    	
    }
    
    
    private void showVoiceListEmpty() {
		new AlertDialog.Builder(getActivity())
				.setTitle(
						getActivity().getResources().getString(
								R.string.dialog_message_title))
				.setMessage(
						getActivity().getResources().getString(
								R.string.voiceListEmpty))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}


	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		Log.i("AAA", "uncaughtException   " + arg1);
	}

}
