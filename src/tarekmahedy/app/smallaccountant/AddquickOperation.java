package tarekmahedy.app.smallaccountant;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddquickOperation extends BaseDialog{


	int fromtype=2;
	int totype=1;
	int dbtype=0;
	int type=0;
	Double value;
	String title="";
	EditText opeartineditetext;
	Button savebtn;
	String toar, toen,fromar,fromen="";
	long toid,fromid=0;
    Boolean savedok=false;
	private OnSaveClicked saveclicked=null;
	
	public AddquickOperation(Context context) {
		super(context);
		
		setContentView(R.layout.addquicklayout);
		opeartineditetext=(EditText)findViewById(R.id.operationsname);
       int langdir=Integer.valueOf(getContext().getString(R.string.langdir));
       TextView textv=(TextView)findViewById(R.id.textView3);
		if(langdir==1) textv.setGravity(Gravity.RIGHT);
		
		savebtn=(Button)findViewById(R.id.savebtn);

		savebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				title=opeartineditetext.getText().toString();
				if(!title.equals("")){
					long insertedid=helper.addquickoperation(title, fromtype, totype, toar, toen, fromar, fromen, dbtype, type, toid, fromid, value);	
					if(insertedid>0){
						savedok=true;
						if(saveclicked!=null){
							saveclicked.onClicked(savedok);
							Headerbar.alert(getContext().getString(R.string.donkey), true);
						}
						cancel();
					}else savedok=false;

				}else{
					Headerbar.alert("·„ Ì „ ﬂ «»… «”„ «·⁄„·Ì…", true);
					savedok=false;
				}

			}
		});


	}


	public void initvalues(int _fromtype,int _totype,int _dbtype,int _type,Double _amount,String _toar,String _toen,String _fromar,String _fromen,long _toid,long _fromid){

		savedok=false;
		fromtype=_fromtype;
		totype=_totype;
		dbtype=_dbtype;
		type=_type;
		value=_amount;
		toar=_toar;
		toen=_toen;
		fromar=_fromar;
		fromen=_fromen;
		toid=_toid;
		fromid=_fromid;


	}




	public void setSaveclicked(OnSaveClicked saveclicked) {

		this.saveclicked = saveclicked;
	}



	public static interface OnSaveClicked {

		void onClicked(Boolean _result);
       
	}



}
