package com.jodexindustries.donatecase.api.data.casedata.gui.typeditem;

import com.jodexindustries.donatecase.api.event.player.GuiClickEvent;
import org.jetbrains.annotations.NotNull;

public interface TypedItemClickHandler {
   void onClick(@NotNull GuiClickEvent var1) throws TypedItemException;
}
