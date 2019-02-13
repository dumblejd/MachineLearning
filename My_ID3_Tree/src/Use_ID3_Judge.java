import java.util.List;


public class Use_ID3_Judge 
{
	int correct_num;
	double accuracy_rate;
	int bool=-1;
	
	public void judge(List<String> data,TreeNode node)
	{
		int temptimes=0;

		for(int i=1;i<data.size();i++)
		{
			String []row=data.get(i).split(",");
			judge_one(data,node,row);
			if(bool==1)
			{
				temptimes++;
			}
		}
		correct_num=temptimes;
		accuracy_rate=correct_num/((1.0)*(data.size()-1));
	}
	public void judge_one(List<String> data,TreeNode node,String [] row)
	{
		String []attr_name=data.get(0).split(",");
		int index=-1;
		for(int i=0;i<attr_name.length;i++)
		{
			if (attr_name[i].equals(node.getName())) 
			{
				index=i;
			}
		}
		
		if(node.getChildren()!=null)
		{
			for(int j=0;j<node.getChildren().size();j++)
			{
				if(row[index].equals(node.getChildren().get(j).getFatherAttribute()))
				{
					judge_one(data, node.getChildren().get(j), row);
				}
			}
		}
		else
		{
			if(row[row.length-1].equals(node.getName()))
			{
				this.bool=1;
			}
			else 	
			{
				this.bool=-2;
			}
		}
	}
}
