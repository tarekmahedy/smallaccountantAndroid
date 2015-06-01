package tarekmahedy.app.smallaccountant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Manageoperations extends Screen {

	Spinner accountfrom;
	Spinner accountto;
	Button savebtn;
	Button quickoperatbtn;
	EditText amounteditetext;
    DatePicker operationdate;
	String titlear,titleen="";
    int nextrecord=0;
	int _fromtype=2;
	int _totype=1;
	int _dbtype=0;
	int _type=0;
	long recordid=0;
	int limitindex=1;
	int recordcount=1;
	Double amount=0.0;
	String toar, toen,fromar,fromen="";
	long toid,fromid=-1;
	private OnSaveClicked saveclicked=null;
    TextView datetext;
    ImageView goback;
    ImageView gonext;
    
    
	public Manageoperations(Context context) {
		super(context);
		int resuid=R.layout.operationsscreen;
		if(lang==1)resuid=R.layout.operationsscreen1;
		
		inflate(context,resuid,this);
			
		initui();
		
	}
	

	public void initui(){
		
		liner=(LinearLayout)findViewById(R.id.mainliner);
		accountfrom=(Spinner)findViewById(R.id.account1);
		accountfrom.setPrompt(getContext().getString(R.string.account1label));
		accountto=(Spinner)findViewById(R.id.account2);
		accountto.setPrompt(getContext().getString(R.string.account2label));
		amounteditetext=(EditText)findViewById(R.id.amount);
		datetext=(TextView)findViewById(R.id.datetext1);
		goback=(ImageView)findViewById(R.id.goback);
		goback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(limitindex>0)limitindex--;
				initscreen(limitindex);
				
			}
		});
		gonext=(ImageView)findViewById(R.id.gonext);
		gonext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(limitindex<recordcount)limitindex++;
				initscreen(limitindex);
				
			}
		});
		
		datetext.setText("2013/03/23");
		savebtn=(Button)findViewById(R.id.savebtn);
		savebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Boolean res=savetodb();
				if(res){
					if(saveclicked!=null){
						saveclicked.onClicked(res);
						Headerbar.alert(getContext().getString(R.string.donkey), true);
					}
				}
				
			}
		});

		
		quickoperatbtn=(Button)findViewById(R.id.quickoperation);
		quickoperatbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				delealerat();
				
			}
		});

		savebtn.setEnabled(false);
		quickoperatbtn.setEnabled(false);
		
		ImageView addnewfrom=(ImageView)findViewById(R.id.imageView1);
		addnewfrom.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					addnew(1);
				}
			});
		
		
		ImageView addnewto=(ImageView)findViewById(R.id.imageView2);
		addnewto.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					addnew(2);
				}
			});
		
		
		
		
	}

	
    int _spinner=1;
    addaccountdialog dialacc;
	public void addnew(int _spiner){
		
		_spinner=_spiner;
	    dialacc=new addaccountdialog(_context);
	    dialacc.show();
	    dialacc.setOnCancelListener(new OnCancelListener() {
		
		@Override
		public void onCancel(DialogInterface dialog) {
			
			if(dialacc.lastinsertedid>0){
				
			    fillaccount(_fromtype,0);
			accountfrom.setSelection(accountfrom.getCount()-1);
			fillaccount(_totype,1);
			accountto.setSelection(accountto.getCount()-1);
			
			}
			
		}
	});
		
		
		
	}

	public Boolean savetodb(){

		
		if(!amounteditetext.getText().toString().trim().equals("")){

			amount=Double.valueOf(amounteditetext.getText().toString());
			if(amount>0){
			
				fromid=accountfrom.getSelectedItemId();
				toid=accountto.getSelectedItemId();
			return helper.updateinputrecord(((int)recordid),amount);
			
			}else return false;

		}

		return false;

	}


	private boolean deloperation() {

		
	    helper.delinputrecord(((int)recordid));
	     recordid=0;
         toid=0;
		  fromid=0;
		  amount=0.0;
		 _type=0;
		  datetext.setText("");	
		  savebtn.setEnabled(false);
		  quickoperatbtn.setEnabled(false);
	     initscreen(nextrecord);
	     Headerbar.alert(getContext().getString(R.string.donkey), true);
	 
	return true;	
	} 


	public void initscreen(int _inputid){

		recordcount=helper.getinputrecordcount();
    	Cursor _data=helper.inputrecord(_inputid);
    	
		if(_data.moveToFirst()){
			
              recordid=_data.getLong(0);
              toid=_data.getInt(1);
  			  fromid=_data.getInt(2);
  			  amount=_data.getDouble(3);
  			 _type=_data.getInt(4);
  			  datetext.setText(_data.getString(5)+" ["+String.valueOf(recordid)+"] ");
  			savebtn.setEnabled(true);
  			quickoperatbtn.setEnabled(true);
			

		}

		fillaccount(_fromtype,0);
		fillaccount(_totype,1);

		amounteditetext.setText(amount.toString());
		
		if(fromid>0)
			accountfrom.setSelection(getidpost(accountfrom,fromid));
		if(toid>0)
			accountto.setSelection(getidpost(accountto, toid));

		
	}


	int getidpost(Spinner _spiiner,Long _id){

		int len=_spiiner.getCount();
		while(len>0){
			len--;
			long loopid=_spiiner.getItemIdAtPosition(len);
			if(loopid==_id)return len;

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
		Cursor data = database.rawQuery("select id _id,accountname from accounts  order by useorder desc ",null);

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

	
	
	
	public void delealerat(){
		
	  AlertDialog.Builder ab = new AlertDialog.Builder(_context);
	      ab.setMessage(_context.getString(R.string.confirmdelmsg)).setPositiveButton(_context.getString(R.string.delitem), dialogClickListener)
	    .setNegativeButton(_context.getString(R.string.cancellabel), dialogClickListener).show();

	
	}
	
	
	
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		
		
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	deloperation();
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //Do your No progress
	            break;
	        }
	    }
	};
	
	
	
	
	

	public void setSaveclicked(OnSaveClicked saveclicked) {

		this.saveclicked = saveclicked;
	}



	public static interface OnSaveClicked {

		void onClicked(Boolean _result);
        void addquickoperation();
	}




}
