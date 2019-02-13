import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



/**
 * 
 * @param name name of node
 * @param parent father node
 * @param children children node
 * @param fatherAttribute connected father attribute ()
 * @param con_attributes different attributes of condition height(hign low normal) so it has three attributes
 * @return
 */
public class TreeNode 
{
	String name; // name of node 
	TreeNode parent; // father node
	List<TreeNode> children; // children node
	String fatherAttribute; // connected father attribute ()
	ArrayList<String> con_attributes; //different attributes of condition height(hign low normal) so it has three attributes
	LinkedList<String[]> data_below_for_prune;  //for prune	
	int canprune=0; //for prune
	int countbelow=1;
	public int nodelevel=0;
	public int total_level=0;
	public int leaf=0;
	List attributes_can_bee_add;   //=new ArrayList();
	public int getNodelevel() {
		return nodelevel;
	}
	public void setNodelevel(int nodelevel) {
		this.nodelevel = nodelevel;
	}
	public List getAttributes_can_bee_add() {
		return attributes_can_bee_add;
	}
	public void setAttributes_can_bee_add(List attributes_can_bee_add) {
		this.attributes_can_bee_add = attributes_can_bee_add;
	}
	public void count_leaf(TreeNode node)
	{
		if(node.getChildren()!=null)
		{
			for(int i=0;i<node.getChildren().size();i++)
			{
			count_leaf(node.getChildren().get(i));
			}
		}
		else
		{
			this.leaf++;
		}
	}
	public void counttotal_level(TreeNode node)
	{
		if(node.getChildren()!=null)
		{
			if(node.getChildren()!=null)
			{
				for(int i=0;i<node.getChildren().size();i++)
				{
					counttotal_level(node.getChildren().get(i));			
				}
			}
		}
		else
		{
			this.total_level+=node.nodelevel;
		}
	}
	public void countnodelevel(TreeNode node)
	{
		if(node.getChildren()!=null)
		{
			for(int i=0;i<node.getChildren().size();i++)
			{
				node.getChildren().get(i).nodelevel=node.nodelevel+1;
				countnodelevel(node.getChildren().get(i));
			}
		}
	}
	public int getCountbelow() {
		return countbelow;
	}

	public void setCountbelow(int countbelow) {
		this.countbelow = countbelow;
	}

	public LinkedList<String[]> getData_below_for_prune() {
		return data_below_for_prune;
	}

	public void setData_below_for_prune(LinkedList<String[]> data_below_for_prune) {
		this.data_below_for_prune = data_below_for_prune;
	}

	
		
		public void countnode_returnint(TreeNode node)
		{
			if(node.getChildren()!=null)
			{
				for(int i=0;i<node.getChildren().size();i++)
				{
					countbelow++;
				}
				for(int i=0;i<node.getChildren().size();i++)
				{
					countnode_returnint(node.getChildren().get(i));
				}
			}
		}
		
	 public int getCanprune() {
		return canprune;
	}
	public void setCanprune(int nodemark) {
		this.canprune = nodemark;
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
	public ArrayList<String> getCon_attributes() {
		return con_attributes;
	}
	public void setCon_attributes(ArrayList<String> con_attributes) {
		this.con_attributes = con_attributes;
	}

    public void addChild(TreeNode child) 
    {
        if (this.getChildren() == null) 
        {
            List<TreeNode> list = new ArrayList<TreeNode>();
            list.add(child);
            this.setChildren(list);
        } else 
        {
            this.getChildren().add(child);
        }
    }
	
	
}
