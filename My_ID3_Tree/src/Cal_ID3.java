import java.util.List;


public class Cal_ID3 
{
	/**
	 * 
	 * @param count_instance aomount of instance
	 * @param frequence_attr frequence of attribute
	 * @return
	 */
	public double getEntropy(int count_instance, List<Double> frequence_attr)
	{
		double entropy=0;
		for(int i=0;i<frequence_attr.size();i++)
		{
			entropy = entropy + sigma(frequence_attr.get(i),1.0*count_instance);
		} 
		return entropy;
	}
	
	/**
	 * @param exist_entropy  entropy of upper node
	 * @param S total number of instance of upper node
	 * @return information gain
	 */
	
	public double cal_Gain(double exist_entropy,int S,List<Attribute> attr){
		double gain=0;
		double sum_entropy=0;
		
		for(int i=0;i<attr.size();i++)
		{
			Attribute a=attr.get(i);
			sum_entropy=sum_entropy+((a.getCount() / (S*1.0))*a.getEntropy());
		}
		
		gain=exist_entropy-sum_entropy;
		return gain;
	}
	
	public static  double sigma(double x, double total)
	{
		if (x == 0)
		{
			return 0.0;
		}
		double p = (x*1.0) / (total*1.0);
		return -(p*log_base_2(p));
	}
	
	public static double log_base_2(double x)
	{
		return Math.log(x) / Math.log(2);
	}
}
