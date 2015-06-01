package tarekmahedy.app.smallaccountant;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;

public class InvoiceItems extends BaseDbClass{

	
	public InvoiceItems(Context _context) {
		super(_context);
		// TODO Auto-generated constructor stub
		this.product=new Products(_context);
	}

	public Products product;
	public int quantity;
	public float price;
	public long invoiceid;
	public Date expirdate;
	
	public Boolean save(){
		
		myDataBase=helper.getWritableDatabase();
			
		ContentValues values = new ContentValues();
		values.put("invoiceid",this.invoiceid); 
		values.put("productid",this.product.dbid);
		values.put("quantity",this.quantity);
		values.put("price",this.price);
			
		this.dbid=myDataBase.insert("invoicitems", null, values);
		
		return true;
	}
	
	
	
	
	public void loadbyid(){
		
		
		
		
	}
	
	public InvoiceItems[] loadallitemsbyinvoiceid(){
		
		InvoiceItems[]items=new InvoiceItems[]{};
		
		
		
		
		return items;
	}

	
}
