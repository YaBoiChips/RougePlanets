package yaboichips.rouge_planets.common.world.parties;

import com.google.common.collect.Sets;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class PartyData extends SavedData {
    private static final String DATA_NAME = "my_party_data";

    private final Map<UUID, Party> playerPartyMap = new HashMap<>();
    private final Map<UUID, Party> parties = new HashMap<>();

    public static PartyData get(ServerLevel level) {
        return level.getServer().overworld().getDataStorage().computeIfAbsent(
                nbt -> fromNbt(nbt),
                PartyData::new,
                DATA_NAME
        );
    }

    private static PartyData fromNbt(CompoundTag tag) {
        PartyData data = new PartyData();
        ListTag partyList = tag.getList("parties", 10);
        for (int i = 0; i < partyList.size(); i++) {
            CompoundTag partyTag = partyList.getCompound(i);
            Party party = Party.fromNbt(partyTag);
            data.parties.put(party.leader, party);
            for (UUID member : party.members) {
                data.playerPartyMap.put(member, party);
            }
        }
        return data;
    }


    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag partyList = new ListTag();
        for (Party party : new HashSet<>(parties.values())) {
            partyList.add(party.toNbt());
        }
        tag.put("parties", partyList);
        return tag;
    }

    // === API ===

    public boolean isInParty(UUID player) {
        return playerPartyMap.containsKey(player);
    }

    public boolean isLeader(UUID player) {
        return playerPartyMap.containsKey(player) && playerPartyMap.get(player).leader.equals(player);
    }

    public Party getParty(UUID player) {
        return playerPartyMap.get(player);
    }

    public void createParty(UUID leader) {
        if (isInParty(leader)) return;
        Party party = new Party(leader);
        parties.put(leader, party);
        playerPartyMap.put(leader, party);
        setDirty();
    }

    public void addPlayerToParty(UUID leader, UUID player) {
        Party party = parties.get(leader);
        if (party != null && !isInParty(player)) {
            party.members.add(player);
            playerPartyMap.put(player, party);
            setDirty();
        }
    }

    public void removePlayerFromParty(UUID player) {
        Party party = playerPartyMap.get(player);
        if (party != null) {
            party.members.remove(player);
            playerPartyMap.remove(player);
            if (party.members.isEmpty()) {
                parties.remove(party.leader);
            }
            setDirty();
        }
    }

    public void disbandParty(UUID leader) {
        Party party = parties.remove(leader);
        if (party != null) {
            for (UUID member : new HashSet<>(party.members)) {
                playerPartyMap.remove(member);
            }
            setDirty();
        }
    }

    // === Inner Party Class ===
    public static class Party {
        public final UUID leader;
        public final Set<UUID> members = Sets.newHashSet();
        private boolean leaderInDimension = false;

        public Party(UUID leader) {
            this.leader = leader;
            members.add(leader);
        }

        public boolean isLeaderInDimension() {
            return leaderInDimension;
        }

        public void setLeaderInDimension(boolean inDimension) {
            this.leaderInDimension = inDimension;
        }
        public void toggleLeaderInDimension() {
            this.leaderInDimension = !this.leaderInDimension;
        }

        public CompoundTag toNbt() {
            CompoundTag tag = new CompoundTag();
            tag.putUUID("leader", leader);
            ListTag memberList = new ListTag();
            for (UUID uuid : members) {
                memberList.add(StringTag.valueOf(uuid.toString()));
            }
            tag.put("members", memberList);
            tag.putBoolean("leaderInDimension", leaderInDimension);
            return tag;
        }

        public static Party fromNbt(CompoundTag tag) {
            UUID leader = tag.getUUID("leader");
            Party party = new Party(leader);
            party.members.clear();
            ListTag memberList = tag.getList("members", 8);
            for (int i = 0; i < memberList.size(); i++) {
                UUID member = UUID.fromString(memberList.getString(i));
                party.members.add(member);
            }
            party.leaderInDimension = tag.getBoolean("leaderInDimension");
            return party;
        }
    }
}

