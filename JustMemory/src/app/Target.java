package app;

import edu.ufl.digitalworlds.j4k.Skeleton;

import javax.media.opengl.GL2;

public class Target {

    //public static Target MID_UP_LEFT = new Target(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f});

    // Array of 4 points, 2 coordinates each (X,Y)
    private float[] vertices;

    private float   x_min,
                    x_max,
                    y_min,
                    y_max;

    public Target(float[] vertices) {
        this.vertices = vertices;
        x_min = getXMin();
        x_max = getXMax();
        y_min = getYMin();
        y_max = getYMax();
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

        x_min = getXMin();
        x_max = getXMax();
        y_min = getYMin();
        y_max = getYMax();
    }

    public Target(Target target) {
        vertices = target.vertices.clone();

        x_min = target.x_min;
        x_max = target.x_max;
        y_min = target.y_min;
        y_max = target.y_max;
    }

    public void translateHorizontally(float value) {
        for (int i = 0; i < vertices.length; i += 2)
            vertices[i] += value;

        x_min = getXMin();
        x_max = getXMax();
    }

    public void translateVertically(float value) {
        for (int i = 1; i < vertices.length; i += 2)
            vertices[i] += value;

        y_min = getYMin();
        y_max = getYMax();
    }

    // TODO Change to handle the new parameters that are now in pixels
    public boolean containsPoint(int x, int y) {
        return (x > x_min && x < x_max) && (y > y_min && y < y_max);
    }

    public boolean isSkeletonsInTarget(Skeleton[] skeletons) {
        for (int i = 0; i < 6; i++) {
            Skeleton sk = skeletons[i];

            if (sk != null && sk.isTracked())
            {
                float[] jointsPosition = sk.getJointPositions();
                for (int j = 0; j < Skeleton.JOINT_COUNT; j += 3)
                {
                    if (jointsPosition[j] != 0.0) {
                        int[] joint = sk.get2DJoint(j, AppContext.VIDEOFRAME_WIDTH, AppContext.VIDEOFRAME_HEIGHT);
                        if (containsPoint(joint[0], joint[1])) {
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

    private float getXMin() {
        float min = Float.MAX_VALUE;
        for (int i = 0; i < vertices.length; i += 2) {
            if (vertices[i] < min)
                min = vertices[i];
        }

        return min;
    }

    private float getXMax() {
        float max = -Float.MAX_VALUE;
        for (int i = 0; i < vertices.length; i += 2) {
            if (vertices[i] > max)
                max = vertices[i];
        }

        return max;
    }

    private float getYMin() {
        float min = Float.MAX_VALUE;
        for (int i = 1; i < vertices.length; i += 2) {
            if (vertices[i] < min)
                min = vertices[i];
        }

        return min;
    }

    private float getYMax() {
        float max = -Float.MAX_VALUE;
        for (int i = 1; i < vertices.length; i += 2) {
            if (vertices[i] > max)
                max = vertices[i];
        }

        return max;
    }
}
