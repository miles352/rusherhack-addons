package org.jefffmod;

import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Items;
import org.rusherhack.client.api.events.client.EventUpdate;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.client.api.utils.InventoryUtils;
import org.rusherhack.core.event.subscribe.Subscribe;
import org.rusherhack.core.setting.NumberSetting;
import org.rusherhack.core.utils.Timer;

import java.util.ArrayList;

public class MapCopy extends ToggleableModule
{

    private final NumberSetting<Integer> copyAmount = new NumberSetting<>("Copy Amount", 4, 2, 64);
    private final NumberSetting<Integer> delay = new NumberSetting<>("Delay in MS", 100, 0, 1000);

    private Timer timer = new Timer();
    private int currMapSlot = 0;
    private ArrayList<Integer> mapsToCopy = new ArrayList<Integer>();

    public MapCopy() {
        super("MapCopy", "MapCopy", ModuleCategory.MISC);
        this.registerSettings(
            this.copyAmount,
            this.delay
        );
    }

    @Subscribe
    private void onUpdate(EventUpdate event)
    {

        if (mc.screen instanceof InventoryScreen && timer.passed(delay.getValue()))
        {
            if (mapsToCopy.isEmpty())
            {
                for (int i = 9; i < 45; i++)
                {
                    Slot slot = mc.player.inventoryMenu.slots.get(i);
                    if (!slot.getItem().is(Items.FILLED_MAP)) continue;
                    if (slot.getItem().getCount() >= copyAmount.getValue()) continue;
                    mapsToCopy.add(i);
                }
                if (mapsToCopy.isEmpty()) return;
                currMapSlot = mapsToCopy.get(0);
            }


            boolean mapsInCrafting = false;
            for (int j = 1; j < 5; j++)
            {
                if (mc.player.inventoryMenu.slots.get(j).getItem().is(Items.MAP))
                {
                    mapsInCrafting = true;
                    break;
                }
            }
            int emptyMapSlot = InventoryUtils.findItem(Items.MAP, false, false);
            if (!mapsInCrafting && emptyMapSlot == -1)
            {
                ChatUtils.print("Out of empty maps!");
                return;
            }
            else if (!mapsInCrafting)
            {
                InventoryUtils.clickSlot(emptyMapSlot, false);
                InventoryUtils.clickSlot(1, false);
            }

            InventoryUtils.clickSlot(currMapSlot, false);
            InventoryUtils.clickSlot(2, false);
            InventoryUtils.clickSlot(0, true);

            if (currMapSlot < mapsToCopy.size() - 1)
            {
                currMapSlot++;
            }
            else
            {
                mapsToCopy = new ArrayList<Integer>();
            }
            timer.reset();
        }
    }

}
