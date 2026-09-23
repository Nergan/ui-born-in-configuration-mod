package com.uiborninconfiguration.mod.mixin;

import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/** salt() объявлен в родителе и protected, обычный @Shadow из наследника его не находит. */
@Mixin(StructurePlacement.class)
public interface StructurePlacementAccessor {
    @Invoker("salt")
    int uibic$salt();
}
