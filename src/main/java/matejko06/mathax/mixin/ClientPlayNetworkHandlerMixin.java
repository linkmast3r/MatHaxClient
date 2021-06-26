package matejko06.mathax.mixin;

import matejko06.mathax.MatHaxClient;
import matejko06.mathax.events.entity.EntityDestroyEvent;
import matejko06.mathax.events.entity.player.PickItemsEvent;
import matejko06.mathax.events.game.GameJoinedEvent;
import matejko06.mathax.events.game.GameLeftEvent;
import matejko06.mathax.events.packets.ContainerSlotUpdateEvent;
import matejko06.mathax.events.packets.PlaySoundPacketEvent;
import matejko06.mathax.events.world.ChunkDataEvent;
import matejko06.mathax.mixininterface.IExplosionS2CPacket;
import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.movement.Velocity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow private ClientWorld world;

    private boolean worldNotNull;

    @Inject(at = @At("HEAD"), method = "onGameJoin")
    private void onGameJoinHead(GameJoinS2CPacket packet, CallbackInfo info) {
        worldNotNull = world != null;
    }

    @Inject(at = @At("TAIL"), method = "onGameJoin")
    private void onGameJoinTail(GameJoinS2CPacket packet, CallbackInfo info) {
        if (worldNotNull) {
            MatHaxClient.EVENT_BUS.post(GameLeftEvent.get());
        }

        MatHaxClient.EVENT_BUS.post(GameJoinedEvent.get());
    }

    @Inject(at = @At("HEAD"), method = "onPlaySound")
    private void onPlaySound(PlaySoundS2CPacket packet, CallbackInfo info) {
        MatHaxClient.EVENT_BUS.post(PlaySoundPacketEvent.get(packet));
    }

    @Inject(method = "onChunkData", at = @At("TAIL"))
    private void onChunkData(ChunkDataS2CPacket packet, CallbackInfo info) {
        WorldChunk chunk = client.world.getChunk(packet.getX(), packet.getZ());
        MatHaxClient.EVENT_BUS.post(ChunkDataEvent.get(chunk));
    }

    @Inject(method = "onScreenHandlerSlotUpdate", at = @At("TAIL"))
    private void onContainerSlotUpdate(ScreenHandlerSlotUpdateS2CPacket packet, CallbackInfo info) {
        MatHaxClient.EVENT_BUS.post(ContainerSlotUpdateEvent.get(packet));
    }

    @Inject(method = "onEntityDestroy", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;removeEntity(ILnet/minecraft/entity/Entity$RemovalReason;)V"))
    private void onEntityDestroy(EntityDestroyS2CPacket packet, CallbackInfo info) {
        MatHaxClient.EVENT_BUS.post(EntityDestroyEvent.get(client.world.getEntityById(packet.getEntityId())));
    }

    @Inject(method = "onExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/util/thread/ThreadExecutor;)V", shift = At.Shift.AFTER))
    private void onExplosionVelocity(ExplosionS2CPacket packet, CallbackInfo ci) {
        Velocity velocity = Modules.get().get(Velocity.class); //Velocity for explosions
        if (!velocity.explosions.get()) return;

        ((IExplosionS2CPacket) packet).setVelocityX((float) (packet.getPlayerVelocityX() * velocity.getHorizontal(velocity.explosionsHorizontal)));
        ((IExplosionS2CPacket) packet).setVelocityY((float) (packet.getPlayerVelocityY() * velocity.getVertical(velocity.explosionsVertical)));
        ((IExplosionS2CPacket) packet).setVelocityZ((float) (packet.getPlayerVelocityZ() * velocity.getHorizontal(velocity.explosionsHorizontal)));
    }

    @Inject(method = "onItemPickupAnimation", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getEntityById(I)Lnet/minecraft/entity/Entity;", ordinal = 0))
    private void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet, CallbackInfo info) {
        Entity itemEntity = client.world.getEntityById(packet.getEntityId());
        Entity entity = client.world.getEntityById(packet.getCollectorEntityId());

        if (itemEntity instanceof ItemEntity && entity == client.player) {
            MatHaxClient.EVENT_BUS.post(PickItemsEvent.get(((ItemEntity) itemEntity).getStack(), packet.getStackAmount()));
        }
    }
}