package ru.testproj.engine;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.Objects;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import ru.testproj.util.Time;

public class Window {
	
	private int width, height;
	private String title;
	private long glfwWindow;
	public float r, g, b, a;

	private static Window window;
	private static Scene currentScene;


	private Window() {
		this.width = 1280;
		this.height = 720;
		this.title = "Game";
		this.r = 1.0f;
		this.g = 1.0f;
		this.b = 1.0f;
		this.a = 1.0f;
	}

	public static void changeScene(int targetScene) {
		switch (targetScene) {
			case 0:
				currentScene = new LevelEditorScene();
				break;
			case 1:
				currentScene = new LevelScene();
				break;
			default:
				assert false : String.format("Unknown scene \"%s\"", targetScene);
				break;
		}
	}
	
	public static Window get() {
		if (Objects.isNull(Window.window)) {
			Window.window = new Window();
		}
		return Window.window;
		
	}
	
	public void run() {
		System.out.println(String.format("Hello, LWGJL, version %s!", Version.getVersion()));
		
		init();
		loop();
		
		//Free callbacks
		glfwFreeCallbacks(glfwWindow);
		glfwDestroyWindow(glfwWindow);

		//Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free(); 
	}
	
	public void init() {
		//Setting u the system callback
		GLFWErrorCallback.createPrint(System.err).set();
		
		//Initialize GLFW
		if (!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW!");
		}
		
		//configure GLFW window hints
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
		
		//Create window
		 glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
		 if (glfwWindow == NULL) {
			 throw new IllegalStateException("Faild to create the GLFW window.");
		 }

		 glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
		 glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
		 glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
		 glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
		 
		 //Make the OpenGL context
		 glfwMakeContextCurrent(glfwWindow);
		 
		 //Enable V-sync
		 glfwSwapInterval(1);
		 
		 //Make the window visible
		 glfwShowWindow(glfwWindow);
		 
		 //Just very important shit, DO NOT REMOVE!
		 GL.createCapabilities();

		 Window.changeScene(0);
	}
	
	public void loop() {
		float beginTime = Time.getTime();
		float endTime; //= Time.getTime();
		float dt = -1.0f;

		while (!glfwWindowShouldClose(glfwWindow)) {
			//Poll events
			glfwPollEvents();
			
			GL11.glClearColor(r, g, b, a);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			if (dt >= 0) {
				currentScene.update(dt);
			}

			if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
				System.out.println("Space key is pressed");
			}

			glfwSwapBuffers(glfwWindow);

			endTime = Time.getTime();
			dt = endTime - beginTime;
			beginTime = endTime;
		}
	}
}
