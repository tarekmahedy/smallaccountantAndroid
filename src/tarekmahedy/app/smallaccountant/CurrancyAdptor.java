package tarekmahedy.app.smallaccountant;

import kankan.wheel.widget.adapters.WheelViewAdapter;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CurrancyAdptor implements WheelViewAdapter{

	
	String []_Values;
	Context _context;
	int lang;
	
	public CurrancyAdptor(Context context){
		
		String[] currear=new String[]{"Ã‰ÌÂ","—Ì«· ","œÌ‰«—"," œ—Â„ "," ÌÊ—Ê ","œÊ·«—"};
		String[] curreen =new String[] {"Muh", "Saf", "Rab'Awwal","Rkhir", "madal", "Juma", "Raj", "Sha", "Rama", "Shaw", "Dhul", "Dhul"};  
		
		String langu=context.getString(R.string.langdir);
		lang=Integer.valueOf(langu);
		if(lang==1)_Values=currear;
		else _Values=curreen;
		_context=context;
		
	}
	@Override
	public int getItemsCount() {
		
		return _Values.length;
	}
	@Override
	public View getItem(int index, View convertView, ViewGroup parent) {
		
		LayoutInflater linflater=LayoutInflater.from(_context);
		convertView = linflater.inflate(R.layout.wheelitemlayout, null);
		TextView textv=(TextView)convertView.findViewById(R.id.itemtextview);
		
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
