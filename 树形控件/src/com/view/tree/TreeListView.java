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
 * @see ��Դʱ�䣺2016��03��31��
 * 
 *      �ǵø��Ҹ�starŶ~
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
	// ���з���

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
	 * ע�ᣨ�飩���������¼�
	 * 
	 * @param listener
	 */
	public void setOnGroupItemClickListener(OnGroupItemClickListener listener) {
		mGroupItemClickListener = listener;
	}

	/**
	 * ע�ᣨ���ڳ�Ա�����������¼�
	 * 
	 * @param listener
	 */
	public void setOnChildItemClickListener(OnChildItemClickListener listener) {
		mChildItemClickListener = listener;
	}

	/**
	 * ע�ᵥ��չ�������¼�
	 * 
	 * @param listener
	 */
	public void setOnExpandClickListener(OnExpandClickListener listener) {
		mExpandClickListener = listener;
	}

	/**
	 * ע�ᵥ����������¼�
	 * 
	 * @param listener
	 */
	public void setOnCollapseClickListener(OnCollapseClickListener listener) {
		mCollapseClickListener = listener;
	}

	/**
	 * ע��չ�������¼�
	 * 
	 * @param listener
	 */
	public void setOnExpandListener(OnExpandListener listener) {
		mListAdapter.setOnExpandListener(listener);
	}

	/**
	 * ע����������¼�
	 * 
	 * @param listener
	 */
	public void setOnCollapseListener(OnCollapseListener listener) {
		mListAdapter.setOnCollapseListener(listener);
	}

	/**
	 * ע��ѡ������¼�
	 * 
	 * @param listener
	 */
	public void setOnSelectListener(OnSelectListener listener) {
		mListAdapter.setOnSelectListener(listener);
	}

	// ******************************************************************************
	// ˽�з���

	/**
	 * ע���¼�
	 */
	private void registerListener() {
		setOnItemClickListener(new ListItemClickListener());
	}

	// ******************************************************************************
	// �Զ������

	/**
	 * �б���ĵ����¼���
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
	 * �б������ĵ����¼���
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnGroupItemClickListener {
		public void onGroupClick(AdapterView<?> parent, View view,
				int position, TreeNode node);
	}

	/**
	 * �б�������ڳ�Ա�ĵ����¼���
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnChildItemClickListener {
		public void onChildClick(AdapterView<?> parent, View view,
				int position, TreeNode node);
	}

	/**
	 * �б������ĵ���չ���¼���
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnExpandClickListener {
		public void onExpandClick(AdapterView<?> parent, View view,
				int position, TreeNode node);
	}

	/**
	 * �б������ĵ��������¼���
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnCollapseClickListener {
		public void onCollapseClick(AdapterView<?> parent, View view,
				int position, TreeNode node);
	}

	/**
	 * �б�������չ���¼���
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnExpandListener {
		public void onExpand(TreeNode node);
	}

	/**
	 * �б������������¼���
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnCollapseListener {
		public void onCollapse(TreeNode node);
	}

	/**
	 * �б����ѡ���¼���
	 * 
	 * @author tangjiabing
	 * 
	 */
	public interface OnSelectListener {
		public void onSelect(TreeNode node);
	}

}
