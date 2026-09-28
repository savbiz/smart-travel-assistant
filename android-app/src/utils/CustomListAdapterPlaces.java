package utils;

import java.util.List;

import com.a2plab.googleplaces.models.Place;
import com.example.semifinaltravelassistant.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomListAdapterPlaces extends BaseAdapter {

	private List<Place> listData;

	private LayoutInflater layoutInflater;

	private float[] distance = new float[100];

	public CustomListAdapterPlaces(Context context, List<Place> listData,
			float[] distance) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
		this.distance = distance;
	}

	@Override
	public int getCount() {
		if (listData == null)
			return 0;
		else
			return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.list_row_places_layout, null);
			holder = new ViewHolder();
			holder.headlineView = (TextView) convertView
					.findViewById(R.id.title);
			holder.addressView = (TextView) convertView
					.findViewById(R.id.address);
			holder.distanceView = (TextView) convertView
					.findViewById(R.id.distance);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (listData != null) {
			holder.headlineView
					.setText(listData.get(position).getName() + "\n");

			if (distance[position] == 0.00)
				holder.distanceView.setText("Distanza non disponibile");

			else
				holder.distanceView.setText(String.format("%.2f",
						distance[position])
						+ "m = ["
						+ String.format("%.2f", distance[position] / 1000)
						+ "km]");
			//

			// holder.addressView.setText(listData.get(position).getFormattedAddress());
		}
		return convertView;
	}

	static class ViewHolder {
		TextView headlineView;
		TextView addressView;
		TextView distanceView;
	}

}
