package check;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class AlertToUserConnGpsManager {

	private Context _context;

	public AlertToUserConnGpsManager(Context context) {
		this._context = context;
	}

	// Metodo che controlla l'attivazione del sensore GPS
	public void showGPSDisabledAlertToUser() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				_context);
		alertDialogBuilder

				.setMessage(
						"Il sensore GPS è disabilitato. \nL'app necessita dell'attivazione.")
				.setCancelable(false)
				.setPositiveButton("Clicca per attivarlo",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent callGPSSettingIntent = new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								_context.startActivity(callGPSSettingIntent);
							}
						});
		alertDialogBuilder.setNegativeButton("Annulla",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	// Metodo che controlla l'attivazione della connessione dati
	public void showConnectionDisabledAlertToUser() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				_context);
		alertDialogBuilder
				.setMessage(
						"Connessione dati è disabilitata. \nL'app necessita dell'attivazione.")
				.setCancelable(false)
				.setPositiveButton("Clicca per attivarla",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent callGPSSettingIntent = new Intent(
										android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
								_context.startActivity(callGPSSettingIntent);
							}
						});
		alertDialogBuilder.setNegativeButton("Annulla",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

}
