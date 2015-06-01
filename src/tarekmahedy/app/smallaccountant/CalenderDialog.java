package tarekmahedy.app.smallaccountant;

import java.text.DecimalFormat;
import java.util.Calendar;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class CalenderDialog extends BaseDialog {


	WheelView pickerday;
	WheelView pickermonth;
	WheelView pickeryear;
	Button applybtn;
	Button cancellbtn;
	
	String []monthshijri;
	String []months;
	String []monthshijrien;
	int hijri=0;
	int yearstart=2005;
	SharedPreferences prefs;
	int cancelled=0;
	
	
	public CalenderDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.calenderlayout);
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		if(prefs.getBoolean("hijricalender",false))
        hijri =1;
		else hijri=0;
        
        
		applybtn=(Button)findViewById(R.id.btnset);
		cancellbtn=(Button)findViewById(R.id.btncancel);
		
		pickerday=(WheelView)findViewById(R.id.pickerday);
		pickermonth=(WheelView)findViewById(R.id.pickermonth);
		pickeryear=(WheelView)findViewById(R.id.pickeryear);

		monthshijri=new String[]{"„Õ—„","’›—","—»Ì⁄ «Ê·","—»Ì⁄ À«‰Ï","Ã„«œÏ «Ê·Ï","Ã„«œ… «·«ŒÌ—…"," —Ã» ","‘⁄»«‰","—„÷«‰","‘Ê«·","–Ï «·ÕÃ…","–Ï «·ﬁ⁄œ…"};
		months=new String[]{"Ì‰«Ì—","›»—«Ì— ","„«—”"," «»—Ì· "," „«ÌÊ ","ÌÊ‰Ì…","ÌÊ·ÌÊ","«€”ÿ”","”» „»—","«ﬂ Ê»—","‰Ê›„»—"," œÌ”„»—"};
        monthshijrien =new String[] {"Muharram", "Safar", "Rabi'ul Awwal","Rabi'ul Akhir", "Jumadal Ula", "Jumadal Akhira", "Rajab", "Sha'ban", "Ramadan", "Shawwal", "Dhul Qa'ada", "Dhul Hijja"};  

        

		Wheeladptor adpt=new Wheeladptor();
		adpt.incremeant=1;
		adpt.startval=1;
		if(hijri==1)
			adpt.endval=31;
		else  adpt.endval=32;
		adpt._context=getContext();
		pickerday.setViewAdapter(adpt);

		Wheeladptor adptm=new Wheeladptor();
		if(hijri==1)
			adptm._Values=monthshijri;
		else  adptm._Values=months;
		adptm._context=getContext();
		pickermonth.setViewAdapter(adptm);

		Wheeladptor adpty=new Wheeladptor();
		adpty.incremeant=1;
		if(hijri==1){
			adpty.startval=1430;
			adpty.endval=1441;
			yearstart=1430;
		}else {
			adpty.startval=2005;
			adpty.endval=2020;
			yearstart=2005;

		}
		adpty._context=getContext();
		pickeryear.setViewAdapter(adpty);


		OnWheelChangedListener changdleg= new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				wheel.setTag(newValue);

				// TODO Auto-generated method stub

			}
		};


		pickerday.addChangingListener(changdleg);
		pickermonth.addChangingListener(changdleg);
		pickeryear.addChangingListener(changdleg);


		cancellbtn.setOnClickListener(clcikedlistenr);
		cancellbtn.setTag(0);
		applybtn.setOnClickListener(clcikedlistenr);
		applybtn.setTag(1);
	}

	
	View.OnClickListener clcikedlistenr=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {

          cancelled=Integer.valueOf(v.getTag().toString());
		  cancel();
		}
	};
	
	public void setdate(String _date){

		String []valdate=_date.split("/");
		int dayinde=(Integer.valueOf(valdate[0]))-1;
		pickerday.setCurrentItem(dayinde);
		pickerday.setTag(dayinde);
		int monthind=(Integer.valueOf(valdate[1]))-1;
		pickermonth.setCurrentItem(monthind);
		pickermonth.setTag(monthind);
		int yearind=(Integer.valueOf(valdate[2]))-yearstart;

		pickeryear.setCurrentItem(yearind);
		pickeryear.setTag(yearind);


	}


	public String getdate(){

		String outputIslamicDate = (1+Integer.valueOf(pickerday.getTag().toString()))+ "/"+(1+Integer.valueOf(pickermonth.getTag().toString()))+ "/" + (yearstart+Integer.valueOf(pickeryear.getTag().toString()));  

		return outputIslamicDate; 


	}





	double gmod(double n,double  m) {  
		return ((n % m) + m) % m;  
	}  

	double[] kuwaiticalendar(boolean adjust) {  

		Calendar today = Calendar.getInstance(); 

		int adj=0;  
		if(adjust){  
			adj=0;  
		}else{  
			adj=1;  
		}  

		if (adjust) {  
			int adjustmili = 1000 * 60 * 60 * 24 * adj;  
			long todaymili = today.getTimeInMillis() + adjustmili;  
			today.setTimeInMillis(todaymili);  
		}  
		double day = today.get(Calendar.DAY_OF_MONTH);  
		double  month = today.get(Calendar.MONTH);  
		double  year = today.get(Calendar.YEAR);  

		double  m = month + 1;  
		double  y = year;  
		if (m < 3) {  
			y -= 1;  
			m += 12;  
		}  

		double a = Math.floor(y / 100.);  
		double b = 2 - a + Math.floor(a / 4.);  

		if (y < 1583)  
			b = 0;  
		if (y == 1582) {  
			if (m > 10)  
				b = -10;  
			if (m == 10) {  
				b = 0;  
				if (day > 4)  
					b = -10;  
			}  
		}  

		double jd = Math.floor(365.25 * (y + 4716)) + Math.floor(30.6001 * (m + 1)) + day  
		+ b - 1524;  

		b = 0;  
		if (jd > 2299160) {  
			a = Math.floor((jd - 1867216.25) / 36524.25);  
			b = 1 + a - Math.floor(a / 4.);  
		}  
		double bb = jd + b + 1524;  
		double cc = Math.floor((bb - 122.1) / 365.25);  
		double dd = Math.floor(365.25 * cc);  
		double ee = Math.floor((bb - dd) / 30.6001);  
		day = (bb - dd) - Math.floor(30.6001 * ee);  
		month = ee - 1;  
		if (ee > 13) {  
			cc += 1;  
			month = ee - 13;  
		}  
		year = cc - 4716;  

		double wd = gmod(jd + 1, 7) + 1;  

		double iyear = 10631. / 30.;  
		double epochastro = 1948084;  
		double epochcivil = 1948085;  

		double shift1 = 8.01 / 60.;  

		double z = jd - epochastro;  
		double cyc = Math.floor(z / 10631.);  
		z = z - 10631 * cyc;  
		double j = Math.floor((z - shift1) / iyear);  
		double iy = 30 * cyc + j;  
		z = z - Math.floor(j * iyear + shift1);  
		double im = Math.floor((z + 28.5001) / 29.5);  
		if (im == 13)  
			im = 12;  
		double id = z - Math.floor(29.5001 * im - 29);  

		double[]  myRes = new double[8];  

		myRes[0] = day; // calculated day (CE)  
		myRes[1] = month - 1; // calculated month (CE)  
		myRes[2] = year; // calculated year (CE)  
		myRes[3] = jd - 1; // julian day number  
		myRes[4] = wd - 1; // weekday number  
		myRes[5] = id; // islamic date  
		myRes[6] = im - 1; // islamic month  
		myRes[7] = iy; // islamic year  

		return myRes;  
	}  
	String writeIslamicDate() {  

		// This Value is used to give the correct day +- 1 day  
		boolean dayTest=true;  
		Calendar today = Calendar.getInstance(); 
		double day = today.get(Calendar.DAY_OF_MONTH);  
		double  monthv = today.get(Calendar.MONTH);  
		double  year = today.get(Calendar.YEAR);  

		double[] iDate = kuwaiticalendar(dayTest);  
		//wdNames[(int) iDate[4]] + ", " +
		DecimalFormat format = new DecimalFormat();
		format.setDecimalSeparatorAlwaysShown(false);
		String outputIslamicDate ;
		if(hijri==1)
			outputIslamicDate =  format.format(iDate[5]) + "/"+format.format(iDate[6]) + "/" + format.format(iDate[7]);  
		else  outputIslamicDate =  format.format(day) + "/"+format.format(monthv+1) + "/" + format.format(year);  

		return outputIslamicDate;  
	} 



}
