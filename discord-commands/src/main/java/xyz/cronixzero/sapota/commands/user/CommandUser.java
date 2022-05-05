package xyz.cronixzero.sapota.commands.user;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class CommandUser {

    private User user;
    private Member member;

    public CommandUser(User user, Member member) {
        this.user = user;
        this.member = member;
    }

    public CommandUser(User user) {
        this.user = user;
    }

    public CommandUser(Member member) {
        this.member = member;
    }

    public User getUser() {
        return user;
    }

    public Member getMember() {
        return member;
    }
}
