package IDDDD;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TheMath {
	
	//��Ϣ�صļ��㹫ʽ�����������ɢ�Ͷ���ֲ����ؼ���
	/**
	 * 
	 * @param S ��������
	 * @param li �����¼������Ĵ���
	 * @return
	 */
	public static  Double getEntropy(Integer S,List<Double> li){
		
		Double entropy=new Double(0.0);
		for(int i=0;i<li.size();i++){
			entropy=entropy+sigma(li.get(i),Double.valueOf(S));
		}
		return entropy;
	}
	//��Ϣ���湫ʽ
	/**
	 * @param �˹�ʽ��ȫ����  ������ѧϰ(Tom.Mitchell��)��3.4��
	 * @param entropyS S����Ϣ��
	 * @param S ���������
	 * @param lasv������������ sv ��sv�ĸ���,sv.entropysv��entropy(sv)
	 * @return ������Ϣ����
	 */
	
	public static Double getGain(Double entropyS,int S,List<Point> lasv){
		Double gain=new Double(0.0);
		Double enSum=new Double(0.0);
		Map.Entry<Double, Double>entry;
		for(int i=0;i<lasv.size();i++){
			Point p=lasv.get(i);
			enSum=enSum+((p.getSv()/Double.valueOf(S))*p.getEntropySv());
		}
		
		gain=entropyS-enSum;
		return gain;
	}
	//��ʽ -pi*log2(x)
	public static Double sigma(Double x, Double total)
	{
		if (x == 0)
		{
			return 0.0;
		}
		double x_pi = getProbability(x,total);
		return -(x_pi*logYBase2(x_pi));
	}

	//ȡ2Ϊ�׵Ķ���
	public static double logYBase2(double y)
	{
		return Math.log(y) / Math.log(2);
	}
	
	//�ȿ����¼�����
	public static double getProbability(double x, double total)
	{
		return x * Double.parseDouble("1.0") / total;
	}
}
