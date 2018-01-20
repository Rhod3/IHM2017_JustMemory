package app;

import javax.media.opengl.GL2;

public class Target {

    public static Target MID_UP_LEFT = new Target(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f});

    // Array of 4 points, 2 coordinates each (X,Y)
    float[] vertices = new float[8];

    public Target(float[] vertices) {
        this.vertices = vertices;
    }

    public boolean containsPoint(float x, float y) {
        return true;
    }

    public void drawInGL(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);

        gl.glVertex2f(vertices[0], vertices[1]); // UPPER_LEFT
        gl.glVertex2f(vertices[2], vertices[3]); // UPPER_RIGHT
        gl.glVertex2f(vertices[4], vertices[5]); // LOWER_RIGHT
        gl.glVertex2f(vertices[6], vertices[7]); // LOWER_LEFT

        gl.glEnd();
        gl.glFlush();
    }
}
