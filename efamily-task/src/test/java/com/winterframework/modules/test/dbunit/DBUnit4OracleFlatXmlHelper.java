package com.winterframework.modules.test.dbunit;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;

public class DBUnit4OracleFlatXmlHelper extends DBUnitFlatXmlHelper {
	@Override
	public void setDataSource(DataSource dataSource, String jdbcSchema) throws Exception {
		super.setDataSource(dataSource, jdbcSchema);

		DatabaseConnection databaseConnection = (new DatabaseConnection(dataSource.getConnection(), jdbcSchema));
		databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new MySqlDataTypeFactory());
		super.setDatabaseConnection(databaseConnection);
	}
}
