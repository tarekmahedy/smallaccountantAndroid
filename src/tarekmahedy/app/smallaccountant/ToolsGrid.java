package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;
import java.util.List;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

public class ToolsGrid extends BaseDialog{

	public ToolsGrid(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initui();
	}

	LinearLayout listlayout;
	GridView listView;
	LinearLayout mainoperations;


	public void  initui() {

		setContentView(R.layout.gridviewlist);
		listView = (GridView)findViewById(R.id.gridView1);

		fillinputlist(lang);
		listView.setCacheColorHint(Color.TRANSPARENT);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

				switch (arg2) {
				case 0:
					viewcalc();
					break;
				case 3:
					viewnearstbanks("«ﬁ—» „«ﬂÌ‰… ’—«›");
					break;
				case 6:
					viewnearstbanks("«ﬁ—» »‰ﬂ");
					break;


				}


			}
		});


	}


	public void viewnearstbanks(String _quer){
		try {

		Uri location = Uri.parse("geo:0,0?q="+_quer);
		//// Or map point based on latitude/longitude
		//// Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

		getContext().startActivity(mapIntent);

		} catch (ActivityNotFoundException noSuchActivity) {

		}

	}



	public void viewcalc(){
		try {

			
			String packageName="";
			PackageManager pm = getContext().getPackageManager();
			List<PackageInfo> packs = pm.getInstalledPackages(0);  
			for (PackageInfo pi : packs) {
				if( pi.packageName.toString().toLowerCase().contains("calcul")){
					Object appName= pi.applicationInfo.loadLabel(pm);
					packageName =pi.packageName;
					break;
				}
			}

			Intent calculintent = pm.getLaunchIntentForPackage(packageName);
			if (calculintent != null)


				getContext().startActivity(calculintent);

		} catch (ActivityNotFoundException noSuchActivity) {

		}

	} 


	public void fillinputlist(int _lang){

		database = helper.getWritableDatabase();
		ArrayList<InputLlistCell> columes=new ArrayList<InputLlistCell>();


		int alink=0;
		while(alink<8){
			InputLlistCell colum1=new InputLlistCell();
			colum1.id= alink;
			colum1=getimglink(alink,colum1);

			columes.add(colum1);
			alink++;

		}

		Grideaptor adpt=new Grideaptor(context, columes,lang);
		listView.setAdapter(adpt);

	}




	public InputLlistCell getimglink(int _id,InputLlistCell colum1){

		switch (_id) {
		case 0:
			colum1.title="«·Õ«”»…";
			colum1.iconid= R.drawable.calculatoricon;
			return colum1;
		case 1:
			colum1.title="Õ«”»… «·“ﬂ«…";
			colum1.iconid= R.drawable.elzakah;
			return colum1;

		case 2:
			colum1.title="«·«Œ»«— «·«ﬁ ’«œÌ…";
			colum1.iconid= R.drawable.newsecon;
			return colum1;

		case 3:
			colum1.title="„ﬂÌ‰«  «·’—«›";
			colum1.iconid=  R.drawable.atm;
			return colum1;


		case 4:
			colum1.title="„ÕÊ· «·⁄„·« ";
			colum1.iconid= R.drawable.converter;
			return colum1;
		case 5:
			colum1.title="«· ÊﬁÌ⁄ «··ﬂ —Ê‰Ï";
			colum1.iconid= R.drawable.digisign;
			return colum1;
		case 6:
			colum1.title="«·»‰Êﬂ";
			colum1.iconid= R.drawable.bank;
			return colum1;

		case 7:
			colum1.title="ﬂ·„«  «·„—Ê—";
			colum1.iconid= R.drawable.keys;
			return colum1;

		case 8:
			colum1.title="Õ«”»… «·“ﬂ«…";
			colum1.iconid= R.drawable.atm;
			return colum1;



		}

		return null;
	}
}
