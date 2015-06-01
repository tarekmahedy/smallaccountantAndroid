package tarekmahedy.app.smallaccountant;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountListadptor extends BaseAdapter{


	private  Context context;
	private  ArrayList<InputLlistCell> values;
	int lang=1;
	static AccountsTab paternttab;

	public AccountListadptor(Context _context, ArrayList<InputLlistCell> values,int _lang,AccountsTab _paternttab) {

		this.context = _context;
		this.values = values;
		this.lang=_lang;
		AccountListadptor.paternttab=_paternttab;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		int resid=R.layout.accountmngerlistitem;
		if(lang==1)resid=R.layout.accountmngerlistitemar;

		convertView = inflater.inflate(resid, null);
		convertView.setTag(values.get(position)); 
		TextView txtviewfrom = (TextView) convertView.findViewById(R.id.accountname);
		txtviewfrom=SmallAccountActivity.setcustomfont(txtviewfrom,this.context);
		
		txtviewfrom.setText(values.get(position).title);
		txtviewfrom.setTextColor(Color.BLACK);
		ImageView logimg=(ImageView)convertView.findViewById(R.id.logo);
		int imglin=Integer.valueOf(values.get(position).iconid);
		logimg.setImageResource(imglin);
		ImageView editbtn=(ImageView) convertView.findViewById(R.id.editimg);
		editbtn.setTag(values.get(position));

		editbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InputLlistCell cell=(InputLlistCell)v.getTag();
				AccountListadptor.paternttab.editeaccountdialogue(cell);
			}
		});


		ImageView delbtn=(ImageView) convertView.findViewById(R.id.delimg);
		if(values.get(position).del==1)
			delbtn.setVisibility(View.GONE);
		else {
			
			delbtn.setTag(values.get(position));
			delbtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					InputLlistCell cell=(InputLlistCell)v.getTag();
					AccountListadptor.paternttab.delealerat((int)cell.id,cell.parentid);

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


