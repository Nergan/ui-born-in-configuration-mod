# UI for Born in Configuration

**[English](README.md)** · **[Русский](README.ru.md)**

A **Minecraft 1.21.1** NeoForge addon for [Born in Chaos](https://modrinth.com/mod/borninchaos) and [Born in Configuration](https://modrinth.com/mod/born-in-configuration). It opens one in-game settings menu from the Config button of all three mods, and adds spawn and structure rates on top of the Born in Configuration options. Written in Kotlin with [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge).

The menu follows the client language (English and Russian).

## Downloads

Jars live on [GitHub Releases](https://github.com/Nergan/ui-born-in-configuration-mod/releases/latest). A push to `main` updates the files on the current version’s release and publishes the addon to [Modrinth](https://modrinth.com/project/ui-born-in-configuration).

Download these files and put them in the `mods` folder:

| File | Required | What it is |
| --- | --- | --- |
| `uiborninconfiguration-1.0.0.jar` | Yes | this addon |
| `kotlinforforge-5.8.0-all.jar` | Yes | [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge) |
| `born_in_chaos_[Neoforge]_1.21.1_1.7.6.jar` | Yes | [Born in Chaos](https://modrinth.com/mod/borninchaos) |
| `borninconfiguration-3.2.2.jar` | Yes | [Born in Configuration](https://modrinth.com/mod/born-in-configuration) |
| `geckolib-neoforge-1.21.1-4.7.6.jar` | Yes | [GeckoLib](https://modrinth.com/mod/geckolib), required by Born in Chaos |

The release workflow builds the addon and fetches the companion jars from Modrinth. GitHub shows a SHA-256 digest next to each file on the release page. Do not install `*-sources.jar`.

## Features

- **One Config button.** Mods → UI for Born in Configuration, Born in Configuration, or Born in Chaos → Config. All three open the same menu.
- **Born in Configuration.** Health, damage, armor, speeds, spawn toggles, weapons and the general switches from that mod, with readable names instead of raw keys.
- **Spawn rates.** A global multiplier and one slider per Born in Chaos mob. `1` leaves the mod alone, `2` about doubles natural, chunk, patrol and event spawns, `0` stops those. Spawn eggs, commands, spawners and mobs placed by structures are not scaled. A per-mob value is multiplied by the global one and clamped to `0..8`.
- **Structure frequency.** Same idea for new chunks: graves (including `gravecarrionexe`), clown caravans, dark towers, observation towers, farms, firewells, infernal pumpkins and mounds of hounds. `1` keeps the datapack spacing. Already generated chunks stay put; a new spacing only affects chunks generated after the change. A structure set added by a future Born in Chaos version still follows the global frequency.
- **No fight with a future Born in Chaos menu.** If that mod later registers its own config screen, it is kept and shown as an extra button (`Born in Chaos: own menu`). If it later registers NeoForge config files, those open from a `Born in Chaos` button on the same screen. Neither replaces the other.

## Requirements

| Component | Version |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.209 (any 21.1.x should work) |
| Kotlin for Forge | 5.8.0, **NeoForge** build |
| Born in Chaos | 1.7.6 (NeoForge 1.21.1) |
| Born in Configuration | 3.2.2 |
| GeckoLib | 4.7.6 (NeoForge 1.21.1) |
| Java | 21 |

## Installation

1. Install NeoForge 1.21.1.
2. Download the jars from [the latest Release](https://github.com/Nergan/ui-born-in-configuration-mod/releases/latest).
3. Put this addon, Kotlin for Forge, Born in Chaos, Born in Configuration and GeckoLib in `mods`.

The addon is required on both client and server. You can also get the companion mods from Modrinth instead of the GitHub release.

## Configuration

In-game: Mods → any of the three mods → Config.

On the title screen the server file is not loaded yet, so server options stay disabled until you are in a world. On a remote server NeoForge shows them read-only; edit the file on the server.

World file: `saves/<world>/serverconfig/uiborninconfiguration-server.toml`.

Dedicated server: `world/serverconfig/uiborninconfiguration-server.toml`. This is a `SERVER` config: the server owns the values and syncs them to clients. Born in Configuration keeps its own server file; this menu edits both.

| Option | Default | Meaning |
| --- | --- | --- |
| `spawn_multiplier` | `1.0` | Global Born in Chaos spawn rate (`0`..`8`) |
| `spawning.mobs.<id>` | `1.0` | Extra rate for one mob |
| `structure_frequency` | `1.0` | Global structure rate for new chunks (`0`..`8`) |
| `structures.groups.<id>` | `1.0` | Extra rate for one structure group |

## License

The addon code is [MIT](LICENSE). Born in Chaos is All Rights Reserved. Born in Configuration and GeckoLib are MIT. Kotlin for Forge is LGPL-2.1. Vanilla Minecraft assets are not shipped.
