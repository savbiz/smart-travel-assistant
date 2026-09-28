package utils;

import java.util.List;

import com.example.semifinaltravelassistant.R;

import database.Query;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {

	private List<Query> listData;

	private LayoutInflater layoutInflater;

	public CustomListAdapter(Context context, List<Query> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
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
					R.layout.list_row_queries_layout, null);
			holder = new ViewHolder();
			holder.headlineView = (TextView) convertView
					.findViewById(R.id.title);
			holder.categoryNameView = (TextView) convertView
					.findViewById(R.id.category);
			holder.reportedDateView = (TextView) convertView
					.findViewById(R.id.date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// String date=listData.get(position).getCreatedAt();
		holder.headlineView.setText(listData.get(position).getCreatedAt());
		// holder.categoryNameView.setText(listData.get(position).getType());
		// //invertito con quello sotto
		holder.reportedDateView.setText(listData.get(position).getCategory());

		return convertView;
	}

	static class ViewHolder {
		TextView headlineView;
		TextView categoryNameView;
		TextView reportedDateView;
	}

}
