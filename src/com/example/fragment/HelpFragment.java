package com.example.fragment;

import com.example.uiwork.R;
import com.example.utils.UiHelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HelpFragment extends BasicFragment {
	private Button englishDetailBtn;
	

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UiHelper.getLightThemeView(getActivity(), R.layout.fragment_help);
    }
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		
		englishDetailBtn = (Button) v.findViewById(R.id.yaoqiudetail);
		englishDetailBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UiHelper.showFragment(HelpFragment.this, null, new EnglishRequestDetailFragment());
			}
		});
	}

}
