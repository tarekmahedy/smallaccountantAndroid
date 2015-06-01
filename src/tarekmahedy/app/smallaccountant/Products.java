package tarekmahedy.app.smallaccountant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Products extends BaseDbClass{

	
	public Products(Context _context) {
		super(_context);
		// TODO Auto-generated constructor stub
		this.useorder=1;
		this.def_quantity=1;
	}
	
	public String name;
	public int useorder;
	public float defaultprice;
	public String barcode ;
	public int def_quantity;
	public int demondquantity;
	
	public ProductsCats parentcat;
	
	
	
	public Boolean loadbyid(Long _id){
		
       myDataBase=helper.getWritableDatabase();
		
		Cursor mCount= myDataBase.rawQuery("select *  from product where id='"+_id+"'",null);
		
		if(mCount.moveToFirst()){
			this.dbid=mCount.getLong(0);
			this.name=mCount.getString(1);
			this.barcode=mCount.getString(2);
			this.def_quantity=mCount.getInt(3);
			this.defaultprice=mCount.getFloat(4);
			this.useorder=mCount.getInt(6);
		
		}
		
		 mCount.close();
		return true;
		
		
	}
	
	
	public Boolean loadbybarcode(String _barcode){
		
		
		 myDataBase=helper.getWritableDatabase();
			
			Cursor mCount= myDataBase.rawQuery("select *  from product where barcode='"+_barcode+"'",null);
			
			if(mCount.moveToFirst()){
				this.dbid=mCount.getLong(0);
				this.name=mCount.getString(1);
				this.barcode=mCount.getString(2);
				this.def_quantity=mCount.getInt(3);
				this.defaultprice=mCount.getFloat(4);
				this.useorder=mCount.getInt(6);
			
			}
			
			 mCount.close();
			return true;
		
		
	}
	
	public int getstock(){
		
		
		
		
		return 0;
		
	}
	
	
	public Boolean save(){
		
		
		    myDataBase=helper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put("title",this.name); 
			values.put("barcode",this.barcode);
			values.put("quantity",this.def_quantity);
			values.put("price",this.defaultprice);
			values.put("del",0);
			values.put("useorder",0);
			
				
			this.dbid=myDataBase.insert("product", null, values);
			
			
		   return true;
		
		
		
	}
	
	public Boolean updateuseroredr(){
		
		
		String query="update product set useorder=useorder+1 where id="+String.valueOf(this.dbid);
		myDataBase.execSQL(query);
		
		return true;
		
	}
	public void delete(){
		
		
		
	}
	
	
	public Products[] getallproducts (){
		
		   myDataBase=helper.getWritableDatabase();
			int a=0;
			Cursor mCount= myDataBase.rawQuery("select *  from product order by useorder desc ",null);
			 int count=mCount.getCount();
			 Products []pros=new Products[count];
			  
			while(mCount.moveToFirst()){
				Products looppro=new Products(_context);
				looppro.dbid=mCount.getLong(0);
				looppro.name=mCount.getString(1);
				looppro.barcode=mCount.getString(2);
				looppro.def_quantity=mCount.getInt(3);
				looppro.defaultprice=mCount.getFloat(4);
				looppro.useorder=mCount.getInt(6);
			
				pros[a]=looppro;
				a++;
			}
			
			 mCount.close();
		
		return pros;
		
		
	}
	
	public Products[] searchbyname(String _searckword){
		
		  myDataBase=helper.getWritableDatabase();
			int a=0;
			Cursor mCount= myDataBase.rawQuery("select *  from product where title like '%"+_searckword+"%' order by useorder desc ",null);
			 int count=mCount.getCount();
			 Products []pros=new Products[count];
			  
			while(mCount.moveToFirst()){
				Products looppro=new Products(_context);
				looppro.dbid=mCount.getLong(0);
				looppro.name=mCount.getString(1);
				looppro.barcode=mCount.getString(2);
				looppro.def_quantity=mCount.getInt(3);
				looppro.defaultprice=mCount.getFloat(4);
				looppro.useorder=mCount.getInt(6);
			
				pros[a]=looppro;
				a++;
			}
			
			 mCount.close();
		
		return pros;
		
	}

	
	
	
}
