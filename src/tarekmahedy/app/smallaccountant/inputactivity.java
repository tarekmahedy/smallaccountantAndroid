package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kankan.wheel.widget.WheelView;
import tarekmahedy.app.smallaccountant.R;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;



public class inputactivity extends Screen {



	Spinner accountfrom;
	Spinner accountto;
	Button savebtn;
	Button quickoperatbtn;
	EditText amounteditetext;
	DatePicker operationdate;
	String titlear,titleen="";
	TextView fristview;
	TextView secondview;
	TextView calddate;
	int _fromtype=-2;
	int _totype=-1;
	int _dbtype=0;
	int _type=0;
	Double amount;
	String toar, toen,fromar,fromen="";
	long toid,fromid=-1;
	CalenderDialog cald;



	private OnSaveClicked saveclicked=null;



	public inputactivity(Context context) {
		super(context);

		int resuid=R.layout.inputscreen;
		if(lang==1)resuid=R.layout.inputscreen1;
		inflate(context,resuid,this);
		_viewslist=new ArrayList<View>();
		_msgs=new ArrayList<String>();
		initui();

	}




	public void initui(){

		cald=new CalenderDialog(_context);
		fristview=(TextView)findViewById(R.id.textView1);
		secondview=(TextView)findViewById(R.id.textView2);
		fristview=SmallAccountActivity.setcustomfont(fristview, _context);
		secondview=SmallAccountActivity.setcustomfont(secondview, _context);
		calddate=(TextView)findViewById(R.id.caltxt);
		calddate.setText(cald.writeIslamicDate());
		TextView textcomme= (TextView)findViewById(R.id.comments);
		textcomme=SmallAccountActivity.setcustomfont(textcomme, _context);
		accountfrom=(Spinner)findViewById(R.id.account1);
		accountfrom.setPrompt(getContext().getString(R.string.account1label));
		accountto=(Spinner)findViewById(R.id.account2);
		
		accountto.setPrompt(getContext().getString(R.string.account2label));
		amounteditetext=(EditText)findViewById(R.id.amount);
		CurrancyAdptor cuadpt=new CurrancyAdptor(getContext());
		
		//operationdate=(DatePicker)findViewById(R.id.datePicker1);

		savebtn=(Button)findViewById(R.id.savebtn);
		savebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Boolean res=savetodb();
				if(res){
					if(saveclicked!=null)
						saveclicked.onClicked(res);
				}else
					Headerbar.alert(getContext().getString(R.string.amountfieldreq),true);

			}
		});
		
		quickoperatbtn=(Button)findViewById(R.id.quickoperation);
		quickoperatbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				addtoquickoperation();

			}
		});

		_viewslist.add(quickoperatbtn);
		_msgs.add(_context.getString(R.string.quibtn));
		ImageView addnewfrom=(ImageView)findViewById(R.id.imageView1);
		addnewfrom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addnew(1);
			}
		});


		ImageView addnewto=(ImageView)findViewById(R.id.imageView2);
		_viewslist.add(addnewto);
		_msgs.add(_context.getString(R.string.addnewtip));
		addnewto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addnew(2);
			}
		});

		ImageView calimg=(ImageView)findViewById(R.id.calimg);
		_viewslist.add(calimg);
		_msgs.add(_context.getString(R.string.savebtntip));
		calimg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				cald.setdate(calddate.getText().toString());
				cald.show();
				cald.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						CalenderDialog caldial=(CalenderDialog)dialog;
						if(caldial.cancelled==1)
							calddate.setText(cald.getdate()) ;


					}
				});
			}
		});

		ImageView calculatorimg=(ImageView)findViewById(R.id.calcimg);
		calculatorimg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {

                    Object appName;
					String packageName="";
					PackageManager pm = _context.getPackageManager();
					List<PackageInfo> packs = pm.getInstalledPackages(0);  
					for (PackageInfo pi : packs) {
						if( pi.packageName.toString().toLowerCase().contains("calcul")){
							appName= pi.applicationInfo.loadLabel(pm);
							packageName =pi.packageName;
							break;
						}
					}

					Intent calculintent = pm.getLaunchIntentForPackage(packageName);
					if (calculintent != null)


						_context.startActivity(calculintent);

				} catch (ActivityNotFoundException noSuchActivity) {

				}

			} 


		});

	}


	int _spinner=1;
	static final int PICK_CONTACT_REQUEST = 16;

	public void addnew(int _spiner){

		_spinner=_spiner;
		final addaccountdialog dialacc=new addaccountdialog(getContext());
		dialacc.show();
		if(_spiner==1)
			dialacc.setparent(_fromtype-1);
		else dialacc.setparent(_totype-1);


		dialacc.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				if(dialacc.lastinsertedid>0){
					if(_spinner==1){
						fillaccount(_fromtype,0);
						accountfrom.setSelection(accountfrom.getCount()-1);

					}
					else { 
						fillaccount(_totype,1);
						accountto.setSelection(accountto.getCount()-1);
					}
				}

			}
		});



	}

	public Boolean savetodb(){


		if(!amounteditetext.getText().toString().trim().equals("")){

			amount=Double.valueOf(amounteditetext.getText().toString());
			if(amount>0){
				if(_dbtype==1){
					fromid=accountfrom.getSelectedItemId();
					toid=accountto.getSelectedItemId();
				}else {
					fromid=accountto.getSelectedItemId();
					toid=accountfrom.getSelectedItemId();
				}

				//	Date date1= (Date) new Date(operationdate.getYear(), operationdate.getMonth(), operationdate.getDayOfMonth());
				//	String operationdates =new SimpleDateFormat("0yy/MM/dd").format(date1);

				String operationdates =calddate.getText().toString();
				String comment=((TextView)findViewById(R.id.comments)).getText().toString();
				if(	helper.addinputoperation(toid, fromid,amount, _type,operationdates.toString(),comment)>0)return true;
				else return false;

			}else return false;

		}

		return false;

	}


	private void addtoquickoperation() {

		fromid=accountfrom.getSelectedItemId();
		toid=accountto.getSelectedItemId();
		amount=Double.valueOf(amounteditetext.getText().toString());

		AddquickOperation addquicdail=new AddquickOperation(getContext());
		addquicdail.initvalues(_fromtype, _totype, _dbtype, _type, amount,toar,toen,fromar,fromen,toid,fromid);
		addquicdail.show();
		addquicdail.setSaveclicked(new AddquickOperation.OnSaveClicked() {

			@Override
			public void onClicked(Boolean _result) {
				saveclicked.addquickoperation();
			}

		});

	} 


	public void initscreen(long _inputid){


		helper.updateuseorder((int)_inputid,"input");

		Cursor _data=helper.inputrow(_inputid);

		if(_data.moveToFirst()){

			titlear=_data.getString(1);
			titleen=_data.getString(2);


			int isty=_data.getInt(3);
			if(_fromtype!=isty){
				_fromtype=isty;
				fillaccount(_fromtype,0);
			}
			isty=_data.getInt(4);
			if(_totype!=isty){
				_totype=isty;
				fillaccount(_totype,1);
			}
			toar=_data.getString(5);
			toen=_data.getString(6);
			fromar=_data.getString(7);
			fromen=_data.getString(8);
			_dbtype=_data.getInt(9);
			_type=_data.getInt(10);
			toid=_data.getInt(11);
			fromid=_data.getInt(12);
			amount=_data.getDouble(13);


		}

		if(_dbtype==0){
			fristview.setText(getContext().getString(R.string.tolabel));
			secondview.setText(getContext().getString(R.string.fromlabel));

		}else {
			fristview.setText(getContext().getString(R.string.fromlabel));
			secondview.setText(getContext().getString(R.string.tolabel));

		}

		long preselint=accountto.getSelectedItemId();
		long preselint1=accountfrom.getSelectedItemId();

		if(fromid>0&&fromid!=preselint1)
			getidpost(accountfrom,fromid);
		if(toid>0&&toid!=preselint)getidpost(accountto, toid);

		amounteditetext.setText(amount.toString());



	}


	int getidpost(Spinner _spiiner,Long _id){

		int len=_spiiner.getCount();
		int looper=0;
		while(len>looper){

			long loopid=_spiiner.getItemIdAtPosition(looper);

			if(loopid==_id){

				_spiiner.setSelection(looper);
				return looper;
			}
			looper++;
		}


		return 0;
	}


	private SQLiteDatabase database;
	private SimpleCursorAdapter dataSource;
	private SimpleCursorAdapter dataSource2;

	public void fillaccount(int _type,int _spinner){


		database = helper.getWritableDatabase();
		String colu="accountname";
		String fields[] = { colu };

		String quer="where type="+String.valueOf(_type);
		if(_type==0){

			quer="";

		}

		Cursor data = database.rawQuery("select id _id,accountname from accounts  "+quer+" order by useorder desc ",null);

		if(_spinner==0){
			dataSource = new SimpleCursorAdapter(getContext(),android.R.layout.simple_spinner_item,data,fields,new int[]{android.R.id.text1});
			dataSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			accountfrom.setAdapter(dataSource);
			if(accountfrom.getCount()<=0)accountfrom.setPrompt(getContext().getString(R.string.emptyaccountlabel));
		}
		else {
			dataSource2 = new SimpleCursorAdapter(getContext(),android.R.layout.simple_spinner_item,data,fields,new int[]{android.R.id.text1});
			dataSource2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			accountto.setAdapter(dataSource2);
			if(accountto.getCount()<=0)accountto.setPrompt(getContext().getString(R.string.emptyaccountlabel));
		}



	}


	public void setSaveclicked(OnSaveClicked saveclicked) {

		this.saveclicked = saveclicked;
	}



	public static interface OnSaveClicked {

		void onClicked(Boolean _result);
		void addquickoperation();
	}


}
