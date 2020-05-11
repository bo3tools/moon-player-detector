# moon-player-detector

## How it detects players

* Get all players which are further than `min` and closer than `max` blocks away from the player
  * *The `min` range is required to avoid counting players that are too close*

* From these players, find the closest player.

* From the closest player, count all players that are nearby

* These players are now detected

## Configuration

Plugin configuration can be changed in the `PlayerDetector/config.yml` file.

* `direction.*`
  * Used to stylize and localize cardinal directions.
  * Value: Plain text

* `basic/super-scouting-device.item.material`
  * Set the material used by the scouting device item
  * Value: Namespaced material (`minecraft:item`)

* `basic/super-scouting-device.item.name`
  * Set the display name used by the scouting device item
  * Value: Colored string (`&1Hello, §2world`, can use `&` as well as `§` for color codes).

* `basic/super-scouting-device.item.lore`
  * Set the lore used by the scouting device item
  * Value: Multiline colored string (`&1Hello,\n§2world`, can use `&` as well as `§` for color codes, `\n` for newline)

* `basic/super-scouting-device.item.model`
  * Set the CustomItemModel used by the scouting device item, used to change the texture or model in datapack
  * Value: Number

* `basic/super-scouting-device.message.notify-revealed`
  * Set the "revealed" message used by the scouting device item to inform players that were detected.
  * Value: Colored string

* `basic/super-scouting-device.message.nobody-detected`
  * Set the "nobody detected" message used by the scouting device item to inform that no players were found.
  * Value: Colored string

* `basic/super-scouting-device.message.people-detected`
  * Set the "people detected" message used by the scouting device item to inform that players were detected in range.
  * Format: %d (amount of people detected), %s (cardinal direction)
  * Value: Formatted colored string

* `basic/super-scouting-device.max-radius`
  * Set the maximum radius of the scouting device detection. 
  * Value: Double

* `basic/super-scouting-device.min-radius`
  * Set the minimum radius of the scouting device detection.
  * Note: People that are too close (<= min) will not be included in the detection to avoid false positives.
  * Value: Double

* `basic/super-scouting-device.nearby-radius`
  * Radius to look up additional players nearby the closest detected player
  * Value: Double

* `super-scouting-device.max-uses` (super only)
  * Maximum uses of the Super Scouting Device, after which it is consumed
  * Value: Integer

* `super-scouting-device.uses-suffix` (super only)
  * Text to format the uses remaining with
  * Format: %d (uses left), %d (max uses)
  * Value: Formatted colored string

## Commands

* `/detector give <basic|super>`
  * Permission: `detector.give`
  * Adds a basic or a super scouting device to your inventory