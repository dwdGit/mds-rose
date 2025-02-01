package com.dg.mdsrose.project;

public class DBProjectService extends ProjectServiceFactory {
    private static ProjectService instance;

    private DBProjectService() {}

    public static ProjectService getInstance() {
        if (instance == null) {
            instance = new DBProjectService().createProjectService();
        }
        return instance;
    }
    @Override
    public ProjectDAO getProjectDAO() {
        return new DBProjectDAO();
    }
}
