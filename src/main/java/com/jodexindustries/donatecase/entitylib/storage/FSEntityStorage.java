package com.jodexindustries.donatecase.entitylib.storage;

import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.util.Collection;
import java.util.Collections;

public class FSEntityStorage implements EntityStorage {
   public Collection<WrapperEntity> readAll() {
      return Collections.emptyList();
   }

   public void writeAll(Collection<WrapperEntity> entities) {
   }
}
