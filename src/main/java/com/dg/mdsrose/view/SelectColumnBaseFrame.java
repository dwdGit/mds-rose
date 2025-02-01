package com.dg.mdsrose.view;

import com.dg.mdsrose.project.InMemoryProjectService;
import com.dg.mdsrose.project.processor.FileProcessor;
import com.dg.mdsrose.project.processor.FileProcessorResult;

import java.io.IOException;
import java.util.Map;

public abstract class SelectColumnBaseFrame extends BaseJFrame {
    protected void partialSave(FileProcessor fileProcessor, Map<Integer, String> selectedColumns) {
        try {
            FileProcessorResult result = fileProcessor.process();
            InMemoryProjectService.getInstance()
                .partialSave(
                    result.completeDatasetRows(),
                    result.classes(),
                    selectedColumns
                );
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
