package com.jodexindustries.donatecase.entitylib.wrapper;

import com.github.retrooper.packetevents.protocol.attribute.Attribute;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes.PropertyModifier.Operation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class WrapperEntityAttributes {
   public static final WrapperPlayServerUpdateAttributes.PropertyModifier.Operation ADDITION;
   public static final WrapperPlayServerUpdateAttributes.PropertyModifier.Operation MULTIPLY_BASE;
   public static final WrapperPlayServerUpdateAttributes.PropertyModifier.Operation MULTIPLY_TOTAL;
   private final WrapperEntity entity;
   private final List<WrapperPlayServerUpdateAttributes.Property> properties;

   public WrapperEntityAttributes(WrapperEntity entity) {
      this.entity = entity;
      this.properties = new CopyOnWriteArrayList();
   }

   public void setAttribute(Attribute attribute, double value, List<WrapperPlayServerUpdateAttributes.PropertyModifier> modifiers) {
      Optional var10000 = this.properties.stream().filter((property) -> property.getAttribute() == attribute).findFirst();
      List var10001 = this.properties;
      Objects.requireNonNull(var10001);
      var10000.ifPresent(var10001::remove);
      this.properties.add(new WrapperPlayServerUpdateAttributes.Property(attribute, value, modifiers));
      this.refresh();
   }

   public void setAttribute(Attribute attribute, double value, WrapperPlayServerUpdateAttributes.PropertyModifier modifier) {
      this.setAttribute(attribute, value, Collections.singletonList(modifier));
   }

   public void setAttribute(Attribute attribute, double value) {
      this.setAttribute(attribute, value, Collections.emptyList());
   }

   public List<WrapperPlayServerUpdateAttributes.Property> getProperties() {
      return new ArrayList(this.properties);
   }

   public void forEach(Consumer<WrapperPlayServerUpdateAttributes.Property> action) {
      this.properties.forEach(action);
   }

   public void clear() {
      this.properties.clear();
      this.refresh();
   }

   public void removeAttribute(Attribute attribute, WrapperPlayServerUpdateAttributes.PropertyModifier modifier) {
      this.properties.stream().filter((property) -> property.getAttribute() == attribute).findFirst().ifPresent((property) -> {
         property.getModifiers().remove(modifier);
         if (property.getModifiers().isEmpty()) {
            this.properties.remove(property);
         }

      });
      this.refresh();
   }

   public void removeAttribute(Attribute attribute) {
      Optional var10000 = this.properties.stream().filter((property) -> property.getAttribute() == attribute).findFirst();
      List var10001 = this.properties;
      Objects.requireNonNull(var10001);
      var10000.ifPresent(var10001::remove);
      this.refresh();
   }

   public void refresh() {
      if (this.entity.isSpawned()) {
         this.entity.sendPacketToViewers(this.createPacket());
      }

   }

   public WrapperPlayServerUpdateAttributes createPacket() {
      return new WrapperPlayServerUpdateAttributes(this.entity.getEntityId(), this.properties);
   }

   static {
      ADDITION = Operation.ADDITION;
      MULTIPLY_BASE = Operation.MULTIPLY_BASE;
      MULTIPLY_TOTAL = Operation.MULTIPLY_TOTAL;
   }
}
