package com.cy.java.ref;
class ClassA{
	public void doMethod() {
		System.out.println("a.doMethod");
	}
}
class ClassB{
	ClassA classA;//null
	public void setClassA(ClassA classA) {
		this.classA = classA;
	}
	public void doMethod() {
		classA.doMethod();
		System.out.println("b.doMethod");
	}
}
public class TestRef01 {
   public static void main(String[] args) {
	 ClassB classB=new ClassB();//假如由spring管理，会由spring通过反射创建对象
	 //DI 依赖注入(为属性赋值)--手动为属性赋值
	 //自动装配可以理解为由框架完成属性值的注入
	 classB.setClassA(new ClassA());
     //classB.doMethod();
	 
   }
}
