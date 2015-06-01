package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;

import tarekmahedy.app.smallaccountant.R;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Reportadaptor extends BaseAdapter{

	private final Context context;

	public int reporttype=0;
	ArrayList<Recordecolums> values;
	LayoutInflater linflater;
	int lang=1;
    ReportGenerator reportgenerator;
    
    
	public Reportadaptor(Context _context,ArrayList<Recordecolums> _values,int _reporttype,int _lang) {

		context = _context;
		values = _values;
		reporttype=_reporttype;
		linflater=LayoutInflater.from(context);
		lang=_lang;
       // reportgenerator=new ReportGenerator(_context,reporttype);
		//reportgenerator.landir=Integer.valueOf(_context.getString(R.string.langdir));
       
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		// reporttype  0 for all record 1 for only one value  3 for two row type 
		
//       if(position==0)convertView= reportgenerator.generateheader();
//       else if((position-1)>=values.size())
//    	   convertView= reportgenerator.generatefooter(new String[]{"4"});
//       else if(values.size()>(position-1)){
//    	 Recordecolums col=  values.get(position-1);
//    	 convertView= reportgenerator.generatecell(col);
//       }
//		
//		return convertView;
		
		convertView = linflater.inflate(getlayoutid(reporttype), null);
		TextView txtviewfrom = (TextView) convertView.findViewById(R.id.txtviewfrom);
		txtviewfrom=SmallAccountActivity.setcustomfont(txtviewfrom,context);
		TextView txtviewdate = (TextView) convertView.findViewById(R.id.txtviewdate);
		txtviewdate=SmallAccountActivity.setcustomfont(txtviewdate,context);
		TextView txtviewvalue = (TextView) convertView.findViewById(R.id.txtviewvalue);
		txtviewvalue=SmallAccountActivity.setcustomfont(txtviewvalue,context);
		TextView txtviewcomment = (TextView) convertView.findViewById(R.id.txtviewcomment);
		txtviewcomment=SmallAccountActivity.setcustomfont(txtviewcomment,context);
		if(values.get(position).txtcomment!=null)
			txtviewcomment.setText(values.get(position).txtcomment);
		int maincolor=Color.BLUE;

		if(reporttype==2&&values.get(position).debttype==1)maincolor=Color.RED;
		if(values.get(position).debttype>=2)maincolor=Color.BLACK;


		
		txtviewfrom.setText(values.get(position).txtfromname);
		txtviewfrom.setTextColor(maincolor);
		//txtviewdate
		
		txtviewdate.setText(values.get(position).txtoperationdate);
		txtviewdate.setTextColor(maincolor);
		
		TextView txtviewto = (TextView) convertView.findViewById(R.id.txtviewto);

		if(reporttype==0){
			txtviewto.setText(values.get(position).txttoname);
			txtviewto.setTextColor(Color.RED);
		}
		else txtviewto.setVisibility(View.GONE);

		
		if(values.get(position).debttype!=3)
			txtviewvalue.setText(String.valueOf(values.get(position).value));
		else txtviewvalue.setText(context.getString(R.string.reportvalue));

		txtviewvalue.setTextColor(maincolor);


		if(position%2==1&&values.get(position).debttype<2)
			convertView.setBackgroundColor(Color.WHITE);
		else 
			convertView.setBackgroundColor(Color.GRAY);

		return convertView;


	}


	private int getlayoutid(int _reporttype){

		switch (_reporttype) {
		case 0:

			if(lang==1)return R.layout.reportlistitem1;
			return R.layout.reportlistitem;
		case 1:
			if(lang==1)return R.layout.reportlistitem1;
			return R.layout.reportlistitem;
		case 2:
			if(lang==1)return R.layout.reportlistitem1;
			return R.layout.reportlistitem;

		}


		return 0;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return values.size();
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
