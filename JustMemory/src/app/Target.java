package app;

import edu.ufl.digitalworlds.j4k.Skeleton;

import javax.media.opengl.GL2;

public class Target {

    private static final double xInc = AppContext.VIDEOFRAME_WIDTH / 200.0;
    private static final double yInc = AppContext.VIDEOFRAME_HEIGHT / 200.0;

    boolean isOK = false;

    // Array of 4 points, 2 coordinates each (X,Y)
    private float[] vertices;

    // Keep track of max and min value
    private float x_min,
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

    public int getXPixel(float x) {
        return (int) ((AppContext.VIDEOFRAME_WIDTH / 2) - (x * 100 * xInc));
    }

    public int getYPixel(float y) {
        return (int) ((AppContext.VIDEOFRAME_HEIGHT / 2) + (y * 100 * yInc));
    }

    public boolean containsPoint(int x, int y) {
        System.out.println("X MIN : " + getXPixel(x_min));
        System.out.println("X MAX : " + getXPixel(x_max));
        System.out.println("Y MIN : " + getYPixel(y_min));
        System.out.println("Y MAX : " + getYPixel(y_max));

        /*
        for (int i = 0; i < vertices.length; i++)
        {
            if ((i % 2) == 0) {
                System.out.println("X: " + getXPixel(vertices[i]));
            } else {
                System.out.println("Y: " + getYPixel(vertices[i]));
            }
        }
        */

        return (x < getXPixel(x_min) && x > getXPixel(x_max)) && (y > getYPixel(y_min) && y < getYPixel(y_max));
    }

    public boolean isSkeletonsInTarget(Skeleton[] skeletons) {
        for (int i = 0; i < 6; i++) {
            Skeleton sk = skeletons[i];

            if (sk != null && sk.isTracked()) {
                float[] jointsPosition = sk.getJointPositions();
                for (int j = 0; j < Skeleton.JOINT_COUNT; j++) {
                    if (jointsPosition[j] != 0.0) {
                        int[] joint = sk.get2DJoint(j, AppContext.VIDEOFRAME_WIDTH, AppContext.VIDEOFRAME_HEIGHT);
                        if (j == 3) {
                            System.out.println("HEAD " + joint[0] + " " + joint[1]);
                        }
                        if (j == 7) {
                            System.out.println("HAND LEFT " + joint[0] + " " + joint[1]);
                        }
                        if (j == 11) {
                            System.out.println("HAND RIGHT " + joint[0] + " " + joint[1]);
                        }
                        if (containsPoint(joint[0], joint[1])) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean OK) {
        isOK = OK;
    }

    public void drawInGL(GL2 gl) {
        gl.glLineWidth(30);
        if (isOK){
            gl.glColor3f(0.0f,1.0f,0.0f);
        } else {
            gl.glColor3f(1.0f,0.0f,0.0f);
        }
        gl.glBegin(GL2.GL_LINE_LOOP);

        gl.glVertex2f(-vertices[0], vertices[1]); // UPPER_LEFT
        gl.glVertex2f(-vertices[2], vertices[3]); // UPPER_RIGHT
        gl.glVertex2f(-vertices[4], vertices[5]); // LOWER_RIGHT
        gl.glVertex2f(-vertices[6], vertices[7]); // LOWER_LEFT

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
