package com.uiborninconfiguration.mod.event

import com.uiborninconfiguration.mod.world.StructurePlacementIndex
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.TagsUpdatedEvent
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent

/** Пересобирает индекс структур, когда сервер загрузил датапаки. */
object StructureIndexEvents {
    @SubscribeEvent
    fun onServerAboutToStart(event: ServerAboutToStartEvent) {
        StructurePlacementIndex.rebuild(event.server.registryAccess())
    }

    @SubscribeEvent
    fun onTagsUpdated(event: TagsUpdatedEvent) {
        if (event.updateCause != TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD) return
        StructurePlacementIndex.rebuild(event.registryAccess)
    }
}
