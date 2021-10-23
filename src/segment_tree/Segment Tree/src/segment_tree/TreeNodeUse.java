package segment_tree;

public class TreeNodeUse {

	public static void main (String args[]){
		
		Segment obj = new Segment();
		System.out.println("sum = " + obj.getSum(1, 3));
//		obj.update(2, 15);
//		System.out.println("sum = " + obj.getSum(1, 3));
//		obj.printArray();
		obj.printArray();
		obj.printTree();
		System.out.println();
		
		
		System.out.println("max = "+obj.getMax(1, 3));
		System.out.println("min = "+obj.getMin(1, 3));
		obj.printMaxDp();
		obj.printMinDp();
	}
	
}


