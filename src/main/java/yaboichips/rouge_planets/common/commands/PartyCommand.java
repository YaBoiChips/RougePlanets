package yaboichips.rouge_planets.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import yaboichips.rouge_planets.common.world.parties.PartyData;

import java.util.UUID;

public class PartyCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("party")
                .then(Commands.literal("create")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            PartyData data = PartyData.get(player.serverLevel());

                            if (data.isInParty(player.getUUID())) {
                                ctx.getSource().sendFailure(Component.literal("You're already in a party."));
                                return 0;
                            }

                            data.createParty(player.getUUID());
                            ctx.getSource().sendSuccess(() -> Component.literal("Party created."), false);
                            return 1;
                        }))
                .then(Commands.literal("invite")
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(ctx -> {
                                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                                    ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
                                    PartyData data = PartyData.get(player.serverLevel());

                                    if (!data.isLeader(player.getUUID())) {
                                        ctx.getSource().sendFailure(Component.literal("Only the party leader can invite."));
                                        return 0;
                                    }
                                    if (data.isInParty(target.getUUID())) {
                                        ctx.getSource().sendFailure(Component.literal("That player is already in a party."));
                                        return 0;
                                    }

                                    data.addPlayerToParty(player.getUUID(), target.getUUID());
                                    ctx.getSource().sendSuccess(() -> Component.literal("Invited " + target.getName().getString() + " to your party."), false);
                                    target.sendSystemMessage(Component.literal("You've been added to " + player.getName().getString() + "'s party."));
                                    return 1;
                                })))
                .then(Commands.literal("remove")
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(ctx -> {
                                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                                    ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
                                    PartyData data = PartyData.get(player.serverLevel());

                                    if (!data.isLeader(player.getUUID())) {
                                        ctx.getSource().sendFailure(Component.literal("Only the party leader can remove players."));
                                        return 0;
                                    }
                                    if (!data.isInParty(target.getUUID()) || data.getParty(target.getUUID()) != data.getParty(player.getUUID())) {
                                        ctx.getSource().sendFailure(Component.literal("That player is not in your party."));
                                        return 0;
                                    }

                                    data.removePlayerFromParty(target.getUUID());
                                    ctx.getSource().sendSuccess(() -> Component.literal("Removed " + target.getName().getString() + " from the party."), false);
                                    target.sendSystemMessage(Component.literal("You have been removed from the party."));
                                    return 1;
                                })))
                .then(Commands.literal("list")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            PartyData data = PartyData.get(player.serverLevel());

                            if (!data.isInParty(player.getUUID())) {
                                ctx.getSource().sendFailure(Component.literal("You're not in a party."));
                                return 0;
                            }

                            PartyData.Party party = data.getParty(player.getUUID());
                            StringBuilder sb = new StringBuilder("Party Members:\n");
                            for (var uuid : party.members) {
                                sb.append("- ").append(ctx.getSource().getServer().getPlayerList().getPlayer(uuid) != null ?
                                        ctx.getSource().getServer().getPlayerList().getPlayer(uuid).getName().getString() :
                                        uuid.toString()).append(uuid.equals(party.leader) ? " (Leader)" : "").append("\n");
                            }

                            ctx.getSource().sendSuccess(() -> Component.literal(sb.toString()), false);
                            return 1;
                        }))
                .then(Commands.literal("disband")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            PartyData data = PartyData.get(player.serverLevel());

                            if (!data.isLeader(player.getUUID())) {
                                ctx.getSource().sendFailure(Component.literal("Only the leader can disband the party."));
                                return 0;
                            }

                            PartyData.Party party = data.getParty(player.getUUID());
                            for (var uuid : party.members) {
                                ServerPlayer member = ctx.getSource().getServer().getPlayerList().getPlayer(uuid);
                                if (member != null) {
                                    member.sendSystemMessage(Component.literal("Your party has been disbanded."));
                                }
                            }

                            data.disbandParty(player.getUUID());
                            ctx.getSource().sendSuccess(() -> Component.literal("Party disbanded."), false);
                            return 1;
                        }))
                .then(Commands.literal("leave")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            PartyData data = PartyData.get(player.serverLevel());

                            UUID playerId = player.getUUID();

                            if (!data.isInParty(playerId)) {
                                ctx.getSource().sendFailure(Component.literal("You are not in a party."));
                                return 0;
                            }

                            if (data.isLeader(playerId)) {
                                // Leader leaves = disband
                                PartyData.Party party = data.getParty(playerId);
                                for (UUID memberId : party.members) {
                                    ServerPlayer member = ctx.getSource().getServer().getPlayerList().getPlayer(memberId);
                                    if (member != null && !memberId.equals(playerId)) {
                                        member.sendSystemMessage(Component.literal("The party has been disbanded."));
                                    }
                                }
                                data.disbandParty(playerId);
                                ctx.getSource().sendSuccess(() -> Component.literal("You left and disbanded the party."), false);
                            } else {
                                data.removePlayerFromParty(playerId);
                                ctx.getSource().sendSuccess(() -> Component.literal("You left the party."), false);
                            }

                            return 1;
                        }))
        );
    }
}
