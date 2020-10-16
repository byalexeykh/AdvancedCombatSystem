# AdvancedCombatSystem
Minecraft combat system rework
https://www.curseforge.com/minecraft/mc-mods/advanced-combat-system

# Configs
Config files are located in *config/advancedcombatsystem/* and have the *.json* extension. Files with this extension can be changed using standard Windows tools (for example Notepad)

**Dedicated server configs override client configs.**

## How to change configs correctly:

### advancedcombatsystem-defaults:
This config contains attributes for each item type and for a hand. *(Applies to items from vanilla and other mods that are not natively supported)*
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
![picture 1](https://raw.githubusercontent.com/byalexeykh/AdvancedCombatSystem/master/readmeimages/readme_angle.png)

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

### advancedcombatsystem-attributesbyid:
This config allows to manually add attributes to items. **Attention! Adding a large number of items to this config can result in performance degradation! This config is a temporary solution.**
```
[
  {
    "modid": "example_modid",
    "name": "example_name",
    "ANGLE": 50.0,
    "RANGE": 7.0,
    "NEEDED_BACKSWING_TICKS": 16.0,
    "MIN_BACKSWING_TICKS": 5.0,
    "MAX_COMBO_NUM": 4,
    "SPEED_REDUCTION_MODIFIER": -0.03,
    "COMBO_CHARGING_SPEED_BOUNS": 0.4
  }
]
```
Attribute name | Description
------------ | -------------
modid | The "modid" to which the item belongs. For example, if it's a vanilla item, then "modid" will be "minecraft", if it's an item from the "Vulcanite" mod, then "vulcanite"
name | Item name in the registry. For Wooden Sword from Vanilla it will be "wooden_sword"
ANGLE | The angle is specified in degrees and is calculated as shown in picture 1.
NEEDED_BACKSWING_TICKS | The required number of ticks that are needed to accumulate energy to make a hit with maximum damage. If the value is "0" (zero), then default item cooldown will be used (For example if you dont want to override default item cooldown).
MIN_BACKSWING_TICKS | The minimum number of ticks required to hit. If the value "NEEDED_BACKSWING_TICKS" is "0" (zero), then it will be calculated as a percentage of the default item cooldown.
MAX_COMBO_NUM | The maximum number of combos, after exaggerating which the player will be forced to wait for cooldown.
SPEED_REDUCTION_MODIFIER | Value corresponding for change of player speed when player accumulates power too long. Positive values speed up the player, negative values slow down. The standard speed value is 1
COMBO_CHARGING_SPEED_BOUNS | The value that increases the rate of power accumulation if combo stage is passed. Negative values will slow down rate of power accumulation.

In order to add a new item to the list, you need to copy the example item from "{" to "}", paste it below and put a comma after the penultimate item. The config with two items will look like this:
```
[
  {
    "modid": "example_modid",
    "name": "example_name",
    "ANGLE": 50.0,
    "RANGE": 7.0,
    "NEEDED_BACKSWING_TICKS": 16.0,
    "MIN_BACKSWING_TICKS": 5.0,
    "MAX_COMBO_NUM": 4,
    "SPEED_REDUCTION_MODIFIER": -0.03,
    "COMBO_CHARGING_SPEED_BOUNS": 0.4
  },
  {
    "modid": "example_modid2",
    "name": "example_name2",
    "ANGLE": 50.0,
    "RANGE": 7.0,
    "NEEDED_BACKSWING_TICKS": 16.0,
    "MIN_BACKSWING_TICKS": 5.0,
    "MAX_COMBO_NUM": 4,
    "SPEED_REDUCTION_MODIFIER": -0.03,
    "COMBO_CHARGING_SPEED_BOUNS": 0.4
  }
]
```
