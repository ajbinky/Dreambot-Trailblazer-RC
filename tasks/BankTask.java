package tasks;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankType;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.TaskNode;
import script.ScriptVars;

public class BankTask extends TaskNode {

    private final Tile nextToLadderTile = new Tile(2465, 3495);
    private final Tile bankTile = new Tile(2449, 3482, 1);

    @Override
    public boolean accept() {
        boolean bankIsOpen = Bank.isOpen();
        boolean hasEssence = Inventory.contains(ScriptVars.essenceId);
        return (bankIsOpen || !hasEssence);
    }

    @Override
    public int execute() {

        if (Bank.isOpen() && Inventory.contains(ScriptVars.essenceId)) {
            Bank.close();
            return Calculations.random(600, 1200);
        }

        if (Bank.isOpen() && Inventory.contains(item -> item.getID() != ScriptVars.rspId && item.getID() != ScriptVars.crystalOfMemoriesId)) {
            Bank.depositAllExcept(ScriptVars.rspId, ScriptVars.crystalOfMemoriesId);
            return Calculations.random(600, 1200);
        }

        if (Bank.isOpen() && Inventory.onlyContains(ScriptVars.crystalOfMemoriesId, ScriptVars.rspId)) {
            Bank.withdrawAll(ScriptVars.essenceId);
            return Calculations.random(600, 1200);
        }

        if (ScriptVars.altarArea.contains(Players.localPlayer()) && !Players.localPlayer().isAnimating()) {
            Inventory.get(ScriptVars.rspId).interact("Commune");
            return Calculations.random(1200, 2400);
        }

        if (Players.localPlayer().getTile().equals(nextToLadderTile) && !Players.localPlayer().isAnimating()) {
            GameObjects.closest(16683).interact("Climb-up");
            return Calculations.random(1200, 2400);
        }

        if (!Bank.isOpen() && Bank.getClosestBank(BankType.BOOTH) != null && Bank.getClosestBank(BankType.BOOTH).distance(Players.localPlayer()) > 3) {
            if (Walking.shouldWalk()) {
                Walking.walk(bankTile);
            }
            return Calculations.random(600, 1200);
        }

        if (!Bank.isOpen() && Bank.getClosestBank(BankType.BOOTH) != null && Bank.getClosestBank(BankType.BOOTH).distance(Players.localPlayer()) <= 3) {
            if (!Players.localPlayer().isMoving()) {
                Bank.getClosestBank(BankType.BOOTH).interact("Bank");
                return Calculations.random(600, 1200);
            }
            return Calculations.random(600, 1200);
        }

        return Calculations.random(600, 1200);
    }

}
