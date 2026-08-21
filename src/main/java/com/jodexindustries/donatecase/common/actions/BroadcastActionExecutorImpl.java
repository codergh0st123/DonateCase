package com.jodexindustries.donatecase.common.actions;

import com.jodexindustries.donatecase.api.DCAPI;
import com.jodexindustries.donatecase.api.data.action.ActionExecutor;
import com.jodexindustries.donatecase.api.platform.DCPlayer;
import com.jodexindustries.donatecase.api.tools.DCTools;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BroadcastActionExecutorImpl implements ActionExecutor {
   private static final Pattern LANGUAGE_PLACEHOLDER = Pattern.compile("%CORE_LANG:([^%]+)%", Pattern.CASE_INSENSITIVE);

   public void execute(@Nullable DCPlayer player, @NotNull String context) {
      for(DCPlayer viewer : DCAPI.getInstance().getPlatform().getOnlinePlayers()) {
         viewer.sendMessage(this.resolveMessage(player, viewer, context));
      }
   }

   private String resolveMessage(@Nullable DCPlayer source, @NotNull DCPlayer viewer, @NotNull String context) {
      String localized = this.resolveLanguage(viewer, context);
      DCPlayer placeholderPlayer = source == null ? viewer : source;
      return DCTools.rc(DCAPI.getInstance().getPlatform().getPAPI().setPlaceholders(placeholderPlayer, localized));
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
}
