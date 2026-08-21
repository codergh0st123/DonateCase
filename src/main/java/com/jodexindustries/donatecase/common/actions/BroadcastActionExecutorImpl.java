package com.jodexindustries.donatecase.common.actions;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.action.ActionExecutor;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.api.tools.Placeholder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BroadcastActionExecutorImpl implements ActionExecutor {
   private static final Pattern LANGUAGE_PLACEHOLDER = Pattern.compile("%CORE_LANG:([^%]+)%", Pattern.CASE_INSENSITIVE);
   private static final Pattern BROADCAST_PLACEHOLDERS = Pattern.compile(" \\[donatecase-placeholders:([A-Za-z0-9_.,-]*)]$");

   public void execute(@Nullable DCPlayer player, @NotNull String context) {
      for(DCPlayer viewer : DCAPI.getInstance().getPlatform().getOnlinePlayers()) {
         viewer.sendMessage(this.resolveMessage(player, viewer, context));
      }
   }

   private String resolveMessage(@Nullable DCPlayer source, @NotNull DCPlayer viewer, @NotNull String context) {
      BroadcastContext broadcast = this.readBroadcastContext(context);
      String localized = this.resolveLanguage(viewer, broadcast.message());
      String dynamic = DCTools.rt(localized, broadcast.placeholders());
      DCPlayer placeholderPlayer = source == null ? viewer : source;
      return DCTools.rc(DCAPI.getInstance().getPlatform().getPAPI().setPlaceholders(placeholderPlayer, dynamic));
   }

   private BroadcastContext readBroadcastContext(@NotNull String context) {
      Matcher matcher = BROADCAST_PLACEHOLDERS.matcher(context);
      if (!matcher.find()) {
         return new BroadcastContext(context, List.of());
      }

      List<Placeholder> placeholders = new ArrayList<>();
      String values = matcher.group(1);
      if (!values.isEmpty()) {
         for(String value : values.split(",")) {
            int separator = value.indexOf('.');
            if (separator <= 0 || separator == value.length() - 1) {
               continue;
            }

            try {
               String name = this.decode(value.substring(0, separator));
               String replacement = this.decode(value.substring(separator + 1));
               placeholders.add(Placeholder.of(name, replacement));
            } catch (IllegalArgumentException ignored) {
            }
         }
      }

      return new BroadcastContext(context.substring(0, matcher.start()), placeholders);
   }

   private String decode(String value) {
      return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
   }

   private String resolveLanguage(@NotNull DCPlayer viewer, @NotNull String context) {
      Matcher matcher = LANGUAGE_PLACEHOLDER.matcher(context);
      StringBuffer result = new StringBuffer();

      while(matcher.find()) {
         String placeholder = "%CORE_LANG_RAW:" + matcher.group(1) + "%";
         String localized = DCAPI.getInstance().getPlatform().getPAPI().setPlaceholders(viewer, placeholder);
         matcher.appendReplacement(result, Matcher.quoteReplacement(localized));
      }

      matcher.appendTail(result);
      return result.toString();
   }

   private record BroadcastContext(String message, List<Placeholder> placeholders) {
   }
}
