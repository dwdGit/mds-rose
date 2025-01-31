package com.dg.mdsrose.project;

public abstract class ProjectServiceFactory {
    public ProjectService createProjectService() {
        return new ProjectService(getProjectDAO());
    }

    public abstract ProjectDAO getProjectDAO();
}
