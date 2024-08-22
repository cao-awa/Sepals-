package com.github.cao.awa.sepals.mixin.entity.ai.brain.sensor.frog;

import com.github.cao.awa.sepals.Sepals;
import com.github.cao.awa.sepals.entity.ai.brain.frog.SepalsFrogBrain;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.FrogAttackablesSensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.passive.FrogEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FrogAttackablesSensor.class)
public class FrogAttackablesSensorMixin {
    @Inject(
            method = "matches",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void matches(LivingEntity entity, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (Sepals.enableSepalsFrogAttackableSensor) {
            cir.setReturnValue(SepalsFrogBrain.attackable(entity, target));
        }
    }
}
