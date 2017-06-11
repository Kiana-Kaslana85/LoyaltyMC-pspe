package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.text.MessageFormat;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleRespawn;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe.LoginSuccess;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.Environment;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Respawn extends MiddleRespawn {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHANGE_DIMENSION, version);
		VarNumberSerializer.writeSVarInt(serializer, getPeDimensionId(dimension));
		MiscSerializer.writeLFloat(serializer, 0); //x
		MiscSerializer.writeLFloat(serializer, 0); //y
		MiscSerializer.writeLFloat(serializer, 0); //z
		serializer.writeBoolean(true); //unused value
		packets.add(serializer);
		packets.add(LoginSuccess.createPlayStatus(version, 3));
		return packets;
	}

	public static int getPeDimensionId(Environment dimId) {
		switch (dimId) {
			case NETHER: {
				return 1;
			}
			case THE_END: {
				return 2;
			}
			case OVERWORLD: {
				return 0;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("Uknown dim id {0}", dimId));
			}
		}
	}

}
