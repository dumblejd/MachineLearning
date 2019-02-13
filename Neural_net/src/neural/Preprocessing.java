package neural;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Preprocessing 
{
	List<String[]> data_string= new ArrayList<String[]>();
	List<double[]> data_double=new ArrayList<double[]>();
	List<double[]> data_standard=new ArrayList<double[]>();
	List<String[]> data_column= new ArrayList<String[]>();
	List<Map<String,Integer>> l_map=new ArrayList<Map<String,Integer>>();//save the numer of 
	public void preprocess(String readpath,String savepath)
	{
		Read_Save r_s=new Read_Save();
		List<String> rawdata=r_s.read(readpath);
		//String []temp=null;
		//=====delete empty and null value rows
		delete_empty(rawdata);  
		transfertocolumn();
		//=====change all value to numeric   //each attribute is defined from 1,2,3,4,5...
		trans_string_to_numeric(data_string,data_double);
		//print_map();
		//System.out.println();
		standardization(data_double);
		r_s.save(savepath,data_standard);
		
		r_s.save(savepath,data_double);
	}
	public void transfertocolumn()
	{
		String[] temp=null;
			for(int j=0;j<data_string.get(0).length;j++)
			{
				temp=new String[data_string.size()];
				for(int i=0;i<data_string.size();i++)
				{
					temp[i]=data_string.get(i)[j];
				}
				data_column.add(temp);
			}
	}
	public void standardization(List<double[]> data)//only standardize input
	{
		double[]temp=null;
		for(int i=0;i<data.size();i++)
		{
			temp=new double[data.get(0).length];
			for(int j=0;j<temp.length;j++)
			{
				temp[j]=data.get(i)[j];
			}
			data_standard.add(temp);
		}
		double []sum=new double[data.get(0).length];
		double []mean=new double[data.get(0).length];
		double []standard_devition=new double[data.get(0).length];
		for(int i=0;i<data.size();i++)
		{
			for(int j=0;j<sum.length;j++)
			{
				sum[j]+=data.get(i)[j];
			}
		}
		for(int i=0;i<sum.length;i++)
		{
			mean[i]=sum[i]/(1.0*data.size());
			//System.out.println(mean[i]);
		}
		for(int i=0;i<data.size();i++)
		{
			for(int j=0;j<sum.length;j++)
			{
				standard_devition[j]+=(data.get(i)[j]-mean[j])*(data.get(i)[j]-mean[j]);
			}
		}
		for(int i=0;i<standard_devition.length;i++)
		{
			standard_devition[i]/=1.0*data.size();
			standard_devition[i]=Math.sqrt(standard_devition[i]);
		}
		
		for(int i=0;i<data_standard.size();i++)
		{
			for(int j=0;j<standard_devition.length-1;j++)
			{
				data_standard.get(i)[j]=(data_standard.get(i)[j]-mean[j])/standard_devition[j];
			}
		}
	}
	public void print_map()
	{
		for(int i=0;i<l_map.size();i++)
		{
			if(i==l_map.size()-1)
			{
				System.out.print("class "+" ");
				for(Map.Entry<String, Integer> entry : l_map.get(i).entrySet())
				{  
				    System.out.print("  Name = "+entry.getKey()+": "+entry.getValue());  
				    
				}  
				break;
			}
			System.out.print("attribute "+(i+1)+" ");
			for(Map.Entry<String, Integer> entry : l_map.get(i).entrySet())
			{  
			    System.out.print("  Name = "+entry.getKey()+": "+entry.getValue());  
			    
			}  
			System.out.println();
		}
	}
	boolean isNumeric(String str)
	{ 
		  Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+"); 
		   Pattern pattern2=Pattern.compile("^-?[0-9]+");
		   Matcher isNum = pattern.matcher(str);
		   Matcher isnn=pattern2.matcher(str);
		   if( isNum.matches()||isnn.matches() ){
		       return true; 
		   } 
		   return false; 
	}
	public void trans_string_to_numeric(List<String[]> data_s,List<double[]> data_d)
	{
		int countsize;
		 Map<String, Integer> map_attr = null;//new HashMap<>();
		for(int i=0;i<data_s.get(0).length;i++)
		{
			countsize=0;
			int judge=-1;
			for(int j=0;j<data_s.size();j++)
			{
				if(!isNumeric(data_s.get(j)[i]))
				{
					judge=1;
				}
			}
			if(judge!=1)
			{
				continue;
			}
			map_attr=new HashMap<>();
			for(int j=0;j<data_s.size();j++)
			{
				String tempkey=data_s.get(j)[i];
				if(map_attr.containsKey(tempkey))
				{
				    //nothing happen
				}
				else
				{
					countsize++;
					map_attr.put(tempkey,countsize);
				}
			}
			l_map.add(map_attr);
			for(int j=0;j<data_s.size();j++)
			{
				data_s.get(j)[i]=String.valueOf(map_attr.get(data_s.get(j)[i]));
			}
			
		}
		
		double[] temp=null;
		for(int i=0;i<data_s.size();i++)
		{
			temp=new double[data_s.get(0).length];
			for(int j=0;j<temp.length;j++)
			{
				temp[j]=Double.valueOf(data_s.get(i)[j]);
			}
			data_d.add(temp);
		}
		
		
		
	}
//	public void change_to_number(String[] a)
//	{
//			int countsize;
//			Map<String, Double> map_attr = null;//new HashMap<>();
//			//System.out.println(data_s.get(0).length);
//			map_attr=new HashMap<>();
//			for(int i=0;i<a.length;i++)
//			{
//				String tempkey=a[i];
//				
//				if(isNumeric(a[i]))
//				{
//					if(map_attr.containsKey(tempkey))
//					{
//					    //nothing happen
//					}
//					else
//					{
//						map_attr.put(tempkey,Double.valueOf(tempkey));
//					}
//				}
//			}
//			for(int i=0;i<a.length;i++)
//			{
//				String tempkey=a[i];
//				if(!isNumeric(tempkey))
//				{
//					if(map_attr.containsKey(tempkey))
//					{
//					    //nothing happen
//					}
//					else
//					{	
//					
//						countsize=1;
//						while(map_attr.containsKey(String.valueOf(countsize)))
//						{
//							countsize++;
//						}
//						map_attr.put(tempkey,Double.valueOf(countsize));
//					}
//				}
//			}
//			
//			
//		
//	}
	public void trans_evertthing_to_numeric(List<String[]> data_s,List<double[]> data_d)
	{
		int countsize=0;
		 Map<String, Integer> map_attr = null;//new HashMap<>();
		for(int i=0;i<data_s.get(0).length;i++)
		{
			System.out.println(data_s.get(0).length);
			map_attr=new HashMap<>();
			for(int j=0;j<data_s.size();j++)
			{
				String tempkey=data_s.get(j)[i];
				if(map_attr.containsKey(tempkey))
				{
				    //nothing happen
				}
				else
				{
					countsize++;
					map_attr.put(tempkey,countsize);
				}
			}
			l_map.add(map_attr); //record map;
			for(int j=0;j<data_s.size();j++)
			{
				if(map_attr.containsKey(data_s.get(j)[i]))
				{
					data_s.get(j)[i]=map_attr.get(data_s.get(j)[i]).toString();
				}
			}
			countsize=0;
		}
		
		double[] temp=null;
		for(int i=0;i<data_s.size();i++)
		{
			temp=new double[data_s.get(0).length];
			for(int j=0;j<temp.length;j++)
			{
				temp[j]=Double.valueOf(data_s.get(i)[j]);
			}
			data_d.add(temp);
		}
		
		
	}
	public void delete_empty(List<String> rawdata)
	{
		
		for(int i=0;i<rawdata.size();i++)
		{
			int count=0;
			String []temp=rawdata.get(i).split(",");
			for(int q=0;q<temp.length;q++)
			{
				Pattern p = Pattern.compile("\\s*|\t|\r|\n");
				Matcher m = p.matcher(temp[q]);
				temp[q]=m.replaceAll("");
				temp[q].trim();
			}
			for(int j=0;j<temp.length;j++)
			{
				count=j;
				if(temp[j].equals(""))
				{
					//System.out.println("a empty value");
					break;
				}
				if(temp[j].equals("null")||temp[j].equals("?"))
				{
					//System.out.println("a null value");
					break;
				}
			}
			if(count==temp.length-1)
			{
				data_string.add(temp);
			}
		}
	}
	public static void main(String[] args) 
	{
		Preprocessing p=new Preprocessing();
		p.preprocess("D:\\JAVA workspace\\Neural_net\\car.data.txt", "D:\\JAVA workspace\\Neural_net\\");
	}

}
