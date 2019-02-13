import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kmeans 
{
	List<Point> lp;   //raw points that haven't been classified
	List<List<Point>> output = new ArrayList<List<Point>>(); // Output of classification
	int maxiter = 25;
	List<Point> centers;
	int num_cen;
	
	public void fit(int Num_cluster,String filepath,String Outfilepath) throws IOException
	{
		num_cen = Num_cluster;
		Preprocessing p=new Preprocessing();
		//init center
		lp = p.read_text(filepath);
		init_center(lp,Num_cluster);
		
		classify(centers,1);
		
		p.write_txt(Outfilepath,output);
		System.out.println();
	}
	public void classify(List<Point> precenter,int iter)
	{
		List<List<Point>> out = new ArrayList<List<Point>>();
		init_output(out);
		
		for(Point point : lp)
		{
			int index=0;
			double maxscore=cal_distance(point,precenter.get(0));
			for(int i = 0;i< precenter.size();i++)
			{
				if(cal_distance(point,precenter.get(i))>maxscore)
				{
					maxscore = cal_distance(point,precenter.get(i));
					index = i;
				}
			}
			out.get(index).add(point);
		}
		for(int i=0;i<centers.size();i++)
		{
			double tx=0;
			double ty=0;
			for(int j=0;j<out.get(i).size();j++)
			{
				tx+=out.get(i).get(j).x;
				ty+=out.get(i).get(j).y;
			}
			centers.get(i).x=tx/out.get(i).size();
			centers.get(i).y=ty/out.get(i).size();
		}
		if(iter>maxiter)
		{
			this.output=out;
			System.out.println(maxiter+" iter finished");
			return;
		}
		classify(centers,iter+1);
	}
	public void init_output(List<List<Point>> a)
	{
		for(int i=0;i<num_cen;i++)
		{
		List<Point> t= new ArrayList<Point>();
		a.add(t);
		}
	}
	
	public void init_center(List<Point> points,int Num_cluster)//random take first num_cluster as center
	{
		centers = new ArrayList<Point>();
		Collections.shuffle(points);
		for(int i=0;i<Num_cluster;i++)
		{
			centers.add(points.get(i));
		}
	}
	public double cal_distance(Point a,Point b)  //small is better!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	{
		double distance=Math.sqrt((a.x-b.x)*(a.x-b.x)+(a.y-b.y)*(a.y-b.y));
		return distance*-1.0;  //small is better
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Kmeans k=new Kmeans();
		try {
			k.fit(3, "/Users/dijin/eclipse-workspace/Kmean/src/test_data.txt", "/Users/dijin/Desktop/output.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
