package hw2;

public class s20171640hw2_1 {
	public static int fx1[] = {-5, 0, 1};
	public static int fx2[] = new int[fx1.length];
	public static int it = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 0;
		float x0 = 2;
		
		while (i < (fx1.length - 1)) {
			fx2[i] = fx1[i + 1] * (i + 1);
			i++;
		}
		
		System.out.print("f'(x) =");
		for (i = fx1.length - 2; i > 0; i--) {
			System.out.print(" " + fx2[i] + "x^" + i + " +");
		}
		System.out.println(" " + fx2[i] + "x^" + i);
		
		newton(x0);
	}
	
	public static float newton(float prevx) {
		float consx;
		float value1 = 0, value2 = 0;
		int i;
		float pow = 1;
		
		it++;
		
		for (i = 0; i < fx1.length; i++) {
			value1 += fx1[i] * pow;
			pow *= prevx;
		}
		
		pow = 1;
		for (i = 0; i < fx1.length - 1; i++) {
			value2 += fx2[i] * pow;
			pow *= prevx;
		}
		
		consx = prevx - value1 / value2;
		
		String str = String.format("%.5f", consx);
		
		if (it ==1)
			System.out.println("x1 = " + str + "\n");
		
		if (consx == prevx)
			return consx;
		else {
			System.out.println("Iteration " + it + " : " + str);
			return newton(consx);
		}
	}
}
