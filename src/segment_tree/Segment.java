package segment_tree;
import java.util.Scanner;
public class Segment {
	
	int tree[];
    int N;
    int arr1[];
    
    public Segment() {
    	buildTree();
    }
    
    private int[] takeInput() {
    	Scanner input = new Scanner(System.in);
    	System.out.println("Enter size of an array...");
    	int n = input.nextInt();
    	int arr[] = new int[n];
    	for(int i = 1; i <= n; i++) {
    		System.out.println("Enter " + i + " element of an array");
    		arr[i-1] = input.nextInt();
    	}
    	input.close();
    	return arr;
    }
    
	private boolean isTwosPower(int N, int two) {

		if (two > N) {
			return false;
		} else if (two == N) {
			return true;
		}
		return isTwosPower(N, 2 * two);

	}

	private int nextTwosPower(int N, int two) {
		if (two > N) {
			return two;
		}
		return nextTwosPower(N, 2 * two);
	}

	private int getMid(int startIndex,int endIndex) {
		return (startIndex + endIndex)/2;
	}
	
	private void buildTreeHelper(int arr[], int tree[], int startIndex, int endIndex, int treeIndex) {

		if (startIndex >= endIndex) {
			tree[treeIndex] = arr[startIndex];
			return;
		}

		int mid = getMid(startIndex,endIndex);

		buildTreeHelper(arr, tree, startIndex, mid, 2 * treeIndex);
		buildTreeHelper(arr, tree, mid + 1, endIndex, 2 * treeIndex + 1);
		
		tree[treeIndex] = tree[treeIndex * 2] + tree[treeIndex * 2 + 1];
	
			 
	}
	
	public void printTree() {
		for(int i=0;i<tree.length;i++) {
			System.out.print(tree[i] + " ");
		}
	}

	public void buildTree() {
		
		int arr[] = takeInput();
		arr1 = arr;
		N = arr.length;
		if(isTwosPower(N,2)) {
			tree = new int[2*N];
			buildTreeHelper(arr,tree,0,arr.length-1,1);
		}else {
			int ans = nextTwosPower(N,2);
			tree = new int[(2*N) + Math.abs(2*N-ans)];
			buildTreeHelper(arr,tree,0,arr.length-1,1);
		}
		System.out.println("Segment tree built successfully...");
	}
	
	public int getSum(int start,int end){
		
		return getSumHelper(0,arr1.length-1,1,start,end);
		
	}

	private int getSumHelper(int currentI, int currentJ,int treeIndex, int start, int end) {
		//if our currentI and currentJ window fits in the window of which sum is required then just return tree[treeIndex];
		//total overlap case
		if(currentI >= start && currentJ <= end) {
			return tree[treeIndex];
		//if end of current window is < than start of which sum is required then out of range case...return 0 similarly if,
		//start of current window is greater than the end of window whose sum is requires then also out of range case hence return 0... 
		}if(currentJ < start || currentI > end) {
			return 0;
		}
		//otherwise return sum of left call and right call....
		else {
			int mid = getMid(currentI,currentJ);
			int left = getSumHelper(currentI,mid,2*treeIndex,start,end);
			int right = getSumHelper(mid+1,currentJ,2*treeIndex+1,start,end);
			return left+right;
		}
		
	}
	
	public void update (int index,int element) {
		if(index < 0 || index > arr1.length) {
			System.out.println("Enter Valid Index...");
			return;
		}else {
			int value = element-arr1[index];
			arr1[index] = element;
			update(0,arr1.length-1,1,value,index);
		}
		
	}

	private void update(int currentI, int currentJ, int treeIndex, int value, int index) {
		
		if(currentI > index || currentJ < index) {
			return;
		}
		tree[treeIndex] += value;
		if(currentI != currentJ) {
			int mid = getMid(currentI,currentJ);
			update(currentI,mid,2*treeIndex,value,index);
			update(mid+1,currentJ,2*treeIndex+1,value,index);
		}
		
	}
	

}
