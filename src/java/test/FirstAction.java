/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author Administrator
 */
public class FirstAction extends ActionSupport {

    private int operand1;
    private int operand2;

    public String execute() throws Exception {
        //test struts2
        if (getSum() >= 0) // 如果代码数和是非负整数，跳到positive.jsp页面
        {
            return "positive";
        } else // 如果代码数和是负整数，跳到negative.jsp页面
        {
            return "negative";
        }
    }
    
    

    public int getOperand1() {
        return operand1;
    }

    public void setOperand1(int operand1) {
        System.out.println(operand1);
        this.operand1 = operand1;
    }

    public int getOperand2() {
        return operand2;
    }

    public void setOperand2(int operand2) {
        System.out.println(operand2);
        this.operand2 = operand2;
    }

    public int getSum() {
        return operand1 + operand2;  // 计算两个整数的代码数和
    }
}
