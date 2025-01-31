package com.dg.mdsrose.project.processor;

import java.util.List;

public record FileProcessorResult(List<CompleteDatasetRow> completeDatasetRows, List<String> classes){}
