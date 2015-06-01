package tarekmahedy.app.smallaccountant;

import tarekmahedy.app.smallaccountant.R;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class settingactivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		addPreferencesFromResource(R.layout.setting);



	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
