package example.domain.services.hibernate;

import com.dbdeploy.DbDeploy;
import example.spring.ApplicationStatus;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcOperations;

public class DbDeployer implements InitializingBean, ApplicationStatus {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final JdbcOperations template;
    private final Resource createSql;
    private final Resource deltasDir;
    private final DbDeploy deploy;

    private boolean ready = false;

    public DbDeployer(JdbcOperations template, Resource createSql, Resource deltasDir, DbDeploy deploy) {
        this.createSql = createSql;
        this.deltasDir = deltasDir;
        this.template = template;
        this.deploy = deploy;
    }

    public void afterPropertiesSet() {
        try {
            createChangeLogTable();
            deployDeltas();
            ready = true;

        } catch (Exception e) {
            logger.error("Unable to deploy database updates. Application startup failed!", e);
        }
    }

    public boolean ready() {
        return ready;
    }

    private void createChangeLogTable() throws Exception {
        try {
            String sql = FileUtils.readFileToString(createSql.getFile());
            template.update(sql);

        } catch (BadSqlGrammarException e) {
            // table already exists
        }
    }

    private void deployDeltas() throws Exception {
        deploy.setScriptdirectory(deltasDir.getFile());
        deploy.go();
    }
}
