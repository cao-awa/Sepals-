package com.github.cao.awa.sepals.mixin.world.poi;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestSet;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;
import java.util.Set;

@Mixin(PointOfInterestSet.class)
public interface PointOfInterestSetAccessor {
    @Accessor
    Map<RegistryEntry<PointOfInterestType>, Set<PointOfInterest>> getPointsOfInterestByType();
    @Invoker
    boolean invokeIsValid();
}
