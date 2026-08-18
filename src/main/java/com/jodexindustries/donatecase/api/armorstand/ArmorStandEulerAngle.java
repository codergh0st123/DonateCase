package com.jodexindustries.donatecase.api.armorstand;

import lombok.Generated;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class ArmorStandEulerAngle {
   @Setting("Head")
   private EulerAngle head;
   @Setting("Body")
   private EulerAngle body;
   @Setting("RightArm")
   private EulerAngle rightArm;
   @Setting("LeftArm")
   private EulerAngle leftArm;
   @Setting("RightLeg")
   private EulerAngle rightLeg;
   @Setting("LeftLeg")
   private EulerAngle leftLeg;

   public ArmorStandEulerAngle(EulerAngle head, EulerAngle body, EulerAngle rightArm, EulerAngle leftArm, EulerAngle rightLeg, EulerAngle leftLeg) {
      this.head = head;
      this.body = body;
      this.rightArm = rightArm;
      this.leftArm = leftArm;
      this.rightLeg = rightLeg;
      this.leftLeg = leftLeg;
   }

   public ArmorStandEulerAngle() {
   }

   public String toString() {
      return "ArmorStandEulerAngle{head=" + this.head + ", body=" + this.body + ", rightArm=" + this.rightArm + ", leftArm=" + this.leftArm + ", rightLeg=" + this.rightLeg + ", leftLeg=" + this.leftLeg + '}';
   }

   @Generated
   public void setHead(EulerAngle head) {
      this.head = head;
   }

   @Generated
   public void setBody(EulerAngle body) {
      this.body = body;
   }

   @Generated
   public void setRightArm(EulerAngle rightArm) {
      this.rightArm = rightArm;
   }

   @Generated
   public void setLeftArm(EulerAngle leftArm) {
      this.leftArm = leftArm;
   }

   @Generated
   public void setRightLeg(EulerAngle rightLeg) {
      this.rightLeg = rightLeg;
   }

   @Generated
   public void setLeftLeg(EulerAngle leftLeg) {
      this.leftLeg = leftLeg;
   }

   @Generated
   public EulerAngle getHead() {
      return this.head;
   }

   @Generated
   public EulerAngle getBody() {
      return this.body;
   }

   @Generated
   public EulerAngle getRightArm() {
      return this.rightArm;
   }

   @Generated
   public EulerAngle getLeftArm() {
      return this.leftArm;
   }

   @Generated
   public EulerAngle getRightLeg() {
      return this.rightLeg;
   }

   @Generated
   public EulerAngle getLeftLeg() {
      return this.leftLeg;
   }
}
