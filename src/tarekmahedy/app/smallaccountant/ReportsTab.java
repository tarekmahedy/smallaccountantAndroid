package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;

import tarekmahedy.app.smallaccountant.R;
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

public class ReportsTab extends tabbaseclass {


	
	@Override
	public void onBackPressed() {
		if(inputview==1){
			headerbar.apptitle.setText(headerbar.headertitle);
			inputview=0;
			fillinputlist(0);
		}
		else if(inputview==2){
			inputview=1;
			headerbar.apptitle.setText(mainreporttitle);
			reint();

		}
		else  super.onBackPressed();

	}

	public void reint(){


		listlayout.removeViewAt(0);
		listlayout.addView(listView);

	}


	ImageView optionicon;
	Manageoperations manageoperations;
	LinearLayout mainoperations;
	String hometitle="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setparent();
	
		headerbar.apptitle.setText(getString(R.string.tab3));
		headerbar.headertitle=getString(R.string.tab3);
		
		inputview=0;

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
				mainswitcher.snapToScreen(0);
	

			}
		});

		
		 ArrayList<LinearLayout> screens=new ArrayList<LinearLayout>();
	     screens.add(0,mainoperations);
	     setcontentslider(screens);
	
		LinearLayout lat=new LinearLayout(getBaseContext());
		lat.setLayoutParams(new LayoutParams(sreenwidth,LayoutParams.FILL_PARENT));
		
		manageoperations=new Manageoperations(this);
		
		
		
		mainoperations.setBackgroundColor(Color.WHITE);
		viewer=new Reportviewer(this);
		listView = (ListView) mainoperations.findViewById(R.id.mainlistview1);
		listlayout=(LinearLayout)mainoperations.findViewById(R.id.listlayout);

		fillinputlist(0);

		listView.setCacheColorHint(Color.TRANSPARENT);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				InputLlistCell cell=(InputLlistCell)arg1.getTag();
				headerbar.apptitle.setText(cell.title);

				if(inputview==0){
					inputview=1;
					fillinputlist(cell.id);
					mainreporttitle=cell.title;
				}
				else {
					viewer.initui(cell);
					inputview=2;
					listlayout.removeViewAt(0);
					listlayout.addView(viewer,0);
				}
			}
		});

		 

	}



	LinearLayout listlayout;
	ListView listView;
	private SQLiteDatabase database;
	Reportviewer viewer;
	int inputview=0;
	String mainreporttitle="";

	public void fillinputlist(long _parentid){
		int lang=0;
		String langu=getString(R.string.langdir);
		lang=Integer.valueOf(langu);

		dbhelper helper = new dbhelper(this);
		database = helper.getWritableDatabase();

		String reporttitle="titleen";
		if(lang==1)reporttitle="titlear";

		ArrayList<InputLlistCell> columes=new ArrayList<InputLlistCell>();

		Cursor data = database.rawQuery("select id _id,"+reporttitle+",accid,type,detview from reports where parentid="+String.valueOf(_parentid)+" order by useorder desc ",null);
		while(data.moveToNext()){
			InputLlistCell colum1=new InputLlistCell();
			colum1.id= data.getLong(0);
			colum1.title=data.getString(1);
			colum1.iconid=R.drawable.folder;
			colum1.parentid=(int)_parentid;
			colum1.accid= data.getInt(2);
			colum1.type= data.getInt(3);
			colum1.detview= data.getInt(4);
			columes.add(colum1);
		}

		data = database.rawQuery("select id _id,accountname from accounts where type="+String.valueOf(_parentid)+" order by useorder desc ",null);
		while(data.moveToNext()){
			InputLlistCell colum1=new InputLlistCell();
			colum1.id= data.getLong(0);
			colum1.title=data.getString(1);
			colum1.iconid=R.drawable.folder;
			colum1.parentid=(int)_parentid;
			colum1.accid= (int)data.getLong(0);
			colum1.type= (int)_parentid;
			colum1.detview=0;
			columes.add(colum1);
		}

		Reportlistadptor replistadpt=new Reportlistadptor(getBaseContext(), columes, lang);
		listView.setAdapter(replistadpt);


	}



	public void filloptionlist(ListView _list){

		ArrayList<String[]> optionlist = new ArrayList<String[]>();
		int homeico=R.drawable.put_out_icon;
		String[]item=new String[]{getString(R.string.tab3),String.valueOf(homeico)};
		homeico=R.drawable.chartsicon;
		String[]item5=new String[]{getString(R.string.chartsoption),String.valueOf(homeico)};
		homeico=R.drawable.gueryicon;
		String[]item6=new String[]{getString(R.string.queryoption),String.valueOf(homeico)};

		optionlist.add(item);
		optionlist.add(item5);
		optionlist.add(item6);

		Optionmenuadptor arradpt=new Optionmenuadptor(getBaseContext(),optionlist,lang,1);
		_list.setAdapter(arradpt); 

	}




}
