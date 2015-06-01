package tarekmahedy.app.smallaccountant;

import java.io.IOException;
import java.util.Random;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UIBackup extends Screen {

	Thread backupthread;
	private Handler mHandler = new Handler();
	ProgressDialog loadingdia=null;
	Button backupbtn;
	EditText telnumber;
	EditText password;
	TextView tellabel;
	TextView passwordlabel;
	int type=0;

	public UIBackup(Context context) {
		super(context);
		int resuid=R.layout.backuplayout;
		inflate(context,resuid,this);

		initui();
		autobackup();
	}

	public void setphonenumber(String _myphone){

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("myphone", _myphone);
		editor.commit();

	}

	
	
	
	public  String getphonenumber(){
		String myphone="";
		try{
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
			myphone =prefs.getString("myphone","0");
			if(myphone=="0"){
				myphone=getMyPhoneNumber();
				if(myphone.length()<5){
					myphone=getnumberwhatsapp();
					return myphone;

				}else return myphone;

			}else return myphone;

		}catch (Exception e) {

		}

		return myphone;
	}


	
	
	
	public String getnumberwhatsapp(){
		String accv="";
		try{

			AccountManager am = AccountManager.get(getContext());
			Account[] accounts = am.getAccounts();
			for (Account ac : accounts) {
				String actype = ac.type;
				if(actype.equals("com.whatsapp"))
					return ac.name;
			}


			return accv;
		}catch (Exception e) {

		}

		return accv;

	}


	public String getMyPhoneNumber(){
		try{
			TelephonyManager mTelephonyMgr;
			mTelephonyMgr = (TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE); 
			return mTelephonyMgr.getLine1Number();
		}catch (Exception e) {
			return "";
		}

	}

	public void initui(){

		liner=(LinearLayout)findViewById(R.id.mainliner);
		telnumber=(EditText)findViewById(R.id.teledit);
		telnumber.setText(getphonenumber());
		password=(EditText)findViewById(R.id.passwordedit);

		tellabel=(TextView)findViewById(R.id.telid);
		passwordlabel=(TextView)findViewById(R.id.passid);
		backupbtn=(Button)findViewById(R.id.btnRegister);
		backupbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				helper.telnumber=telnumber.getText().toString();
				helper.password=password.getText().toString();

				if(helper.password.length()<1||helper.telnumber.length()<1){
					//	Toast.makeText(_context,_context.getString(R.string.validtel), Toast.LENGTH_LONG).show();
					Headerbar.alert(_context.getString(R.string.validtel), true);

				}
				else {
					if(type==1)
						loadingdia=ProgressDialog.show(_context,_context.getString(R.string.restorebtn), _context.getString(R.string.waitlabel), true);
					else 
						loadingdia=ProgressDialog.show(_context,_context.getString(R.string.backupbtn), _context.getString(R.string.waitlabel), true);

					setphonenumber(helper.telnumber);
					backupthread=new Thread(initmsgner);
					backupthread.start();


				}
			}
		});


	}




	public void autobackup(){


		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(_context);
		int versionnmuber=prefs.getInt("version", 0);

		if(versionnmuber<3){

			Random re=new Random();
			re.nextInt();
			helper.telnumber=getphonenumber();
			helper.password=String.valueOf(re.nextInt());
			SharedPreferences.Editor editor = prefs.edit();
			editor.putInt("version", 3);
			editor.commit();
			helper.updatedb(helper.difquery);


		}

	}


	public void initscreen(){

		if(lang!=1){

			telnumber.setGravity(Gravity.LEFT);
			password.setGravity(Gravity.LEFT);
			tellabel.setGravity(Gravity.LEFT);
			passwordlabel.setGravity(Gravity.LEFT);


		}
		if(type==1){

			backupbtn.setText(this._context.getString(R.string.restorebtn));
		}



	}




	Runnable initmsgner=new Runnable() {

		public void run() {  


			if(type==0)
				helper.backupdb();
			else helper.postData("-1");

			mHandler.post(new Runnable() {
				public void run() {
					loadingdia.dismiss();
					Headerbar.alert(_context.getString(R.string.donkey), true);



				}
			});






		}
	};




}














