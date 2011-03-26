package example.domain.hibernate;

import com.dbdeploy.DbDeploy;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcOperations;

public class DbDeployer implements InitializingBean {

    private final JdbcOperations template;
    private final Resource createSql;
    private final Resource deltasDir;
    private final DbDeploy deploy;

    public DbDeployer(JdbcOperations template, Resource createSql, Resource deltasDir, DbDeploy deploy) {
        this.createSql = createSql;
        this.deltasDir = deltasDir;
        this.template = template;
        this.deploy = deploy;
    }

    public void afterPropertiesSet() throws Exception {
        createChangeLogTable();
        deployDeltas();
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
