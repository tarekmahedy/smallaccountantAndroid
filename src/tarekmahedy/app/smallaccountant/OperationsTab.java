package tarekmahedy.app.smallaccountant;


import java.util.ArrayList;

import com.dm.zbar.android.scanner.ZBarConstants;

import tarekmahedy.app.smallaccountant.R;
import tarekmahedy.app.smallaccountant.inputactivity.OnSaveClicked;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OperationsTab extends tabbaseclass {

	inputactivity inputscreen;
	Aboutdialog aboudial;
	int inputview=0;
	int currentview=0;
	int activescreen=0;
	Context _context;

	@Override
	public void onBackPressed() {

		if(inputview!=0&&currentview==0){
			headerbar.apptitle.setText(headerbar.headertitle);
			reint();
			headerbar.shownotify("",false);
			_viewslist=null;
			_msgs=null;
		}
		else  super.onBackPressed();

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{    

		int a=0;
		if (resultCode == RESULT_OK) 
		{
			//			 MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.combo);
			//			 mp.start();
			if(requestCode==16){
				String resultData=data.getDataString();

				Toast.makeText(getApplicationContext(),"data"+ resultData, Toast.LENGTH_LONG).show();


			}
			else{
				String resultData=data.getStringExtra(ZBarConstants.SCAN_RESULT);

				if(requestCode==3)
					addproduct.barcode.setText(resultData);


				else {

					if(activescreen==1)
						purchasinvoice.getproductbybarcode(resultData);
					else if(activescreen==2)
						sellinvoice.getproductbybarcode(resultData);

				}

			} }

	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
     
		setparent();
		initui();
		if(lang==2)optionviewed=0;

		AppRater.app_launched(this);
	}


	public void reint(){

		inputview=0;
		listlayout.removeViewAt(0);
		listlayout.addView(listView);

	}

	LinearLayout listlayout;
	ImageView optionicon;
	LinearLayout mainoperations;

	UIBackup backupscreen;
	AddInvoicebase sellinvoice;
	AddInvoicebase purchasinvoice;
	AccountsScreen addaccounts;
	ProductsScreen addproduct;
	UIDigitalcurrancy digitalcuuancyscreen;

	String hometitle="";



	public void  initui() {


		headerbar.headertitle=headerbar.apptitle.getText().toString();

		inputview=0;

		mainoperations=(LinearLayout)inflater.inflate(R.layout.contactslist, null);
		mainoperations.setLayoutParams(new LayoutParams(sreenwidth,LayoutParams.FILL_PARENT));


		filloptionlist(optionslist);
		_context=this;
		optionslist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				setcolorbg(arg1);
				mSlideHolder.toggle();
				String[]_val=(String[])arg1.getTag();
				headerbar.apptitle.setText(_val[0]);
				activescreen=arg2;
				mainswitcher.snapToScreen(arg2);
				if(arg2==0){
					if(inputscreen._viewslist!=null){
					_viewslist=inputscreen._viewslist;
					_msgs=inputscreen._msgs;
					}

				}
				else {

					_viewslist=null;
					_msgs=null;

				}			
			}
		});


		setscreens();

		ArrayList<LinearLayout> screens=new ArrayList<LinearLayout>();
		screens.add(0,mainoperations);
		screens.add(1,purchasinvoice);
		screens.add(2,sellinvoice);
		screens.add(3,addaccounts);
		screens.add(4,addproduct);
		screens.add(5,digitalcuuancyscreen);
		screens.add(6,backupscreen);
		setcontentslider(screens);


		listView = (ListView) mainoperations.findViewById(R.id.mainlistview1);
		listlayout=(LinearLayout)mainoperations.findViewById(R.id.listlayout);
		fillinputlist(lang);

		inputscreen=new inputactivity(this);
		listView.setCacheColorHint(Color.TRANSPARENT);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				TextView titletext=(TextView)arg1.findViewById(R.id.username);
				headerbar.apptitle.setText(titletext.getText());
				InputLlistCell cell=(InputLlistCell)arg1.getTag();

				inputscreen.initscreen(cell.id);
				if(inputscreen._viewslist!=null){
					_viewslist=inputscreen._viewslist;
					_msgs=inputscreen._msgs;
					}
				inputscreen.setSaveclicked(new OnSaveClicked() {

					@Override
					public void onClicked(Boolean _result) {
						saveinput(_result);

					}
					@Override
					public void addquickoperation() {

						fillinputlist(lang);
					}
				});
				inputview=1;
				listlayout.removeViewAt(0);
				listlayout.addView(inputscreen,0);
			}
		});

		mainswitcher.snapToScreen(0);
		
		
		
	}




	public void setscreens(){

		mainoperations.setBackgroundColor(Color.WHITE);

		backupscreen=new UIBackup(this);
		backupscreen.type=0;
		backupscreen.initscreen();
		backupscreen.setBackgroundColor(Color.WHITE);

		sellinvoice=new AddInvoicebase(this);
		sellinvoice.screenindex=4;
		sellinvoice.setBackgroundColor(Color.WHITE);
		sellinvoice.parentscreen=this;

		purchasinvoice=new AddInvoicebase(this);
		purchasinvoice.screenindex=5;
		purchasinvoice.setBackgroundColor(Color.WHITE);
		purchasinvoice.parentscreen=this;
		addaccounts=new AccountsScreen(this);
		addaccounts.setBackgroundColor(Color.WHITE);

		addproduct=new ProductsScreen(this);
		addproduct._mainactivy=this;
		addproduct.setBackgroundColor(Color.WHITE);

		digitalcuuancyscreen=new UIDigitalcurrancy(this);
		digitalcuuancyscreen.setBackgroundColor(Color.WHITE);

	}


	public void showscreen(Screen _screen){

		_screen.setVisibility(View.VISIBLE);
		//((LinearLayout)headerbar.getParent()).removeView(headerbar);
		//_screen.liner.addView(headerbar,0);
		_screen.bringToFront();

	}



	public void showmainscreen(int _cuurentview){

		mainoperations.setVisibility(View.VISIBLE);
		//((LinearLayout)headerbar.getParent()).removeView(headerbar);
		//mainoperations.addView(headerbar,0);
		mainoperations.bringToFront();
		backupscreen.setVisibility(View.INVISIBLE);

	}



	public void saveinput(Boolean _res){

		headerbar.apptitle.setText(headerbar.headertitle);
		reint();
		if(_res){
			headerbar.shownotify(getBaseContext().getString(R.string.newoperasavedmsg),true);
		}

	}



	ListView listView;
	private SQLiteDatabase database;


	public void fillinputlist(int _lang){

		dbhelper helper = new dbhelper(this);
		database = helper.getWritableDatabase();
		ArrayList<InputLlistCell> columes=new ArrayList<InputLlistCell>();

		String colu="titlear";
		if(lang==2)colu="titleen";

		Cursor data = database.rawQuery("select id _id,"+colu+" from input order by useorder desc",null);

		while(data.moveToNext()){
			InputLlistCell colum1=new InputLlistCell();
			colum1.id= data.getLong(0);
			colum1.title=data.getString(1);
			colum1.iconid=getimglink((int) data.getLong(0));
			columes.add(colum1);
		}

		listadpt adpt=new listadpt(this, columes,this,lang);
		listView.setAdapter(adpt);

	}





	public int getimglink(int _id){

		switch (_id) {
		case 1:
			return R.drawable.expences;
		case 2:
			return R.drawable.income;
		case 3:
			return R.drawable.asset;
		case 4:
			return R.drawable.asset;
		case 5:
			return R.drawable.personal_icon;
		case 6:
			return R.drawable.personal_icon;
		case 7:
			return R.drawable.personal_icon;
		case 8:
			return R.drawable.personal_icon;
		default:
			return R.drawable.payment_icon;

		}


	}



	public void filloptionlist(ListView _list){


		ArrayList<String[]> optionlist = new ArrayList<String[]>();
		int homeico=R.drawable.homeicon;
		String[]item=new String[]{getString(R.string.addopertaionoption),String.valueOf(homeico)};

		homeico=R.drawable.purchaseicon;
		String[]item5=new String[]{getString(R.string.addpurchasoption),String.valueOf(homeico)};

		homeico=R.drawable.sellicon;
		String[]item6=new String[]{getString(R.string.sell),String.valueOf(homeico)};

		homeico=R.drawable.services_icon;
		String[]item1=new String[]{getString(R.string.addaccountoption),String.valueOf(homeico)};

		homeico=R.drawable.producticon;
		String[]item3=new String[]{getString(R.string.products),String.valueOf(homeico)};

		homeico=R.drawable.backupicon;
		String[]item4=new String[]{getString(R.string.backup),String.valueOf(homeico)};

		homeico=R.drawable.coin;
		String[]item2=new String[]{getString(R.string.digitalcoin),String.valueOf(homeico)};


		optionlist.add(item);
		optionlist.add(item5);
		optionlist.add(item6);
		optionlist.add(item1);
		optionlist.add(item3);
		optionlist.add(item2);
		optionlist.add(item4);

		Optionmenuadptor arradpt=new Optionmenuadptor(getBaseContext(),optionlist,lang,1);
		_list.setAdapter(arradpt); 


	}


	int deletedid=0;	
	public void delealerat(int _delid){

		deletedid=_delid;

		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setMessage(getString(R.string.confirmdelmsg)).setPositiveButton(getString(R.string.delitem), dialogClickListener)
		.setNegativeButton(getString(R.string.cancellabel), dialogClickListener).show();


	}



	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {


		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which){
			case DialogInterface.BUTTON_POSITIVE:
				dbhelper helper = new dbhelper(getBaseContext());
				helper.getWritableDatabase().execSQL("delete from input where id="+String.valueOf(deletedid)+" ;");
				fillinputlist(lang);
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				//Do your No progress
				break;
			}
		}
	};








}
