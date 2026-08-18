package com.jodexindustries.donatecase.entitylib.storage;

import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;

public interface EntitySerializer<R, W> {
   WrapperEntity read(R var1);

   void write(W var1, WrapperEntity var2);
}
