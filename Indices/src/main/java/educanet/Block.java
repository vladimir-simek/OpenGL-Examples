package educanet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;


public class Block {
    private final int[] indices = {
            0,1,3,
            1,2,3
    };

    private float[] vertices;

    private int vaoId;
    private int vboId;
    private int eboId;

    public Block(float a, float b, float c){

        this.vertices = new float[]{
                (a+c),b,0f,
                (a+c),(b-c),0f,
                a,(b-c),0f,
                a,b,0f
        };

        vaoId = GL33.glGenVertexArrays();
        vboId = GL33.glGenBuffers();
        eboId = GL33.glGenBuffers();

        GL33.glBindVertexArray(vaoId);
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, eboId);

        IntBuffer intBuffer = BufferUtils.createIntBuffer(indices.length).put(indices).flip();

        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, intBuffer, GL33.GL_STATIC_DRAW);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboId);

        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, floatBuffer, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0,3,GL33.GL_FLOAT,false,0,0);


    }
}
