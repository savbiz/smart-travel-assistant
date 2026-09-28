package check;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {

	private Context _context;

	public ConnectionDetector(Context context) {
		this._context = context;
	}

	// controlla la connessione mobile
	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo mobile = connectivity
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			return mobile.isConnected();
		}
		return false;
	}
}