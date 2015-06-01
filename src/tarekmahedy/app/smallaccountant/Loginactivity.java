package tarekmahedy.app.smallaccountant;

import tarekmahedy.app.smallaccountant.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Loginactivity extends Activity{
	int lang=2;

	Button loginbtn;
	EditText userna;
	EditText pass;
	Aboutdialog aboudial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		String langu=getString(R.string.langdir);
		lang=Integer.valueOf(langu);
		
		int contentid=R.layout.login;
		if(lang==1)contentid=R.layout.login1;

		setContentView(contentid);
		aboudial=new Aboutdialog(this);

		loginbtn=(Button)findViewById(R.id.btnLogin);
		userna=(EditText)findViewById(R.id.username);
		pass=(EditText)findViewById(R.id.pass);

		loginbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
				String username=prefs.getString("username","");
				String password=prefs.getString("password","");

				if(username.equals(userna.getText().toString())&&password.equals(pass.getText().toString())){
					finish();
					Intent switchint=new Intent(getBaseContext(),SmallAccountActivity.class);
					startActivity(switchint);
				}
				else {

					//notify user about the error

				}

			}
		});


	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.basicoptionmenu, menu);

		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.iconlogout: 

			Loginactivity.this.finish();   
			break;
		case R.id.iconabout: 
			if(aboudial!=null){
				aboudial.show();
				aboudial.setContenttext(getString(R.string.aboutcontent));

			}
			break;

		case R.id.iconhelp: 

			if(aboudial!=null){
				aboudial.show();
				aboudial.setContenttext(getString(R.string.helpcontent));
			}

			break;


		}
		return true;
	}





}
