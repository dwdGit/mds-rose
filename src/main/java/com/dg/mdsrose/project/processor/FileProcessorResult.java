package com.dg.mdsrose.project.processor;

import java.util.List;

public record FileProcessorResult(List<DatasetRow> datasetRows, List<String> classes){}
