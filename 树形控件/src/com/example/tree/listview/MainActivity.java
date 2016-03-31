package com.example.tree.listview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.view.tree.TreeBaseAdapter;
import com.view.tree.TreeListView;
import com.view.tree.TreeListView.OnChildItemClickListener;
import com.view.tree.TreeListView.OnCollapseClickListener;
import com.view.tree.TreeListView.OnCollapseListener;
import com.view.tree.TreeListView.OnExpandClickListener;
import com.view.tree.TreeListView.OnExpandListener;
import com.view.tree.TreeListView.OnGroupItemClickListener;
import com.view.tree.TreeListView.OnSelectListener;
import com.view.tree.TreeNode;

public class MainActivity extends Activity {

	private TreeListView mTreeListView = null;
	private MyTreeBaseAdapter mTreeListAdapter = null;
	private ArrayList<UserBean> mDataList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findView();
		init();
		registerListener();
	}

	// ********************************************************************
	// findView，init，registerListener

	private void findView() {
		mTreeListView = (TreeListView) findViewById(R.id.treeListView);
	}

	private void init() {
		initDataList();
		mTreeListAdapter = new MyTreeBaseAdapter(MainActivity.this, mDataList);
		mTreeListView.setAdapter(mTreeListAdapter);
		TreeMethod();
	}

	private void registerListener() {
		// mTreeListView.setOnGroupItemClickListener(new
		// GroupItemClickListener());
		// mTreeListView.setOnChildItemClickListener(new
		// ChildItemClickListener());
		// mTreeListView.setOnExpandClickListener(new ExpandClickListener());
		// mTreeListView.setOnCollapseClickListener(new
		// CollapseClickListener());
		mTreeListView.setOnExpandListener(new ExpandListener());
		mTreeListView.setOnCollapseListener(new CollapseListener());
		mTreeListView.setOnSelectListener(new SelectListener());
	}

	// ********************************************************************
	// 私有方法

	private void initDataList() {
		mDataList = new ArrayList<UserBean>();

		// ////////////////////////////
		// 根组
		UserBean bean = new UserBean(0x01, TreeNode.ROOT_PARENT_ID, true, "第1组");
		mDataList.add(bean);
		bean = new UserBean(0x02, TreeNode.ROOT_PARENT_ID, true, "第2组");
		mDataList.add(bean);
		bean = new UserBean(0x03, TreeNode.ROOT_PARENT_ID, true, "第3组");
		mDataList.add(bean);
		bean = new UserBean(0x04, TreeNode.ROOT_PARENT_ID, true, "第4组");
		mDataList.add(bean);
		bean = new UserBean(0x05, TreeNode.ROOT_PARENT_ID, true, "第5组");
		mDataList.add(bean);
		bean = new UserBean(0x06, TreeNode.ROOT_PARENT_ID, true, "第6组");
		mDataList.add(bean);

		// ////////////////////////////
		// 第1组
		bean = new UserBean(0x11, 0x01, true, "1组_1");
		mDataList.add(bean);
		bean = new UserBean(0x12, 0x01, true, "1组_2");
		mDataList.add(bean);

		// ////////////////////////////
		// 1组_1
		bean = new UserBean(0x111, 0x11, false, "卡卡西");
		mDataList.add(bean);
		bean = new UserBean(0x112, 0x11, false, "白牙");
		mDataList.add(bean);
		bean = new UserBean(0x113, 0x11, true, "1组_1_1");
		mDataList.add(bean);

		// ////////////////////////////
		// 1组_1_1
		bean = new UserBean(0x1131, 0x113, false, "九尾妖狐");
		mDataList.add(bean);

		// ////////////////////////////
		// 1组_2
		bean = new UserBean(0x1211, 0x12, false, "波风水门");
		mDataList.add(bean);

		// ////////////////////////////
		// 第2组
		bean = new UserBean(0x21, 0x02, true, "2组_1");
		mDataList.add(bean);
		bean = new UserBean(0x22, 0x02, false, "鸣人");
		mDataList.add(bean);
		bean = new UserBean(0x23, 0x02, false, "佐助");
		mDataList.add(bean);

		// ////////////////////////////
		// 2组_1
		bean = new UserBean(0x211, 0x21, false, "我爱罗");
		mDataList.add(bean);

		// ////////////////////////////
		// 第3组
		bean = new UserBean(0x31, 0x03, false, "大蛇丸");
		mDataList.add(bean);
		bean = new UserBean(0x32, 0x03, false, "自来也");
		mDataList.add(bean);
		bean = new UserBean(0x33, 0x03, false, "纲手");
		mDataList.add(bean);

		// ////////////////////////////
		// 第5组
		bean = new UserBean(0x51, 0x05, true, "5组_1");
		mDataList.add(bean);
		bean = new UserBean(0x52, 0x05, false, "君麻吕");
		mDataList.add(bean);

		// ////////////////////////////
		// 5组_1
		bean = new UserBean(0x511, 0x51, true, "5组_1_1");
		mDataList.add(bean);
		bean = new UserBean(0x512, 0x51, false, "春野樱");
		mDataList.add(bean);

		// ////////////////////////////
		// 5组_1_1
		bean = new UserBean(0x5111, 0x511, true, "5组_1_1_1");
		mDataList.add(bean);
		bean = new UserBean(0x5112, 0x511, false, "鹿丸");
		mDataList.add(bean);

		// ////////////////////////////
		// 5组_1_1_1
		bean = new UserBean(0x51111, 0x5111, true, "5组_1_1_1_1");
		mDataList.add(bean);
		bean = new UserBean(0x51112, 0x5111, false, "柱间");
		mDataList.add(bean);

		// ////////////////////////////
		// 5组_1_1_1_1
		bean = new UserBean(0x511111, 0x51111, false, "宇智波鼬");
		mDataList.add(bean);
		bean = new UserBean(0x511112, 0x51111, false, "迪达拉");
		mDataList.add(bean);
		bean = new UserBean(0x511113, 0x51111, false, "佩恩");
		mDataList.add(bean);
		bean = new UserBean(0x511114, 0x51111, false, "阿飞");
		mDataList.add(bean);
		bean = new UserBean(0x511115, 0x51111, false, "黑绝");
		mDataList.add(bean);

	}

	private void TreeMethod() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// ////////////////////////////
				// 5组_1_1_2
				UserBean bean = new UserBean(0x51121, 0x5111, true,
						"5组_1_1_2_1");
				mDataList.add(bean);
				bean = new UserBean(0x51122, 0x5111, false, "六道仙人");
				mDataList.add(bean);

				notifyDataSetChanged(mDataList);
			}
		}, 6000);
	}

	private void expand(int groupId) {
		mTreeListAdapter.expand(groupId);
	}

	private void collapse(int groupId) {
		mTreeListAdapter.collapse(groupId);
	}

	private void expandAll() {
		mTreeListAdapter.expandAll();
	}

	private void collapseAll() {
		mTreeListAdapter.collapseAll();
	}

	private void selectAll(boolean flag) {
		mTreeListAdapter.selectAll(flag);
	}

	private void notifyDataSetChanged(ArrayList<UserBean> dataList) {
		mTreeListAdapter.notifyDataSetChanged(dataList);
	}

	private void toast(String text) {
		// Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		Log.i("My", text);
	}

	// ********************************************************************
	// 自定义的类

	private class MyTreeBaseAdapter<T> extends TreeBaseAdapter<T> {

		private LayoutInflater inflater = null;

		public MyTreeBaseAdapter(Context context, List<T> dataList) {
			super(dataList, 30);
			inflater = LayoutInflater.from(context);
		}

		private class ViewHolder {
			public ImageView imageView = null;
			public TextView textView = null;
			public CheckBox checkBox = null;
		}

		@Override
		public View getConvertView(final TreeNode node, int position,
				View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater
						.inflate(R.layout.treelistview_item, null);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.imageView);
				holder.textView = (TextView) convertView
						.findViewById(R.id.textView);
				holder.checkBox = (CheckBox) convertView
						.findViewById(R.id.checkBox);
				convertView.setTag(holder);
			} else
				holder = (ViewHolder) convertView.getTag();

			boolean isGroup = node.isGroup();
			UserBean bean = (UserBean) node.getSelfData();
			if (isGroup == true) {
				holder.imageView.setImageResource(R.drawable.ic_launcher);
				holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 22);
				holder.textView.setTextColor(Color.rgb(0, 0, 0xb0));
			} else {
				holder.imageView.setImageBitmap(null);
				holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);
				holder.textView.setTextColor(Color.rgb(0, 0, 0));
			}
			holder.textView.setText(bean.getName());

			holder.checkBox.setChecked(node.isSelected());
			holder.checkBox.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mTreeListAdapter.select(node.getId(), !node.isSelected());
				}
			});

			return convertView;
		}

	}

	private class GroupItemClickListener implements OnGroupItemClickListener {
		@Override
		public void onGroupClick(AdapterView<?> parent, View view,
				int position, TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("【组】：" + bean.getName());
		}

	}

	private class ChildItemClickListener implements OnChildItemClickListener {
		@Override
		public void onChildClick(AdapterView<?> parent, View view,
				int position, TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("【非组】：" + bean.getName());
		}
	}

	private class ExpandClickListener implements OnExpandClickListener {
		@Override
		public void onExpandClick(AdapterView<?> parent, View view,
				int position, TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("单击【展开】：" + bean.getName());
		}
	}

	private class CollapseClickListener implements OnCollapseClickListener {
		@Override
		public void onCollapseClick(AdapterView<?> parent, View view,
				int position, TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("单击【收起】：" + bean.getName());
		}
	}

	private class ExpandListener implements OnExpandListener {
		@Override
		public void onExpand(TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("【展开】" + bean.getName());
		}
	}

	private class CollapseListener implements OnCollapseListener {
		@Override
		public void onCollapse(TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("【收起】" + bean.getName());
		}
	}

	private class SelectListener implements OnSelectListener {
		@Override
		public void onSelect(TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("【选择】" + bean.getName() + "  " + node.isSelected());
		}
	}

}
