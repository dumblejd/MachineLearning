package IDDDD;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.LinkedList;

public class MyID3 {

    private static LinkedList<String> attribute = new LinkedList<String>(); // �洢���Ե�����
    private static LinkedList<ArrayList<String>> attributevalue = new LinkedList<ArrayList<String>>(); // �洢ÿ�����Ե�ȡֵ
    private static LinkedList<String[]> data = new LinkedList<String[]>();; // ԭʼ����
   
    public static final String patternString = "@attribute(.*)[{](.*?)[}]";
	public static String[] yesNo;
	public static TreeNode root;
	


    /**
     * 
     * @param lines ����Ҫ���������ݼ�
     * @param index �ĸ����ԣ�attribute��index
     */
    public Double getGain(LinkedList<String[]> lines,int index)
    
    {
	    	Double gain=-1.0;
	    	List<Double> li=new ArrayList<Double>();
	    	//ͳ��Yes No�Ĵ���
	    	for(int i=0;i<yesNo.length;i++)
	    	{
	    		Double sum=0.0;
	    		for(int j=0;j<lines.size();j++)
	    		{
	    			String[] line=lines.get(j);
	    			//dataΪ�ṹ������,����������һ��==yes,sum+1
	    			if(line[line.length-1].equals(yesNo[i]))
	    			{
	    				sum=sum+1;
	    			}
	    		}
	    		li.add(sum);
	    	}
	    	//����Entropy(S)����Entropy(S) ���ο��顶����ѧϰ ��Tom.Mitchell��  ��3.4.1.2��
	    	Double entropyS=TheMath.getEntropy(lines.size(), li);   //li����ŵ���getGainʱ���������ж���yes ��no
	    	//�������gain
	    	
	    	List<String> la=attributevalue.get(index);    //la�� ���Ե�����  temp��weather��health
	    	List<Point> lasv=new ArrayList<Point>();
	    	for(int n=0;n<la.size();n++)
	    	{
	    		String attvalue=la.get(n);   //attvalue ��������Եĵ�n����� sunny rain fost
	        	//ͳ��Yes No�Ĵ���
	    		List<Double> lisub=new ArrayList<Double>();//�磺sunny ��yesʱ�����Ĵ�������no�����Ĵ���
	    		Double Sv=0.0;//��ʽ3.4�е�Sv ���ο��顶����ѧϰ(Tom.Mitchell��)��
	        	for(int i=0;i<yesNo.length;i++)
	        	{
	        		Double sum=0.0;
	        		for(int j=0;j<lines.size();j++)
	        		{
	        			String[] line=lines.get(j);
	        			//dataΪ�ṹ������,����������һ��==yes,sum+1
	        			if(line[index].equals(attvalue)&&line[line.length-1].equals(yesNo[i]))
	        			{
	        				sum=sum+1;
	        			}
	        		}
	        		Sv=Sv+sum;//��������
	        		lisub.add(sum);
	        	}
	        	//����Entropy(S) ���ο��顶����ѧϰ(Tom.Mitchell��)��
	        	Double entropySv=TheMath.getEntropy(Sv.intValue(), lisub);
	        	//
	        	Point p=new Point();
	        	p.setSv(Sv);
	        	p.setEntropySv(entropySv);
	        	lasv.add(p);
	    	}
	    	gain=TheMath.getGain(entropyS,lines.size(),lasv);
	    	return gain;
   }
    //Ѱ��������Ϣ����,���������Զ�Ϊ��ǰ�ڵ㣬�����ظ���������list��λ�ú�gainֵ
    public Maxgain getMaxGain(LinkedList<String[]> lines)
    {
	    	if(lines==null||lines.size()<=0){    
	    		return null;
	    	}
	    	Maxgain maxgain = new Maxgain();
	    	Double maxvalue=0.0;
	    	int maxindex=-1;
	    	for(int i=0;i<attribute.size();i++){
	    		Double tmp=getGain(lines,i);
	    		if(maxvalue< tmp){
	    			maxvalue=tmp;
	    			maxindex=i;
	    		}
	    	}
	    	maxgain.setMaxgain(maxvalue);
	    	maxgain.setMaxindex(maxindex);
	    	return maxgain;
    }
    //��ȡ����
    public LinkedList<String[]>  filterLines(LinkedList<String[]> lines, String attvalue, int index)   //�ѵ�������sunny�����Զ��س���
    {
	    	LinkedList<String[]> newlines=new LinkedList<String[]>();
	    	for(int i=0;i<lines.size();i++){
	    		String[] line=lines.get(i);
	    		if(line[index].equals(attvalue)){
	    			newlines.add(line);
	    		}
	    	}
	    	
	    	return newlines;
    }
    
    public void createDTree()
    {
	    	root=new TreeNode();
	    	Maxgain maxgain=getMaxGain(data);
	    	if(maxgain==null){
	    		System.out.println("û�����ݼ�������!");
	    	}
	    	int maxKey=maxgain.getMaxindex();
	    	String nodename=attribute.get(maxKey);
	    	root.setName(nodename);
	    	root.setLiatts(attributevalue.get(maxKey));
	    	insertNode(data,root,maxKey);
    }
    /**
     * 
     * @param lines ��������ݼ�����Ϊ�µĵݹ����ݼ�
     * @param node ����˽ڵ�
     * @param index ����λ��
     */
    public void insertNode(LinkedList<String[]> lines,TreeNode node,int index){
    	List<String> liatts=node.getLiatts();//liatts�ŵ������Ե�  �������   sunny rain fost
    	for(int i=0;i<liatts.size();i++)
    	{
    		String attname=liatts.get(i);
    		LinkedList<String[]> newlines=filterLines(lines,attname,index);
    		if(newlines.size()<=0)
    		{
    	    	System.out.println("�����쳣��ѭ������");
    	    	return;
    	    }
    		Maxgain maxgain=getMaxGain(newlines);
    		double gain=maxgain.getMaxgain();
    		Integer maxKey=maxgain.getMaxindex();
    		//������0�����ݹ飬����0˵����Ҷ�ӽڵ㣬�����ݹ顣
    		if(gain!=0){
    			TreeNode subnode=new TreeNode();
    			subnode.setParent(node);
    			subnode.setFatherAttribute(attname);
    			String nodename=attribute.get(maxKey);
    			subnode.setName(nodename);
    			subnode.setLiatts(attributevalue.get(maxKey));
    			node.addChild(subnode);
    			//������0�������ݹ�
    			insertNode(newlines,subnode,maxKey);
    		}else{
    			TreeNode subnode=new TreeNode();
    			subnode.setParent(node);
    			subnode.setFatherAttribute(attname);
    			//Ҷ�ӽڵ���yes����no?ȡ���������һ������������,��Ϊֻ����ȫ��yes,����ȫ��no������²Ż���Ҷ�ӽڵ�
    			String[] line=newlines.get(0);
    			String nodename=line[line.length-1];
    			subnode.setName(nodename);
    			node.addChild(subnode);
    		}
    	}
    }
	//���������
	public void printDTree(TreeNode node)
	{
		if(node.getChildren()==null){
			System.out.println("--"+node.getName());
			return;
		}
		System.out.println(node.getName());
		List<TreeNode> childs = node.getChildren();
		for (int i = 0; i < childs.size(); i++)
		{
			System.out.println(childs.get(i).getFatherAttribute());
			printDTree(childs.get(i));
		}
	}
    public static void main(String[] args) {
		// TODO Auto-generated method stub
    	MyID3 myid3 = new MyID3();
    	myid3.readARFF(new File("//Users//dijin//eclipse-workspace//ID3_hub//weather.nominal.arff"));
    	myid3.createDTree();
    	myid3.printDTree(root);
	}
    //��ȡarff�ļ�����attribute��attributevalue��data��ֵ
    public void readARFF(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            Pattern pattern = Pattern.compile(patternString);
            while ((line = br.readLine()) != null) {
            	if (line.startsWith("@decision")) {
                   line = br.readLine();
                        if(line=="")
                            continue;
                        yesNo = line.split(",");
                }
            	Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    attribute.add(matcher.group(1).trim());
                    String[] values = matcher.group(2).split(",");
                    ArrayList<String> al = new ArrayList<String>(values.length);
                    for (String value : values) {
                        al.add(value.trim());
                    }
                    attributevalue.add(al);
                } else if (line.startsWith("@data")) {
                    while ((line = br.readLine()) != null) {
                        if(line=="")
                            continue;
                        String[] row = line.split(",");
                        data.add(row);
                    }
                } else {
                    continue;
                }
            }
            br.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}