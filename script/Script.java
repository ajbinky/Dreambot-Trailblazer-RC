package script;

import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import tasks.BankTask;
import tasks.CraftTask;
import tasks.TeleToAltarTask;

import java.awt.*;
import java.util.concurrent.TimeUnit;

@ScriptManifest(author = "AJBinky", category = Category.RUNECRAFTING, name = "Leagues RC", version = 0.01)
public class Script extends TaskScript {



    @Override
    public void onStart() {
        addNodes(new BankTask(), new TeleToAltarTask(), new CraftTask());
        SkillTracker.start();
    }

    @Override
    public void onPaint(Graphics graphics) {
        long ttl = SkillTracker.getTimeToLevel(Skill.RUNECRAFTING);

        graphics.drawString("Level " + Skills.getRealLevel(Skill.RUNECRAFTING) + " (+" + SkillTracker.getGainedLevels(Skill.RUNECRAFTING) + ")", 10, 50);
        graphics.drawString(SkillTracker.getGainedExperience(Skill.RUNECRAFTING) + " xp", 10, 65);
        graphics.drawString(SkillTracker.getGainedExperiencePerHour(Skill.RUNECRAFTING) + " xp/hr", 10, 80);
        graphics.drawString("Time to level: " + formatTime(ttl), 10, 95);
    }

    private String formatTime(long ms) {
        return String.format(
                "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(ms),
                TimeUnit.MILLISECONDS.toMinutes(ms) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(ms) % TimeUnit.MINUTES.toSeconds(1)
        );
    }
}
