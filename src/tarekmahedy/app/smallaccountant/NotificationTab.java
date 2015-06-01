package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NotificationTab  extends tabbaseclass{
	
	
	int currentview=0;
	addaccountdialog dialacc;
	LinearLayout mainoperations;
	
	@Override
	public void onBackPressed() {
		
	 super.onBackPressed();
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setparent();
		 
		 mainoperations=(LinearLayout)inflater.inflate(R.layout.contactslist, null);
		 
		// mainoperations.setLayoutParams(new LayoutParams(sreenwidth,LayoutParams.FILL_PARENT));
	  
		//setContentView(R.layout.contactslist);
		//SmallAccountActivity act=(SmallAccountActivity)getParent();
		//headerbar=new Headerbar(getBaseContext(),act);
		
		headerbar.apptitle.setText(getString(R.string.alarmtab));
		headerbar.headertitle=getString(R.string.alarmtab);
		//headerbar.optionicon.setImageResource(R.drawable.clock);
		listView = (ListView) mainoperations.findViewById(R.id.mainlistview1);
		fillinputlist();
		listView.setCacheColorHint(Color.TRANSPARENT);
		
		mainscreen.addView(mainoperations);

	}



	ListView listView;


	public void fillinputlist(){
    
		ArrayList<InputLlistCell> columes=new ArrayList<InputLlistCell>();
	    
		   InputLlistCell colum1=new InputLlistCell();
	         	colum1.id= 1;
	         	colum1.title=getString(R.string.invoices);
	         	colum1.iconid=getimglink(1);
				columes.add(colum1);
			
				InputLlistCell colum2=new InputLlistCell();
	         	colum2.id= 2;
	         	colum2.title=getString(R.string.aksat);
	         	colum2.iconid=getimglink(2);
				columes.add(colum2);
				
				InputLlistCell colum3=new InputLlistCell();
	         	colum3.id= 3;
	         	colum3.title=getString(R.string.association);
	         	colum3.iconid=getimglink(3);
				columes.add(colum3);
				
				InputLlistCell colum4=new InputLlistCell();
	         	colum4.id= 4;
	         	colum4.title=getString(R.string.backup);
	         	colum4.iconid=getimglink(4);
				columes.add(colum4);
				
		AccountsTypeAdptor apttypes=new AccountsTypeAdptor(getBaseContext(), columes,lang);
		listView.setAdapter(apttypes);

		
	}

	
	public int getimglink(int _id){
		
		switch (_id) {
		case 1:
			return R.drawable.invoicesicon;
		case 3:
			return R.drawable.associationicon;
		case 2:
			return R.drawable.aksaticon;
		case 4:
			return R.drawable.backupicon;
		default:
			return R.drawable.backupicon;
			
		}
		
			
	}
	

	
	
	
}
