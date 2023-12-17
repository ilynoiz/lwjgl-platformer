package ru.testproj;

public class Worker extends Object{
	private String name;
	private Sex sex;

	public Worker(String name, Sex sex) {
		this.name = name;
		this.sex = sex;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Sex getSex() {
		return this.sex;
	}
}
