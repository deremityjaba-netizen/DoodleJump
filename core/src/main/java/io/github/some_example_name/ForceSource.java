package io.github.some_example_name;

public class ForceSource {
    float minForce = 1;
    float maxForce = 10;
    float stepForce = 1;
    private int direction = 1;
    private float force = 1;
    public ForceSource(float minForce, float maxForce, float stepForce){

    }
    public void  update() {

            force = force + direction * stepForce;

            if (force > maxForce) {
                force = maxForce;
                direction = -1;
            } else if (force < minForce) {
                force = minForce;
                direction = 1;

            }


    }

    public float getForce() {
        return force;
    }
}
