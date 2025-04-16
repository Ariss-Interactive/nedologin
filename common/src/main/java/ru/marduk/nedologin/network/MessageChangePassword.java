package ru.marduk.nedologin.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import ru.marduk.nedologin.NLConstants;

public record MessageChangePassword(String from, String to) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageChangePassword> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(NLConstants.MODID, "change_pwd"));

    public static final StreamCodec<ByteBuf, MessageChangePassword> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            MessageChangePassword::from,
            ByteBufCodecs.STRING_UTF8,
            MessageChangePassword::to,
            MessageChangePassword::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
