package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Invoiceitemsadptor extends BaseAdapter{

	private final Context context;

	public int reporttype=0;
	ArrayList<InvoiceItems> values;
	LayoutInflater linflater;
	int lang=1;
      Double total;
	
	public Invoiceitemsadptor(Context _context,ArrayList<InvoiceItems> _values,int _lang) {

		context = _context;
		values = _values;
		linflater=LayoutInflater.from(context);
		lang=_lang;
        total=(double) 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        int layid=R.layout.invoiceitem;
		if(lang==1)layid= R.layout.invoiceitemar;
		int footer=values.size()+1;
		convertView = linflater.inflate(layid, null);
		
		TextView txtviewvalue = (TextView) convertView.findViewById(R.id.price);
		TextView txtviewfrom = (TextView) convertView.findViewById(R.id.quantit);
		TextView txtviewdate = (TextView) convertView.findViewById(R.id.prodname);
		txtviewvalue=SmallAccountActivity.setcustomfont(txtviewvalue, context);
		txtviewfrom=SmallAccountActivity.setcustomfont(txtviewfrom, context);
		txtviewdate=SmallAccountActivity.setcustomfont(txtviewdate, context);
		
		 int maincolor=Color.BLUE;
		
		    String headertitle=context.getString(R.string.pricelabel);
			String footertitle=context.getString(R.string.totallabel);
		    String quantitsr=context.getString(R.string.quantitylabel);
	        String proname=context.getString(R.string.productname);
	        
	       
	       
		if(position==0){
			//header cloun
			txtviewdate.setText(proname);
			txtviewfrom.setText(quantitsr);
			txtviewvalue.setText(headertitle);
			maincolor=Color.BLACK;
			convertView.setBackgroundColor(Color.LTGRAY);
		}
		else if(position==footer){
            	//footer cloun
            	txtviewdate.setText(footertitle);
    			txtviewvalue.setText(String.valueOf(total));
    			maincolor=Color.BLACK;
    			convertView.setBackgroundColor(Color.LTGRAY);
		}
		else {
			txtviewdate.setText(values.get(position-1).product.name);
			txtviewfrom.setText(String.valueOf(values.get(position-1).quantity));
			txtviewvalue.setText(String.valueOf(values.get(position-1).price));
			maincolor=Color.BLUE;
			convertView.setBackgroundColor(Color.WHITE);
		}
		
		
		
		txtviewdate.setTextColor(maincolor);
	    txtviewfrom.setTextColor(maincolor);
	    txtviewvalue.setTextColor(maincolor);

	   
		return convertView;


	}


	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return values.size()+2;
	}


	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}





}
