package com.Bluehat_R.hPArmorBar;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.HashMap;
import java.util.Map;

public final class HPArmorBar extends JavaPlugin implements Listener {

    private final Map<Player, Double> lastHealth = new HashMap<>();
    private final Map<Player, Double> lastArmor = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        // 🕒 定期的に常時更新（10tick = 0.5秒ごと）
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    updatePlayerBar(p, true); // ← force = true に変更！
                }
            }
        }.runTaskTimer(this, 0L, 20L); // ← 1秒ごと（軽くて安定）

        getLogger().info("HPArmorBar enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("HPArmorBar disabled!");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        updatePlayerBar(e.getPlayer(), true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p) {
            Bukkit.getScheduler().runTaskLater(this, () -> updatePlayerBar(p, true), 1L);
        }
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player p) {
            Bukkit.getScheduler().runTaskLater(this, () -> updatePlayerBar(p, true), 1L);
        }
    }

    @EventHandler
    public void onArmorChange(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player p) {
            Bukkit.getScheduler().runTaskLater(this, () -> updatePlayerBar(p, true), 2L);
        }
    }

    private void updatePlayerBar(Player p, boolean force) {
        double health = p.getHealth();
        double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double armor = p.getAttribute(Attribute.GENERIC_ARMOR).getValue();

        Double lastH = lastHealth.getOrDefault(p, -1.0);
        Double lastA = lastArmor.getOrDefault(p, -1.0);

        // 数値が変わった or 強制更新なら表示
        if (force || health != lastH || armor != lastA) {
            String healthStr = String.format("%.1f/%.0f", health, maxHealth);
            String armorStr = String.format("%.0f", armor);

            Component comp = Component.text("HP: ", NamedTextColor.RED)
                    .append(Component.text(healthStr, NamedTextColor.WHITE))
                    .append(Component.text("  ARM: ", NamedTextColor.AQUA))
                    .append(Component.text(armorStr, NamedTextColor.WHITE));

            p.sendActionBar(comp);

            lastHealth.put(p, health);
            lastArmor.put(p, armor);
        }
    }
}
