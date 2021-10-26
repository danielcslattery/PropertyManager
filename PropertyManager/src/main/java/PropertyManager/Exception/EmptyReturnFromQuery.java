package PropertyManager.Exception;

import java.sql.SQLException;

public class EmptyReturnFromQuery extends RuntimeException {

    private String entityType;
    private String queryType;

    public EmptyReturnFromQuery(String entityType, String queryType){
        this.entityType = entityType;
        this.queryType = queryType;
    };

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
}
