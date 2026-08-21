package com.jodexindustries.donatecase.common.managers;

import com.jodexindustries.donatecase.api.data.ActiveCase;
import com.jodexindustries.donatecase.api.data.animation.Animation;
import com.jodexindustries.donatecase.api.data.animation.CaseAnimation;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataItem;
import com.jodexindustries.donatecase.api.data.casedata.gui.CaseGuiWrapper;
import com.jodexindustries.donatecase.api.data.storage.CaseInfo;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.event.DCEvent;
import com.jodexindustries.donatecase.api.event.animation.AnimationEndEvent;
import com.jodexindustries.donatecase.api.event.animation.AnimationPreStartEvent;
import com.jodexindustries.donatecase.api.event.animation.AnimationStartEvent;
import com.jodexindustries.donatecase.api.manager.AnimationManager;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.api.tools.ProbabilityCollection;
import com.jodexindustries.donatecase.common.DonateCase;
import com.jodexindustries.donatecase.common.platform.BackendPlatform;
import com.jodexindustries.donatecase.common.tools.LocalPlaceholder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;

public class AnimationManagerImpl implements AnimationManager {
   private static final Map<String, CaseAnimation> registeredAnimations = new ConcurrentHashMap<>();
   private static final Map<UUID, ActiveCase> activeCases = new ConcurrentHashMap<>();
   private static final Map<CaseLocation, List<UUID>> activeCasesByBlock = new ConcurrentHashMap<>();
   private final DonateCase api;
   private final BackendPlatform backend;

   public AnimationManagerImpl(DonateCase api) {
      this.api = api;
      this.backend = api.getPlatform();
   }

   public boolean register(CaseAnimation animation) {
      String name = animation.getName();
      if (!this.isRegistered(name)) {
         registeredAnimations.put(name, animation);
         return true;
      } else {
         this.backend.getLogger().warning("Animation " + name + " already registered!");
         return false;
      }
   }

   public void unregister(@NotNull String name) {
      if (this.isRegistered(name)) {
         registeredAnimations.remove(name);
      } else {
         this.backend.getLogger().warning("Animation with name " + name + " already unregistered!");
      }

   }

   public void unregister() {
      List<String> list = new ArrayList<>(registeredAnimations.keySet());
      list.forEach(this::unregister);
   }

   public CompletableFuture<UUID> start(@NotNull DCPlayer player, @NotNull CaseLocation location, @NotNull CaseData caseData) {
      return this.start(player, location, caseData, caseData.cooldownBeforeStart());
   }

   public CompletableFuture<UUID> start(@NotNull DCPlayer player, @NotNull CaseLocation location, @NotNull CaseData caseData, int delay) {
      return this.start(player, location, caseData, false, delay);
   }

   public CompletableFuture<UUID> start(@NotNull DCPlayer player, @NotNull CaseLocation location, @NotNull CaseData caseData, boolean keyRemoved, int delay) {
      CaseData data = caseData.clone();
      String animation;
      if (!data.animation().equalsIgnoreCase("RANDOM")) {
         animation = data.animation();
      } else {
         animation = this.getRandomAnimation(this.getSettings(data));
         data.animation(animation);
      }

      ConfigurationNode settings = this.getSettings(data);
      CaseLocation temp = location.clone();
      CaseAnimation caseAnimation = this.get(animation);
      if (!this.validateStartConditions(data, caseAnimation, settings, temp, player)) {
         return CompletableFuture.completedFuture(null);
      } else {
         assert caseAnimation != null;

         data.items(DCTools.sortItemsByIndex(data.items()));
         CaseDataItem winItem = data.getRandomItem();
         winItem.material().displayName(this.api.getPlatform().getPAPI().setPlaceholders(player, winItem.material().displayName()));
         AnimationPreStartEvent event = new AnimationPreStartEvent(player, data, temp, winItem);
         this.api.getEventBus().post((DCEvent)event);
         winItem = event.winItem();
         UUID uuid = UUID.randomUUID();
         CompletableFuture<UUID> animationCompletion = new CompletableFuture();
         if (caseAnimation.isRequireBlock()) {
            CaseInfo info = this.api.getConfigManager().getCaseStorage().get(temp);
            if (info != null) {
               CaseLocation caseLocation = info.location();
               temp.pitch(caseLocation.pitch());
               temp.yaw(caseLocation.yaw());
            }

            for(CaseGuiWrapper gui : this.api.getGUIManager().getMap().values()) {
               if (gui.getLocation().equals(temp)) {
                  gui.getPlayer().closeInventory();
               }
            }
         }

         Class<? extends Animation> animationClass = caseAnimation.getAnimation();

         try {
            Animation javaAnimation = (Animation)animationClass.getDeclaredConstructor().newInstance();
            javaAnimation.init(player, temp.clone(), uuid, data, winItem, settings);
            ActiveCase activeCase = new ActiveCase(uuid, temp, player, winItem, data.caseType(), javaAnimation);
            activeCase.locked(caseAnimation.isRequireBlock());
            activeCase.keyRemoved(keyRemoved);
            activeCases.put(uuid, activeCase);
            ((List)activeCasesByBlock.computeIfAbsent(temp, (k) -> new ArrayList<>())).add(uuid);
            this.api.getPlatform().getScheduler().run(this.backend, (Runnable)(() -> {
               try {
                  if (caseAnimation.isRequireBlock()) {
                     CaseData.Hologram hologram = data.hologram();
                     if (hologram != null && hologram.enabled()) {
                        this.api.getHologramManager().remove(temp);
                     }
                  }

                  javaAnimation.start();
                  animationCompletion.complete(uuid);
                  this.api.getEventBus().post((DCEvent)(new AnimationStartEvent(activeCase)));
               } catch (Throwable t) {
                  this.backend.getLogger().log(Level.WARNING, "Error with starting animation " + animation, t);
                  if (caseAnimation.isRequireBlock()) {
                     activeCasesByBlock.remove(temp);
                     CaseData.Hologram hologram = data.hologram();
                     if (hologram != null && hologram.enabled()) {
                        this.api.getHologramManager().create(temp, hologram);
                     }
                  }

                  activeCases.remove(uuid);
                  animationCompletion.complete(null);
               }

            }), (long)delay);
         } catch (Throwable t) {
            this.backend.getLogger().log(Level.WARNING, "Error with starting animation " + animation, t);
            if (caseAnimation.isRequireBlock()) {
               activeCasesByBlock.remove(location);
            }

            animationCompletion.complete(null);
         }

         return animationCompletion;
      }
   }

   public void preEnd(UUID uuid) {
      ActiveCase activeCase = (ActiveCase)activeCases.get(uuid);
      if (activeCase == null) {
         this.backend.getLogger().warning("Animation with uuid: " + uuid + " not found!");
      } else {
         CaseData caseData = this.api.getCaseManager().get(activeCase.caseType());
         if (caseData != null) {
            this.preEnd(caseData, activeCase.player(), activeCase.winItem());
         }

      }
   }

   public void preEnd(CaseData caseData, DCPlayer player, CaseDataItem item) {
      CaseDataItem.RandomAction randomAction = item.giveType().equalsIgnoreCase("ONE") ? null : item.getRandomAction();
      Map<String, Integer> levelGroups = this.api.getConfigManager().getConfig().levelGroups();
      if (!caseData.levelGroups().isEmpty()) {
         levelGroups = caseData.levelGroups();
      }

      String primaryGroup = this.backend.getLuckPermsSupport().getPrimaryGroup(player.getUniqueId());
      this.executeActions(player, caseData, item, randomAction, isBetterOrEqual(levelGroups, primaryGroup, item.group()));
      this.saveOpenInfo(caseData, player, item, randomAction);
   }

   public void end(UUID uuid) {
      ActiveCase activeCase = (ActiveCase)activeCases.get(uuid);
      if (activeCase == null) {
         this.backend.getLogger().warning("Animation with uuid: " + uuid + " not found!");
      } else {
         this.animationEnd(activeCase);
      }
   }

   private void animationEnd(@NotNull ActiveCase activeCase) {
      CaseLocation block = activeCase.block();
      activeCases.remove(activeCase.uuid());
      activeCasesByBlock.remove(block);
      DCPlayer player = activeCase.player();
      if (!activeCase.keyRemoved()) {
         this.api.getCaseKeyManager().remove(activeCase.caseType(), player.getName(), 1);
      }

      this.api.getEventBus().post((DCEvent)(new AnimationEndEvent(activeCase)));
      CaseData caseData = this.api.getCaseManager().get(activeCase.caseType());
      if (caseData != null) {
         CaseAnimation caseAnimation = this.get(caseData.animation());
         if (caseAnimation != null) {
            if (caseAnimation.isRequireBlock()) {
               CaseData.Hologram hologram = caseData.hologram();
               if (hologram != null && hologram.enabled()) {
                  this.api.getHologramManager().create(block, hologram);
               }
            }

         }
      }
   }

   public boolean isRegistered(String name) {
      return registeredAnimations.containsKey(name);
   }

   public @Nullable CaseAnimation get(String animation) {
      return (CaseAnimation)registeredAnimations.get(animation);
   }

   public Map<String, CaseAnimation> getMap() {
      return registeredAnimations;
   }

   public Map<UUID, ActiveCase> getActiveCases() {
      return activeCases;
   }

   public Map<CaseLocation, List<UUID>> getActiveCasesByBlock() {
      return activeCasesByBlock;
   }

   private boolean validateStartConditions(CaseData caseData, CaseAnimation animation, ConfigurationNode settings, CaseLocation location, DCPlayer player) {
      if (animation == null) {
         this.backend.getLogger().log(Level.WARNING, "Case animation " + caseData.animation() + " does not exist!");
         return false;
      } else if (location.getWorld() != null && location.getWorld().name() != null) {
         if (this.isLocked(location)) {
            this.backend.getLogger().warning("Player " + player.getName() + " trying to start animation while another animation is running in case: " + caseData.caseType());
            return false;
         } else if (animation.isRequireSettings() && settings == null) {
            this.backend.getLogger().warning("Animation " + animation + " requires settings for starting!");
            return false;
         } else if (caseData.items().isEmpty()) {
            this.backend.getLogger().warning("Player " + player.getName() + " trying to start animation without items in case: " + caseData.caseType());
            return false;
         } else if (!caseData.hasRealItems()) {
            this.backend.getLogger().warning("Player " + player.getName() + " trying to start animation without real (chance > 0) items in case: " + caseData.caseType());
            return false;
         } else {
            return true;
         }
      } else {
         this.backend.getLogger().warning("Player " + player.getName() + " trying to start animation without world name in case: " + caseData.caseType() + " Check the Cases.yml file!");
         return false;
      }
   }

   private void saveOpenInfo(@NotNull CaseData caseData, @NotNull DCPlayer player, @NotNull CaseDataItem item, CaseDataItem.@Nullable RandomAction action) {
      this.backend.getScheduler().async(this.backend, (Runnable)(() -> {
         CaseData.History newEntry = new CaseData.History(item.getName(), caseData.caseType(), player.getName(), System.currentTimeMillis(), item.group(), action == null ? null : action.getName());
         this.api.getDatabase().addHistory(caseData.caseType(), newEntry, caseData.historyDataSize());
         this.api.getCaseOpenManager().add(caseData.caseType(), player.getName(), 1);
      }), 0L);
   }

   public void executeActions(DCPlayer player, CaseData caseData, CaseDataItem item, CaseDataItem.RandomAction randomAction, boolean alternative) {
      Collection<LocalPlaceholder> placeholders = LocalPlaceholder.of(caseData);
      placeholders.add(LocalPlaceholder.of("%player%", player.getName()));
      placeholders.addAll(LocalPlaceholder.of(item));
      List<String> configuredActions = item.getActionsBasedOnChoice(randomAction, alternative);
      List<String> localizedActions = configuredActions.stream()
              .map(action -> this.isBroadcastAction(action) ? action : this.api.getPlatform().getPAPI().setPlaceholders(player, action))
              .toList();
      List<String> actions = DCTools.rt(localizedActions, placeholders);
      this.api.getActionManager().execute(player, actions);
   }

   private boolean isBroadcastAction(String action) {
      return action.regionMatches(true, 0, "[broadcast]", 0, 11);
   }

   public static boolean isBetterOrEqual(Map<String, Integer> groupLevels, String playerGroup, String rewardGroup) {
      Integer playerLevel = (Integer)groupLevels.get(playerGroup);
      Integer rewardLevel = (Integer)groupLevels.get(rewardGroup);
      return playerLevel != null && rewardLevel != null && playerLevel >= rewardLevel;
   }

   public String getRandomAnimation(ConfigurationNode settings) {
      ProbabilityCollection<String> collection = new ProbabilityCollection<String>();
      settings.childrenMap().forEach((key, value) -> collection.add((String)key, (double)value.getInt()));
      return collection.get();
   }

   private ConfigurationNode getSettings(CaseData caseData) {
      return caseData.animationSettings().isNull() ? this.api.getConfigManager().getAnimations().node(new Object[]{caseData.animation()}) : caseData.animationSettings();
   }
}
