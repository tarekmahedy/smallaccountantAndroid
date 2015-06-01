package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;
import tarekmahedy.app.smallaccountant.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AccountsTab extends tabbaseclass {


	

	@Override
	public void onBackPressed() {

		if(inputview!=0&&currentviewoption==0){
			
			headerbar.apptitle.setText(headerbar.headertitle);
			inputview=0;
			fillinputlist();
			headerbar.shownotify("",false);

		}
		else  super.onBackPressed();

	}


	

	public void editeaccountdialogue(InputLlistCell cell){
		
		lasteditparentid=cell.parentid;
		dialacc=new addaccountdialog(this);
		dialacc.show();
		dialacc.setparent(cell.parentid-1);
		dialacc.loaddata(cell.id,(cell.parentid-1),cell.title);

		dialacc.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				fillaccountlist(lasteditparentid);
			}
		});


	}
	
	
	int currentview=0;
	addaccountdialog dialacc;
	public int lasteditparentid;
	LinearLayout listlayout;
	ImageView optionicon;
	
	AddInvoicebase mngsellinvoice;
	AddInvoicebase mngpurchasinvoice;
	AddInvoicebase mngproinvoice;
	
	Manageoperations manageoperations;
	UIBackup restorescreen;
	LinearLayout mainoperations;
	String hometitle="";
	int inputview=0;
    int currentviewoption=0;

    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setparent();

		
		headerbar.apptitle.setText(getString(R.string.tab2));
		headerbar.headertitle=getString(R.string.tab2);

		inputview=0;
		currentviewoption=0;
		mainoperations=(LinearLayout)inflater.inflate(R.layout.contactslist, null);
		mainoperations.setLayoutParams(new LayoutParams(sreenwidth,LayoutParams.FILL_PARENT));
  
		filloptionlist(optionslist);
		optionslist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				setcolorbg(arg1);
				
				mSlideHolder.toggle();
				
				String[]_val=(String[])arg1.getTag();
				headerbar.apptitle.setText(_val[0]);
				
			    mainswitcher.snapToScreen(arg2);
				
		    }
			
		});

        setscreens();
        
        ArrayList<LinearLayout> screens=new ArrayList<LinearLayout>();
        screens.add(0,mainoperations);
        screens.add(1,manageoperations);
        screens.add(2,mngsellinvoice);
        screens.add(3,mngproinvoice);
        screens.add(4,mngpurchasinvoice);
        screens.add(5,restorescreen);
        setcontentslider(screens);
           
		listView = (ListView) mainoperations.findViewById(R.id.mainlistview1);
		listlayout=(LinearLayout)mainoperations.findViewById(R.id.listlayout);
		fillinputlist();
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(inputview==0){
					inputview=1;
					InputLlistCell colum1=(InputLlistCell)arg1.getTag();
					fillaccountlist((int)colum1.id);
					currentview=1;
				}

			}
		}); 

		
		
		
		
		
	}




public void setscreens(){
		
		mainoperations.setBackgroundColor(Color.WHITE);
		
		restorescreen=new UIBackup(this);
		restorescreen.type=1;
		manageoperations=new Manageoperations(this);
		manageoperations.setBackgroundColor(Color.WHITE);
		restorescreen.setBackgroundColor(Color.WHITE);
		restorescreen.initscreen();
		mainoperations.setBackgroundColor(Color.WHITE);
		mngsellinvoice=new AddInvoicebase(this);
		mngsellinvoice.setBackgroundColor(Color.WHITE);
		mngpurchasinvoice=new AddInvoicebase(this);
		mngpurchasinvoice.setBackgroundColor(Color.WHITE);
		mngproinvoice=new AddInvoicebase(this);
		mngproinvoice.setBackgroundColor(Color.WHITE);
		
	}
	
	
	public void showscreen(Screen _screen){

	       _screen.setVisibility(View.VISIBLE);
			((LinearLayout)headerbar.getParent()).removeView(headerbar);
			_screen.liner.addView(headerbar,0);
			_screen.bringToFront();
			
	}
	
	
	public void showmainscreen(int _cuurentview){
		
			 mainoperations.setVisibility(View.VISIBLE);
			 ((LinearLayout)headerbar.getParent()).removeView(headerbar);
			 mainoperations.addView(headerbar,0);
			 mainoperations.bringToFront();
		    
	}
	
	

	ListView listView;
	private SQLiteDatabase database;


	public void fillinputlist(){

		dbhelper helper = new dbhelper(this);
		database = helper.getWritableDatabase();
		String acoountname="nameen";
		if(lang==1)acoountname="namear";


		ArrayList<InputLlistCell> columes=new ArrayList<InputLlistCell>();

		Cursor data = database.rawQuery("select id _id,"+acoountname+" from accountstypes  order by vieworder desc ",null);

		while(data.moveToNext()){
			InputLlistCell colum1=new InputLlistCell();
			colum1.id= data.getLong(0);
			colum1.title=data.getString(1);
			colum1.iconid=getimglink((int) data.getLong(0));
			columes.add(colum1);
		}


		AccountsTypeAdptor apttypes=new AccountsTypeAdptor(getBaseContext(), columes,lang);
		listView.setAdapter(apttypes);

	}


	public void fillaccountlist(int _inpttypeid){

		dbhelper helper = new dbhelper(this);
		database = helper.getWritableDatabase();

		String acoountname="accountname";
		if(lang==1)acoountname="accountname";

		ArrayList<InputLlistCell> columes=new ArrayList<InputLlistCell>();
		Cursor data = database.rawQuery("select id _id,"+acoountname+",type,del from accounts where type="+String.valueOf(_inpttypeid)+" order by useorder desc ",null);

		while(data.moveToNext()){
			InputLlistCell colum1=new InputLlistCell();
			colum1.id= data.getLong(0);
			colum1.title=data.getString(1);
			colum1.iconid=R.drawable.folder;
			colum1.parentid=_inpttypeid;
			colum1.del=data.getInt(3);
			columes.add(colum1);
		}

		AccountListadptor accadpt=new AccountListadptor(getBaseContext(), columes, lang,this);
		listView.setAdapter(accadpt);
		registerForContextMenu(listView);


	}


	public void filloptionlist(ListView _list){

		
		ArrayList<String[]> optionlist = new ArrayList<String[]>();
		int homeico=R.drawable.services_icon;
		String[]item=new String[]{getString(R.string.headeraccountscontext),String.valueOf(homeico)};
		homeico=R.drawable.operationmng;
		String[]item1=new String[]{getString(R.string.opeartionmngertitle),String.valueOf(homeico)};
	    homeico=R.drawable.purchaseicon;
		String[]item5=new String[]{getString(R.string.mngpurchasoption),String.valueOf(homeico)};
		homeico=R.drawable.sellicon;
		String[]item6=new String[]{getString(R.string.mngsell),String.valueOf(homeico)};
		homeico=R.drawable.producticon;
		String[]item3=new String[]{getString(R.string.mngproducts),String.valueOf(homeico)};
		homeico=R.drawable.backupicon;
		String[]item4=new String[]{getString(R.string.restoretitle),String.valueOf(homeico)};
		homeico=R.drawable.coin;
		String[]item2=new String[]{getString(R.string.mngcoins),String.valueOf(homeico)};

		optionlist.add(item);
		optionlist.add(item1);
		optionlist.add(item5);
		optionlist.add(item6);
		optionlist.add(item3);
		//optionlist.add(item2);
		optionlist.add(item4);
		Optionmenuadptor arradpt=new Optionmenuadptor(getBaseContext(),optionlist,lang,1);
		_list.setAdapter(arradpt); 

		
		
	}





	public int getimglink(int _id){

		
		switch (_id) {
		case 2:
			return R.drawable.expences;
		case 1:
			return R.drawable.income;
		case 3:
			return R.drawable.asset;
		case 4:
			return R.drawable.payment_icon;
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

	int deletedid;
	int parentdid;

	public void delealerat(int _delid,int _parentid){

		deletedid=_delid;
		parentdid=_parentid;

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
				SQLiteDatabase database = helper.getWritableDatabase();
				database.execSQL("delete from accounts where id="+String.valueOf(deletedid));
				fillaccountlist(parentdid);
				Headerbar.alert(getString(R.string.donkey), true);
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				//Do your No progress
				break;
			}
		}
	};


}
