package tarekmahedy.app.smallaccountant;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.Window;
import android.view.WindowManager;

public class BaseDialog  extends Dialog{

	
	
	int lang;
	Context context;
    SQLiteDatabase database;
    dbhelper helper;
	
	public BaseDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		helper=new dbhelper(context);
		this.context=context;
		String langu=context.getString(R.string.langdir);
		lang=Integer.valueOf(langu);
		
		
	}
	
	
	
}
