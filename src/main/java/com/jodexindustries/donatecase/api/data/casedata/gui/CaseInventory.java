package com.jodexindustries.donatecase.api.data.casedata.gui;

import org.jetbrains.annotations.Nullable;

public interface CaseInventory {
   Object getInventory();

   void setItem(int var1, @Nullable Object var2);
}
