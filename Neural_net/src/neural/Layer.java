package neural;

public class Layer 
{
	double step; 
	int type; //Type:{0,1,2 } input ,hidden layer,output
	double[]input;  //Input[attri_num+1]  (bias,color_car,height_car,brand_car)
	double[][]weight;//Weight[ num_of_node_thislayer]  [num_of_node_lastlayer+1]        +1:for bias in last layer
	double[]value; //value[num_of_thislayer+1]   +1:for bias in this layer //{first is bias, input[0]xweight[1][0]+input[1]xweight[1][1]+input[2]xweight[1][2]
	double[]delta; //delta[num_of_thislayer]
	
	public void initial_layer(int num_node_last,int num_node_this,int type)
	{
		this.type=type;
		if(this.type==1)
		{
		input=new double[num_node_last+1];
		weight=new double[num_node_this][num_node_last+1];
		value=new double[num_node_this+1];
		delta=new double[num_node_this];
		}
		if(this.type==0)
		{
			input=new double[num_node_last+1];
			value=new double[num_node_this+1];
			delta=new double[num_node_this];
			weight=null;
		}
		if(this.type==2)
		{
			input=new double[num_node_last+1];
			weight=new double[num_node_this][num_node_last+1];
			value=new double[num_node_this];
			delta=new double[num_node_this];
		}
		
	}
	
	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double[] getInput() {
		return input;
	}

	public void setInput(double[] input) {
		this.input = input;
	}

	public double[][] getWeight() {
		return weight;
	}

	public void setWeight(double[][] weight) {
		this.weight = weight;
	}

	public double[] getValue() {
		return value;
	}

	public void setValue(double[] value) {
		this.value = value;
	}

	public double[] getDelta() {
		return delta;
	}

	public void setDelta(double[] delta) {
		this.delta = delta;
	}

	public static void main(String[] args) 
	{
	
	}

}
