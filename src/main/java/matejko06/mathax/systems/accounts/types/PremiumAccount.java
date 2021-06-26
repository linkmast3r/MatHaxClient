package matejko06.mathax.systems.accounts.types;

import com.google.gson.Gson;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import matejko06.mathax.MatHaxClient;
import matejko06.mathax.mixin.MinecraftClientAccessor;
import matejko06.mathax.systems.accounts.Account;
import matejko06.mathax.systems.accounts.AccountType;
import matejko06.mathax.utils.misc.NbtException;
import net.minecraft.client.util.Session;
import net.minecraft.nbt.NbtCompound;

import static matejko06.mathax.utils.Utils.mc;

public class PremiumAccount extends Account<PremiumAccount> {
    private static final Gson GSON = new Gson();

    private String password;

    public PremiumAccount(String name, String password) {
        super(AccountType.Premium, name);
        this.password = password;
    }

    @Override
    public boolean fetchInfo() {
        YggdrasilUserAuthentication auth = getAuth();

        try {
            auth.logIn();

            cache.username = auth.getSelectedProfile().getName();
            cache.uuid = auth.getSelectedProfile().getId().toString();

            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

    @Override
    public boolean fetchHead() {
        try {
            return cache.makeHead("https://www.mc-heads.net/avatar/" + cache.uuid + "/8");
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean login() {
        super.login();

        YggdrasilUserAuthentication auth = getAuth();

        try {
            auth.logIn();
            setSession(new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang"));

            cache.username = auth.getSelectedProfile().getName();
            return true;
        } catch (AuthenticationUnavailableException e) {
            MatHaxClient.LOG.error("Failed to contact the authentication server.");
            return false;
        } catch (AuthenticationException e) {
            if (e.getMessage().contains("Invalid username or password") || e.getMessage().contains("account migrated"))
                MatHaxClient.LOG.error("Wrong password.");
            else MatHaxClient.LOG.error("Failed to contact the authentication server.");
            return false;
        }
    }

    public YggdrasilUserAuthentication getAuth() {
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(((MinecraftClientAccessor) mc).getProxy(), "").createUserAuthentication(Agent.MINECRAFT);

        auth.setUsername(name);
        auth.setPassword(password);

        return auth;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = super.toTag();

        tag.putString("password", password);

        return tag;
    }

    @Override
    public PremiumAccount fromTag(NbtCompound tag) {
        super.fromTag(tag);
        if (!tag.contains("password")) throw new NbtException();

        password = tag.getString("password");

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PremiumAccount)) return false;
        return ((PremiumAccount) o).name.equals(this.name);
    }
}
