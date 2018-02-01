package ad.simple;

import android.app.Activity;
import android.os.Bundle;

import ad.simple.library.BannerView;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BannerView bannerView = BannerView.newInstance("https://stackoverflow.com/questions/18097748/how-to-get-row-count-in-sqlite-using-android");
		bannerView.show(getFragmentManager(), "BANNER");
	}
}
