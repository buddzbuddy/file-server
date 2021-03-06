package itx.fileserver.services.data.base;

import itx.fileserver.services.data.UserManagerService;
import itx.fileserver.services.dto.RoleId;
import itx.fileserver.services.dto.UserData;
import itx.fileserver.services.dto.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public abstract class UserManagerServiceImpl implements UserManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(UserManagerServiceImpl.class);

    protected Map<UserId, UserData> users;
    protected RoleId anonymousRole;
    protected RoleId adminRole;

    @Override
    public Optional<UserData> getUser(UserId id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Collection<UserData> getUsers() {
        return Collections.unmodifiableList(new ArrayList<>(users.values()));
    }

    @Override
    public void addUser(UserData userData) {
        if (users.get(userData.getId()) != null) {
            throw new UnsupportedOperationException();
        }
        users.put(userData.getId(), userData);
        persist();
    }

    @Override
    public void removeUser(UserId id) {
        users.remove(id);
        persist();
    }

    @Override
    public RoleId getAnonymousRole() {
        return anonymousRole;
    }

    @Override
    public RoleId getAdminRole() {
        return adminRole;
    }

    public abstract void persist();

}
