package com.jodexindustries.donatecase.api.tools;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.armorstand.ArmorStandCreator;
import com.jodexindustries.donatecase.api.chat.ColorUtils;
import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataItem;
import com.jodexindustries.donatecase.api.data.casedata.gui.CaseInventory;
import com.jodexindustries.donatecase.api.data.material.CaseMaterial;
import com.jodexindustries.donatecase.api.data.material.CaseMaterialException;
import com.jodexindustries.donatecase.api.data.storage.CaseLocation;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommand;
import com.jodexindustries.donatecase.api.manager.MaterialManager;
import com.jodexindustries.donatecase.api.platform.DCCommandSender;
import com.jodexindustries.donatecase.api.platform.DCOfflinePlayer;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DCTools {
   public abstract CaseInventory createInventory(int var1, @Nullable String var2);

   public abstract ArmorStandCreator createArmorStand(UUID var1, CaseLocation var2);

   public abstract @Nullable Object loadCaseItem(String var1);

   public static DateFormat getDateFormat() {
      return new SimpleDateFormat(DCAPI.getInstance().getConfigManager().getConfig().dateFormat());
   }

   public static boolean isValidPlayerName(String player) {
      return DCAPI.getInstance().getConfigManager().getConfig().checkPlayerName() ? Arrays.stream(DCAPI.getInstance().getPlatform().getOfflinePlayers()).map(DCOfflinePlayer::getName).anyMatch((name) -> name != null && name.equals(player.trim())) : true;
   }

   public static @NotNull List<String> resolveSDGCompletions(String[] args) {
      List<String> value = new ArrayList(DCAPI.getInstance().getCaseManager().getMap().keySet());
      List<String> list = new ArrayList();
      if (args.length == 1) {
         list.addAll((Collection)Arrays.stream(DCAPI.getInstance().getPlatform().getOnlinePlayers()).map(DCPlayer::getName).filter((px) -> px.startsWith(args[0])).collect(Collectors.toList()));
         return list;
      } else if (args.length >= 3) {
         if (args.length == 4) {
            list.add("-s");
            return list;
         } else {
            return new ArrayList();
         }
      } else {
         if (args[args.length - 1].isEmpty()) {
            list = value;
         } else {
            list.addAll((Collection)value.stream().filter((tmp) -> tmp.startsWith(args[args.length - 1])).collect(Collectors.toList()));
         }

         return list;
      }
   }

   public static @Nullable Object getItemFromManager(@NotNull String id) {
      MaterialManager manager = DCAPI.getInstance().getMaterialManager();
      Optional<String> temp = manager.getByStart(id);
      if (temp.isPresent()) {
         CaseMaterial caseMaterial = manager.get((String)temp.get());
         if (caseMaterial != null) {
            String context = id.replace((CharSequence)temp.get(), "").replaceFirst(":", "").trim();

            try {
               return caseMaterial.handle(context);
            } catch (CaseMaterialException e) {
               DCAPI.getInstance().getPlatform().getLogger().log(Level.WARNING, "Error with handling material " + context, e);
            }
         }
      }

      return null;
   }

   public static String prefix(String text) {
      return rc(DCAPI.getInstance().getConfigManager().getMessages().getString("prefix") + text);
   }

   public static String rc(String text) {
      return text == null ? null : ColorUtils.color(text);
   }

   public static String rt(String text, Placeholder... placeholders) {
      return text != null && placeholders.length != 0 ? rt(text, (Collection)Arrays.asList(placeholders)) : text;
   }

   public static String rt(String text, Collection<? extends Placeholder> placeholders) {
      if (text != null && placeholders != null && !placeholders.isEmpty()) {
         StringBuilder result = new StringBuilder(text);

         int index;
         for(Placeholder placeholder : placeholders) {
            while((index = result.indexOf(placeholder.name())) != -1) {
               result.replace(index, index + placeholder.name().length(), placeholder.value());
            }
         }

         return rc(result.toString());
      } else {
         return text;
      }
   }

   public static List<String> rt(List<String> text, Collection<? extends Placeholder> placeholders) {
      return text == null ? null : (List)text.stream().map((t) -> rt(t, placeholders)).collect(Collectors.toCollection(ArrayList::new));
   }

   public static List<String> rt(List<String> text, Placeholder... placeholders) {
      return text == null ? null : (List)text.stream().map((t) -> rt(t, placeholders)).collect(Collectors.toCollection(ArrayList::new));
   }

   public static List<String> rc(List<String> list) {
      return list == null ? null : (List)list.stream().map(DCTools::rc).collect(Collectors.toCollection(ArrayList::new));
   }

   public static boolean isHasCommandForSender(DCCommandSender sender, Map<String, List<Map<String, SubCommand>>> addonsMap) {
      Stream var10000 = addonsMap.keySet().stream();
      Objects.requireNonNull(addonsMap);
      return var10000.map(addonsMap::get).anyMatch((commands) -> isHasCommandForSender(sender, commands));
   }

   public static boolean isHasCommandForSender(DCCommandSender sender, List<Map<String, SubCommand>> commands) {
      return commands.stream().flatMap((command) -> command.values().stream()).map(SubCommand::permission).anyMatch((permission) -> permission == null || sender.hasPermission(permission));
   }

   public static String getLocalPlaceholder(String string) {
      Pattern pattern = Pattern.compile("%(.*?)%");
      Matcher matcher = pattern.matcher(string);
      if (matcher.find()) {
         int startIndex = string.indexOf("%") + 1;
         int endIndex = string.lastIndexOf("%");
         return string.substring(startIndex, endIndex);
      } else {
         return "null";
      }
   }

   public static int getPluginVersion(String version) {
      version = version.replaceAll("\\.", "");
      if (version.length() == 4) {
         return Integer.parseInt(version);
      } else {
         version = version.concat("0000");
         return Integer.parseInt(version.substring(0, 4));
      }
   }

   public static int extractCooldown(String action) {
      Pattern pattern = Pattern.compile("\\[cooldown:(.*?)]");
      Matcher matcher = pattern.matcher(action);
      return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
   }

   public static List<CaseData.History> sortHistoryDataByCase(List<CaseData.History> historyData, String caseType) {
      List<CaseData.History> list = new ArrayList();

      for(CaseData.History data : historyData) {
         if (data != null && data.caseType().equals(caseType)) {
            list.add(data);
         }
      }

      list.sort(Comparator.comparingLong((object) -> ((CaseData.History)object).time()).reversed());
      return list;
   }

   public static List<CaseData.History> sortHistoryDataByDate(List<CaseData.History> list) {
      return (List)list.stream().filter(Objects::nonNull).sorted(Comparator.comparingLong((object) -> ((CaseData.History)object).time()).reversed()).collect(Collectors.toList());
   }

   public static Map<String, CaseDataItem> sortItemsByIndex(Map<String, CaseDataItem> items) {
      return (Map)items.entrySet().stream().sorted(Entry.comparingByValue(Comparator.comparingInt(CaseDataItem::index))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
   }

   public static boolean isValidGuiSize(int size) {
      return size >= 9 && size <= 54 && size % 9 == 0;
   }
}
