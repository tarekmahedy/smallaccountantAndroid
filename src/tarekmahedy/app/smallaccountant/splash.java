package tarekmahedy.app.smallaccountant;
//that for ads
import group.pals.android.lib.ui.lockpattern.LockPatternActivity;
import tarekmahedy.app.smallaccountant.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;


public class splash extends Activity {
	
	protected boolean _active = true;
	protected int _splashTime = 3000;

	
	@Override
	public void onBackPressed() {
		
       finish();
	  //super.onBackPressed();
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);

		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean viewsplash =prefs.getBoolean("spalsh",false);
		
		
		if(!viewsplash){

			if(!resto){
				setContentView(R.layout.splashscreen);

				Handler handler = new Handler();

				// run a thread after 2 seconds to start the home screen
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						
						
							viewmainactivity();
					}

				}, _splashTime);

			}else {

				Intent switchint=new Intent(this,SmallAccountActivity.class);
				startActivity(switchint);
                splash.this.finish();
			}
		}else viewmainactivity();

		
	}

	SharedPreferences prefs;
	private static int _ReqCreatePattern = 0;

	void viewmainactivity(){

		
		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean autologin = prefs.getBoolean("autologin",false);
		String savedpa = prefs.getString("savepatter","0");
		boolean registerd =prefs.getBoolean("registerd",false);
		
		
		if(autologin){
			Intent  switchint=new Intent(this,SmallAccountActivity.class);
			startActivity(switchint);
			splash.this.finish();
		}
		else {
			Intent intent = new Intent(this, LockPatternActivity.class);
			
			if(savedpa.equals("0")){
				
//				RegisterDialog regdial=new RegisterDialog(this);
//				regdial.show();
				
				
				 AlertDialog.Builder ab = new AlertDialog.Builder(this);
			      ab.setMessage(getString(R.string.addpasswordconfirm)).setPositiveButton(getString(R.string.yeslabel), dialogClickListener)
			    .setNegativeButton(getString(R.string.nolabel), dialogClickListener).show();
  
			      
			      
			      
			}else {
				_ReqCreatePattern=0;
				intent.putExtra(LockPatternActivity._Mode, LockPatternActivity.LPMode.ComparePattern);
				intent.putExtra(LockPatternActivity._Pattern, savedpa);
				startActivityForResult(intent,_ReqCreatePattern);
			}
		
		}
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode==RESULT_OK){
		if(requestCode==1){
			String pattern = data.getStringExtra(LockPatternActivity._Pattern);
            SharedPreferences.Editor editor = prefs.edit();
			editor.putString("savepatter", pattern);
			editor.commit();
			Intent switchint=new Intent(this,SmallAccountActivity.class);
			startActivity(switchint);
			splash.this.finish();
		}
		else{
			   Intent switchint=new Intent(this,SmallAccountActivity.class);
			   startActivity(switchint);
			   splash.this.finish();
			
		}
		
		}else if(resultCode==RESULT_CANCELED)finish();
		
		
		  
	}

	
	
DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		
		
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	    _ReqCreatePattern=1;
	        	    Intent intent = new Intent(getBaseContext(), LockPatternActivity.class);
	    			intent.putExtra(LockPatternActivity._Mode, LockPatternActivity.LPMode.CreatePattern);
					startActivityForResult(intent,_ReqCreatePattern);
					
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	        	prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	        	 SharedPreferences.Editor editor = prefs.edit();
	 			 editor.putBoolean("autologin",true);
	 			 editor.commit();
	 			 Intent switchint=new Intent(getBaseContext(),SmallAccountActivity.class);
				 startActivity(switchint);
				 splash.this.finish();
	            break;
	        }
	    }
	};
	
	
	
	Boolean resto=false;

	
	@Override
	protected void onRestoreInstanceState(Bundle state) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(state);
		resto=true;
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}



}
