package com.dg.mdsrose.project;

public class InMemoryProjectService extends ProjectServiceFactory {
    @Override
    public ProjectDAO getProjectDAO() {
        return new InMemoryProjectDAO();
    }
}
