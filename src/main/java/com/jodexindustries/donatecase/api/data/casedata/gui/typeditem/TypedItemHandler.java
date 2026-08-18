package com.jodexindustries.donatecase.api.data.casedata.gui.typeditem;

import com.jodexindustries.donatecase.api.data.casedata.gui.CaseGui;
import com.jodexindustries.donatecase.api.data.casedata.gui.CaseGuiWrapper;
import org.jetbrains.annotations.NotNull;

public interface TypedItemHandler {
   CaseGui.@NotNull @NotNull Item handle(@NotNull CaseGuiWrapper var1, CaseGui.@NotNull Item var2) throws TypedItemException;
}
