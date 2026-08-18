package com.jodexindustries.donatecase.common.managers;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.action.ActionException;
import com.jodexindustries.donatecase.api.data.action.CaseAction;
import com.jodexindustries.donatecase.api.manager.ActionManager;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.api.platform.Platform;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.common.actions.BroadcastActionExecutorImpl;
import com.jodexindustries.donatecase.common.actions.MessageActionExecutorImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ActionManagerImpl implements ActionManager {
   private static final Map<String, CaseAction> registeredActions = new ConcurrentHashMap();
   private final DCAPI api;
   private final Platform platform;

   public ActionManagerImpl(DCAPI api) {
      this.api = api;
      this.platform = api.getPlatform();
      List<? extends CaseAction> defaultActions = Arrays.asList(CaseAction.builder().name("[message]").addon(this.platform).executor(new MessageActionExecutorImpl()).description("Sends a message in the player's chat").build(), CaseAction.builder().name("[broadcast]").addon(this.platform).executor(new BroadcastActionExecutorImpl()).description("Sends a broadcast to the players").build());
      defaultActions.forEach(this::register);
   }

   public void register(CaseAction action) throws ActionException {
      if (this.isRegistered(action.name())) {
         throw new ActionException("Action with name " + action.name() + " already registered!");
      } else {
         registeredActions.put(action.name(), action);
      }
   }

   public void unregister(@NotNull String name) throws ActionException {
      if (!this.isRegistered(name)) {
         throw new ActionException("Action with name " + name + " already unregistered!");
      } else {
         registeredActions.remove(name);
      }
   }

   public void unregister() {
      List<String> list = new ArrayList(registeredActions.keySet());
      list.forEach(this::unregister);
   }

   public @NotNull Map<String, CaseAction> getMap() {
      return registeredActions;
   }

   public void execute(@Nullable DCPlayer player, @NotNull String action, int cooldown) {
      Optional<String> temp = this.getByStart(action);
      if (temp.isPresent()) {
         String context = action.replace((CharSequence)temp.get(), "").trim();
         Optional<CaseAction> caseAction = this.get((String)temp.get());
         if (caseAction.isPresent()) {
            this.platform.getScheduler().run(this.platform, (Runnable)(() -> {
               try {
                  ((CaseAction)caseAction.get()).execute(player, context);
               } catch (ActionException e) {
                  this.platform.getLogger().log(Level.WARNING, "Error with executing action: " + context, e);
               }

            }), (long)cooldown);
         }
      }
   }

   public void execute(@Nullable DCPlayer player, @NotNull List<String> actions) {
      for(String action : actions) {
         if (player != null) {
            action = DCTools.rc(this.api.getPlatform().getPAPI().setPlaceholders(player, action));
         }

         int cooldown = DCTools.extractCooldown(action);
         action = action.replaceFirst("\\[cooldown:(.*?)]", "");
         this.execute(player, action, cooldown);
      }

   }
}
