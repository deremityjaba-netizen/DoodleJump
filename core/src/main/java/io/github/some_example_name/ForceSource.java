package io.github.some_example_name;

public class ForceSource {
    float minForce;
    float maxForce;
    float stepForce;
    private int direction = 1;
    private float force;
    public ForceSource(float minForce, float maxForce, float stepForce){

    }
    public void  update(){
        force = force + direction * stepForce;
        if(force > maxForce){
            force = maxForce;
            direction = -1;
        } else if (force < minForce) {
            force = minForce;
            direction = 1;

        }

    }

}
