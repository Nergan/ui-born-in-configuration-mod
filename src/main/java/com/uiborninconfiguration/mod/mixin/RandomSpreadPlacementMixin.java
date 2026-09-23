package com.uiborninconfiguration.mod.mixin;

import com.uiborninconfiguration.mod.ChaosIds;
import com.uiborninconfiguration.mod.logic.StructureSpacing;
import com.uiborninconfiguration.mod.world.StructurePlacementIndex;
import com.uiborninconfiguration.mod.world.StructureRates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Частота 1.0 не трогает ванильный расчёт. Иначе тот же алгоритм считается
 * с другим spacing, так что уже стоящие структуры в старых чанках не двигаются,
 * а новые чанки получают другую сетку.
 */
@Mixin(RandomSpreadStructurePlacement.class)
public abstract class RandomSpreadPlacementMixin {
    @Shadow
    public abstract int spacing();

    @Shadow
    public abstract int separation();

    @Shadow
    public abstract int salt();

    @Shadow
    public abstract RandomSpreadType spreadType();

    @Inject(method = "isPlacementChunk", at = @At("HEAD"), cancellable = true)
    private void uibic$scale(ChunkGeneratorStructureState state, int chunkX, int chunkZ, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation id = StructurePlacementIndex.idOf((StructurePlacement) (Object) this);
        if (id == null || !ChaosIds.NAMESPACE.equals(id.getNamespace())) {
            return;
        }
        double frequency = StructureRates.frequency(id.getPath());
        if (frequency == 1.0D) {
            return;
        }
        if (frequency <= 0.0D) {
            cir.setReturnValue(false);
            return;
        }
        int spacing = spacing();
        int separation = separation();
        int scaled = StructureSpacing.INSTANCE.scaled(spacing, separation, frequency);
        if (scaled == spacing) {
            return;
        }
        ChunkPos pos = uibic$potential(state.getLevelSeed(), chunkX, chunkZ, scaled, separation);
        cir.setReturnValue(pos.x == chunkX && pos.z == chunkZ);
    }

    @Unique
    private ChunkPos uibic$potential(long seed, int chunkX, int chunkZ, int spacing, int separation) {
        int regionX = Math.floorDiv(chunkX, spacing);
        int regionZ = Math.floorDiv(chunkZ, spacing);
        WorldgenRandom random = new WorldgenRandom(new LegacyRandomSource(0L));
        random.setLargeFeatureWithSalt(seed, regionX, regionZ, salt());
        int spread = spacing - separation;
        int offsetX = spreadType().evaluate((RandomSource) random, spread);
        int offsetZ = spreadType().evaluate((RandomSource) random, spread);
        return new ChunkPos(regionX * spacing + offsetX, regionZ * spacing + offsetZ);
    }
}
