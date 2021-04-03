package educanet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    /**
     *
     * @param number int
     * @return ArrayList
     */
    public static ArrayList<String> getMazeCode(int number){
        ArrayList<String> arrayList = new ArrayList<>();

        String maze1 = "101111111000111011101010000001\n" +
                "100000101101101100111011111111\n" +
                "110011100111000110100010000100\n" +
                "010110000000010010101110110111\n" +
                "011100111101110110111010100001\n" +
                "100011100111001100100000111011\n" +
                "100000100010011000101111101010\n" +
                "110011101110010111111000000010\n" +
                "010110100001110000001010111010\n" +
                "111100101111001111011010101011\n" +
                "001000001000001010010110101001\n" +
                "111011101011101011110100101101\n" +
                "101010101010111000001110100111\n" +
                "100010111010000111001010100001\n" +
                "111110000011001101011010101101\n" +
                "100100111101011001110001101001\n" +
                "101100100101000011011011001001\n" +
                "011001100101111010010010001011\n" +
                "110111001110001110110110011010\n" +
                "100100111011100010101100110010\n" +
                "100100000000111010101011101011\n" +
                "101111011110001011001010001001\n" +
                "100001010011001001101011001101\n" +
                "110011011101101100101001111001\n" +
                "011010000100100110101011001001\n" +
                "001011001101100010001000011001\n" +
                "111001111001011010111011110011\n" +
                "010000100001010011100010001110\n" +
                "011110001111010000000111001000\n" +
                "110011111001011111111101111111";

        String maze2 = "1110111101\n" +
                "0010010111\n" +
                "1011110010\n" +
                "1100000011\n" +
                "0101111001\n" +
                "0100001001\n" +
                "0111111001\n" +
                "1100100111\n" +
                "1000101101\n" +
                "1110111001";

        String maze3 = "10111\n" +
                "10001\n" +
                "11101\n" +
                "01001\n" +
                "11111";

        String[] strings = null;

        switch (number) {
            case 1: {
                strings = maze1.split("\n");

                Collections.addAll(arrayList, strings);
            }
            case 2: {
                strings = maze2.split("\n");

                Collections.addAll(arrayList, strings);
            }
            case 3: {
                strings = maze3.split("\n");

                Collections.addAll(arrayList, strings);
            }

            break;
            default: throw new IllegalStateException("Unexpected value: " + number);
        }

        /*try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();

            while (line != null) {
                arrayList.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return arrayList;
    }

    public static void main(String[] args) throws Exception {

        ArrayList<String> arrayList = getMazeCode(1);
        System.out.println(arrayList.size());

        //region: Window init
        GLFW.glfwInit();
        // Tell GLFW what version of OpenGL we want to use.
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        // TODO: Add support for macOS

        // Create the window...
        // We can set multiple options with glfwWindowHint ie. fullscreen, resizability etc.
        long window = GLFW.glfwCreateWindow(800, 600, "My first window", 0, 0);
        if (window == 0) {
            GLFW.glfwTerminate();
            throw new Exception("Can't open window");
        }
        GLFW.glfwMakeContextCurrent(window);

        // Tell GLFW, that we are using OpenGL
        GL.createCapabilities();
        GL33.glViewport(0, 0, 800, 600);

        // Resize callback
        GLFW.glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            GL33.glViewport(0, 0, w, h);
        });
        //endregion

        // Main game loop
        Shaders.initShaders();
        Game.init(window);

        // Draw in polygon mod
        //GL33.glPolygonMode(GL33.GL_FRONT_AND_BACK, GL33.GL_LINE);
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Key input management
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS)
                GLFW.glfwSetWindowShouldClose(window, true); // Send a shutdown signal...

            // Change the background color
            GL33.glClearColor(0f, 0f, 0f, 1f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            Game.render(window);
            Game.update(window);

            // Swap the color buffer -> screen tearing solution
            GLFW.glfwSwapBuffers(window);
            // Listen to input
            GLFW.glfwPollEvents();
        }

        // Don't forget to cleanup
        GLFW.glfwTerminate();
    }

}
