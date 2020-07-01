package itx.examples.mongodb.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import itx.examples.mongodb.Utils;
import itx.examples.mongodb.dto.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.eq;

public class RoleServiceImpl implements RoleService {

    final private static Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);

    private MongoDatabase database;

    public RoleServiceImpl(MongoDatabase database) {
        this.database = database;
    }

    @Override
    public Collection<Role> getRoles() {
        LOG.info("getRoles");
        MongoCollection<Role> collection = database.getCollection(Utils.ROLES_COLLECTION_NAME, Role.class);
        Collection<Role> roles = new ArrayList<>();
        MongoCursor<Role> rolesIterator = collection.find().iterator();
        while (rolesIterator.hasNext()) {
            roles.add(rolesIterator.next());
        }
        return roles;
    }

    @Override
    public void insertRole(Role role) throws DataException {
        LOG.info("insert role: {} {}", role.getId(), role.getDescription());
        MongoCollection<Role> collection = database.getCollection(Utils.ROLES_COLLECTION_NAME, Role.class);
        collection.insertOne(role);
    }

    @Override
    public void removeRole(String id) throws DataException {
        LOG.info("remove role: {}", id);
        MongoCollection<Role> collection = database.getCollection(Utils.ROLES_COLLECTION_NAME, Role.class);
        DeleteResult deleteResult = collection.deleteOne(eq("_id", id));
        LOG.info("deleted {}", deleteResult.getDeletedCount());
    }

    @Override
    public void removeAll() throws DataException {
        LOG.info("remove all");
        MongoCollection<Role> collection = database.getCollection(Utils.ROLES_COLLECTION_NAME, Role.class);
        collection.drop();
    }

}
