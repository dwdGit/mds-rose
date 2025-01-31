package com.dg.mdsrose.project;

public class DBProjectService extends ProjectServiceFactory {
    @Override
    public ProjectDAO getProjectDAO() {
        return new DBProjectDAO();
    }
}
