
/**
 	 *@category this is a single attribute of a condition (e.g. high of height)
	 * @param count one count of e.g. high/low/normal
	 * @param entropy entropy of this attribute(one of the e.g. high/low/normal)
	*/
public class Attribute 
{
	double count;  //one count of high/low/normal
	double entropy;
	
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	public double getEntropy() {
		return entropy;
	}
	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}
}
