package tarekmahedy.app.smallaccountant;

import org.apache.http.client.ClientProtocolException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;



public class UserData implements Connectorlistener {

	
	public long dbid;
	public int userid;
	public String username=null;
	public String password=null;
	public String devicetoken=null;
	Context _context;
    dbhelper helper;
    SQLiteDatabase myDataBase;
    
    public  void intuser(Context _context) {
		// TODO Auto-generated method stub
    	this._context=_context;
    	
    	helper = new dbhelper(_context);
    	this.dbid=-1;
    	this.load();
    	
	}
    
    
	public void load(){
		
		     myDataBase=helper.getWritableDatabase();
			Cursor mCount= myDataBase.rawQuery("select id,identifier,devicetoken,username,password from profile limit 1 ",null);
			
			if(mCount.moveToFirst()){
				this.dbid=mCount.getLong(0);
				this.userid=mCount.getInt(1);
				this.devicetoken=mCount.getString(2);
				this.username=mCount.getString(3);
				this.password=mCount.getString(4);
			
			}
			
			 mCount.close();
		     if(this.dbid==-1)register();
			 
		
	}
	
	
	public void savedata(){
		
		
		    myDataBase=helper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put("username",this.username);
			values.put("password",this.password);
			values.put("identifier",this.userid); 
			values.put("devicetoken",this.devicetoken);
			
			
			this.dbid=myDataBase.insert("profile", null, values);
		
			
	}
	
	
	public void register(){
		
		Connector connect=new Connector();
		connect.setListener(this);
		this.devicetoken=((TelephonyManager)_context.getSystemService("phone")).getDeviceId();
		this.username=android.provider.Settings.Secure.getString(_context.getContentResolver(), "android_id");
		
		connect.postData("f=addregisterdevice&devtoken="+this.devicetoken+"&android=1&devpass="+this.username);
		
	}


	@Override
	public void Onincomingdata(String _data, int _type) {


		if(this.devicetoken==null)this.devicetoken="notset";
		
		this.userid=Integer.valueOf(_data.trim());
		this.savedata();
		
		
	}


	@Override
	public void Onerror(ClientProtocolException e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
