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
	// findView��init��registerListener

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
	// ˽�з���

	private void initDataList() {
		mDataList = new ArrayList<UserBean>();

		// ////////////////////////////
		// ����
		UserBean bean = new UserBean(0x01, TreeNode.ROOT_PARENT_ID, true, "��1��");
		mDataList.add(bean);
		bean = new UserBean(0x02, TreeNode.ROOT_PARENT_ID, true, "��2��");
		mDataList.add(bean);
		bean = new UserBean(0x03, TreeNode.ROOT_PARENT_ID, true, "��3��");
		mDataList.add(bean);
		bean = new UserBean(0x04, TreeNode.ROOT_PARENT_ID, true, "��4��");
		mDataList.add(bean);
		bean = new UserBean(0x05, TreeNode.ROOT_PARENT_ID, true, "��5��");
		mDataList.add(bean);
		bean = new UserBean(0x06, TreeNode.ROOT_PARENT_ID, true, "��6��");
		mDataList.add(bean);

		// ////////////////////////////
		// ��1��
		bean = new UserBean(0x11, 0x01, true, "1��_1");
		mDataList.add(bean);
		bean = new UserBean(0x12, 0x01, true, "1��_2");
		mDataList.add(bean);

		// ////////////////////////////
		// 1��_1
		bean = new UserBean(0x111, 0x11, false, "������");
		mDataList.add(bean);
		bean = new UserBean(0x112, 0x11, false, "����");
		mDataList.add(bean);
		bean = new UserBean(0x113, 0x11, true, "1��_1_1");
		mDataList.add(bean);

		// ////////////////////////////
		// 1��_1_1
		bean = new UserBean(0x1131, 0x113, false, "��β����");
		mDataList.add(bean);

		// ////////////////////////////
		// 1��_2
		bean = new UserBean(0x1211, 0x12, false, "����ˮ��");
		mDataList.add(bean);

		// ////////////////////////////
		// ��2��
		bean = new UserBean(0x21, 0x02, true, "2��_1");
		mDataList.add(bean);
		bean = new UserBean(0x22, 0x02, false, "����");
		mDataList.add(bean);
		bean = new UserBean(0x23, 0x02, false, "����");
		mDataList.add(bean);

		// ////////////////////////////
		// 2��_1
		bean = new UserBean(0x211, 0x21, false, "�Ұ���");
		mDataList.add(bean);

		// ////////////////////////////
		// ��3��
		bean = new UserBean(0x31, 0x03, false, "������");
		mDataList.add(bean);
		bean = new UserBean(0x32, 0x03, false, "����Ҳ");
		mDataList.add(bean);
		bean = new UserBean(0x33, 0x03, false, "����");
		mDataList.add(bean);

		// ////////////////////////////
		// ��5��
		bean = new UserBean(0x51, 0x05, true, "5��_1");
		mDataList.add(bean);
		bean = new UserBean(0x52, 0x05, false, "������");
		mDataList.add(bean);

		// ////////////////////////////
		// 5��_1
		bean = new UserBean(0x511, 0x51, true, "5��_1_1");
		mDataList.add(bean);
		bean = new UserBean(0x512, 0x51, false, "��Ұӣ");
		mDataList.add(bean);

		// ////////////////////////////
		// 5��_1_1
		bean = new UserBean(0x5111, 0x511, true, "5��_1_1_1");
		mDataList.add(bean);
		bean = new UserBean(0x5112, 0x511, false, "¹��");
		mDataList.add(bean);

		// ////////////////////////////
		// 5��_1_1_1
		bean = new UserBean(0x51111, 0x5111, true, "5��_1_1_1_1");
		mDataList.add(bean);
		bean = new UserBean(0x51112, 0x5111, false, "����");
		mDataList.add(bean);

		// ////////////////////////////
		// 5��_1_1_1_1
		bean = new UserBean(0x511111, 0x51111, false, "���ǲ���");
		mDataList.add(bean);
		bean = new UserBean(0x511112, 0x51111, false, "�ϴ���");
		mDataList.add(bean);
		bean = new UserBean(0x511113, 0x51111, false, "���");
		mDataList.add(bean);
		bean = new UserBean(0x511114, 0x51111, false, "����");
		mDataList.add(bean);
		bean = new UserBean(0x511115, 0x51111, false, "�ھ�");
		mDataList.add(bean);

	}

	private void TreeMethod() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// ////////////////////////////
				// 5��_1_1_2
				UserBean bean = new UserBean(0x51121, 0x5111, true,
						"5��_1_1_2_1");
				mDataList.add(bean);
				bean = new UserBean(0x51122, 0x5111, false, "��������");
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
	// �Զ������

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
			toast("���顿��" + bean.getName());
		}

	}

	private class ChildItemClickListener implements OnChildItemClickListener {
		@Override
		public void onChildClick(AdapterView<?> parent, View view,
				int position, TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("�����顿��" + bean.getName());
		}
	}

	private class ExpandClickListener implements OnExpandClickListener {
		@Override
		public void onExpandClick(AdapterView<?> parent, View view,
				int position, TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("������չ������" + bean.getName());
		}
	}

	private class CollapseClickListener implements OnCollapseClickListener {
		@Override
		public void onCollapseClick(AdapterView<?> parent, View view,
				int position, TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("���������𡿣�" + bean.getName());
		}
	}

	private class ExpandListener implements OnExpandListener {
		@Override
		public void onExpand(TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("��չ����" + bean.getName());
		}
	}

	private class CollapseListener implements OnCollapseListener {
		@Override
		public void onCollapse(TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("������" + bean.getName());
		}
	}

	private class SelectListener implements OnSelectListener {
		@Override
		public void onSelect(TreeNode node) {
			UserBean bean = (UserBean) node.getSelfData();
			toast("��ѡ��" + bean.getName() + "  " + node.isSelected());
		}
	}

}
