package com.dg.mdsrose.project;

import com.dg.mdsrose.view.ShowProject;

public abstract class ProjectViewer {
    public void retrieveProject(Long projectId) {
        new ShowProject(new ProjectService(getProjectDAO()), projectId);
    }

    public abstract ProjectDAO getProjectDAO();
}
