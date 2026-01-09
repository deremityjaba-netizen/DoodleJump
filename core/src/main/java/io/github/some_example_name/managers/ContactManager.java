package io.github.some_example_name.managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import io.github.some_example_name.game.GameSettings;
import io.github.some_example_name.objects.GameObject;

public class ContactManager {
    World world;

    public ContactManager(World world){
        this.world = world;
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDefA = fixA.getFilterData().categoryBits;
                int cDefB = fixB.getFilterData().categoryBits;

                System.out.println(cDefA + " " + cDefB);

                if(cDefA == GameSettings.PLATE_BIT && cDefB == GameSettings.DOODLE_BIT
                || cDefA == GameSettings.DOODLE_BIT && cDefB == GameSettings.PLATE_BIT) {
                    ((GameObject) fixA.getUserData()).setTouched(true);
                    ((GameObject) fixB.getUserData()).setTouched(true);
                }else if (cDefA == GameSettings.BOMB_BIT && cDefB == GameSettings.DOODLE_BIT) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                } else if(cDefA == GameSettings.DOODLE_BIT && cDefB == GameSettings.BOMB_BIT){
                    ((GameObject) fixB.getUserData()).hit();
                    ((GameObject) fixA.getUserData()).hit();
                }

            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDefA = fixA.getFilterData().categoryBits;
                int cDefB = fixB.getFilterData().categoryBits;

                if(cDefA == GameSettings.PLATE_BIT && cDefB == GameSettings.DOODLE_BIT
                    || cDefA == GameSettings.DOODLE_BIT && cDefB == GameSettings.PLATE_BIT) {
                    ((GameObject) fixA.getUserData()).setTouched(false);
                    ((GameObject) fixB.getUserData()).setTouched(false);
                }

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }
}
