package tarekmahedy.app.smallaccountant;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class addaccountdialog extends Dialog {


	public addaccountdialog(Context context) {
		super(context);
		helper=new dbhelper(context);

	}


	Spinner accounttype;
	EditText accountnameedite;
	Button   savebtn;
	long accid;
	public int  editable=0;
   public  long lastinsertedid=-1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		int langdir=Integer.valueOf(getContext().getString(R.string.langdir));
		
		int resuid=R.layout.addaccountlayout;
		if(langdir==1) resuid=R.layout.addaccountlayoutar;
		
		setContentView(resuid);
		accounttype=(Spinner)findViewById(R.id.account1);
		filltyps();
		accountnameedite=(EditText)findViewById(R.id.editText1);
		savebtn=(Button)findViewById(R.id.button1);
		savebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				lastinsertedid=-1;
				String _text=accountnameedite.getText().toString();
				int _selectedtype=(int)accounttype.getSelectedItemId();
				if(_text.length()>1){
				if(editable==0){
					
					Boolean resul=helper.addaccount(_selectedtype,_text);
					lastinsertedid=helper.lastinsertedid;
					Headerbar.alert(getContext().getString(R.string.donkey), true);
					if(resul)cancel();
					
				}else {
					//update the account here 
					helper.updateaccount(accid,_selectedtype,_text);
					Headerbar.alert(getContext().getString(R.string.donkey), true);
					cancel();

				}
			}
				else Headerbar.alert(getContext().getString(R.string.requirfieldvali), true);

			}
		});
		
		

	}

	public void loaddata(long _id,int _parentid,String _accountname){
		editable=1;
		accountnameedite.setText(_accountname);
		accounttype.setSelection(_parentid);
		accid=_id;
	}

	public void setparent(int _parentid){

		
		accounttype.setSelection(_parentid);

	}


	private SQLiteDatabase database;
	private SimpleCursorAdapter dataSource;
	dbhelper helper;
	int lang=2;

	public void filltyps(){

		lang=Integer.valueOf(getContext().getString(R.string.langdir));
		database = helper.getWritableDatabase();
		String colu="namear";
		if(lang==2)colu="nameen";
		String fields[] = { colu };
		Cursor data = database.rawQuery("select id _id,"+colu+" from accountstypes ",null);

		dataSource = new SimpleCursorAdapter(getContext(),android.R.layout.simple_spinner_item,data,fields,new int[]{android.R.id.text1});
		dataSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		accounttype.setAdapter(dataSource);




	}

}
