package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;

import tarekmahedy.app.smallaccountant.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class listadpt extends BaseAdapter {
	
	
	private final Context context;
	private final ArrayList<InputLlistCell> values;
    int lang=1;
    OperationsTab parentlist;
	public listadpt(Context context, ArrayList<InputLlistCell> values,OperationsTab _parentlist,int _lang) {
		
		this.context = context;
		this.values = values;
		this.lang=_lang;
		this.parentlist=_parentlist;
	}

	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		int layoutid=R.layout.contactlistitem;
		if(lang==1)layoutid=R.layout.contactslistitem1;
		convertView = inflater.inflate(layoutid, null);
		convertView.setTag(values.get(position));
	//	View rowView = inflater.inflate(layoutid, parent, false);
		
		TextView textView = (TextView) convertView.findViewById(R.id.username);
		textView.setText(values.get(position).title);
		textView=SmallAccountActivity.setcustomfont(textView,context);
		Long a= Long.valueOf(values.get(position).id);
		
		ImageView logimg=(ImageView)convertView.findViewById(R.id.logo);
		logimg.setImageResource(values.get(position).iconid);
		
		ImageView imgbtn=(ImageView) convertView.findViewById(R.id.imageButton1);
		
		if(a>8){
			imgbtn.setVisibility(View.VISIBLE);
			imgbtn.setTag(values.get(position));
			imgbtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					InputLlistCell cell=(InputLlistCell)v.getTag();
					if(cell.id>9) parentlist.delealerat((int)cell.id);
					
				}
			});
			
	
		
		}
		
		return convertView;
		
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return values.size();
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
