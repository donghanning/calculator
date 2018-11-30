package com.example.wangliyong.calculator;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Stack;

public class PostfixExpression {
	private Stack<Double> peStack = new Stack<Double>();
	private double data1, data2;
	private Data[] ch;
	private Data[] dataArray;
	String add=String.valueOf('+');

	PostfixExpression(String str) {
		// 首先将字符串转换成中缀表达式
		this.dataArray = split(str);
		// 其次将中缀表达式转换成后缀表达式
		this.ch = transform(dataArray);
	}
	
	public enum oprEnum{
		
		ln,lg,sin,cos,tan,add;
	}

	public double calculate() {
		//计算过程
		for (int i = 0; i < this.ch.length; i++)
			if (ch[i].f)
				//如果当前字符为数字，将其压入数据栈中
				peStack.push(Double.parseDouble(ch[i].str));
			else {
				//判断当前操作符是否是双目运算符
				if (compareOpr(ch[i].str)) {
					// 判断是否是进行阶乘运算
					if (ch[i].str.equals((String) "!")) {
						// 如果是进行阶乘运算，分两种情况
						// 第一种：当前栈内有不止一个元算，则连续弹出两个元素，再讲第一个弹出的元素压入栈中，取第二个元素运算
						data1 = peStack.pop();
						if (!peStack.empty()) {
							data2 = peStack.pop();
							peStack.push(data1);
						} else {
							//第二种，当前栈内只有一个元素，则弹出元素，进行运算
							data2 = data1;
						}

					} else {
						// 如果是双目运算符，则连续弹出两个元素，进行运算
						data1 = peStack.pop();
						data2 = peStack.pop();
					}
				} else {
					//如果是单目运算符，弹出一个元素进行运算，data2置为0
					data1 = peStack.pop();
					data2 = 0;
				}
				//将局部结果压入数据栈中
				peStack.push(cal(data1, data2, ch[i].str));
			}
		// 弹出最后结果
		return peStack.pop();
	}

	private double cal(double data1, double data2, String ch) {
		BigDecimal b1 = new BigDecimal(Double.toString(data1));
		BigDecimal b2 = new BigDecimal(Double.toString(data2));
		//完成局部运算
		double res = 0;
		if(ch.equals((String)"+"))
			res=b1.add(b2).doubleValue();
		else
			if(ch.equals((String)"-"))
					res=b2.subtract(b1).doubleValue();
			else
				if(ch.equals((String)"×"))
						res=b1.multiply(b2).doubleValue();
				else
					if(ch.equals((String)"÷"))
						res=b2.divide(b1,8,BigDecimal.ROUND_HALF_UP).doubleValue();
					else
						if(ch.equals((String)"^"))
							res=Math.pow(data2, data1);
						else
							if(ch.equals((String)"sin"))
								res=Math.sin(data1);
							else
								if(ch.equals((String)"cos"))
									res=Math.cos(data1);
								else
									if(ch.equals((String)"tan"))
										res=Math.tan(data1);
									else
										if(ch.equals((String)"√"))
											res=Math.sqrt(data1);
										else
											if(ch.equals((String)"ln"))
												res=Math.log(data1);
											else
												if(ch.equals((String)"lg"))
													res=Math.log10(data1);
												else
													if(ch.equals((String)"!")){
														res=1;
														for(int i=1;i<=data1;i++)
															res=res*i;
													}
		return res;
	}

	private Data[] transform(Data[] pech) {
		// 将中缀表达式转换成后缀表达式
		int j = 0, bracket = 0;
		Stack<Data> oprStack = new Stack<Data>();// 操作符栈
		Data[] outCh = new Data[pech.length];// 后缀表达式序列
		for (int i = 0; i < pech.length; i++) {
			if (pech[i].f) {
				// 如果是数字，直接输出
				outCh[j] = pech[i];
				j++;
			} else if (pech[i].str.equals((String) ")")) {
				//如果是右括号。括号个数+2并且输出操作符栈中的操作符，直到左括号，左括号弹出并不输出ֱ
				bracket += 2;
				while (!oprStack.peek().str.equals((String) "(")) {
					outCh[j] = oprStack.pop();
					j++;
				}
				oprStack.pop();
			}

			else if (oprStack.empty()
					|| pech[i].str.charAt(0) == '('
					|| compare(pech[i].str.charAt(0),
							oprStack.peek().str.charAt(0)))
				// 如果既不是数字也不是右括号，如果操作符栈空或当前操作符比栈顶操作符优先级高或者是左括号，直接压入栈中
				oprStack.push(pech[i]);
			else {
				//否则输出栈顶元素，直到遇到更低优先级的栈中操作符或者栈空，不弹出左括号
				while (!oprStack.empty()
						&& oprStack.peek().str.charAt(0) != '('
						&& !compare(pech[i].str.charAt(0),
								oprStack.peek().str.charAt(0))) {
					outCh[j] = oprStack.pop();
					j++;
				}
				oprStack.push(pech[i]);
			}
		}
		while (!oprStack.empty()) {
			//遍历完毕，依次输出操作符栈中的操作符
			outCh[j] = oprStack.pop();
			j++;
		}
		Data[] outChh = new Data[outCh.length - bracket];//输出序列需除去原始序列中所有括号
		System.arraycopy(outCh, 0, outChh, 0, outCh.length - bracket);
		return outChh;
	}

	private boolean compare(char currentChar, char topChar) {
		//比较两个操作符的优先级，当前操作符比栈顶操作符优先级高，则返回true，否则返回false
		if (currentChar == '+' || currentChar == '-')
			//如果当前操作符是+-级别
			if (topChar == '+' || topChar == '-')
				//如果栈顶操作符属于+-级别，返回false
				return false;
			else if (topChar == '×' || topChar == '÷')
				// 如果栈顶操作符属于*/级别，返回false
				return false;
			else
				// 如果栈顶操作符属于其他级别，返回false
				return false;
		else if (currentChar == '×' || currentChar == '÷')
			//如果当前操作符属于*/级别
			if (topChar == '+' || topChar == '-')
				//如果栈顶操作符属于+-级别，返回true
				return true;
			else if (topChar == '×' || topChar == '÷')
				//如果栈顶操作符属于*/级别。返回false
				return false;
			else
				//如果栈顶操作符属于其他级别，返回false
				return false;
		else
		//如果当前操作符属于其他级别
		if (topChar == '+' || topChar == '-')
			//如果栈顶操作符属于+-级别，返回true
			return true;
		else if (topChar == '×' || topChar == '÷')
			//如果栈顶操作符属于*/级别，返回true
			return true;
		else
			//如果栈顶操作符属于其他级别，返回false
			return true;

	}

	private Data[] split(String str) {
		//将字符串改成中缀表达式
		int l = 0;//统计中缀表达式具体长度
		char[] temp = str.toCharArray();// 将字符串分离成字符数组
		for (int i = 0; i < temp.length; i++)
			if ((temp[i] >= '0' && temp[i] <= '9') || temp[i] == '.') {
				//如果当前字符是数字或者小数点，向后遍历，直到出现非数字或小数点
				l++;
				while (i < temp.length
						&& ((temp[i] >= '0' && temp[i] <= '9') || temp[i] == '.'))
					i++;
				// 还原位置
				i--;
			} else if (temp[i] >= 'a' && temp[i] <= 'z') {
				//如果当前字符是英文字母，向后遍历，直到出现非英文字母
				while (temp[i] >= 'a' && temp[i] <= 'z' && i < temp.length)
					i++;
				l++;
				// 还原位置
				i--;
			} else
				l++;
		Data[] dataArray = new Data[l];
		for (int i = 0; i < l; i++)
			//初始化数据类
			dataArray[i] = new Data();
		int i = 0;
		//分离数字与操作符
		for (int j = 0; j < temp.length; j++) {
            //遍历char数组
			if ((temp[j] >= '0' && temp[j] <= '9') || temp[j] == '.') {
				//如果当前字符是数字或者小数点，向后遍历，直到出现非数字或小数点，或者完成遍历
				while (j < temp.length
						&& (((temp[j] >= '0' && temp[j] <= '9') || temp[j] == '.'))) {
					//讲后续数字加入当前dataArray的字符串中
					dataArray[i].str += temp[j];
					j++;
					dataArray[i].f = true;
				}
				//还原位置
				j--;
			} else
			//如果当前字符是操作符，直接加入当前dataArray的字符串中
			if (temp[j] >= 'a' && temp[j] <= 'z') {
				//如果当前操作符为英文字母，向后遍历，直到出现非英文字母
				while (j < temp.length && (temp[j] >= 'a' && temp[j] <= 'z')) {
					dataArray[i].str += temp[j];
					j++;
				}
				j--;
			} else
				dataArray[i].str += temp[j];
			// 填充下一个dataArray
			i++;
		}
		return dataArray;

	}

	private boolean compareOpr(String str) {
		//判断当前操作符是否是单目运算符，如果是返回true否则false
		if ((str.equals((String) "lg")) || (str.equals((String) "sin"))
				|| (str.equals((String) "cos")) || (str.equals((String) "tan"))
				|| (str.equals((String) "√")))
			return false;
		else
			return true;
	}

}



