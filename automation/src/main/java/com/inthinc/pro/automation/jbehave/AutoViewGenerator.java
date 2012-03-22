package com.inthinc.pro.automation.jbehave;

import static java.util.Arrays.asList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jbehave.core.io.StoryNameResolver;
import org.jbehave.core.io.UnderscoredToCapitalized;
import org.jbehave.core.reporters.FreemarkerProcessor;
import org.jbehave.core.reporters.FreemarkerViewGenerator;
import org.jbehave.core.reporters.ReportsCount;
import org.jbehave.core.reporters.TemplateProcessor;

public class AutoViewGenerator extends FreemarkerViewGenerator {
    
    private final StoryNameResolver nameResolver;
    private final TemplateProcessor processor;
    private Properties viewProperties;
    private List<Report> reports = new ArrayList<Report>();
    
    public AutoViewGenerator(StoryNameResolver nameResolver, TemplateProcessor processor) {
        this.nameResolver = nameResolver;
        this.processor = processor;
    }
    
    public AutoViewGenerator(){
        this(new UnderscoredToCapitalized(), new FreemarkerProcessor());
    }
    
    
    public void generateReportsView(File outputDirectory, List<String> formats, Properties viewProperties) {
        this.viewProperties = mergeWithDefault(viewProperties);
        String outputName = templateResource("viewDirectory") + "/reports.html";
        String reportsTemplate = templateResource("reports");
        List<String> mergedFormats = mergeFormatsWithDefaults(formats);
        reports = createReports(readReportFiles(outputDirectory, outputName, mergedFormats));
        Map<String, Object> dataModel = newDataModel();
        dataModel.put("reportsTable", new ReportsTable(reports, nameResolver));
        dataModel.put("date", new Date());
        dataModel.put("timeFormatter", new TimeFormatter());
        write(outputDirectory, outputName, reportsTemplate, dataModel);
        generateViewsIndex(outputDirectory);
    }
    
    private Properties mergeWithDefault(Properties properties) {
        Properties merged = defaultViewProperties();
        merged.putAll(properties);
        return merged;
    }
    
    private String templateResource(String format) {
        return viewProperties.getProperty(format);
    }

    private Map<String, Object> newDataModel() {
        return new HashMap<String, Object>();
    }
    
    private List<String> mergeFormatsWithDefaults(List<String> formats) {
        List<String> merged = new ArrayList<String>();
        merged.addAll(asList(templateResource("defaultFormats").split(",")));
        merged.addAll(formats);
        return merged;
    }
    
    List<Report> createReports(Map<String, List<File>> reportFiles) {
        try {
            String decoratedTemplate = templateResource("decorated");
            String nonDecoratedTemplate = templateResource("nonDecorated");
            String viewDirectory = templateResource("viewDirectory");
            boolean decorateNonHtml = Boolean.valueOf(templateResource("decorateNonHtml"));
            List<Report> reports = new ArrayList<Report>();
            for (String name : reportFiles.keySet()) {
                Map<String, File> filesByFormat = new HashMap<String, File>();
                for (File file : reportFiles.get(name)) {
                    String fileName = file.getName();
                    String format = FilenameUtils.getExtension(fileName);
                    Map<String, Object> dataModel = newDataModel();
                    dataModel.put("name", name);
                    dataModel.put("body", IOUtils.toString(new FileReader(file)));
                    dataModel.put("format", format);
                    File outputDirectory = file.getParentFile();
                    String outputName = viewDirectory + "/" + fileName;
                    String template = decoratedTemplate;
                    if (!format.equals("html")) {
                        if (decorateNonHtml) {
                            outputName = outputName + ".html";
                        } else {
                            template = nonDecoratedTemplate;
                        }
                    }
                    File written = write(outputDirectory, outputName, template, dataModel);
                    filesByFormat.put(format, written);
                }
                reports.add(new Report(name, filesByFormat));
            }
            return reports;
        } catch (Exception e) {
            throw new ReportCreationFailed(reportFiles, e);
        }
    }

    SortedMap<String, List<File>> readReportFiles(File outputDirectory, final String outputName,
            final List<String> formats) {
        SortedMap<String, List<File>> reportFiles = new TreeMap<String, List<File>>();
        if (outputDirectory == null || !outputDirectory.exists()) {
            return reportFiles;
        }
        String[] fileNames = outputDirectory.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return !name.equals(outputName) && hasFormats(name, formats);
            }

            private boolean hasFormats(String name, List<String> formats) {
                for (String format : formats) {
                    if (name.endsWith(format)) {
                        return true;
                    }
                }
                return false;
            }
        });
        for (String fileName : fileNames) {
            String name = FilenameUtils.getBaseName(fileName);
            List<File> filesByName = reportFiles.get(name);
            if (filesByName == null) {
                filesByName = new ArrayList<File>();
                reportFiles.put(name, filesByName);
            }
            filesByName.add(new File(outputDirectory, fileName));
        }
        return reportFiles;
    }

    private File write(File outputDirectory, String outputName, String resource, Map<String, Object> dataModel) {
        try {
            File file = new File(outputDirectory, outputName);
            file.getParentFile().mkdirs();
            Writer writer = new FileWriter(file);
            processor.process(resource, dataModel, writer);
            return file;
        } catch (Exception e) {
            throw new ViewGenerationFailedForTemplate(resource, e);
        }
    }

    private void generateViewsIndex(File outputDirectory) {
        String outputName = templateResource("viewDirectory") + "/index.html";
        String viewsTemplate = templateResource("views");
        Map<String, Object> dataModel = newDataModel();
        dataModel.put("date", new Date());
        write(outputDirectory, outputName, viewsTemplate, dataModel);
    }
    
    public ReportsCount getReportsCount() {
        int stories = storyCount();
        int storiesNotAllowed = count("notAllowed", reports);
        int storiesPending = count("pending", reports);
        int scenarios = count("scenarios", reports);
        int scenariosFailed = count("scenariosFailed", reports);
        int scenariosNotAllowed = count("scenariosNotAllowed", reports);
        int scenariosPending = count("scenariosPending", reports);
        int stepsFailed = count("stepsFailed", reports);
        return new ReportsCount(stories, storiesNotAllowed, storiesPending, scenarios, scenariosFailed,
                scenariosNotAllowed, scenariosPending, stepsFailed);
    }
    
    private int storyCount(){
        int storyCount = 0;
        for (Report report : reports){
            if (report.getStats().containsKey("scenarios")){
                if (report.getStats().get("scenarios") > 0)
                storyCount++;
            }
        }
        return storyCount;
    }
    
    int count(String event, Collection<Report> reports) {
        int count = 0;
        for (Report report : reports) {
            Properties stats = report.asProperties("stats");
            if (stats.containsKey(event)) {
                count = count + Integer.parseInt((String) stats.get(event));
            }
        }
        return count;
    }
}
