import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Preprocessing 
{
	public List<Point> read_text(String filepath)
	{
		File file=new File(filepath);
		List<Point> pl=new ArrayList<Point>();
		if(file.isFile() && file.exists())
		{
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                 
                StringBuffer sb = new StringBuffer();
                String text = null;
                while((text = bufferedReader.readLine()) != null)
                {
                    String point[]=text.split("	");
                    if(!point[0].equals("id"))
                    {
                    	Point temp_p=new Point(Integer.parseInt(point[0]),Double.parseDouble(point[1]),Double.parseDouble(point[2]));
                    	pl.add(temp_p);
                    }
                }
                return pl;
                
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
		return null;
	}
	public static void main(String[] args) 
	{
		 Preprocessing a=new Preprocessing();
		 a.read_text("L:\\Eclipse_workspace\\Kmean\\src\\test_data.txt");
	}
	public void write_txt(String outfilepath, List<List<Point>> output) throws IOException 
	{
		File a = new File(outfilepath);
		if(a==null)
		{
			return;
		}
		try {

//			FileOutputStream fileOutputStream = new FileOutputStream(outfilepath);
//			OutputStreamWriter OutputStreamReader = new OutputStreamWriter(fileOutputStream);
			FileWriter f = new FileWriter(outfilepath);
	        BufferedWriter bufferedWriter = new BufferedWriter(f);
	        
	        for(int i=0;i<output.size();i++)
	        {
	         	bufferedWriter.write(i+1);
	         	bufferedWriter.write("	");
	         	for(int j=0;j<output.get(i).size();j++)
	         	{
	         		
	         		if(j!=0)
	         		{
	         		bufferedWriter.write(",");
	         		bufferedWriter.write(output.get(i).get(j).number);
	         		}
	         		else
	         		{
	         		bufferedWriter.write(output.get(i).get(j).number);
	         		}
	         	}
	         	bufferedWriter.newLine();
	        }
	        bufferedWriter.flush();
	        bufferedWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
