package tarekmahedy.app.smallaccountant;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import kankan.wheel.widget.adapters.WheelViewAdapter;

public class Wheeladptor implements WheelViewAdapter{

	
	String []_Values;
	Context _context;
	int incremeant=0;
	int startval=0;
	int endval=1;
	@Override
	public int getItemsCount() {
		// TODO Auto-generated method stub
		if(incremeant==1)
			return (endval-startval);
		
		if(_Values!=null)
			return _Values.length;
		
		return 0;
	}

	@Override
	public View getItem(int index, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		//wheelitemlayout
		LayoutInflater linflater=LayoutInflater.from(_context);
		convertView = linflater.inflate(R.layout.wheelitemlayout, null);
		TextView textv=(TextView)convertView.findViewById(R.id.itemtextview);
		
		if(incremeant==1)textv.setText(String.valueOf(startval+index));
		else 
		textv.setText(_Values[index]);
		return convertView;
		
	}

	@Override
	public View getEmptyItem(View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	
	
}
