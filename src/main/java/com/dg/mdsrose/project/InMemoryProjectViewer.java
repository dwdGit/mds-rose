package com.dg.mdsrose.project;

public class InMemoryProjectViewer extends ProjectViewer {
    @Override
    public ProjectDAO getProjectDAO() {
        return new InMemoryProjectDAO();
    }
}
