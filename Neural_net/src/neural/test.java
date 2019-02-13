package neural;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
	boolean isNumeric(String str)
	{ 
		Pattern pattern = Pattern.compile("[0-9]*"); 
		return pattern.matcher(str).matches(); 
	} 
	public boolean issNumeric(String str){ 
		   Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+"); 
		   Pattern pattern2=Pattern.compile("^-?[0-9]+");
		   Matcher isNum = pattern.matcher(str);
		   Matcher isnn=pattern2.matcher(str);
		   if( isNum.matches()||isnn.matches() ){
		       return true; 
		   } 
		   return false; 
		}
	public static void main(String[] args) {
//		String a="a,q,,e,e";
//		String b[]=a.split(",");
//		for(int i=0;i<b.length;i++)
//		{
//			System.out.println(b[i]);
//		}
		System.out.println(Double.valueOf("5"));
		String a="5";
		double c=0.22;
		int size=1001;
		System.out.println((int)(c*size));

		double[][]q=new double[3][2];
		System.out.println(q.length);
		
		
		Map<Double, Integer> map_attr=new HashMap<>();
		map_attr.put(2.0, 1);
		map_attr.put(2.0, 1);
		map_attr.put(3.0, 1);
		System.out.println(map_attr.size());
		
		test w=new test();
		System.out.println(w.issNumeric("4143747"));
		
		Map<Double,Double>map_out=new HashMap<Double,Double>();
		map_out.put(1.0, 1.0);
		map_out.put(1.5, 1.5);
		System.out.println(map_out.size());

		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		String str=" 	this is test text	!	";
		Matcher m = p.matcher(str);
		String outStr = m.replaceAll("");
		System.out.println(outStr); 
		
		 String t=" 300528";
		 Matcher tt = p.matcher(t);
		 String gg=tt.replaceAll("");
		 System.out.println(gg);
	}
}

