package tasks;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.TaskNode;
import script.ScriptVars;

public class CraftTask extends TaskNode {

    private final int runecraftingAltarId = 29631;

    @Override
    public boolean accept() {
        boolean playerIsInArea = ScriptVars.altarArea.contains(Players.localPlayer());
        boolean hasEssence = Inventory.contains(ScriptVars.essenceId);
        return playerIsInArea && hasEssence;
    }

    @Override
    public int execute() {
        if (GameObjects.closest(runecraftingAltarId) != null) {
            GameObjects.closest(runecraftingAltarId).interact("Craft-rune");
        }
        return Calculations.random(600, 1200);
    }

}
