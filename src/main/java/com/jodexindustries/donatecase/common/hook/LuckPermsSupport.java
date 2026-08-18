package com.jodexindustries.donatecase.common.hook;

import java.util.UUID;
import lombok.Generated;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.jetbrains.annotations.Nullable;

public class LuckPermsSupport {
   private LuckPerms provider;

   public void load() {
      this.provider = LuckPermsProvider.get();
   }

   public @Nullable String getPrimaryGroup(UUID uuid) {
      if (this.provider != null) {
         User user = this.provider.getUserManager().getUser(uuid);
         if (user != null) {
            return user.getPrimaryGroup();
         }
      }

      return null;
   }

   @Generated
   public LuckPerms getProvider() {
      return this.provider;
   }
}
