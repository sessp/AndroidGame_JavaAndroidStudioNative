# AndroidGame
A game implemented on Android using Java and Android studio.
Project was created during university studies, Mobile Application Development (COMP2008) and was enhanced outside of university.

This program was tested on a Nexus 6 API 30 using AndroidStudio.

### Compiling and Running:
1. Launch/import project into AndroidStudio.
2. Make sure the "app" configuration is selected.
3. Press the Run button.

### Game Information/Gameplay.
The player must navigate a set of areas, where each area can be either a Town or a wilderness area.

Items are present in the game and can either be equipment or food.

The player has cash and health of 100, and can carry a certain number of equipment.

The goal for the player, you, is to collect specific items. These items are the Jade Monkey, the Roadmap and the Ice Scraper, which in combination will cause the player to win.

When the player moves from area to area, they lose health. When the player is dead, the game is over. Food can increase or decrease your health depending on what you eat, e.g eating a poisonous apple.

If the current area is a town, the “Options” button will start the market activity, showing all the items that might be bought or sold.

If the current area is not a town, the options button will instead start the wilderness
activity. This will actually work much like the market, except that there is no cash involved. That is, the player can simply pick up any items present, and/or drop any from
their inventory.

The overview button displays a graphical view of the whole map grid.

Also on the map are special items. 
###### Special items:
Portable Smell-O-Scope. (5kg) When used, this displays (in a separate, newly-started activity) a list of all items up to two grid squares away from the player’s current location.

Improbability Drive. (−πkg) When used, causes the entire map (all areas and the items in each area) to be randomly re-generated, except that the player keeps their current items, and their current health, cash and grid location.

Ben Kenobi. (0kg) When used in a town, you immediately acquire all items in the market without paying for them. The same happens in wilderness areas, even though this isn’t terribly useful.
