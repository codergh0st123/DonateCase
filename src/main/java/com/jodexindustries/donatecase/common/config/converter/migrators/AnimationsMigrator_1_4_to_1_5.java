package com.jodexindustries.donatecase.common.config.converter.migrators;

import com.jodexindustries.donatecase.api.config.Config;
import com.jodexindustries.donatecase.api.config.converter.ConfigMigrator;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import java.lang.reflect.InvocationTargetException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class AnimationsMigrator_1_4_to_1_5 implements ConfigMigrator {
   public void migrate(Config config) throws SerializationException {
      try {
         Class<?> popClazz = Class.forName("com.jodexindustries.donatecase.spigot.animations.pop.PopSettings");
         Object popObject = popClazz.getDeclaredConstructor().newInstance();
         config.node("POP").set(popObject);
         Class<?> selectClazz = Class.forName("com.jodexindustries.donatecase.spigot.animations.select.SelectSettings");
         Object selectObject = selectClazz.getDeclaredConstructor().newInstance();
         config.node("SELECT").set(selectObject);
         this.updatePosition(config.node("SHAPE", "StartPosition"));
         this.updatePosition(config.node("FIREWORK", "StartPosition"));
         ConfigurationNode wheelNode = config.node("WHEEL");
         wheelNode.node(new Object[]{"StartPosition"}).set(new CaseLocation((double)0.5F, (double)1.0F, (double)0.5F));
         wheelNode.removeChild("LiftingAlongX");
         wheelNode.removeChild("LiftingAlongY");
         wheelNode.removeChild("LiftingAlongZ");
         config.node("config", "version").set(15);
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException | InstantiationException e) {
         throw new SerializationException(e);
      }
   }

   private void updatePosition(ConfigurationNode node) throws SerializationException {
      CaseLocation startPosition = this.parseLocation(node);
      node.set(startPosition);
   }

   private CaseLocation parseLocation(ConfigurationNode node) {
      CaseLocation location = new CaseLocation(node.node(new Object[]{"X"}).getDouble(), node.node(new Object[]{"Y"}).getDouble(), node.node(new Object[]{"Z"}).getDouble());
      node.removeChild("X");
      node.removeChild("Y");
      node.removeChild("Z");
      return location;
   }
}
