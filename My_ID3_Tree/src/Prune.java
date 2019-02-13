import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.lang.model.type.IntersectionType;

public class Prune 
{
	TreeNode prunenode;
	int num=2;
	int countnode=0;
	int nodec=0;
	TreeNode subnode;
	int left=0;
	public void Prune_a_tree(List<String> rawdata,TreeNode node, double prune_rate)
	{
		
//		node.setNodemark(1);
	 
		node.countnode_returnint(node);
		int max=node.getCountbelow();
		int  min=2;
		int cutnumber=0;
		cutnumber=(int) (((1.0)*max*prune_rate));
		int lefttocut=cutnumber;
		left=lefttocut;
		
		LinkedList<String[]> data=new LinkedList<String[]>();
		for(int i=1;i<rawdata.size();i++)
		{
			String[]a=rawdata.get(i).split(",");
			data.add(a);
		}
		String[]attr_name=rawdata.get(0).split(",");
		put_prune_data(data,prunenode,attr_name);
		while(left>0)
		{
			marknode(node);
			  node.countnode_returnint(node);
			  max=node.getCountbelow();
			  min=2;
		 Prune_a_node(node,max,min,left);
		}
		
	}
	public void Prune_a_node(TreeNode node,int max,int min,int lefttocut)
	{
		num=2;
		marknode(node);
		int nodenum=random(max, min);	
		count_node_count_with_num(node,nodenum);
		
			while((countnode+1)>lefttocut)
			{
				nodenum=random(max, min);	
				count_node_count_with_num(node,nodenum);
			}
			left=lefttocut-countnode-1;
			findnode(node,nodenum);
			LinkedList<String[]> temp=subnode.getData_below_for_prune();
			subnode.setName(findmost(temp));
			subnode.setChildren(null);
			set_subnode(node);
	}
	public void set_subnode(TreeNode node)
	{
		if(node.getChildren()!=null)
		{
			if(node.getName().equals(subnode.getName()))
			{
				node.setName(subnode.getName());
				node.setChildren(null);
			}
			else {
				for(int i=0;i<node.getChildren().size();i++)
				{
					 set_subnode(node.getChildren().get(i));
				}
			}
		}
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
	public void findnode(TreeNode node,int nodemark)
	{
//		if(nodemark==node.getNodemark())
//		{
//			subnode=node;
//		}
//		if(node.getChildren()!=null)
//		{
//			for(int i=0;i<node.getChildren().size();i++)
//			{
//				findnode(node.getChildren().get(i),nodemark);
//			}
//		}
		
	}
	public void count_node_count_with_num(TreeNode node,int num)
	{
//		if(node.getNodemark()==num)
//		{
//			countnode(node);
//		}
//		else 
//		{
//			if(node.getChildren()!=null)
//			{
//				for(int i=0;i<node.getChildren().size();i++)
//				{
//					count_node_count_with_num(node.getChildren().get(i),num);
//				}
//			}
//		}
	}
	public void put_prune_data(LinkedList<String[]> data,TreeNode node,String[]attr_name)
	{
		if(node.getFatherAttribute()!=null)
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
				put_prune_data(data,node.getChildren().get(i),attr_name);
			}
		}
		else 
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

	public void marknode(TreeNode node)
	{

//		if(node.getChildren()!=null)
//		{
//			for(int i=0;i<node.getChildren().size();i++)
//			{
//				node.getChildren().get(i).setNodemark(num);
//				num++;
//			}
//			for(int i=0;i<node.getChildren().size();i++)
//			{
//				marknode(node.getChildren().get(i));
//			}
//		}
	}
	public int random(int max,int min)
	{
		int result=-1;
		Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
		return result;
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
