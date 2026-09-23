package com.uiborninconfiguration.mod.logic

/**
 * Мобы и группы структур Born in Chaos 1.7.6, для которых есть отдельный множитель.
 * Моб без записи всё равно подчиняется общему множителю спавна.
 * Набор структур без своей группы подчиняется общей частоте.
 */
object ChaosCatalog {
    data class NamedEntry(val id: String, val english: String, val russian: String)

    val mobs: List<NamedEntry> = listOf(
        NamedEntry("baby_skeleton", "Baby Skeleton", "Малыш-скелет"),
        NamedEntry("baby_spider", "Baby Spider", "Паучонок"),
        NamedEntry("barrel_zombie", "Barrel Zombie", "Зомби в бочке"),
        NamedEntry("bloody_gadfly", "Bloody Gadfly", "Кровавый овод"),
        NamedEntry("bonescaller", "Bonescaller", "Призыватель костей"),
        NamedEntry("bone_imp", "Bone Imp", "Костяной бес"),
        NamedEntry("corpse_fish", "Corpse Fish", "Трупная рыба"),
        NamedEntry("corpse_fly", "Corpse Fly", "Трупная муха"),
        NamedEntry("dark_vortex", "Dark Vortex", "Тёмный вихрь"),
        NamedEntry("decaying_zombie", "Decaying Zombie", "Разлагающийся зомби"),
        NamedEntry("decrepit_skeleton", "Decrepit Skeleton", "Дряхлый скелет"),
        NamedEntry("diamond_termite", "Diamond Termite", "Алмазный термит"),
        NamedEntry("dire_hound_leader", "Dire Hound Leader", "Вожак лютых гончих"),
        NamedEntry("door_knight", "Door Knight", "Дверной рыцарь"),
        NamedEntry("dread_hound", "Dread Hound", "Жуткая гончая"),
        NamedEntry("fallen_chaos_knight", "Fallen Chaos Knight", "Падший рыцарь хаоса"),
        NamedEntry("firelight", "Firelight", "Огонёк"),
        NamedEntry("glutton_fish", "Glutton Fish", "Рыба-обжора"),
        NamedEntry("krampus", "Krampus", "Крампус"),
        NamedEntry("krampus_henchman", "Krampus Henchman", "Приспешник Крампуса"),
        NamedEntry("lifestealer", "Lifestealer", "Похититель жизни"),
        NamedEntry("maggot", "Corpse Maggot", "Трупная личинка"),
        NamedEntry("missioner", "Missionary", "Миссионер"),
        NamedEntry("mother_spider", "Spiders Mother", "Мать пауков"),
        NamedEntry("mr_pumpkin", "Mr. Pumpkin", "Мистер Тыква"),
        NamedEntry("mrs_pumpkin", "Mrs Pumpkin", "Миссис Тыква"),
        NamedEntry("nightmare_stalker", "Nightmare Stalker", "Кошмарный преследователь"),
        NamedEntry("phantom_creeper", "Phantom Creeper", "Фантомный крипер"),
        NamedEntry("pumpkin_bruiser", "Pumpkin Bruiser", "Тыквенный громила"),
        NamedEntry("pumpkin_dunce", "Pumpkin Dunce", "Тыквенный болван"),
        NamedEntry("pumpkinhead", "Pumpkinhead", "Тыквенная голова"),
        NamedEntry("restless_spirit", "Restless Spirit", "Беспокойный дух"),
        NamedEntry("scarlet_persecutor", "Scarlet Persecutor", "Алый преследователь"),
        NamedEntry("seared_spirit", "Seared Spirit", "Опалённый дух"),
        NamedEntry("senor_pumpkin", "Senor Pumpkin", "Сеньор Тыква"),
        NamedEntry("siamese_skeletons", "Siamese Skeletons", "Сиамские скелеты"),
        NamedEntry("sir_pumpkinhead", "Sir Pumpkinhead", "Сэр Тыквенная голова"),
        NamedEntry("skeleton_demoman", "Skeleton Demoman", "Скелет-подрывник"),
        NamedEntry("skeleton_thrasher", "Skeleton Thrasher", "Скелет-молотильщик"),
        NamedEntry("spirit_guide", "Spirit Guide", "Дух-проводник"),
        NamedEntry("spiritof_chaos", "Spirit of Chaos", "Дух хаоса"),
        NamedEntry("supreme_bonescaller", "Supreme Bonescaller", "Верховный призыватель костей"),
        NamedEntry("swarmer", "Swarmer", "Роевик"),
        NamedEntry("thornshell_crab", "Thornshell Crab", "Шипастый краб"),
        NamedEntry("zombie_bruiser", "Zombie Bruiser", "Зомби-громила"),
        NamedEntry("zombie_clown", "Zombie Clown", "Зомби-клоун"),
        NamedEntry("zombie_fisherman", "Zombie Fisherman", "Зомби-рыбак"),
        NamedEntry("zombie_lumberjack", "Zombie Lumberjack", "Зомби-лесоруб"),
    )

    val structureGroups: List<NamedEntry> = listOf(
        NamedEntry("graves", "Graves", "Могилы"),
        NamedEntry("clown_caravan", "Clown caravan", "Караван клоунов"),
        NamedEntry("dark_tower", "Dark tower", "Тёмная башня"),
        NamedEntry("observation_tower", "Observation tower", "Сторожевая башня"),
        NamedEntry("farm", "Farm", "Ферма"),
        NamedEntry("firewell", "Firewell", "Огненный колодец"),
        NamedEntry("infernal_pumpkin", "Infernal pumpkin", "Инфернальная тыква"),
        NamedEntry("mound_of_hounds", "Mound of hounds", "Курган гончих"),
    )
}
