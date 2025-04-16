package ru.marduk.nedologin.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import ru.marduk.nedologin.NLConstants;

public record MessageLogin(String password) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageLogin> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(NLConstants.MODID, "m_login"));
    public static final StreamCodec<ByteBuf, MessageLogin> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            MessageLogin::password,
            MessageLogin::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
