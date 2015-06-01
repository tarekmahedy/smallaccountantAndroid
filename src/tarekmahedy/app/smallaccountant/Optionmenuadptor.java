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


public class Optionmenuadptor extends BaseAdapter{


	private final Context context;

	ArrayList<String[]> values;
	LayoutInflater linflater;
	int lang=1;
    int style=0;
    
	public Optionmenuadptor(Context _context,ArrayList<String[]> _values,int _lang,int _style) {

		context = _context;
		values = _values;
		linflater=LayoutInflater.from(context);
		lang=_lang;
        style=_style;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

          int resid=R.layout.optionslistitem;
          if(lang==1)resid=R.layout.optionslistitemar;

		convertView = linflater.inflate(resid, null);
		if(position==0)
		convertView.setBackgroundColor(Color.GRAY);
		convertView.setTag(values.get(position));
        TextView txtviewfrom = (TextView) convertView.findViewById(R.id.username);
        if(style==1){
        txtviewfrom.setTextColor(Color.WHITE);
       
        }
		txtviewfrom.setText(values.get(position)[0]);
		txtviewfrom=SmallAccountActivity.setcustomfont(txtviewfrom,context);
		ImageView logimg=(ImageView)convertView.findViewById(R.id.logo);
		int imglin=Integer.valueOf(values.get(position)[1]);
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
		return values.get(arg0);
	}


	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}





}
