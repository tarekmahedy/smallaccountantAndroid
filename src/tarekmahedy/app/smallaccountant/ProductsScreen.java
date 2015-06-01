package tarekmahedy.app.smallaccountant;

import com.dm.zbar.android.scanner.ZBarScannerActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class ProductsScreen  extends Screen {

	Spinner accounttype;
	EditText productnameEdit;
	EditText barcode;
	EditText quantityedit;
	EditText priceedit;
	ImageView scanimg;
	Button   savebtn;
	long accid;
	public int  editable=0;
    public  long lastinsertedid=-1;
	private SQLiteDatabase database;
	private SimpleCursorAdapter dataSource;
	OperationsTab _mainactivy;
	
	
	public ProductsScreen(Context context) {
		super(context);
		
		int resuid=R.layout.productlayout;
		if(lang==1) resuid=R.layout.productlayoutar;
		
		inflate(context,resuid,this);
		
		initui();
		
	}
	
	
	
	public void initui(){
		
		liner=(LinearLayout)findViewById(R.id.mainliner);
		productnameEdit=(EditText)findViewById(R.id.proname);
		barcode=(EditText)findViewById(R.id.barcode);
		quantityedit=(EditText)findViewById(R.id.editText3);
		priceedit=(EditText)findViewById(R.id.editText4);
		
		
		savebtn=(Button)findViewById(R.id.button1);
		savebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try{
				Products newpro=new Products(getContext());
				newpro.barcode=barcode.getText().toString();
				newpro.name=productnameEdit.getText().toString();
				
				newpro.defaultprice=Float.valueOf(priceedit.getText().toString());
				newpro.def_quantity=Integer.valueOf(quantityedit.getText().toString());
				newpro.save();
			   // Long insertedid=helper.addproduct(prodname, prodbarcode,Integer.valueOf(prodquantity),Double.valueOf(prodprice));
			
			    if(newpro.dbid>0){
			    	if(_mainactivy!=null){
			    	_mainactivy.sellinvoice.fillproducts();
			    	_mainactivy.purchasinvoice.fillproducts();
			    	}
			    Headerbar.alert(getContext().getString(R.string.donkey), true);
			    }
			//else Headerbar.alert(getContext().getString(R.string.donkey), true);
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		
		scanimg=(ImageView)findViewById(R.id.imageView2);
		scanimg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), ZBarScannerActivity.class);
				_mainactivy.startActivityForResult(intent,3);		

			}
		});
	}
	
	
	
	public void initscreen(){
		
		
	}


	public void loaddata(long _id,int _parentid,String _accountname){
		editable=1;
		productnameEdit.setText(_accountname);
		accounttype.setSelection(_parentid);
		accid=_id;
	}

	
	public void filltyps(){

		lang=Integer.valueOf(getContext().getString(R.string.langdir));
		database = helper.getWritableDatabase();
		String colu="namear";
		if(lang==2)colu="nameen";
		String fields[] = { colu };
		Cursor data = database.rawQuery("select id _id,"+colu+" from accountstypes ",null);

		dataSource = new SimpleCursorAdapter(getContext(),android.R.layout.simple_spinner_item,data,fields,new int[]{android.R.id.text1});
		dataSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		accounttype.setAdapter(dataSource);




	}
	
}
