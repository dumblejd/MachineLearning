
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





public class Util_random 
{
	 LinkedList<String> attributes_name = new LinkedList<String>(); //attribute name
	 LinkedList<ArrayList<String>> attributes_values = new LinkedList<ArrayList<String>>(); //here only have 1,0 . value of each attribute(e.g. high,low,normal)
	 LinkedList<String[]> data = new LinkedList<String[]>(); // instance
	 String[]yes_no= {"1","0"};; //save the judgement (in homework there are only '1' and '0', in other work may use 'stay','leave')
	 int countnode=1;
	 int leaf=0;
	 static TreeNode root;
	 
	 public int randomindex(List canadd)
	 {
		 Random rand = new Random();
		 int randNum = rand.nextInt(canadd.size());
		 int index=(int) canadd.get(randNum);
		return index;
	 }
	 public void create_ID3_tree()
	 {
		root=new TreeNode();
	    List tempadd=new ArrayList();
	    for(int i=0;i<attributes_name.size();i++)
	    {
	    		tempadd.add(i);
	    }
	    int maxkey=randomindex(tempadd);
	    String nodename=attributes_name.get(maxkey);
	    root.setName(nodename);
	    root.setCon_attributes(attributes_values.get(maxkey));
	    root.setAttributes_can_bee_add(tempadd);;
	    root.getAttributes_can_bee_add().remove(maxkey);
	    //root.setData_below_this_node(data); //i use it wrong, should filled the tree with validation data
	    insertNode(data,root,maxkey);
	     countnode(root);
	 }
	 
	 
	 
	

	 
	 /**
	 	 *@category recursion function to add children node
		 * @param node father node
		 * @param attr_values values of a attribute
		 * @param att_value value of a attribute
	 */
	 
	 public int ispure(LinkedList<String[]> subdata)
	 {
		 int ispure=-1;
		 String temp=subdata.get(0)[subdata.get(0).length-1];
		 for(int i=0;i<subdata.size();i++)
		 {
			 if(!subdata.get(i)[subdata.get(i).length-1].equals(temp))
			 {
				 ispure=0;
				 break;
			 }
			 if(i==(subdata.size()-1))
			 {
				 ispure=1;
			 }
		 }
		 return ispure;
	 }
	 public void insertNode(LinkedList<String[]> data,TreeNode node,int index)//recursion function
	 {
	    	List<String> attr_values=node.getCon_attributes();
	    	for(int i=0;i<attr_values.size();i++)  //for each high/low/normal create tree  here only 1,0
	    	{
	    		String att_value=attr_values.get(i);
	    		LinkedList<String[]> newrows=cutdata(data,att_value,index);
	    		if(newrows.size()<=0)
	    		{
	    			Map<String, Integer> map = new HashMap<String, Integer>();
    		        List<String> list = new ArrayList<String>();

    		        
    		        for(int q=0;q<data.size();q++)
    		        {
    		        	String []temp=data.get(q);
    		        	String s=temp[temp.length-1];
    		            if(map.containsKey(s))
    		            {
    		                int x = map.get(s);
    		                x++;
    		                map.put(s, x);
    		            }
    		            else
    		            {  
    		                map.put(s, 1);
    		                list.add(s);
    		            }
    		        }
    		        int max=0;
    		        String maxString = null;
    		       
    		        for(String s : list)
    		        {
    		            int x = map.get(s);
    		            if(x>max)
    		            {
    		                maxString = s;
    		                max = x;
    		            }
    		        }
    		        node.setName(maxString);
    		        node.setChildren(null);
	    	    	return;
	    	    }
	    		int isanytoadd=1;
	    		if(node.getAttributes_can_bee_add().size()==0)
	    		{
	    			isanytoadd=0;
	    		}
	    		int ispure=ispure(newrows);
	    		
	    		//use self until gain=0
	    		if(ispure==0&&isanytoadd==1){
	    			int ind=randomindex(node.getAttributes_can_bee_add());
		    		int maxkey=ind;
		    		
		    		
	    			TreeNode child=new TreeNode();
	    			child.setParent(node);
	    			child.setFatherAttribute(att_value);
	    			String nodename=attributes_name.get(maxkey);
	    			child.setName(nodename);
	    			child.setCon_attributes(attributes_values.get(maxkey));
	    			//=======set can add=====
	    			List temp=new ArrayList<>();
	    					for(int k=0;k<node.getAttributes_can_bee_add().size();k++)
	    					{
	    						int t=(int)node.getAttributes_can_bee_add().get(k);
	    						temp.add(t);
	    					}
	    			int removeindex=-1;
	    			for(int q=0;q<temp.size();q++)
	    			{
	    				if(((int)temp.get(q))==maxkey)
	    				{
	    					removeindex=q;
	    				}
	    			}
	    			temp.remove(removeindex);
	    			child.setAttributes_can_bee_add(temp);
	    			//========================
	    			//child.setData_below_this_node(newrows);//for prune //i use it wrong, should filled the tree with validation data
	    			node.addChild(child);
	    			insertNode(newrows,child,maxkey);
	    		}
	    		else if(ispure==1||isanytoadd==0)
	    		{
	    			TreeNode child=new TreeNode();
	    			child.setParent(node);
	    			child.setFatherAttribute(att_value);

//=============================choose the majority=====================================================
	    			 Map<String, Integer> map = new HashMap<String, Integer>();
	    		        List<String> list = new ArrayList<String>();

	    		        
	    		        for(int q=0;q<newrows.size();q++)
	    		        {
	    		        	String []temp=newrows.get(q);
	    		        	String s=temp[temp.length-1];
	    		            if(map.containsKey(s))
	    		            {
	    		                int x = map.get(s);
	    		                x++;
	    		                map.put(s, x);
	    		            }
	    		            else
	    		            {  
	    		                map.put(s, 1);
	    		                list.add(s);
	    		            }
	    		        }
	    		        int max=0;
	    		        String maxString = null;
	    		       
	    		        for(String s : list)
	    		        {
	    		            int x = map.get(s);
	    		            if(x>max)
	    		            {
	    		                maxString = s;
	    		                max = x;
	    		            }
	    		        }
	    		        
//================================================================================   			
	    			child.setName(maxString);   
	    			node.addChild(child);
	    		}
	    	}
	    }
	 /**
	 	 *@category get data with specific attribute value of a attribute (e.g. high out of height(high/low/normal))
		 * @param att_value one attribute value of a condition (e.g. high out of height(high/low/normal))
	 */
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
	 
	 public static int print=0;
	public void printTree(TreeNode node,int level)
	{
		int count=level;
		if(print!=0&&node.getChildren()!=null)
		{
			System.out.println();
		}
		print++;
		 
		if(node.getChildren()==null){
			System.out.print(node.getName());
			System.out.println();
			return;
		}
	   ++count;
	    for(int i=0;i<node.getChildren().size();i++)
	    {
	    	for(int j=0;j<level;j++)
	    	{
	    		System.out.print("|");
	    	}
	    	System.out.print(node.getChildren().get(i).getParent().getName()+"="+node.getChildren().get(i).getFatherAttribute());
	    	System.out.print(":");
	    	printTree(node.getChildren().get(i),count);
	    }
	}
	public void setdata(List<String> rawdata)
	{
		String names[]=rawdata.get(0).split(",");
	
		 for(int i=0;i<names.length-1;i++)
		 {
			 attributes_name.add(names[i]);
			 ArrayList<String> t=new  ArrayList<String>();
			 t.add("1");
			 t.add("0");
			 attributes_values.add(t);
		 }
		 //System.out.println(attributes_name.size());
		 for(int j=1;j<rawdata.size();j++)  //start with second line for data
		 {
			 String tempdata[]=rawdata.get(j).split(",");
			 data.add(tempdata);
		 }
		
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
	public void outerinterface(String training,String validation,String testing)
	{
	 	Util_random myid3 = new Util_random();
	    	Read_from_csv read=new Read_from_csv();
	    	List<String> rawdata=read.read(training);
	    	myid3.setdata(rawdata);
	    	myid3.create_ID3_tree();
	    	myid3.printTree(myid3.root,0);
	    	System.out.println("---------------------------------------------");
	    	System.out.println("number of traning instance=:"+(read.datasize-1));
	    	System.out.println("number of traning attribute=:"+read.attributesize);
	    	root.countnode_returnint(myid3.root);
	    	System.out.println("total number of node=:"+(myid3.root.countbelow+1));
	    	count_leaf(myid3.root);
	    	System.out.println("number of leaf=:"+this.leaf);
	    	Use_ID3_Judge judge=new Use_ID3_Judge();
	    	judge.judge(rawdata, myid3.root);
	    	System.out.println("accuracy	of the model on	the training dataset="+judge.accuracy_rate);
	    	System.out.println();
	    	
	    List<String> rawpdata=read.read(validation);
	    	System.out.println("number of validation instance=:"+(read.datasize-1));
	    	System.out.println("number of validation attribute=:"+read.attributesize);
	    	judge.judge(rawpdata, myid3.root);
	    	System.out.println("accuracy	of the model on the validation dataset="+judge.accuracy_rate);
	    	System.out.println();
	    	
	   List<String> testdata=read.read(testing);
	   System.out.println("number of testing instance=:"+(read.datasize-1));
    	   System.out.println("number of testing attribute=:"+read.attributesize);
       judge.judge(testdata, myid3.root);
       System.out.println("accuracy of the model on the testing dataset="+judge.accuracy_rate);
       System.out.println();
       
       myid3.root.countnodelevel(root);
       myid3.root.total_level=0;
       myid3.root.leaf=0;
       myid3.root.count_leaf(myid3.root);
       myid3.root.counttotal_level(myid3.root);
       
       double avglevel=(1.0*myid3.root.total_level)/(1.0*myid3.root.leaf);
       System.out.println("average level is:"+avglevel);
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
	public static void main(String[] args) 
	{
    	Util_random myid3 = new Util_random();
		myid3.outerinterface("/Users/dijin/eclipse-workspace/My_ID3_Tree/training_set.csv", "/Users/dijin/eclipse-workspace/My_ID3_Tree/validation_set.csv", "/Users/dijin/eclipse-workspace/My_ID3_Tree/test_set.csv");
		
//    	Read_from_csv read=new Read_from_csv();
//   	List<String> rawdata=read.read("/Users/dijin/eclipse-workspace/My_ID3_Tree/training_set.csv");
//    	myid3.setdata(rawdata);
//  
//   	myid3.create_ID3_tree();
//    	myid3.printTree(root,0);
//       	Use_ID3_Judge judge=new Use_ID3_Judge();
//
//       	judge.judge(rawdata, myid3.root);
//        	System.out.println("correct times:"+judge.correct_num+"  accuracy rate:"+judge.accuracy_rate);
//        	
//    	List<String> judgedata=read.read("/Users/dijin/eclipse-workspace/My_ID3_Tree/test_set.csv");
//   	judge.judge(judgedata, myid3.root);
//    	System.out.println("correct times:"+judge.correct_num+"  accuracy rate:"+judge.accuracy_rate);
//
//   	
//   	Prune_new prune=new Prune_new();
//   	List<String> rawpdata=read.read("/Users/dijin/eclipse-workspace/My_ID3_Tree/validation_set.csv");
//   	
//   	judge.judge(rawpdata, myid3.root);
//   	System.out.println("correct times:"+judge.correct_num+"  accuracy rate:"+judge.accuracy_rate);
		
    	
    	
    	//myid3.outerinterface("/Users/dijin/eclipse-workspace/My_ID3_Tree/training_set.csv", "/Users/dijin/eclipse-workspace/My_ID3_Tree/validation_set.csv", "/Users/dijin/eclipse-workspace/My_ID3_Tree/test_set.csv",0.2);
    	  
//    	myid3.root.countnodelevel(myid3.root);
//    	System.out.println(myid3.root.getChildren().get(0).getChildren().get(1).nodelevel);
	}

}

