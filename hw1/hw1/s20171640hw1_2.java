package hw1;

public class s20171640hw1_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] num = new int[5];
		int flag = 0, it = 0, i, j;
		
		while(flag < 3) {
			it++;
			
			// 초기화
			flag = 0;
			for (i = 0; i < 5; i++) {
				num[i] = -1;
			}
			
			//할당
			for (i = 0; i < 5; i++) {
				num[i] = (int)(50*Math.random());
				for (j = 0; j < i; j++) {
					if (num[j] == num[i]) {
						i--;
						break;
					}
				}
			}
			
			//확인
			for (i = 0; i< 5; i++) {
				if (num[i] == 7 || num[i] == 18 || num[i] == 32 || num[i] == 37 || num[i] == 44)
					flag++;
			}
		}
		
		//출력
		System.out.print("final random numbers : ");
		for (i = 0; i < 5; i++) {
			System.out.print(num[i]+" ");
		}
		System.out.println("\nthe number of iterations : " + it);
	}

}
