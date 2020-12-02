package com.sabo.cdh.kerberos.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * @author crazy
 * date: 2020/12/1
 */
public class EmptyConnection implements Connection {

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public BufferedMutator getBufferedMutator(TableName tableName) throws IOException {
        return null;
    }

    @Override
    public BufferedMutator getBufferedMutator(BufferedMutatorParams bufferedMutatorParams) throws IOException {
        return null;
    }

    @Override
    public RegionLocator getRegionLocator(TableName tableName) throws IOException {
        return null;
    }

    @Override
    public void clearRegionLocationCache() {

    }

    @Override
    public Admin getAdmin() throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public TableBuilder getTableBuilder(TableName tableName, ExecutorService executorService) {
        return null;
    }

    @Override
    public void abort(String s, Throwable throwable) {

    }

    @Override
    public boolean isAborted() {
        return false;
    }

    private static final EmptyConnection emptyConnection = new EmptyConnection();

    public static EmptyConnection emptyConnection() {
        return emptyConnection;
    }
}
