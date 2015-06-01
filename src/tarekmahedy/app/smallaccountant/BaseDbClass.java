package tarekmahedy.app.smallaccountant;

import java.util.Date;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BaseDbClass {

	
	public long dbid;
	public Date regdate;
	SQLiteDatabase myDataBase; 
	dbhelper helper;
	Context _context;
	
	
	public BaseDbClass(Context _context) {
			
			helper = new dbhelper(_context);
			this._context=_context;
			
	 }
	
	
	
}
