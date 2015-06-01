package tarekmahedy.app.smallaccountant;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class dbhelper extends SQLiteOpenHelper {


	private static String DB_PATH = "/data/data/tarekmahedy.app.smallaccountant/databases/";
	private static String DB_name = "accountantdb.sqlite";
	private static String pre_DB_name = "accountantdb.sqlite";
	public String telnumber="";
	public String password="";
	private SQLiteDatabase myDataBase; 
	private final Context myContext;
	public long lastinsertedid=-1;
   


	public dbhelper(Context context) {

		super(context,DB_name, null,1);
		
		//DB_PATH=context.getDatabasePath(DB_name).getPath();
		//check for the previous data base 
		//if exist auto backup and restore 

		myContext=context;
		createdb();
		//myDataBase=getWritableDatabase();
		

	}


	@Override
	public void onCreate(SQLiteDatabase arg0) {


	}	


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}


	public void autobackup(){


		//this.backupdb();

		// this.postData("-1");


	}

	public void createdb(){

		boolean dbExist = checkDataBase();

		if(!dbExist){
			this.getReadableDatabase();

			try {
				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}


	}



	int colum=0;

	public static final String md5(final String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
			.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}


	public String backupdb(){

		int a=0;
		String _backup="";
		myDataBase=getWritableDatabase();
		Cursor data= myDataBase.rawQuery("SELECT name  FROM  sqlite_master WHERE type='table';",null);
		while(data.moveToNext()){
			String tablename=data.getString(0);
			if(!tablename.contains("sqlite_sequence"))
				_backup=_backup+" #### "+this.backuptable(tablename,a);


		}

		this.postData(_backup);

		return _backup;	

	}


	@SuppressWarnings("finally")
	public String backuptable(String _tablename,int a){
		String tablesql="";
		try {

           this.colum=0;
			String sqlinsert=this.createinsert(_tablename);

			String quer="SELECT  *  FROM  "+_tablename;
			if(_tablename.contains("input"))quer=quer+" where id>8 ;";
			else if(_tablename.contains("reports"))quer=quer+" where id>23 ;";
			else if(_tablename.contains("accountstypes"))quer=quer+" where id>5 ;";
			else quer=quer+" ;";
            Cursor data=myDataBase.rawQuery(quer,null);
			
			while(data.moveToNext()){
				int looper=0;
				int colcou=data.getColumnCount();
				String row="";
				while(looper<colcou){
					if(looper==0)
						row=row+" '"+data.getString(looper)+"'";
					else row=row+",'"+data.getString(looper)+"'";

					looper++;
				}

				tablesql=tablesql+" #### "+sqlinsert+row;
			}
         int al=0;

		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			return tablesql;


		}
	}


	public String createinsert(String _tablename){

		String sqlinsert="insert into "+_tablename+" (Id,";
		Cursor data= myDataBase.rawQuery("PRAGMA table_info("+_tablename+");",null);
		int a=0;
		while(data.moveToNext()){
			if(a==1)
				sqlinsert=sqlinsert+","+data.getString(1)+"";
			else {
				a=1;
				sqlinsert=sqlinsert+"client_id";	
			}
			this.colum++;
		}

		return sqlinsert+",user_id,versionid) values (NULL,";	

	}


	public void postData(String _data) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://www.tarekmahedy.com/wallet/index.php");

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

			if(_data!="-1"){
				nameValuePairs.add(new BasicNameValuePair("f", "addbackup"));
				nameValuePairs.add(new BasicNameValuePair("data", _data));
			}else 
				nameValuePairs.add(new BasicNameValuePair("f", "getbackup"));
			//  this.password=md5(this.password);


			nameValuePairs.add(new BasicNameValuePair("tel",this.telnumber));
			nameValuePairs.add(new BasicNameValuePair("pass", this.password));
			nameValuePairs.add(new BasicNameValuePair("appv",SmallAccountActivity.appversion));
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity httpEntity = response.getEntity();
			InputStream is = httpEntity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is,"UTF-8"), 8);
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			//    String a=sb.toString();
			Log.v("backup","dbstr"+ sb.toString());
			if(_data=="-1")
				this.restoredb(sb.toString());


		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	} 



	public void restoredb(String _data){

		try{
			if(_data.length()>30){
				myDataBase=getWritableDatabase();

				String[] querys = _data.split(" #### ");
				int looper=querys.length;
				this.empttydb();
				while(looper>0){
					looper--;
					String quer=querys[looper];
					if(quer.length()>20){
						myDataBase.execSQL(quer);

					}
				}
			}

		}catch (Exception e) {

		}
	}

	
	public String difquery=" #### CREATE TABLE invoices( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,"
    +"number VARCHAR NOT NULL UNIQUE,clientid INTEGER NOT NULL,type INTEGER DEFAULT 1,"
    +"discount FLOAT DEFAULT 0,datevalue NOT NULL DEFAULT CURRENT_DATE );"
    +" #### CREATE TABLE invoicitems(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,"
    +"invoiceid INTEGER NOT NULL,productid INTEGER NOT NULL,"
    +"quantity INTEGER NOT NULL DEFAULT 1,price FLOAT NOT NULL DEFAULT 1"
    +");  #### CREATE TABLE product(id INTEGER PRIMARY KEY NOT NULL,"
    +"title VARCHAR NOT NULL,barcode VARCHAR,quantity INTEGER DEFAULT (1),"
    +"price FLOAT DEFAULT (1),del INTEGER DEFAULT (0),useorder INTEGER DEFAULT (0)"
    +");  #### CREATE TABLE profile"
    +"(id INTEGER PRIMARY KEY NOT NULL,username VARCHAR,password VARCHAR,title VARCHAR,"
    +"identifier VARCHAR NOT NULL DEFAULT (0),note TEXT,devicetoken VARCHAR NOT NULL DEFAULT (0)"
    +");  #### DROP TABLE products;  #### DROP TABLE purchaseinvoice;DROP TABLE purchaseitems;DROP TABLE salesinvoice;DROP TABLE salesitems;";

	

	public void updatedb(String _data){

		try{
			if(_data.length()>30){
				myDataBase=getWritableDatabase();
                 String[] querys = _data.split(" #### ");
				int looper=querys.length;
				while(looper>0){
					looper--;
					String quer=querys[looper];
					if(quer.length()>20){
						myDataBase.execSQL(quer);

					}
				}
			}

		}catch (Exception e) {

		}
	}
	
	
	public void empttydb(){

		Cursor data= getWritableDatabase().rawQuery("SELECT name  FROM  sqlite_master WHERE type='table';",null);
		while(data.moveToNext()){
			String tablename=data.getString(0);  

			String quer="delete from "+tablename;
			if(tablename.contains("input"))quer=quer+" where id>8 ;";
			else if(tablename.contains("reports"))quer=quer+" where id>23 ;";
			else if(tablename.contains("accountstypes"))quer=quer+" where id>5 ;";
			else quer=quer+" ;";

			myDataBase.execSQL(quer);
		}
	}





	public void updatedatabase(){

		Cursor mCount= getWritableDatabase().rawQuery("select sql from sqlite_master where name='accounts' ;",null);
		String sql="";
		if(mCount.moveToFirst())
			sql= mCount.getString(0);

		if(!sql.contains("del")){
			myDataBase=getWritableDatabase();
			String query="ALTER TABLE 'accounts' ADD COLUMN 'del' INTEGER NOT NULL  DEFAULT 0";
			myDataBase.execSQL(query);

			myDataBase=getWritableDatabase();
			query="ALTER TABLE operations ADD COLUMN 'comments' TEXT";
			myDataBase.execSQL(query);

		}

	}


	public Boolean addaccount(int _typeid,String _title){

		myDataBase=getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("accountname",_title); 
		values.put("parentaccount",0);
		values.put("type",_typeid);
		values.put("openbalance",0);
		values.put("useorder",0);

		lastinsertedid=myDataBase.insert("accounts", null, values);

		return true;

	}


	public Boolean autoaccount(int _typeid,String _title){

		myDataBase=getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("accountname",_title); 
		values.put("parentaccount",0);
		values.put("type",_typeid);
		values.put("openbalance",0);
		values.put("useorder",0);
		values.put("del",1);
		lastinsertedid=myDataBase.insert("accounts", null, values);

		return true;

	}


	public Boolean updateaccount(long _id,int _typeid,String _title){

		myDataBase=getWritableDatabase();
		String query="update accounts set accountname='"+_title+"' , type="+String.valueOf(_typeid)+" where id="+String.valueOf(_id);
		myDataBase.execSQL(query);

		return true;

	}

	public int getaccountsid(String _title){

		int count=-1;
		Cursor mCount= getWritableDatabase().rawQuery("select id from accounts where accountname='"+_title+"'",null);
		if(mCount.moveToFirst())
			count= mCount.getInt(0);
		mCount.close();
		return count;

	}

	public long addinputoperation(long _toid,long _fromid,Double _value,int _type,String operationdates,String comment){

		myDataBase=getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("toid",_toid); 
		values.put("fromid",_fromid);
		values.put("value",_value);
		values.put("type",_type);
		values.put("operationdate", operationdates);
		values.put("comments", comment);
		return 	myDataBase.insert("operations", null, values);


	}

	public long updateinputoperation(long _toid,long _fromid,Double _value,int _type,String operationdates){

		myDataBase=getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("toid",_toid); 
		values.put("fromid",_fromid);
		values.put("value",_value);
		values.put("type",_type);
		values.put("operationdate", operationdates);
		return 	myDataBase.insert("operations", null, values);


	}


	public long addquickoperation(String _title,int _fromtype,int _totype,String _toar,String _toen,String _fromar,String _fromen,int _dbtype,int _type,long _toid,long _fromid,Double _value){

		myDataBase=getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("titlear",_title); 
		values.put("titleen",_title); 
		values.put("fromtype",_fromtype);
		values.put("totype",_totype); 
		values.put("toar",_toar); 
		values.put("toen",_toen);
		values.put("fromar",_fromar); 
		values.put("fromen",_fromen); 
		values.put("dbtype",_dbtype);
		values.put("type",_type);
		values.put("toid",_toid);
		values.put("fromid",_fromid);
		values.put("value",_value);
		values.put("useorder",0);

		return	myDataBase.insert("input", null, values);


	}


	public long addproduct(String proname,String probarcode,int  quantity,Double price){


		myDataBase=getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("title",proname); 
		values.put("barcode",probarcode);
		values.put("quantity",quantity);
		values.put("price",price);
		values.put("del",0);
		values.put("useorder",0);

		lastinsertedid=myDataBase.insert("product", null, values);

		return lastinsertedid;

	}


	public Boolean updateuseorder(int _id,String _tablename){
		myDataBase=getWritableDatabase();
		myDataBase.execSQL("update "+_tablename+" set useorder=(useorder+1) where id="+String.valueOf(_id));
		return true;

	}


	public Cursor inputrow(Long _id){

		return getWritableDatabase().rawQuery("select * from input where id="+String.valueOf(_id),null);	
	}


	public Cursor inputrecord(int _id){

		return getWritableDatabase().rawQuery("select * from operations order by id desc limit "+String.valueOf(_id)+",1",null);	
	}

	public int getinputrecordcount(){

		Cursor mCount= getWritableDatabase().rawQuery("select count(id) from operations",null);
		mCount.moveToFirst();
		int count= mCount.getInt(0);
		mCount.close();
		return count;
	}

	public Boolean updateinputrecord(int _id,Double _value){
		myDataBase=getWritableDatabase();
		myDataBase.execSQL("update operations set value="+String.valueOf(_value)+" where id="+String.valueOf(_id));
		return true;

	}


	public Boolean delinputrecord(int _id){
		myDataBase=getWritableDatabase();
		myDataBase.execSQL("delete from operations  where  id="+String.valueOf(_id));
		return true;

	}

	private boolean checkDataBase(){

		SQLiteDatabase checkDB = null;

		try{
			String myPath = DB_PATH +DB_name;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);

		}catch(SQLiteException e){

			//database does't exist yet.

		}

		if(checkDB != null){

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	public void copyDataBase() throws IOException{

		//Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_name);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_name;

		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}


	public void openDataBase() throws SQLException{

		//Open the database
		String myPath = DB_PATH + DB_name;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

	}

	@Override
	public synchronized void close() {

		if(myDataBase != null)
			myDataBase.close();

		super.close();

	}








}
