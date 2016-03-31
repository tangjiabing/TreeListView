package com.view.tree;

import java.util.List;

/**
 * 
 * @author tangjiabing
 * 
 * @see 开源时间：2016年03月31日
 * 
 *      记得给我个star哦~
 * 
 */
public class TreeNode<T> {

	public static final int ROOT_PARENT_ID = -1; // 根节点的父id
	private int id = 0; // 树节点的id
	private int parentId = 0; // 树节点的父id
	private int level = 0; // 树节点的级别，从0开始
	private boolean isGroup = false; // 判断该树节点是否有子孙节点
	private boolean isExpanded = false; // 判断该树节点是否已展开，要有子孙节点才能展开
	private boolean isSelected = false; // 判断该树节点是否已选中
	private T selfData = null; // 该树节点的数据
	private List<TreeNode> childNodeList = null; // 该树节点的子孙节点

	public TreeNode(int id, int pId, boolean isGroup, T t) {
		this.id = id;
		parentId = pId;
		this.isGroup = isGroup;
		selfData = t;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public T getSelfData() {
		return selfData;
	}

	public void setSelfData(T selfData) {
		this.selfData = selfData;
	}

	public List<TreeNode> getChildNodeList() {
		return childNodeList;
	}

	public void setChildNodeList(List<TreeNode> childNodeList) {
		this.childNodeList = childNodeList;
	}

}
