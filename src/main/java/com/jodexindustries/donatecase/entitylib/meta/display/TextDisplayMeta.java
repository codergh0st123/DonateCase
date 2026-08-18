package com.jodexindustries.donatecase.entitylib.meta.display;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.jodexindustries.donatecase.entitylib.meta.Metadata;
import net.kyori.adventure.text.Component;

public class TextDisplayMeta extends AbstractDisplayMeta {
   public static final byte OFFSET;
   public static final byte MAX_OFFSET;
   private static final byte SHADOW = 1;
   private static final byte SEE_THROUGH = 2;
   private static final byte USE_DEFAULT_BACKGROUND = 4;
   private static final byte ALIGN_LEFT = 8;
   private static final byte ALIGN_RIGHT = 16;

   public TextDisplayMeta(int entityId, Metadata metadata) {
      super(entityId, metadata);
   }

   public Component getText() {
      return (Component)this.metadata.getIndex(OFFSET, Component.empty());
   }

   public void setText(Component component) {
      this.metadata.setIndex(OFFSET, EntityDataTypes.ADV_COMPONENT, component);
   }

   public int getLineWidth() {
      return (Integer)this.metadata.getIndex(offset(OFFSET, 1), 200);
   }

   public void setLineWidth(int value) {
      this.metadata.setIndex(offset(OFFSET, 1), EntityDataTypes.INT, value);
   }

   public int getBackgroundColor() {
      return (Integer)this.metadata.getIndex(offset(OFFSET, 2), 0);
   }

   public void setBackgroundColor(int value) {
      this.metadata.setIndex(offset(OFFSET, 2), EntityDataTypes.INT, value);
   }

   public byte getTextOpacity() {
      return (Byte)this.metadata.getIndex(offset(OFFSET, 3), -1);
   }

   public void setTextOpacity(byte value) {
      this.metadata.setIndex(offset(OFFSET, 3), EntityDataTypes.BYTE, value);
   }

   public boolean isShadow() {
      return this.getMaskBit(offset(OFFSET, 4), (byte)1);
   }

   public void setShadow(boolean value) {
      this.setMaskBit(offset(OFFSET, 4), (byte)1, value);
   }

   public boolean isSeeThrough() {
      return this.getMaskBit(offset(OFFSET, 4), (byte)2);
   }

   public void setSeeThrough(boolean value) {
      this.setMaskBit(offset(OFFSET, 4), (byte)2, value);
   }

   public boolean isUseDefaultBackground() {
      return this.getMaskBit(offset(OFFSET, 4), (byte)4);
   }

   public void setUseDefaultBackground(boolean value) {
      this.setMaskBit(offset(OFFSET, 4), (byte)4, value);
   }

   public boolean isAlignLeft() {
      return this.getMaskBit(offset(OFFSET, 4), (byte)8);
   }

   public void setAlignLeft(boolean value) {
      this.setMaskBit(OFFSET + 4, (byte)8, value);
   }

   public boolean isAlignRight() {
      return this.getMaskBit(offset(OFFSET, 4), (byte)16);
   }

   public void setAlignRight(boolean value) {
      this.setMaskBit(offset(OFFSET, 4), (byte)16, value);
   }

   static {
      OFFSET = AbstractDisplayMeta.MAX_OFFSET;
      MAX_OFFSET = offset(OFFSET, 5);
   }
}
