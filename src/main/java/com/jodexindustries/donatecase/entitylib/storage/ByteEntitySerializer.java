package com.jodexindustries.donatecase.entitylib.storage;

import com.jodexindustries.donatecase.entitylib.wrapper.WrapperEntity;
import java.nio.ByteBuffer;

public class ByteEntitySerializer implements EntitySerializer<ByteBuffer, ByteBuffer> {
   public WrapperEntity read(ByteBuffer reader) {
      return null;
   }

   public void write(ByteBuffer writer, WrapperEntity wrapper) {
   }
}
