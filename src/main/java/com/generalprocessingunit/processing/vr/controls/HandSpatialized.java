package com.generalprocessingunit.processing.vr.controls;

import com.generalprocessingunit.hid.GloveManager;
import com.generalprocessingunit.hid.Hand;
import com.generalprocessingunit.processing.space.AxisAngle;
import com.generalprocessingunit.processing.Color;
import com.generalprocessingunit.processing.space.EuclideanSpaceObject;
import com.generalprocessingunit.processing.space.YawPitchRoll;
import com.generalprocessingunit.processing.vr.PAppletVR;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

import java.util.Arrays;
import java.util.List;

public class HandSpatialized {
    PApplet p5;
    HeadModel headModel;

    GloveManager gloveManager = new GloveManager();
    public Hand leftHand = gloveManager.getLeftHand();

    public HandPart razerHydraSensor = new HandPart(new PVector(.0125f, .0125f, .05f));
    public HandPart palm = new HandPart(new PVector(.0425f, .0125f, .050f));
    
    public HandPart pinky =    new HandPart(new PVector(.005f,    .01f, .035f));
    public HandPart ring =     new HandPart(new PVector(.0075f, .0125f, .040f));
    public HandPart middle =   new HandPart(new PVector(.0075f, .0125f, .045f));
    public HandPart pointer =  new HandPart(new PVector(.0075f, .0125f, .040f));
    public HandPart thumb =    new HandPart(new PVector(.008f,   .0125f, .030f));

    public EuclideanSpaceObject pinkyKnuckle = new EuclideanSpaceObject();
    public EuclideanSpaceObject ringKnuckle = new EuclideanSpaceObject();
    public EuclideanSpaceObject middleKnuckle = new EuclideanSpaceObject();
    public EuclideanSpaceObject pointerKnuckle = new EuclideanSpaceObject();
    public EuclideanSpaceObject thumbKnuckle = new EuclideanSpaceObject();
    
    static final float vibratorShellRadius = .005f;
    public VibratorShell thumbBase = new VibratorShell(leftHand.knuckles.get(0));
    public VibratorShell pointerBase = new VibratorShell(leftHand.knuckles.get(1));
    public VibratorShell middleBase = new VibratorShell(leftHand.knuckles.get(2));
    public VibratorShell ringBase = new VibratorShell(leftHand.knuckles.get(3));
    public VibratorShell pinkyBase = new VibratorShell(leftHand.knuckles.get(4));

    public VibratorShell thumbTip = new VibratorShell(leftHand.fingertips.get(0));
    public VibratorShell pointerTip = new VibratorShell(leftHand.fingertips.get(1));
    public VibratorShell middleTip = new VibratorShell(leftHand.fingertips.get(2));
    public VibratorShell ringTip = new VibratorShell(leftHand.fingertips.get(3));
    public VibratorShell pinkyTip = new VibratorShell(leftHand.fingertips.get(4));

    public VibratorShell palmRingPinky = new VibratorShell(leftHand.palm.get(0));
    public VibratorShell palmPointerMid = new VibratorShell(leftHand.palm.get(1));
    public VibratorShell palmPinkyMeat = new VibratorShell(leftHand.palm.get(2));
    public VibratorShell palmThumbMeat = new VibratorShell(leftHand.palm.get(3));

    public List<VibratorShell> vibratorShells = Arrays.asList(
            thumbTip, pointerTip, middleTip, ringTip, pinkyTip,
            thumbBase, pointerBase, middleBase, ringBase, pinkyBase,
            palmPointerMid, palmRingPinky, palmThumbMeat, palmPinkyMeat
    );

    PVector shoulderLocation;

    public Color color = new Color(10);

    public boolean drawArm = true;

    public HandSpatialized(HeadModel headModel, PApplet p5) {
        this.p5 = p5;
        this.headModel = headModel;

        leftHand.gloveDevice.addChild(razerHydraSensor, new PVector(0, 0, -.025f));
        leftHand.gloveDevice.addChild(palm, new PVector(0, -.0125f, -.03f));

        leftHand.gloveDevice.addChild(pinkyKnuckle,      new PVector(-.0175f, -.0125f,   .0f));
        leftHand.gloveDevice.addChild(ringKnuckle,       new PVector(-.0075f, -.0125f,   .0f));
        leftHand.gloveDevice.addChild(middleKnuckle,     new PVector( .005f, -.0125f,    .0f));
        leftHand.gloveDevice.addChild(pointerKnuckle,    new PVector( .0175f, -.0125f,   .0f));
        leftHand.gloveDevice.addChild(thumbKnuckle,      new PVector( .0275f,  -.025f, -.025f), new YawPitchRoll(0, 0, -1.2f));

        pinkyKnuckle.addChild(pinky,      new PVector(0f, 0f, .0175f));
        ringKnuckle.addChild(ring,        new PVector(0f, 0f, .0200f));
        middleKnuckle.addChild(middle,    new PVector(0f, 0f, .0225f));
        pointerKnuckle.addChild(pointer,  new PVector(0f, 0f, .0200f));
        thumbKnuckle.addChild(thumb,      new PVector(0f, 0f, .0200f));
        
        thumbKnuckle.addChild(thumbBase, new PVector(0, 0, .01f));
        pointerKnuckle.addChild(pointerBase, new PVector(0, 0, .01f));
        middleKnuckle.addChild(middleBase, new PVector(0, 0, .01f));
        ringKnuckle.addChild(ringBase, new PVector(0, 0, .01f));
        pinkyKnuckle.addChild(pinkyBase, new PVector(0, 0, .01f));

        thumbKnuckle.addChild(thumbTip, new PVector(0, 0, thumb.dimensions.z + .005f));
        pointerKnuckle.addChild(pointerTip, new PVector(0, 0, pointer.dimensions.z));
        middleKnuckle.addChild(middleTip, new PVector(0, 0, middle.dimensions.z));
        ringKnuckle.addChild(ringTip, new PVector(0, 0, ring.dimensions.z));
        pinkyKnuckle.addChild(pinkyTip, new PVector(0, 0, pinky.dimensions.z));

        palm.addChild(palmPointerMid, new PVector(-palm.dimensions.x / 4, -.01f, palm.dimensions.z / 3));
        palm.addChild(palmRingPinky, new PVector(  palm.dimensions.x / 4, -.01f, palm.dimensions.z / 3));
        palm.addChild(palmThumbMeat, new PVector( -palm.dimensions.x / 4, -.01f, -palm.dimensions.z / 4));
        palm.addChild(palmPinkyMeat, new PVector(  palm.dimensions.x / 4, -.01f, -palm.dimensions.z / 4));
    }

    public void update() {
        gloveManager.poll();

        headModel.headContainer.translateAndRotateObjectWRTObjectCoords(leftHand.gloveDevice);

        pinkyKnuckle.pitch(leftHand.pinky.getBend() / 350f);
        ringKnuckle.pitch(leftHand.ring.getBend() / 350f);
        middleKnuckle.pitch(leftHand.middle.getBend() / 350f);
        pointerKnuckle.pitch(leftHand.pointer.getBend() / 350f);
        thumbKnuckle.pitch(leftHand.thumb.getBend() / 350f);


    }

    public void drawView(PGraphics pG) {
        drawHand(leftHand, pG);
    }

    void drawHand(Hand hand, PGraphics pG) {
        shoulderLocation = headModel.neck.getTranslationWRTObjectCoords( new PVector(-.01f, -.02f, -.01f));

        pG.colorMode(PConstants.RGB);
        pG.fill(255, 0, 0);
        pG.emissive(hand.isGrabbing() ? 64 : 32, 0, 0);
        razerHydraSensor.draw(pG);
        pG.emissive(0);

        pG.fill(color.R, color.G, color.B);
        pG.stroke(64 , 32, 32);
        palm.draw(pG);
        pinky.draw(pG);  
        ring.draw(pG);
        middle.draw(pG);
        pointer.draw(pG);
        thumb.draw(pG);

        for(VibratorShell vs : vibratorShells) {
            vs.draw(pG);
        }
        pG.colorMode(PConstants.RGB);

        if(!drawArm) return;

        pG.noStroke();
        pG.fill(color.R, color.G, color.B, color.A);
        PVector wristLoc = palm.getTranslationWRTObjectCoords(new PVector(0, 0, -palm.dimensions.z / 1.8f));
        for (float f = 0; f < 1; f += .1f) {
            pG.pushMatrix();
            {
                PVector loc = PVector.lerp(shoulderLocation, wristLoc, f);
                pG.translate(loc.x, loc.y, loc.z);
                pG.sphere(.01f);
            }
            pG.popMatrix();
        }

        pG.pushMatrix();  // shoulder
        {
            PVector loc = shoulderLocation;
            pG.translate(loc.x, loc.y, loc.z);
            pG.fill(255, 0, 255);
            pG.sphere(.0025f);
        }
        pG.popMatrix();

    }

    public void reset() {
        leftHand.reset( PVector.sub(headModel.head.getLocation(), headModel.headContainer.getLocation()));
    }

    public void invert() {
        leftHand.toggleInvertedLocation();
    }
    
    public class HandPart extends EuclideanSpaceObject {
        PVector dimensions;
        HandPart(PVector dimensions) {
            this.dimensions = dimensions;
        }
        
        void draw(PGraphics pG) {
            pG.pushMatrix();
            {
                pG.translate(x(), y(), z());
                AxisAngle aa = getAxisAngle();
                pG.rotate(aa.w, aa.x, aa.y, aa.z);
                pG.box(dimensions.x, dimensions.y, dimensions.z);
            }
            pG.popMatrix();
        }
    }

    public class VibratorShell extends EuclideanSpaceObject {
        public Hand.Vibrator vibrator;
        public float r;
        VibratorShell(Hand.Vibrator vibrator) {
            this.vibrator = vibrator;
            r = vibratorShellRadius;
        }

        private int millisAtVibrate = 0;
        private int intensity = 0;
        public void setVibrate(int intensity) {
            vibrator.setVibrate(intensity);
            millisAtVibrate = p5.millis();
            this.intensity = intensity;

        }
        
        void draw(PGraphics pG) {

            pG.colorMode(PConstants.HSB);

            pG.noStroke();
            pG.fill(13 * vibrator.index, 255, 255);
            pG.emissive(13 * vibrator.index, 255, 255);

            if(p5.millis() - millisAtVibrate < 300) {
                pG.colorMode(PConstants.RGB);
                pG.emissive(55 + 200 * intensity / 11f);
            }

            pG.pushMatrix();
            {
                pG.translate(x(), y(), z());
                AxisAngle aa = getAxisAngle();
                pG.rotate(aa.w, aa.x, aa.y, aa.z);
                pG.sphere(r);
            }
            pG.popMatrix();

            pG.emissive(0);
        }
    }
}
