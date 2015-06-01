package tarekmahedy.app.smallaccountant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class Connector {



	String serverurl="http://tarekmahedy.com/";
	
	ArrayList<Connectorlistener> listeners = new ArrayList<Connectorlistener> ();
	  public static String[] delimiter=new String[]{"####","###","#%#"};


	public void setListener (Connectorlistener listener) 
	{

		this.listeners.add(listener);

	}


	public static final String md5(final String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
			.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}



	List<NameValuePair> convertstringtopairs(String _parametrs){

		//"f=3getbackup&linke=dgsgdf&name=tarek"
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();	

		String []values=_parametrs.split("&");
		int alen=values.length;
		while(alen>0){
			alen--;
			String []param=	values[alen].trim().split("=");
			if(param.length==2){
				nameValuePairs.add(new BasicNameValuePair(param[0].trim(), param[1].trim()));
			}

		}

		
		//apend the auto required data here also 
		//like user id device id ,networkcode,country code,
		
		return nameValuePairs;
	}



	public void postData(String _data) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(this.serverurl);

		try {

			httppost.setEntity(new UrlEncodedFormEntity(convertstringtopairs(_data),"UTF-8"));

			HttpResponse response = httpclient.execute(httppost);

			HttpEntity httpEntity = response.getEntity();
			InputStream is = httpEntity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is,"UTF-8"),2000);
			
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			is.close();

			String resdata=sb.toString().substring(1, sb.toString().length());
			this.parsedata(resdata);


		} catch (ClientProtocolException e) {

			for (Connectorlistener listener : this.listeners) {
				listener.Onerror(e);
				this.listeners.remove(listener);
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	} 





	public void parsedata(String _result){

		int type=1;
		
		int strlen=_result.length();

		if(strlen<10){
			strlen=_result.length();
			int value=Integer.parseInt(_result);
			
			if(value>0)type=2;
			else {
				switch (value) {
				case 0:
					type=3;
					break;
				case -1:
					type=4;
					break;
				case -2:
					type=5;
					break;
				case -3:
					type=6;
					break;
				} 

			}
		}

		for (Connectorlistener listener : this.listeners) {
			listener.Onincomingdata(_result, type);

			this.listeners.remove(listener);
		}

	}


	public static void test(){
		
		
		ArrayList<Object> resultarray=Connector.parsejson("11144 #%# 66 #%# ”·… «”»Ê⁄Ì… #%# 0 #%# 0 #%# 0 #%# a7e8rpu #%#  #### 63915 #%# 11144 #%# 17 #%# 1 #%# 0 #%# 0 #%# 0 #%# 66 #%# 2013-10-10 14:34:40 #%# NULL #%# 17 #%# 17 #%# 51 #%# »’· «Œ÷— #%# 0.00 #%# green_onions #%# 3 #%# 1 #%# 1 #%# 51 #%# 333 #%# 17 #%# Mlist #%# 51 #%# Œ÷—Ê«  #%# 51 #%#  #%# 51 #%#  ### 64444 #%# 11144 #%# 33 #%# 1 #%# 0 #%# 0 #%# 0 #%# 66 #%# 2013-10-10 14:34:52 #%# NULL #%# 33 #%# 33 #%# 51 #%# ›·›· —Ê„Ì «Õ„— #%# 0.00 #%# red_peppers #%# 3 #%# 1 #%# 1 #%# 51 #%# 333 #%# 33 #%# Mlist #%# 51 #%# Œ÷—Ê«  #%# 51 #%#  #%# 51 #%#  ### ", 0);
		
		int a=resultarray.size();
		
	}
	

	public static ArrayList<Object> parsejson(String _data ,int _delindex){

		ArrayList<Object> resultarray=new ArrayList<Object>();	
		  
		   String delimite  = delimiter[_delindex];
		    
		   _delindex++;
		    
		   
		   String[] subarrava=_data.split(delimite);
		      
		        int coun=subarrava.length;
		        int looper=0;
		        
		        while (coun>looper) {
		            
		            String datalevel2=subarrava[looper];
		            
		            if(_delindex<delimiter.length){
		            	ArrayList<Object> subresultarray=new ArrayList<Object>();
		            	subresultarray = parsejson(datalevel2,_delindex);
		                
		            	 resultarray.add(subresultarray);
		                
		            }
		            else {
		                
		                if(datalevel2.length()<=0)datalevel2="null";
		                 resultarray.add(datalevel2.trim());
		               
		                
		            }
		            
		            looper++;
		            
		        }
		        
		      

		return resultarray;

	}




}
