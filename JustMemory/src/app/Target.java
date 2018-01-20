package app;

import edu.ufl.digitalworlds.j4k.Skeleton;

import javax.media.opengl.GL2;

public class Target {

    //public static Target MID_UP_LEFT = new Target(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f});

    // Array of 4 points, 2 coordinates each (X,Y)
    private float[] vertices;

    public Target(float[] vertices) {
        this.vertices = vertices;
    }

    public Target(float x, float y) {
        this.vertices = new float[]{
                -0.25f + x,
                -0.25f + y,
                0.25f + x,
                -0.25f + y,
                0.25f + x,
                0.25f + y,
                -0.25f + x,
                0.25f + y
        };
    }

    public void translateHorizontally(float value) {
        for (int i = 0; i < vertices.length; i += 2)
            vertices[i] += value;
    }

    public void translateVertically(float value) {
        for (int i = 1; i < vertices.length; i += 2)
            vertices[i] += value;
    }

    public boolean containsPoint(float x, float y) {
        return true;
    }

    public boolean isSkeletonsInTarget(Skeleton[] skeletons) {
        for (int i = 0; i < 6; i++) {
            Skeleton sk = skeletons[i];

            if (sk != null && sk.isTracked())
            {
                float[] jointsPosition = sk.getJointPositions();
                for (int j = 0; j < Skeleton.JOINT_COUNT; j++)
                {
                    if (jointsPosition[j] != 0.0 && jointsPosition[j + 1] != 0.0) {
                        if (containsPoint(jointsPosition[j], jointsPosition[j + 1])) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
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
