package ad.simple.library;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by pugman on 16.01.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class BannerView extends DialogFragment implements View.OnClickListener{

	private static final String ARG_URL = "ad_url";

	/**
	 *
	 * @param url will be loaded in banner view
	 */
	public static BannerView newInstance(@NonNull String url){
		Bundle args = new Bundle();
		args.putString(ARG_URL, url);
		BannerView fragment = new BannerView();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//attribute that makes the banner view full-screen
		setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
		Bundle args = getArguments();
		String url = args != null ? args.getString(ARG_URL) : "";
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
		bannerView.loadUrl(url);
		return contentView;
	}

	@Override
	public void onClick(View v){
		//Called when close icon pressed
		dismiss();
	}
}
