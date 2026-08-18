package com.jodexindustries.donatecase.entitylib.storage;

import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.util.Collection;

public interface EntityStorage {
   Collection<WrapperEntity> readAll();

   void writeAll(Collection<WrapperEntity> var1);
}
