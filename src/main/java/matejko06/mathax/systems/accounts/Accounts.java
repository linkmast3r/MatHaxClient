package matejko06.mathax.systems.accounts;

import matejko06.mathax.systems.System;
import matejko06.mathax.systems.Systems;
import matejko06.mathax.systems.accounts.types.CrackedAccount;
import matejko06.mathax.systems.accounts.types.PremiumAccount;
import matejko06.mathax.systems.accounts.types.TheAlteningAccount;
import matejko06.mathax.utils.misc.NbtException;
import matejko06.mathax.utils.misc.NbtUtils;
import matejko06.mathax.utils.network.MatHaxExecutor;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Accounts extends System<Accounts> implements Iterable<Account<?>> {
    private List<Account<?>> accounts = new ArrayList<>();

    public Accounts() {
        super("accounts");
    }

    public static Accounts get() {
        return Systems.get(Accounts.class);
    }

    @Override
    public void init() {}

    public void add(Account<?> account) {
        accounts.add(account);
        save();
    }

    public boolean exists(Account<?> account) {
        return accounts.contains(account);
    }

    public void remove(Account<?> account) {
        if (accounts.remove(account)) {
            save();
        }
    }

    public int size() {
        return accounts.size();
    }

    @Override
    public Iterator<Account<?>> iterator() {
        return accounts.iterator();
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.put("accounts", NbtUtils.listToTag(accounts));

        return tag;
    }

    @Override
    public Accounts fromTag(NbtCompound tag) {
        MatHaxExecutor.execute(() -> accounts = NbtUtils.listFromTag(tag.getList("accounts", 10), tag1 -> {
            NbtCompound t = (NbtCompound) tag1;
            if (!t.contains("type")) return null;

            AccountType type = AccountType.valueOf(t.getString("type"));

            try {
                Account<?> account = null;
                if (type == AccountType.Cracked) {
                    account = new CrackedAccount(null).fromTag(t);
                } else if (type == AccountType.Premium) {
                    account = new PremiumAccount(null, null).fromTag(t);
                } else if (type == AccountType.TheAltening) {
                    account = new TheAlteningAccount(null).fromTag(t);
                }

                if (account.fetchHead()) return account;
            } catch (NbtException e) {
                return null;
            }

            return null;
        }));

        return this;
    }
}
