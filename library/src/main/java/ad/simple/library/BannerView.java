package ad.simple.library;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/*
  Created by pugman on 16.01.18.
  Contact the developer - sckalper@gmail.com
  company - A2Lab
 */

/**
 * Created for displaying fullscreen advertising
 */
public class BannerView extends DialogFragment implements View.OnClickListener{

	private static final String ARG_PACKAGE_NAME = "ad_package_name";

	private String adUrl = null;

	/**
	 *
	 * @param packageName on which to return ad's url
	 */
	public static BannerView newInstance(@NonNull String packageName){
		Bundle args = new Bundle();
		args.putString(ARG_PACKAGE_NAME, packageName);
		BannerView fragment = new BannerView();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//attribute that makes the banner view full-screen
		setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);

		Bundle args = getArguments();
		String packageName = args != null ? args.getString(ARG_PACKAGE_NAME) : "";

		HttpGetRequest request = new HttpGetRequest();
		try{
			adUrl = request.execute(packageName).get();
		} catch(InterruptedException e){
			Log.e("RESULT", "Interrupted: " + e.getMessage());
		} catch(ExecutionException e){
			Log.e("RESULT", "Execution failed: " + e.getMessage());
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
		//Banner view layout
		FrameLayout contentView = new FrameLayout(getActivity());
		//Create and init web view
		WebView bannerView = new WebView(contentView.getContext());
		FrameLayout.LayoutParams bannerLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		bannerView.setLayoutParams(bannerLayoutParams);
		contentView.addView(bannerView);
		//Create and init close icon
		int closeIconSize = getActivity().getResources().getDimensionPixelOffset(R.dimen.bottomSheetPadding);
		int margin = getActivity().getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin);
		ImageView closeBut = new ImageView(contentView.getContext());
		FrameLayout.LayoutParams closeLayoutParams = new FrameLayout.LayoutParams(closeIconSize, closeIconSize);
		closeLayoutParams.gravity = Gravity.END;
		closeLayoutParams.topMargin = margin;
		closeLayoutParams.rightMargin = margin;
		closeBut.setLayoutParams(closeLayoutParams);
		closeBut.setPadding(3, 3, 3, 3);
		closeBut.setScaleType(ImageView.ScaleType.CENTER_CROP);
		closeBut.setImageResource(R.drawable.ic_close);
		closeBut.setOnClickListener(this);
		contentView.addView(closeBut);
		//Set up web view and start displaying url content
		WebViewClient bannerClient = new WebViewClient();
		bannerView.setWebViewClient(bannerClient);
		if(adUrl == null) {
			Log.e("BANNER_VIEW", "Url == null. Url cannot be loaded or was not initialized for requested package name");
			Toast.makeText(getActivity(), "Cannot load advertising", Toast.LENGTH_SHORT).show();
			dismiss();
		} else{
			bannerView.loadUrl(adUrl);
		}
		return contentView;
	}

	@Override
	public void onClick(View v){
		//Called when close icon pressed
		dismiss();
	}
}
