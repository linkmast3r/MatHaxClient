package matejko06.mathax.systems.accounts.types;

import matejko06.mathax.systems.accounts.Account;
import matejko06.mathax.systems.accounts.AccountType;
import matejko06.mathax.systems.accounts.ProfileResponse;
import matejko06.mathax.utils.network.HttpUtils;
import net.minecraft.client.util.Session;

public class CrackedAccount extends Account<CrackedAccount> {
    public CrackedAccount(String name) {
        super(AccountType.Cracked, name);

    }

    @Override
    public boolean fetchInfo() {
        cache.username = name;
        return true;
    }

    @Override
    public boolean fetchHead() {
        try {
            ProfileResponse response = HttpUtils.get("https://api.mojang.com/users/profiles/minecraft/" + cache.username, ProfileResponse.class);
            return cache.makeHead("https://www.mc-heads.net/avatar/" + response.getId() + "/8");
        } catch (Exception e) {
            return cache.makeHead("steve");
        }
    }

    @Override
    public boolean login() {
        super.login();

        setSession(new Session(name, "", "", "mojang"));
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CrackedAccount)) return false;
        return ((CrackedAccount) o).getUsername().equals(this.getUsername());
    }
}