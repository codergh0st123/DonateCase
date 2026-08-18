package com.jodexindustries.donatecase.api.tools;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.SplittableRandom;
import java.util.TreeSet;
import lombok.Generated;

public final class ProbabilityCollection<E> {
   private final NavigableSet<ProbabilitySetElement<E>> collection = new TreeSet(Comparator.comparingDouble((rec$) -> ((ProbabilitySetElement)rec$).getIndex()));
   private final SplittableRandom random = new SplittableRandom();
   private double totalProbability = (double)0.0F;

   public int size() {
      return this.collection.size();
   }

   public boolean isEmpty() {
      return this.collection.isEmpty();
   }

   public boolean contains(E object) {
      if (object == null) {
         throw new IllegalArgumentException("Cannot check if null object is contained in this collection");
      } else {
         return this.collection.stream().anyMatch((entry) -> entry.getObject().equals(object));
      }
   }

   public Iterator<ProbabilitySetElement<E>> iterator() {
      return this.collection.iterator();
   }

   public void add(E object, double probability) {
      if (object == null) {
         throw new IllegalArgumentException("Cannot add null object");
      } else {
         if (probability <= (double)0.0F) {
            probability = (double)1.0F;
         }

         ProbabilitySetElement<E> entry = new ProbabilitySetElement<E>(object, probability);
         entry.setIndex(this.totalProbability + (double)1.0F);
         this.collection.add(entry);
         this.totalProbability += probability;
      }
   }

   public boolean remove(E object) {
      if (object == null) {
         throw new IllegalArgumentException("Cannot remove null object");
      } else {
         Iterator<ProbabilitySetElement<E>> it = this.iterator();
         boolean removed = false;

         while(it.hasNext()) {
            ProbabilitySetElement<E> entry = (ProbabilitySetElement)it.next();
            if (entry.getObject().equals(object)) {
               this.totalProbability -= entry.getProbability();
               it.remove();
               removed = true;
            }
         }

         if (removed) {
            double previousIndex = (double)0.0F;

            for(ProbabilitySetElement<E> entry : this.collection) {
               previousIndex = entry.setIndex(previousIndex + (double)1.0F) + (entry.getProbability() - (double)1.0F);
            }
         }

         return removed;
      }
   }

   public void clear() {
      this.collection.clear();
      this.totalProbability = (double)0.0F;
   }

   public E get() {
      if (this.isEmpty()) {
         throw new IllegalStateException("Cannot get an object out of a empty collection");
      } else {
         ProbabilitySetElement<E> toFind = new ProbabilitySetElement<E>((Object)null, (double)0.0F);
         toFind.setIndex(this.random.nextDouble((double)1.0F, this.totalProbability + (double)1.0F));
         return (E)((ProbabilitySetElement)Objects.requireNonNull((ProbabilitySetElement)this.collection.floor(toFind))).getObject();
      }
   }

   @Generated
   public double getTotalProbability() {
      return this.totalProbability;
   }

   public static final class ProbabilitySetElement<T> {
      private final T object;
      private final double probability;
      private double index;

      private ProbabilitySetElement(T object, double probability) {
         this.object = object;
         this.probability = probability;
      }

      private double getIndex() {
         return this.index;
      }

      private double setIndex(double index) {
         this.index = index;
         return this.index;
      }

      @Generated
      public T getObject() {
         return this.object;
      }

      @Generated
      public double getProbability() {
         return this.probability;
      }
   }
}
