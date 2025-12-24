package io.github.some_example_name.game;

public class ForceSource {
    float minForce;
    float maxForce ;
    float stepForce;
    private int direction = 1;
    private float force;
    public ForceSource(float minForce, float maxForce, float stepForce){
        this.minForce = minForce;
        this.maxForce = maxForce;
        this.stepForce = stepForce;
        this.force = (minForce + maxForce ) / 2;
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
