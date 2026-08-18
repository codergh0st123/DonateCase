package com.jodexindustries.donatecase.common.gui;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataMaterial;
import com.jodexindustries.donatecase.api.data.casedata.gui.CaseGui;
import com.jodexindustries.donatecase.api.data.casedata.gui.CaseGuiWrapper;
import com.jodexindustries.donatecase.api.data.casedata.gui.CaseInventory;
import com.jodexindustries.donatecase.api.data.casedata.gui.typeditem.TypedItem;
import com.jodexindustries.donatecase.api.data.casedata.gui.typeditem.TypedItemException;
import com.jodexindustries.donatecase.api.data.casedata.gui.typeditem.TypedItemHandler;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.api.platform.Platform;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.common.command.sub.KeysCommand;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CaseGuiWrapperImpl implements CaseGuiWrapper {
   private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);
   protected final Platform platform;
   protected final DCPlayer player;
   protected final CaseData caseData;
   protected final CaseLocation location;
   protected final CaseGui temporary;
   protected final CaseInventory inventory;
   private List<CaseData.History> globalHistoryData;

   public CaseGuiWrapperImpl(@NotNull Platform platform, @NotNull DCPlayer player, @NotNull CaseData caseData, @NotNull CaseLocation location) {
      this.platform = platform;
      this.player = player;
      this.caseData = caseData;
      this.location = location;
      this.temporary = caseData.caseGui().clone();
      this.inventory = platform.getTools().createInventory(this.temporary.size(), DCTools.rc(this.setPlaceholders(this.temporary.title())));
      this.load().thenAccept((loaded) -> {
         platform.getScheduler().run(platform, (Runnable)(() -> player.openInventory(this.inventory.getInventory())), 0L);
         this.startUpdateTask();
      }).exceptionally((ex) -> {
         platform.getLogger().log(Level.WARNING, "GUI loading failed: " + ex.getMessage());
         player.sendMessage(DCTools.rc("&cFailed to load the GUI. Please try again later."));
         return null;
      });
   }

   private CompletableFuture<Void> load() {
      CompletableFuture<Void> future = new CompletableFuture();
      this.platform.getScheduler().async(this.platform, (Runnable)(() -> {
         this.globalHistoryData = DCTools.sortHistoryDataByDate(this.platform.getAPI().getDatabase().getCache());

         for(CaseGui.Item item : this.temporary.items().values()) {
            try {
               this.processItem(item);
            } catch (TypedItemException e) {
               this.platform.getLogger().log(Level.WARNING, "Error occurred while loading item: " + item.node().key(), e);
            }
         }

         future.complete((Object)null);
      }), 0L);
      SCHEDULER.schedule(() -> {
         if (!future.isDone()) {
            future.completeExceptionally(new TimeoutException("GUI loading timed out"));
         }

      }, 5L, TimeUnit.SECONDS);
      return future;
   }

   private void updateMeta(CaseGui.Item temp) {
      CaseDataMaterial original = this.getOriginal((String)temp.node().key());
      CaseDataMaterial material = temp.material();
      material.displayName(this.setPlaceholders(original.displayName()));
      material.lore(this.setPlaceholders(original.lore()));
      material.updateMeta();
   }

   private void colorize(CaseDataMaterial material) {
      material.displayName(DCTools.rc(material.displayName()));
      material.lore(DCTools.rc(material.lore()));
      material.updateMeta();
   }

   private void startUpdateTask() {
      int updateRate = this.temporary.updateRate();
      if (updateRate >= 0) {
         this.platform.getScheduler().async(this.platform, (Consumer)((task) -> {
            if (!this.platform.getAPI().getGUIManager().getMap().containsKey(this.player.getUniqueId())) {
               task.cancel();
            }

            this.load();
         }), (long)updateRate, (long)updateRate);
      }

   }

   private CaseDataMaterial getOriginal(String itemName) {
      return ((CaseGui.Item)this.caseData.caseGui().items().get(itemName)).material();
   }

   private void processItem(CaseGui.Item item) throws TypedItemException {
      String itemType = item.type();
      if (!itemType.equalsIgnoreCase("DEFAULT")) {
         Optional<TypedItem> typedItem = this.platform.getAPI().getGuiTypedItemManager().getFromString(itemType);
         if (typedItem.isPresent()) {
            TypedItemHandler handler = ((TypedItem)typedItem.get()).handler();
            if (handler != null) {
               item = handler.handle(this, item);
            }

            if (((TypedItem)typedItem.get()).updateMeta()) {
               this.updateMeta(item);
            }
         }
      } else {
         this.updateMeta(item);
      }

      CaseDataMaterial material = item.material();
      if (material.itemStack() == null) {
         material.itemStack(this.platform.getTools().loadCaseItem(material.id()));
      }

      this.colorize(material);

      for(Integer slot : item.slots()) {
         this.inventory.setItem(slot, item.material().itemStack());
      }

   }

   private String setPlaceholders(@Nullable String text) {
      if (text == null) {
         return null;
      } else {
         String caseType = this.caseData.caseType();
         text = this.platform.getPAPI().setPlaceholders(this.player, text);
         return KeysCommand.formatMessage(this.player.getName(), text.replace("%casetype%", caseType), true, caseType);
      }
   }

   private List<String> setPlaceholders(List<String> lore) {
      return (List)lore.stream().map(this::setPlaceholders).collect(Collectors.toList());
   }

   public @NotNull CaseInventory getInventory() {
      return this.inventory;
   }

   public @NotNull CaseLocation getLocation() {
      return this.location;
   }

   public @NotNull DCPlayer getPlayer() {
      return this.player;
   }

   public @NotNull CaseData getCaseData() {
      return this.caseData;
   }

   public @NotNull CaseGui getTemporary() {
      return this.temporary;
   }

   public @NotNull List<CaseData.History> getGlobalHistoryData() {
      return this.globalHistoryData;
   }
}
