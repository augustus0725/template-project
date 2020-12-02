package com.sabo.cdh.kerberos.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author canbin.zhang
 */
public class HBaseTemplate {

    private static final String TABLE_NAME = "stu";
    private static final String CF_DEFAULT = "people";

    public static void createOrOverwrite(Admin admin, TableDescriptor table) throws IOException {
        if (admin.tableExists(table.getTableName())) {
            admin.disableTable(table.getTableName());
            admin.deleteTable(table.getTableName());
        }
        admin.createTable(table);
    }

    public static void createSchemaTables(Configuration config) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {
            List<TableDescriptor> tableDescriptors = admin.listTableDescriptorsByNamespace(Bytes.toBytes("default"));
            for (TableDescriptor table : tableDescriptors) {
                System.out.println(table.getTableName());
                ColumnFamilyDescriptor[] families = table.getColumnFamilies();
                for (ColumnFamilyDescriptor family : families) {
                    System.out.println(family.getNameAsString());

                }
            }

            ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor newCf = (ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor) ColumnFamilyDescriptorBuilder.of(CF_DEFAULT);
            newCf.setCompressionType(Compression.Algorithm.SNAPPY).setMaxVersions(5);
            TableDescriptor table = TableDescriptorBuilder
                    .newBuilder(TableName.valueOf(TABLE_NAME))
                    .setColumnFamily(newCf)
                    .build();
            System.out.print("Creating table. ");
            createOrOverwrite(admin, table);
            System.out.println(" Done.");
        }
    }

    public static void modifySchema (Configuration config) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {

            TableName tableName = TableName.valueOf(TABLE_NAME);
            if (!admin.tableExists(tableName)) {
                System.out.println("Table does not exist.");
                System.exit(-1);
            }
            TableDescriptor table = admin.getDescriptor(tableName);
            // Update existing table
            ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor newColumn = (ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor) ColumnFamilyDescriptorBuilder.of("NEWCF");
            newColumn.setCompressionType(Compression.Algorithm.SNAPPY);
            newColumn.setMaxVersions(5);
            admin.addColumnFamily(tableName, newColumn);

            // Update existing column family
            ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor existingColumn = (ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor) ColumnFamilyDescriptorBuilder.of(CF_DEFAULT);
            existingColumn.setCompressionType(Compression.Algorithm.NONE);
            admin.modifyTable(TableDescriptorBuilder.newBuilder(table).modifyColumnFamily(existingColumn).build());

            // Disable an existing table
            admin.disableTable(tableName);

            // Delete an existing column family
            admin.deleteColumnFamily(tableName, CF_DEFAULT.getBytes(StandardCharsets.UTF_8));

            // Delete a table (Need to be disabled first)
            admin.deleteTable(tableName);
        }
    }

    public static void main(String... args) throws IOException {
        Configuration config = HBaseConfiguration.create();
        System.setProperty("java.security.krb5.conf", "hbase-conf/krb5.conf");

        //Add any necessary configuration files (hbase-site.xml, core-site.xml)
        // 减少重试次数
        config.set("java.security.krb5.conf", "hbase-conf/krb5.conf");
        config.setInt("hbase.client.retries.number",2);
        config.addResource(new Path("hbase-conf", "hbase-site.xml"));
        config.addResource(new Path("hbase-conf", "core-site.xml"));
        UserGroupInformation.setConfiguration(config);
        UserGroupInformation.loginUserFromKeytab("vagrant@hongwang.com",
                "hbase-conf/vagrant.keytab");

        createSchemaTables(config);
        modifySchema(config);
    }
}
