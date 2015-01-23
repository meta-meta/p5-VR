package com.generalprocessingunit.processing.vr.controls;

import com.generalprocessingunit.processing.space.EuclideanSpaceObject;
import com.generalprocessingunit.processing.space.Orientation;
import processing.core.PApplet;
import processing.core.PVector;

public interface HeadModel {
    /** Can be thought of as a tank floating in space.
     * The Oculus rift is the homunculus inside.
     * */
    public EuclideanSpaceObject headContainer = new EuclideanSpaceObject();

    public EuclideanSpaceObject head = new EuclideanSpaceObject();

    public EuclideanSpaceObject neck = new EuclideanSpaceObject();

}
