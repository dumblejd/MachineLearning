import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Prune_new 
{
	int countnode=0;
	
	public void Prune_a_tree(List<String> rawdata,TreeNode node, double prune_rate)
	{
		node.countbelow=1;
		int cutnumber;
		node.countnode_returnint(node);
		int datanumber=node.getCountbelow()+1;
		cutnumber=(int) (((1.0)*datanumber*prune_rate));
		
		LinkedList<String[]> data=new LinkedList<String[]>();
		for(int i=1;i<rawdata.size();i++)
		{
			String[]a=rawdata.get(i).split(",");
			data.add(a);
		}
		String[]attr_name=rawdata.get(0).split(",");
		
		put_prune_data(data,node,attr_name);
		setprunenode(node);
		countnode=0;
		
	    countnode(node);
	    while(countnode>datanumber-1-cutnumber)
	    {
			Prune_a_node(node);
			countnode=0;
			countnode(node);
		}
		
		
	}
	public int random()
	{
		int rd=Math.random()>0.5?1:0;
		return rd;
	}
	public void setprunenode(TreeNode node)
	{
		if(node.getChildren()!=null)
		{
			if(node.getChildren().get(0).getChildren()==null&&node.getChildren().get(1).getChildren()==null)
			{
				node.setCanprune(1);
				
			}
			else
			{
				for(int i=0;i<node.getChildren().size();i++)
				{
					setprunenode(node.getChildren().get(i));
				}
			}
		}
			
	}
	public void Prune_a_node(TreeNode node)// only cut last two levels nodes
	{
		if(node.getChildren()!=null)
		{
			if(node.getCanprune()!=1)
			{
				int iii=random();
				Prune_a_node(node.getChildren().get(iii));
			}
			if(node.getCanprune()==1)
			{
				node.setName(findmost(node.getData_below_for_prune()));
				node.children=null;
			}
		}
	}
	
	public void put_prune_data(LinkedList<String[]> data,TreeNode node,String[]attr_name)
	{
		if(node.getFatherAttribute()!=null&&node.getChildren()!=null)
		{
			int index=-1;
			for(int i=0;i<attr_name.length;i++)
			{
				if (attr_name[i].equals(node.getParent().getName())) 
				{
					index=i;
				}
			}
			
		
			LinkedList<String[]> temp=cutdata(data, node.getFatherAttribute(),index);
			node.setData_below_for_prune(temp);
				
				
			for(int i=0;i<node.getChildren().size();i++)
			{
				put_prune_data(temp,node.getChildren().get(i),attr_name);
			}
		}
		if(node.getChildren()!=null&&node.getFatherAttribute()==null)
		{
			node.setData_below_for_prune(data);
			for(int i=0;i<node.getChildren().size();i++)
			{
				put_prune_data(data,node.getChildren().get(i),attr_name);
			}
		}
	}
	public LinkedList<String[]> cutdata(LinkedList<String[]> data, String att_value, int index)
	{
    	LinkedList<String[]> newdata=new LinkedList<String[]>();
    	for(int i=0;i<data.size();i++)
    	{
    		String[] row=data.get(i);
    		if(row[index].equals(att_value))
    		{
    			newdata.add(row);
    		}
    	}
    	return newdata;
	}
	
	public String findmost(LinkedList<String[]> temp)
	{
		String judge=null;
		int count1=0;
		int count0=0;
		for(int i=0;i<temp.size();i++)
		{
		   if(temp.get(i)[temp.get(i).length-1].equals("0"))
		   {
			   count0++;
		   }
		   else {
			   {
				   count1++;
			   }
		}
		}
		if(count1>count0)
		{
			judge="1";
		}
		if(count1<count0)
		{
			judge="0";
		}
		return judge;
	}
	public void countnode(TreeNode node)
	{
		if(node.getChildren()!=null)
		{
			for(int i=0;i<node.getChildren().size();i++)
			{
				countnode++;
			}
			for(int i=0;i<node.getChildren().size();i++)
			{
				countnode(node.getChildren().get(i));
			}
		}
	}
}
