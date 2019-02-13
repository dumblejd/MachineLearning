import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





public class Util_ID3 
{
	 LinkedList<String> attributes_name = new LinkedList<String>(); //attribute name
	 LinkedList<ArrayList<String>> attributes_values = new LinkedList<ArrayList<String>>(); //here only have 1,0 . value of each attribute(e.g. high,low,normal)
	 LinkedList<String[]> data = new LinkedList<String[]>(); // instance
	 String[]yes_no= {"1","0"};; //save the judgement (in homework there are only '1' and '0', in other work may use 'stay','leave')
	 int countnode=1;
	 int leaf=0;
	 static TreeNode root;
	 
	 
	 public void create_ID3_tree()
	 {
		root=new TreeNode();
	    Maxgain maxgain=get_maxgain(data);
	    if(maxgain==null)
	    {
	    	System.out.println("empty data");
	    }
	    int maxkey=maxgain.getMaxindex();
	    String nodename=attributes_name.get(maxkey);
	    root.setName(nodename);
	    root.setCon_attributes(attributes_values.get(maxkey));
	    //root.setData_below_this_node(data); //i use it wrong, should filled the tree with validation data
	    insertNode(data,root,maxkey);
	   countnode(root);
	 }
	 
	 /**
	 	 *@category return Maxgain type value that include maxgain and index of maxgain attribute
	 */
	 public Maxgain get_maxgain(LinkedList<String[]> data)
	 {
		if(data==null||data.size()<=0)
		{
			System.out.println("no data for get_maxgain");
	    	return null;
	    }
		
	    Maxgain maxgain = new Maxgain();
	    double maxvalue=0;
	    int maxindex=-1;
	    
	    for(int i=0;i<attributes_name.size();i++)
	    {
		    double tmp=get_gain(data,i);
		    if(maxvalue< tmp)
		    {
		    	maxvalue=tmp;
		    	maxindex=i;
		    }
	    }
    	maxgain.setMaxgain(maxvalue);
    	maxgain.setMaxindex(maxindex);
    	
    	return maxgain;
	 }
	 
	 /**
	 	 *@category method to get gain with input(datalist,index of attr)
		 * @param count_yesorno (double) the number of yes or no
		 * @param count_yesno_attrs number of yesandno of this attribute
		 * @param values_attribute here only 1,0
	 */
	 public double get_gain(LinkedList<String[]> data,double attr_index)
	 {
		double gain=-1.0;
	    ArrayList<Double> counts_yesorno=new ArrayList<Double>();
	  //=============================================================
	    for(int i=0;i<yes_no.length;i++)
	    {
	    	double count=0.0;
	    	for(int j=0;j<data.size();j++)
	    	{
	    		String[] row=data.get(j);
	    		if(row[row.length-1].equals(yes_no[i]))
	    		{
	    			count+=1;
	    		}
	    	}
	    	counts_yesorno.add(count);
	    }
	    Cal_ID3 calid3=new Cal_ID3();
    	double entropy_upper=calid3.getEntropy(data.size(), counts_yesorno);  
    	//============================================================
    	List<String> values_attribute=attributes_values.get((int)attr_index); //1,0   
    	List<Attribute> att_entropy=new ArrayList<Attribute>();
    	
    	for(int k=0;k<values_attribute.size();k++)
    	{
    		String value_attr=values_attribute.get(k);   
    		List<Double> count_yesno_attrs=new ArrayList<Double>();
    		double total_count=0.0;
        	for(int i=0;i<yes_no.length;i++)
        	{
        		double sum=0.0;
        		for(int j=0;j<data.size();j++)
        		{
        			String[] row=data.get(j);
        			if(row[(int)attr_index].equals(value_attr)&&row[row.length-1].equals(yes_no[i]))
        			{
        				sum=sum+1;
        			}
        		}
        		total_count=total_count+sum;
        		count_yesno_attrs.add(sum);
        	}
        	
        	double entropy=calid3.getEntropy((int)total_count, count_yesno_attrs);
        	
        	Attribute a=new Attribute();
        	a.setCount(total_count);
        	a.setEntropy(entropy);
        	att_entropy.add(a);
    	}
    	gain=calid3.cal_Gain(entropy_upper,data.size(),att_entropy);
    	return gain;
	 }
	 
	 /**
	 	 *@category recursion function to add children node
		 * @param node father node
		 * @param attr_values values of a attribute
		 * @param att_value value of a attribute
	 */
	 public void insertNode(LinkedList<String[]> data,TreeNode node,int index)//recursion function
	 {
	    	List<String> attr_values=node.getCon_attributes();
	    	for(int i=0;i<attr_values.size();i++)  //for each high/low/normal create tree  here only 1,0
	    	{
	    		String att_value=attr_values.get(i);
	    		LinkedList<String[]> newrows=cutdata(data,att_value,index);
	    		if(newrows.size()<=0)
	    		{
	    	    	System.out.println("the cutdata is empty");
	    	    	return;
	    	    }
	    		Maxgain maxgain=get_maxgain(newrows);
	    		double gain=maxgain.getMaxgain();
	    		int maxkey=maxgain.getMaxindex();
	    		//use self until gain=0
	    		if(gain!=0){
	    			TreeNode child=new TreeNode();
	    			child.setParent(node);
	    			child.setFatherAttribute(att_value);
	    			String nodename=attributes_name.get(maxkey);
	    			child.setName(nodename);
	    			child.setCon_attributes(attributes_values.get(maxkey));
	    			//child.setData_below_this_node(newrows);//for prune //i use it wrong, should filled the tree with validation data
	    			node.addChild(child);
	    			insertNode(newrows,child,maxkey);
	    		}else{
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
	public void outerinterface(String training,String validation,String testing,double rate)
	{
		Util_ID3 myid3 = new Util_ID3();
	    	Read_from_csv read=new Read_from_csv();
	    	List<String> rawdata=read.read(training);
	    	myid3.setdata(rawdata);
	    	myid3.create_ID3_tree();
	    	myid3.printTree(myid3.root,0);
	    	System.out.println("---------------------------------------------");
	    System.out.println("Pre-purned Accuracy");
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
   	System.out.println("---------------------------------------------");
    System.out.println("Post-purned Accuracy");
    	System.out.println("---------------------------------------------");
    	
    	
    	rawpdata=read.read(validation);
    	Prune_new prune=new Prune_new();
    	prune.Prune_a_tree(rawpdata, myid3.root, rate);
    	
    	rawdata=read.read(training);
    	System.out.println("number of traning instance=:"+(read.datasize-1));
    	System.out.println("number of traning attribute=:"+read.attributesize);
    	myid3.root.countbelow=1;
    	root.countnode_returnint(myid3.root);
    	System.out.println("total number of node=:"+(myid3.root.countbelow));
    	myid3.leaf=0;
    	myid3.count_leaf(myid3.root);
    	System.out.println("number of leaf=:"+myid3.leaf);
    	judge.judge(rawdata, myid3.root);
    	System.out.println("accuracy	of the model on	the training dataset="+judge.accuracy_rate);
    	System.out.println();
    	
    	rawpdata=read.read(validation);
    	System.out.println("number of traning instance=:"+(read.datasize-1));
    	System.out.println("number of traning attribute=:"+read.attributesize);
    	judge.judge(rawpdata, myid3.root);
    	System.out.println("accuracy	of the model on the validation dataset="+judge.accuracy_rate);
    	System.out.println();
    	
      	testdata=read.read(testing);
    	System.out.println("number of traning instance=:"+(read.datasize-1));
    	System.out.println("number of traning attribute=:"+read.attributesize);
    	judge.judge(testdata, myid3.root);
    	System.out.println("accuracy	of the model on the testing dataset="+judge.accuracy_rate);
    	System.out.println();
    	
    	  myid3.root.countnodelevel(root);
          myid3.root.total_level=0;
          myid3.root.leaf=0;
          myid3.root.count_leaf(myid3.root);
          myid3.root.counttotal_level(myid3.root);
          
          avglevel=(1.0*myid3.root.total_level)/(1.0*myid3.root.leaf);
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
    	Util_ID3 myid3 = new Util_ID3();
//    	Read_from_csv read=new Read_from_csv();
//   	List<String> rawdata=read.read("/Users/dijin/eclipse-workspace/My_ID3_Tree/training_set.csv");
//    	myid3.setdata(rawdata);
//  
//   	myid3.create_ID3_tree();
//    	//myid3.printTree(root,0);
//    	
//    	List<String> judgedata=read.read("/Users/dijin/eclipse-workspace/My_ID3_Tree/test_set.csv");
//    	Use_ID3_Judge judge=new Use_ID3_Judge();
//    	judge.judge(judgedata, myid3.root);
//    	System.out.println("correct times:"+judge.correct_num+"  accuracy rate:"+judge.accuracy_rate);
//	
//    	
//    	Prune_new prune=new Prune_new();
//    	List<String> rawpdata=read.read("/Users/dijin/eclipse-workspace/My_ID3_Tree/validation_set.csv");
//    	prune.Prune_a_tree(rawpdata, myid3.root, 0.2);
//    	
//    	judge.judge(judgedata, myid3.root);
//    	System.out.println("correct times:"+judge.correct_num+"  accuracy rate:"+judge.accuracy_rate);
		myid3.outerinterface("/Users/dijin/eclipse-workspace/My_ID3_Tree/training_set.csv", "/Users/dijin/eclipse-workspace/My_ID3_Tree/validation_set.csv", "/Users/dijin/eclipse-workspace/My_ID3_Tree/test_set.csv",0.2);
//    	  
//    	myid3.root.countnodelevel(myid3.root);
//    	System.out.println(myid3.root.getChildren().get(0).getChildren().get(1).nodelevel);
//    	System.out.println(myid3.attributes_name.size());
	}

}
