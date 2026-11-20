# Auto Double Hand - Pop Switch (Minecraft Java 1.21.11)

Fabric **client-side** mod project. **The ZIP is SOURCE CODE, not a built Minecraft mod.**

## Behavior

- When **your** Totem of Undying activates (in the main hand OR offhand), the mod scans the 9 hotbar slots, beginning immediately after the currently selected slot, wrapping around to the start.
- It **selects the next slot that already contains a Totem of Undying** so it is held in the MAIN hand.
- It **never moves any item to the offhand** and does not restock the offhand.
- If no totem is found, the current slot remains selected.
- Other players' totem pops are ignored.

## Build without a terminal (GitHub Actions)

1. Unzip the project, including the hidden `.github` folder.
2. Create a GitHub repository and upload the project contents into the repository root. Be sure the **hidden `.github/workflows/build.yml` file** is present (Finder may hide it; press Cmd+Shift+Period to show hidden files).
3. Select the **Actions** tab, select **Build Fabric JAR**, and click **Run workflow** if the build did not run on push.
4. If the action succeeds, open the run, download the **AutoDoubleHand-PopSwitch-1.21.11-JAR** artifact, unzip that artifact and choose the `.jar` **without** `-dev` or `-sources` in its name.
5. Put this jar in the Fabric Minecraft **1.21.11** mods folder.

## Local build

Install Java 21 and compatible Gradle, and run `gradle build`.

## Notes

The code and workflow have not been compiled or tested in this environment (build dependencies are inaccessible here). The compilation might require changes if the upstream game API differs. Only use mods on servers that permit automation.
