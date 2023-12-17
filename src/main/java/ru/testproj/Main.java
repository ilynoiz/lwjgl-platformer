package ru.testproj;

import ru.testproj.engine.Window;

public class Main {
	
	public static void main(String[] args) {
		
		String helloWorldString = "Hello, world!";
		System.out.println(helloWorldString);
		
		System.out.println("Creating a worker with name \"John\"");
		Worker worker = new Worker("John", Sex.MALE);
		
		System.out.println(String.format("This worker name is %s.", worker.getName()));

		//TODO: remove multithreading shit.

		Window window = Window.get();
		window.run();
		
	}


}
