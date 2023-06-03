# OneKeyMiner

A minecraft mod for Fabric that allows you to mine blocks with one mining action.

## Usage

### Commands

- `/onekeyminer screen` - Displays the mod's screen.(Only works in singleplayer and on LAN servers and only for the
  player who open the LAN server)
- `/onekeyminer limit` - Displays the current limit.
- `/onekeyminer limit <limit>` - Max amount of blocks that can be mined with one action. (Slightly deviates from the
  actual limit so that the actual limit is always greater than or equal to the specified limit; slightly below the limit
  also possible.)
- `/onekeyminer <AXE|HOE|PICKAXE|SHOVEL|SHEARS> list` - Lists all blocks that are currently in the tool's list.
- `/onekeyminer <AXE|HOE|PICKAXE|SHOVEL|SHEARS> <ADD|REMOVE> <block>` - Adds or removes a block from the tool's list.