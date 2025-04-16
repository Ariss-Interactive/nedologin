package ru.marduk.nedologin.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import ru.marduk.nedologin.NLConstants;

public record MessageRequestLogin() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageRequestLogin> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(NLConstants.MODID, "request_login"));
    public static final MessageRequestLogin INSTANCE = new MessageRequestLogin();
    public static final StreamCodec<ByteBuf, MessageRequestLogin> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
