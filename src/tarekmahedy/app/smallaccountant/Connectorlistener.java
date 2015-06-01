package tarekmahedy.app.smallaccountant;

import org.apache.http.client.ClientProtocolException;

public interface Connectorlistener {

	void Onincomingdata(String _data,int _type);
	
	void Onerror(ClientProtocolException e);
	
}
