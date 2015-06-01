package tarekmahedy.app.smallaccountant;

import android.content.Context;
import android.widget.LinearLayout;

public class UIDigitalcurrancy extends Screen {

	public UIDigitalcurrancy(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		int resuid=R.layout.login;
		inflate(context,resuid,this);
		liner=(LinearLayout)findViewById(R.id.mainliner);
		setlayout();
	}
	
	

}
