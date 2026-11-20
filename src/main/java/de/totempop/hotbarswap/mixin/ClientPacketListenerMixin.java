package de.totempop.hotbarswap.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
    // Vanilla entity event 35 means that a totem of undying activated.
    private static final int TOTEM_POP_EVENT = 35;
    private static final int HOTBAR_SIZE = 9;

    @Inject(method = "handleEntityEvent", at = @At("TAIL"))
    private void totemPopHotbarSwap$onEntityEvent(ClientboundEntityEventPacket packet, CallbackInfo ci) {
        if (packet.getEventId() != TOTEM_POP_EVENT) return;

        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.level == null || client.getConnection() == null) return;
        // Only react to our own pops, not other players or mobs.
        if (packet.getEntity(client.level) != client.player) return;

        Inventory inventory = client.player.getInventory();
        int current = inventory.getSelectedSlot();

        // Search cyclically to the right, wrapping around at slot 9.
        // Include the current slot last, just in case it still contains a totem.
        for (int offset = 1; offset <= HOTBAR_SIZE; offset++) {
            int slot = (current + offset) % HOTBAR_SIZE;
            if (inventory.getItem(slot).is(Items.TOTEM_OF_UNDYING)) {
                if (slot != current) {
                    inventory.setSelectedSlot(slot);
                    // Immediately synchronize the selected hotbar slot with the server.
                    client.getConnection().send(new ServerboundSetCarriedItemPacket(slot));
                }
                return;
            }
        }
        // No remaining totem in the hotbar: keep the current slot unchanged.
    }
}
