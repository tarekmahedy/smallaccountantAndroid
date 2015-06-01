package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;

public class Invoices extends BaseDbClass{

	
	public Invoices(Context _context) {
		super(_context);
		// TODO Auto-generated constructor stub
		items=new ArrayList<InvoiceItems>();
		total=0;
		
	}

	
	public ArrayList<InvoiceItems> items;
	public Date issuedate;
	public int number ;
	public int invoicetype;
	public double total;
	public double discount;
	
	
	public void additem(InvoiceItems _item){
		
		this.items.add(_item);
	    this.total+=(_item.quantity*_item.price);
	       
		
		
	}
	
	public Boolean save(){
		   myDataBase=helper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put("number",this.number); 
			values.put("clientid",1);
			values.put("discount",this.discount);
			values.put("type",this.invoicetype);
				
			this.dbid=myDataBase.insert("invoices", null, values);
			if(this.dbid>0){
				int a=this.items.size();
				int looper=0;
				while(looper<a){
					
					this.items.get(looper).invoiceid=this.dbid;
					this.items.get(looper).save();
					
					looper++;
				}
				
			}
			else return false;
		
		return true;
	}
	
	
	public void loadbynumber(){
		
		
		
	}
	public void loadalltype(){
		
		
		
		
	}
	
	public Invoices[] searchbynumber(){
		
		Invoices[] resulinvoices=new Invoices[]{};
		
		
		return resulinvoices;
	}

	
}
