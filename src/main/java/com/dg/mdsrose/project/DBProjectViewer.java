package com.dg.mdsrose.project;

public class DBProjectViewer extends ProjectViewer {
    @Override
    public ProjectDAO getProjectDAO() {
        return new DBProjectDAO();
    }
}
