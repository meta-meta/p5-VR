package com.generalprocessingunit.processing.vr.controls;

import com.generalprocessingunit.hid.SpaceNavigator;
import com.generalprocessingunit.processing.MomentumVector;
import com.generalprocessingunit.processing.MomentumYawPitchRoll;
import com.generalprocessingunit.processing.space.EuclideanSpaceObject;
import processing.core.PApplet;
import processing.core.PVector;

public class SpaceNavVR {
    EuclideanSpaceObject headContainer;

    SpaceNavigator spaceNav;
    public MomentumVector momentum;
    public MomentumYawPitchRoll rotMomentum;

    static float speedCoef = 0.004f;
    static float rotSpeedCoef = 0.005f;

    static boolean invertedControl = true;

    public SpaceNavVR(PApplet p5, EuclideanSpaceObject headContainer, float friction, float speed) {
        this.headContainer = headContainer;
        spaceNav = new SpaceNavigator(p5);
        momentum = new MomentumVector(p5, friction);
        rotMomentum = new MomentumYawPitchRoll(p5, friction);

        speedCoef *= speed;
    }

    public void update() {
        spaceNav.poll();

        int i = invertedControl ? -1 : 1;


        momentum.add(PVector.mult(spaceNav.translation, i * speedCoef));
        rotMomentum.add(PVector.mult(spaceNav.rotation, i * rotSpeedCoef));

        momentum.friction();
        rotMomentum.friction();

        headContainer.translateWRTObjectCoords(momentum.getValue());
        headContainer.rotate(rotMomentum.getValue());
    }

    public void invertControl(){
        invertedControl = !invertedControl;
    }
}
