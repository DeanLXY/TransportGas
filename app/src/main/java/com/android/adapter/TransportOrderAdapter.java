package com.android.adapter;

import android.content.Context;
import android.view.View;

import com.android.annotation.ContentView;
import com.android.annotation.Viewholder;
import com.android.module.Order;
import com.example.transportgas.R;

@ContentView(R.layout.distace_list_item)
@Viewholder(TransportOrderAdapter.ViewHolder.class)
public class TransportOrderAdapter extends AbsHolderAdapter<Order> {

	public TransportOrderAdapter(Context context) {
		super(context);
	}

	public static class ViewHolder {

	}

	@Override
	public void bindValue(int position, View convertView, Order t) {

	}

}
