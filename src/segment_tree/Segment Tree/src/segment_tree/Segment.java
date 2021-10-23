package segment_tree;
import java.util.Scanner;
public class Segment {
	
	int tree[];
    int N;
    int arr1[];
    int minDp[][];
    int maxDp[][];
    //Constructor...
    public Segment() {
    	buildTree();
    }
    
   public void printArray() {
    	for(int i = 0; i < arr1.length; i++) {
    		System.out.print(arr1[i] + " ");
    	}
    	System.out.println();
    }
    
    public void printTree() {
    	for(int i = 0; i < tree.length; i++) {
    		System.out.print(tree[i] + " ");
    	}
    	System.out.println();
    }
    
    private int[] takeInput() {
    	Scanner input = new Scanner(System.in);
    	System.out.println("Enter size of an array...");
    	int n = input.nextInt();
    	N = n;
    	int arr[] = new int[n];
    	for(int i = 1; i <= n; i++) {
    		System.out.println("Enter " + i + " element of an array");
    		arr[i-1] = input.nextInt();
    	}
    	arr1 = arr;
    	input.close();
    	return arr;
    }
//------------------------------ Tree Building --- range sum query --- update query -------------------------------------------------------------    
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

	public void buildTree() {
		
		int arr[] = takeInput();
		
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

	/* There will be total 3 cases
	 * SAY WE REQUIRE SUM IN RANGE 1 - 3
	 * 1] if the current node sum storing window is greater than the required sum window then it is partial overlap hence, make call left and right
	 * 2] if the current node sum storing window is smaller than the required sum window then it is complete overlap,
	 * 	  (i.e. current node window becomes smaller than the required sum window) hence return current window value.
	 * 3] if the current node sum storing window is range is out of the required sum window then just return 0.
	 * 4] finally return left sum and right sum. 
	 * */
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
			// updating the value in the input array
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
//--------------------------------------------------------------------------End------------------------------------------------------------------
	
//------------------------------ Minimum range query --------------------------------------------------------------------------------------------
	
	public void buildMinRangeQuery() {
		minDp = new int[N][N];
		for(int i=0;i<N;i++) {
			minDp[i][i] = arr1[i];
		}
		
		for(int i=0;i<N;i++) {
			for(int j=i+1;j<N;j++) {
				minDp[i][j] = Math.min(arr1[j], minDp[i][j-1]);
			}
		}
	}
	 
	// O(1) complexity
	public int getMin(int left,int right) {
		if(left < 0 || right >= N) {
			return -1;
		}
		buildMinRangeQuery();
		return minDp[left][right];
		
	}
	
	public void buildMaxRangeQuery() {
		maxDp = new int[N][N];
		for(int i=0;i<N;i++) {
			maxDp[i][i] = arr1[i];
		}
		for(int i=0;i<N;i++) {
			for(int j=i+1;j<N;j++) {
				maxDp[i][j] = Math.max(arr1[j], maxDp[i][j-1]);
			}
		}
	}
	
	public int getMax(int left,int right) {
	
		if(left < 0 || right >= N) {
			return -1;
		}
		buildMaxRangeQuery();
		return maxDp[left][right];
		
	}
	
	public void printMinDp() {
		System.out.println("----------------------Dp-------------------------");
		for(int i=0;i<minDp.length;i++) {
			for(int j=0;j<minDp[i].length;j++) {
				System.out.print(minDp[i][j] +" ");
			}
			System.out.println();
		}
		System.out.println("----------------------Dp-------------------------");
		
	}
	
	public void printMaxDp() {
		System.out.println("----------------------Dp-------------------------");
		for(int i=0;i<maxDp.length;i++) {
			for(int j=0;j<maxDp[i].length;j++) {
				System.out.print(maxDp[i][j] +" ");
			}
			System.out.println();
		}
		System.out.println("----------------------Dp-------------------------");
		
	}
	
	
}
