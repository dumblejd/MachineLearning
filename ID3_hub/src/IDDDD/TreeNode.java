package IDDDD;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private String name; // �ڵ�����
	private TreeNode parent; // ���ڵ�
	private List<TreeNode> children; // �ӽڵ�
	private String fatherAttribute; // �˽ڵ��Ǹ�����ľ����Եķ�֧
	//���Ŷ�
	private Double percent;
	
	//��������
	private ArrayList<String> liatts;
	
   
	public ArrayList<String> getLiatts() {
		return liatts;
	}
	public void setLiatts(ArrayList<String> liatts) {
		this.liatts = liatts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TreeNode getParent() {
		return parent;
	}
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	public String getFatherAttribute() {
		return fatherAttribute;
	}
	public void setFatherAttribute(String fatherAttribute) {
		this.fatherAttribute = fatherAttribute;
	}
	public Double getPercent() {
		return percent;
	}
	public void setPercent(Double percent) {
		this.percent = percent;
	}
	/**
     * ���һ���ڵ�
     * @param child
     */
    public void addChild(TreeNode child) {
        if (this.getChildren() == null) {
            List<TreeNode> list = new ArrayList<TreeNode>();
            list.add(child);
            this.setChildren(list);
        } else {
            this.getChildren().add(child);
        }
    }
    /**
    *  �Ƿ�����Ÿýڵ�,���ڷ��ظýڵ㣬�����ڷ��ؿ�
    * @param name
    * @return
    */
    public TreeNode findChild(String name) {
        List<TreeNode> children = this.getChildren();
        if (children != null) {
            for (TreeNode child : children) {
                if (child.getName().equals(name)) {
                    return child;
                }
            }
        }
        return null;
    }
	
}
