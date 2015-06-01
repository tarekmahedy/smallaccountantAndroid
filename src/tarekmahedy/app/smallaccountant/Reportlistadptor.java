package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class Reportlistadptor  extends BaseAdapter{


	private  Context context;
	private  ArrayList<InputLlistCell> values;
	int lang=1;

	public Reportlistadptor(Context _context, ArrayList<InputLlistCell> values,int _lang) {

		this.context = _context;
		this.values = values;
		this.lang=_lang;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		int resid=R.layout.contactlistitem;
		if(lang==1)resid=R.layout.contactslistitem1;

		convertView = inflater.inflate(resid, null);
		convertView.setTag(values.get(position)); 
		TextView txtviewfrom = (TextView) convertView.findViewById(R.id.username);
		txtviewfrom.setText(values.get(position).title);
		txtviewfrom.setTextColor(Color.BLACK);
		
		txtviewfrom=SmallAccountActivity.setcustomfont(txtviewfrom,context);
		
		ImageView logimg=(ImageView)convertView.findViewById(R.id.logo);
		int imglin=Integer.valueOf(values.get(position).iconid);
		logimg.setImageResource(imglin);
		
		return convertView;


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


