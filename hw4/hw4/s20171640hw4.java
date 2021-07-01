package hw4;

import java.awt.*;          
import java.awt.event.*;

class WindowDestroyer extends WindowAdapter
{
    public void windowClosing(WindowEvent e) 
    {
        System.exit(0);
    }
}

public class s20171640hw4 implements ActionListener {  
	private TextArea screen;
	private String cal = "", ans = "";
	
	public s20171640hw4(){
		Frame f;
		f = new Frame("Calculator");
		f.setLayout(new GridLayout(2, 1));
		
		screen = new TextArea("", 2, 50, TextArea.SCROLLBARS_NONE);
		screen.setEditable(false);

		Button button[] = new Button[20];
		
		for (int i = 0; i < 15; i++)
			if (i % 5 < 3)
				button[i] = new Button(""+ (i + 7 - 8 * (i / 5)));
		
		button[3] = new Button("/");
		button[4] = new Button("C");
		button[8] = new Button("*");
		button[9] = new Button("<-");
		button[13] = new Button("-");
		button[14] = new Button("(");
		button[15] = new Button("0");
		button[16] = new Button(".");
		button[17] = new Button("="); 
		button[18] = new Button("+");
		button[19] = new Button(")");
		
		for(int i = 0; i < 20; i++)
			button[i].addActionListener(this);
		
		Panel result, key;
		
		result = new Panel();
		key = new Panel();
		key.setLayout(new GridLayout(4, 5));

		result.add(screen);
		for(int i = 0; i < 20; i++)
			key.add(button[i]);
		
		f.add(result);
		f.add(key);
		f.pack();
		
	    f.setSize(500, 300);
	    f.setVisible(true);   
	    
		WindowDestroyer listener = new WindowDestroyer();
	    f.addWindowListener(listener);
	}
	
	public double basic_operation(String exp) {
		boolean minusflag = false;
		
		if (exp.charAt(0) == '-') {
			exp = exp.substring(1);
			minusflag = true;
		}
		
		int minusidx[] = new int[50];
		int k = 0;
		
		int numcnt = 1;
		for (int i = 0; ; i++) {
			if (i == exp.length())
				break;
			if (exp.charAt(i) == '+' || exp.charAt(i) == '-' || exp.charAt(i) == '*' || exp.charAt(i) == '/') {
				if (exp.charAt(i + 1) == '-') {
					exp = exp.substring(0, i + 1) + exp.substring(i + 2);
					i++;
					minusidx[k] = numcnt;
					k++;
				}
					numcnt++;
			}
		}
		
		String[] num = exp.split("-|\\+|\\*|/");
		String[] op = exp.split("0|1|2|3|4|5|6|7|8|9|\\.");
		
		if (minusflag == true)
			num[0] = "-" + num[0];
		
		double answer = Double.parseDouble(num[0]);
		double tmp;
		int j = 1, prevj = 0;
		for (int i = 0; i < op.length; i++) {
			for (k = 0; k < num.length; k++) {
				if (j == minusidx[k] && j != prevj) {
					num[j] = "-" + num[j];
					break;
				}
			}
			prevj = j;
			tmp = Double.parseDouble(num[j]);
			if (op[i].equals("-")) {
				answer -= tmp;
				j++;
			}
			else if (op[i].equals("+")){
				answer += tmp;
				j++;
			}
			else if (op[i].equals("*")) {
				answer *= tmp;
				j++;
			}
			else if (op[i].equals("/")) {
				answer /= tmp;
				j++;
			}
		}
		
		return answer;
	}
	
	public void calculate(String exp) {
		int lpar = 0, rpar = 0;
		
		for(int i = 0; i < exp.length(); i++) {
			if (exp.charAt(i) == '(')
				lpar++;
			else if (exp.charAt(i) == ')')
				rpar++;
		}
		
		if (lpar != rpar) {
			ans = "\nerror: wrong expression";
			return;
		}
		
		int lidx = -1, ridx = -1;
		for (int i = 0; i < lpar; i++) {
			String tmpexp = "";
			for (int j = 0; j < exp.length(); j++) {
				if (exp.charAt(j) == '(') {
					lidx = j;
				}
				else if (exp.charAt(j) == ')') {
					ridx = j;
					if (lidx == -1) {
						ans = "\nerror: wrong expression";
						return;
					}
					break;
				}
			}
			tmpexp = exp.substring(lidx+ 1, ridx);
			tmpexp = Double.toString(basic_operation(tmpexp));
			exp = exp.substring(0, lidx) + tmpexp + exp.substring(ridx + 1, exp.length());
		}
		
		double answer;
		answer = basic_operation(exp);
		if ((int)answer == answer)
			ans = "\n" + (int)answer;
		else
			ans = "\n" + answer;
	}
		
	public void actionPerformed(ActionEvent e) {
		Button btn = (Button)e.getSource();
		String str = btn.getLabel();
		char input = str.charAt(0);
		
		if(!ans.isEmpty()) {
			ans = "";
			cal = "";
			screen.setText("");
		}
		
		if ((input >= '0' && input <= '9') || input == '(' || input == ')')
			screen.append(str);
		else if (input == '+' || input == '-' || input == '*' || input == '/' || input == '=') {
			if (!cal.isEmpty()) {
				int idx = (int)cal.length() - 1;
				char prev = cal.charAt(idx);
				
				if (prev == '+' || prev == '-' || prev == '*' || prev == '/')
					screen.replaceRange(str, idx, idx + 1);
				else
					screen.append(str);
			}
			else if (input == '-')
				screen.append(str);
		}
		else if (input == '.') {
			if (!cal.isEmpty()) {
				int idx = (int)cal.length() - 1;
				char prev = cal.charAt(idx);
				
				if (prev == '+' || prev == '-' || prev == '*' || prev == '/')
					screen.append("0.");
				else if (prev != '.')
					screen.append(str);
			}
			else
				screen.append("0.");
		}
		else if (input == 'C')
			screen.setText("");
		else if (input == '<') {
			if (!cal.isEmpty())
				screen.replaceRange("", cal.length() - 1, cal.length());
		}
		
		cal = screen.getText();
		
		if (input == '=')
			if (!cal.isEmpty()) {
				cal = cal.replace("=", "");
				calculate(cal);
				screen.append(ans);
			}
	}

	public static void main(String args[]) {
		s20171640hw4 calculator = new s20171640hw4();
	}
}

