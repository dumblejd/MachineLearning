package neural;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Read_Save {
	int datasize=0;
	int attributesize=0;
	
	public void save(String filepath, List<double[]> data)
	{
		 boolean isSucess=false;
	        
	        FileOutputStream out=null;
	        OutputStreamWriter osw=null;
	        BufferedWriter bw=null;
	        try {
	            out = new FileOutputStream(filepath);
	            osw = new OutputStreamWriter(out);
	            bw =new BufferedWriter(osw);
	            if(data!=null && !data.isEmpty())
	            {
	                for(int i=0;i<data.size();i++)
	                {
	                		for(int j=0;j<data.get(0).length;j++)
	                		{
	                			bw.append(String.valueOf(data.get(i)[j]));
	                			if(j==data.get(0).length-1)
	                			{
	                				bw.append("\n");
	                			}
	                			else
	                			{
	                				bw.append(",");
	                			}
	                		}
	                }
	            }
	            isSucess=true;
	        } catch (Exception e) {
	            isSucess=false;
	        }finally{
	            if(bw!=null){
	                try {
	                    bw.close();
	                    bw=null;
	                } catch (IOException e) {
	                    e.printStackTrace();
	                } 
	            }
	            if(osw!=null){
	                try {
	                    osw.close();
	                    osw=null;
	                } catch (IOException e) {
	                    e.printStackTrace();
	                } 
	            }
	            if(out!=null){
	                try {
	                    out.close();
	                    out=null;
	                } catch (IOException e) {
	                    e.printStackTrace();
	                } 
	            }
	        }
	       // System.out.println("save action:"+isSucess  +"   you should creat a txt for saving");
	}
	public List<String> read(String filepath)
	{
	    File csv = new File(filepath);  
	    BufferedReader br = null;
	    try
	    {
	        br = new BufferedReader(new FileReader(csv));
	    } catch (FileNotFoundException e)
	    {
	        e.printStackTrace();
	    }
	    String line = "";
	    String everyLine = "";
	    try 
	    {
	            List<String> allString = new ArrayList<>();
	            while ((line = br.readLine()) != null)  
	            {   if(line.equals(""))
		            {
		            //	System.out.println("gap");
		            }
	                else
		            {
		                everyLine = line;
		                allString.add(everyLine);
		            }
	            }
	            datasize=allString.size();
	            //System.out.println("size:"+allString.size());
	            String[]a=allString.get(0).split(",");
	            this.attributesize=a.length-1;
	            return allString;
	    } 
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	   
	    return null;
	}
	public static void main(String[] args) {
		Read_Save a=new Read_Save();
		a.read("D:\\JAVA workspace\\Neural_net\\car.data");

	}

}
