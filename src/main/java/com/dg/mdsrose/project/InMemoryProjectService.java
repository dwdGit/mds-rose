package com.dg.mdsrose.project;

public class InMemoryProjectService extends ProjectServiceFactory {
    private static ProjectService instance;

    private InMemoryProjectService() {}

    public static ProjectService getInstance() {
        if (instance == null) {
            instance = new InMemoryProjectService().createProjectService();
        }
        return instance;
    }

    @Override
    public ProjectDAO getProjectDAO() {
        return new InMemoryProjectDAO();
    }
}
