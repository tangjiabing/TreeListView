package com.view.tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.view.tree.TreeListView.OnCollapseListener;
import com.view.tree.TreeListView.OnExpandListener;
import com.view.tree.TreeListView.OnSelectListener;

/**
 * 
 * @author tangjiabing
 * 
 * @see 开源时间：2016年03月31日
 * 
 *      记得给我个star哦~
 * 
 */
public abstract class TreeBaseAdapter<T> extends BaseAdapter {

	private int mPaddingConvertViewLeft;
	private List<TreeNode> mTreeNodeList;
	private List<TreeNode> mVisibleNodeList;

	private OnExpandListener mExpandListener;
	private OnCollapseListener mCollapseListener;
	private OnSelectListener mSelectListener;

	public TreeBaseAdapter(List<T> dataList, int paddingConvertViewLeft) {
		mPaddingConvertViewLeft = paddingConvertViewLeft;
		mVisibleNodeList = new ArrayList<TreeNode>();
		setNodeListOfClass(dataList);
	}

	// **********************************************************************************
	// 公有方法

	@Override
	public int getCount() {
		return mVisibleNodeList.size();
	}

	@Override
	public Object getItem(int position) {
		return mVisibleNodeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TreeNode node = mVisibleNodeList.get(position);
		convertView = getConvertView(node, position, convertView, parent);
		int paddingRight = convertView.getPaddingRight();
		int paddingTop = convertView.getPaddingTop();
		int paddingBottom = convertView.getPaddingBottom();
		convertView.setPadding(node.getLevel() * mPaddingConvertViewLeft,
				paddingTop, paddingRight, paddingBottom);
		return convertView;
	}

	/**
	 * 设置并返回列表项的convertView
	 * 
	 * @param node
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract View getConvertView(TreeNode node, int position,
			View convertView, ViewGroup parent);

	/**
	 * 获取指定列表项的树节点对象
	 * 
	 * @param position
	 *            列表项的序号
	 * @return 树节点对象
	 */
	public TreeNode getNodeOfItem(int position) {
		return mVisibleNodeList.get(position);
	}

	/**
	 * 更新列表项
	 * 
	 * @param dataList
	 *            数据
	 */
	public void notifyDataSetChanged(List<T> dataList) {
		setNodeListOfClass(dataList);
		notifyDataSetChanged();
	}

	/**
	 * 展开指定id的组
	 * 
	 * @param id
	 *            组Id
	 * @return true为展开成功，false为失败
	 */
	public boolean expand(int id) {
		boolean result = false;
		for (TreeNode node : mTreeNodeList) {
			List<TreeNode> resultList = expand(node, id);
			if (resultList.size() > 0) {
				result = true;
				setVisibleNodeList();
				notifyDataSetChanged();
				break;
			}
		}
		return result;
	}

	/**
	 * 收起指定id的组
	 * 
	 * @param id
	 *            组Id
	 * @return true为收起成功，false为失败
	 */
	public boolean collapse(int id) {
		boolean result = false;
		for (TreeNode node : mTreeNodeList) {
			result = collapse(node, id);
			if (result == true) {
				setVisibleNodeList();
				notifyDataSetChanged();
				break;
			}
		}
		return result;
	}

	/**
	 * 展开所有的组
	 */
	public void expandAll() {
		for (TreeNode node : mTreeNodeList)
			expand(node);
		setVisibleNodeList();
		notifyDataSetChanged();
	}

	/**
	 * 收起所有的组
	 */
	public void collapseAll() {
		for (TreeNode node : mTreeNodeList)
			collapse(node);
		setVisibleNodeList();
		notifyDataSetChanged();
	}

	/**
	 * 选中指定id的树节点，包括它的子孙节点一起选中
	 * 
	 * @param id
	 *            树节点id
	 * @param isSelected
	 *            true为选中，false为不选中
	 */
	public void select(int id, boolean isSelected) {
		for (TreeNode node : mTreeNodeList) {
			boolean flag = select(node, id, isSelected);
			if (flag == true) {
				notifyDataSetChanged();
				break;
			}
		}
	}

	/**
	 * 选中所有的树节点
	 * 
	 * @param isSelected
	 *            true为选中，false为不选中
	 */
	public void selectAll(boolean isSelected) {
		for (TreeNode node : mTreeNodeList)
			select(node, isSelected);
		notifyDataSetChanged();
	}

	// **********************************************************************************
	// 保护方法

	/**
	 * 注册展开监听事件，在TreeListView中注册了
	 * 
	 * @param listener
	 */
	protected void setOnExpandListener(OnExpandListener listener) {
		mExpandListener = listener;
	}

	/**
	 * 注册收起监听事件，在TreeListView中注册了
	 * 
	 * @param listener
	 */
	protected void setOnCollapseListener(OnCollapseListener listener) {
		mCollapseListener = listener;
	}

	/**
	 * 注册选择监听事件，在TreeListView中注册了
	 * 
	 * @param listener
	 */
	protected void setOnSelectListener(OnSelectListener listener) {
		mSelectListener = listener;
	}

	// **********************************************************************************
	// 私有方法

	/**
	 * 设置全部树节点和可见树节点
	 * 
	 * @param dataList
	 */
	private void setNodeListOfClass(List<T> dataList) {
		mTreeNodeList = convertTreeNode(dataList);
		setVisibleNodeList();
	}

	/**
	 * 设置可见树节点
	 */
	private void setVisibleNodeList() {
		mVisibleNodeList.clear();
		List<TreeNode> visibleList = getVisibleNoteList(mTreeNodeList);
		for (TreeNode node : visibleList)
			mVisibleNodeList.add(node);
	}

	/**
	 * 把数据转换成有序的树节点
	 * 
	 * @param dataList
	 *            数据
	 * @return 有序的树节点
	 */
	private List<TreeNode> convertTreeNode(List<T> dataList) {
		try {

			List<TreeNode> nodeList = new ArrayList<TreeNode>();
			for (T t : dataList) {
				boolean isGetId = false;
				boolean isGetPid = false;
				boolean isGetGroup = false;
				int id = -1;
				int pId = -1;
				boolean isGroup = false;
				Class<? extends Object> clazz = t.getClass();
				Field[] declaredFields = clazz.getDeclaredFields();
				for (Field f : declaredFields) {
					if (f.getAnnotation(TreeNodeId.class) != null) {
						// java代码中，常常将一个类的成员变量设置为private，在类的外面获取它的私有成员变量时，需使用setAccessible方法。
						// setAccessible(true)并不是将访问权限改成了public，而是取消java的权限控制检查。
						// 所以即使是public，其accessible属性默认也是false。
						f.setAccessible(true);
						id = f.getInt(t);
						isGetId = true;
					}
					if (f.getAnnotation(TreeNodePid.class) != null) {
						f.setAccessible(true);
						pId = f.getInt(t);
						isGetPid = true;
					}
					if (f.getAnnotation(TreeNodeGroup.class) != null) {
						f.setAccessible(true);
						isGroup = f.getBoolean(t);
						isGetGroup = true;
					}
					if (isGetId == true && isGetPid == true
							&& isGetGroup == true) {
						TreeNode node = new TreeNode<T>(id, pId, isGroup, t);
						nodeList.add(node);
						break;
					}
				}
			}
			List<TreeNode> rootList = getRootNodeList(nodeList);
			List<TreeNode> treeList = sortLevelOfTreeNode(rootList, nodeList);
			return treeList;

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<TreeNode>();
		}
	}

	/**
	 * 获取树的根节点
	 * 
	 * @param nodeList
	 *            杂乱的树节点
	 * @return
	 */
	private List<TreeNode> getRootNodeList(List<TreeNode> nodeList) {
		List<TreeNode> rootList = new ArrayList<TreeNode>();
		for (TreeNode node : nodeList) {
			int parentId = node.getParentId();
			if (parentId == TreeNode.ROOT_PARENT_ID)
				rootList.add(node);
		}
		return rootList;
	}

	/**
	 * 利用递归方法，把杂乱的树节点排序成有序的树节点
	 * 
	 * @param parentList
	 *            父树节点
	 * @param nodeList
	 *            杂乱的树节点
	 * @return
	 */
	private List<TreeNode> sortLevelOfTreeNode(List<TreeNode> parentList,
			List<TreeNode> nodeList) {
		for (TreeNode parentNode : parentList) {
			int id = parentNode.getId();
			int level = parentNode.getLevel();
			nodeList.remove(parentNode);
			List<TreeNode> childNodeList = null;
			for (TreeNode node : nodeList) {
				int pId = node.getParentId();
				if (pId == id) {
					childNodeList = parentNode.getChildNodeList();
					if (childNodeList == null) {
						childNodeList = new ArrayList<TreeNode>();
						parentNode.setChildNodeList(childNodeList);
					}
					node.setLevel(level + 1);
					childNodeList.add(node);
				}
			}
			if (childNodeList != null)
				childNodeList = sortLevelOfTreeNode(childNodeList, nodeList);
		}
		return parentList;
	}

	/**
	 * 获取可见的树节点
	 * 
	 * @param treeList
	 *            有序的树节点
	 * @return
	 */
	private List<TreeNode> getVisibleNoteList(List<TreeNode> treeList) {
		List<TreeNode> visibleList = new ArrayList<TreeNode>();
		for (TreeNode node : treeList) {
			visibleList.add(node);
			List<TreeNode> sonList = filterVisibleNodeList(node);
			visibleList.addAll(sonList);
		}
		return visibleList;
	}

	/**
	 * 过滤出可见的树节点
	 * 
	 * @param node
	 * @return
	 */
	private List<TreeNode> filterVisibleNodeList(TreeNode node) {
		List<TreeNode> visibleList = new ArrayList<TreeNode>();
		if (node.isExpanded()) {
			List<TreeNode> childList = node.getChildNodeList();
			for (TreeNode child : childList) {
				visibleList.add(child);
				List<TreeNode> grandsonList = filterVisibleNodeList(child);
				visibleList.addAll(grandsonList);
			}
		}
		return visibleList;
	}

	/**
	 * 展开指定id的组
	 * 
	 * @param node
	 * @param id
	 * @return
	 */
	private List<TreeNode> expand(TreeNode node, int id) {
		List<TreeNode> resultList = new ArrayList<TreeNode>();
		List<TreeNode> childList = node.getChildNodeList();
		if (childList != null) {
			if (node.getId() == id) {
				node.setExpanded(true);
				expandListener(node);
				resultList.addAll(childList);
			} else {
				for (TreeNode child : childList) {
					List<TreeNode> tempList = expand(child, id);
					if (tempList.size() > 0) {
						if (node.isExpanded() == false) {
							node.setExpanded(true);
							expandListener(node);
						}
						resultList.addAll(childList);
						break;
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * 展开树节点
	 * 
	 * @param node
	 */
	private void expand(TreeNode node) {
		List<TreeNode> childList = node.getChildNodeList();
		if (childList != null) {
			node.setExpanded(true);
			expandListener(node);
			for (TreeNode child : childList)
				expand(child);
		}
	}

	/**
	 * 收起指定id的组
	 * 
	 * @param node
	 * @param id
	 * @return
	 */
	private boolean collapse(TreeNode node, int id) {
		boolean result = false;
		if (node.isExpanded()) {
			List<TreeNode> childList = node.getChildNodeList();
			if (node.getId() == id) {
				collapse(node);
				result = true;
			} else {
				for (TreeNode child : childList) {
					result = collapse(child, id);
					if (result == true)
						break;
				}
			}
		}
		return result;
	}

	/**
	 * 收起树节点
	 * 
	 * @param node
	 */
	private void collapse(TreeNode node) {
		if (node.isExpanded()) {
			node.setExpanded(false);
			collapseListener(node);
			List<TreeNode> childList = node.getChildNodeList();
			for (TreeNode child : childList)
				collapse(child);
		}
	}

	/**
	 * 选中指定id的树节点，包括它的子孙节点一起选中
	 * 
	 * @param node
	 * @param id
	 * @param isSelected
	 *            true为选中，false为不选中
	 * @return
	 */
	private boolean select(TreeNode node, int id, boolean isSelected) {
		boolean result = false;
		List<TreeNode> childList = node.getChildNodeList();
		if (node.getId() == id) {
			select(node, isSelected);
			result = true;
		} else {
			if (childList != null) {
				for (TreeNode child : childList) {
					result = select(child, id, isSelected);
					if (result == true)
						break;
				}
			}
		}
		return result;
	}

	/**
	 * 选中树节点，包括它的子孙节点一起选中
	 * 
	 * @param node
	 * @param isSelected
	 *            true为选中，false为不选中
	 */
	private void select(TreeNode node, boolean isSelected) {
		if (node.isSelected() != isSelected) {
			node.setSelected(isSelected);
			selectListener(node);
		}
		List<TreeNode> childList = node.getChildNodeList();
		if (childList != null) {
			for (TreeNode child : childList)
				select(child, isSelected);
		}
	}

	/**
	 * 回调展开监听方法
	 * 
	 * @param node
	 */
	private void expandListener(TreeNode node) {
		if (mExpandListener != null)
			mExpandListener.onExpand(node);
	}

	/**
	 * 回调收起监听方法
	 * 
	 * @param node
	 */
	private void collapseListener(TreeNode node) {
		if (mCollapseListener != null)
			mCollapseListener.onCollapse(node);
	}

	/**
	 * 回调选中监听方法
	 * 
	 * @param node
	 */
	private void selectListener(TreeNode node) {
		if (mSelectListener != null)
			mSelectListener.onSelect(node);
	}

}
