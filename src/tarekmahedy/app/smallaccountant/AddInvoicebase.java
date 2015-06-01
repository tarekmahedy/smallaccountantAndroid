package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;
import com.dm.zbar.android.scanner.ZBarScannerActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class AddInvoicebase extends Screen implements OnItemSelectedListener  {

	
	 int invoicetype=0;
	 private SQLiteDatabase database;
	 ListView itemslist;
	 Invoices activeinvoic;
	 ArrayList<Recordecolums> columes;	
	 Button   addbtn;
	 Button   cancelbtn;
	 EditText priceeditor;
	 EditText quantityeditor;
	 Context _mainactivy;
	 ImageView scanimg;
	 ImageView addproduct;
	 int screenindex;
	 Button   savebtn;
	 Products loadedproduct;
	 private SimpleCursorAdapter dataSource;
	 Spinner prodspinner;
	 OperationsTab parentscreen;
	 
	 
	 
	public AddInvoicebase(Context context) {
		
		super(context);
		loadedproduct=new Products(context);
		activeinvoic=new Invoices(context);
		
		int resuid=R.layout.invoicescreen;
		if(lang==1)
			resuid=R.layout.invoicescreenar;
		inflate(context,resuid,this);
		this._mainactivy=context;
		initui();
		
	}
	
	
	public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
		
		loadedproduct.loadbyid(id);
		intproui();
	       }

	
	
	    public void onNothingSelected(AdapterView parent) {
	    	loadedproduct.loadbyid(prodspinner.getSelectedItemId());
	        intproui();
	    }

	    

	public void initui(){
		
		liner=(LinearLayout)findViewById(R.id.mainliner);
		itemslist=(ListView)findViewById(R.id.invoicelist);
		prodspinner=(Spinner)findViewById(R.id.productslist);
		prodspinner.setOnItemSelectedListener(this);
           

		fillproducts();
		priceeditor=(EditText)findViewById(R.id.editText4);
		quantityeditor=(EditText)findViewById(R.id.editText3);
		
		savebtn=(Button)findViewById(R.id.button3);
		savebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Headerbar.alert("··«”› ·„ Ì „  ›⁄Ì· «÷«›… «·›Ê« Ì— »⁄œ", true);
				activeinvoic.invoicetype=invoicetype;
				if(activeinvoic.save())
					 Headerbar.alert(getContext().getString(R.string.donkey), true);
				else  Headerbar.alert(getContext().getString(R.string.donkey), true);
				
			}
			
		});
		
		addbtn=(Button)findViewById(R.id.button1);
		addbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getreporttype();
				
			}
		});
		
		
		cancelbtn=(Button)findViewById(R.id.button2);
		cancelbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initinvoice();
				
			}
		});
		
		scanimg=(ImageView)findViewById(R.id.barcodeicon);
		scanimg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), ZBarScannerActivity.class);
				((Activity) _mainactivy).startActivityForResult(intent,screenindex);		

			}
		});
		
		
		
		addproduct=(ImageView)findViewById(R.id.imageView1);
		addproduct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				parentscreen.mainswitcher.snapToScreen(4);
			//	parentscreen.showscreen(parentscreen.addproduct);
				
			}
		});
		
		
		initinvoice();  
		loadedproduct.loadbyid(prodspinner.getSelectedItemId());
        intproui();
	}


	public void initinvoice(){
		
           Invoiceitemsadptor adpt=new Invoiceitemsadptor(getContext(),activeinvoic.items ,lang);
		    int lenhei=120;
		    itemslist.setAdapter(adpt);
		    itemslist.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,lenhei, 1f));
				
		
	}
	
	
	public void getproductbybarcode(String barcodestr){
		
		this.loadedproduct.loadbybarcode(barcodestr);
		intproui();
		SpinnerAdapter adapter= prodspinner.getAdapter();
		 for (int position = 0; position < adapter.getCount(); position++)
		    {
		        if(adapter.getItemId(position) == this.loadedproduct.dbid)
		        {
		        	prodspinner.setSelection(position);
		            return;
		        }
		    }

	}
	
	public void intproui(){


		quantityeditor.setText(String.valueOf(this.loadedproduct.def_quantity));
		priceeditor.setText(String.valueOf(this.loadedproduct.defaultprice));
		
		
	}
	
	
	public int  getreporttype(){

	    addcolums();
		
		Invoiceitemsadptor adpt=new Invoiceitemsadptor(getContext(),activeinvoic.items,lang);
		adpt.total=activeinvoic.total;
		int lenhei=120;
		if(adpt.values!=null)
		lenhei = (adpt.values.size()+2)*60;
		itemslist.setAdapter(adpt);
		itemslist.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,lenhei, 1f));
		
        return lenhei;
	}


	//get report data 

	public void  addcolums(){

		InvoiceItems invitem=new InvoiceItems(getContext());
		invitem.quantity=Integer.valueOf(quantityeditor.getText().toString());
		invitem.price=Float.valueOf(priceeditor.getText().toString());
		invitem.product=new Products(_context);
		invitem.product.loadbyid(loadedproduct.dbid);
		
	    priceeditor.setText("1");
	    quantityeditor.setText("1");
        this.activeinvoic.additem(invitem);
		 
	}

	
	public void fillproducts(){

		lang=Integer.valueOf(getContext().getString(R.string.langdir));
		database = helper.getWritableDatabase();
		String colu="title";
		String fields[] = { colu };
		Cursor data = database.rawQuery("select id _id,"+colu+",barcode from product ",null);

		dataSource = new SimpleCursorAdapter(getContext(),android.R.layout.simple_spinner_item,data,fields,new int[]{android.R.id.text1});
		dataSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		prodspinner.setAdapter(dataSource);
		
	}
	

}
