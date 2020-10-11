# AdvancedCombatSystem
Minecraft combat system rework
https://www.curseforge.com/minecraft/mc-mods/advanced-combat-system

# Configs
Config files are located in *config/advancedcombatsystem/* and have the *.json* extension. Files with this extension can be changed using standard Windows tools (for example Notepad)

## How to change configs correctly:

### advancedcombatsystem-defaults:
This config contains attributes for each item type and for a hand. *(Applies to items from vanilla and other mods)*
```
{
    "Type": "PICKAXE",
    "Angle": 30.0,
    "Range": 5.0,
    "Min_backswing_ticks_in_percents": 25.0,
    "Max_combo_num": 2,
    "Speed_reduction_modifier": -0.025,
    "Combo_charge_speed_bonus": 0.2
}
```
Attribute name | Description
------------ | -------------
Type | The type of items to which these settings will apply. ***Don't change it! Otherwise, errors will occur in the work of the mod.***
Angle | The angle is specified in degrees and is calculated as shown in picture 1.
Range | The distance within which creatures take damage, leaves breaks, etc.
Min_backswing_ticks_in_percents | The minimum number of ticks required to make a hit. Calculated based on vanilla cooldowns.
Max_combo_num | The maximum number of combos, after exaggerating which the player will be forced to wait for cooldown.
Speed_reduction_modifier | Value corresponding for change of player speed when player accumulates power too long. Positive values speed up the player, negative values slow down. The standard speed value is 1
Combo_charge_speed_bonus | The value that increases the rate of power accumulation if combo stage is passed. Negative values will slow down rate of power accumulation.

*picture 1:*

### advancedcombatsystem-common:
This config contains general mod settings.
```
{
  "reset_Configs_To_Default": false,
  "draw_Extended_Tooltip": true
}
```

Setting name | Description
------------ | -------------
reset_Configs_To_Default | Reset all configs to default. Configs will be reset when the game is started with the value "true" of this parameter.
draw_Extended_Tooltip | Display the attributes added by this mod to an item when hovering over it in the inventory.
