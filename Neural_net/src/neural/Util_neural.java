package neural;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Util;
//public void initial_layer(int num_node_last,int num_node_this,int type)
//{
//	this.type=type;
//	if(this.type!=0)
//	{
//	input=new double[num_node_last+1];
//	weight=new double[num_node_this][num_node_last+1];
//	value=new double[num_node_this+1];
//	delta=new double[num_node_this];
//	}
//	else
//	{
//		value=new double[num_node_this+1];
//		delta=new double[num_node_this];
//	}
//	
//}
public class Util_neural 
{
	public static double step=0.9;
	public static int num_outputlayer=1;
	List<double[]> data_train=new ArrayList<double[]>();
	List<double[]> data_test=new ArrayList<double[]>();
	List<Layer>all_layer=new ArrayList<Layer>();
	public double accuracy=0;
	
	public void train(String filepath_input,String savepath,double percentage_train,int interation,int num_hidden,int...nums_node)
	{
		if(num_hidden!=nums_node.length)
		{
			System.out.println("num of hiddent lyer donesn't consistent with numbers of node");
			return;
		}
		Preprocessing p=new Preprocessing();
		p.preprocess(filepath_input, savepath);
		load_data(p.data_standard, percentage_train);
		create_net(num_hidden, nums_node);
		for(int i=0;i<interation;i++)
		{
			for(int j=0;j<data_train.size();j++)
			{
				forward_pass(data_train.get(j));
				backward_pass(data_train.get(j));
			}
		}
	}
	public void test()
	{
		double[]d= {1,0,1,1} ;
		data_train.add(d);
		data_test.add(d);
		int t[]= {1};
		create_net(1, t);
		forward_pass(data_train.get(0));
		backward_pass(data_train.get(0));
	}
    public void print_net()
    {
    		for(int i=0;i<all_layer.size();i++)
    		{
    			if(all_layer.get(i).type==0)
    			{
    				System.out.println("Layer "+i+"(Input Layer):");
    				System.out.println("first neuron is bias   ");
    				for(int k=0;k<all_layer.get(i).value.length;k++)
    				{
    					System.out.print("Neuron"+(k+1)+" weights: ");
	    				for(int j=0;j<all_layer.get(i+1).weight.length;j++)
	    				{
	    					System.out.print("("+(k+1)+"-"+(j+1)+")");
	    					System.out.print(all_layer.get(i+1).weight[j][k]+" ");
	    				}
	    				System.out.println();
    				}
    			}
    			if(all_layer.get(i).type==1)
    			{
    				System.out.println("Layer "+i+"(Hidden Layer "+i+"):");
    				System.out.println("first neuron is bias   ");
    				for(int k=0;k<all_layer.get(i).value.length;k++)
    				{
    					System.out.print("Neuron"+(k+1)+" weights: ");
	    				for(int j=0;j<all_layer.get(i+1).weight.length;j++)
	    				{
	    					System.out.print("("+(k+1)+"-"+(j+1)+")");
	    					System.out.print(all_layer.get(i+1).weight[j][k]+" ");
	    				}
	    				System.out.println();
    				}
    			}
    			if(all_layer.get(i).type==2)
    			{
    				System.out.println("Layer "+i+"(Output Layer)");
    				for(int k=0;k<all_layer.get(i).value.length;k++)
    				{
    					System.out.println("Neuron"+(k+1));
	    				
    				}
    			}
    		}
    }
	public void backward_pass(double[] data)
	{
		//Layer temp=null;
		double target=data[data.length-1];
		//=======update all delta 
		if(all_layer.size()<=0)
		{
			System.out.println("please run the forward pass first");
			return;
		}
		for(int i=all_layer.size()-1;i>=0;i--)
		{
			if(all_layer.get(i).type==2)
			{
				for(int j=0;j<all_layer.get(i).value.length;j++)
				{
					double value=all_layer.get(i).value[j];
				    all_layer.get(i).delta[j]=value*(1-value)*(target-value);
				}
			}
			if(all_layer.get(i).type==1)
			{
				for(int k=0;k<all_layer.get(i).delta.length;k++)
				{
					for(int q=0;q<all_layer.get(i+1).delta.length;q++)
					{
						all_layer.get(i).delta[k]+=all_layer.get(i+1).delta[q]*all_layer.get(i+1).weight[q][k+1];
					}
					double temp=all_layer.get(i).delta[k];
					all_layer.get(i).delta[k]=temp*(1-all_layer.get(i).value[k+1])*all_layer.get(i).value[k+1];
				}
			}
//			if(all_layer.get(i).type==1&&all_layer.get(i+1).type!=0)
//			{
//				for(int k=0;k<all_layer.get(i).delta.length;k++)
//				{
//					for(int q=0;q<all_layer.get(i+1).delta.length;q++)
//					{
//						all_layer.get(i).delta[k]+=all_layer.get(i+1).delta[q]*all_layer.get(i+1).weight[q][k+1];
//					}
//				}
//			}
			if(all_layer.get(i).type==0)
			{
				//no need to compute delta
			}
		}
		//=============update all weight=====
		for(int i=0;i<all_layer.size();i++)
		{
			if(all_layer.get(i).type==0)
			{
				//no need to compute delta
			}
			else
			{
				for(int j=0;j<all_layer.get(i).weight.length;j++)
				{
					for(int k=0;k<all_layer.get(i).weight[0].length;k++)
					{
						all_layer.get(i).weight[j][k]+=all_layer.get(i-1).value[k]*step*all_layer.get(i).delta[j];
					}
				}
			}
		}
	}
	public void forward_pass(double[] data)
	{

		for(int i=0;i<all_layer.size();i++)
		{
			double value=0;
			double weight=0;
			if(all_layer.get(i).type==0)
			{
				for(int j=1;j<all_layer.get(i).value.length;j++)
				{
					all_layer.get(i).value[j]=data[j-1];
				}
				for(int j=0;j<all_layer.get(i).value.length;j++)
				{
					all_layer.get(i+1).input[j]=all_layer.get(i).value[j];
				}
			}
			if(all_layer.get(i).type==1)
			{
			
				for(int i_value=1;i_value<all_layer.get(i).value.length;i_value++)
				{
					for(int i_input=0;i_input<all_layer.get(i).input.length;i_input++)
					{
						weight=all_layer.get(i).weight[i_value-1][i_input];
						value=all_layer.get(i).input[i_input];
						all_layer.get(i).value[i_value]+=value*weight;
					}
					all_layer.get(i).value[i_value]=sigmoid(all_layer.get(i).value[i_value]);
				}
				for(int j=0;j<all_layer.get(i).value.length;j++)
				{
					all_layer.get(i+1).input[j]=all_layer.get(i).value[j];
				}
			}
			if(all_layer.get(i).type==2)
			{
				for(int i_value=0;i_value<all_layer.get(i).value.length;i_value++)
				{
					for(int i_input=0;i_input<all_layer.get(i).input.length;i_input++)
					{
						weight=all_layer.get(i).weight[i_value][i_input];
						value=all_layer.get(i).input[i_input];
						all_layer.get(i).value[i_value]+=value*weight;
					}
					all_layer.get(i).value[i_value]=sigmoid(all_layer.get(i).value[i_value]);
				}
			}
		}
	}
	 private double sigmoid(double input) 
	 {  
	        return 1d / (1d + Math.exp(-input));  
	 }  
	public void create_net(int num_hidden,int[]nums_node)
	{
		//===========initial the structure of the data======
		Layer input=new Layer();
		input.initial_layer(data_train.get(0).length-1, data_train.get(0).length-1, 0);
		all_layer.add(input);
		for(int i=0;i<num_hidden;i++)
		{
			Layer hiddenlayer=new Layer();
			hiddenlayer.initial_layer(all_layer.get(all_layer.size()-1).value.length-1,nums_node[i], 1);
			all_layer.add(hiddenlayer);
		}
//		Map<Double, Integer> map_attr=new HashMap<>();
//		for(int i=0;i<data_train.size();i++)
//		{
//			map_attr.put(data_train.get(i)[data_train.get(i).length-1], 1);
//		}
		Layer output=new Layer();
		output.initial_layer(all_layer.get(all_layer.size()-1).value.length-1, num_outputlayer, 2);
		all_layer.add(output);
		//============random the weight==================
		for(Layer l:all_layer)
		{
			random_weight(l);
		}
		//============set bias===========================
		for(Layer l:all_layer)
		{
			if(l.type!=2)
			{
				l.value[0]=1;    //set bias all=1
			}
		}
	}

	public void load_data(List<double[]> data,double percentage)
	{
		List<double[]> list=new ArrayList<double[]>();
		double[]temp=null;
		for(int i=0;i<data.size();i++)
		{
			temp=new double[data.get(0).length];
			for(int j=0;j<temp.length;j++)
			{
				temp[j]=data.get(i)[j];
			}
			list.add(temp);
		}
		
		int size=list.size();
		int tempcount= (int)(size*percentage);
		for (int i = 0; i <tempcount; i++) 
		{
            int math = (int) (Math.random() * size);
            data_train.add(list.get(math));
            list.remove(math);
            size--;
        }
//		tempcount=size-tempcount;
		
		for (int i = 0; i <list.size(); i++) 
		{
			size=list.size();
            int math = (int) (Math.random() * size);
            data_test.add(list.get(math));
            list.remove(math);
        }
		
	}
	public void random_weight(Layer layer)
	{
		if(layer.type==0)
		{
			System.out.println("input layer does not have weight");
			return;
		}
		for(int i=0;i<layer.weight.length;i++)
		{
			for(int j=0;j<layer.weight[0].length;j++)
			{
				
				double temp=0;
				while(temp==0)
				{
				temp=Math.random();
				}
				layer.weight[i][j]=temp;//for test I set 0.5£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡
			}
		}
	}
	public void cal_error()
	{
	
	}
	public void accuracy_for_one_output(List<double[]> data)
	{
		int count=0;
		for(int i=0;i<data.size();i++)
		{
			forward_pass(data.get(i));
			double judge=all_layer.get(all_layer.size()-1).value[0]-data.get(i)[data.get(i).length-1];
			System.out.println(all_layer.get(all_layer.size()-1).value[0]);
			if(judge<=0.5&&judge>=-0.5)
			{
				count++;
			}
		}
		System.out.println("accuracy:"+count/((1.0)*data.size()));
	}
	public static void main(String[] args)
	{
//		Preprocessing p=new Preprocessing();
//		p.preprocess("/Users/dijin/eclipse-workspace/Neural_net/car.data", "/Users/dijin/Desktop/ML/homeworkä½œä¸š/neural/standard_data.csv");
		Util_neural u=new Util_neural();
//		u.load_data(p.data_standard, 0.8);
//		int[]a= {3,4,2};
//		u.create_net(3, a);
//		u.forward_pass(u.data_train.get(0));
		
		
//		u.train("/Users/dijin/eclipse-workspace/Neural_net/car.data", 0.8, 1000, 2, 4,2);
//		u.print_net();
//		u.accuracy_for_one_output(u.data_train);
//		u.accuracy_for_one_output(u.data_test);
//		
//		u.train("/Users/dijin/eclipse-workspace/Neural_net/adult.data.txt", 0.8, 1000, 2, 4,2);
//		u.print_net();
//		u.accuracy_for_one_output(u.data_train);
//		u.accuracy_for_one_output(u.data_test);
		
		u.train("D:\\JAVA workspace\\Neural_net\\car.data","D:\\JAVA workspace\\Neural_net\\", 0.8, 1, 1, 1);
		u.print_net();
//		u.accuracy_for_one_output(u.data_train);
		u.accuracy_for_one_output(u.data_test);
		//u.test();
	}
}
