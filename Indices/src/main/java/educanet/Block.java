package educanet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;


public class Block {
    private final int[] indices = {
            0, 1, 3,
            1, 2, 3
    };

    private final int vaoId;

    public Block(float a, float b, float c) {

        float[] vertices = {
                a + c, b, 0.0f,
                a + c, b - c, 0.0f,
                a, b - c, 0.0f,
                a, b, 0.0f,

        };

        vaoId = GL33.glGenVertexArrays();

        int eboId = GL33.glGenBuffers();
        int vboId = GL33.glGenBuffers();

        GL33.glBindVertexArray(vaoId);
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, eboId);

        IntBuffer intBuff = BufferUtils.createIntBuffer(indices.length).put(indices).flip();

        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, intBuff, GL33.GL_STATIC_DRAW);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboId);

        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, floatBuffer, GL33.GL_STATIC_DRAW);

        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);

        GL33.glEnableVertexAttribArray(0);

        MemoryUtil.memFree(floatBuffer);
        MemoryUtil.memFree(intBuff);
    }
    public void render () {
        GL33.glUseProgram(educanet.Shaders.shaderProgramId);

        GL33.glBindVertexArray(vaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);
    }
}