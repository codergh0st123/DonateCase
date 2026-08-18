package com.jodexindustries.donatecase.entitylib.wrapper.ai.goals;

import com.github.retrooper.packetevents.util.Vector3d;
import com.jodexindustries.donatecase.entitylib.extras.CoordinateUtil;
import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntityCreature;
import com.jodexindustries.donatecase.entitylib.wrapper.ai.GoalSelector;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public class RandomHeadMovementGoal extends GoalSelector {
   private static final Random RANDOM = new Random();
   private final int chancePerTick;
   private final Supplier<Integer> minimalLookTimeSupplier;
   private final Function<WrapperEntityCreature, Vector3d> randomDirectionFunction;
   private Vector3d lookDirection;
   private int lookTime;

   public RandomHeadMovementGoal(WrapperEntityCreature entityCreature, int chancePerTick) {
      this(entityCreature, chancePerTick, () -> 20 + RANDOM.nextInt(20), (creature) -> {
         double n = (Math.PI * 2D) * RANDOM.nextDouble();
         return new Vector3d((double)((float)Math.cos(n)), (double)0.0F, (double)((float)Math.sin(n)));
      });
   }

   public RandomHeadMovementGoal(WrapperEntityCreature entityCreature, int chancePerTick, @NotNull Supplier<Integer> minimalLookTimeSupplier, @NotNull Function<WrapperEntityCreature, Vector3d> randomDirectionFunction) {
      super(entityCreature);
      this.lookTime = 0;
      this.chancePerTick = chancePerTick;
      this.minimalLookTimeSupplier = minimalLookTimeSupplier;
      this.randomDirectionFunction = randomDirectionFunction;
   }

   public boolean shouldStart() {
      return RANDOM.nextInt(this.chancePerTick) == 0;
   }

   public void start() {
      this.lookTime = (Integer)this.minimalLookTimeSupplier.get();
      this.lookDirection = (Vector3d)this.randomDirectionFunction.apply(this.entity);
   }

   public void tick(long time) {
      --this.lookTime;
      this.entity.teleport(CoordinateUtil.withDirection(this.entity.getLocation(), this.lookDirection), this.entity.isOnGround());
   }

   public boolean shouldEnd() {
      return this.lookTime < 0;
   }

   public void end() {
   }
}
