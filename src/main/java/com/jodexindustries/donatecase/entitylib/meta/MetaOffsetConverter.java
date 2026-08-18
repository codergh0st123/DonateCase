package com.jodexindustries.donatecase.entitylib.meta;

import com.jodexindustries.donatecase.entitylib.EntityLib;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public final class MetaOffsetConverter {
   private MetaOffsetConverter() {
   }

   public static final class EntityMetaOffsets {
      private EntityMetaOffsets() {
      }

      public static byte airTicksOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 47 && protocolVersion <= 767) {
            return 1;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte customNameOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 47 && protocolVersion <= 767) {
            return 2;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte customNameVisibleOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 47 && protocolVersion <= 767) {
            return 3;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte silentOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 47 && protocolVersion <= 767) {
            return 4;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte hasNoGravityOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 47 && protocolVersion <= 767) {
            return 5;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte poseOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 47 && protocolVersion <= 767) {
            return 6;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte ticksFrozenInPowderedSnowOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 47 && protocolVersion <= 767) {
            return 7;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }
   }

   public static final class AbstractDisplayMetaOffsets {
      private AbstractDisplayMetaOffsets() {
      }

      public static byte interpolationDelayOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 762 && protocolVersion <= 767) {
            return 8;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte transformationDurationOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 762 && protocolVersion <= 767) {
            return 9;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte positionRotationInterpolationDurationOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 10;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte translationOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 11;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 10;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte scaleOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 12;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 11;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte leftRotationOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 13;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 12;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte rightRotationOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 14;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 13;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte billboardConstraintsOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 15;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 14;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte brightnessOverrideOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 16;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 15;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte viewRangeOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 17;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 16;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte shadowRadiusOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 18;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 17;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte shadowStrengthOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 19;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 18;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte widthOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 20;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 19;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte heightOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 21;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 20;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte glowColorOverrideOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 22;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 21;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }
   }

   public static final class BlockDisplayMetaOffsets {
      private BlockDisplayMetaOffsets() {
      }

      public static byte blockIdOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 23;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 22;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }
   }

   public static final class ItemDisplayMetaOffsets {
      private ItemDisplayMetaOffsets() {
      }

      public static byte itemOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 24;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 23;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte displayTypeOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 25;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 24;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }
   }

   public static final class TextDisplayMetaOffsets {
      private TextDisplayMetaOffsets() {
      }

      public static byte textOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 26;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 25;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte textColorOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 27;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 26;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte textBackgroundColorOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 28;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 27;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte textBackgroundOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 29;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 28;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte textScaleOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 30;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 29;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte textAlignmentOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 31;
         } else if (protocolVersion >= 762 && protocolVersion <= 767) {
            return 30;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte lineWidthOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 32;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 31;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte backgroundColorOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 33;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 32;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte textOpacityOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 34;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 33;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte shadowOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 35;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 34;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte seeThroughOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 36;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 35;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte useDefaultBackgroundOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 37;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 36;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte allighnLeftOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 38;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 37;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte allighnRightOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 39;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 38;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }

      public static byte allighnCenterOffset() {
         int protocolVersion = EntityLib.getApi().getPacketEvents().getServerManager().getVersion().getProtocolVersion();
         if (protocolVersion >= 764 && protocolVersion <= 767) {
            return 40;
         } else if (protocolVersion >= 762 && protocolVersion <= 763) {
            return 39;
         } else {
            throw new RuntimeException("Unknown protocol version for this method");
         }
      }
   }
}
