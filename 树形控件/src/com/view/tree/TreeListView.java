package com.view.tree;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 
 * @author tangjiabing
 * 
 * @see 开源时间：2016年03月31日
 * 
 *      记得给我个star哦~
 * 
 */
public class TreeListView extends ListView {

	private TreeBaseAdapter mListAdapter;
	private ListItemClickListener mItemClickListener;
	private OnGroupItemClickListener mGroupItemClickListener;
	private OnChildItemClickListener mChildItemClickListener;
	private OnExpandClickListener mExpandClickListener;
	private OnCollapseClickListener mCollapseClickListener;

	public TreeListView(Context context) {
		this(context, null);
	}

	public TreeListView(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.listViewStyle);
	}

	public TreeListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		registerListener();
	}

	// ******************************************************************************
	// 公有方法

	@Override
	public void setAdapter(ListAdapter adapter) {
		if (adapter instanceof TreeBaseAdapter) {
			super.setAdapter(adapter);
			mListAdapter = (TreeBaseAdapter) adapter;
		} else
			throw new IllegalArgumentException(
					"the adapter must is TreeBaseAdapter");
	}

	@Override
	public void setOnItemClickListener(OnItemClickListener listener) {
		if (listener instanceof ListItemClickListener) {
			super.setOnItemClickListener(listener);
			mItemClickListener = (ListItemClickListener) listener;
		} else
			throw new IllegalArgumentException(
					"the listener must is ListItemClickListener of TreeListView");
	}

	/**
	 * 注册（组）单击监听事件
	 * 
	 * @param listener
	 */
	public void setOnGroupItemClickListener(OnGroupItemClickListener listener) {
		mGroupItemClickListener = listener;
	}

	/**
	 * 注册（组内成员）单击监听事件
	 * 
	 * @param listener
	 */
	public void setOnChildItemClickListener(OnChildItemClickListener listener) {
		mChildItemClickListener = listener;
	}

	/**
	 * 注册单击展开监听事件
	 * 
	 * @param listener
	 */
	public void setOnExpandClickListener(OnExpandClickListener listener) {
		mExpandClickListener = listener;
	}

	/**
	 * 注册单击收起监听事件
	 * 
	 * @param listener
	 */
	public void setOnCollapseClickListener(OnCollapseClickListener listener) {
		mCollapseClickListener = listener;
	}

	/**
	 * 注册展开监听事件
	 * 
	 * @param listener
	 */
	public void setOnExpandListener(OnExpandListener listener) {
		mListAdapter.setOnExpandListener(listener);
	}

	/**
	 * 注册收起监听事件
	 * 
	 * @param listener
	 */
	public void setOnCollapseListener(OnCollapseListener listener) {
		mListAdapter.setOnCollapseListener(listener);
	}

	/**
	 * 注册选择监听事件
	 * 
	 * @param listener
	 */
	public void setOnSelectListener(OnSelectListener listener) {
		mListAdapter.setOnSelectListener(listener);
	}

	// ******************************************************************************
	// 私有方法

	/**
	 * 注册事件
	 */
	private void registerListener() {
		setOnItemClickListener(new ListItemClickListener());
	}

	// ******************************************************************************
	// 自定义的类

	/**
	 * 列表项的单击事件类
	 * 
	 * @author tangjiabing
	 * 
	 */
	private class ListItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			int headerViewCount = getHeaderViewsCount();
			if (position < headerViewCount)
				return;
			position = position - headerViewCount;
			if (position >= mListAdapter.getCount())
				return;

			TreeNode node = mListAdapter.getNodeOfItem(position);
			int nId = node.getId();
			boolean isGroup = node.isGroup();
			boolean isExpanded = node.isExpanded();
			if (isGroup) {
				if (mGroupItemClickListener != null)
					mGroupItemClickListener.onGroupClick(parent, view,
							position, node);
				if (isExpanded == false) {
					mListAdapter.expand(nId);
					if (mExpandClickListener != null)
						mExpandClickListener.onExpandClick(parent, view,
								position, node);
				} else {
					mListAdapter.collapse(nId);
					if (mCollapseClickListener != null)
						mCollapseClickListener.onCollapseClick(parent, view,
								position, node);
				}
			} else {
				if (mChildItemClickListener != null)
					mChildItemClickListener.onChildClick(parent, view,
							position, node);
			}
		}
	}

	/**
	 * 列表项的组的单击事件类
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnGroupItemClickListener {
		public void onGroupClick(AdapterView<?> parent, View view,
				int position, TreeNode node);
	}

	/**
	 * 列表项的组内成员的单击事件类
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnChildItemClickListener {
		public void onChildClick(AdapterView<?> parent, View view,
				int position, TreeNode node);
	}

	/**
	 * 列表项的组的单击展开事件类
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnExpandClickListener {
		public void onExpandClick(AdapterView<?> parent, View view,
				int position, TreeNode node);
	}

	/**
	 * 列表项的组的单击收起事件类
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnCollapseClickListener {
		public void onCollapseClick(AdapterView<?> parent, View view,
				int position, TreeNode node);
	}

	/**
	 * 列表项的组的展开事件类
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnExpandListener {
		public void onExpand(TreeNode node);
	}

	/**
	 * 列表项的组的收起事件类
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnCollapseListener {
		public void onCollapse(TreeNode node);
	}

	/**
	 * 列表项的选择事件类
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnSelectListener {
		public void onSelect(TreeNode node);
	}

}
