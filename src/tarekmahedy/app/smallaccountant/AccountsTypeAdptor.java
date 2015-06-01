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

public class AccountsTypeAdptor extends BaseAdapter {

	
	private final Context context;
	private final ArrayList<InputLlistCell> values;
    int lang=1;
	public AccountsTypeAdptor(Context context, ArrayList<InputLlistCell> values,int _lang) {
		
		this.context = context;
		this.values = values;
		this.lang=_lang;
		
	}

	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		int layoutid=R.layout.accountitem;
		if(lang==1)layoutid=R.layout.accountitemar;
		convertView = inflater.inflate(layoutid, null);
		convertView.setTag(values.get(position));
		
		TextView textView = (TextView) convertView.findViewById(R.id.username);
		textView.setText(values.get(position).title);
		textView.setTextColor(Color.BLACK);
		textView=SmallAccountActivity.setcustomfont(textView,context);
		textView.setTextSize(17);
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
