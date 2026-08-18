package com.jodexindustries.donatecase.common.tools;

import com.jodexindustries.donatecase.api.data.casedata.CaseData;
import com.jodexindustries.donatecase.api.data.casedata.CaseDataItem;
import com.jodexindustries.donatecase.api.tools.DCTools;
import com.jodexindustries.donatecase.api.tools.Placeholder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LocalPlaceholder extends Placeholder {
   private LocalPlaceholder(String name, String value) {
      super(name, value);
   }

   public static LocalPlaceholder of(String name, Object value) {
      return new LocalPlaceholder(name, String.valueOf(value));
   }

   public static Set<LocalPlaceholder> of(CaseData caseData) {
      return new HashSet<>(Arrays.asList(of("%casetype%", caseData.caseType()), of("%casename%", caseData.caseType()), of("%casedisplayname%", caseData.caseDisplayName()), of("%casetitle%", caseData.caseGui().title()), of("%animation%", caseData.animation())));
   }

   public static Set<LocalPlaceholder> of(CaseDataItem item) {
      return new HashSet<>(Arrays.asList(of("%group%", item.group()), of("%groupdisplayname%", item.material().displayName())));
   }

   public static Set<LocalPlaceholder> of(CaseData.History data) {
      String time = DCTools.getDateFormat().format(new Date(data.time()));
      String group = data.group();
      String action = data.action() != null ? data.action() : group;
      return new HashSet<>(Arrays.asList(of("%group%", group), of("%action%", action), of("%player%", data.playerName()), of("%casetype%", data.caseType()), of("%casename%", data.caseType()), of("%time%", time)));
   }

   public boolean equals(Object object) {
      if (object != null && this.getClass() == object.getClass()) {
         LocalPlaceholder that = (LocalPlaceholder)object;
         return Objects.equals(this.name(), that.name());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.name());
   }
}
