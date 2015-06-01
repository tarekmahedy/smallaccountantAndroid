package tarekmahedy.app.smallaccountant;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class AddProduct extends Dialog {

	
	EditText accountnameedite;
	Button   savebtn;
	long accid;
	public int  editable=0;
	dbhelper helper;
	int lang=2;
	public  long lastinsertedid=-1;
	
	
	public AddProduct(Context context) {
		super(context);
		helper=new dbhelper(context);

	}
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		int langdir=Integer.valueOf(getContext().getString(R.string.langdir));
		
		int resuid=R.layout.addproductlayout;
		if(langdir==1) resuid=R.layout.addproductlayoutar;
		
		setContentView(resuid);
		accountnameedite=(EditText)findViewById(R.id.editText1);
		savebtn=(Button)findViewById(R.id.button1);
		savebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				lastinsertedid=-1;
				String _text=accountnameedite.getText().toString();
				if(editable==0){
					Boolean resul=helper.addaccount(0,_text);
					lastinsertedid=helper.lastinsertedid;
					if(resul)cancel();
				}else {
					//update the account here 
					
					helper.updateaccount(accid,0,_text);
					cancel();

				}

			}
		});
		
		

	}

	public void loaddata(long _id,int _parentid,String _accountname){
		editable=1;
		accountnameedite.setText(_accountname);
		accid=_id;
	}

	
	
}
