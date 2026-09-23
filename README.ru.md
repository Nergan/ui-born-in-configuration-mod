# UI for Born in Configuration

**[English](README.md)** · **[Русский](README.ru.md)**

Аддон для **Minecraft 1.21.1** (NeoForge) к модам [Born in Chaos](https://modrinth.com/mod/borninchaos) и [Born in Configuration](https://modrinth.com/mod/born-in-configuration). Одна кнопка Config у всех трёх модов открывает одно меню настроек, а к параметрам Born in Configuration добавляются частота спавна и структур. Написан на Kotlin через [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge).

Язык меню берётся из клиента (английский и русский).

## Загрузки

Готовые jar лежат в [GitHub Releases](https://github.com/Nergan/ui-born-in-configuration-mod/releases/latest). Пуш в `main` обновляет файлы текущего релиза и публикует аддон на [Modrinth](https://modrinth.com/project/ui-born-in-configuration).

Скачайте эти файлы и положите в папку `mods`:

| Файл | Обязателен | Что это |
| --- | --- | --- |
| `uiborninconfiguration-1.0.0.jar` | Да | этот аддон |
| `kotlinforforge-5.8.0-all.jar` | Да | [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge) |
| `born_in_chaos_[Neoforge]_1.21.1_1.7.6.jar` | Да | [Born in Chaos](https://modrinth.com/mod/borninchaos) |
| `borninconfiguration-3.2.2.jar` | Да | [Born in Configuration](https://modrinth.com/mod/born-in-configuration) |
| `geckolib-neoforge-1.21.1-4.7.6.jar` | Да | [GeckoLib](https://modrinth.com/mod/geckolib), нужен Born in Chaos |

Workflow релиза собирает аддон и забирает чужие jar с Modrinth. SHA-256 у каждого файла GitHub считает сам и показывает рядом с ним на странице релиза. Файл `*-sources.jar` в `mods` класть не нужно.

## Возможности

- **Одна кнопка Config.** Mods → UI for Born in Configuration, Born in Configuration или Born in Chaos → Config. Все три открывают одно меню.
- **Born in Configuration.** Здоровье, урон, броня, скорости, флаги спавна, оружие и общие переключатели того мода, с нормальными названиями вместо сырых ключей.
- **Частота спавна.** Общий множитель и отдельный ползунок на моба Born in Chaos. `1` ничего не меняет, `2` примерно удваивает естественный спавн, спавн при генерации чанка, патрули и события, `0` их выключает. Яйца призыва, команды, спавнеры и мобы, прописанные в структуре, не масштабируются. Множитель моба перемножается с общим и зажимается в `0..8`.
- **Частота структур.** То же для новых чанков: могилы (включая `gravecarrionexe`), караваны клоунов, тёмные башни, сторожевые башни, фермы, огненные колодцы, инфернальные тыквы и курганы гончих. `1` оставляет spacing датапака. Уже сгенерированные чанки остаются на месте; новая сетка действует только на чанки, сгенерированные после изменения. Набор структур из будущей версии Born in Chaos всё равно слушает общую частоту.
- **Без конфликта с будущим меню Born in Chaos.** Если тот мод позже зарегистрирует свой экран настроек, он сохраняется и показывается отдельной кнопкой (`Born in Chaos: своё меню`). Если он зарегистрирует файлы конфига NeoForge, они откроются кнопкой `Born in Chaos` на том же экране. Одно другое не заменяет.

## Требования

| Компонент | Версия |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.209 (подойдёт линейка 21.1.x) |
| Kotlin for Forge | 5.8.0, сборка **NeoForge** |
| Born in Chaos | 1.7.6 (NeoForge 1.21.1) |
| Born in Configuration | 3.2.2 |
| GeckoLib | 4.7.6 (NeoForge 1.21.1) |
| Java | 21 |

## Установка

1. Установите NeoForge 1.21.1.
2. Скачайте jar из [последнего Release](https://github.com/Nergan/ui-born-in-configuration-mod/releases/latest).
3. Положите в `mods` этот аддон, Kotlin for Forge, Born in Chaos, Born in Configuration и GeckoLib.

Аддон нужен и на клиенте, и на сервере. Зависимости можно взять и с Modrinth, не из GitHub Release.

## Настройки

В игре: Mods → любой из трёх модов → Config.

На титульном экране серверный файл ещё не загружен, поэтому серверные пункты неактивны, пока вы не в мире. На чужом сервере NeoForge показывает их только для чтения; править файл нужно на сервере.

Файл мира: `saves/<мир>/serverconfig/uiborninconfiguration-server.toml`.

На выделенном сервере: `world/serverconfig/uiborninconfiguration-server.toml`. Конфиг типа `SERVER`: значения задаёт сервер и рассылает игрокам. У Born in Configuration остаётся свой серверный файл; это меню правит оба.

| Параметр | По умолчанию | Смысл |
| --- | --- | --- |
| `spawn_multiplier` | `1.0` | Общая частота спавна Born in Chaos (`0`..`8`) |
| `spawning.mobs.<id>` | `1.0` | Дополнительная частота одного моба |
| `structure_frequency` | `1.0` | Общая частота структур в новых чанках (`0`..`8`) |
| `structures.groups.<id>` | `1.0` | Дополнительная частота одной группы структур |

## Лицензия

Код аддона — [MIT](LICENSE). Born in Chaos распространяется на условиях All Rights Reserved. Born in Configuration и GeckoLib — MIT. Kotlin for Forge — LGPL-2.1. Ванильные ассеты Minecraft в дистрибутив не входят.
