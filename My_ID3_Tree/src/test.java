import java.util.ArrayList;
import java.util.List;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s=new String("aa;bb;cc;dd");
		String[] ss=s.split(";");
		List list=new ArrayList();
		int a=1;
		int b=2;
		list.add(a);
		list.add(b);
		list.remove(0); //删除了第三个元素
		list.remove(0);
		System.out.println(list.size());
	}

}
