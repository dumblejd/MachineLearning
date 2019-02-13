import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Read_from_csv {

	int datasize=0;
	int attributesize=0;
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
	            {
	                everyLine = line;
	                allString.add(everyLine);
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
//	public static void main(String[] args)
//	{
//		Read_from_csv a=new Read_from_csv();
//		List<String> b=a.read("a");
//	}
}
