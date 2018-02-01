package ad.simple;

import android.app.Activity;
import android.os.Bundle;

import ad.simple.library.BannerView;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BannerView bannerView = BannerView.newInstance(getPackageName());
		bannerView.show(getFragmentManager(), "BANNER");
	}
}
