package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class Grideaptor extends BaseAdapter {
	
	
	private final Context context;
	private final ArrayList<InputLlistCell> values;
    int lang=1;
   
    
	public Grideaptor(Context context, ArrayList<InputLlistCell> values,int _lang) {
		
		this.context = context;
		this.values = values;
		this.lang=_lang;
	}

	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		int layoutid=R.layout.griditem;
		
		convertView = inflater.inflate(layoutid, null);
		convertView.setTag(values.get(position));
		
		TextView textView = (TextView) convertView.findViewById(R.id.username);
		textView.setText(values.get(position).title);
		textView=SmallAccountActivity.setcustomfont(textView,this.context);
		//Long a= Long.valueOf(values.get(position).id);
		
		ImageView logimg=(ImageView)convertView.findViewById(R.id.logo);
		logimg.setImageResource(values.get(position).iconid);
		
		
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
