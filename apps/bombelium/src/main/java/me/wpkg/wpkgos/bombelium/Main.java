package me.wpkg.wpkgos.bombelium;

import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Main {

    static Texture bombel,bombel_ear,bombel_empty,bombel_skin;
    static boolean party = false;


    public static void gluPerspective(float fovy, float aspect, float near, float far) {
        float bottom = -near * (float) Math.tan(fovy / 2);
        float top = -bottom;
        float left = aspect * bottom;
        float right = -left;
        glFrustum(left, right, bottom, top, near, far);
    }
    public static void init(){
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set background color to black and opaque
        glClearDepth(1.0f);                   // Set background depth to farthest
        glEnable(GL_DEPTH_TEST);   // Enable depth testing for z-culling
        glDepthFunc(GL_LEQUAL);    // Set the type of depth-test
        glShadeModel(GL_SMOOTH);   // Enable smooth shading
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);  // Nice perspective corrections

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        glEnable(GL_TEXTURE_2D);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        bombel = new Texture(Main.class.getResourceAsStream("/bombelek_face.png"));
        bombel.bind();
        bombel.unbind();

        bombel_ear = new Texture(Main.class.getResourceAsStream("/bombelek_ear.png"));
        bombel_ear.bind();
        bombel_ear.unbind();

        bombel_empty = new Texture(Main.class.getResourceAsStream("/bombelek_empty.png"));
        bombel_empty.bind();
        bombel_empty.unbind();

        bombel_skin = new Texture(Main.class.getResourceAsStream("/bombelek_skin.png"));
        bombel_skin.bind();
        bombel_skin.unbind();
    }

    public static void drawBombelium()
    {
        // Render a pyramid consists of 4 triangles
        glRotatef(i,0,1,0);

        if (party)
        {
            double time = glfwGetTime();
            glColor3d(Math.abs(Math.sin(time * 2.0)),
                    Math.abs(Math.sin(time * 0.7)),
                    Math.abs(Math.sin(time * 1.3)));
        }
        glScalef(0.3f,0.3f,0.3f);

        bombel.bind();

        glBegin(GL_TRIANGLES);           // Begin drawing the pyramid with 4 triangles
        // Front

        glTexCoord2f(0.5f,0);
        glVertex3f( 0.0f, 1.0f, 0.0f);
        glTexCoord2f(0,1);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glTexCoord2f(1,1);
        glVertex3f(1.0f, -1.0f, 1.0f);

        glEnd();

        bombel_ear.bind();
        glBegin(GL_TRIANGLES);

        // Right
        glTexCoord2f(0.5f,0);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glTexCoord2f(0,1);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glTexCoord2f(1,1);
        glVertex3f(1.0f, -1.0f, -1.0f);

        // Left
        glTexCoord2f(0.5f,0);
        glVertex3f( 0.0f, 1.0f, 0.0f);
        glTexCoord2f(1,1);
        glVertex3f(-1.0f,-1.0f,-1.0f);
        glTexCoord2f(0,1);
        glVertex3f(-1.0f,-1.0f, 1.0f);

        glEnd();   // Done drawing the pyramid

        bombel_empty.bind();
        glBegin(GL_TRIANGLES);

        // Back
        glTexCoord2f(0.5f,0);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glTexCoord2f(0,1);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glTexCoord2f(1,1);
        glVertex3f(-1.0f, -1.0f, -1.0f);

        glEnd();

        bombel_skin.bind();
        glBegin(GL_QUADS);

        // Bottom
        glTexCoord2f(0,0);
        glVertex3f( 1.0f, -1.0f,  1.0f);
        glTexCoord2f(1,0);
        glVertex3f(-1.0f, -1.0f,  1.0f);
        glTexCoord2f(1,1);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glTexCoord2f(0,1);
        glVertex3f( 1.0f, -1.0f, -1.0f);

        glEnd();
    }

    static int i;
    public static void display() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear color and depth buffers
        glMatrixMode(GL_MODELVIEW);     // To operate on model-view matrix

        glLoadIdentity();                  // Reset the model-view matrix
        glTranslatef(0f, 0.1f, -1f);  // Move left and into the screen


        for (int i =0; i < 100;i++)
        {
            drawBombelium();
        }
    }

    public static void reshape(long win,int width,int height) {
        // Compute aspect ratio of the new window
        if (height == 0) height = 1;                // To prevent divide by 0
        float aspect = (float)width / (float)height;

        // Set the viewport to cover the new window
        glViewport(0, 0, width, height);

        // Set the aspect ratio of the clipping volume to match the viewport
        glMatrixMode(GL_PROJECTION);  // To operate on the Projection matrix
        glLoadIdentity();             // Reset
        // Enable perspective projection with fovy, aspect, zNear and zFar
        gluPerspective(45.0f, aspect, 0.1f, 100.0f);
    }

    //fps
    public static double timerek = System.currentTimeMillis();
    public static int UPS = 0 , FPS = 0;
    public static final int FRAMERATE = 60;
    private static double delta;
    private static double frametime = 1000000000 / FRAMERATE;
    private static long timeNOW = System.nanoTime();
    private static long timeLAST = System.nanoTime();

    public static void main(String[] args)
    {

        for (int i = 0;i < args.length;i++)
            if(args[i].startsWith("--")) {
                switch (args[i]) {
                    case "--party":
                        party = true;
                        break;
                }
            }

        if (!glfwInit())System.err.println("Error");

        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        // Create the window
        long window = glfwCreateWindow(1280, 720, "Bombelium", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(window);

        glfwSetWindowSizeCallback(window, Main::reshape);

        GL.createCapabilities();

        glfwShowWindow(window);

        glfwSwapInterval(0);

        init();
        reshape(window,1280,720);

        while (!glfwWindowShouldClose(window))
        {
            timeNOW = System.nanoTime();
            delta += (timeNOW - timeLAST) / frametime;
            timeLAST = timeNOW;

            while (delta >= 1)
            {
                //update
                i+=2;
                delta -= 1;
                UPS++;

            }
            display();
            FPS++;
            if(System.currentTimeMillis() - timerek >= 1000)
            {
                try
                {
                    timerek = System.currentTimeMillis();

					System.out.println("FPS: " + FPS + " UPS: " + UPS);
                    UPS = 0;
                    FPS = 0;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            display();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        glfwDestroyWindow(window);
    }
}
