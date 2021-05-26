package tasks;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.TaskNode;
import script.ScriptVars;

public class TeleToAltarTask extends TaskNode {


    @Override
    public boolean accept() {
        boolean bankIsOpen = Bank.isOpen();
        boolean inventoryIsFull = Inventory.isFull();
        boolean hasEssence = Inventory.contains(ScriptVars.essenceId);
        boolean isAnimating = Players.localPlayer().isAnimating();
        boolean isNearAltar = ScriptVars.altarArea.contains(Players.localPlayer());
        return !bankIsOpen && inventoryIsFull && hasEssence && !isAnimating && !isNearAltar;
    }

    @Override
    public int execute() {
        if (Inventory.get(ScriptVars.crystalOfMemoriesId) != null) {
            Inventory.get(ScriptVars.crystalOfMemoriesId).interact("Teleport-back");
        } else {
            return -1;
        }
        return Calculations.random(1200, 2400);
    }
}
